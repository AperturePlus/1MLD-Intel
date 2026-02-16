package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.Tenant;

import java.util.List;
import java.util.Optional;

public interface TenantRepository {
    Optional<Tenant> findById(Long id);

    Optional<Tenant> findByTenantCode(String tenantCode);

    List<Tenant> listAll();

    Tenant save(Tenant tenant);

    Tenant update(Tenant tenant);

    Boolean deleteById(Long id);
}
