package xenosoft.imldintelligence.module.audit.internal.api.dto;

import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

/**
 * 审计模块查询 DTO 分类目录。
 *
 * <p>审计模块已经存在运行中的查询接口，因此这里只补充查询参数分类，
 * 保持现有响应 DTO 不发生破坏性调整。</p>
 */
public final class AuditQueryDtos {
    private AuditQueryDtos() {
    }

    /**
     * 查询条件 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 审计日志检索条件。
         */
        public record AuditLogPageQuery(
                @Positive(message = "userId must be positive")
                Long userId,
                String action,
                String resourceType,
                String resourceId,
                String traceId,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime from,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime to
        ) {
        }

        /**
         * 敏感访问日志检索条件。
         */
        public record SensitiveAccessLogPageQuery(
                @Positive(message = "userId must be positive")
                Long userId,
                String sensitiveType,
                String resourceType,
                String resourceId,
                String accessResult,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime from,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime to
        ) {
        }

        /**
         * 模型调用日志检索条件。
         */
        public record ModelInvocationLogPageQuery(
                @Positive(message = "sessionId must be positive")
                Long sessionId,
                @Positive(message = "modelRegistryId must be positive")
                Long modelRegistryId,
                String provider,
                String requestId,
                String status,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime from,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime to
        ) {
        }
    }
}
