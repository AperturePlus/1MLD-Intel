package xenosoft.imldintelligence.module.audit.internal.repository;

import xenosoft.imldintelligence.module.audit.internal.repository.query.SensitiveDataAccessLogQuery;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

import java.util.List;

/**
 * SensitiveDataAccessLogRepository 接口定义。
 */
public interface SensitiveDataAccessLogRepository {
    /**
     * 保存敏感数据访问日志。
     *
     * @param log 待保存的敏感访问日志对象
     * @return 保存后的敏感访问日志对象
     */
    SensitiveDataAccessLog save(SensitiveDataAccessLog log);

    /**
     * 按条件分页查询敏感数据访问日志。
     *
     * @param query 查询条件
     * @param offset 分页起始偏移量
     * @param limit 单页返回条数
     * @return 符合条件的敏感访问日志列表
     */
    List<SensitiveDataAccessLog> query(SensitiveDataAccessLogQuery query, int offset, int limit);

    /**
     * 统计符合条件的敏感访问日志总数。
     *
     * @param query 查询条件
     * @return 符合条件的日志总数
     */
    long count(SensitiveDataAccessLogQuery query);
}
