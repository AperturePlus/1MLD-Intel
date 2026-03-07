package xenosoft.imldintelligence.module.payment.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.payment.internal.model.VipPlan;
import xenosoft.imldintelligence.module.payment.internal.repository.VipPlanRepository;
import xenosoft.imldintelligence.module.payment.internal.repository.mybatis.VipPlanMapper;

import java.util.List;
import java.util.Optional;

/**
 * VIP套餐仓储实现类，基于 MyBatis Mapper 完成VIP套餐的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class VipPlanRepositoryImpl implements VipPlanRepository {
    private final VipPlanMapper vipPlanMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<VipPlan> findById(Long tenantId, Long id) {
        return Optional.ofNullable(vipPlanMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<VipPlan> findByPlanCode(Long tenantId, String planCode) {
        return Optional.ofNullable(vipPlanMapper.findByPlanCode(tenantId, planCode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VipPlan> listByTenantId(Long tenantId) {
        return vipPlanMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VipPlan save(VipPlan vipPlan) {
        vipPlanMapper.insert(vipPlan);
        return vipPlan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VipPlan update(VipPlan vipPlan) {
        vipPlanMapper.update(vipPlan);
        return vipPlan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return vipPlanMapper.deleteById(tenantId, id) > 0;
    }
}
