package xenosoft.imldintelligence.module.clinical.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.clinical.api.dto.ClinicalApiDtos;

/**
 * 临床数据模块 HTTP 契约。
 *
 * <p>接口围绕证据采集与标准化设计，不暴露仓储模型，也不在边界层扩散解析实现细节。</p>
 */
@Validated
@RequestMapping({"/api/v1/clinical", "/api/v1/app/clinical", "/api/v1/web/clinical"})
public interface ClinicalApi {

    /**
     * 分页查询检验结果。
     */
    @GetMapping("/lab-results")
    ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.LabResultResponse>> listLabResults(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ClinicalApiDtos.Query.LabResultPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 新增或修正检验结果。
     */
    @PostMapping("/lab-results")
    ApiResponse<ClinicalApiDtos.Response.LabResultResponse> upsertLabResult(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ClinicalApiDtos.Request.UpsertLabResultRequest request
    );

    /**
     * 分页查询基因报告。
     */
    @GetMapping("/genetic-reports")
    ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.GeneticReportResponse>> listGeneticReports(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ClinicalApiDtos.Query.GeneticReportPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 登记基因报告。
     */
    @PostMapping("/genetic-reports")
    ApiResponse<ClinicalApiDtos.Response.GeneticReportResponse> registerGeneticReport(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ClinicalApiDtos.Request.RegisterGeneticReportRequest request
    );

    /**
     * 分页查询影像报告。
     */
    @GetMapping("/imaging-reports")
    ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.ImagingReportResponse>> listImagingReports(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ClinicalApiDtos.Query.ImagingReportPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 登记影像报告。
     */
    @PostMapping("/imaging-reports")
    ApiResponse<ClinicalApiDtos.Response.ImagingReportResponse> upsertImagingReport(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ClinicalApiDtos.Request.UpsertImagingReportRequest request
    );

    /**
     * 分页查询病史条目。
     */
    @GetMapping("/history-entries")
    ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.ClinicalHistoryEntryResponse>> listClinicalHistoryEntries(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ClinicalApiDtos.Query.ClinicalHistoryPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 记录病史条目。
     */
    @PostMapping("/history-entries")
    ApiResponse<ClinicalApiDtos.Response.ClinicalHistoryEntryResponse> recordClinicalHistory(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ClinicalApiDtos.Request.RecordClinicalHistoryRequest request
    );

    /**
     * 配置指标映射。
     */
    @PostMapping("/indicator-mappings")
    ApiResponse<ClinicalApiDtos.Response.IndicatorMappingResponse> upsertIndicatorMapping(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ClinicalApiDtos.Request.UpsertIndicatorMappingRequest request
    );
}
