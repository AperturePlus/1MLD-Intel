package xenosoft.imldintelligence.module.screening.internal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xenosoft.imldintelligence.module.screening.api.dto.ScreeningApiDtos;
import xenosoft.imldintelligence.module.screening.internal.model.Questionnaire;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireQuestion;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireResponse;
import xenosoft.imldintelligence.module.screening.internal.model.TocClinicalTransfer;
import xenosoft.imldintelligence.module.screening.internal.repository.QuestionnaireQuestionRepository;
import xenosoft.imldintelligence.module.screening.internal.repository.QuestionnaireRepository;
import xenosoft.imldintelligence.module.screening.internal.repository.QuestionnaireResponseRepository;
import xenosoft.imldintelligence.module.screening.internal.repository.TocClinicalTransferRepository;
import xenosoft.imldintelligence.module.screening.internal.service.ScreeningService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;
    private final QuestionnaireResponseRepository questionnaireResponseRepository;
    private final TocClinicalTransferRepository tocClinicalTransferRepository;

    @Override
    public long countQuestionnaires(Long tenantId, ScreeningApiDtos.Query.QuestionnairePageQuery query) {
        return listAllQuestionnaires(tenantId, query).size();
    }

    @Override
    public List<Questionnaire> listQuestionnaires(Long tenantId,
                                                   ScreeningApiDtos.Query.QuestionnairePageQuery query,
                                                   long offset, int limit) {
        return paginate(listAllQuestionnaires(tenantId, query), offset, limit);
    }

    @Override
    public Questionnaire publishQuestionnaire(Long tenantId,
                                               ScreeningApiDtos.Request.PublishQuestionnaireRequest request) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setTenantId(tenantId);
        questionnaire.setQuestionnaireCode(request.questionnaireCode());
        questionnaire.setTitle(request.title());
        questionnaire.setDescription(request.description());
        questionnaire.setDiseaseScope(request.diseaseScope());
        questionnaire.setStatus("ACTIVE");
        questionnaire.setVersionNo(request.versionNo() != null ? request.versionNo() : 1);
        questionnaire.setValidatedFlag(request.validatedFlag() != null ? request.validatedFlag() : true);
        questionnaire.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        Questionnaire saved = questionnaireRepository.save(questionnaire);

        if (request.questions() != null) {
            for (ScreeningApiDtos.Shared.QuestionItem q : request.questions()) {
                QuestionnaireQuestion question = new QuestionnaireQuestion();
                question.setTenantId(tenantId);
                question.setQuestionnaireId(saved.getId());
                question.setQuestionNo(q.questionNo());
                question.setContent(q.content());
                question.setQuestionType(q.questionType());
                question.setOptionsJson(q.optionsJson());
                question.setScoringRuleJson(q.scoringRuleJson());
                question.setSortOrder(q.sortOrder() != null ? q.sortOrder() : 0);
                question.setRequiredFlag(q.requiredFlag() != null ? q.requiredFlag() : true);
                question.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
                questionnaireQuestionRepository.save(question);
            }
        }
        return saved;
    }

    @Override
    public long countResponses(Long tenantId, ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query) {
        return listAllResponses(tenantId, query).size();
    }

    @Override
    public List<QuestionnaireResponse> listResponses(Long tenantId,
                                                       ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query,
                                                       long offset, int limit) {
        return paginate(listAllResponses(tenantId, query), offset, limit);
    }

    @Override
    public QuestionnaireResponse submitResponse(Long tenantId,
                                                  ScreeningApiDtos.Request.SubmitQuestionnaireResponseRequest request) {
        QuestionnaireResponse response = new QuestionnaireResponse();
        response.setTenantId(tenantId);
        response.setQuestionnaireId(request.questionnaireId());
        response.setTocUserId(request.tocUserId());
        response.setResponseNo(UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase());
        response.setRiskScore(request.riskScore());
        response.setRiskLevel(request.riskLevel());
        response.setSuggestion(request.suggestion());
        response.setCanShowPatient(request.canShowPatient() != null ? request.canShowPatient() : true);
        response.setDataConsent(request.dataConsent() != null ? request.dataConsent() : false);
        response.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        response.setExpiresAt(request.expiresAt());
        return questionnaireResponseRepository.save(response);
    }

    @Override
    public long countTransfers(Long tenantId, ScreeningApiDtos.Query.TransferPageQuery query) {
        return listAllTransfers(tenantId, query).size();
    }

    @Override
    public List<TocClinicalTransfer> listTransfers(Long tenantId,
                                                     ScreeningApiDtos.Query.TransferPageQuery query,
                                                     long offset, int limit) {
        return paginate(listAllTransfers(tenantId, query), offset, limit);
    }

    @Override
    public TocClinicalTransfer approveTransfer(Long tenantId,
                                                ScreeningApiDtos.Request.ApproveClinicalTransferRequest request) {
        TocClinicalTransfer transfer = new TocClinicalTransfer();
        transfer.setTenantId(tenantId);
        transfer.setResponseId(request.responseId());
        transfer.setPatientId(request.patientId());
        transfer.setApprovedBy(request.approvedBy());
        transfer.setTransferStatus(request.transferStatus());
        transfer.setTransferNote(request.transferNote());
        transfer.setApprovedAt(OffsetDateTime.now(ZoneOffset.UTC));
        transfer.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return tocClinicalTransferRepository.save(transfer);
    }

    private List<Questionnaire> listAllQuestionnaires(Long tenantId,
                                                       ScreeningApiDtos.Query.QuestionnairePageQuery query) {
        return questionnaireRepository.listByTenantId(tenantId).stream()
                .filter(q -> query.diseaseScope() == null || query.diseaseScope().equals(q.getDiseaseScope()))
                .filter(q -> query.status() == null || query.status().equals(q.getStatus()))
                .filter(q -> query.validatedFlag() == null || query.validatedFlag().equals(q.getValidatedFlag()))
                .filter(q -> query.versionNo() == null || query.versionNo().equals(q.getVersionNo()))
                .toList();
    }

    private List<QuestionnaireResponse> listAllResponses(Long tenantId,
                                                           ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query) {
        List<QuestionnaireResponse> list;
        if (query.questionnaireId() != null) {
            list = questionnaireResponseRepository.listByQuestionnaireId(tenantId, query.questionnaireId());
        } else if (query.tocUserId() != null) {
            list = questionnaireResponseRepository.listByTocUserId(tenantId, query.tocUserId());
        } else {
            list = questionnaireResponseRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(r -> query.riskLevel() == null || query.riskLevel().equals(r.getRiskLevel()))
                .filter(r -> query.dataConsent() == null || query.dataConsent().equals(r.getDataConsent()))
                .filter(r -> query.createdFrom() == null || (r.getCreatedAt() != null && !r.getCreatedAt().isBefore(query.createdFrom())))
                .filter(r -> query.createdTo() == null || (r.getCreatedAt() != null && !r.getCreatedAt().isAfter(query.createdTo())))
                .toList();
    }

    private List<TocClinicalTransfer> listAllTransfers(Long tenantId,
                                                         ScreeningApiDtos.Query.TransferPageQuery query) {
        List<TocClinicalTransfer> list;
        if (query.responseId() != null) {
            list = tocClinicalTransferRepository.listByResponseId(tenantId, query.responseId());
        } else if (query.patientId() != null) {
            list = tocClinicalTransferRepository.listByPatientId(tenantId, query.patientId());
        } else {
            list = tocClinicalTransferRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(t -> query.transferStatus() == null || query.transferStatus().equals(t.getTransferStatus()))
                .filter(t -> query.approvedBy() == null || query.approvedBy().equals(t.getApprovedBy()))
                .toList();
    }

    private <T> List<T> paginate(List<T> list, long offset, int limit) {
        int from = (int) Math.min(offset, list.size());
        int to = (int) Math.min(offset + limit, list.size());
        return list.subList(from, to);
    }
}
