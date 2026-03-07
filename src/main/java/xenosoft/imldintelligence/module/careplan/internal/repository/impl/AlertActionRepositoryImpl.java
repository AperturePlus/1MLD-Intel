package xenosoft.imldintelligence.module.careplan.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.careplan.internal.repository.AlertActionRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.mybatis.AlertActionMapper;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertAction;

import java.util.List;
import java.util.Optional;

/**
 * 预警动作仓储实现类，基于 MyBatis Mapper 完成预警动作的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class AlertActionRepositoryImpl implements AlertActionRepository {
    private final AlertActionMapper alertActionMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AlertAction> findById(Long tenantId, Long id) {
        return Optional.ofNullable(alertActionMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlertAction> listByTenantId(Long tenantId) {
        return alertActionMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AlertAction> listByAlertId(Long tenantId, Long alertId) {
        return alertActionMapper.listByAlertId(tenantId, alertId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlertAction save(AlertAction alertAction) {
        alertActionMapper.insert(alertAction);
        return alertAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AlertAction update(AlertAction alertAction) {
        alertActionMapper.update(alertAction);
        return alertAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return alertActionMapper.deleteById(tenantId, id) > 0;
    }
}
