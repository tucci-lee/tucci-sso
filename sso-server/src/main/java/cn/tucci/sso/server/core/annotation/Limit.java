package cn.tucci.sso.server.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制访问，实现{@link cn.tucci.sso.server.core.aspect.LimitAspect}
 *
 * @author tucci.lee
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limit {

    /**
     * 访问次数
     *
     * @return int
     */
    int rate() default 10;

    /**
     * 访问间隔，单位s
     *
     * @return int
     */
    int rateInterval() default 60;
}
