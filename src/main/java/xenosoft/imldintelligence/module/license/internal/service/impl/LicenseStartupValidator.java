package xenosoft.imldintelligence.module.license.internal.service.impl;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Runs license validation at application startup unless a license CLI command is being executed.
 */
@Component
public class LicenseStartupValidator implements ApplicationRunner {
    private static final String OPT_COMMAND = "license-cli-command";
    private final ValidateService validateService;

    public LicenseStartupValidator(ValidateService validateService) {
        this.validateService = validateService;
    }

    /**
     * Triggers startup license validation for normal application boot.
     *
     * @param args parsed application arguments
     * @throws IllegalStateException if the configured private deployment license is invalid
     */
    @Override
    public void run(ApplicationArguments args) {
        if (isCliCommandActive(args)) {
            return;
        }
        validateService.validateStartupOrThrow();
    }

    private boolean isCliCommandActive(ApplicationArguments args) {
        List<String> values = args.getOptionValues(OPT_COMMAND);
        if (values == null || values.isEmpty()) {
            return false;
        }
        String command = values.get(values.size() - 1);
        return command != null && !command.trim().isEmpty();
    }
}
