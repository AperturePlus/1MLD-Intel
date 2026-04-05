package xenosoft.imldintelligence.module.integration.api;

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
import xenosoft.imldintelligence.module.integration.api.dto.IntegrationApiDtos;

/**
 * 集成模块 HTTP 契约。
 *
 * <p>接口按任务编排和重试补偿设计，默认坚持最小出域与摘要回显策略。</p>
 */
@Validated
@RequestMapping({"/api/v1/integration", "/api/v1/app/integration", "/api/v1/web/integration"})
public interface IntegrationApi {

    /**
     * 分页查询集成任务。
     */
    @GetMapping("/jobs")
    ApiResponse<PagedResultResponse<IntegrationApiDtos.Response.IntegrationJobResponse>> listJobs(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute IntegrationApiDtos.Query.IntegrationJobPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 查询单个集成任务。
     */
    @GetMapping("/jobs/{jobId}")
    ApiResponse<IntegrationApiDtos.Response.IntegrationJobResponse> getJob(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @PathVariable("jobId")
            @Positive(message = "jobId must be positive")
            Long jobId
    );

    /**
     * 创建集成任务。
     */
    @PostMapping("/jobs")
    ApiResponse<IntegrationApiDtos.Response.IntegrationJobResponse> createJob(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody IntegrationApiDtos.Request.CreateIntegrationJobRequest request
    );

    /**
     * 重试集成任务。
     */
    @PostMapping("/jobs/retries")
    ApiResponse<IntegrationApiDtos.Response.IntegrationJobResponse> retryJob(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody IntegrationApiDtos.Request.RetryIntegrationJobRequest request
    );
}
