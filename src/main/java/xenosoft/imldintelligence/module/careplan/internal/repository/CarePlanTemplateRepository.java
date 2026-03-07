package xenosoft.imldintelligence.module.careplan.internal.repository;

import xenosoft.imldintelligence.module.careplan.internal.model.CarePlanTemplate;

import java.util.List;
import java.util.Optional;

/**
 * 护理计划模板仓储接口，负责在租户边界内维护模板定义数据。
 */
public interface CarePlanTemplateRepository {
    /**
     * 按租户和护理计划模板主键查询护理计划模板。
     *
     * @param tenantId 租户标识
     * @param id 护理计划模板主键
     * @return 匹配的护理计划模板，不存在时返回空
     */
    Optional<CarePlanTemplate> findById(Long tenantId, Long id);

    /**
     * 按租户和模板编码和版本号查询护理计划模板。
     *
     * @param tenantId 租户标识
     * @param templateCode 模板编码
     * @param versionNo 版本号
     * @return 匹配的护理计划模板，不存在时返回空
     */
    Optional<CarePlanTemplate> findByTemplateCodeAndVersionNo(Long tenantId, String templateCode, Integer versionNo);

    /**
     * 查询租户下全部护理计划模板。
     *
     * @param tenantId 租户标识
     * @return 符合条件的护理计划模板列表
     */
    List<CarePlanTemplate> listByTenantId(Long tenantId);

    /**
     * 新增护理计划模板。
     *
     * @param carePlanTemplate 待保存的护理计划模板
     * @return 保存后的护理计划模板
     */
    CarePlanTemplate save(CarePlanTemplate carePlanTemplate);

    /**
     * 更新护理计划模板。
     *
     * @param carePlanTemplate 待更新的护理计划模板
     * @return 更新后的护理计划模板
     */
    CarePlanTemplate update(CarePlanTemplate carePlanTemplate);

    /**
     * 按租户和护理计划模板主键删除护理计划模板。
     *
     * @param tenantId 租户标识
     * @param id 护理计划模板主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
