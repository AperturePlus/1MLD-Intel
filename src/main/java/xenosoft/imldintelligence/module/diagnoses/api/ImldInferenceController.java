package xenosoft.imldintelligence.module.diagnoses.api;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RestController;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.module.diagnoses.api.dto.ImldInferenceApiDtos;
import xenosoft.imldintelligence.module.diagnoses.internal.service.ImldInferenceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "imld.inference.imld", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ImldInferenceController implements ImldInferenceControllerContract {

    private final ImldInferenceService inferenceService;

    @Override
    public ApiResponse<ImldInferenceApiDtos.Response.HealthResponse> health() {
        return ApiResponse.success(inferenceService.health());
    }

    @Override
    public ApiResponse<ImldInferenceApiDtos.Response.PredictData> predict(
            ImldInferenceApiDtos.Request.ImldPredictRequest request) {
        return ApiResponse.success(inferenceService.predict(request));
    }

    @Override
    public ApiResponse<ImldInferenceApiDtos.Response.BatchPredictData> batchPredict(
            List<ImldInferenceApiDtos.Request.ImldPredictRequest> requests) {
        return ApiResponse.success(inferenceService.batchPredict(requests));
    }
}
