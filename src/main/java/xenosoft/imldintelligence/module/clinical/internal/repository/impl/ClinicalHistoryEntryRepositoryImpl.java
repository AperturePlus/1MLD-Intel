package xenosoft.imldintelligence.module.clinical.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.clinical.internal.model.ClinicalHistoryEntry;
import xenosoft.imldintelligence.module.clinical.internal.repository.ClinicalHistoryEntryRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.mybatis.ClinicalHistoryEntryMapper;

import java.util.List;
import java.util.Optional;

/**
 * 临床病史条目仓储实现类，基于 MyBatis Mapper 完成临床病史条目的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class ClinicalHistoryEntryRepositoryImpl implements ClinicalHistoryEntryRepository {
    private final ClinicalHistoryEntryMapper clinicalHistoryEntryMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ClinicalHistoryEntry> findById(Long tenantId, Long id) {
        return Optional.ofNullable(clinicalHistoryEntryMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClinicalHistoryEntry> listByTenantId(Long tenantId) {
        return clinicalHistoryEntryMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClinicalHistoryEntry> listByPatientId(Long tenantId, Long patientId) {
        return clinicalHistoryEntryMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ClinicalHistoryEntry> listByEncounterId(Long tenantId, Long encounterId) {
        return clinicalHistoryEntryMapper.listByEncounterId(tenantId, encounterId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClinicalHistoryEntry save(ClinicalHistoryEntry clinicalHistoryEntry) {
        clinicalHistoryEntryMapper.insert(clinicalHistoryEntry);
        return clinicalHistoryEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClinicalHistoryEntry update(ClinicalHistoryEntry clinicalHistoryEntry) {
        clinicalHistoryEntryMapper.update(clinicalHistoryEntry);
        return clinicalHistoryEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return clinicalHistoryEntryMapper.deleteById(tenantId, id) > 0;
    }
}
