package xenosoft.imldintelligence.module.audit.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.audit.internal.repository.query.ModelInvocationLogQuery;
import xenosoft.imldintelligence.common.model.ModelInvocationLog;

/**
 * 模型调用日志 MyBatis Mapper，定义模型调用日志的数据读写映射。
 */
@Mapper
public interface ModelInvocationLogMapper extends BaseMapper<ModelInvocationLog> {
    java.util.List<ModelInvocationLog> query(@Param("query") ModelInvocationLogQuery query,
                                                   @Param("offset") int offset,
                                                   @Param("limit") int limit);

    long count(@Param("query") ModelInvocationLogQuery query);
}
