package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;

/**
 * 随访任务 MyBatis Mapper，定义随访任务的数据读写映射。
 */
@Mapper
public interface FollowupTaskMapper extends BaseMapper<FollowupTask> {
}
