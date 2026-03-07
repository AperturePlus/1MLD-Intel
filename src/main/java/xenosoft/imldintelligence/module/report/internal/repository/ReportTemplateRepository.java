package xenosoft.imldintelligence.module.report.internal.repository;

import xenosoft.imldintelligence.module.report.internal.model.ReportTemplate;

import java.util.List;
import java.util.Optional;

/**
 * 报告模板仓储接口，负责在租户边界内维护报告模板定义。
 */
public interface ReportTemplateRepository {
    /**
     * 按租户和报告模板主键查询报告模板。
     *
     * @param tenantId 租户标识
     * @param id 报告模板主键
     * @return 匹配的报告模板，不存在时返回空
     */
    Optional<ReportTemplate> findById(Long tenantId, Long id);

    /**
     * 按租户和模板编码和版本号查询报告模板。
     *
     * @param tenantId 租户标识
     * @param templateCode 模板编码
     * @param versionNo 版本号
     * @return 匹配的报告模板，不存在时返回空
     */
    Optional<ReportTemplate> findByTemplateCodeAndVersionNo(Long tenantId, String templateCode, Integer versionNo);

    /**
     * 查询租户下全部报告模板。
     *
     * @param tenantId 租户标识
     * @return 符合条件的报告模板列表
     */
    List<ReportTemplate> listByTenantId(Long tenantId);

    /**
     * 新增报告模板。
     *
     * @param reportTemplate 待保存的报告模板
     * @return 保存后的报告模板
     */
    ReportTemplate save(ReportTemplate reportTemplate);

    /**
     * 更新报告模板。
     *
     * @param reportTemplate 待更新的报告模板
     * @return 更新后的报告模板
     */
    ReportTemplate update(ReportTemplate reportTemplate);

    /**
     * 按租户和报告模板主键删除报告模板。
     *
     * @param tenantId 租户标识
     * @param id 报告模板主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
