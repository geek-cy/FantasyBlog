package cn.myBlog.anntation;

import java.lang.annotation.*;

/**
 * 自定义访问日志注解
 */
@Documented//是否将包含在JavaDoc中
@Target(ElementType.METHOD)//作用在什么地方
@Retention(RetentionPolicy.RUNTIME)//什么时候使用该注解
public @interface AccessLog {
    /**
     * 访问日志描述信息
     * @return
     */
    String value() default "";
}
