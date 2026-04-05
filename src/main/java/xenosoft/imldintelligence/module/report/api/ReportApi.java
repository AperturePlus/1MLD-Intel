package xenosoft.imldintelligence.module.report.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.report.api.dto.ReportApiDtos;

/**
 * 报告模块 HTTP 契约。
 *
 * <p>该接口强调报告工作流，而不是底层版本表实现细节。</p>
 */
@Validated
@RequestMapping({"/api/v1/reports", "/api/v1/app/reports", "/api/v1/web/reports"})
public interface ReportApi {

    /**
     * 分页查询报告。
     */
    @GetMapping
    ApiResponse<PagedResultResponse<ReportApiDtos.Response.ReportDetailResponse>> listReports(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ReportApiDtos.Query.ReportPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 查询单个报告详情。
     */
    @GetMapping("/{reportId}")
    ApiResponse<ReportApiDtos.Response.ReportDetailResponse> getReport(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @PathVariable("reportId")
            @Positive(message = "reportId must be positive")
            Long reportId
    );

    /**
     * 创建报告草稿。
     */
    @PostMapping
    ApiResponse<ReportApiDtos.Response.ReportDetailResponse> createReportDraft(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ReportApiDtos.Request.CreateReportDraftRequest request
    );

    /**
     * 保存报告版本。
     */
    @PostMapping("/{reportId}/versions")
    ApiResponse<ReportApiDtos.Response.ReportDetailResponse> saveReportVersion(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @PathVariable("reportId")
            @Positive(message = "reportId must be positive")
            Long reportId,
            @Valid @RequestBody ReportApiDtos.Request.SaveReportVersionRequest request
    );

    /**
     * 签署报告。
     */
    @PostMapping("/{reportId}/signatures")
    ApiResponse<ReportApiDtos.Response.ReportDetailResponse> signReport(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @PathVariable("reportId")
            @Positive(message = "reportId must be positive")
            Long reportId,
            @Valid @RequestBody ReportApiDtos.Request.SignReportRequest request
    );

    /**
     * 分页查询报告模板。
     */
    @GetMapping("/templates")
    ApiResponse<PagedResultResponse<ReportApiDtos.Response.ReportTemplateResponse>> listTemplates(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ReportApiDtos.Query.ReportTemplatePageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 发布报告模板。
     */
    @PostMapping("/templates")
    ApiResponse<ReportApiDtos.Response.ReportTemplateResponse> publishTemplate(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ReportApiDtos.Request.PublishTemplateRequest request
    );
}
