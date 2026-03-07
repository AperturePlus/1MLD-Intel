package xenosoft.imldintelligence.module.careplan.internal.repository;

import xenosoft.imldintelligence.module.careplan.internal.model.AlertAction;

import java.util.List;
import java.util.Optional;

/**
 * 预警动作仓储接口，负责在租户边界内持久化预警处置动作数据。
 */
public interface AlertActionRepository {
    /**
     * 按租户和预警动作主键查询预警动作。
     *
     * @param tenantId 租户标识
     * @param id 预警动作主键
     * @return 匹配的预警动作，不存在时返回空
     */
    Optional<AlertAction> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部预警动作。
     *
     * @param tenantId 租户标识
     * @return 符合条件的预警动作列表
     */
    List<AlertAction> listByTenantId(Long tenantId);

    /**
     * 按租户和预警事件主键查询预警动作列表。
     *
     * @param tenantId 租户标识
     * @param alertId 预警事件主键
     * @return 符合条件的预警动作列表
     */
    List<AlertAction> listByAlertId(Long tenantId, Long alertId);

    /**
     * 新增预警动作。
     *
     * @param alertAction 待保存的预警动作
     * @return 保存后的预警动作
     */
    AlertAction save(AlertAction alertAction);

    /**
     * 更新预警动作。
     *
     * @param alertAction 待更新的预警动作
     * @return 更新后的预警动作
     */
    AlertAction update(AlertAction alertAction);

    /**
     * 按租户和预警动作主键删除预警动作。
     *
     * @param tenantId 租户标识
     * @param id 预警动作主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
