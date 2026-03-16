package xenosoft.imldintelligence.module.notify.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.notify.internal.model.NotificationDelivery;

/**
 * 通知投递记录 MyBatis Mapper，定义通知投递记录的数据读写映射。
 */
@Mapper
public interface NotificationDeliveryMapper extends BaseMapper<NotificationDelivery> {
}
