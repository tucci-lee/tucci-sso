package cn.tucci.sso.server.core.utils;


import cn.tucci.sso.server.core.exception.ServiceException;
import cn.tucci.sso.server.core.response.ResultStatus;

import java.util.Collection;
import java.util.Map;

/**
 * 断言
 * <p>
 * 传入ResultStatus类型的为抛出自定义code的错误信息
 *
 * @author tucci.lee
 */
public class Assert {

    public static void isNull(Object obj, ResultStatus resultStatus) {
        if (obj != null) {
            throw new ServiceException(resultStatus);
        }
    }

    public static void notNull(Object obj, ResultStatus resultStatus) {
        if (obj == null) {
            throw new ServiceException(resultStatus);
        }
    }

    public static void isTrue(boolean expression, ResultStatus resultStatus) {
        if (!expression) {
            throw new ServiceException(resultStatus);
        }
    }

    public static void notEmpty(CharSequence obj, ResultStatus resultStatus) {
        if (obj == null || obj.length() == 0) {
            throw new ServiceException(resultStatus);
        }
    }

    public static void notEmpty(Collection<?> collection, ResultStatus resultStatus) {
        if (collection == null || collection.isEmpty()) {
            throw new ServiceException(resultStatus);
        }
    }

    public static void notEmpty(Map<?, ?> map, ResultStatus resultStatus) {
        if (map == null || map.isEmpty()) {
            throw new ServiceException(resultStatus);
        }
    }
}
