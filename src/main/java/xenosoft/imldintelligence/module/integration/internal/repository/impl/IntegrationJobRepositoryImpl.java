package xenosoft.imldintelligence.module.integration.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.integration.internal.model.IntegrationJob;
import xenosoft.imldintelligence.module.integration.internal.repository.IntegrationJobRepository;
import xenosoft.imldintelligence.module.integration.internal.repository.mybatis.IntegrationJobMapper;

import java.util.List;
import java.util.Optional;

/**
 * 集成任务仓储实现类，基于 MyBatis Mapper 完成集成任务的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class IntegrationJobRepositoryImpl implements IntegrationJobRepository {
    private final IntegrationJobMapper integrationJobMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IntegrationJob> findById(Long tenantId, Long id) {
        return Optional.ofNullable(integrationJobMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IntegrationJob> findByJobNo(Long tenantId, String jobNo) {
        return Optional.ofNullable(integrationJobMapper.findByJobNo(tenantId, jobNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IntegrationJob> listByTenantId(Long tenantId) {
        return integrationJobMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IntegrationJob> listBySourceSystem(Long tenantId, String sourceSystem) {
        return integrationJobMapper.listBySourceSystem(tenantId, sourceSystem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegrationJob save(IntegrationJob integrationJob) {
        integrationJobMapper.insert(integrationJob);
        return integrationJob;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegrationJob update(IntegrationJob integrationJob) {
        integrationJobMapper.update(integrationJob);
        return integrationJob;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return integrationJobMapper.deleteById(tenantId, id) > 0;
    }
}
