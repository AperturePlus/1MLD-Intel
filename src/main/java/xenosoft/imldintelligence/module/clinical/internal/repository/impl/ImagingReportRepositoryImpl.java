package xenosoft.imldintelligence.module.clinical.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.clinical.internal.model.ImagingReport;
import xenosoft.imldintelligence.module.clinical.internal.repository.ImagingReportRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.mybatis.ImagingReportMapper;

import java.util.List;
import java.util.Optional;

/**
 * 影像报告仓储实现类，基于 MyBatis Mapper 完成影像报告的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class ImagingReportRepositoryImpl implements ImagingReportRepository {
    private final ImagingReportMapper imagingReportMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ImagingReport> findById(Long tenantId, Long id) {
        return Optional.ofNullable(imagingReportMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImagingReport> listByTenantId(Long tenantId) {
        return imagingReportMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImagingReport> listByPatientId(Long tenantId, Long patientId) {
        return imagingReportMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImagingReport> listByEncounterId(Long tenantId, Long encounterId) {
        return imagingReportMapper.listByEncounterId(tenantId, encounterId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImagingReport save(ImagingReport imagingReport) {
        imagingReportMapper.insert(imagingReport);
        return imagingReport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImagingReport update(ImagingReport imagingReport) {
        imagingReportMapper.update(imagingReport);
        return imagingReport;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return imagingReportMapper.deleteById(tenantId, id) > 0;
    }
}
