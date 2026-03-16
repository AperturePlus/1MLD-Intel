package xenosoft.imldintelligence.module.diagnoses.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import xenosoft.imldintelligence.common.CheckPermission;
import xenosoft.imldintelligence.common.RequireAnyRole;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.diagnoses.api.dto.DiagnosesApiDtos;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;
import xenosoft.imldintelligence.module.diagnoses.internal.model.ModelRegistry;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisRecommendationRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisResultRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DoctorFeedbackRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.service.DiagnosesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiagnosesController implements DiagnosesControllerContract {

    private final DiagnosesService diagnosesService;
    private final DiagnosisResultRepository diagnosisResultRepository;
    private final DiagnosisRecommendationRepository diagnosisRecommendationRepository;
    private final DoctorFeedbackRepository doctorFeedbackRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Override
    @CheckPermission(resource = "DIAGNOSIS", action = "READ")
    public ApiResponse<PagedResultResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse>> listSessions(
            Long tenantId,
            DiagnosesApiDtos.Query.SessionPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = diagnosesService.countSessions(tenantId, query);
        List<DiagnosisSession> items = diagnosesService.listSessions(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(s -> toSessionResponse(tenantId, s)).toList()));
    }

    @Override
    @CheckPermission(resource = "DIAGNOSIS", action = "READ")
    public ApiResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse> getSession(
            Long tenantId, Long sessionId) {
        DiagnosisSession session = diagnosesService.getSession(tenantId, sessionId);
        return ApiResponse.success(toSessionResponse(tenantId, session));
    }

    @Override
    @CheckPermission(resource = "DIAGNOSIS", action = "CREATE")
    public ApiResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse> startSession(
            Long tenantId,
            DiagnosesApiDtos.Request.StartDiagnosisSessionRequest request) {
        DiagnosisSession session = diagnosesService.startSession(tenantId, request);
        return ApiResponse.success(toSessionResponse(tenantId, session));
    }

    @Override
    @CheckPermission(resource = "DIAGNOSIS", action = "UPDATE")
    public ApiResponse<DiagnosesApiDtos.Response.DiagnosisSessionResponse> submitDoctorFeedback(
            Long tenantId,
            DiagnosesApiDtos.Request.SubmitDoctorFeedbackRequest request) {
        DiagnosisSession session = diagnosesService.submitDoctorFeedback(tenantId, request);
        return ApiResponse.success(toSessionResponse(tenantId, session));
    }

    @Override
    @RequireAnyRole({"SYSTEM_ADMIN", "DATA_ADMIN"})
    public ApiResponse<PagedResultResponse<DiagnosesApiDtos.Response.ModelRegistryResponse>> listModels(
            Long tenantId,
            DiagnosesApiDtos.Query.ModelRegistryPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = diagnosesService.countModels(tenantId, query);
        List<ModelRegistry> items = diagnosesService.listModels(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toModelRegistryResponse).toList()));
    }

    @Override
    @RequireAnyRole({"SYSTEM_ADMIN", "DATA_ADMIN"})
    public ApiResponse<DiagnosesApiDtos.Response.ModelRegistryResponse> registerModel(
            Long tenantId,
            DiagnosesApiDtos.Request.RegisterModelRequest request) {
        ModelRegistry model = diagnosesService.registerModel(tenantId, request);
        return ApiResponse.success(toModelRegistryResponse(model));
    }

    private DiagnosesApiDtos.Response.DiagnosisSessionResponse toSessionResponse(Long tenantId,
                                                                                   DiagnosisSession s) {
        List<DiagnosesApiDtos.Shared.DiagnosisResultItem> results = diagnosisResultRepository
                .listBySessionId(tenantId, s.getId()).stream()
                .map(r -> new DiagnosesApiDtos.Shared.DiagnosisResultItem(
                        r.getId(), r.getDiseaseCode(), r.getDiseaseName(), r.getConfidence(),
                        r.getRankNo(), r.getRiskLevel(), r.getEvidenceJson(),
                        r.getIsDisplayToPatient(), r.getCreatedAt()))
                .toList();

        List<DiagnosesApiDtos.Shared.DiagnosisRecommendationItem> recommendations =
                diagnosisRecommendationRepository.listBySessionId(tenantId, s.getId()).stream()
                        .map(rec -> new DiagnosesApiDtos.Shared.DiagnosisRecommendationItem(
                                rec.getId(), rec.getRecType(), rec.getContent(),
                                rec.getPriority(), rec.getReason(), rec.getCreatedAt()))
                        .toList();

        List<DiagnosesApiDtos.Shared.DoctorFeedbackItem> feedbacks = doctorFeedbackRepository
                .listBySessionId(tenantId, s.getId()).stream()
                .map(f -> new DiagnosesApiDtos.Shared.DoctorFeedbackItem(
                        f.getId(), f.getResultId(), f.getDoctorId(), f.getAction(),
                        f.getModifiedValue(), f.getRejectReason(), f.getCreatedAt()))
                .toList();

        return new DiagnosesApiDtos.Response.DiagnosisSessionResponse(
                s.getId(), s.getPatientId(), s.getEncounterId(), s.getDoctorId(),
                s.getTriggeredBy(), s.getModelRegistryId(), s.getStatus(),
                s.getStartedAt(), s.getCompletedAt(), results, recommendations, feedbacks);
    }

    private DiagnosesApiDtos.Response.ModelRegistryResponse toModelRegistryResponse(ModelRegistry m) {
        return new DiagnosesApiDtos.Response.ModelRegistryResponse(
                m.getId(), m.getModelCode(), m.getModelName(), m.getModelType(),
                m.getModelVersion(), m.getProvider(), m.getStatus(),
                m.getReleasedAt(), m.getCreatedAt());
    }
}
