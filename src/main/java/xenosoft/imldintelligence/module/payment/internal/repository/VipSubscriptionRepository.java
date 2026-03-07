package xenosoft.imldintelligence.module.payment.internal.repository;

import xenosoft.imldintelligence.module.payment.internal.model.VipSubscription;

import java.util.List;
import java.util.Optional;

/**
 * VIP订阅仓储接口，负责在租户边界内持久化会员订阅数据。
 */
public interface VipSubscriptionRepository {
    /**
     * 按租户和VIP订阅主键查询VIP订阅。
     *
     * @param tenantId 租户标识
     * @param id VIP订阅主键
     * @return 匹配的VIP订阅，不存在时返回空
     */
    Optional<VipSubscription> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部VIP订阅。
     *
     * @param tenantId 租户标识
     * @return 符合条件的VIP订阅列表
     */
    List<VipSubscription> listByTenantId(Long tenantId);

    /**
     * 按租户和C端用户主键查询VIP订阅列表。
     *
     * @param tenantId 租户标识
     * @param tocUserId C端用户主键
     * @return 符合条件的VIP订阅列表
     */
    List<VipSubscription> listByTocUserId(Long tenantId, Long tocUserId);

    /**
     * 按租户和订单主键查询VIP订阅列表。
     *
     * @param tenantId 租户标识
     * @param orderId 订单主键
     * @return 符合条件的VIP订阅列表
     */
    List<VipSubscription> listByOrderId(Long tenantId, Long orderId);

    /**
     * 新增VIP订阅。
     *
     * @param vipSubscription 待保存的VIP订阅
     * @return 保存后的VIP订阅
     */
    VipSubscription save(VipSubscription vipSubscription);

    /**
     * 更新VIP订阅。
     *
     * @param vipSubscription 待更新的VIP订阅
     * @return 更新后的VIP订阅
     */
    VipSubscription update(VipSubscription vipSubscription);

    /**
     * 按租户和VIP订阅主键删除VIP订阅。
     *
     * @param tenantId 租户标识
     * @param id VIP订阅主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
