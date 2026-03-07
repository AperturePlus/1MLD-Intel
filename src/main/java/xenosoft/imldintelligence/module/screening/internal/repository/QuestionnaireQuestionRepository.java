package xenosoft.imldintelligence.module.screening.internal.repository;

import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireQuestion;

import java.util.List;
import java.util.Optional;

/**
 * 问卷题目仓储接口，负责在租户边界内维护问卷题目定义。
 */
public interface QuestionnaireQuestionRepository {
    /**
     * 按租户和问卷题目主键查询问卷题目。
     *
     * @param tenantId 租户标识
     * @param id 问卷题目主键
     * @return 匹配的问卷题目，不存在时返回空
     */
    Optional<QuestionnaireQuestion> findById(Long tenantId, Long id);

    /**
     * 按租户和问卷主键和题目编号查询问卷题目。
     *
     * @param tenantId 租户标识
     * @param questionnaireId 问卷主键
     * @param questionNo 题目编号
     * @return 匹配的问卷题目，不存在时返回空
     */
    Optional<QuestionnaireQuestion> findByQuestionnaireIdAndQuestionNo(Long tenantId, Long questionnaireId, String questionNo);

    /**
     * 查询租户下全部问卷题目。
     *
     * @param tenantId 租户标识
     * @return 符合条件的问卷题目列表
     */
    List<QuestionnaireQuestion> listByTenantId(Long tenantId);

    /**
     * 按租户和问卷主键查询问卷题目列表。
     *
     * @param tenantId 租户标识
     * @param questionnaireId 问卷主键
     * @return 符合条件的问卷题目列表
     */
    List<QuestionnaireQuestion> listByQuestionnaireId(Long tenantId, Long questionnaireId);

    /**
     * 新增问卷题目。
     *
     * @param questionnaireQuestion 待保存的问卷题目
     * @return 保存后的问卷题目
     */
    QuestionnaireQuestion save(QuestionnaireQuestion questionnaireQuestion);

    /**
     * 更新问卷题目。
     *
     * @param questionnaireQuestion 待更新的问卷题目
     * @return 更新后的问卷题目
     */
    QuestionnaireQuestion update(QuestionnaireQuestion questionnaireQuestion);

    /**
     * 按租户和问卷题目主键删除问卷题目。
     *
     * @param tenantId 租户标识
     * @param id 问卷题目主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
