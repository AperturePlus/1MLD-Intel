package xenosoft.imldintelligence.module.payment.internal.repository;

import xenosoft.imldintelligence.module.payment.internal.model.VipPlan;

import java.util.List;
import java.util.Optional;

/**
 * VIP套餐仓储接口，负责在租户边界内维护会员套餐定义。
 */
public interface VipPlanRepository {
    /**
     * 按租户和VIP套餐主键查询VIP套餐。
     *
     * @param tenantId 租户标识
     * @param id VIP套餐主键
     * @return 匹配的VIP套餐，不存在时返回空
     */
    Optional<VipPlan> findById(Long tenantId, Long id);

    /**
     * 按租户和套餐编码查询VIP套餐。
     *
     * @param tenantId 租户标识
     * @param planCode 套餐编码
     * @return 匹配的VIP套餐，不存在时返回空
     */
    Optional<VipPlan> findByPlanCode(Long tenantId, String planCode);

    /**
     * 查询租户下全部VIP套餐。
     *
     * @param tenantId 租户标识
     * @return 符合条件的VIP套餐列表
     */
    List<VipPlan> listByTenantId(Long tenantId);

    /**
     * 新增VIP套餐。
     *
     * @param vipPlan 待保存的VIP套餐
     * @return 保存后的VIP套餐
     */
    VipPlan save(VipPlan vipPlan);

    /**
     * 更新VIP套餐。
     *
     * @param vipPlan 待更新的VIP套餐
     * @return 更新后的VIP套餐
     */
    VipPlan update(VipPlan vipPlan);

    /**
     * 按租户和VIP套餐主键删除VIP套餐。
     *
     * @param tenantId 租户标识
     * @param id VIP套餐主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
