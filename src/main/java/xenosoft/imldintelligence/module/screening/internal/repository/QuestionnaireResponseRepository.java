package xenosoft.imldintelligence.module.screening.internal.repository;

import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireResponse;

import java.util.List;
import java.util.Optional;

/**
 * 问卷应答仓储接口，负责在租户边界内持久化问卷作答结果。
 */
public interface QuestionnaireResponseRepository {
    /**
     * 按租户和问卷应答主键查询问卷应答。
     *
     * @param tenantId 租户标识
     * @param id 问卷应答主键
     * @return 匹配的问卷应答，不存在时返回空
     */
    Optional<QuestionnaireResponse> findById(Long tenantId, Long id);

    /**
     * 按租户和问卷应答编号查询问卷应答。
     *
     * @param tenantId 租户标识
     * @param responseNo 问卷应答编号
     * @return 匹配的问卷应答，不存在时返回空
     */
    Optional<QuestionnaireResponse> findByResponseNo(Long tenantId, String responseNo);

    /**
     * 查询租户下全部问卷应答。
     *
     * @param tenantId 租户标识
     * @return 符合条件的问卷应答列表
     */
    List<QuestionnaireResponse> listByTenantId(Long tenantId);

    /**
     * 按租户和问卷主键查询问卷应答列表。
     *
     * @param tenantId 租户标识
     * @param questionnaireId 问卷主键
     * @return 符合条件的问卷应答列表
     */
    List<QuestionnaireResponse> listByQuestionnaireId(Long tenantId, Long questionnaireId);

    /**
     * 按租户和C端用户主键查询问卷应答列表。
     *
     * @param tenantId 租户标识
     * @param tocUserId C端用户主键
     * @return 符合条件的问卷应答列表
     */
    List<QuestionnaireResponse> listByTocUserId(Long tenantId, Long tocUserId);

    /**
     * 新增问卷应答。
     *
     * @param questionnaireResponse 待保存的问卷应答
     * @return 保存后的问卷应答
     */
    QuestionnaireResponse save(QuestionnaireResponse questionnaireResponse);

    /**
     * 更新问卷应答。
     *
     * @param questionnaireResponse 待更新的问卷应答
     * @return 更新后的问卷应答
     */
    QuestionnaireResponse update(QuestionnaireResponse questionnaireResponse);

    /**
     * 按租户和问卷应答主键删除问卷应答。
     *
     * @param tenantId 租户标识
     * @param id 问卷应答主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
