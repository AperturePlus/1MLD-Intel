package xenosoft.imldintelligence.module.screening.internal.service;

import xenosoft.imldintelligence.module.screening.api.dto.ScreeningApiDtos;
import xenosoft.imldintelligence.module.screening.internal.model.Questionnaire;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireResponse;
import xenosoft.imldintelligence.module.screening.internal.model.TocClinicalTransfer;

import java.util.List;

/**
 * 筛查业务服务接口。
 */
public interface ScreeningService {

    long countQuestionnaires(Long tenantId, ScreeningApiDtos.Query.QuestionnairePageQuery query);

    List<Questionnaire> listQuestionnaires(Long tenantId, ScreeningApiDtos.Query.QuestionnairePageQuery query,
                                           long offset, int limit);

    Questionnaire publishQuestionnaire(Long tenantId,
                                       ScreeningApiDtos.Request.PublishQuestionnaireRequest request);

    long countResponses(Long tenantId, ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query);

    List<QuestionnaireResponse> listResponses(Long tenantId,
                                               ScreeningApiDtos.Query.QuestionnaireResponsePageQuery query,
                                               long offset, int limit);

    QuestionnaireResponse submitResponse(Long tenantId,
                                          ScreeningApiDtos.Request.SubmitQuestionnaireResponseRequest request);

    long countTransfers(Long tenantId, ScreeningApiDtos.Query.TransferPageQuery query);

    List<TocClinicalTransfer> listTransfers(Long tenantId, ScreeningApiDtos.Query.TransferPageQuery query,
                                             long offset, int limit);

    TocClinicalTransfer approveTransfer(Long tenantId,
                                         ScreeningApiDtos.Request.ApproveClinicalTransferRequest request);
}
