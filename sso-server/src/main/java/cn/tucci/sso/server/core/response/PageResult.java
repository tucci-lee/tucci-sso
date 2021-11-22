package cn.tucci.sso.server.core.response;

import java.util.Collection;

/**
 * 分页返回结果
 *
 * @author tucci.lee
 */
public class PageResult<T> extends Result<Collection<T>> {
    private Long total;

    public PageResult(Collection<T> data, Long total) {
        super(Boolean.TRUE, ResultStatus.OK.code(), ResultStatus.OK.msg(), data);
        this.total = total;
    }

    public static <T> PageResult<T> ok(Collection<T> data, Long total) {
        return new PageResult<T>(data, total);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                "} " + super.toString();
    }
}
