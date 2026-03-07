package xenosoft.imldintelligence.module.careplan.internal.repository;

import xenosoft.imldintelligence.module.careplan.internal.model.PatientReportedData;

import java.util.List;
import java.util.Optional;

/**
 * 患者上报数据仓储接口，负责在租户边界内持久化患者主动上报信息。
 */
public interface PatientReportedDataRepository {
    /**
     * 按租户和患者上报数据主键查询患者上报数据。
     *
     * @param tenantId 租户标识
     * @param id 患者上报数据主键
     * @return 匹配的患者上报数据，不存在时返回空
     */
    Optional<PatientReportedData> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部患者上报数据。
     *
     * @param tenantId 租户标识
     * @return 符合条件的患者上报数据列表
     */
    List<PatientReportedData> listByTenantId(Long tenantId);

    /**
     * 按租户和护理计划主键查询患者上报数据列表。
     *
     * @param tenantId 租户标识
     * @param carePlanId 护理计划主键
     * @return 符合条件的患者上报数据列表
     */
    List<PatientReportedData> listByCarePlanId(Long tenantId, Long carePlanId);

    /**
     * 按租户和患者主键查询患者上报数据列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的患者上报数据列表
     */
    List<PatientReportedData> listByPatientId(Long tenantId, Long patientId);

    /**
     * 新增患者上报数据。
     *
     * @param patientReportedData 待保存的患者上报数据
     * @return 保存后的患者上报数据
     */
    PatientReportedData save(PatientReportedData patientReportedData);

    /**
     * 更新患者上报数据。
     *
     * @param patientReportedData 待更新的患者上报数据
     * @return 更新后的患者上报数据
     */
    PatientReportedData update(PatientReportedData patientReportedData);

    /**
     * 按租户和患者上报数据主键删除患者上报数据。
     *
     * @param tenantId 租户标识
     * @param id 患者上报数据主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
