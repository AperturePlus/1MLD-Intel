package xenosoft.imldintelligence.common.dto;

import java.util.List;

/**
 * 通用分页响应对象。
 *
 * @param page  当前页码，从 0 开始
 * @param size  当前页大小
 * @param total 满足条件的总记录数
 * @param items 当前页数据
 * @param <T>   列表元素类型
 */
public record PagedResultResponse<T>(
        int page,
        int size,
        long total,
        List<T> items
) {
}
