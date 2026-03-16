package xenosoft.imldintelligence.module.report.internal.service;

import xenosoft.imldintelligence.module.report.api.dto.ReportApiDtos;
import xenosoft.imldintelligence.module.report.internal.model.Report;
import xenosoft.imldintelligence.module.report.internal.model.ReportTemplate;

import java.util.List;

/**
 * 报告业务服务接口。
 */
public interface ReportService {

    long countReports(Long tenantId, ReportApiDtos.Query.ReportPageQuery query);

    List<Report> listReports(Long tenantId, ReportApiDtos.Query.ReportPageQuery query,
                              long offset, int limit);

    Report getReport(Long tenantId, Long reportId);

    Report createReportDraft(Long tenantId, ReportApiDtos.Request.CreateReportDraftRequest request);

    Report saveReportVersion(Long tenantId, Long reportId,
                              ReportApiDtos.Request.SaveReportVersionRequest request);

    Report signReport(Long tenantId, Long reportId, ReportApiDtos.Request.SignReportRequest request);

    long countTemplates(Long tenantId, ReportApiDtos.Query.ReportTemplatePageQuery query);

    List<ReportTemplate> listTemplates(Long tenantId, ReportApiDtos.Query.ReportTemplatePageQuery query,
                                        long offset, int limit);

    ReportTemplate publishTemplate(Long tenantId, ReportApiDtos.Request.PublishTemplateRequest request);
}
