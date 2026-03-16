package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisRecommendation;

/**
 * 诊断建议 MyBatis Mapper，定义诊断建议的数据读写映射。
 */
@Mapper
public interface DiagnosisRecommendationMapper extends BaseMapper<DiagnosisRecommendation> {
}
