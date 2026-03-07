package xenosoft.imldintelligence.module.clinical.internal.repository;

import xenosoft.imldintelligence.module.clinical.internal.model.ImagingReport;

import java.util.List;
import java.util.Optional;

/**
 * 影像报告仓储接口，负责在租户边界内持久化影像检查报告数据。
 */
public interface ImagingReportRepository {
    /**
     * 按租户和影像报告主键查询影像报告。
     *
     * @param tenantId 租户标识
     * @param id 影像报告主键
     * @return 匹配的影像报告，不存在时返回空
     */
    Optional<ImagingReport> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部影像报告。
     *
     * @param tenantId 租户标识
     * @return 符合条件的影像报告列表
     */
    List<ImagingReport> listByTenantId(Long tenantId);

    /**
     * 按租户和患者主键查询影像报告列表。
     *
     * @param tenantId 租户标识
     * @param patientId 患者主键
     * @return 符合条件的影像报告列表
     */
    List<ImagingReport> listByPatientId(Long tenantId, Long patientId);

    /**
     * 按租户和就诊记录主键查询影像报告列表。
     *
     * @param tenantId 租户标识
     * @param encounterId 就诊记录主键
     * @return 符合条件的影像报告列表
     */
    List<ImagingReport> listByEncounterId(Long tenantId, Long encounterId);

    /**
     * 新增影像报告。
     *
     * @param imagingReport 待保存的影像报告
     * @return 保存后的影像报告
     */
    ImagingReport save(ImagingReport imagingReport);

    /**
     * 更新影像报告。
     *
     * @param imagingReport 待更新的影像报告
     * @return 更新后的影像报告
     */
    ImagingReport update(ImagingReport imagingReport);

    /**
     * 按租户和影像报告主键删除影像报告。
     *
     * @param tenantId 租户标识
     * @param id 影像报告主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
