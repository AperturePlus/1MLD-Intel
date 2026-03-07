package xenosoft.imldintelligence.module.license.internal.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * 许可证信息模型，描述部署模式、支持期、功能范围与授权约束。
 */
@Data
public class LicenseInfo {
    private String licenseId;
    private String hospitalId;
    private String deploymentMode;
    private String issuer;
    private OffsetDateTime issuedAt;
    private LocalDate supportStartDate;
    private LocalDate supportEndDate;
    private String machineFingerprintHash;
    private String activationCodeHash;
    private List<String> features;
    private List<String> scenarios;
}
