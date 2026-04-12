package xenosoft.imldintelligence.module.identity.internal.service;

import java.time.Duration;

public interface VerificationEmailSender {
    void sendVerificationCode(String toEmail, String scenario, String code, Duration ttl);
}
