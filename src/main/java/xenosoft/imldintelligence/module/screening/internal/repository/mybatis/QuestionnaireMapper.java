package xenosoft.imldintelligence.module.screening.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.screening.internal.model.Questionnaire;

/**
 * 问卷 MyBatis Mapper，定义问卷的数据读写映射。
 */
@Mapper
public interface QuestionnaireMapper extends BaseMapper<Questionnaire> {
}
