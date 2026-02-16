package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.GuardianRelation;

import java.util.List;
import java.util.Optional;

public interface GuardianRelationRepository {
    Optional<GuardianRelation> findById(Long tenantId, Long id);

    Optional<GuardianRelation> findPrimaryByPatientId(Long tenantId, Long patientId);

    List<GuardianRelation> listByPatientId(Long tenantId, Long patientId);

    List<GuardianRelation> listByTenantId(Long tenantId);

    GuardianRelation save(GuardianRelation guardianRelation);

    GuardianRelation update(GuardianRelation guardianRelation);

    Boolean deleteById(Long tenantId, Long id);
}
