package xenosoft.imldintelligence.module.diagnoses.internal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xenosoft.imldintelligence.module.diagnoses.api.dto.DiagnosesApiDtos;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DoctorFeedback;
import xenosoft.imldintelligence.module.diagnoses.internal.model.ModelRegistry;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisSessionRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DoctorFeedbackRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.ModelRegistryRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.service.DiagnosesService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosesServiceImpl implements DiagnosesService {

    private final DiagnosisSessionRepository diagnosisSessionRepository;
    private final DoctorFeedbackRepository doctorFeedbackRepository;
    private final ModelRegistryRepository modelRegistryRepository;

    @Override
    public long countSessions(Long tenantId, DiagnosesApiDtos.Query.SessionPageQuery query) {
        return listAllSessions(tenantId, query).size();
    }

    @Override
    public List<DiagnosisSession> listSessions(Long tenantId, DiagnosesApiDtos.Query.SessionPageQuery query,
                                                long offset, int limit) {
        return paginate(listAllSessions(tenantId, query), offset, limit);
    }

    @Override
    public DiagnosisSession getSession(Long tenantId, Long sessionId) {
        return diagnosisSessionRepository.findById(tenantId, sessionId)
                .orElseThrow(() -> new IllegalArgumentException("DiagnosisSession not found: " + sessionId));
    }

    @Override
    public DiagnosisSession startSession(Long tenantId,
                                          DiagnosesApiDtos.Request.StartDiagnosisSessionRequest request) {
        DiagnosisSession session = new DiagnosisSession();
        session.setTenantId(tenantId);
        session.setPatientId(request.patientId());
        session.setEncounterId(request.encounterId());
        session.setDoctorId(request.doctorId());
        session.setTriggeredBy(request.triggeredBy());
        session.setModelRegistryId(request.modelRegistryId());
        session.setInputSnapshot(request.inputSnapshot());
        session.setStatus("RUNNING");
        session.setStartedAt(OffsetDateTime.now(ZoneOffset.UTC));
        session.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return diagnosisSessionRepository.save(session);
    }

    @Override
    public DiagnosisSession submitDoctorFeedback(Long tenantId,
                                                   DiagnosesApiDtos.Request.SubmitDoctorFeedbackRequest request) {
        DiagnosisSession session = diagnosisSessionRepository.findById(tenantId, request.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("DiagnosisSession not found: " + request.sessionId()));

        DoctorFeedback feedback = new DoctorFeedback();
        feedback.setTenantId(tenantId);
        feedback.setSessionId(request.sessionId());
        feedback.setResultId(request.resultId());
        feedback.setDoctorId(request.doctorId());
        feedback.setAction(request.action());
        feedback.setModifiedValue(request.modifiedValue());
        feedback.setRejectReason(request.rejectReason());
        feedback.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        doctorFeedbackRepository.save(feedback);

        return session;
    }

    @Override
    public long countModels(Long tenantId, DiagnosesApiDtos.Query.ModelRegistryPageQuery query) {
        return listAllModels(tenantId, query).size();
    }

    @Override
    public List<ModelRegistry> listModels(Long tenantId, DiagnosesApiDtos.Query.ModelRegistryPageQuery query,
                                           long offset, int limit) {
        return paginate(listAllModels(tenantId, query), offset, limit);
    }

    @Override
    public ModelRegistry registerModel(Long tenantId, DiagnosesApiDtos.Request.RegisterModelRequest request) {
        ModelRegistry model = new ModelRegistry();
        model.setTenantId(tenantId);
        model.setModelCode(request.modelCode());
        model.setModelName(request.modelName());
        model.setModelType(request.modelType());
        model.setModelVersion(request.modelVersion());
        model.setProvider(request.provider());
        model.setStatus(request.status() != null ? request.status() : "ACTIVE");
        model.setReleasedAt(request.releasedAt());
        model.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return modelRegistryRepository.save(model);
    }

    private List<DiagnosisSession> listAllSessions(Long tenantId,
                                                     DiagnosesApiDtos.Query.SessionPageQuery query) {
        List<DiagnosisSession> list;
        if (query.patientId() != null) {
            list = diagnosisSessionRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.encounterId() != null) {
            list = diagnosisSessionRepository.listByEncounterId(tenantId, query.encounterId());
        } else {
            list = diagnosisSessionRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(s -> query.doctorId() == null || query.doctorId().equals(s.getDoctorId()))
                .filter(s -> query.triggeredBy() == null || query.triggeredBy().equals(s.getTriggeredBy()))
                .filter(s -> query.status() == null || query.status().equals(s.getStatus()))
                .filter(s -> query.startedFrom() == null || (s.getStartedAt() != null && !s.getStartedAt().isBefore(query.startedFrom())))
                .filter(s -> query.startedTo() == null || (s.getStartedAt() != null && !s.getStartedAt().isAfter(query.startedTo())))
                .toList();
    }

    private List<ModelRegistry> listAllModels(Long tenantId,
                                                DiagnosesApiDtos.Query.ModelRegistryPageQuery query) {
        return modelRegistryRepository.listByTenantId(tenantId).stream()
                .filter(m -> query.provider() == null || query.provider().equals(m.getProvider()))
                .filter(m -> query.modelType() == null || query.modelType().equals(m.getModelType()))
                .filter(m -> query.status() == null || query.status().equals(m.getStatus()))
                .toList();
    }

    private <T> List<T> paginate(List<T> list, long offset, int limit) {
        int from = (int) Math.min(offset, list.size());
        int to = (int) Math.min(offset + limit, list.size());
        return list.subList(from, to);
    }
}
