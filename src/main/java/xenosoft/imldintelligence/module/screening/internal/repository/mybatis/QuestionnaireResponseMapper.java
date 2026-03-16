package xenosoft.imldintelligence.module.screening.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireResponse;

/**
 * 问卷应答 MyBatis Mapper，定义问卷应答的数据读写映射。
 */
@Mapper
public interface QuestionnaireResponseMapper extends BaseMapper<QuestionnaireResponse> {
}
