package xenosoft.imldintelligence.module.diagnoses.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisResultRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis.DiagnosisResultMapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisResult;

import java.util.List;
import java.util.Optional;

/**
 * 诊断结果仓储实现类，基于 MyBatis Mapper 完成诊断结果的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class DiagnosisResultRepositoryImpl implements DiagnosisResultRepository {
    private final DiagnosisResultMapper diagnosisResultMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DiagnosisResult> findById(Long tenantId, Long id) {
        return Optional.ofNullable(diagnosisResultMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisResult> listByTenantId(Long tenantId) {
        return diagnosisResultMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisResult> listBySessionId(Long tenantId, Long sessionId) {
        return diagnosisResultMapper.listBySessionId(tenantId, sessionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiagnosisResult save(DiagnosisResult diagnosisResult) {
        diagnosisResultMapper.insert(diagnosisResult);
        return diagnosisResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiagnosisResult update(DiagnosisResult diagnosisResult) {
        diagnosisResultMapper.update(diagnosisResult);
        return diagnosisResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return diagnosisResultMapper.deleteById(tenantId, id) > 0;
    }
}
