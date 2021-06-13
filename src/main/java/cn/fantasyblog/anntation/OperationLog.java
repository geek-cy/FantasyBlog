package cn.fantasyblog.anntation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {
    /**
     * 操作日志描述信息
     * @return
     */
    String value() default "";
}
