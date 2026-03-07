package xenosoft.imldintelligence.module.report.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.report.internal.model.ReportVersion;

import java.util.List;

/**
 * 报告版本 MyBatis Mapper，定义报告版本的数据读写映射。
 */
@Mapper
public interface ReportVersionMapper {
    ReportVersion findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    ReportVersion findByReportIdAndVersionNum(@Param("tenantId") Long tenantId,
                                              @Param("reportId") Long reportId,
                                              @Param("versionNum") Integer versionNum);

    List<ReportVersion> listByReportId(@Param("tenantId") Long tenantId, @Param("reportId") Long reportId);

    int insert(ReportVersion reportVersion);

    int update(ReportVersion reportVersion);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
