package xenosoft.imldintelligence.module.audit.internal.service;

import xenosoft.imldintelligence.module.audit.internal.service.command.AuditRecordCommand;
import xenosoft.imldintelligence.module.audit.internal.service.command.ModelInvocationRecordCommand;
import xenosoft.imldintelligence.module.audit.internal.service.command.SensitiveAccessRecordCommand;
import xenosoft.imldintelligence.common.model.AuditLog;
import xenosoft.imldintelligence.common.model.ModelInvocationLog;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

/**
 * AuditTrailService 接口定义。
 */
public interface AuditTrailService {
    /**
     * 记录业务审计日志。
     *
     * @param command 审计记录命令
     * @return 保存后的业务审计日志
     */
    AuditLog recordAudit(AuditRecordCommand command);

    /**
     * 记录敏感数据访问日志。
     *
     * @param command 敏感访问记录命令
     * @return 保存后的敏感访问日志
     */
    SensitiveDataAccessLog recordSensitiveAccess(SensitiveAccessRecordCommand command);

    /**
     * 记录模型调用日志。
     *
     * @param command 模型调用记录命令
     * @return 保存后的模型调用日志
     */
    ModelInvocationLog recordModelInvocation(ModelInvocationRecordCommand command);
}
