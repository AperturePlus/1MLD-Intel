package xenosoft.imldintelligence.module.integration.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.integration.internal.model.IntegrationJob;

import java.util.List;

/**
 * 集成任务 MyBatis Mapper，定义集成任务的数据读写映射。
 */
@Mapper
public interface IntegrationJobMapper {
    IntegrationJob findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    IntegrationJob findByJobNo(@Param("tenantId") Long tenantId, @Param("jobNo") String jobNo);

    List<IntegrationJob> listByTenantId(@Param("tenantId") Long tenantId);

    List<IntegrationJob> listBySourceSystem(@Param("tenantId") Long tenantId, @Param("sourceSystem") String sourceSystem);

    int insert(IntegrationJob integrationJob);

    int update(IntegrationJob integrationJob);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
