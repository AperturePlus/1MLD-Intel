package xenosoft.imldintelligence.module.audit.internal.repository.query;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * ModelInvocationLogQuery 查询对象，封装筛选条件。
 */
@Data
public class ModelInvocationLogQuery {
    private Long tenantId;
    private Long sessionId;
    private Long modelRegistryId;
    private String provider;
    private String requestId;
    private String status;
    private OffsetDateTime from;
    private OffsetDateTime to;
}
