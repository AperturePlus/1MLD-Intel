package xenosoft.imldintelligence.module.diagnoses.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DiagnosisRecommendationRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis.DiagnosisRecommendationMapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisRecommendation;

import java.util.List;
import java.util.Optional;

/**
 * 诊断建议仓储实现类，基于 MyBatis Mapper 完成诊断建议的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class DiagnosisRecommendationRepositoryImpl implements DiagnosisRecommendationRepository {
    private final DiagnosisRecommendationMapper diagnosisRecommendationMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DiagnosisRecommendation> findById(Long tenantId, Long id) {
        return Optional.ofNullable(diagnosisRecommendationMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisRecommendation> listByTenantId(Long tenantId) {
        return diagnosisRecommendationMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DiagnosisRecommendation> listBySessionId(Long tenantId, Long sessionId) {
        return diagnosisRecommendationMapper.listBySessionId(tenantId, sessionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiagnosisRecommendation save(DiagnosisRecommendation diagnosisRecommendation) {
        diagnosisRecommendationMapper.insert(diagnosisRecommendation);
        return diagnosisRecommendation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DiagnosisRecommendation update(DiagnosisRecommendation diagnosisRecommendation) {
        diagnosisRecommendationMapper.update(diagnosisRecommendation);
        return diagnosisRecommendation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return diagnosisRecommendationMapper.deleteById(tenantId, id) > 0;
    }
}
