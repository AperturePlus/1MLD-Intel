package xenosoft.imldintelligence.module.clinical.internal.repository;

import xenosoft.imldintelligence.module.clinical.internal.model.LabResult;

import java.util.List;
import java.util.Optional;

/**
 * 检验结果仓储接口，负责在租户边界内持久化检验结果数据。
 */
public interface LabResultRepository {
    /**
     * 按租户和检验结果主键查询检验结果。
     *
     * @param tenantId 租户标识
     * @param id 检验结果主键
     * @return 匹配的检验结果，不存在时返回空
     */
    Optional<LabResult> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部检验结果。
     *
     * @param tenantId 租户标识
     * @return 符合条件的检验结果列表
     */
    List<LabResult> listByTenantId(Long tenantId);

    /**
     * 按租户和患者主键查询检验结果列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的检验结果列表
     */
    List<LabResult> listByPatientId(Long tenantId, Long patientId);

    /**
     * 按租户和就诊记录主键查询检验结果列表。
     *
     * @param tenantId 租户标识
     * @param encounterId 就诊记录主键
     * @return 符合条件的检验结果列表
     */
    List<LabResult> listByEncounterId(Long tenantId, Long encounterId);

    /**
     * 新增检验结果。
     *
     * @param labResult 待保存的检验结果
     * @return 保存后的检验结果
     */
    LabResult save(LabResult labResult);

    /**
     * 更新检验结果。
     *
     * @param labResult 待更新的检验结果
     * @return 更新后的检验结果
     */
    LabResult update(LabResult labResult);

    /**
     * 按租户和检验结果主键删除检验结果。
     *
     * @param tenantId 租户标识
     * @param id 检验结果主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
