package xenosoft.imldintelligence.module.payment.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * 支付模块 DTO 分类目录。
 *
 * <p>该模块服务于 ToC 会员权益闭环，接口设计区分套餐、订单、订阅三类状态对象。</p>
 */
public final class PaymentApiDtos {
    private PaymentApiDtos() {
    }

    /**
     * 查询类 DTO。
     */
    public static final class Query {
        private Query() {
        }

        /**
         * 会员套餐检索条件。
         */
        public record VipPlanPageQuery(
                String status,
                String currencyCode
        ) {
        }

        /**
         * 会员订单检索条件。
         */
        public record VipOrderPageQuery(
                @Positive(message = "tocUserId must be positive")
                Long tocUserId,
                @Positive(message = "planId must be positive")
                Long planId,
                String orderStatus,
                String channel,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime paidFrom,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime paidTo
        ) {
        }

        /**
         * 会员订阅检索条件。
         */
        public record VipSubscriptionPageQuery(
                @Positive(message = "tocUserId must be positive")
                Long tocUserId,
                @Positive(message = "planId must be positive")
                Long planId,
                String subscriptionStatus,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime activeAt
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
         * 创建会员订单。
         */
        public record CreateVipOrderRequest(
                @Positive(message = "tocUserId must be positive")
                Long tocUserId,
                @Positive(message = "planId must be positive")
                Long planId,
                BigDecimal amount,
                @NotBlank(message = "channel must not be blank")
                @Size(max = 32, message = "channel must be at most 32 characters")
                String channel
        ) {
        }

        /**
         * 记录订单支付。
         */
        public record MarkVipOrderPaidRequest(
                @Positive(message = "orderId must be positive")
                Long orderId,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime paidAt,
                @NotBlank(message = "channel must not be blank")
                @Size(max = 32, message = "channel must be at most 32 characters")
                String channel,
                @Size(max = 128, message = "providerTradeNo must be at most 128 characters")
                String providerTradeNo
        ) {
        }

        /**
         * 激活会员订阅。
         */
        public record ActivateSubscriptionRequest(
                @Positive(message = "orderId must be positive")
                Long orderId,
                @Positive(message = "tocUserId must be positive")
                Long tocUserId,
                @Positive(message = "planId must be positive")
                Long planId,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime startAt,
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                OffsetDateTime endAt
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
         * 会员套餐响应。
         */
        public record VipPlanResponse(
                Long id,
                String planCode,
                String planName,
                Integer durationDays,
                BigDecimal priceAmount,
                String currencyCode,
                String status,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 会员订单响应。
         */
        public record VipOrderResponse(
                Long id,
                String orderNo,
                Long tocUserId,
                Long planId,
                String orderStatus,
                Shared.MonetaryAmountItem amount,
                OffsetDateTime paidAt,
                String channel,
                OffsetDateTime createdAt
        ) {
        }

        /**
         * 会员订阅响应。
         */
        public record VipSubscriptionResponse(
                Long id,
                Long tocUserId,
                Long planId,
                Long orderId,
                String subscriptionStatus,
                OffsetDateTime startAt,
                OffsetDateTime endAt,
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
         * 金额片段。
         */
        public record MonetaryAmountItem(
                BigDecimal amount,
                String currencyCode
        ) {
        }
    }
}
