package xenosoft.imldintelligence.module.careplan.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.careplan.internal.repository.PatientReportedDataRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.mybatis.PatientReportedDataMapper;
import xenosoft.imldintelligence.module.careplan.internal.model.PatientReportedData;

import java.util.List;
import java.util.Optional;

/**
 * 患者上报数据仓储实现类，基于 MyBatis Mapper 完成患者上报数据的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class PatientReportedDataRepositoryImpl implements PatientReportedDataRepository {
    private final PatientReportedDataMapper patientReportedDataMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PatientReportedData> findById(Long tenantId, Long id) {
        return Optional.ofNullable(patientReportedDataMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PatientReportedData> listByTenantId(Long tenantId) {
        return patientReportedDataMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PatientReportedData> listByCarePlanId(Long tenantId, Long carePlanId) {
        return patientReportedDataMapper.listByCarePlanId(tenantId, carePlanId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PatientReportedData> listByPatientId(Long tenantId, Long patientId) {
        return patientReportedDataMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientReportedData save(PatientReportedData patientReportedData) {
        patientReportedDataMapper.insert(patientReportedData);
        return patientReportedData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PatientReportedData update(PatientReportedData patientReportedData) {
        patientReportedDataMapper.update(patientReportedData);
        return patientReportedData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return patientReportedDataMapper.deleteById(tenantId, id) > 0;
    }
}
