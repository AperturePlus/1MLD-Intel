package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.Encounter;

import java.util.List;
import java.util.Optional;

public interface EncounterRepository {
    Optional<Encounter> findById(Long tenantId, Long id);

    Optional<Encounter> findByEncounterNo(Long tenantId, String encounterNo);

    List<Encounter> listByTenantId(Long tenantId);

    List<Encounter> listByPatientId(Long tenantId, Long patientId);

    Encounter save(Encounter encounter);

    Encounter update(Encounter encounter);

    Boolean deleteById(Long tenantId, Long id);
}
