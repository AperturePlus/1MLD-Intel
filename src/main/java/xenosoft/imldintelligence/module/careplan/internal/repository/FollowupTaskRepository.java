package xenosoft.imldintelligence.module.careplan.internal.repository;

import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;

import java.util.List;
import java.util.Optional;

/**
 * 随访任务仓储接口，负责在租户边界内持久化护理计划随访任务。
 */
public interface FollowupTaskRepository {
    /**
     * 按租户和随访任务主键查询随访任务。
     *
     * @param tenantId 租户标识
     * @param id 随访任务主键
     * @return 匹配的随访任务，不存在时返回空
     */
    Optional<FollowupTask> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部随访任务。
     *
     * @param tenantId 租户标识
     * @return 符合条件的随访任务列表
     */
    List<FollowupTask> listByTenantId(Long tenantId);

    /**
     * 按租户和护理计划主键查询随访任务列表。
     *
     * @param tenantId 租户标识
     * @param carePlanId 护理计划主键
     * @return 符合条件的随访任务列表
     */
    List<FollowupTask> listByCarePlanId(Long tenantId, Long carePlanId);

    /**
     * 按租户和患者主键查询随访任务列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的随访任务列表
     */
    List<FollowupTask> listByPatientId(Long tenantId, Long patientId);

    /**
     * 新增随访任务。
     *
     * @param followupTask 待保存的随访任务
     * @return 保存后的随访任务
     */
    FollowupTask save(FollowupTask followupTask);

    /**
     * 更新随访任务。
     *
     * @param followupTask 待更新的随访任务
     * @return 更新后的随访任务
     */
    FollowupTask update(FollowupTask followupTask);

    /**
     * 按租户和随访任务主键删除随访任务。
     *
     * @param tenantId 租户标识
     * @param id 随访任务主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
