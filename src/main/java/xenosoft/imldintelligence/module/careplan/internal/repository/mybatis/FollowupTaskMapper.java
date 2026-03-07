package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;

import java.util.List;

/**
 * 随访任务 MyBatis Mapper，定义随访任务的数据读写映射。
 */
@Mapper
public interface FollowupTaskMapper {
    FollowupTask findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    List<FollowupTask> listByTenantId(@Param("tenantId") Long tenantId);

    List<FollowupTask> listByCarePlanId(@Param("tenantId") Long tenantId, @Param("carePlanId") Long carePlanId);

    List<FollowupTask> listByPatientId(@Param("tenantId") Long tenantId, @Param("patientId") Long patientId);

    int insert(FollowupTask followupTask);

    int update(FollowupTask followupTask);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
