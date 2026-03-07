package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisRecommendation;

import java.util.List;

/**
 * 诊断建议 MyBatis Mapper，定义诊断建议的数据读写映射。
 */
@Mapper
public interface DiagnosisRecommendationMapper {
    DiagnosisRecommendation findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    List<DiagnosisRecommendation> listByTenantId(@Param("tenantId") Long tenantId);

    List<DiagnosisRecommendation> listBySessionId(@Param("tenantId") Long tenantId, @Param("sessionId") Long sessionId);

    int insert(DiagnosisRecommendation diagnosisRecommendation);

    int update(DiagnosisRecommendation diagnosisRecommendation);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
