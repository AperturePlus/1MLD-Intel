package xenosoft.imldintelligence.module.audit.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.audit.internal.repository.SensitiveDataAccessLogRepository;
import xenosoft.imldintelligence.module.audit.internal.repository.mybatis.SensitiveDataAccessLogMapper;
import xenosoft.imldintelligence.module.audit.internal.repository.query.SensitiveDataAccessLogQuery;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

import java.util.List;

/**
 * 敏感数据访问日志仓储实现类，基于 MyBatis Mapper 完成敏感数据访问日志的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class SensitiveDataAccessLogRepositoryImpl implements SensitiveDataAccessLogRepository {
    private final SensitiveDataAccessLogMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public SensitiveDataAccessLog save(SensitiveDataAccessLog log) {
        mapper.insert(log);
        return log;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SensitiveDataAccessLog> query(SensitiveDataAccessLogQuery query, int offset, int limit) {
        return mapper.query(query, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(SensitiveDataAccessLogQuery query) {
        return mapper.count(query);
    }
}
