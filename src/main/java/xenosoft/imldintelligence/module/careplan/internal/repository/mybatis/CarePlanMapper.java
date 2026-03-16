package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.careplan.internal.model.CarePlan;

/**
 * 护理计划 MyBatis Mapper，定义护理计划的数据读写映射。
 */
@Mapper
public interface CarePlanMapper extends BaseMapper<CarePlan> {
}
