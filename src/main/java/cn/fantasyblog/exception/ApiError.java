package cn.fantasyblog.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 异常返回结果
 * @Author Cy
 * @Date 2021-04-29 19:38
 */
@Data
public class ApiError {
    private Integer status = 400;
    private LocalDateTime time;
    private String message;

    private ApiError(){
        time = LocalDateTime.now();
    }

    public static ApiError error(String message){
        ApiError apiError = new ApiError();
        apiError.setMessage(message);
        return apiError;
    }

    public static ApiError error(Integer status,String message){
        ApiError apiError = new ApiError();
        apiError.setStatus(status);
        apiError.setMessage(message);
        return apiError;
    }
}
