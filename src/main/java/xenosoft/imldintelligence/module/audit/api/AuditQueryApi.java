package xenosoft.imldintelligence.module.audit.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.module.audit.api.dto.AuditLogResponse;
import xenosoft.imldintelligence.module.audit.api.dto.AuditQueryDtos;
import xenosoft.imldintelligence.module.audit.api.dto.ModelInvocationLogResponse;
import xenosoft.imldintelligence.module.audit.api.dto.PagedResponse;
import xenosoft.imldintelligence.module.audit.api.dto.SensitiveAccessLogResponse;

/**
 * 审计查询 HTTP 契约。
 *
 * <p>该接口只定义模块边界与参数语义，不提供额外实现；
 * 现有 {@link AuditQueryController} 可继续按既有方式演进。</p>
 */
@Validated
@RequestMapping({"/api/v1/audit", "/api/v1/app/audit", "/api/v1/web/audit"})
public interface AuditQueryApi {

    /**
     * 查询审计日志。
     */
    @GetMapping("/logs")
    PagedResponse<AuditLogResponse> queryAuditLogs(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute AuditQueryDtos.Query.AuditLogPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 查询敏感字段访问日志。
     */
    @GetMapping("/sensitive-access-logs")
    PagedResponse<SensitiveAccessLogResponse> querySensitiveAccessLogs(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute AuditQueryDtos.Query.SensitiveAccessLogPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 查询模型调用日志。
     */
    @GetMapping("/model-invocation-logs")
    PagedResponse<ModelInvocationLogResponse> queryModelInvocationLogs(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute AuditQueryDtos.Query.ModelInvocationLogPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );
}
