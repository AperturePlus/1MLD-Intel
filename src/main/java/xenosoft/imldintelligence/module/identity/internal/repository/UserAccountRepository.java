package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository {
    Optional<UserAccount> findById(Long tenantId, Long id);

    Optional<UserAccount> findByUserNo(Long tenantId, String userNo);

    Optional<UserAccount> findByUsername(Long tenantId, String username);

    List<UserAccount> listByTenantId(Long tenantId);

    UserAccount save(UserAccount userAccount);

    UserAccount update(UserAccount userAccount);

    Boolean deleteById(Long tenantId, Long id);
}
