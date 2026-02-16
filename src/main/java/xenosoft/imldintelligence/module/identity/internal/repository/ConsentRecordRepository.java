package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.ConsentRecord;

import java.util.List;
import java.util.Optional;

public interface ConsentRecordRepository {
    Optional<ConsentRecord> findById(Long tenantId, Long id);

    List<ConsentRecord> listByTenantId(Long tenantId);

    List<ConsentRecord> listByPatientId(Long tenantId, Long patientId);

    List<ConsentRecord> listByTocUserId(Long tenantId, Long tocUserId);

    ConsentRecord save(ConsentRecord consentRecord);

    ConsentRecord update(ConsentRecord consentRecord);

    Boolean deleteById(Long tenantId, Long id);
}
