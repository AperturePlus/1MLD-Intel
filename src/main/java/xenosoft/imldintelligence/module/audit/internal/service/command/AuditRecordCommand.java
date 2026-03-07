package xenosoft.imldintelligence.module.audit.internal.service.command;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * AuditRecordCommand 命令对象，封装写入操作所需参数。
 */
@Data
public class AuditRecordCommand {
    private Long tenantId;
    private Long userId;
    private String userRole;
    private String action;
    private String resourceType;
    private String resourceId;
    private JsonNode beforeData;
    private JsonNode afterData;
    private String ipAddress;
    private String deviceInfo;
    private String appVersion;
    private String traceId;
}
