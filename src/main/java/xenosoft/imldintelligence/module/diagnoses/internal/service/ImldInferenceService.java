package xenosoft.imldintelligence.module.diagnoses.internal.service;

import xenosoft.imldintelligence.module.diagnoses.api.dto.ImldInferenceApiDtos;

import java.util.List;

public interface ImldInferenceService {
    ImldInferenceApiDtos.Response.HealthResponse health();

    ImldInferenceApiDtos.Response.PredictData predict(ImldInferenceApiDtos.Request.ImldPredictRequest request);

    ImldInferenceApiDtos.Response.BatchPredictData batchPredict(
            List<ImldInferenceApiDtos.Request.ImldPredictRequest> requests
    );
}
