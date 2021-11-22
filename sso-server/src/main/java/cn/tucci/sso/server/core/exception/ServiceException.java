package cn.tucci.sso.server.core.exception;


import cn.tucci.sso.server.core.response.ResultStatus;

/**
 * @author tucci.lee
 */
public class ServiceException extends RuntimeException {

    private ResultStatus status;

    /**
     * 返回的代码由resultStatus决定
     *
     * @param status 返回状态码和信息
     */
    public ServiceException(ResultStatus status) {
        this.status = status;
    }

    public ResultStatus getStatus(){
        return status;
    }

    public int getCode() {
        return status.code();
    }

    @Override
    public String getMessage(){
        return status.msg();
    }
}
