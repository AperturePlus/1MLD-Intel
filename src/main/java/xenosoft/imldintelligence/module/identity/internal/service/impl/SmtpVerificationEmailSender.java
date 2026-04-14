package xenosoft.imldintelligence.module.identity.internal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import xenosoft.imldintelligence.module.identity.internal.config.IdentityVerificationProperties;
import xenosoft.imldintelligence.module.identity.internal.service.VerificationEmailSender;
import org.springframework.mail.javamail.MimeMailMessage;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SmtpVerificationEmailSender implements VerificationEmailSender {
    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final IdentityVerificationProperties properties;

    @Value("${spring.mail.username:}")
    private String smtpUsername;

    @Override
    public void sendVerificationCode(String toEmail, String scenario, String code, Duration ttl) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SMTP service is not configured");
        }
        String from = resolveFromAddress();
        if (!hasText(from)) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "SMTP sender address is not configured");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toEmail);
        message.setSubject(buildSubject(scenario));
        message.setText(buildBody(scenario, code, ttl));
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Failed to send verification email", ex);
        }
    }

    private String buildSubject(String scenario) {
        String prefix = properties.getEmailSubjectPrefix();
        String normalizedPrefix = hasText(prefix) ? prefix.trim() + " " : "";
        return normalizedPrefix + scenario + " verification code";
    }

    private String buildBody(String scenario, String code, Duration ttl) {
        long minutes = Math.max(1L, ttl.toMinutes());
        return "You are requesting " + scenario + ".\n"
                + "Verification code: " + code + "\n"
                + "The code expires in " + minutes + " minute(s).\n"
                + "If this action was not initiated by you, please ignore this email.";
    }

    private String resolveFromAddress() {
        if (hasText(properties.getFromAddress())) {
            return properties.getFromAddress().trim();
        }
        if (hasText(smtpUsername)) {
            return smtpUsername.trim();
        }
        return null;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
