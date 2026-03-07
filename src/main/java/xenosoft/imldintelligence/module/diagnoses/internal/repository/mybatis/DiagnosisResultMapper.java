package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisResult;

import java.util.List;

/**
 * 诊断结果 MyBatis Mapper，定义诊断结果的数据读写映射。
 */
@Mapper
public interface DiagnosisResultMapper {
    DiagnosisResult findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    List<DiagnosisResult> listByTenantId(@Param("tenantId") Long tenantId);

    List<DiagnosisResult> listBySessionId(@Param("tenantId") Long tenantId, @Param("sessionId") Long sessionId);

    int insert(DiagnosisResult diagnosisResult);

    int update(DiagnosisResult diagnosisResult);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
