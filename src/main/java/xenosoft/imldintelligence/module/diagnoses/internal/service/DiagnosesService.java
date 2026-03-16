package xenosoft.imldintelligence.module.diagnoses.internal.service;

import xenosoft.imldintelligence.module.diagnoses.api.dto.DiagnosesApiDtos;
import xenosoft.imldintelligence.module.diagnoses.internal.model.DiagnosisSession;
import xenosoft.imldintelligence.module.diagnoses.internal.model.ModelRegistry;

import java.util.List;

/**
 * 诊断业务服务接口。
 */
public interface DiagnosesService {

    long countSessions(Long tenantId, DiagnosesApiDtos.Query.SessionPageQuery query);

    List<DiagnosisSession> listSessions(Long tenantId, DiagnosesApiDtos.Query.SessionPageQuery query,
                                         long offset, int limit);

    DiagnosisSession getSession(Long tenantId, Long sessionId);

    DiagnosisSession startSession(Long tenantId, DiagnosesApiDtos.Request.StartDiagnosisSessionRequest request);

    DiagnosisSession submitDoctorFeedback(Long tenantId,
                                           DiagnosesApiDtos.Request.SubmitDoctorFeedbackRequest request);

    long countModels(Long tenantId, DiagnosesApiDtos.Query.ModelRegistryPageQuery query);

    List<ModelRegistry> listModels(Long tenantId, DiagnosesApiDtos.Query.ModelRegistryPageQuery query,
                                    long offset, int limit);

    ModelRegistry registerModel(Long tenantId, DiagnosesApiDtos.Request.RegisterModelRequest request);
}
