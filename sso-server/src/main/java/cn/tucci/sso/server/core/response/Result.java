package cn.tucci.sso.server.core.response;

/**
 * @author tucci.lee
 */
public class Result<T> {

    private Boolean status;
    private Integer code;
    private String msg;
    private T data;

    protected Result(boolean status, int code, String msg, T data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(Boolean.TRUE, ResultStatus.OK.code(), ResultStatus.OK.msg(), data);
    }

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(Boolean.FALSE, code, msg, null);
    }

    public static <T> Result<T> fail(ResultStatus status) {
        return fail(status.code(), status.msg());
    }

    public Boolean getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
