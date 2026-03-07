package xenosoft.imldintelligence.module.audit.internal.repository;

import xenosoft.imldintelligence.module.audit.internal.repository.query.ModelInvocationLogQuery;
import xenosoft.imldintelligence.common.model.ModelInvocationLog;

import java.util.List;

/**
 * ModelInvocationLogRepository 接口定义。
 */
public interface ModelInvocationLogRepository {
    /**
     * 保存模型调用日志。
     *
     * @param log 待保存的模型调用日志对象
     * @return 保存后的模型调用日志对象
     */
    ModelInvocationLog save(ModelInvocationLog log);

    /**
     * 按条件分页查询模型调用日志。
     *
     * @param query 查询条件
     * @param offset 分页起始偏移量
     * @param limit 单页返回条数
     * @return 符合条件的模型调用日志列表
     */
    List<ModelInvocationLog> query(ModelInvocationLogQuery query, int offset, int limit);

    /**
     * 统计符合条件的模型调用日志总数。
     *
     * @param query 查询条件
     * @return 符合条件的日志总数
     */
    long count(ModelInvocationLogQuery query);
}
