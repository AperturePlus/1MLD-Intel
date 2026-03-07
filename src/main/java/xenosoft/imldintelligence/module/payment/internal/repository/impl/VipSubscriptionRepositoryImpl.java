package xenosoft.imldintelligence.module.payment.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.payment.internal.model.VipSubscription;
import xenosoft.imldintelligence.module.payment.internal.repository.VipSubscriptionRepository;
import xenosoft.imldintelligence.module.payment.internal.repository.mybatis.VipSubscriptionMapper;

import java.util.List;
import java.util.Optional;

/**
 * VIP订阅仓储实现类，基于 MyBatis Mapper 完成VIP订阅的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class VipSubscriptionRepositoryImpl implements VipSubscriptionRepository {
    private final VipSubscriptionMapper vipSubscriptionMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<VipSubscription> findById(Long tenantId, Long id) {
        return Optional.ofNullable(vipSubscriptionMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VipSubscription> listByTenantId(Long tenantId) {
        return vipSubscriptionMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VipSubscription> listByTocUserId(Long tenantId, Long tocUserId) {
        return vipSubscriptionMapper.listByTocUserId(tenantId, tocUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VipSubscription> listByOrderId(Long tenantId, Long orderId) {
        return vipSubscriptionMapper.listByOrderId(tenantId, orderId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VipSubscription save(VipSubscription vipSubscription) {
        vipSubscriptionMapper.insert(vipSubscription);
        return vipSubscription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VipSubscription update(VipSubscription vipSubscription) {
        vipSubscriptionMapper.update(vipSubscription);
        return vipSubscription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return vipSubscriptionMapper.deleteById(tenantId, id) > 0;
    }
}
