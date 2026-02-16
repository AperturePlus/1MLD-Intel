package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.AbacPolicy;

import java.util.List;
import java.util.Optional;

public interface AbacPolicyRepository {
    Optional<AbacPolicy> findById(Long tenantId, Long id);

    Optional<AbacPolicy> findByPolicyCode(Long tenantId, String policyCode);

    List<AbacPolicy> listByTenantId(Long tenantId);

    AbacPolicy save(AbacPolicy abacPolicy);

    AbacPolicy update(AbacPolicy abacPolicy);

    Boolean deleteById(Long tenantId, Long id);
}
