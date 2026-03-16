package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertEvent;

/**
 * 预警事件 MyBatis Mapper，定义预警事件的数据读写映射。
 */
@Mapper
public interface AlertEventMapper extends BaseMapper<AlertEvent> {
}
