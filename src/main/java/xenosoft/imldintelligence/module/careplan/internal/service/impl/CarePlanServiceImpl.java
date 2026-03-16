package xenosoft.imldintelligence.module.careplan.internal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xenosoft.imldintelligence.module.careplan.api.dto.CarePlanApiDtos;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertAction;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertEvent;
import xenosoft.imldintelligence.module.careplan.internal.model.CarePlan;
import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;
import xenosoft.imldintelligence.module.careplan.internal.model.PatientReportedData;
import xenosoft.imldintelligence.module.careplan.internal.repository.AlertActionRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.AlertEventRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.CarePlanRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.FollowupTaskRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.PatientReportedDataRepository;
import xenosoft.imldintelligence.module.careplan.internal.service.CarePlanService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarePlanServiceImpl implements CarePlanService {

    private final CarePlanRepository carePlanRepository;
    private final FollowupTaskRepository followupTaskRepository;
    private final AlertEventRepository alertEventRepository;
    private final AlertActionRepository alertActionRepository;
    private final PatientReportedDataRepository patientReportedDataRepository;

    @Override
    public long countCarePlans(Long tenantId, CarePlanApiDtos.Query.CarePlanPageQuery query) {
        return listAllCarePlans(tenantId, query).size();
    }

    @Override
    public List<CarePlan> listCarePlans(Long tenantId, CarePlanApiDtos.Query.CarePlanPageQuery query,
                                        long offset, int limit) {
        List<CarePlan> all = listAllCarePlans(tenantId, query);
        return paginate(all, offset, limit);
    }

    @Override
    public CarePlan createCarePlan(Long tenantId, CarePlanApiDtos.Request.CreateCarePlanRequest request) {
        CarePlan carePlan = new CarePlan();
        carePlan.setTenantId(tenantId);
        carePlan.setPatientId(request.patientId());
        carePlan.setDiseaseCode(request.diseaseCode());
        carePlan.setPlanType(request.planType());
        carePlan.setEnrollmentType(request.enrollmentType());
        carePlan.setTemplateId(request.templateId());
        carePlan.setStatus("ACTIVE");
        carePlan.setStartDate(request.startDate());
        carePlan.setEndDate(request.endDate());
        carePlan.setCreatedBy(request.createdBy());
        carePlan.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return carePlanRepository.save(carePlan);
    }

    @Override
    public long countFollowupTasks(Long tenantId, CarePlanApiDtos.Query.FollowupTaskPageQuery query) {
        return listAllFollowupTasks(tenantId, query).size();
    }

    @Override
    public List<FollowupTask> listFollowupTasks(Long tenantId, CarePlanApiDtos.Query.FollowupTaskPageQuery query,
                                                 long offset, int limit) {
        return paginate(listAllFollowupTasks(tenantId, query), offset, limit);
    }

    @Override
    public FollowupTask scheduleFollowupTask(Long tenantId,
                                              CarePlanApiDtos.Request.ScheduleFollowupTaskRequest request) {
        FollowupTask task = new FollowupTask();
        task.setTenantId(tenantId);
        task.setCarePlanId(request.carePlanId());
        task.setPatientId(request.patientId());
        task.setTaskType(request.taskType());
        task.setScheduledAt(request.scheduledAt());
        task.setChannel(request.channel());
        task.setStatus("PENDING");
        task.setAssignedTo(request.assignedTo());
        task.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return followupTaskRepository.save(task);
    }

    @Override
    public long countAlertEvents(Long tenantId, CarePlanApiDtos.Query.AlertEventPageQuery query) {
        return listAllAlertEvents(tenantId, query).size();
    }

    @Override
    public List<AlertEvent> listAlertEvents(Long tenantId, CarePlanApiDtos.Query.AlertEventPageQuery query,
                                             long offset, int limit) {
        return paginate(listAllAlertEvents(tenantId, query), offset, limit);
    }

    @Override
    public AlertEvent resolveAlert(Long tenantId, CarePlanApiDtos.Request.ResolveAlertRequest request) {
        AlertEvent alertEvent = alertEventRepository.findById(tenantId, request.alertId())
                .orElseThrow(() -> new IllegalArgumentException("AlertEvent not found: " + request.alertId()));

        AlertAction action = new AlertAction();
        action.setTenantId(tenantId);
        action.setAlertId(request.alertId());
        action.setActionType(request.actionType());
        action.setActionBy(request.actionBy());
        action.setActionNote(request.actionNote());
        action.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        alertActionRepository.save(action);

        alertEvent.setStatus("RESOLVED");
        alertEvent.setResolutionNote(request.resolutionNote());
        alertEvent.setResolvedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return alertEventRepository.update(alertEvent);
    }

    @Override
    public long countPatientReportedData(Long tenantId,
                                          CarePlanApiDtos.Query.PatientReportedDataPageQuery query) {
        return listAllPatientReportedData(tenantId, query).size();
    }

    @Override
    public List<PatientReportedData> listPatientReportedData(Long tenantId,
                                                              CarePlanApiDtos.Query.PatientReportedDataPageQuery query,
                                                              long offset, int limit) {
        return paginate(listAllPatientReportedData(tenantId, query), offset, limit);
    }

    @Override
    public PatientReportedData reportPatientData(Long tenantId,
                                                  CarePlanApiDtos.Request.ReportPatientDataRequest request) {
        PatientReportedData data = new PatientReportedData();
        data.setTenantId(tenantId);
        data.setPatientId(request.patientId());
        data.setCarePlanId(request.carePlanId());
        data.setIndicatorCode(request.indicatorCode());
        data.setValueNumeric(request.valueNumeric());
        data.setValueText(request.valueText());
        data.setSource(request.source() != null ? request.source() : "MANUAL");
        data.setDeviceInfo(request.deviceInfo());
        data.setRecordedAt(request.recordedAt() != null ? request.recordedAt() : OffsetDateTime.now(ZoneOffset.UTC));
        data.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return patientReportedDataRepository.save(data);
    }

    private List<CarePlan> listAllCarePlans(Long tenantId, CarePlanApiDtos.Query.CarePlanPageQuery query) {
        List<CarePlan> list = query.patientId() != null
                ? carePlanRepository.listByPatientId(tenantId, query.patientId())
                : carePlanRepository.listByTenantId(tenantId);
        return list.stream()
                .filter(c -> query.diseaseCode() == null || query.diseaseCode().equals(c.getDiseaseCode()))
                .filter(c -> query.planType() == null || query.planType().equals(c.getPlanType()))
                .filter(c -> query.status() == null || query.status().equals(c.getStatus()))
                .filter(c -> query.startFrom() == null || (c.getStartDate() != null && !c.getStartDate().isBefore(query.startFrom())))
                .filter(c -> query.startTo() == null || (c.getStartDate() != null && !c.getStartDate().isAfter(query.startTo())))
                .toList();
    }

    private List<FollowupTask> listAllFollowupTasks(Long tenantId,
                                                     CarePlanApiDtos.Query.FollowupTaskPageQuery query) {
        List<FollowupTask> list;
        if (query.carePlanId() != null) {
            list = followupTaskRepository.listByCarePlanId(tenantId, query.carePlanId());
        } else if (query.patientId() != null) {
            list = followupTaskRepository.listByPatientId(tenantId, query.patientId());
        } else {
            list = followupTaskRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(t -> query.taskType() == null || query.taskType().equals(t.getTaskType()))
                .filter(t -> query.status() == null || query.status().equals(t.getStatus()))
                .filter(t -> query.assignedTo() == null || query.assignedTo().equals(t.getAssignedTo()))
                .filter(t -> query.scheduledFrom() == null || (t.getScheduledAt() != null && !t.getScheduledAt().isBefore(query.scheduledFrom())))
                .filter(t -> query.scheduledTo() == null || (t.getScheduledAt() != null && !t.getScheduledAt().isAfter(query.scheduledTo())))
                .toList();
    }

    private List<AlertEvent> listAllAlertEvents(Long tenantId,
                                                 CarePlanApiDtos.Query.AlertEventPageQuery query) {
        List<AlertEvent> list;
        if (query.patientId() != null) {
            list = alertEventRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.carePlanId() != null) {
            list = alertEventRepository.listByCarePlanId(tenantId, query.carePlanId());
        } else {
            list = alertEventRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(a -> query.severity() == null || query.severity().equals(a.getSeverity()))
                .filter(a -> query.status() == null || query.status().equals(a.getStatus()))
                .filter(a -> query.assignedTo() == null || query.assignedTo().equals(a.getAssignedTo()))
                .toList();
    }

    private List<PatientReportedData> listAllPatientReportedData(Long tenantId,
                                                                   CarePlanApiDtos.Query.PatientReportedDataPageQuery query) {
        List<PatientReportedData> list;
        if (query.patientId() != null) {
            list = patientReportedDataRepository.listByPatientId(tenantId, query.patientId());
        } else if (query.carePlanId() != null) {
            list = patientReportedDataRepository.listByCarePlanId(tenantId, query.carePlanId());
        } else {
            list = patientReportedDataRepository.listByTenantId(tenantId);
        }
        return list.stream()
                .filter(d -> query.indicatorCode() == null || query.indicatorCode().equals(d.getIndicatorCode()))
                .filter(d -> query.recordedFrom() == null || (d.getRecordedAt() != null && !d.getRecordedAt().isBefore(query.recordedFrom())))
                .filter(d -> query.recordedTo() == null || (d.getRecordedAt() != null && !d.getRecordedAt().isAfter(query.recordedTo())))
                .toList();
    }

    private <T> List<T> paginate(List<T> list, long offset, int limit) {
        int from = (int) Math.min(offset, list.size());
        int to = (int) Math.min(offset + limit, list.size());
        return list.subList(from, to);
    }
}
