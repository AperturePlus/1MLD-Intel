package xenosoft.imldintelligence.module.careplan.internal.repository;

import xenosoft.imldintelligence.module.careplan.internal.model.CarePlan;

import java.util.List;
import java.util.Optional;

/**
 * 护理计划仓储接口，负责在租户边界内持久化护理计划数据。
 */
public interface CarePlanRepository {
    /**
     * 按租户和护理计划主键查询护理计划。
     *
     * @param tenantId 租户标识
     * @param id 护理计划主键
     * @return 匹配的护理计划，不存在时返回空
     */
    Optional<CarePlan> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部护理计划。
     *
     * @param tenantId 租户标识
     * @return 符合条件的护理计划列表
     */
    List<CarePlan> listByTenantId(Long tenantId);

    /**
     * 按租户和患者主键查询护理计划列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的护理计划列表
     */
    List<CarePlan> listByPatientId(Long tenantId, Long patientId);

    /**
     * 新增护理计划。
     *
     * @param carePlan 待保存的护理计划
     * @return 保存后的护理计划
     */
    CarePlan save(CarePlan carePlan);

    /**
     * 更新护理计划。
     *
     * @param carePlan 待更新的护理计划
     * @return 更新后的护理计划
     */
    CarePlan update(CarePlan carePlan);

    /**
     * 按租户和护理计划主键删除护理计划。
     *
     * @param tenantId 租户标识
     * @param id 护理计划主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
