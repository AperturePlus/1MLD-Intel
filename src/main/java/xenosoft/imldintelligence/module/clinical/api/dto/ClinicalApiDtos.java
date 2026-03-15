package xenosoft.imldintelligence.module.clinical.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * 临床数据模块 DTO 分类目录。
 *
 * <p>该模块承接患者关键证据数据，接口设计优先保证数据溯源、时间语义和原始数据最小暴露。</p>
 */
public final class ClinicalApiDtos {
    private ClinicalApiDtos() {
    }

    /**
     * 查询类 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 检验结果检索条件。
         */
        public record LabResultPageQuery(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                String indicatorCode,
                String abnormalFlag,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime collectedFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime collectedTo
        ) {
        }

        /**
         * 基因报告检索条件。
         */
        public record GeneticReportPageQuery(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                String parseStatus,
                String conclusion
        ) {
        }

        /**
         * 影像报告检索条件。
         */
        public record ImagingReportPageQuery(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                String modality,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime examinedFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime examinedTo
        ) {
        }

        /**
         * 病史条目检索条件。
         */
        public record ClinicalHistoryPageQuery(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                String historyType,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime recordedFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime recordedTo
        ) {
        }
    }

    /**
     * 写入类 DTO。
     */
    public static final class Request {
        private Request() {
        }

        /**
         * 新增或修正检验结果。
         */
        public record UpsertLabResultRequest(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @NotBlank(message = "indicatorCode must not be blank")
                @Size(max = 64, message = "indicatorCode must be at most 64 characters")
                String indicatorCode,
                Double valueNumeric,
                String valueText,
                String unit,
                Double referenceLow,
                Double referenceHigh,
                String abnormalFlag,
                String sourceType,
                JsonNode rawData,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime collectedAt
        ) {
        }

        /**
         * 登记基因报告。
         */
        public record RegisterGeneticReportRequest(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @NotBlank(message = "reportSource must not be blank")
                @Size(max = 128, message = "reportSource must be at most 128 characters")
                String reportSource,
                LocalDate reportDate,
                Long fileId,
                String summary,
                String conclusion
        ) {
        }

        /**
         * 登记影像报告。
         */
        public record UpsertImagingReportRequest(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @NotBlank(message = "modality must not be blank")
                @Size(max = 64, message = "modality must be at most 64 characters")
                String modality,
                @NotBlank(message = "reportText must not be blank")
                String reportText,
                Long fileId,
                String sourceSystem,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime examinedAt
        ) {
        }

        /**
         * 记录病史条目。
         */
        public record RecordClinicalHistoryRequest(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @NotBlank(message = "historyType must not be blank")
                @Size(max = 64, message = "historyType must be at most 64 characters")
                String historyType,
                String templateCode,
                JsonNode contentJson,
                String sourceType,
                Long recordedBy,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime recordedAt
        ) {
        }

        /**
         * 配置指标映射。
         */
        public record UpsertIndicatorMappingRequest(
                @NotBlank(message = "sourceSystem must not be blank")
                @Size(max = 64, message = "sourceSystem must be at most 64 characters")
                String sourceSystem,
                @NotBlank(message = "sourceCode must not be blank")
                @Size(max = 128, message = "sourceCode must be at most 128 characters")
                String sourceCode,
                String sourceName,
                @NotBlank(message = "targetIndicatorCode must not be blank")
                @Size(max = 64, message = "targetIndicatorCode must be at most 64 characters")
                String targetIndicatorCode,
                String unitConversionExpr,
                JsonNode qualityRule,
                String status
        ) {
        }
    }

    /**
     * 响应类 DTO。
     */
    public static final class Response {
        private Response() {
        }

        /**
         * 检验结果响应。
         */
        public record LabResultResponse(
                Long id,
                Long patientId,
                Long encounterId,
                String indicatorCode,
                Double valueNumeric,
                String valueText,
                String unit,
                Double referenceLow,
                Double referenceHigh,
                String abnormalFlag,
                String sourceType,
                OffsetDateTime collectedAt,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 基因报告响应。
         */
        public record GeneticReportResponse(
                Long id,
                Long patientId,
                Long encounterId,
                String reportSource,
                LocalDate reportDate,
                Long fileId,
                String parseStatus,
                String summary,
                String conclusion,
                OffsetDateTime createdAt,
                List<Shared.GeneticVariantItem> variants
        ) {
        }

        /**
         * 影像报告响应。
         */
        public record ImagingReportResponse(
                Long id,
                Long patientId,
                Long encounterId,
                String modality,
                String reportText,
                Long fileId,
                String sourceSystem,
                OffsetDateTime examinedAt,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 病史条目响应。
         */
        public record ClinicalHistoryEntryResponse(
                Long id,
                Long patientId,
                Long encounterId,
                String historyType,
                String templateCode,
                JsonNode contentJson,
                String sourceType,
                Long recordedBy,
                OffsetDateTime recordedAt,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 指标映射响应。
         */
        public record IndicatorMappingResponse(
                Long id,
                String sourceSystem,
                String sourceCode,
                String sourceName,
                String targetIndicatorCode,
                String unitConversionExpr,
                JsonNode qualityRule,
                String status,
                OffsetDateTime createdAt
        ) {
        }
    }

    /**
     * 共享片段 DTO。
     */
    public static final class Shared {
        private Shared() {
        }

        /**
         * 基因位点片段。
         */
        public record GeneticVariantItem(
                Long id,
                String gene,
                String chromosome,
                Long position,
                String refAllele,
                String altAllele,
                String variantType,
                String zygosity,
                String classification,
                String hgvsC,
                String hgvsP,
                String evidence,
                String sourceType
        ) {
        }
    }
}
