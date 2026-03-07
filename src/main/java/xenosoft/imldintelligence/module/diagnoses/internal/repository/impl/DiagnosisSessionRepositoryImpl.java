package xenosoft.imldintelligence.module.diagnoses.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisSessionRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis.DiagnosisSessionMapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;

import java.util.List;
import java.util.Optional;

/**
 * 诊断会话仓储实现类，基于 MyBatis Mapper 完成诊断会话的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class DiagnosisSessionRepositoryImpl implements DiagnosisSessionRepository {
    private final DiagnosisSessionMapper diagnosisSessionMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DiagnosisSession> findById(Long tenantId, Long id) {
        return Optional.ofNullable(diagnosisSessionMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisSession> listByTenantId(Long tenantId) {
        return diagnosisSessionMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisSession> listByPatientId(Long tenantId, Long patientId) {
        return diagnosisSessionMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisSession> listByEncounterId(Long tenantId, Long encounterId) {
        return diagnosisSessionMapper.listByEncounterId(tenantId, encounterId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiagnosisSession save(DiagnosisSession diagnosisSession) {
        diagnosisSessionMapper.insert(diagnosisSession);
        return diagnosisSession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiagnosisSession update(DiagnosisSession diagnosisSession) {
        diagnosisSessionMapper.update(diagnosisSession);
        return diagnosisSession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return diagnosisSessionMapper.deleteById(tenantId, id) > 0;
    }
}
