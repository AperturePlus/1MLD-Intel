package xenosoft.imldintelligence.module.careplan.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import xenosoft.imldintelligence.common.CheckPermission;
import xenosoft.imldintelligence.common.dto.ApiResponse;
import xenosoft.imldintelligence.common.dto.PageQueryRequest;
import xenosoft.imldintelligence.common.dto.PagedResultResponse;
import xenosoft.imldintelligence.module.careplan.api.dto.CarePlanApiDtos;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertEvent;
import xenosoft.imldintelligence.module.careplan.internal.model.CarePlan;
import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;
import xenosoft.imldintelligence.module.careplan.internal.model.PatientReportedData;
import xenosoft.imldintelligence.module.careplan.internal.repository.AlertActionRepository;
import xenosoft.imldintelligence.module.careplan.internal.service.CarePlanService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarePlanController implements CarePlanControllerContract {

    private final CarePlanService carePlanService;
    private final AlertActionRepository alertActionRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "READ")
    public ApiResponse<PagedResultResponse<CarePlanApiDtos.Response.CarePlanResponse>> listCarePlans(
            Long tenantId,
            CarePlanApiDtos.Query.CarePlanPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = carePlanService.countCarePlans(tenantId, query);
        List<CarePlan> items = carePlanService.listCarePlans(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toCarePlanResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "CREATE")
    public ApiResponse<CarePlanApiDtos.Response.CarePlanResponse> createCarePlan(
            Long tenantId,
            CarePlanApiDtos.Request.CreateCarePlanRequest request) {
        CarePlan carePlan = carePlanService.createCarePlan(tenantId, request);
        return ApiResponse.success(toCarePlanResponse(carePlan));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "READ")
    public ApiResponse<PagedResultResponse<CarePlanApiDtos.Response.FollowupTaskResponse>> listFollowupTasks(
            Long tenantId,
            CarePlanApiDtos.Query.FollowupTaskPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = carePlanService.countFollowupTasks(tenantId, query);
        List<FollowupTask> items = carePlanService.listFollowupTasks(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toFollowupTaskResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "CREATE")
    public ApiResponse<CarePlanApiDtos.Response.FollowupTaskResponse> scheduleFollowupTask(
            Long tenantId,
            CarePlanApiDtos.Request.ScheduleFollowupTaskRequest request) {
        FollowupTask task = carePlanService.scheduleFollowupTask(tenantId, request);
        return ApiResponse.success(toFollowupTaskResponse(task));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "READ")
    public ApiResponse<PagedResultResponse<CarePlanApiDtos.Response.AlertEventResponse>> listAlertEvents(
            Long tenantId,
            CarePlanApiDtos.Query.AlertEventPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = carePlanService.countAlertEvents(tenantId, query);
        List<AlertEvent> items = carePlanService.listAlertEvents(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(e -> toAlertEventResponse(tenantId, e)).toList()));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "UPDATE")
    public ApiResponse<CarePlanApiDtos.Response.AlertEventResponse> resolveAlert(
            Long tenantId,
            CarePlanApiDtos.Request.ResolveAlertRequest request) {
        AlertEvent alertEvent = carePlanService.resolveAlert(tenantId, request);
        return ApiResponse.success(toAlertEventResponse(tenantId, alertEvent));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "READ")
    public ApiResponse<PagedResultResponse<CarePlanApiDtos.Response.PatientReportedDataResponse>> listPatientReportedData(
            Long tenantId,
            CarePlanApiDtos.Query.PatientReportedDataPageQuery query,
            PageQueryRequest pageQuery) {
        int page = pageQuery.page() != null ? pageQuery.page() : DEFAULT_PAGE;
        int size = pageQuery.size() != null ? pageQuery.size() : DEFAULT_SIZE;
        long offset = (long) page * size;

        long total = carePlanService.countPatientReportedData(tenantId, query);
        List<PatientReportedData> items = carePlanService.listPatientReportedData(tenantId, query, offset, size);

        return ApiResponse.success(new PagedResultResponse<>(page, size, total,
                items.stream().map(this::toPatientReportedDataResponse).toList()));
    }

    @Override
    @CheckPermission(resource = "CARE_PLAN", action = "CREATE")
    public ApiResponse<CarePlanApiDtos.Response.PatientReportedDataResponse> reportPatientData(
            Long tenantId,
            CarePlanApiDtos.Request.ReportPatientDataRequest request) {
        PatientReportedData data = carePlanService.reportPatientData(tenantId, request);
        return ApiResponse.success(toPatientReportedDataResponse(data));
    }

    private CarePlanApiDtos.Response.CarePlanResponse toCarePlanResponse(CarePlan c) {
        return new CarePlanApiDtos.Response.CarePlanResponse(
                c.getId(), c.getPatientId(), c.getDiseaseCode(), c.getPlanType(),
                c.getEnrollmentType(), c.getTemplateId(), c.getStatus(),
                c.getStartDate(), c.getEndDate(), c.getCreatedBy(), c.getCreatedAt());
    }

    private CarePlanApiDtos.Response.FollowupTaskResponse toFollowupTaskResponse(FollowupTask t) {
        return new CarePlanApiDtos.Response.FollowupTaskResponse(
                t.getId(), t.getCarePlanId(), t.getPatientId(), t.getTaskType(),
                t.getScheduledAt(), t.getChannel(), t.getStatus(), t.getAssignedTo(),
                t.getCompletedAt(), t.getResultSummary(), t.getCreatedAt());
    }

    private CarePlanApiDtos.Response.AlertEventResponse toAlertEventResponse(Long tenantId, AlertEvent e) {
        List<CarePlanApiDtos.Shared.AlertActionItem> actions = alertActionRepository
                .listByAlertId(tenantId, e.getId()).stream()
                .map(a -> new CarePlanApiDtos.Shared.AlertActionItem(
                        a.getId(), a.getAlertId(), a.getActionType(),
                        a.getActionBy(), a.getActionNote(), a.getCreatedAt()))
                .toList();
        return new CarePlanApiDtos.Response.AlertEventResponse(
                e.getId(), e.getPatientId(), e.getCarePlanId(), e.getTriggerType(),
                e.getTriggerDetail(), e.getSeverity(), e.getStatus(), e.getAssignedTo(),
                e.getCreatedAt(), e.getResolvedAt(), e.getResolutionNote(), actions);
    }

    private CarePlanApiDtos.Response.PatientReportedDataResponse toPatientReportedDataResponse(PatientReportedData d) {
        return new CarePlanApiDtos.Response.PatientReportedDataResponse(
                d.getId(), d.getPatientId(), d.getCarePlanId(), d.getIndicatorCode(),
                d.getValueNumeric(), d.getValueText(), d.getSource(), d.getDeviceInfo(),
                d.getRecordedAt(), d.getCreatedAt());
    }
}
