package xenosoft.imldintelligence.module.notify.internal.api.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

/**
 * 通知模块 DTO 分类目录。
 *
 * <p>契约按消息编排与回执跟踪拆分，避免直接泄露底层渠道 SDK 结构。</p>
 */
public final class NotifyApiDtos {
    private NotifyApiDtos() {
    }

    /**
     * 查询类 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 消息检索条件。
         */
        public record NotificationMessagePageQuery(
                String bizType,
                String bizId,
                String receiverType,
                @Positive(message = "receiverRefId must be positive")
                Long receiverRefId,
                String status,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime scheduledFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime scheduledTo
        ) {
        }

        /**
         * 送达回执检索条件。
         */
        public record NotificationDeliveryPageQuery(
                @Positive(message = "messageId must be positive")
                Long messageId,
                String providerName,
                String deliveryStatus,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime deliveredFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime deliveredTo
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
         * 创建通知消息。
         */
        public record CreateNotificationMessageRequest(
                @NotBlank(message = "bizType must not be blank")
                @Size(max = 64, message = "bizType must be at most 64 characters")
                String bizType,
                @NotBlank(message = "bizId must not be blank")
                @Size(max = 128, message = "bizId must be at most 128 characters")
                String bizId,
                @NotBlank(message = "receiverType must not be blank")
                @Size(max = 32, message = "receiverType must be at most 32 characters")
                String receiverType,
                @Positive(message = "receiverRefId must be positive")
                Long receiverRefId,
                @NotBlank(message = "title must not be blank")
                @Size(max = 128, message = "title must be at most 128 characters")
                String title,
                @NotBlank(message = "content must not be blank")
                String content,
                @NotBlank(message = "channel must not be blank")
                @Size(max = 32, message = "channel must be at most 32 characters")
                String channel,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime scheduledAt
        ) {
        }

        /**
         * 记录送达回调。
         */
        public record RecordDeliveryCallbackRequest(
                @Positive(message = "messageId must be positive")
                Long messageId,
                @NotBlank(message = "providerName must not be blank")
                @Size(max = 64, message = "providerName must be at most 64 characters")
                String providerName,
                @Size(max = 128, message = "providerMessageId must be at most 128 characters")
                String providerMessageId,
                @NotBlank(message = "deliveryStatus must not be blank")
                @Size(max = 32, message = "deliveryStatus must be at most 32 characters")
                String deliveryStatus,
                @Size(max = 500, message = "failReason must be at most 500 characters")
                String failReason,
                JsonNode callbackPayload,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime deliveredAt
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
         * 通知消息响应。
         */
        public record NotificationMessageResponse(
                Long id,
                String bizType,
                String bizId,
                Shared.MessageRoutingItem routing,
                String title,
                String content,
                String status,
                OffsetDateTime scheduledAt,
                OffsetDateTime sentAt,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 送达回执响应。
         */
        public record NotificationDeliveryResponse(
                Long id,
                Long messageId,
                String providerName,
                String providerMessageId,
                String deliveryStatus,
                String failReason,
                JsonNode callbackPayload,
                OffsetDateTime deliveredAt,
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
         * 消息路由片段。
         */
        public record MessageRoutingItem(
                String receiverType,
                Long receiverRefId,
                String channel
        ) {
        }
    }
}
