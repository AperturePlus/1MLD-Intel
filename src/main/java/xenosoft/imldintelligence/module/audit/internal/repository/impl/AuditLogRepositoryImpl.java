package xenosoft.imldintelligence.module.audit.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.audit.internal.repository.AuditLogRepository;
import xenosoft.imldintelligence.module.audit.internal.repository.mybatis.AuditLogMapper;
import xenosoft.imldintelligence.module.audit.internal.repository.query.AuditLogQuery;
import xenosoft.imldintelligence.common.model.AuditLog;

import java.util.List;

/**
 * 审计日志仓储实现类，基于 MyBatis Mapper 完成审计日志的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class AuditLogRepositoryImpl implements AuditLogRepository {
    private final AuditLogMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public AuditLog save(AuditLog auditLog) {
        mapper.insert(auditLog);
        return auditLog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuditLog> query(AuditLogQuery query, int offset, int limit) {
        return mapper.query(query, offset, limit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(AuditLogQuery query) {
        return mapper.count(query);
    }
}
