package xenosoft.imldintelligence.module.diagnoses.api;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticReport;
import xenosoft.imldintelligence.module.clinical.internal.model.LabResult;
import xenosoft.imldintelligence.module.clinical.internal.model.ClinicalHistoryEntry;
import xenosoft.imldintelligence.module.clinical.internal.repository.ClinicalHistoryEntryRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.GeneticReportRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.GeneticVariantRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.LabResultRepository;
import xenosoft.imldintelligence.module.diagnoses.api.dto.DiagnosesApiDtos;
import xenosoft.imldintelligence.module.diagnoses.api.dto.ImldInferenceApiDtos;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisRecommendation;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisResult;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DoctorFeedback;
import xenosoft.imldintelligence.module.diagnoses.internal.model.ModelRegistry;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisRecommendationRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisResultRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisSessionRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DoctorFeedbackRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.ModelRegistryRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.service.ImldInferenceService;
import xenosoft.imldintelligence.module.identity.internal.model.Encounter;
import xenosoft.imldintelligence.module.identity.internal.model.Patient;
import xenosoft.imldintelligence.module.identity.internal.model.UserAccount;
import xenosoft.imldintelligence.module.identity.internal.repository.EncounterRepository;
import xenosoft.imldintelligence.module.identity.internal.repository.PatientRepository;
import xenosoft.imldintelligence.module.identity.internal.repository.UserAccountRepository;

@RestController
@RequiredArgsConstructor
public class DiagnosesController implements DiagnosesControllerContract {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final String STATUS_RUNNING = "RUNNING";
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_FAILED = "FAILED";
    private static final String STATUS_REVIEWED = "REVIEWED";

    private final DiagnosisSessionRepository sessionRepository;
    private final DiagnosisResultRepository resultRepository;
    private final DiagnosisRecommendationRepository recommendationRepository;
    private final DoctorFeedbackRepository feedbackRepository;
    private final ModelRegistryRepository modelRegistryRepository;
    private final PatientRepository patientRepository;
    private final EncounterRepository encounterRepository;
    private final UserAccountRepository userAccountRepository;
    private final LabResultRepository labResultRepository;
    private final ClinicalHistoryEntryRepository clinicalHistoryEntryRepository;
    private final GeneticReportRepository geneticReportRepository;
    private final GeneticVariantRepository geneticVariantRepository;
    private final ImldInferenceService inferenceService;
    private final ObjectMapper objectMapper;

    @Override
    public ApiResponse<PagedResultResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse>> listSessions(
            Long tenantId,
            DiagnosesApiDtos.Query.SessionPageQuery query,
            PageQueryRequest pageQuery) {
        DiagnosesApiDtos.Query.SessionPageQuery q = query == null
                ? new DiagnosesApiDtos.Query.SessionPageQuery(null, null, null, null, null, null, null)
                : query;
        List<DiagnosisSession> sessions = pickSessions(tenantId, q).stream()
                .filter(s -> q.doctorId() == null || Objects.equals(q.doctorId(), s.getDoctorId()))
                .filter(s -> q.triggeredBy() == null || eqIgnoreCase(q.triggeredBy(), s.getTriggeredBy()))
                .filter(s -> q.status() == null || eqIgnoreCase(q.status(), s.getStatus()))
                .filter(s -> q.startedFrom() == null || !sortTime(s).isBefore(q.startedFrom()))
                .filter(s -> q.startedTo() == null || !sortTime(s).isAfter(q.startedTo()))
                .sorted(Comparator
                        .comparing(this::sortTime, Comparator.reverseOrder())
                        .thenComparing(DiagnosisSession::getId, Comparator.reverseOrder()))
                .toList();

        int page = pageQuery == null || pageQuery.page() == null ? DEFAULT_PAGE : Math.max(pageQuery.page(), 0);
        int size = pageQuery == null || pageQuery.size() == null ? DEFAULT_SIZE : Math.min(Math.max(pageQuery.size(), 1), 200);
        int from = Math.min(page * size, sessions.size());
        int to = Math.min(from + size, sessions.size());
        List<DiagnosesApiDtos.Response.DiagnosisSessionResponse> items = sessions.subList(from, to).stream()
                .map(s -> toSessionResponse(tenantId, s))
                .toList();
        return ApiResponse.success(new PagedResultResponse<>(page, size, sessions.size(), items));
    }

