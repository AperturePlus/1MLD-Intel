package xenosoft.imldintelligence.module.identity.internal.repository;

import xenosoft.imldintelligence.module.identity.internal.model.TocUser;

import java.util.List;
import java.util.Optional;

public interface TocUserRepository {
    Optional<TocUser> findById(Long tenantId, Long id);

    Optional<TocUser> findByTocUid(Long tenantId, String tocUid);

    List<TocUser> listByTenantId(Long tenantId);

    TocUser save(TocUser tocUser);

    TocUser update(TocUser tocUser);

    Boolean deleteById(Long tenantId, Long id);
}
