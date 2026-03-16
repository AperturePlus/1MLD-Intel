package xenosoft.imldintelligence.module.audit.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.audit.internal.repository.query.SensitiveDataAccessLogQuery;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

/**
 * 敏感数据访问日志 MyBatis Mapper，定义敏感数据访问日志的数据读写映射。
 */
@Mapper
public interface SensitiveDataAccessLogMapper extends BaseMapper<SensitiveDataAccessLog> {
    java.util.List<SensitiveDataAccessLog> query(@Param("query") SensitiveDataAccessLogQuery query,
                                                   @Param("offset") int offset,
                                                   @Param("limit") int limit);

    long count(@Param("query") SensitiveDataAccessLogQuery query);
}
