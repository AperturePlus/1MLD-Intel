package xenosoft.imldintelligence.module.clinical.internal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xenosoft.imldintelligence.module.clinical.api.dto.ClinicalApiDtos;
import xenosoft.imldintelligence.module.clinical.internal.model.ClinicalHistoryEntry;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticReport;
import xenosoft.imldintelligence.module.clinical.internal.model.ImagingReport;
import xenosoft.imldintelligence.module.clinical.internal.model.IndicatorMapping;
import xenosoft.imldintelligence.module.clinical.internal.model.LabResult;
import xenosoft.imldintelligence.module.clinical.internal.repository.ClinicalHistoryEntryRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.GeneticReportRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.ImagingReportRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.IndicatorMappingRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.LabResultRepository;
import xenosoft.imldintelligence.module.clinical.internal.service.ClinicalService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicalServiceImpl implements ClinicalService {

    private final LabResultRepository labResultRepository;
    private final GeneticReportRepository geneticReportRepository;
    private final ImagingReportRepository imagingReportRepository;
    private final ClinicalHistoryEntryRepository clinicalHistoryEntryRepository;
    private final IndicatorMappingRepository indicatorMappingRepository;

    @Override
    public long countLabResults(Long tenantId, ClinicalApiDtos.Query.LabResultPageQuery query) {
        return listAllLabResults(tenantId, query).size();
    }

    @Override
    public List<LabResult> listLabResults(Long tenantId, ClinicalApiDtos.Query.LabResultPageQuery query,
                                           long offset, int limit) {
        return paginate(listAllLabResults(tenantId, query), offset, limit);
    }

    @Override
    public LabResult upsertLabResult(Long tenantId, ClinicalApiDtos.Request.UpsertLabResultRequest request) {
        LabResult result = new LabResult();
        result.setTenantId(tenantId);
        result.setPatientId(request.patientId());
        result.setEncounterId(request.encounterId());
        result.setIndicatorCode(request.indicatorCode());
        result.setValueNumeric(request.valueNumeric());
        result.setValueText(request.valueText());
        result.setUnit(request.unit());
        result.setReferenceLow(request.referenceLow());
        result.setReferenceHigh(request.referenceHigh());
        result.setAbnormalFlag(request.abnormalFlag());
        result.setSourceType(request.sourceType() != null ? request.sourceType() : "MANUAL");
        result.setRawData(request.rawData());
        result.setCollectedAt(request.collectedAt() != null ? request.collectedAt() : OffsetDateTime.now(ZoneOffset.UTC));
        result.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return labResultRepository.save(result);
    }

    @Override
    public long countGeneticReports(Long tenantId, ClinicalApiDtos.Query.GeneticReportPageQuery query) {
        return listAllGeneticReports(tenantId, query).size();
    }

    @Override
    public List<GeneticReport> listGeneticReports(Long tenantId,
                                                    ClinicalApiDtos.Query.GeneticReportPageQuery query,
                                                    long offset, int limit) {
        return paginate(listAllGeneticReports(tenantId, query), offset, limit);
    }

    @Override
    public GeneticReport registerGeneticReport(Long tenantId,
                                                ClinicalApiDtos.Request.RegisterGeneticReportRequest request) {
        GeneticReport report = new GeneticReport();
        report.setTenantId(tenantId);
        report.setPatientId(request.patientId());
        report.setEncounterId(request.encounterId());
        report.setReportSource(request.reportSource());
        report.setReportDate(request.reportDate());
        report.setFileId(request.fileId());
        report.setParseStatus("PENDING");
        report.setSummary(request.summary());
        report.setConclusion(request.conclusion());
        report.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return geneticReportRepository.save(report);
    }

    @Override
    public long countImagingReports(Long tenantId, ClinicalApiDtos.Query.ImagingReportPageQuery query) {
        return listAllImagingReports(tenantId, query).size();
    }

    @Override
    public List<ImagingReport> listImagingReports(Long tenantId,
                                                    ClinicalApiDtos.Query.ImagingReportPageQuery query,
                                                    long offset, int limit) {
        return paginate(listAllImagingReports(tenantId, query), offset, limit);
    }

    @Override
    public ImagingReport upsertImagingReport(Long tenantId,
                                              ClinicalApiDtos.Request.UpsertImagingReportRequest request) {
        ImagingReport report = new ImagingReport();
        report.setTenantId(tenantId);
        report.setPatientId(request.patientId());
        report.setEncounterId(request.encounterId());
        report.setModality(request.modality());
        report.setReportText(request.reportText());
        report.setFileId(request.fileId());
        report.setSourceSystem(request.sourceSystem());
        report.setExaminedAt(request.examinedAt());
        report.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return imagingReportRepository.save(report);
    }

    @Override
    public long countClinicalHistoryEntries(Long tenantId,
                                             ClinicalApiDtos.Query.ClinicalHistoryPageQuery query) {
        return listAllClinicalHistoryEntries(tenantId, query).size();
    }

    @Override
    public List<ClinicalHistoryEntry> listClinicalHistoryEntries(Long tenantId,
                                                                   ClinicalApiDtos.Query.ClinicalHistoryPageQuery query,
                                                                   long offset, int limit) {
        return paginate(listAllClinicalHistoryEntries(tenantId, query), offset, limit);
    }

    @Override
    public ClinicalHistoryEntry recordClinicalHistory(Long tenantId,
                                                       ClinicalApiDtos.Request.RecordClinicalHistoryRequest request) {
        ClinicalHistoryEntry entry = new ClinicalHistoryEntry();
        entry.setTenantId(tenantId);
        entry.setPatientId(request.patientId());
        entry.setEncounterId(request.encounterId());
        entry.setHistoryType(request.historyType());
        entry.setTemplateCode(request.templateCode());
        entry.setContentJson(request.contentJson());
        entry.setSourceType(request.sourceType() != null ? request.sourceType() : "MANUAL");
        entry.setRecordedBy(request.recordedBy());
        entry.setRecordedAt(request.recordedAt() != null ? request.recordedAt() : OffsetDateTime.now(ZoneOffset.UTC));
        entry.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return clinicalHistoryEntryRepository.save(entry);
    }

    @Override
    public IndicatorMapping upsertIndicatorMapping(Long tenantId,
                                                    ClinicalApiDtos.Request.UpsertIndicatorMappingRequest request) {
        return indicatorMappingRepository
                .findBySourceSystemAndSourceCode(tenantId, request.sourceSystem(), request.sourceCode())
                .map(existing -> {
                    existing.setSourceName(request.sourceName());
                    existing.setTargetIndicatorCode(request.targetIndicatorCode());
                    existing.setUnitConversionExpr(request.unitConversionExpr());
                    existing.setQualityRule(request.qualityRule());
                    existing.setStatus(request.status() != null ? request.status() : existing.getStatus());
                    return indicatorMappingRepository.update(existing);
                })
                .orElseGet(() -> {
                    IndicatorMapping mapping = new IndicatorMapping();
                    mapping.setTenantId(tenantId);
                    mapping.setSourceSystem(request.sourceSystem());
                    mapping.setSourceCode(request.sourceCode());
                    mapping.setSourceName(request.sourceName());
                    mapping.setTargetIndicatorCode(request.targetIndicatorCode());
                    mapping.setUnitConversionExpr(request.unitConversionExpr());
                    mapping.setQualityRule(request.qualityRule());
                    mapping.setStatus(request.status() != null ? request.status() : "ACTIVE");
                    mapping.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
                    return indicatorMappingRepository.save(mapping);
                });
    }

    private List<LabResult> listAllLabResults(Long tenantId, ClinicalApiDtos.Query.LabResultPageQuery query) {
        List<LabResult> list;
        if (query.patientId() != null) {
            list = labResultRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.encounterId() != null) {
            list = labResultRepository.listByEncounterId(tenantId, query.encounterId());
        } else {
            list = labResultRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(r -> query.indicatorCode() == null || query.indicatorCode().equals(r.getIndicatorCode()))
                .filter(r -> query.abnormalFlag() == null || query.abnormalFlag().equals(r.getAbnormalFlag()))
                .filter(r -> query.collectedFrom() == null || (r.getCollectedAt() != null && !r.getCollectedAt().isBefore(query.collectedFrom())))
                .filter(r -> query.collectedTo() == null || (r.getCollectedAt() != null && !r.getCollectedAt().isAfter(query.collectedTo())))
                .toList();
    }

    private List<GeneticReport> listAllGeneticReports(Long tenantId,
                                                        ClinicalApiDtos.Query.GeneticReportPageQuery query) {
        List<GeneticReport> list;
        if (query.patientId() != null) {
            list = geneticReportRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.encounterId() != null) {
            list = geneticReportRepository.listByEncounterId(tenantId, query.encounterId());
        } else {
            list = geneticReportRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(r -> query.parseStatus() == null || query.parseStatus().equals(r.getParseStatus()))
                .filter(r -> query.conclusion() == null || query.conclusion().equals(r.getConclusion()))
                .toList();
    }

    private List<ImagingReport> listAllImagingReports(Long tenantId,
                                                        ClinicalApiDtos.Query.ImagingReportPageQuery query) {
        List<ImagingReport> list;
        if (query.patientId() != null) {
            list = imagingReportRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.encounterId() != null) {
            list = imagingReportRepository.listByEncounterId(tenantId, query.encounterId());
        } else {
            list = imagingReportRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(r -> query.modality() == null || query.modality().equals(r.getModality()))
                .filter(r -> query.examinedFrom() == null || (r.getExaminedAt() != null && !r.getExaminedAt().isBefore(query.examinedFrom())))
                .filter(r -> query.examinedTo() == null || (r.getExaminedAt() != null && !r.getExaminedAt().isAfter(query.examinedTo())))
                .toList();
    }

    private List<ClinicalHistoryEntry> listAllClinicalHistoryEntries(Long tenantId,
                                                                       ClinicalApiDtos.Query.ClinicalHistoryPageQuery query) {
        List<ClinicalHistoryEntry> list;
        if (query.patientId() != null) {
            list = clinicalHistoryEntryRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.encounterId() != null) {
            list = clinicalHistoryEntryRepository.listByEncounterId(tenantId, query.encounterId());
        } else {
            list = clinicalHistoryEntryRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(e -> query.historyType() == null || query.historyType().equals(e.getHistoryType()))
                .filter(e -> query.recordedFrom() == null || (e.getRecordedAt() != null && !e.getRecordedAt().isBefore(query.recordedFrom())))
                .filter(e -> query.recordedTo() == null || (e.getRecordedAt() != null && !e.getRecordedAt().isAfter(query.recordedTo())))
                .toList();
    }

    private <T> List<T> paginate(List<T> list, long offset, int limit) {
        int from = (int) Math.min(offset, list.size());
        int to = (int) Math.min(offset + limit, list.size());
        return list.subList(from, to);
    }
}
