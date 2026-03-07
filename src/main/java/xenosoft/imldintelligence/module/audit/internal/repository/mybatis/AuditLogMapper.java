package xenosoft.imldintelligence.module.audit.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.audit.internal.repository.query.AuditLogQuery;
import xenosoft.imldintelligence.common.model.AuditLog;

import java.util.List;

/**
 * 审计日志 MyBatis Mapper，定义审计日志的数据读写映射。
 */
@Mapper
public interface AuditLogMapper {
    int insert(AuditLog auditLog);

    List<AuditLog> query(@Param("query") AuditLogQuery query,
                         @Param("offset") int offset,
                         @Param("limit") int limit);

    long count(@Param("query") AuditLogQuery query);
}
