package xenosoft.imldintelligence.module.clinical.internal.repository;

import xenosoft.imldintelligence.module.clinical.internal.model.GeneticVariant;

import java.util.List;
import java.util.Optional;

/**
 * 基因变异仓储接口，负责在租户边界内维护基因变异条目数据。
 */
public interface GeneticVariantRepository {
    /**
     * 按租户和基因变异主键查询基因变异。
     *
     * @param tenantId 租户标识
     * @param id 基因变异主键
     * @return 匹配的基因变异，不存在时返回空
     */
    Optional<GeneticVariant> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部基因变异。
     *
     * @param tenantId 租户标识
     * @return 符合条件的基因变异列表
     */
    List<GeneticVariant> listByTenantId(Long tenantId);

    /**
     * 按租户和报告主键查询基因变异列表。
     *
     * @param tenantId 租户标识
     * @param reportId 报告主键
     * @return 符合条件的基因变异列表
     */
    List<GeneticVariant> listByReportId(Long tenantId, Long reportId);

    /**
     * 新增基因变异。
     *
     * @param geneticVariant 待保存的基因变异
     * @return 保存后的基因变异
     */
    GeneticVariant save(GeneticVariant geneticVariant);

    /**
     * 更新基因变异。
     *
     * @param geneticVariant 待更新的基因变异
     * @return 更新后的基因变异
     */
    GeneticVariant update(GeneticVariant geneticVariant);

    /**
     * 按租户和基因变异主键删除基因变异。
     *
     * @param tenantId 租户标识
     * @param id 基因变异主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
