package xenosoft.imldintelligence.module.clinical.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.clinical.internal.model.IndicatorMapping;
import xenosoft.imldintelligence.module.clinical.internal.repository.IndicatorMappingRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.mybatis.IndicatorMappingMapper;

import java.util.List;
import java.util.Optional;

/**
 * 指标映射仓储实现类，基于 MyBatis Mapper 完成指标映射的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class IndicatorMappingRepositoryImpl implements IndicatorMappingRepository {
    private final IndicatorMappingMapper indicatorMappingMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IndicatorMapping> findById(Long tenantId, Long id) {
        return Optional.ofNullable(indicatorMappingMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IndicatorMapping> findBySourceSystemAndSourceCode(Long tenantId, String sourceSystem, String sourceCode) {
        return Optional.ofNullable(indicatorMappingMapper.findBySourceSystemAndSourceCode(tenantId, sourceSystem, sourceCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IndicatorMapping> listByTenantId(Long tenantId) {
        return indicatorMappingMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IndicatorMapping> listByTargetIndicatorCode(Long tenantId, String targetIndicatorCode) {
        return indicatorMappingMapper.listByTargetIndicatorCode(tenantId, targetIndicatorCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorMapping save(IndicatorMapping indicatorMapping) {
        indicatorMappingMapper.insert(indicatorMapping);
        return indicatorMapping;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorMapping update(IndicatorMapping indicatorMapping) {
        indicatorMappingMapper.update(indicatorMapping);
        return indicatorMapping;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return indicatorMappingMapper.deleteById(tenantId, id) > 0;
    }
}
