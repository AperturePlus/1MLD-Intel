package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DoctorFeedback;

/**
 * 医生反馈 MyBatis Mapper，定义医生反馈的数据读写映射。
 */
@Mapper
public interface DoctorFeedbackMapper extends BaseMapper<DoctorFeedback> {
}
