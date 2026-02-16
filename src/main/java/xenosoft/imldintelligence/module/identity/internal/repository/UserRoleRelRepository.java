package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.UserRoleRel;

import java.util.List;
import java.util.Optional;

public interface UserRoleRelRepository {
    Optional<UserRoleRel> findById(Long tenantId, Long id);

    Optional<UserRoleRel> findByUserIdAndRoleId(Long tenantId, Long userId, Long roleId);

    List<UserRoleRel> listByUserId(Long tenantId, Long userId);

    List<UserRoleRel> listByRoleId(Long tenantId, Long roleId);

    UserRoleRel save(UserRoleRel userRoleRel);

    UserRoleRel update(UserRoleRel userRoleRel);

    Boolean deleteById(Long tenantId, Long id);

    Boolean deleteByUserIdAndRoleId(Long tenantId, Long userId, Long roleId);
}
