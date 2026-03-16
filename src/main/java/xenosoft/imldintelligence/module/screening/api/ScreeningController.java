package xenosoft.imldintelligence.module.screening.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import xenosoft.imldintelligence.common.CheckPermission;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.screening.api.dto.ScreeningApiDtos;
import xenosoft.imldintelligence.module.screening.internal.model.Questionnaire;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireResponse;
import xenosoft.imldintelligence.module.screening.internal.model.TocClinicalTransfer;
import xenosoft.imldintelligence.module.screening.internal.repository.QuestionnaireQuestionRepository;
import xenosoft.imldintelligence.module.screening.internal.service.ScreeningService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScreeningController implements ScreeningControllerContract {

    private final ScreeningService screeningService;
    private final QuestionnaireQuestionRepository questionnaireQuestionRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Override
    @CheckPermission(resource = "SCREENING", action = "READ")
    public ApiResponse<PagedResultResponse<ScreeningApiDtos.Response.QuestionnaireDetailResponse>> listQuestionnaires(
            Long tenantId,
            ScreeningApiDtos.Query.QuestionnairePageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = screeningService.countQuestionnaires(tenantId, query);
        List<Questionnaire> items = screeningService.listQuestionnaires(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(q -> toQuestionnaireDetailResponse(tenantId, q)).toList()));
    }

    @Override
    @CheckPermission(resource = "SCREENING", action = "CREATE")
    public ApiResponse<ScreeningApiDtos.Response.QuestionnaireDetailResponse> publishQuestionnaire(
            Long tenantId,
            ScreeningApiDtos.Request.PublishQuestionnaireRequest request) {
        Questionnaire questionnaire = screeningService.publishQuestionnaire(tenantId, request);
        return ApiResponse.success(toQuestionnaireDetailResponse(tenantId, questionnaire));
    }

    @Override
    @CheckPermission(resource = "SCREENING", action = "READ")
    public ApiResponse<PagedResultResponse<ScreeningApiDtos.Response.QuestionnaireSubmissionResponse>> listResponses(
            Long tenantId,
            ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = screeningService.countResponses(tenantId, query);
        List<QuestionnaireResponse> items = screeningService.listResponses(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toSubmissionResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "SCREENING", action = "CREATE")
    public ApiResponse<ScreeningApiDtos.Response.QuestionnaireSubmissionResponse> submitResponse(
            Long tenantId,
            ScreeningApiDtos.Request.SubmitQuestionnaireResponseRequest request) {
        QuestionnaireResponse response = screeningService.submitResponse(tenantId, request);
        return ApiResponse.success(toSubmissionResponse(response));
    }

    @Override
    @CheckPermission(resource = "SCREENING", action = "READ")
    public ApiResponse<PagedResultResponse<ScreeningApiDtos.Response.ClinicalTransferResponse>> listTransfers(
            Long tenantId,
            ScreeningApiDtos.Query.TransferPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = screeningService.countTransfers(tenantId, query);
        List<TocClinicalTransfer> items = screeningService.listTransfers(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toTransferResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "SCREENING", action = "UPDATE")
    public ApiResponse<ScreeningApiDtos.Response.ClinicalTransferResponse> approveTransfer(
            Long tenantId,
            ScreeningApiDtos.Request.ApproveClinicalTransferRequest request) {
        TocClinicalTransfer transfer = screeningService.approveTransfer(tenantId, request);
        return ApiResponse.success(toTransferResponse(transfer));
    }

    private ScreeningApiDtos.Response.QuestionnaireDetailResponse toQuestionnaireDetailResponse(Long tenantId,
                                                                                                  Questionnaire q) {
        List<ScreeningApiDtos.Shared.QuestionItem> questions = questionnaireQuestionRepository
                .listByQuestionnaireId(tenantId, q.getId()).stream()
                .map(qq -> new ScreeningApiDtos.Shared.QuestionItem(
                        qq.getQuestionNo(), qq.getContent(), qq.getQuestionType(),
                        qq.getOptionsJson(), qq.getScoringRuleJson(),
                        qq.getSortOrder(), qq.getRequiredFlag()))
                .toList();
        return new ScreeningApiDtos.Response.QuestionnaireDetailResponse(
                q.getId(), q.getQuestionnaireCode(), q.getTitle(), q.getDescription(),
                q.getDiseaseScope(), q.getStatus(), q.getVersionNo(), q.getValidatedFlag(),
                q.getCreatedAt(), questions);
    }

    private ScreeningApiDtos.Response.QuestionnaireSubmissionResponse toSubmissionResponse(QuestionnaireResponse r) {
        return new ScreeningApiDtos.Response.QuestionnaireSubmissionResponse(
                r.getId(), r.getQuestionnaireId(), r.getTocUserId(), r.getResponseNo(),
                r.getRiskScore(), r.getRiskLevel(), r.getSuggestion(),
                r.getCanShowPatient(), r.getDataConsent(), r.getCreatedAt(), r.getExpiresAt());
    }

    private ScreeningApiDtos.Response.ClinicalTransferResponse toTransferResponse(TocClinicalTransfer t) {
        return new ScreeningApiDtos.Response.ClinicalTransferResponse(
                t.getId(), t.getResponseId(), t.getPatientId(), t.getTransferStatus(),
                t.getApprovedBy(), t.getApprovedAt(), t.getTransferNote(), t.getCreatedAt());
    }
}
