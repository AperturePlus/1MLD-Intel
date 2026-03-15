package xenosoft.imldintelligence.module.identity.internal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Registers public endpoints of the identity module into the shared authorization chain.
 *
 * <p>This customizer runs with highest precedence so authentication endpoints are opened before
 * other module-specific deny or role rules are evaluated.</p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class IdentityRequestAuthorizationCustomizer implements ModuleRequestAuthorizationCustomizer {
    private final IdentitySecurityProperties properties;

    /**
     * Appends identity module permit-all matchers from configuration.
     */
    @Override
    public void customize(org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer<org.springframework.security.config.annotation.web.builders.HttpSecurity>.AuthorizationManagerRequestMatcherRegistry requests) {
        List<String> publicPaths = properties.getPublicPaths();
        if (publicPaths == null || publicPaths.isEmpty()) {
            return;
        }
        requests.requestMatchers(publicPaths.toArray(String[]::new)).permitAll();
    }
}
