package xenosoft.imldintelligence.module.report.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.report.internal.model.Report;

import java.util.List;

/**
 * 报告 MyBatis Mapper，定义报告的数据读写映射。
 */
@Mapper
public interface ReportMapper {
    Report findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    Report findByReportNo(@Param("tenantId") Long tenantId, @Param("reportNo") String reportNo);

    List<Report> listByTenantId(@Param("tenantId") Long tenantId);

    List<Report> listByPatientId(@Param("tenantId") Long tenantId, @Param("patientId") Long patientId);

    List<Report> listBySessionId(@Param("tenantId") Long tenantId, @Param("sessionId") Long sessionId);

    int insert(Report report);

    int update(Report report);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
