package cn.fantasyblog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @Description 自定义异常
 * @Author Cy
 * @Date 2021-03-14 17:06
 */

@Getter
public class BadRequestException extends RuntimeException{

    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String msg){
        super(msg);
    }

    public BadRequestException(HttpStatus status,String msg){
        super(msg);
        this.status = status.value();
    }
    /**
     * 不打印堆栈信息
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }



}
