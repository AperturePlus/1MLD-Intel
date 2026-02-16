package xenosoft.imldintelligence.module.license.internal.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LicenseStartupValidatorTest {

    @Test
    void shouldValidateStartupWhenCliCommandOptionIsBlank() {
        ValidateService validateService = mock(ValidateService.class);
        LicenseStartupValidator validator = new LicenseStartupValidator(validateService);

        validator.run(new DefaultApplicationArguments("--license-cli-command="));

        verify(validateService, times(1)).validateStartupOrThrow();
    }

    @Test
    void shouldSkipStartupValidationWhenCliCommandIsProvided() {
        ValidateService validateService = mock(ValidateService.class);
        LicenseStartupValidator validator = new LicenseStartupValidator(validateService);

        validator.run(new DefaultApplicationArguments("--license-cli-command=import-license"));

        verify(validateService, never()).validateStartupOrThrow();
    }
}
