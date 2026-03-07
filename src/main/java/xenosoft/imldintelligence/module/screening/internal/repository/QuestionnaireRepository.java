package xenosoft.imldintelligence.module.screening.internal.repository;

import xenosoft.imldintelligence.module.screening.internal.model.Questionnaire;

import java.util.List;
import java.util.Optional;

/**
 * 问卷仓储接口，负责在租户边界内维护问卷定义数据。
 */
public interface QuestionnaireRepository {
    /**
     * 按租户和问卷主键查询问卷。
     *
     * @param tenantId 租户标识
     * @param id 问卷主键
     * @return 匹配的问卷，不存在时返回空
     */
    Optional<Questionnaire> findById(Long tenantId, Long id);

    /**
     * 按租户和问卷编码和版本号查询问卷。
     *
     * @param tenantId 租户标识
     * @param questionnaireCode 问卷编码
     * @param versionNo 版本号
     * @return 匹配的问卷，不存在时返回空
     */
    Optional<Questionnaire> findByQuestionnaireCodeAndVersionNo(Long tenantId, String questionnaireCode, Integer versionNo);

    /**
     * 查询租户下全部问卷。
     *
     * @param tenantId 租户标识
     * @return 符合条件的问卷列表
     */
    List<Questionnaire> listByTenantId(Long tenantId);

    /**
     * 新增问卷。
     *
     * @param questionnaire 待保存的问卷
     * @return 保存后的问卷
     */
    Questionnaire save(Questionnaire questionnaire);

    /**
     * 更新问卷。
     *
     * @param questionnaire 待更新的问卷
     * @return 更新后的问卷
     */
    Questionnaire update(Questionnaire questionnaire);

    /**
     * 按租户和问卷主键删除问卷。
     *
     * @param tenantId 租户标识
     * @param id 问卷主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
