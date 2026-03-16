package xenosoft.imldintelligence.module.screening.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.screening.internal.model.TocClinicalTransfer;

/**
 * TOC临床转化 MyBatis Mapper，定义TOC临床转化的数据读写映射。
 */
@Mapper
public interface TocClinicalTransferMapper extends BaseMapper<TocClinicalTransfer> {
}
