package xenosoft.imldintelligence.module.audit.internal.service.model;

import java.util.List;

/**
 * 分页结果对象，用于统一封装列表数据与总量信息。
 */
public record PageResult<T>(int page, int size, long total, List<T> items) {
}
