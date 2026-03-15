package xenosoft.imldintelligence.module.audit.api.dto;

import lombok.Data;
import xenosoft.imldintelligence.common.model.SensitiveDataAccessLog;

import java.time.OffsetDateTime;

/**
 * SensitiveAccessLog 响应对象，封装SensitiveAccessLog相关的返回字段。
 */
@Data
public class SensitiveAccessLogResponse {
    private Long id;
    private Long tenantId;
    private Long userId;
    private String sensitiveType;
    private String resourceType;
    private String resourceId;
    private String accessReason;
    private String accessResult;
    private String ipAddress;
    private OffsetDateTime createdAt;

    public static SensitiveAccessLogResponse from(SensitiveDataAccessLog log) {
        SensitiveAccessLogResponse response = new SensitiveAccessLogResponse();
        response.setId(log.getId());
        response.setTenantId(log.getTenantId());
        response.setUserId(log.getUserId());
        response.setSensitiveType(log.getSensitiveType());
        response.setResourceType(log.getResourceType());
        response.setResourceId(log.getResourceId());
        response.setAccessReason(log.getAccessReason());
        response.setAccessResult(log.getAccessResult());
        response.setIpAddress(log.getIpAddress());
        response.setCreatedAt(log.getCreatedAt());
        return response;
    }
}
