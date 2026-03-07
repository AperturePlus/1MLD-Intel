package xenosoft.imldintelligence.module.audit.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.audit.internal.repository.ModelInvocationLogRepository;
import xenosoft.imldintelligence.module.audit.internal.repository.mybatis.ModelInvocationLogMapper;
import xenosoft.imldintelligence.module.audit.internal.repository.query.ModelInvocationLogQuery;
import xenosoft.imldintelligence.common.model.ModelInvocationLog;

import java.util.List;

/**
 * 模型调用日志仓储实现类，基于 MyBatis Mapper 完成模型调用日志的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class ModelInvocationLogRepositoryImpl implements ModelInvocationLogRepository {
    private final ModelInvocationLogMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelInvocationLog save(ModelInvocationLog log) {
        mapper.insert(log);
        return log;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModelInvocationLog> query(ModelInvocationLogQuery query, int offset, int limit) {
        return mapper.query(query, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(ModelInvocationLogQuery query) {
        return mapper.count(query);
    }
}
