package xenosoft.imldintelligence.module.identity.internal.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;
import xenosoft.imldintelligence.module.identity.internal.model.UserSubject;
import xenosoft.imldintelligence.module.identity.internal.util.JwtUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Parses a JWT access token from the {@code Authorization} header and stores an authenticated
 * {@link UserSubject} in Spring Security context.
 *
 * <p>If the header is missing, the request continues without authentication. If the token is
 * malformed or verification fails, the filter clears the context and delegates the response to
 * the configured {@link AuthenticationEntryPoint}.</p>
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Applies bearer-token authentication for the current request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || authorization.isBlank() || !authorization.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(BEARER_PREFIX.length()).trim();
        if (token.isBlank()) {
            authenticationEntryPoint.commence(request, response, new BadCredentialsException("Bearer token is blank"));
            return;
        }

        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserSubject userSubject = jwtUtil.parseAccessToken(token);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated(
                        userSubject,
                        token,
                        toAuthorities(userSubject.roleCodes())
                ));
                SecurityContextHolder.setContext(context);
            }
            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response, new BadCredentialsException("Invalid JWT access token", ex));
        }
    }

    /**
     * Expands role codes into the complete authority set required by authorization rules.
     */
    private Collection<? extends GrantedAuthority> toAuthorities(Set<String> roleCodes) {
        return RoleAuthorityUtils.expandAuthorityNames(roleCodes).stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
