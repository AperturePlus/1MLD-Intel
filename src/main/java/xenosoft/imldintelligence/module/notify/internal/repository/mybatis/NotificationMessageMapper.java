package xenosoft.imldintelligence.module.notify.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.notify.internal.model.NotificationMessage;

import java.util.List;

/**
 * 通知消息 MyBatis Mapper，定义通知消息的数据读写映射。
 */
@Mapper
public interface NotificationMessageMapper {
    NotificationMessage findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    List<NotificationMessage> listByTenantId(@Param("tenantId") Long tenantId);

    List<NotificationMessage> listByReceiver(@Param("tenantId") Long tenantId,
                                             @Param("receiverType") String receiverType,
                                             @Param("receiverRefId") Long receiverRefId);

    int insert(NotificationMessage notificationMessage);

    int update(NotificationMessage notificationMessage);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
