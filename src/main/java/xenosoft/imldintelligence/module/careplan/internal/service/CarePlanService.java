package xenosoft.imldintelligence.module.careplan.internal.service;

import xenosoft.imldintelligence.module.careplan.api.dto.CarePlanApiDtos;
import xenosoft.imldintelligence.module.careplan.internal.model.AlertEvent;
import xenosoft.imldintelligence.module.careplan.internal.model.CarePlan;
import xenosoft.imldintelligence.module.careplan.internal.model.FollowupTask;
import xenosoft.imldintelligence.module.careplan.internal.model.PatientReportedData;

import java.util.List;

/**
 * 随访计划业务服务接口。
 */
public interface CarePlanService {

    long countCarePlans(Long tenantId, CarePlanApiDtos.Query.CarePlanPageQuery query);

    List<CarePlan> listCarePlans(Long tenantId, CarePlanApiDtos.Query.CarePlanPageQuery query,
                                 long offset, int limit);

    CarePlan createCarePlan(Long tenantId, CarePlanApiDtos.Request.CreateCarePlanRequest request);

    long countFollowupTasks(Long tenantId, CarePlanApiDtos.Query.FollowupTaskPageQuery query);

    List<FollowupTask> listFollowupTasks(Long tenantId, CarePlanApiDtos.Query.FollowupTaskPageQuery query,
                                         long offset, int limit);

    FollowupTask scheduleFollowupTask(Long tenantId, CarePlanApiDtos.Request.ScheduleFollowupTaskRequest request);

    long countAlertEvents(Long tenantId, CarePlanApiDtos.Query.AlertEventPageQuery query);

    List<AlertEvent> listAlertEvents(Long tenantId, CarePlanApiDtos.Query.AlertEventPageQuery query,
                                     long offset, int limit);

    AlertEvent resolveAlert(Long tenantId, CarePlanApiDtos.Request.ResolveAlertRequest request);

    long countPatientReportedData(Long tenantId, CarePlanApiDtos.Query.PatientReportedDataPageQuery query);

    List<PatientReportedData> listPatientReportedData(Long tenantId,
                                                      CarePlanApiDtos.Query.PatientReportedDataPageQuery query,
                                                      long offset, int limit);

    PatientReportedData reportPatientData(Long tenantId, CarePlanApiDtos.Request.ReportPatientDataRequest request);
}
