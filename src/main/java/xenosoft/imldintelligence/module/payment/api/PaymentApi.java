package xenosoft.imldintelligence.module.payment.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.payment.api.dto.PaymentApiDtos;

/**
 * 支付模块 HTTP 契约。
 *
 * <p>契约用于承接会员套餐、订单和订阅三段式流程，不包含第三方支付网关细节实现。</p>
 */
@Validated
@RequestMapping({"/api/v1/payment", "/api/v1/app/payment", "/api/v1/web/payment"})
public interface PaymentApi {

    /**
     * 分页查询会员套餐。
     */
    @GetMapping("/plans")
    ApiResponse<PagedResultResponse<PaymentApiDtos.Response.VipPlanResponse>> listPlans(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute PaymentApiDtos.Query.VipPlanPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 分页查询会员订单。
     */
    @GetMapping("/orders")
    ApiResponse<PagedResultResponse<PaymentApiDtos.Response.VipOrderResponse>> listOrders(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute PaymentApiDtos.Query.VipOrderPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 创建会员订单。
     */
    @PostMapping("/orders")
    ApiResponse<PaymentApiDtos.Response.VipOrderResponse> createOrder(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody PaymentApiDtos.Request.CreateVipOrderRequest request
    );

    /**
     * 标记订单支付成功。
     */
    @PostMapping("/orders/payment-confirmations")
    ApiResponse<PaymentApiDtos.Response.VipOrderResponse> markOrderPaid(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody PaymentApiDtos.Request.MarkVipOrderPaidRequest request
    );

    /**
     * 分页查询会员订阅。
     */
    @GetMapping("/subscriptions")
    ApiResponse<PagedResultResponse<PaymentApiDtos.Response.VipSubscriptionResponse>> listSubscriptions(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute PaymentApiDtos.Query.VipSubscriptionPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 激活会员订阅。
     */
    @PostMapping("/subscriptions")
    ApiResponse<PaymentApiDtos.Response.VipSubscriptionResponse> activateSubscription(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody PaymentApiDtos.Request.ActivateSubscriptionRequest request
    );
}
