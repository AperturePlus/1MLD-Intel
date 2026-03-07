package xenosoft.imldintelligence.module.audit.internal.service.command;

import lombok.Data;

/**
 * SensitiveAccessRecordCommand 命令对象，封装写入操作所需参数。
 */
@Data
public class SensitiveAccessRecordCommand {
    private Long tenantId;
    private Long userId;
    private String sensitiveType;
    private String resourceType;
    private String resourceId;
    private String accessReason;
    private String accessResult;
    private String ipAddress;
    private String traceId;
}
