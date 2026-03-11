package xenosoft.imldintelligence.module.screening.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.screening.api.dto.ScreeningApiDtos;

/**
 * 筛查模块 HTTP 契约。
 *
 * <p>契约按“问卷配置 -> 患者作答 -> 院内转介”主链设计，便于 SaaS 与私有化场景共用。</p>
 */
@Validated
@RequestMapping("/api/v1/screening")
public interface ScreeningApi {

    /**
     * 分页查询问卷。
     */
    @GetMapping("/questionnaires")
    ApiResponse<PagedResultResponse<ScreeningApiDtos.Response.QuestionnaireDetailResponse>> listQuestionnaires(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ScreeningApiDtos.Query.QuestionnairePageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 发布问卷。
     */
    @PostMapping("/questionnaires")
    ApiResponse<ScreeningApiDtos.Response.QuestionnaireDetailResponse> publishQuestionnaire(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ScreeningApiDtos.Request.PublishQuestionnaireRequest request
    );

    /**
     * 分页查询问卷答卷。
     */
    @GetMapping("/responses")
    ApiResponse<PagedResultResponse<ScreeningApiDtos.Response.QuestionnaireSubmissionResponse>> listResponses(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 提交问卷答卷。
     */
    @PostMapping("/responses")
    ApiResponse<ScreeningApiDtos.Response.QuestionnaireSubmissionResponse> submitResponse(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ScreeningApiDtos.Request.SubmitQuestionnaireResponseRequest request
    );

    /**
     * 分页查询转介记录。
     */
    @GetMapping("/transfers")
    ApiResponse<PagedResultResponse<ScreeningApiDtos.Response.ClinicalTransferResponse>> listTransfers(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @ModelAttribute ScreeningApiDtos.Query.TransferPageQuery query,
            @Valid @ModelAttribute PageQueryRequest pageQuery
    );

    /**
     * 审批转介。
     */
    @PostMapping("/transfers")
    ApiResponse<ScreeningApiDtos.Response.ClinicalTransferResponse> approveTransfer(
            @RequestHeader("X-Tenant-Id")
            @Positive(message = "tenantId must be positive")
            Long tenantId,
            @Valid @RequestBody ScreeningApiDtos.Request.ApproveClinicalTransferRequest request
    );
}
