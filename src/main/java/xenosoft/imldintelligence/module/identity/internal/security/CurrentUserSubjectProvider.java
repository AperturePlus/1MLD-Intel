package xenosoft.imldintelligence.module.identity.internal.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import xenosoft.imldintelligence.module.identity.internal.model.UserSubject;

import java.util.Optional;

/**
 * Reads the authenticated {@link UserSubject} from Spring Security context.
 */
@Component
public class CurrentUserSubjectProvider {

    /**
     * Returns the current authenticated subject if present and typed as {@link UserSubject}.
     *
     * <p>The returned subject is reconstructed with normalized role codes so downstream authorization
     * checks see a stable role representation.</p>
     */
    public Optional<UserSubject> getCurrentSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserSubject userSubject)) {
            return Optional.empty();
        }
        return Optional.of(new UserSubject(
                userSubject.userId(),
                userSubject.tenantId(),
                userSubject.userType(),
                userSubject.deptName(),
                RoleAuthorityUtils.normalizeRoleCodes(userSubject.roleCodes())
        ));
    }

    /**
     * Returns the current authenticated subject or throws when the request is unauthenticated.
     */
    public UserSubject requireCurrentSubject() {
        return getCurrentSubject().orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("Authenticated user context is required."));
    }
}
