package xenosoft.imldintelligence.module.audit.internal.service;

import xenosoft.imldintelligence.module.audit.internal.repository.query.AuditLogQuery;
import xenosoft.imldintelligence.module.audit.internal.repository.query.ModelInvocationLogQuery;
import xenosoft.imldintelligence.module.audit.internal.repository.query.SensitiveDataAccessLogQuery;
import xenosoft.imldintelligence.module.audit.internal.service.model.PageResult;
import xenosoft.imldintelligence.common.model.AuditLog;
import xenosoft.imldintelligence.common.model.ModelInvocationLog;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

/**
 * Provides paged read access to audit trail data.
 */
public interface AuditQueryService {
    /**
     * Queries business audit logs with tenant-scoped filters.
     *
     * @param query filter criteria, including tenant and optional business attributes
     * @param page zero-based page index
     * @param size page size to fetch
     * @return paged audit log records
     */
    PageResult<AuditLog> queryAuditLogs(AuditLogQuery query, int page, int size);

    /**
     * Queries sensitive-data access logs with tenant-scoped filters.
     *
     * @param query filter criteria, including tenant and optional access attributes
     * @param page zero-based page index
     * @param size page size to fetch
     * @return paged sensitive access log records
     */
    PageResult<SensitiveDataAccessLog> querySensitiveAccessLogs(SensitiveDataAccessLogQuery query, int page, int size);

    /**
     * Queries model invocation audit logs with tenant-scoped filters.
     *
     * @param query filter criteria, including tenant and optional model invocation attributes
     * @param page zero-based page index
     * @param size page size to fetch
     * @return paged model invocation log records
     */
    PageResult<ModelInvocationLog> queryModelInvocationLogs(ModelInvocationLogQuery query, int page, int size);
}
