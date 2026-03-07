package xenosoft.imldintelligence.module.careplan.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.careplan.internal.repository.FollowupTaskRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.mybatis.FollowupTaskMapper;
import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;

import java.util.List;
import java.util.Optional;

/**
 * 随访任务仓储实现类，基于 MyBatis Mapper 完成随访任务的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class FollowupTaskRepositoryImpl implements FollowupTaskRepository {
    private final FollowupTaskMapper followupTaskMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<FollowupTask> findById(Long tenantId, Long id) {
        return Optional.ofNullable(followupTaskMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FollowupTask> listByTenantId(Long tenantId) {
        return followupTaskMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FollowupTask> listByCarePlanId(Long tenantId, Long carePlanId) {
        return followupTaskMapper.listByCarePlanId(tenantId, carePlanId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FollowupTask> listByPatientId(Long tenantId, Long patientId) {
        return followupTaskMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FollowupTask save(FollowupTask followupTask) {
        followupTaskMapper.insert(followupTask);
        return followupTask;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FollowupTask update(FollowupTask followupTask) {
        followupTaskMapper.update(followupTask);
        return followupTask;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return followupTaskMapper.deleteById(tenantId, id) > 0;
    }
}
