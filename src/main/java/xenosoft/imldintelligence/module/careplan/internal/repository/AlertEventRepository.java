package xenosoft.imldintelligence.module.careplan.internal.repository;

import xenosoft.imldintelligence.module.careplan.internal.model.AlertEvent;

import java.util.List;
import java.util.Optional;

/**
 * 预警事件仓储接口，负责在租户边界内持久化护理计划相关预警数据。
 */
public interface AlertEventRepository {
    /**
     * 按租户和预警事件主键查询预警事件。
     *
     * @param tenantId 租户标识
     * @param id 预警事件主键
     * @return 匹配的预警事件，不存在时返回空
     */
    Optional<AlertEvent> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部预警事件。
     *
     * @param tenantId 租户标识
     * @return 符合条件的预警事件列表
     */
    List<AlertEvent> listByTenantId(Long tenantId);

    /**
     * 按租户和护理计划主键查询预警事件列表。
     *
     * @param tenantId 租户标识
     * @param carePlanId 护理计划主键
     * @return 符合条件的预警事件列表
     */
    List<AlertEvent> listByCarePlanId(Long tenantId, Long carePlanId);

    /**
     * 按租户和患者主键查询预警事件列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的预警事件列表
     */
    List<AlertEvent> listByPatientId(Long tenantId, Long patientId);

    /**
     * 新增预警事件。
     *
     * @param alertEvent 待保存的预警事件
     * @return 保存后的预警事件
     */
    AlertEvent save(AlertEvent alertEvent);

    /**
     * 更新预警事件。
     *
     * @param alertEvent 待更新的预警事件
     * @return 更新后的预警事件
     */
    AlertEvent update(AlertEvent alertEvent);

    /**
     * 按租户和预警事件主键删除预警事件。
     *
     * @param tenantId 租户标识
     * @param id 预警事件主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
