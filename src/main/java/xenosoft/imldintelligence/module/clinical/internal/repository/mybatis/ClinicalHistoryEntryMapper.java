package xenosoft.imldintelligence.module.clinical.internal.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xenosoft.imldintelligence.module.clinical.internal.model.ClinicalHistoryEntry;

import java.util.List;

/**
 * 临床病史条目 MyBatis Mapper，定义临床病史条目的数据读写映射。
 */
@Mapper
public interface ClinicalHistoryEntryMapper {
    ClinicalHistoryEntry findById(@Param("tenantId") Long tenantId, @Param("id") Long id);

    List<ClinicalHistoryEntry> listByTenantId(@Param("tenantId") Long tenantId);

    List<ClinicalHistoryEntry> listByPatientId(@Param("tenantId") Long tenantId, @Param("patientId") Long patientId);

    List<ClinicalHistoryEntry> listByEncounterId(@Param("tenantId") Long tenantId, @Param("encounterId") Long encounterId);

    int insert(ClinicalHistoryEntry clinicalHistoryEntry);

    int update(ClinicalHistoryEntry clinicalHistoryEntry);

    int deleteById(@Param("tenantId") Long tenantId, @Param("id") Long id);
}
