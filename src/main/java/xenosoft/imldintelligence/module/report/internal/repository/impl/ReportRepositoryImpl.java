package xenosoft.imldintelligence.module.report.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.report.internal.repository.ReportRepository;
import xenosoft.imldintelligence.module.report.internal.repository.mybatis.ReportMapper;
import xenosoft.imldintelligence.module.report.internal.model.Report;

import java.util.List;
import java.util.Optional;

/**
 * 报告仓储实现类，基于 MyBatis Mapper 完成报告的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    private final ReportMapper reportMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Report> findById(Long tenantId, Long id) {
        return Optional.ofNullable(reportMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Report> findByReportNo(Long tenantId, String reportNo) {
        return Optional.ofNullable(reportMapper.findByReportNo(tenantId, reportNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Report> listByTenantId(Long tenantId) {
        return reportMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Report> listByPatientId(Long tenantId, Long patientId) {
        return reportMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Report> listBySessionId(Long tenantId, Long sessionId) {
        return reportMapper.listBySessionId(tenantId, sessionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Report save(Report report) {
        reportMapper.insert(report);
        return report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Report update(Report report) {
        reportMapper.update(report);
        return report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return reportMapper.deleteById(tenantId, id) > 0;
    }
}
