package xenosoft.imldintelligence.module.screening.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.screening.internal.repository.TocClinicalTransferRepository;
import xenosoft.imldintelligence.module.screening.internal.repository.mybatis.TocClinicalTransferMapper;
import xenosoft.imldintelligence.module.screening.internal.model.TocClinicalTransfer;

import java.util.List;
import java.util.Optional;

/**
 * TOC临床转化仓储实现类，基于 MyBatis Mapper 完成TOC临床转化的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class TocClinicalTransferRepositoryImpl implements TocClinicalTransferRepository {
    private final TocClinicalTransferMapper tocClinicalTransferMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TocClinicalTransfer> findById(Long tenantId, Long id) {
        return Optional.ofNullable(tocClinicalTransferMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TocClinicalTransfer> listByTenantId(Long tenantId) {
        return tocClinicalTransferMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TocClinicalTransfer> listByResponseId(Long tenantId, Long responseId) {
        return tocClinicalTransferMapper.listByResponseId(tenantId, responseId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TocClinicalTransfer> listByPatientId(Long tenantId, Long patientId) {
        return tocClinicalTransferMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TocClinicalTransfer save(TocClinicalTransfer tocClinicalTransfer) {
        tocClinicalTransferMapper.insert(tocClinicalTransfer);
        return tocClinicalTransfer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TocClinicalTransfer update(TocClinicalTransfer tocClinicalTransfer) {
        tocClinicalTransferMapper.update(tocClinicalTransfer);
        return tocClinicalTransfer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return tocClinicalTransferMapper.deleteById(tenantId, id) > 0;
    }
}
