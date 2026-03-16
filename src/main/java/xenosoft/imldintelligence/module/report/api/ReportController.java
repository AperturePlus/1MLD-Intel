package xenosoft.imldintelligence.module.report.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import xenosoft.imldintelligence.common.CheckPermission;
import xenosoft.imldintelligence.common.RequireAnyRole;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.report.api.dto.ReportApiDtos;
import xenosoft.imldintelligence.module.report.internal.model.Report;
import xenosoft.imldintelligence.module.report.internal.model.ReportTemplate;
import xenosoft.imldintelligence.module.report.internal.repository.ReportVersionRepository;
import xenosoft.imldintelligence.module.report.internal.service.ReportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportControllerContract {

    private final ReportService reportService;
    private final ReportVersionRepository reportVersionRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Override
    @CheckPermission(resource = "REPORT", action = "READ")
    public ApiResponse<PagedResultResponse<ReportApiDtos.Response.ReportDetailResponse>> listReports(
            Long tenantId,
            ReportApiDtos.Query.ReportPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = reportService.countReports(tenantId, query);
        List<Report> items = reportService.listReports(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(r -> toReportDetailResponse(tenantId, r)).toList()));
    }

    @Override
    @CheckPermission(resource = "REPORT", action = "READ")
    public ApiResponse<ReportApiDtos.Response.ReportDetailResponse> getReport(Long tenantId, Long reportId) {
        Report report = reportService.getReport(tenantId, reportId);
        return ApiResponse.success(toReportDetailResponse(tenantId, report));
    }

    @Override
    @CheckPermission(resource = "REPORT", action = "CREATE")
    public ApiResponse<ReportApiDtos.Response.ReportDetailResponse> createReportDraft(
            Long tenantId,
            ReportApiDtos.Request.CreateReportDraftRequest request) {
        Report report = reportService.createReportDraft(tenantId, request);
        return ApiResponse.success(toReportDetailResponse(tenantId, report));
    }

    @Override
    @CheckPermission(resource = "REPORT", action = "UPDATE")
    public ApiResponse<ReportApiDtos.Response.ReportDetailResponse> saveReportVersion(
            Long tenantId,
            Long reportId,
            ReportApiDtos.Request.SaveReportVersionRequest request) {
        Report report = reportService.saveReportVersion(tenantId, reportId, request);
        return ApiResponse.success(toReportDetailResponse(tenantId, report));
    }

    @Override
    @CheckPermission(resource = "REPORT", action = "UPDATE")
    public ApiResponse<ReportApiDtos.Response.ReportDetailResponse> signReport(
            Long tenantId,
            Long reportId,
            ReportApiDtos.Request.SignReportRequest request) {
        Report report = reportService.signReport(tenantId, reportId, request);
        return ApiResponse.success(toReportDetailResponse(tenantId, report));
    }

    @Override
    @CheckPermission(resource = "REPORT", action = "READ")
    public ApiResponse<PagedResultResponse<ReportApiDtos.Response.ReportTemplateResponse>> listTemplates(
            Long tenantId,
            ReportApiDtos.Query.ReportTemplatePageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = reportService.countTemplates(tenantId, query);
        List<ReportTemplate> items = reportService.listTemplates(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toTemplateResponse).toList()));
    }

    @Override
    @RequireAnyRole({"SYSTEM_ADMIN", "DATA_ADMIN"})
    public ApiResponse<ReportApiDtos.Response.ReportTemplateResponse> publishTemplate(
            Long tenantId,
            ReportApiDtos.Request.PublishTemplateRequest request) {
        ReportTemplate template = reportService.publishTemplate(tenantId, request);
        return ApiResponse.success(toTemplateResponse(template));
    }

    private ReportApiDtos.Response.ReportDetailResponse toReportDetailResponse(Long tenantId, Report r) {
        List<ReportApiDtos.Shared.ReportVersionItem> versions = reportVersionRepository
                .listByReportId(tenantId, r.getId()).stream()
                .map(v -> new ReportApiDtos.Shared.ReportVersionItem(
                        v.getId(), v.getVersionNum(), v.getContentSnapshot(),
                        v.getChangeSummary(), v.getChangedBy(), v.getCreatedAt()))
                .toList();
        return new ReportApiDtos.Response.ReportDetailResponse(
                r.getId(), r.getPatientId(), r.getEncounterId(), r.getSessionId(),
                r.getTemplateId(), r.getReportNo(), r.getStatus(), r.getCurrentVersion(),
                r.getSignedBy(), r.getSignedAt(), r.getCreatedBy(), r.getCreatedAt(),
                r.getUpdatedAt(), versions);
    }

    private ReportApiDtos.Response.ReportTemplateResponse toTemplateResponse(ReportTemplate t) {
        return new ReportApiDtos.Response.ReportTemplateResponse(
                t.getId(), t.getTemplateCode(), t.getTemplateName(), t.getDiseaseCode(),
                t.getDepartment(), t.getTemplateSchema(), t.getStatus(), t.getVersionNo(),
                t.getCreatedBy(), t.getCreatedAt());
    }
}
