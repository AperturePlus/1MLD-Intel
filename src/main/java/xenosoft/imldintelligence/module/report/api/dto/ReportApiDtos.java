package xenosoft.imldintelligence.module.report.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 报告模块 DTO 分类目录。
 *
 * <p>报告模块承接诊断输出文书化过程，因此契约重点覆盖草稿、版本、签署和模板治理。</p>
 */
public final class ReportApiDtos {
    private ReportApiDtos() {
    }

    /**
     * 查询类 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 报告检索条件。
         */
        public record ReportPageQuery(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @Positive(message = "sessionId must be positive")
                Long sessionId,
                String status,
                @Positive(message = "signedBy must be positive")
                Long signedBy,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime createdFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime createdTo
        ) {
        }

        /**
         * 报告模板检索条件。
         */
        public record ReportTemplatePageQuery(
                String diseaseCode,
                String department,
                String status
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
         * 创建报告草稿。
         */
        public record CreateReportDraftRequest(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @Positive(message = "sessionId must be positive")
                Long sessionId,
                @Positive(message = "templateId must be positive")
                Long templateId,
                @Positive(message = "createdBy must be positive")
                Long createdBy,
                JsonNode initialContent
        ) {
        }

        /**
         * 保存报告版本。
         */
        public record SaveReportVersionRequest(
                @Positive(message = "reportId must be positive")
                Long reportId,
                JsonNode contentSnapshot,
                @Size(max = 500, message = "changeSummary must be at most 500 characters")
                String changeSummary,
                @Positive(message = "changedBy must be positive")
                Long changedBy
        ) {
        }

        /**
         * 签署报告。
         */
        public record SignReportRequest(
                @Positive(message = "reportId must be positive")
                Long reportId,
                @Positive(message = "signedBy must be positive")
                Long signedBy,
                JsonNode signatureData
        ) {
        }

        /**
         * 发布报告模板。
         */
        public record PublishTemplateRequest(
                @NotBlank(message = "templateCode must not be blank")
                @Size(max = 64, message = "templateCode must be at most 64 characters")
                String templateCode,
                @NotBlank(message = "templateName must not be blank")
                @Size(max = 128, message = "templateName must be at most 128 characters")
                String templateName,
                String diseaseCode,
                String department,
                JsonNode templateSchema,
                String status,
                Integer versionNo,
                @Positive(message = "createdBy must be positive")
                Long createdBy
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
         * 报告详情响应。
         */
        public record ReportDetailResponse(
                Long id,
                Long patientId,
                Long encounterId,
                Long sessionId,
                Long templateId,
                String reportNo,
                String status,
                Integer currentVersion,
                Long signedBy,
                OffsetDateTime signedAt,
                Long createdBy,
                OffsetDateTime createdAt,
                OffsetDateTime updatedAt,
                List<Shared.ReportVersionItem> versions
        ) {
        }

        /**
         * 报告模板响应。
         */
        public record ReportTemplateResponse(
                Long id,
                String templateCode,
                String templateName,
                String diseaseCode,
                String department,
                JsonNode templateSchema,
                String status,
                Integer versionNo,
                Long createdBy,
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
         * 报告版本片段。
         */
        public record ReportVersionItem(
                Long id,
                Integer versionNum,
                JsonNode contentSnapshot,
                String changeSummary,
                Long changedBy,
                OffsetDateTime createdAt
        ) {
        }
    }
}
