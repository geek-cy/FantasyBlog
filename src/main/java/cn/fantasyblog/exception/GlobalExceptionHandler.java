package cn.fantasyblog.exception;

import cn.fantasyblog.utils.AjaxUtil;
import cn.fantasyblog.utils.RequestHolderUtil;
import cn.fantasyblog.utils.ThrowableUtil;
import cn.hutool.core.io.resource.NoResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Description 全局异常处理
 * @Author Cy
 * @Date 2021-04-29 18:35
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ApplicationContext applicationContext;

    // 防止没有权限的请求绕过前端认证
    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDeniedException(HttpServletRequest request){
        if(AjaxUtil.isAjaxRequest(request)){
            return buildResponseEntity(ApiError.error("您没有权限执行该操作"));
        }
//        log.error(ThrowableUtil.getStackTrace(e));
        return new ModelAndView("error/403");
    }

    @ExceptionHandler(value = NoResourceException.class)
    public Object notFoundException(HttpServletRequest request){
        return new ModelAndView("error/404");
    }
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException e){
        log.error(ThrowableUtil.getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getStatus(),e.getMessage()));
    }

    /**
     * 处理其他不可知的异常
     */
    public Object handleException(Throwable e){
        log.error(ThrowableUtil.getStackTrace(e));
        HttpServletRequest httpServletRequest = RequestHolderUtil.getHttpServletRequest();
        if(AjaxUtil.isAjaxRequest(httpServletRequest)){
            return buildResponseEntity(ApiError.error(500,e.getMessage()));
        } else{
            return new ModelAndView("error/500");
        }
    }



    /**
     * 处理Validated异常
     * 重写ResponseEntityExceptionHandler中的方法
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(getError(ex.getBindingResult().getAllErrors()) , status);
    }

    private ApiError getError(List<ObjectError> allErrors) {
        /*StringBuffer message = new StringBuffer();
        for(ObjectError error: allErrors){
            message.append(error.getDefaultMessage()).append(" & ");
        }
        return ApiError.error(message.substring(0, message.length() - 3));*/
        return ApiError.error(allErrors.get(0).getDefaultMessage());
    }

    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
