package xenosoft.imldintelligence.module.clinical.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import xenosoft.imldintelligence.common.CheckPermission;
import xenosoft.imldintelligence.common.RequireAnyRole;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.clinical.api.dto.ClinicalApiDtos;
import xenosoft.imldintelligence.module.clinical.internal.model.ClinicalHistoryEntry;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticReport;
import xenosoft.imldintelligence.module.clinical.internal.model.ImagingReport;
import xenosoft.imldintelligence.module.clinical.internal.model.IndicatorMapping;
import xenosoft.imldintelligence.module.clinical.internal.model.LabResult;
import xenosoft.imldintelligence.module.clinical.internal.repository.GeneticVariantRepository;
import xenosoft.imldintelligence.module.clinical.internal.service.ClinicalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClinicalController implements ClinicalControllerContract {

    private final ClinicalService clinicalService;
    private final GeneticVariantRepository geneticVariantRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Override
    @CheckPermission(resource = "CLINICAL", action = "READ")
    public ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.LabResultResponse>> listLabResults(
            Long tenantId,
            ClinicalApiDtos.Query.LabResultPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = clinicalService.countLabResults(tenantId, query);
        List<LabResult> items = clinicalService.listLabResults(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toLabResultResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "CREATE")
    public ApiResponse<ClinicalApiDtos.Response.LabResultResponse> upsertLabResult(
            Long tenantId,
            ClinicalApiDtos.Request.UpsertLabResultRequest request) {
        LabResult result = clinicalService.upsertLabResult(tenantId, request);
        return ApiResponse.success(toLabResultResponse(result));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "READ")
    public ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.GeneticReportResponse>> listGeneticReports(
            Long tenantId,
            ClinicalApiDtos.Query.GeneticReportPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = clinicalService.countGeneticReports(tenantId, query);
        List<GeneticReport> items = clinicalService.listGeneticReports(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(r -> toGeneticReportResponse(tenantId, r)).toList()));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "CREATE")
    public ApiResponse<ClinicalApiDtos.Response.GeneticReportResponse> registerGeneticReport(
            Long tenantId,
            ClinicalApiDtos.Request.RegisterGeneticReportRequest request) {
        GeneticReport report = clinicalService.registerGeneticReport(tenantId, request);
        return ApiResponse.success(toGeneticReportResponse(tenantId, report));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "READ")
    public ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.ImagingReportResponse>> listImagingReports(
            Long tenantId,
            ClinicalApiDtos.Query.ImagingReportPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = clinicalService.countImagingReports(tenantId, query);
        List<ImagingReport> items = clinicalService.listImagingReports(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toImagingReportResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "CREATE")
    public ApiResponse<ClinicalApiDtos.Response.ImagingReportResponse> upsertImagingReport(
            Long tenantId,
            ClinicalApiDtos.Request.UpsertImagingReportRequest request) {
        ImagingReport report = clinicalService.upsertImagingReport(tenantId, request);
        return ApiResponse.success(toImagingReportResponse(report));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "READ")
    public ApiResponse<PagedResultResponse<ClinicalApiDtos.Response.ClinicalHistoryEntryResponse>> listClinicalHistoryEntries(
            Long tenantId,
            ClinicalApiDtos.Query.ClinicalHistoryPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = clinicalService.countClinicalHistoryEntries(tenantId, query);
        List<ClinicalHistoryEntry> items = clinicalService.listClinicalHistoryEntries(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toClinicalHistoryEntryResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "CLINICAL", action = "CREATE")
    public ApiResponse<ClinicalApiDtos.Response.ClinicalHistoryEntryResponse> recordClinicalHistory(
            Long tenantId,
            ClinicalApiDtos.Request.RecordClinicalHistoryRequest request) {
        ClinicalHistoryEntry entry = clinicalService.recordClinicalHistory(tenantId, request);
        return ApiResponse.success(toClinicalHistoryEntryResponse(entry));
    }

    @Override
    @RequireAnyRole({"SYSTEM_ADMIN", "DATA_ADMIN"})
    public ApiResponse<ClinicalApiDtos.Response.IndicatorMappingResponse> upsertIndicatorMapping(
            Long tenantId,
            ClinicalApiDtos.Request.UpsertIndicatorMappingRequest request) {
        IndicatorMapping mapping = clinicalService.upsertIndicatorMapping(tenantId, request);
        return ApiResponse.success(toIndicatorMappingResponse(mapping));
    }

    private ClinicalApiDtos.Response.LabResultResponse toLabResultResponse(LabResult r) {
        return new ClinicalApiDtos.Response.LabResultResponse(
                r.getId(), r.getPatientId(), r.getEncounterId(), r.getIndicatorCode(),
                r.getValueNumeric(), r.getValueText(), r.getUnit(),
                r.getReferenceLow(), r.getReferenceHigh(), r.getAbnormalFlag(),
                r.getSourceType(), r.getCollectedAt(), r.getCreatedAt());
    }

    private ClinicalApiDtos.Response.GeneticReportResponse toGeneticReportResponse(Long tenantId,
                                                                                     GeneticReport r) {
        List<ClinicalApiDtos.Shared.GeneticVariantItem> variants = geneticVariantRepository
                .listByReportId(tenantId, r.getId()).stream()
                .map(v -> new ClinicalApiDtos.Shared.GeneticVariantItem(
                        v.getId(), v.getGene(), v.getChromosome(), v.getPosition(),
                        v.getRefAllele(), v.getAltAllele(), v.getVariantType(),
                        v.getZygosity(), v.getClassification(), v.getHgvsC(),
                        v.getHgvsP(), v.getEvidence(), v.getSourceType()))
                .toList();
        return new ClinicalApiDtos.Response.GeneticReportResponse(
                r.getId(), r.getPatientId(), r.getEncounterId(), r.getReportSource(),
                r.getReportDate(), r.getFileId(), r.getParseStatus(),
                r.getSummary(), r.getConclusion(), r.getCreatedAt(), variants);
    }

    private ClinicalApiDtos.Response.ImagingReportResponse toImagingReportResponse(ImagingReport r) {
        return new ClinicalApiDtos.Response.ImagingReportResponse(
                r.getId(), r.getPatientId(), r.getEncounterId(), r.getModality(),
                r.getReportText(), r.getFileId(), r.getSourceSystem(),
                r.getExaminedAt(), r.getCreatedAt());
    }

    private ClinicalApiDtos.Response.ClinicalHistoryEntryResponse toClinicalHistoryEntryResponse(
            ClinicalHistoryEntry e) {
        return new ClinicalApiDtos.Response.ClinicalHistoryEntryResponse(
                e.getId(), e.getPatientId(), e.getEncounterId(), e.getHistoryType(),
                e.getTemplateCode(), e.getContentJson(), e.getSourceType(),
                e.getRecordedBy(), e.getRecordedAt(), e.getCreatedAt());
    }

    private ClinicalApiDtos.Response.IndicatorMappingResponse toIndicatorMappingResponse(IndicatorMapping m) {
        return new ClinicalApiDtos.Response.IndicatorMappingResponse(
                m.getId(), m.getSourceSystem(), m.getSourceCode(), m.getSourceName(),
                m.getTargetIndicatorCode(), m.getUnitConversionExpr(),
                m.getQualityRule(), m.getStatus(), m.getCreatedAt());
    }
}
