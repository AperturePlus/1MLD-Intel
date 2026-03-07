package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.diagnoses.internal.model.ModelRegistry;

import java.util.List;

/**
 * 模型注册 MyBatis Mapper，定义模型注册的数据读写映射。
 */
@Mapper
public interface ModelRegistryMapper {
    ModelRegistry findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    ModelRegistry findByModelCodeAndModelVersion(@Param("tenantId") Long tenantId,
                                                 @Param("modelCode") String modelCode,
                                                 @Param("modelVersion") String modelVersion);

    List<ModelRegistry> listByTenantId(@Param("tenantId") Long tenantId);

    int insert(ModelRegistry modelRegistry);

    int update(ModelRegistry modelRegistry);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