    @Override
    public ApiResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse> getSession(Long tenantId, Long sessionId) {
        DiagnosisSession session = sessionRepository.findById(tenantId, sessionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diagnosis session not found"));
        return ApiResponse.success(toSessionResponse(tenantId, session));
    }

    @Override
    @Transactional
    public ApiResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse> startSession(
            Long tenantId,
            DiagnosesApiDtos.Request.StartDiagnosisSessionRequest request) {
        if (request == null || request.patientId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "patientId is required");
        }
        Patient patient = patientRepository.findById(tenantId, request.patientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "patientId does not exist"));
        Encounter encounter = resolveEncounter(tenantId, request.patientId(), request.encounterId());
        Long doctorId = resolveDoctorId(tenantId, request.doctorId(), encounter);
        ModelRegistry model = resolveModel(tenantId, request.modelRegistryId());
        ObjectNode derivedSourceSnapshot = buildDerivedSourceSnapshot(tenantId, patient, encounter);
        ImldInferenceApiDtos.Request.ImldPredictRequest inferenceRequest =
                buildInferenceRequest(tenantId, patient, encounter, request.inputSnapshot(), derivedSourceSnapshot);

        DiagnosisSession session = new DiagnosisSession();
        session.setTenantId(tenantId);
        session.setPatientId(patient.getId());
        session.setEncounterId(encounter == null ? request.encounterId() : encounter.getId());
        session.setDoctorId(doctorId);
        session.setTriggeredBy(normalize(request.triggeredBy(), "MANUAL"));
        session.setModelRegistryId(model.getId());
        session.setInputSnapshot(buildSnapshot(request.inputSnapshot(), inferenceRequest, derivedSourceSnapshot));
        session.setStatus(STATUS_RUNNING);
        session.setStartedAt(now());
        sessionRepository.save(session);

