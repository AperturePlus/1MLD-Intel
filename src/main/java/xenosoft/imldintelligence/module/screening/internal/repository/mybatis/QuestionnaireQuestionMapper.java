package xenosoft.imldintelligence.module.screening.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireQuestion;

/**
 * 问卷题目 MyBatis Mapper，定义问卷题目的数据读写映射。
 */
@Mapper
public interface QuestionnaireQuestionMapper extends BaseMapper<QuestionnaireQuestion> {
}
