package xenosoft.imldintelligence.module.diagnoses.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.diagnoses.internal.model.ModelRegistry;

/**
 * 模型注册 MyBatis Mapper，定义模型注册的数据读写映射。
 */
@Mapper
public interface ModelRegistryMapper extends BaseMapper<ModelRegistry> {
}
