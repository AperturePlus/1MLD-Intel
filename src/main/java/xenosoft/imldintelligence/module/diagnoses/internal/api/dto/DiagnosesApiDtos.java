package xenosoft.imldintelligence.module.diagnoses.internal.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 诊断模块 DTO 分类目录。
 *
 * <p>该模块负责把临床证据转化为可解释结论，因此请求与响应同时保留证据快照和人工反馈边界。</p>
 */
public final class DiagnosesApiDtos {
    private DiagnosesApiDtos() {
    }

    /**
     * 查询类 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 诊断会话检索条件。
         */
        public record SessionPageQuery(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @Positive(message = "doctorId must be positive")
                Long doctorId,
                String triggeredBy,
                String status,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime startedFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime startedTo
        ) {
        }

        /**
         * 模型注册表检索条件。
         */
        public record ModelRegistryPageQuery(
                String provider,
                String modelType,
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
         * 启动诊断会话。
         */
        public record StartDiagnosisSessionRequest(
                @Positive(message = "patientId must be positive")
                Long patientId,
                @Positive(message = "encounterId must be positive")
                Long encounterId,
                @Positive(message = "doctorId must be positive")
                Long doctorId,
                @NotBlank(message = "triggeredBy must not be blank")
                @Size(max = 32, message = "triggeredBy must be at most 32 characters")
                String triggeredBy,
                @Positive(message = "modelRegistryId must be positive")
                Long modelRegistryId,
                JsonNode inputSnapshot
        ) {
        }

        /**
         * 医生反馈请求。
         */
        public record SubmitDoctorFeedbackRequest(
                @Positive(message = "sessionId must be positive")
                Long sessionId,
                @Positive(message = "resultId must be positive")
                Long resultId,
                @Positive(message = "doctorId must be positive")
                Long doctorId,
                @NotBlank(message = "action must not be blank")
                @Size(max = 32, message = "action must be at most 32 characters")
                String action,
                JsonNode modifiedValue,
                String rejectReason
        ) {
        }

        /**
         * 注册模型。
         */
        public record RegisterModelRequest(
                @NotBlank(message = "modelCode must not be blank")
                @Size(max = 64, message = "modelCode must be at most 64 characters")
                String modelCode,
                @NotBlank(message = "modelName must not be blank")
                @Size(max = 128, message = "modelName must be at most 128 characters")
                String modelName,
                @NotBlank(message = "modelType must not be blank")
                @Size(max = 64, message = "modelType must be at most 64 characters")
                String modelType,
                @NotBlank(message = "modelVersion must not be blank")
                @Size(max = 64, message = "modelVersion must be at most 64 characters")
                String modelVersion,
                @NotBlank(message = "provider must not be blank")
                @Size(max = 64, message = "provider must be at most 64 characters")
                String provider,
                String status,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime releasedAt
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
         * 诊断会话响应。
         */
        public record DiagnosisSessionResponse(
                Long id,
                Long patientId,
                Long encounterId,
                Long doctorId,
                String triggeredBy,
                Long modelRegistryId,
                String status,
                OffsetDateTime startedAt,
                OffsetDateTime completedAt,
                List<Shared.DiagnosisResultItem> results,
                List<Shared.DiagnosisRecommendationItem> recommendations,
                List<Shared.DoctorFeedbackItem> feedbacks
        ) {
        }

        /**
         * 模型注册表响应。
         */
        public record ModelRegistryResponse(
                Long id,
                String modelCode,
                String modelName,
                String modelType,
                String modelVersion,
                String provider,
                String status,
                OffsetDateTime releasedAt,
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
         * 诊断结果片段。
         */
        public record DiagnosisResultItem(
                Long id,
                String diseaseCode,
                String diseaseName,
                Double confidence,
                Integer rankNo,
                String riskLevel,
                JsonNode evidenceJson,
                Boolean displayToPatient,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 诊断建议片段。
         */
        public record DiagnosisRecommendationItem(
                Long id,
                String recType,
                String content,
                Integer priority,
                String reason,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 医生反馈片段。
         */
        public record DoctorFeedbackItem(
                Long id,
                Long resultId,
                Long doctorId,
                String action,
                JsonNode modifiedValue,
                String rejectReason,
                OffsetDateTime createdAt
        ) {
        }
    }
}
