package xenosoft.imldintelligence.module.screening.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireResponse;

import java.util.List;

/**
 * 问卷应答 MyBatis Mapper，定义问卷应答的数据读写映射。
 */
@Mapper
public interface QuestionnaireResponseMapper {
    QuestionnaireResponse findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    QuestionnaireResponse findByResponseNo(@Param("tenantId") Long tenantId, @Param("responseNo") String responseNo);

    List<QuestionnaireResponse> listByTenantId(@Param("tenantId") Long tenantId);

    List<QuestionnaireResponse> listByQuestionnaireId(@Param("tenantId") Long tenantId, @Param("questionnaireId") Long questionnaireId);

    List<QuestionnaireResponse> listByTocUserId(@Param("tenantId") Long tenantId, @Param("tocUserId") Long tocUserId);

    int insert(QuestionnaireResponse questionnaireResponse);

    int update(QuestionnaireResponse questionnaireResponse);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
