package xenosoft.imldintelligence.module.report.internal.repository;

import xenosoft.imldintelligence.module.report.internal.model.ReportVersion;

import java.util.List;
import java.util.Optional;

/**
 * 报告版本仓储接口，负责在租户边界内维护报告版本快照。
 */
public interface ReportVersionRepository {
    /**
     * 按租户和报告版本主键查询报告版本。
     *
     * @param tenantId 租户标识
     * @param id 报告版本主键
     * @return 匹配的报告版本，不存在时返回空
     */
    Optional<ReportVersion> findById(Long tenantId, Long id);

    /**
     * 按租户和报告主键和版本号查询报告版本。
     *
     * @param tenantId 租户标识
     * @param reportId 报告主键
     * @param versionNum 版本号
     * @return 匹配的报告版本，不存在时返回空
     */
    Optional<ReportVersion> findByReportIdAndVersionNum(Long tenantId, Long reportId, Integer versionNum);

    /**
     * 按租户和报告主键查询报告版本列表。
     *
     * @param tenantId 租户标识
     * @param reportId 报告主键
     * @return 符合条件的报告版本列表
     */
    List<ReportVersion> listByReportId(Long tenantId, Long reportId);

    /**
     * 新增报告版本。
     *
     * @param reportVersion 待保存的报告版本
     * @return 保存后的报告版本
     */
    ReportVersion save(ReportVersion reportVersion);

    /**
     * 更新报告版本。
     *
     * @param reportVersion 待更新的报告版本
     * @return 更新后的报告版本
     */
    ReportVersion update(ReportVersion reportVersion);

    /**
     * 按租户和报告版本主键删除报告版本。
     *
     * @param tenantId 租户标识
     * @param id 报告版本主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
