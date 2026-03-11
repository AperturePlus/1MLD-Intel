package xenosoft.imldintelligence.module.integration.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

/**
 * 集成模块 DTO 分类目录。
 *
 * <p>该模块承担受控出域与第三方任务编排，因此响应默认只暴露摘要信息，不直接回传原始跨域载荷。</p>
 */
public final class IntegrationApiDtos {
    private IntegrationApiDtos() {
    }

    /**
     * 查询类 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 集成任务检索条件。
         */
        public record IntegrationJobPageQuery(
                String sourceSystem,
                String direction,
                String bizType,
                String status,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime startedFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime startedTo
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
         * 创建集成任务。
         */
        public record CreateIntegrationJobRequest(
                @NotBlank(message = "sourceSystem must not be blank")
                @Size(max = 64, message = "sourceSystem must be at most 64 characters")
                String sourceSystem,
                @NotBlank(message = "direction must not be blank")
                @Size(max = 32, message = "direction must be at most 32 characters")
                String direction,
                @NotBlank(message = "bizType must not be blank")
                @Size(max = 64, message = "bizType must be at most 64 characters")
                String bizType,
                JsonNode requestPayload,
                @Size(max = 128, message = "idempotencyKey must be at most 128 characters")
                String idempotencyKey,
                @Size(max = 500, message = "egressJustification must be at most 500 characters")
                String egressJustification
        ) {
        }

        /**
         * 重试集成任务。
         */
        public record RetryIntegrationJobRequest(
                @Positive(message = "jobId must be positive")
                Long jobId,
                @Positive(message = "operatorId must be positive")
                Long operatorId,
                @Size(max = 500, message = "reason must be at most 500 characters")
                String reason
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
         * 集成任务响应。
         */
        public record IntegrationJobResponse(
                Long id,
                String jobNo,
                String sourceSystem,
                String direction,
                String bizType,
                String status,
                String errorMessage,
                OffsetDateTime startedAt,
                OffsetDateTime finishedAt,
                OffsetDateTime createdAt,
                Shared.PayloadDigestSummary requestDigest,
                Shared.PayloadDigestSummary responseDigest
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
         * 跨域载荷摘要片段。
         */
        public record PayloadDigestSummary(
                String sha256,
                Integer sizeBytes,
                boolean redacted
        ) {
        }
    }
}
