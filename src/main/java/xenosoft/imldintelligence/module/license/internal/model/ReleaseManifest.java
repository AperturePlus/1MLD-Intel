package xenosoft.imldintelligence.module.license.internal.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * 发布清单模型，描述目标版本、发布日期与补丁属性。
 */
@Data
public class ReleaseManifest {
    private String version;
    private LocalDate releaseDate;
    private boolean securityPatch;
    private String channel;
}
