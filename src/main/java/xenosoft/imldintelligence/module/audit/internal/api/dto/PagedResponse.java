package xenosoft.imldintelligence.module.audit.internal.api.dto;

import lombok.Data;
import xenosoft.imldintelligence.module.audit.internal.service.model.PageResult;

import java.util.List;
import java.util.function.Function;

/**
 * Paged 响应对象，封装Paged相关的返回字段。
 */
@Data
public class PagedResponse<T> {
    private int page;
    private int size;
    private long total;
    private List<T> items;

    public static <S, T> PagedResponse<T> from(PageResult<S> source, Function<S, T> mapper) {
        PagedResponse<T> response = new PagedResponse<>();
        response.setPage(source.page());
        response.setSize(source.size());
        response.setTotal(source.total());
        response.setItems(source.items().stream().map(mapper).toList());
        return response;
    }
}
