package xenosoft.imldintelligence.module.careplan.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.careplan.internal.model.PatientReportedData;

/**
 * 患者上报数据 MyBatis Mapper，定义患者上报数据的数据读写映射。
 */
@Mapper
public interface PatientReportedDataMapper extends BaseMapper<PatientReportedData> {
}
