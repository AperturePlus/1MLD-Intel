package xenosoft.imldintelligence.module.clinical.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.clinical.internal.model.GeneticVariant;

import java.util.List;

/**
 * 基因变异 MyBatis Mapper，定义基因变异的数据读写映射。
 */
@Mapper
public interface GeneticVariantMapper {
    GeneticVariant findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    List<GeneticVariant> listByTenantId(@Param("tenantId") Long tenantId);

    List<GeneticVariant> listByReportId(@Param("tenantId") Long tenantId, @Param("reportId") Long reportId);

    int insert(GeneticVariant geneticVariant);

    int update(GeneticVariant geneticVariant);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
