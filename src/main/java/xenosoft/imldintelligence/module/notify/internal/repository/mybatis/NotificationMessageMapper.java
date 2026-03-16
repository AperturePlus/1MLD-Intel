package xenosoft.imldintelligence.module.notify.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.notify.internal.model.NotificationMessage;

/**
 * 通知消息 MyBatis Mapper，定义通知消息的数据读写映射。
 */
@Mapper
public interface NotificationMessageMapper extends BaseMapper<NotificationMessage> {
}
