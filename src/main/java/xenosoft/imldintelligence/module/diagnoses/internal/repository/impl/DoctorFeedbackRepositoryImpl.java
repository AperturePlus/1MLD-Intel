package xenosoft.imldintelligence.module.diagnoses.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.DoctorFeedbackRepository;
import xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis.DoctorFeedbackMapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DoctorFeedback;

import java.util.List;
import java.util.Optional;

/**
 * 医生反馈仓储实现类，基于 MyBatis Mapper 完成医生反馈的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class DoctorFeedbackRepositoryImpl implements DoctorFeedbackRepository {
    private final DoctorFeedbackMapper doctorFeedbackMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DoctorFeedback> findById(Long tenantId, Long id) {
        return Optional.ofNullable(doctorFeedbackMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DoctorFeedback> listByTenantId(Long tenantId) {
        return doctorFeedbackMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DoctorFeedback> listBySessionId(Long tenantId, Long sessionId) {
        return doctorFeedbackMapper.listBySessionId(tenantId, sessionId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DoctorFeedback> listByResultId(Long tenantId, Long resultId) {
        return doctorFeedbackMapper.listByResultId(tenantId, resultId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorFeedback save(DoctorFeedback doctorFeedback) {
        doctorFeedbackMapper.insert(doctorFeedback);
        return doctorFeedback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoctorFeedback update(DoctorFeedback doctorFeedback) {
        doctorFeedbackMapper.update(doctorFeedback);
        return doctorFeedback;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return doctorFeedbackMapper.deleteById(tenantId, id) > 0;
    }
}
