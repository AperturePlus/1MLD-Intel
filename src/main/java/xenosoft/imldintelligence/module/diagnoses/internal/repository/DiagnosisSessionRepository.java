package xenosoft.imldintelligence.module.diagnoses.internal.repository;

import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;

import java.util.List;
import java.util.Optional;

/**
 * 诊断会话仓储接口，负责在租户边界内持久化诊断会话数据。
 */
public interface DiagnosisSessionRepository {
    /**
     * 按租户和诊断会话主键查询诊断会话。
     *
     * @param tenantId 租户标识
     * @param id 诊断会话主键
     * @return 匹配的诊断会话，不存在时返回空
     */
    Optional<DiagnosisSession> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部诊断会话。
     *
     * @param tenantId 租户标识
     * @return 符合条件的诊断会话列表
     */
    List<DiagnosisSession> listByTenantId(Long tenantId);

    /**
     * 按租户和患者主键查询诊断会话列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的诊断会话列表
     */
    List<DiagnosisSession> listByPatientId(Long tenantId, Long patientId);

    /**
     * 按租户和就诊记录主键查询诊断会话列表。
     *
     * @param tenantId 租户标识
     * @param encounterId 就诊记录主键
     * @return 符合条件的诊断会话列表
     */
    List<DiagnosisSession> listByEncounterId(Long tenantId, Long encounterId);

    /**
     * 新增诊断会话。
     *
     * @param diagnosisSession 待保存的诊断会话
     * @return 保存后的诊断会话
     */
    DiagnosisSession save(DiagnosisSession diagnosisSession);

    /**
     * 更新诊断会话。
     *
     * @param diagnosisSession 待更新的诊断会话
     * @return 更新后的诊断会话
     */
    DiagnosisSession update(DiagnosisSession diagnosisSession);

    /**
     * 按租户和诊断会话主键删除诊断会话。
     *
     * @param tenantId 租户标识
     * @param id 诊断会话主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
