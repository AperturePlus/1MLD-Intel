package xenosoft.imldintelligence.module.audit.internal.context;

import lombok.Data;

/**
 * 审计上下文对象，保存当前请求关联的租户、用户与链路信息。
 */
@Data
public class AuditContext {
    private Long tenantId;
    private Long userId;
    private String userRole;
    private String traceId;
    private String ipAddress;
    private String deviceInfo;
    private String appVersion;
}
