package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertAction;

/**
 * 预警动作 MyBatis Mapper，定义预警动作的数据读写映射。
 */
@Mapper
public interface AlertActionMapper extends BaseMapper<AlertAction> {
}
