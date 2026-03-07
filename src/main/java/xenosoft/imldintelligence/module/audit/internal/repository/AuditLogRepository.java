package xenosoft.imldintelligence.module.audit.internal.repository;

import xenosoft.imldintelligence.module.audit.internal.repository.query.AuditLogQuery;
import xenosoft.imldintelligence.common.model.AuditLog;

import java.util.List;

/**
 * AuditLogRepository 接口定义。
 */
public interface AuditLogRepository {
    /**
     * 保存业务审计日志。
     *
     * @param auditLog 待保存的审计日志对象
     * @return 保存后的审计日志对象
     */
    AuditLog save(AuditLog auditLog);

    /**
     * 按条件分页查询业务审计日志。
     *
     * @param query 查询条件
     * @param offset 分页起始偏移量
     * @param limit 单页返回条数
     * @return 符合条件的审计日志列表
     */
    List<AuditLog> query(AuditLogQuery query, int offset, int limit);

    /**
     * 统计符合条件的业务审计日志总数。
     *
     * @param query 查询条件
     * @return 符合条件的日志总数
     */
    long count(AuditLogQuery query);
}
