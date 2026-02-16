package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.PatientExternalId;

import java.util.List;
import java.util.Optional;

public interface PatientExternalIdRepository {
    Optional<PatientExternalId> findById(Long tenantId, Long id);

    Optional<PatientExternalId> findByIdTypeAndIdValue(Long tenantId, String idType, String idValue);

    List<PatientExternalId> listByPatientId(Long tenantId, Long patientId);

    List<PatientExternalId> listByTenantId(Long tenantId);

    PatientExternalId save(PatientExternalId patientExternalId);

    PatientExternalId update(PatientExternalId patientExternalId);

    Boolean deleteById(Long tenantId, Long id);
}
