package xenosoft.imldintelligence.module.notify.internal.repository;

import xenosoft.imldintelligence.module.notify.internal.model.NotificationDelivery;

import java.util.List;
import java.util.Optional;

/**
 * 通知投递记录仓储接口，负责在租户边界内维护消息投递状态。
 */
public interface NotificationDeliveryRepository {
    /**
     * 按租户和通知投递记录主键查询通知投递记录。
     *
     * @param tenantId 租户标识
     * @param id 通知投递记录主键
     * @return 匹配的通知投递记录，不存在时返回空
     */
    Optional<NotificationDelivery> findById(Long tenantId, Long id);

    /**
     * 查询租户下全部通知投递记录。
     *
     * @param tenantId 租户标识
     * @return 符合条件的通知投递记录列表
     */
    List<NotificationDelivery> listByTenantId(Long tenantId);

    /**
     * 按租户和消息主键查询通知投递记录列表。
     *
     * @param tenantId 租户标识
     * @param messageId 消息主键
     * @return 符合条件的通知投递记录列表
     */
    List<NotificationDelivery> listByMessageId(Long tenantId, Long messageId);

    /**
     * 新增通知投递记录。
     *
     * @param notificationDelivery 待保存的通知投递记录
     * @return 保存后的通知投递记录
     */
    NotificationDelivery save(NotificationDelivery notificationDelivery);

    /**
     * 更新通知投递记录。
     *
     * @param notificationDelivery 待更新的通知投递记录
     * @return 更新后的通知投递记录
     */
    NotificationDelivery update(NotificationDelivery notificationDelivery);

    /**
     * 按租户和通知投递记录主键删除通知投递记录。
     *
     * @param tenantId 租户标识
     * @param id 通知投递记录主键
     * @return 删除成功时返回 {@code true}，否则返回 {@code false}
     */
    Boolean deleteById(Long tenantId, Long id);
}
