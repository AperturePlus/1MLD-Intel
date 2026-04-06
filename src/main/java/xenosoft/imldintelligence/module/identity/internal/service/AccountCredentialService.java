package xenosoft.imldintelligence.module.identity.internal.service;

import xenosoft.imldintelligence.module.identity.api.dto.IdentityApiDtos;
import xenosoft.imldintelligence.module.identity.internal.dto.AuthToken;

public interface AccountCredentialService {
    IdentityApiDtos.Response.EmailCodeSendResponse sendRegistrationEmailCode(
            IdentityApiDtos.Request.SendRegistrationEmailCodeCommand request
    );

    IdentityApiDtos.Response.EmailCodeSendResponse sendPasswordResetEmailCode(
            IdentityApiDtos.Request.ForgotPasswordCommand request
    );

    AuthToken register(IdentityApiDtos.Request.RegisterCommand request);

    void resetPassword(IdentityApiDtos.Request.ResetPasswordCommand request);
}
