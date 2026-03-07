package xenosoft.imldintelligence.module.notify.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.notify.internal.model.NotificationMessage;
import xenosoft.imldintelligence.module.notify.internal.repository.NotificationMessageRepository;
import xenosoft.imldintelligence.module.notify.internal.repository.mybatis.NotificationMessageMapper;

import java.util.List;
import java.util.Optional;

/**
 * 通知消息仓储实现类，基于 MyBatis Mapper 完成通知消息的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class NotificationMessageRepositoryImpl implements NotificationMessageRepository {
    private final NotificationMessageMapper notificationMessageMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<NotificationMessage> findById(Long tenantId, Long id) {
        return Optional.ofNullable(notificationMessageMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationMessage> listByTenantId(Long tenantId) {
        return notificationMessageMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NotificationMessage> listByReceiver(Long tenantId, String receiverType, Long receiverRefId) {
        return notificationMessageMapper.listByReceiver(tenantId, receiverType, receiverRefId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationMessage save(NotificationMessage notificationMessage) {
        notificationMessageMapper.insert(notificationMessage);
        return notificationMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationMessage update(NotificationMessage notificationMessage) {
        notificationMessageMapper.update(notificationMessage);
        return notificationMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return notificationMessageMapper.deleteById(tenantId, id) > 0;
    }
}
