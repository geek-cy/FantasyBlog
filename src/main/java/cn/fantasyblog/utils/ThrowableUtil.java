package cn.fantasyblog.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Description 异常工具
 * @Author Cy
 * @Date 2021-03-14 22:59
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
