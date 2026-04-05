package xenosoft.imldintelligence.module.license.api;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.module.license.api.dto.LicenseApiDtos;

/**
 * 许可证模块 HTTP 契约。
 *
 * <p>接口仅定义授权、验签、升级准入和指纹读取的边界；
 * 具体离线验签与激活流程仍由后续实现承接。</p>
 */
@Validated
@RequestMapping({"/api/v1/license", "/api/v1/app/license", "/api/v1/web/license"})
public interface LicenseApi {

    /**
     * 查询当前部署的许可证状态。
     */
    @GetMapping("/status")
    ApiResponse<LicenseApiDtos.Response.LicenseSummaryResponse> getLicenseStatus();

    /**
     * 上传签名许可证文件。
     */
    @PostMapping("/envelopes")
    ApiResponse<LicenseApiDtos.Response.LicenseSummaryResponse> uploadLicenseEnvelope(
            @Valid @RequestBody LicenseApiDtos.Request.UploadLicenseEnvelopeRequest request
    );

    /**
     * 激活许可证。
     */
    @PostMapping("/activate")
    ApiResponse<LicenseApiDtos.Response.LicenseValidationResponse> activateLicense(
            @Valid @RequestBody LicenseApiDtos.Request.ActivateLicenseRequest request
    );

    /**
     * 纯验签校验。
     */
    @PostMapping("/validate")
    ApiResponse<LicenseApiDtos.Response.LicenseValidationResponse> validateLicense(
            @Valid @RequestBody LicenseApiDtos.Request.ValidateLicenseRequest request
    );

    /**
     * 查询本机指纹摘要。
     */
    @GetMapping("/fingerprint")
    ApiResponse<LicenseApiDtos.Response.FingerprintResponse> getFingerprint();

    /**
     * 判断当前许可证是否允许升级到指定发布清单。
     */
    @GetMapping("/release-eligibility")
    ApiResponse<LicenseApiDtos.Response.ReleaseEligibilityResponse> evaluateReleaseEligibility(
            @Valid @ModelAttribute LicenseApiDtos.Query.ReleaseEligibilityQuery query
    );
}
