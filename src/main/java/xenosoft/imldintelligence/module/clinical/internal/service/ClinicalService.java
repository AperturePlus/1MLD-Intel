package xenosoft.imldintelligence.module.clinical.internal.service;

import xenosoft.imldintelligence.module.clinical.api.dto.ClinicalApiDtos;
import xenosoft.imldintelligence.module.clinical.internal.model.ClinicalHistoryEntry;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticReport;
import xenosoft.imldintelligence.module.clinical.internal.model.ImagingReport;
import xenosoft.imldintelligence.module.clinical.internal.model.IndicatorMapping;
import xenosoft.imldintelligence.module.clinical.internal.model.LabResult;

import java.util.List;

/**
 * 临床数据业务服务接口。
 */
public interface ClinicalService {

    long countLabResults(Long tenantId, ClinicalApiDtos.Query.LabResultPageQuery query);

    List<LabResult> listLabResults(Long tenantId, ClinicalApiDtos.Query.LabResultPageQuery query,
                                   long offset, int limit);

    LabResult upsertLabResult(Long tenantId, ClinicalApiDtos.Request.UpsertLabResultRequest request);

    long countGeneticReports(Long tenantId, ClinicalApiDtos.Query.GeneticReportPageQuery query);

    List<GeneticReport> listGeneticReports(Long tenantId, ClinicalApiDtos.Query.GeneticReportPageQuery query,
                                            long offset, int limit);

    GeneticReport registerGeneticReport(Long tenantId,
                                         ClinicalApiDtos.Request.RegisterGeneticReportRequest request);

    long countImagingReports(Long tenantId, ClinicalApiDtos.Query.ImagingReportPageQuery query);

    List<ImagingReport> listImagingReports(Long tenantId, ClinicalApiDtos.Query.ImagingReportPageQuery query,
                                            long offset, int limit);

    ImagingReport upsertImagingReport(Long tenantId, ClinicalApiDtos.Request.UpsertImagingReportRequest request);

    long countClinicalHistoryEntries(Long tenantId, ClinicalApiDtos.Query.ClinicalHistoryPageQuery query);

    List<ClinicalHistoryEntry> listClinicalHistoryEntries(Long tenantId,
                                                           ClinicalApiDtos.Query.ClinicalHistoryPageQuery query,
                                                           long offset, int limit);

    ClinicalHistoryEntry recordClinicalHistory(Long tenantId,
                                                ClinicalApiDtos.Request.RecordClinicalHistoryRequest request);

    IndicatorMapping upsertIndicatorMapping(Long tenantId,
                                             ClinicalApiDtos.Request.UpsertIndicatorMappingRequest request);
}
