package xenosoft.imldintelligence.module.clinical.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticVariant;
import xenosoft.imldintelligence.module.clinical.internal.repository.GeneticVariantRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.mybatis.GeneticVariantMapper;

import java.util.List;
import java.util.Optional;

/**
 * 基因变异仓储实现类，基于 MyBatis Mapper 完成基因变异的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class GeneticVariantRepositoryImpl implements GeneticVariantRepository {
    private final GeneticVariantMapper geneticVariantMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GeneticVariant> findById(Long tenantId, Long id) {
        return Optional.ofNullable(geneticVariantMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeneticVariant> listByTenantId(Long tenantId) {
        return geneticVariantMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeneticVariant> listByReportId(Long tenantId, Long reportId) {
        return geneticVariantMapper.listByReportId(tenantId, reportId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeneticVariant save(GeneticVariant geneticVariant) {
        geneticVariantMapper.insert(geneticVariant);
        return geneticVariant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeneticVariant update(GeneticVariant geneticVariant) {
        geneticVariantMapper.update(geneticVariant);
        return geneticVariant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return geneticVariantMapper.deleteById(tenantId, id) > 0;
    }
}
