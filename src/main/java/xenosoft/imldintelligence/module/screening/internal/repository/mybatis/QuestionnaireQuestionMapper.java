package xenosoft.imldintelligence.module.screening.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireQuestion;

import java.util.List;

/**
 * 问卷题目 MyBatis Mapper，定义问卷题目的数据读写映射。
 */
@Mapper
public interface QuestionnaireQuestionMapper {
    QuestionnaireQuestion findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    QuestionnaireQuestion findByQuestionnaireIdAndQuestionNo(@Param("tenantId") Long tenantId,
                                                             @Param("questionnaireId") Long questionnaireId,
                                                             @Param("questionNo") String questionNo);

    List<QuestionnaireQuestion> listByTenantId(@Param("tenantId") Long tenantId);

    List<QuestionnaireQuestion> listByQuestionnaireId(@Param("tenantId") Long tenantId, @Param("questionnaireId") Long questionnaireId);

    int insert(QuestionnaireQuestion questionnaireQuestion);

    int update(QuestionnaireQuestion questionnaireQuestion);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
