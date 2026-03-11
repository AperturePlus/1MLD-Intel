package xenosoft.imldintelligence.module.notify.internal.api;

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
import xenosoft.imldintelligence.module.notify.internal.api.dto.NotifyApiDtos;

/**
 * 通知模块 HTTP 契约。
 *
 * <p>接口聚焦消息编排和送达追踪，不约束具体通知渠道实现。</p>
 */
@Validated
@RequestMapping("/api/v1/notify")
public interface NotifyApi {

    /**
     * 分页查询通知消息。
     */
    @GetMapping("/messages")
    ApiResponse<PagedResultResponse<NotifyApiDtos.Response.NotificationMessageResponse>> listMessages(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute NotifyApiDtos.Query.NotificationMessagePageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 创建通知消息。
     */
    @PostMapping("/messages")
    ApiResponse<NotifyApiDtos.Response.NotificationMessageResponse> createMessage(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody NotifyApiDtos.Request.CreateNotificationMessageRequest request
    );

    /**
     * 分页查询送达回执。
     */
    @GetMapping("/deliveries")
    ApiResponse<PagedResultResponse<NotifyApiDtos.Response.NotificationDeliveryResponse>> listDeliveries(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute NotifyApiDtos.Query.NotificationDeliveryPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 记录送达回调。
     */
    @PostMapping("/deliveries/callbacks")
    ApiResponse<NotifyApiDtos.Response.NotificationDeliveryResponse> recordDeliveryCallback(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody NotifyApiDtos.Request.RecordDeliveryCallbackRequest request
    );
}
