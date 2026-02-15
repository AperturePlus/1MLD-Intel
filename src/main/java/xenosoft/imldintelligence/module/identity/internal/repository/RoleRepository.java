package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findById(Long tenantId, Long id);

    Optional<Role> findByRoleCode(Long tenantId, String roleCode);

    List<Role> listByTenantId(Long tenantId);

    Role save(Role role);

    Role update(Role role);

    Boolean deleteById(Long tenantId, Long id);
}
