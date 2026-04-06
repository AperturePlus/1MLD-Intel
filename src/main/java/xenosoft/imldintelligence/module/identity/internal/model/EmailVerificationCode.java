package xenosoft.imldintelligence.module.identity.internal.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class EmailVerificationCode {
    private Long id;
    private Long tenantId;
    private String username;
    private String email;
    private String purpose;
    private String codeHash;
    private Integer verifyAttemptCount;
    private Integer maxVerifyAttempts;
    private String status;
    private OffsetDateTime expiresAt;
    private OffsetDateTime consumedAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
