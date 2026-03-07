package xenosoft.imldintelligence.module.careplan.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.careplan.internal.repository.AlertEventRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.mybatis.AlertEventMapper;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertEvent;

import java.util.List;
import java.util.Optional;

/**
 * 预警事件仓储实现类，基于 MyBatis Mapper 完成预警事件的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class AlertEventRepositoryImpl implements AlertEventRepository {
    private final AlertEventMapper alertEventMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AlertEvent> findById(Long tenantId, Long id) {
        return Optional.ofNullable(alertEventMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlertEvent> listByTenantId(Long tenantId) {
        return alertEventMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlertEvent> listByCarePlanId(Long tenantId, Long carePlanId) {
        return alertEventMapper.listByCarePlanId(tenantId, carePlanId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlertEvent> listByPatientId(Long tenantId, Long patientId) {
        return alertEventMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlertEvent save(AlertEvent alertEvent) {
        alertEventMapper.insert(alertEvent);
        return alertEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlertEvent update(AlertEvent alertEvent) {
        alertEventMapper.update(alertEvent);
        return alertEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return alertEventMapper.deleteById(tenantId, id) > 0;
    }
}
