package xenosoft.imldintelligence.module.payment.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.payment.internal.model.VipOrder;

import java.util.List;

/**
 * VIP订单 MyBatis Mapper，定义VIP订单的数据读写映射。
 */
@Mapper
public interface VipOrderMapper {
    VipOrder findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    VipOrder findByOrderNo(@Param("tenantId") Long tenantId, @Param("orderNo") String orderNo);

    List<VipOrder> listByTenantId(@Param("tenantId") Long tenantId);

    List<VipOrder> listByTocUserId(@Param("tenantId") Long tenantId, @Param("tocUserId") Long tocUserId);

    int insert(VipOrder vipOrder);

    int update(VipOrder vipOrder);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
