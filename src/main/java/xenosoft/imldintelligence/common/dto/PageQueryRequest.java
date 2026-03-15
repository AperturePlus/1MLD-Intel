package xenosoft.imldintelligence.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * 通用分页查询参数。
 *
 * <p>所有列表接口统一复用该对象，避免模块之间自行扩展出不一致的分页语义。</p>
 */
public record PageQueryRequest(
        @Min(value = 0, message = "page must be >= 0")
        Integer page,
        @Min(value = 1, message = "size must be >= 1")
        @Max(value = 200, message = "size must be <= 200")
        Integer size
) {
}
