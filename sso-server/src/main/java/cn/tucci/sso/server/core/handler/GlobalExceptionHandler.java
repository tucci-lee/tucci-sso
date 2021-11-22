package cn.tucci.sso.server.core.handler;

import cn.tucci.sso.server.core.exception.ServiceException;
import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * @author tucci.lee
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Message message;

    public GlobalExceptionHandler(Message message) {
        this.message = message;
    }

    /**
     * 自定义异常捕获
     *
     * @param e ServiceException
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> serviceExceptionHandler(ServiceException e) {
        return Result.fail(e.getCode(), getMessage(e.getMessage()));
    }

    /**
     * 请求query参数校验异常
     *
     * @param e ConstraintViolationException
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String msg = e.getConstraintViolations().iterator().next().getMessage();
        return Result.fail(ResultStatus.PARAMETER_ERROR.code(), getMessage(msg));
    }

    /**
     * POST请求参数校验异常
     *
     * @param e MethodArgumentNotValidException
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.fail(ResultStatus.PARAMETER_ERROR.code(), getMessage(msg));
    }

    /**
     * GET请求封装参数校验异常，参数封装错误也会返回这个异常
     *
     * @param e BindException
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result<?> bindExceptionHandler(BindException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.fail(ResultStatus.PARAMETER_ERROR.code(), getMessage(msg));
    }

    /**
     * 全局异常捕获
     *
     * @param e Throwable
     * @return Result
     */
    @ExceptionHandler(Throwable.class)
    public Result<Object> globalHandler(Throwable e) {
        ResultStatus status;
        if (e instanceof HttpRequestMethodNotSupportedException) {
            // 请求方法不支持
            status = ResultStatus.METHOD_NOT_ALLOWED;
        } else if (e instanceof HttpMessageNotReadableException) {
            // json解析错误
            status = ResultStatus.JSON_PARSE_ERROR;
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            // 媒体类型不支持
            status = ResultStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            // 参数类型转换错误
            status = ResultStatus.PARAMETER_TYPE_ERROR;
        } else {
            status = ResultStatus.SERVER_ERROR;
            log.error("未知异常", e);
        }
        return Result.fail(status.code(), getMessage(status.msg()));
    }

    protected String getMessage(String code) {
        return message.getMessage(code);
    }
}
