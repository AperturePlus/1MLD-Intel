package xenosoft.imldintelligence.module.integration.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.integration.internal.model.IntegrationJob;

/**
 * 集成任务 MyBatis Mapper，定义集成任务的数据读写映射。
 */
@Mapper
public interface IntegrationJobMapper extends BaseMapper<IntegrationJob> {
}
