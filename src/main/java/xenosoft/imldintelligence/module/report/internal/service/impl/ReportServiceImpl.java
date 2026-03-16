package xenosoft.imldintelligence.module.report.internal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xenosoft.imldintelligence.module.report.api.dto.ReportApiDtos;
import xenosoft.imldintelligence.module.report.internal.model.Report;
import xenosoft.imldintelligence.module.report.internal.model.ReportTemplate;
import xenosoft.imldintelligence.module.report.internal.model.ReportVersion;
import xenosoft.imldintelligence.module.report.internal.repository.ReportRepository;
import xenosoft.imldintelligence.module.report.internal.repository.ReportTemplateRepository;
import xenosoft.imldintelligence.module.report.internal.repository.ReportVersionRepository;
import xenosoft.imldintelligence.module.report.internal.service.ReportService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportVersionRepository reportVersionRepository;
    private final ReportTemplateRepository reportTemplateRepository;

    @Override
    public long countReports(Long tenantId, ReportApiDtos.Query.ReportPageQuery query) {
        return listAllReports(tenantId, query).size();
    }

    @Override
    public List<Report> listReports(Long tenantId, ReportApiDtos.Query.ReportPageQuery query,
                                     long offset, int limit) {
        return paginate(listAllReports(tenantId, query), offset, limit);
    }

    @Override
    public Report getReport(Long tenantId, Long reportId) {
        return reportRepository.findById(tenantId, reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + reportId));
    }

    @Override
    public Report createReportDraft(Long tenantId, ReportApiDtos.Request.CreateReportDraftRequest request) {
        Report report = new Report();
        report.setTenantId(tenantId);
        report.setPatientId(request.patientId());
        report.setEncounterId(request.encounterId());
        report.setSessionId(request.sessionId());
        report.setTemplateId(request.templateId());
        report.setReportNo(UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase());
        report.setStatus("DRAFT");
        report.setCurrentVersion(1);
        report.setCreatedBy(request.createdBy());
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        report.setCreatedAt(now);
        report.setUpdatedAt(now);
        Report saved = reportRepository.save(report);

        ReportVersion version = new ReportVersion();
        version.setTenantId(tenantId);
        version.setReportId(saved.getId());
        version.setVersionNum(1);
        version.setContentSnapshot(request.initialContent());
        version.setChangeSummary("Initial draft");
        version.setChangedBy(request.createdBy());
        version.setCreatedAt(now);
        reportVersionRepository.save(version);

        return saved;
    }

    @Override
    public Report saveReportVersion(Long tenantId, Long reportId,
                                     ReportApiDtos.Request.SaveReportVersionRequest request) {
        Report report = reportRepository.findById(tenantId, reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + reportId));

        int nextVersion = (report.getCurrentVersion() != null ? report.getCurrentVersion() : 0) + 1;

        ReportVersion version = new ReportVersion();
        version.setTenantId(tenantId);
        version.setReportId(reportId);
        version.setVersionNum(nextVersion);
        version.setContentSnapshot(request.contentSnapshot());
        version.setChangeSummary(request.changeSummary());
        version.setChangedBy(request.changedBy());
        version.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        reportVersionRepository.save(version);

        report.setCurrentVersion(nextVersion);
        report.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return reportRepository.update(report);
    }

    @Override
    public Report signReport(Long tenantId, Long reportId, ReportApiDtos.Request.SignReportRequest request) {
        Report report = reportRepository.findById(tenantId, reportId)
                .orElseThrow(() -> new IllegalArgumentException("Report not found: " + reportId));
        report.setSignedBy(request.signedBy());
        report.setSignedAt(OffsetDateTime.now(ZoneOffset.UTC));
        report.setSignatureData(request.signatureData());
        report.setStatus("SIGNED");
        report.setUpdatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return reportRepository.update(report);
    }

    @Override
    public long countTemplates(Long tenantId, ReportApiDtos.Query.ReportTemplatePageQuery query) {
        return listAllTemplates(tenantId, query).size();
    }

    @Override
    public List<ReportTemplate> listTemplates(Long tenantId, ReportApiDtos.Query.ReportTemplatePageQuery query,
                                               long offset, int limit) {
        return paginate(listAllTemplates(tenantId, query), offset, limit);
    }

    @Override
    public ReportTemplate publishTemplate(Long tenantId, ReportApiDtos.Request.PublishTemplateRequest request) {
        ReportTemplate template = new ReportTemplate();
        template.setTenantId(tenantId);
        template.setTemplateCode(request.templateCode());
        template.setTemplateName(request.templateName());
        template.setDiseaseCode(request.diseaseCode());
        template.setDepartment(request.department());
        template.setTemplateSchema(request.templateSchema());
        template.setStatus(request.status() != null ? request.status() : "ACTIVE");
        template.setVersionNo(request.versionNo() != null ? request.versionNo() : 1);
        template.setCreatedBy(request.createdBy());
        template.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return reportTemplateRepository.save(template);
    }

    private List<Report> listAllReports(Long tenantId, ReportApiDtos.Query.ReportPageQuery query) {
        List<Report> list;
        if (query.patientId() != null) {
            list = reportRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.sessionId() != null) {
            list = reportRepository.listBySessionId(tenantId, query.sessionId());
        } else {
            list = reportRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(r -> query.encounterId() == null || query.encounterId().equals(r.getEncounterId()))
                .filter(r -> query.status() == null || query.status().equals(r.getStatus()))
                .filter(r -> query.signedBy() == null || query.signedBy().equals(r.getSignedBy()))
                .filter(r -> query.createdFrom() == null || (r.getCreatedAt() != null && !r.getCreatedAt().isBefore(query.createdFrom())))
                .filter(r -> query.createdTo() == null || (r.getCreatedAt() != null && !r.getCreatedAt().isAfter(query.createdTo())))
                .toList();
    }

    private List<ReportTemplate> listAllTemplates(Long tenantId,
                                                    ReportApiDtos.Query.ReportTemplatePageQuery query) {
        return reportTemplateRepository.listByTenantId(tenantId).stream()
                .filter(t -> query.diseaseCode() == null || query.diseaseCode().equals(t.getDiseaseCode()))
                .filter(t -> query.department() == null || query.department().equals(t.getDepartment()))
                .filter(t -> query.status() == null || query.status().equals(t.getStatus()))
                .toList();
    }

    private <T> List<T> paginate(List<T> list, long offset, int limit) {
        int from = (int) Math.min(offset, list.size());
        int to = (int) Math.min(offset + limit, list.size());
        return list.subList(from, to);
    }
}
