package xenosoft.imldintelligence.module.license.internal.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 许可证签名信封模型，封装原始载荷与签名内容。
 */
@Data
public class LicenseEnvelope {
    private JsonNode payload;
    private String signature;
}
