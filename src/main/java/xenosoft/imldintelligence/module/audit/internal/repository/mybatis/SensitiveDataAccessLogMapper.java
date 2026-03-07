package xenosoft.imldintelligence.module.audit.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.audit.internal.repository.query.SensitiveDataAccessLogQuery;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

import java.util.List;

/**
 * 敏感数据访问日志 MyBatis Mapper，定义敏感数据访问日志的数据读写映射。
 */
@Mapper
public interface SensitiveDataAccessLogMapper {
    int insert(SensitiveDataAccessLog log);

    List<SensitiveDataAccessLog> query(@Param("query") SensitiveDataAccessLogQuery query,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit);

    long count(@Param("query") SensitiveDataAccessLogQuery query);
}