        try {
            ImldInferenceApiDtos.Response.PredictData prediction = inferenceService.predict(inferenceRequest);
            savePrediction(tenantId, session, prediction);
            session.setStatus(STATUS_COMPLETED);
            session.setCompletedAt(now());
            sessionRepository.update(session);
        } catch (RuntimeException ex) {
            session.setStatus(STATUS_FAILED);
            session.setCompletedAt(now());
            sessionRepository.update(session);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "IMLD inference failed", ex);
        }
        return ApiResponse.success(toSessionResponse(tenantId, session));
    }

    @Override
    @Transactional
    public ApiResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse> submitDoctorFeedback(
            Long tenantId,
            DiagnosesApiDtos.Request.SubmitDoctorFeedbackRequest request) {
        if (request == null || request.sessionId() == null || request.resultId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sessionId and resultId are required");
        }
        DiagnosisSession session = sessionRepository.findById(tenantId, request.sessionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diagnosis session not found"));
        DiagnosisResult result = resultRepository.findById(tenantId, request.resultId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "resultId does not exist"));
        if (!Objects.equals(result.getSessionId(), session.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "resultId does not belong to sessionId");
        }

        DoctorFeedback feedback = new DoctorFeedback();
        feedback.setTenantId(tenantId);
        feedback.setSessionId(session.getId());
        feedback.setResultId(result.getId());
        Long feedbackDoctorId = request.doctorId() != null
                ? resolveDoctorId(tenantId, request.doctorId(), null)
                : (session.getDoctorId() != null ? session.getDoctorId() : resolveDoctorId(tenantId, null, null));
        feedback.setDoctorId(feedbackDoctorId);
        feedback.setAction(normalize(request.action(), "MODIFY"));
        feedback.setModifiedValue(request.modifiedValue());
        feedback.setRejectReason(trimToNull(request.rejectReason()));
        feedbackRepository.save(feedback);

        if (Set.of("ACCEPT", "MODIFY").contains(feedback.getAction())) {
            session.setStatus(STATUS_REVIEWED);
            sessionRepository.update(session);
        }
        return ApiResponse.success(toSessionResponse(tenantId, session));
    }

    @Override
    public ApiResponse<PagedResultResponse<DiagnosesApiDtos.Response.ModelRegistryResponse>> listModels(
            Long tenantId,
            DiagnosesApiDtos.Query.ModelRegistryPageQuery query,
            PageQueryRequest pageQuery) {
        DiagnosesApiDtos.Query.ModelRegistryPageQuery q = query == null
                ? new DiagnosesApiDtos.Query.ModelRegistryPageQuery(null, null, null)
                : query;
        List<ModelRegistry> filtered = modelRegistryRepository.listByTenantId(tenantId).stream()
                .filter(m -> q.provider() == null || eqIgnoreCase(q.provider(), m.getProvider()))
                .filter(m -> q.modelType() == null || eqIgnoreCase(q.modelType(), m.getModelType()))
                .filter(m -> q.status() == null || eqIgnoreCase(q.status(), m.getStatus()))
                .sorted(Comparator.comparing(ModelRegistry::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();
        int page = pageQuery == null || pageQuery.page() == null ? DEFAULT_PAGE : Math.max(pageQuery.page(), 0);
        int size = pageQuery == null || pageQuery.size() == null ? DEFAULT_SIZE : Math.min(Math.max(pageQuery.size(), 1), 200);
        int from = Math.min(page * size, filtered.size());
        int to = Math.min(from + size, filtered.size());
        List<DiagnosesApiDtos.Response.ModelRegistryResponse> items = filtered.subList(from, to).stream()
                .map(this::toModelResponse)
                .toList();
        return ApiResponse.success(new PagedResultResponse<>(page, size, filtered.size(), items));
    }

    @Override
    @Transactional
    public ApiResponse<DiagnosesApiDtos.Response.ModelRegistryResponse> registerModel(
            Long tenantId,
            DiagnosesApiDtos.Request.RegisterModelRequest request) {
        if (modelRegistryRepository.findByModelCodeAndModelVersion(tenantId, request.modelCode(), request.modelVersion()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "modelCode + modelVersion already exists");
        }
        ModelRegistry model = new ModelRegistry();
        model.setTenantId(tenantId);
        model.setModelCode(request.modelCode().trim());
        model.setModelName(request.modelName().trim());
        model.setModelType(request.modelType().trim());
        model.setModelVersion(request.modelVersion().trim());
        model.setProvider(request.provider().trim());
        model.setStatus(normalize(request.status(), "ACTIVE"));
        model.setReleasedAt(request.releasedAt());
        modelRegistryRepository.save(model);
        return ApiResponse.success(toModelResponse(model));
    }

    private List<DiagnosisSession> pickSessions(Long tenantId, DiagnosesApiDtos.Query.SessionPageQuery q) {
        if (q.patientId() != null) return sessionRepository.listByPatientId(tenantId, q.patientId());
        if (q.encounterId() != null) return sessionRepository.listByEncounterId(tenantId, q.encounterId());
        return sessionRepository.listByTenantId(tenantId);
    }

    private DiagnosesApiDtos.Response.DiagnosisSessionResponse toSessionResponse(Long tenantId, DiagnosisSession s) {
        return new DiagnosesApiDtos.Response.DiagnosisSessionResponse(
                s.getId(), s.getPatientId(), s.getEncounterId(), s.getDoctorId(), s.getTriggeredBy(), s.getModelRegistryId(),
                s.getStatus(), s.getStartedAt(), s.getCompletedAt(),
                resultRepository.listBySessionId(tenantId, s.getId()).stream().map(r -> new DiagnosesApiDtos.Shared.DiagnosisResultItem(
                        r.getId(), r.getDiseaseCode(), r.getDiseaseName(), r.getConfidence(), r.getRankNo(), r.getRiskLevel(),
                        r.getEvidenceJson(), r.getIsDisplayToPatient(), r.getCreatedAt())).toList(),
                recommendationRepository.listBySessionId(tenantId, s.getId()).stream().map(r -> new DiagnosesApiDtos.Shared.DiagnosisRecommendationItem(
                        r.getId(), r.getRecType(), r.getContent(), r.getPriority(), r.getReason(), r.getCreatedAt())).toList(),
                feedbackRepository.listBySessionId(tenantId, s.getId()).stream().map(f -> new DiagnosesApiDtos.Shared.DoctorFeedbackItem(
                        f.getId(), f.getResultId(), f.getDoctorId(), f.getAction(), f.getModifiedValue(), f.getRejectReason(), f.getCreatedAt())).toList()
        );
    }

    private DiagnosesApiDtos.Response.ModelRegistryResponse toModelResponse(ModelRegistry m) {
        return new DiagnosesApiDtos.Response.ModelRegistryResponse(
                m.getId(), m.getModelCode(), m.getModelName(), m.getModelType(), m.getModelVersion(),
                m.getProvider(), m.getStatus(), m.getReleasedAt(), m.getCreatedAt()
        );
    }

    private Encounter resolveEncounter(Long tenantId, Long patientId, Long encounterId) {
        if (encounterId != null) {
            return encounterRepository.findById(tenantId, encounterId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "encounterId does not exist"));
        }
        return encounterRepository.listByPatientId(tenantId, patientId).stream()
                .sorted(Comparator.comparing(Encounter::getStartAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .findFirst().orElse(null);
    }

    private Long resolveDoctorId(Long tenantId, Long doctorId, Encounter encounter) {
        if (doctorId != null) {
            return userAccountRepository.findById(tenantId, doctorId).map(UserAccount::getId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "doctorId does not exist"));
        }
        if (encounter != null && encounter.getAttendingDoctorId() != null &&
                userAccountRepository.findById(tenantId, encounter.getAttendingDoctorId()).isPresent()) {
            return encounter.getAttendingDoctorId();
        }
        return userAccountRepository.listByTenantId(tenantId).stream()
                .filter(u -> "ACTIVE".equalsIgnoreCase(u.getStatus()))
                .findFirst().map(UserAccount::getId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No active doctor in tenant"));
    }

    private ModelRegistry resolveModel(Long tenantId, Long modelRegistryId) {
        if (modelRegistryId != null) {
            return modelRegistryRepository.findById(tenantId, modelRegistryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "modelRegistryId does not exist"));
        }
        Optional<ModelRegistry> active = modelRegistryRepository.listByTenantId(tenantId).stream()
                .filter(m -> "ACTIVE".equalsIgnoreCase(m.getStatus()))
                .findFirst();
        if (active.isPresent()) {
            return active.get();
        }
        Optional<ModelRegistry> builtIn = modelRegistryRepository.findByModelCodeAndModelVersion(
                tenantId, "IMLD_XGBOOST", "v2_gene_clinical_fusion"
        );
        if (builtIn.isPresent()) {
            ModelRegistry existing = builtIn.get();
            if (!"ACTIVE".equalsIgnoreCase(existing.getStatus())) {
                existing.setStatus("ACTIVE");
                modelRegistryRepository.update(existing);
            }
            return existing;
        }
        ModelRegistry created = new ModelRegistry();
        created.setTenantId(tenantId);
        created.setModelCode("IMLD_XGBOOST");
        created.setModelName("IMLD XGBoost Inference");
        created.setModelType("ML");
        created.setModelVersion("v2_gene_clinical_fusion");
        created.setProvider("LOCAL");
        created.setStatus("ACTIVE");
        created.setReleasedAt(now());
        modelRegistryRepository.save(created);
        return created;
    }

    private ImldInferenceApiDtos.Request.ImldPredictRequest buildInferenceRequest(
            Long tenantId, Patient patient, Encounter encounter, JsonNode input, ObjectNode derivedSourceSnapshot) {
        JsonNode n = input != null && input.has("inference_input") ? input.get("inference_input") : input;
        List<LabResult> labs = encounter != null ? labResultRepository.listByEncounterId(tenantId, encounter.getId())
                : labResultRepository.listByPatientId(tenantId, patient.getId());
        if (labs.isEmpty()) labs = labResultRepository.listByPatientId(tenantId, patient.getId());
        int age = clamp(intOf(n, "age").orElse(ageOf(patient)), 1, 120);
        int gender = clamp(intOf(n, "gender").orElse(genderFlag(patient.getGender())), 0, 1);
        double alt = Math.max(0D, doubleOf(n, "ALT", "alt").orElse(indicatorValue(labs, Set.of("ALT", "GPT")).orElse(45D)));
        double bilirubin = Math.max(0D, doubleOf(n, "bilirubin", "TBIL").orElse(indicatorValue(labs, Set.of("TBIL", "BILIRUBIN")).orElse(21D)));
        double ceruloplasmin = Math.max(0D, doubleOf(n, "ceruloplasmin", "CP").orElse(indicatorValue(labs, Set.of("CERULOPLASMIN", "CP")).orElse(180D)));
        int jaundice = clamp(intOf(n, "jaundice").orElse(0), 0, 1);
        Integer nasScore = intOf(n, "nasScore", "nas_score").orElseGet(() -> pathologyNasScore(derivedSourceSnapshot));
        List<ImldInferenceApiDtos.Request.GeneVariant> geneVariants = geneVariants(tenantId, patient.getId(), encounter, n);
        return new ImldInferenceApiDtos.Request.ImldPredictRequest(age, gender, alt, bilirubin, ceruloplasmin, jaundice, nasScore, geneVariants,
                patient.getPatientNo() == null ? String.valueOf(patient.getId()) : patient.getPatientNo());
    }

    private List<ImldInferenceApiDtos.Request.GeneVariant> geneVariants(Long tenantId, Long patientId, Encounter encounter, JsonNode n) {
        JsonNode array = n != null && n.has("gene_variants") ? n.get("gene_variants") : n != null ? n.get("geneVariants") : null;
        if (array != null && array.isArray()) {
            List<ImldInferenceApiDtos.Request.GeneVariant> variants = new ArrayList<>();
            for (JsonNode item : array) {
                variants.add(new ImldInferenceApiDtos.Request.GeneVariant(
                        strOf(item, "gene").orElse("UNKNOWN"),
                        strOf(item, "c_change", "cChange").orElse(null),
                        strOf(item, "p_change", "pChange").orElse(null),
                        strOf(item, "variant_type", "variantType").map(String::toLowerCase).orElse("unknown"),
                        strOf(item, "zygosity").map(String::toLowerCase).orElse("unknown"),
                        strOf(item, "pathogenicity", "classification").map(String::toLowerCase).orElse("unknown"),
                        doubleOf(item, "allele_frequency", "alleleFrequency").orElse(null)
                ));
            }
            return variants;
        }
        List<GeneticReport> reports = encounter != null ? geneticReportRepository.listByEncounterId(tenantId, encounter.getId())
                : geneticReportRepository.listByPatientId(tenantId, patientId);
        Optional<GeneticReport> latest = reports.stream().sorted(Comparator.comparing(GeneticReport::getCreatedAt).reversed()).findFirst();
        if (latest.isEmpty()) return List.of();
        return geneticVariantRepository.listByReportId(tenantId, latest.get().getId()).stream()
                .map(v -> new ImldInferenceApiDtos.Request.GeneVariant(
                        v.getGene(), v.getHgvsC(), v.getHgvsP(), normalize(v.getVariantType(), "unknown").toLowerCase(Locale.ROOT),
                        normalize(v.getZygosity(), "unknown").toLowerCase(Locale.ROOT),
                        normalize(v.getClassification(), "unknown").toLowerCase(Locale.ROOT), null
                )).toList();
    }

    private ObjectNode buildDerivedSourceSnapshot(Long tenantId, Patient patient, Encounter encounter) {
        ObjectNode derived = objectMapper.createObjectNode();
        findLatestPathologyEntry(tenantId, patient.getId(), encounter).ifPresent(entry -> {
            ObjectNode pathology = objectMapper.createObjectNode();
            JsonNode content = entry.getContentJson();
            if (content != null && content.get("reportText") != null && !content.get("reportText").isNull()) {
                pathology.put("reportText", content.get("reportText").asText());
            }
            if (content != null && content.get("nasScore") != null && !content.get("nasScore").isNull()) {
                pathology.put("nasScore", content.get("nasScore").asInt());
            }
            if (content != null && content.get("reportedAt") != null && !content.get("reportedAt").isNull()) {
                pathology.put("reportedAt", content.get("reportedAt").asText());
            }
            derived.set("pathology", pathology);
        });
        return derived;
    }

    private Optional<ClinicalHistoryEntry> findLatestPathologyEntry(Long tenantId, Long patientId, Encounter encounter) {
        List<ClinicalHistoryEntry> entries = encounter != null
                ? clinicalHistoryEntryRepository.listByEncounterId(tenantId, encounter.getId())
                : clinicalHistoryEntryRepository.listByPatientId(tenantId, patientId);
        return entries.stream()
                .filter(entry -> "PATHOLOGY".equalsIgnoreCase(entry.getHistoryType()))
                .sorted(Comparator.comparing((ClinicalHistoryEntry entry) -> entry.getRecordedAt() != null ? entry.getRecordedAt() : now()).reversed()
                        .thenComparing(ClinicalHistoryEntry::getId, Comparator.reverseOrder()))
                .findFirst();
    }

    private Integer pathologyNasScore(ObjectNode derivedSourceSnapshot) {
        if (derivedSourceSnapshot == null || derivedSourceSnapshot.get("pathology") == null) {
            return null;
        }
        JsonNode pathology = derivedSourceSnapshot.get("pathology");
        return pathology != null && pathology.get("nasScore") != null && !pathology.get("nasScore").isNull()
                ? pathology.get("nasScore").asInt()
                : null;
    }

    private void mergeSourceSnapshot(ObjectNode target, ObjectNode derived) {
        if (target == null || derived == null) {
            return;
        }
        derived.fields().forEachRemaining(entry -> target.set(entry.getKey(), entry.getValue()));
    }

    private JsonNode buildSnapshot(JsonNode input,
                                   ImldInferenceApiDtos.Request.ImldPredictRequest inferenceRequest,
                                   ObjectNode derivedSourceSnapshot) {
        if (input != null && input.has("inference_input")) {
            if (input.isObject() && derivedSourceSnapshot != null && !derivedSourceSnapshot.isEmpty()) {
                ObjectNode snapshot = ((ObjectNode) input).deepCopy();
                ObjectNode sourceSnapshot = snapshot.has("source_snapshot") && snapshot.get("source_snapshot").isObject()
                        ? (ObjectNode) snapshot.get("source_snapshot")
                        : objectMapper.createObjectNode();
                mergeSourceSnapshot(sourceSnapshot, derivedSourceSnapshot);
                snapshot.set("source_snapshot", sourceSnapshot);
                return snapshot;
            }
            return input;
        }
        ObjectNode node = objectMapper.createObjectNode();
        ObjectNode sourceSnapshot = input != null && input.isObject() ? ((ObjectNode) input).deepCopy() : objectMapper.createObjectNode();
        mergeSourceSnapshot(sourceSnapshot, derivedSourceSnapshot);
        if (!sourceSnapshot.isEmpty()) {
            node.set("source_snapshot", sourceSnapshot);
        }
        node.set("inference_input", objectMapper.valueToTree(inferenceRequest));
        return node;
    }

    private void savePrediction(Long tenantId, DiagnosisSession session, ImldInferenceApiDtos.Response.PredictData p) {
        String diseaseCode = "IMLD_RISK";
        String diseaseName = "遗传代谢性肝病风险提示";
        List<String> genes = p.geneAbnormalities().stream().map(ImldInferenceApiDtos.Response.GeneAbnormality::gene).filter(Objects::nonNull).toList();
        if (genes.contains("ATP7B")) {
            diseaseCode = "WILSON_DISEASE";
            diseaseName = "肝豆状核变性（Wilson病）";
        } else if (genes.contains("HFE")) {
            diseaseCode = "HEMOCHROMATOSIS";
            diseaseName = "遗传性血色病";
        }

        DiagnosisResult result = new DiagnosisResult();
        result.setTenantId(tenantId);
        result.setSessionId(session.getId());
        result.setDiseaseCode(diseaseCode);
        result.setDiseaseName(diseaseName);
        result.setConfidence(p.riskProbability());
        result.setRankNo(1);
        result.setRiskLevel(p.riskLevel());
        ObjectNode evidence = objectMapper.createObjectNode();
        evidence.set("inference", objectMapper.valueToTree(p));
        evidence.put("generated_at", now().toString());
        result.setEvidenceJson(evidence);
        result.setIsDisplayToPatient(Boolean.FALSE);
        resultRepository.save(result);

        List<String> suggestions = p.suggestions() == null ? List.of() : p.suggestions();
        int priority = 10;
        for (String suggestion : suggestions) {
            if (suggestion == null || suggestion.isBlank()) continue;
            DiagnosisRecommendation rec = new DiagnosisRecommendation();
            rec.setTenantId(tenantId);
            rec.setSessionId(session.getId());
            rec.setRecType(recType(suggestion));
            rec.setContent(suggestion);
            rec.setPriority(priority);
            rec.setReason("IMLD_MODEL");
            recommendationRepository.save(rec);
            priority += 10;
        }
    }

    private String recType(String text) {
        String n = text.toLowerCase(Locale.ROOT);
        if (n.contains("基因") || n.contains("gene")) return "GENETIC";
        if (n.contains("饮食") || n.contains("膳食") || n.contains("diet")) return "DIET";
        if (n.contains("检查") || n.contains("检验") || n.contains("exam")) return "EXAM";
        return "FOLLOWUP";
    }

    private int ageOf(Patient patient) {
        LocalDate birth = patient.getBirthDate();
        return birth == null ? 40 : (int) ChronoUnit.YEARS.between(birth, LocalDate.now(ZoneOffset.UTC));
    }

    private int genderFlag(String gender) {
        String n = normalize(gender, "1").toLowerCase(Locale.ROOT);
        return n.equals("0") || n.equals("f") || n.equals("female") || n.equals("女") ? 0 : 1;
    }

    private Optional<Double> indicatorValue(List<LabResult> labs, Set<String> aliases) {
        Set<String> normalizedAliases = aliases.stream().map(s -> s.toUpperCase(Locale.ROOT)).collect(Collectors.toSet());
        return labs.stream()
                .filter(l -> l.getIndicatorCode() != null && normalizedAliases.contains(l.getIndicatorCode().toUpperCase(Locale.ROOT)))
                .map(LabResult::getValueNumeric).filter(Objects::nonNull).findFirst();
    }

    private Optional<String> strOf(JsonNode node, String... keys) {
        if (node == null) return Optional.empty();
        for (String key : keys) {
            JsonNode value = node.get(key);
            if (value != null && !value.isNull() && !value.asText().isBlank()) return Optional.of(value.asText().trim());
        }
        return Optional.empty();
    }

    private Optional<Integer> intOf(JsonNode node, String... keys) {
        return strOf(node, keys).flatMap(value -> {
            try { return Optional.of(Integer.valueOf(value)); } catch (NumberFormatException ignored) { return Optional.empty(); }
        });
    }

    private Optional<Double> doubleOf(JsonNode node, String... keys) {
        return strOf(node, keys).flatMap(value -> {
            try { return Optional.of(Double.valueOf(value)); } catch (NumberFormatException ignored) { return Optional.empty(); }
        });
    }

    private String normalize(String value, String fallback) {
        String trimmed = trimToNull(value);
        return trimmed == null ? fallback : trimmed.toUpperCase(Locale.ROOT);
    }

    private boolean eqIgnoreCase(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }

    private String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC).withNano(0);
    }

    private OffsetDateTime sortTime(DiagnosisSession s) {
        return s.getStartedAt() != null ? s.getStartedAt() : (s.getCreatedAt() != null ? s.getCreatedAt() : now());
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
