package xenosoft.imldintelligence.module.clinical.internal.repository;

import xenosoft.imldintelligence.module.clinical.internal.model.IndicatorDict;

import java.util.List;
import java.util.Optional;

/**
 * 指标字典仓储接口，负责维护标准化指标字典数据。
 */
public interface IndicatorDictRepository {
    /**
     * 按指标编码查询指标字典。
     *
     * @param code 指标编码
     * @return 匹配的指标字典，不存在时返回空
     */
    Optional<IndicatorDict> findByCode(String code);

    /**
     * 查询全部指标字典。
     *
     * @return 全部指标字典列表
     */
    List<IndicatorDict> listAll();

    /**
     * 按状态编码查询指标字典列表。
     *
     * @param status 状态编码
     * @return 符合条件的指标字典列表
     */
    List<IndicatorDict> listByStatus(String status);

    /**
     * 新增指标字典。
     *
     * @param indicatorDict 待保存的指标字典
     * @return 保存后的指标字典
     */
    IndicatorDict save(IndicatorDict indicatorDict);

    /**
     * 更新指标字典。
     *
     * @param indicatorDict 待更新的指标字典
     * @return 更新后的指标字典
     */
    IndicatorDict update(IndicatorDict indicatorDict);

    /**
     * 按指标编码删除指标字典。
     *
     * @param code 指标编码
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteByCode(String code);
}
