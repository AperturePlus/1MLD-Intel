package xenosoft.imldintelligence.module.identity.internal.security;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * JWT 令牌类型枚举，区分访问令牌与刷新令牌。
 */
@RequiredArgsConstructor
public enum JwtTokenType {
    ACCESS("access"),
    REFRESH("refresh");

    private final String claimValue;

    public String claimValue() {
        return claimValue;
    }

    public static JwtTokenType fromClaim(String value) {
        if (value == null || value.isBlank()) {
            throw new JwtException("JWT token_type claim is missing");
        }
        return Arrays.stream(values())
                .filter(tokenType -> tokenType.claimValue.equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new JwtException("Unsupported JWT token type: " + value));
    }
}
