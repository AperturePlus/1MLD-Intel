package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.careplan.internal.model.CarePlanTemplate;

import java.util.List;

/**
 * 护理计划模板 MyBatis Mapper，定义护理计划模板的数据读写映射。
 */
@Mapper
public interface CarePlanTemplateMapper {
    CarePlanTemplate findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    CarePlanTemplate findByTemplateCodeAndVersionNo(@Param("tenantId") Long tenantId,
                                                    @Param("templateCode") String templateCode,
                                                    @Param("versionNo") Integer versionNo);

    List<CarePlanTemplate> listByTenantId(@Param("tenantId") Long tenantId);

    int insert(CarePlanTemplate carePlanTemplate);

    int update(CarePlanTemplate carePlanTemplate);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
