package xenosoft.imldintelligence.module.clinical.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticReport;
import xenosoft.imldintelligence.module.clinical.internal.repository.GeneticReportRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.mybatis.GeneticReportMapper;

import java.util.List;
import java.util.Optional;

/**
 * 基因报告仓储实现类，基于 MyBatis Mapper 完成基因报告的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class GeneticReportRepositoryImpl implements GeneticReportRepository {
    private final GeneticReportMapper geneticReportMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GeneticReport> findById(Long tenantId, Long id) {
        return Optional.ofNullable(geneticReportMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeneticReport> listByTenantId(Long tenantId) {
        return geneticReportMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeneticReport> listByPatientId(Long tenantId, Long patientId) {
        return geneticReportMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GeneticReport> listByEncounterId(Long tenantId, Long encounterId) {
        return geneticReportMapper.listByEncounterId(tenantId, encounterId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeneticReport save(GeneticReport geneticReport) {
        geneticReportMapper.insert(geneticReport);
        return geneticReport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeneticReport update(GeneticReport geneticReport) {
        geneticReportMapper.update(geneticReport);
        return geneticReport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return geneticReportMapper.deleteById(tenantId, id) > 0;
    }
}
