package xenosoft.imldintelligence.module.payment.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.payment.internal.model.VipOrder;
import xenosoft.imldintelligence.module.payment.internal.repository.VipOrderRepository;
import xenosoft.imldintelligence.module.payment.internal.repository.mybatis.VipOrderMapper;

import java.util.List;
import java.util.Optional;

/**
 * VIP订单仓储实现类，基于 MyBatis Mapper 完成VIP订单的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class VipOrderRepositoryImpl implements VipOrderRepository {
    private final VipOrderMapper vipOrderMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<VipOrder> findById(Long tenantId, Long id) {
        return Optional.ofNullable(vipOrderMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<VipOrder> findByOrderNo(Long tenantId, String orderNo) {
        return Optional.ofNullable(vipOrderMapper.findByOrderNo(tenantId, orderNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VipOrder> listByTenantId(Long tenantId) {
        return vipOrderMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VipOrder> listByTocUserId(Long tenantId, Long tocUserId) {
        return vipOrderMapper.listByTocUserId(tenantId, tocUserId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VipOrder save(VipOrder vipOrder) {
        vipOrderMapper.insert(vipOrder);
        return vipOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VipOrder update(VipOrder vipOrder) {
        vipOrderMapper.update(vipOrder);
        return vipOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return vipOrderMapper.deleteById(tenantId, id) > 0;
    }
}
