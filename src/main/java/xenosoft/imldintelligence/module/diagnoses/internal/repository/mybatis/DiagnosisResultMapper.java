package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisResult;

/**
 * 诊断结果 MyBatis Mapper，定义诊断结果的数据读写映射。
 */
@Mapper
public interface DiagnosisResultMapper extends BaseMapper<DiagnosisResult> {
}
