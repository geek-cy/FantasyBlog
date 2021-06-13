package cn.fantasyblog.anntation;

import java.lang.annotation.*;

/**
 * 自定义访问日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLog {
    /**
     * 访问日志描述信息
     * @return
     */
    String value() default "";
}
