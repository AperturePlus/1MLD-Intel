package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;

/**
 * 诊断会话 MyBatis Mapper，定义诊断会话的数据读写映射。
 */
@Mapper
public interface DiagnosisSessionMapper extends BaseMapper<DiagnosisSession> {
}
