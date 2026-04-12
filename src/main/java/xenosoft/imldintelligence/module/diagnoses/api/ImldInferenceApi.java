package xenosoft.imldintelligence.module.diagnoses.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.module.diagnoses.api.dto.ImldInferenceApiDtos;

import java.util.List;

@Validated
@RequestMapping("/api/imld")
public interface ImldInferenceApi {

    @GetMapping("/health")
    ApiResponse<ImldInferenceApiDtos.Response.HealthResponse> health();

    @PostMapping("/predict")
    ApiResponse<ImldInferenceApiDtos.Response.PredictData> predict(
            @Valid @RequestBody ImldInferenceApiDtos.Request.ImldPredictRequest request
    );

    @PostMapping("/batch_predict")
    ApiResponse<ImldInferenceApiDtos.Response.BatchPredictData> batchPredict(
            @Valid @RequestBody @NotEmpty List<ImldInferenceApiDtos.Request.ImldPredictRequest> requests
    );
}
