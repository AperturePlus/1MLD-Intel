package xenosoft.imldintelligence.module.report.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.report.internal.repository.ReportTemplateRepository;
import xenosoft.imldintelligence.module.report.internal.repository.mybatis.ReportTemplateMapper;
import xenosoft.imldintelligence.module.report.internal.model.ReportTemplate;

import java.util.List;
import java.util.Optional;

/**
 * 报告模板仓储实现类，基于 MyBatis Mapper 完成报告模板的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class ReportTemplateRepositoryImpl implements ReportTemplateRepository {
    private final ReportTemplateMapper reportTemplateMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ReportTemplate> findById(Long tenantId, Long id) {
        return Optional.ofNullable(reportTemplateMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ReportTemplate> findByTemplateCodeAndVersionNo(Long tenantId, String templateCode, Integer versionNo) {
        return Optional.ofNullable(reportTemplateMapper.findByTemplateCodeAndVersionNo(tenantId, templateCode, versionNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReportTemplate> listByTenantId(Long tenantId) {
        return reportTemplateMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReportTemplate save(ReportTemplate reportTemplate) {
        reportTemplateMapper.insert(reportTemplate);
        return reportTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReportTemplate update(ReportTemplate reportTemplate) {
        reportTemplateMapper.update(reportTemplate);
        return reportTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return reportTemplateMapper.deleteById(tenantId, id) > 0;
    }
}
