package xenosoft.imldintelligence.module.audit.internal.repository.query;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * AuditLogQuery 查询对象，封装筛选条件。
 */
@Data
public class AuditLogQuery {
    private Long tenantId;
    private Long userId;
    private String action;
    private String resourceType;
    private String resourceId;
    private String traceId;
    private OffsetDateTime from;
    private OffsetDateTime to;
}
