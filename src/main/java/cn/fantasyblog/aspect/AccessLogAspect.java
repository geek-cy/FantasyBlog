package cn.fantasyblog.aspect;

import cn.fantasyblog.utils.RequestHolderUtil;
import cn.fantasyblog.utils.StringUtils;
import cn.fantasyblog.utils.ThrowableUtil;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.AccessLog;
import cn.fantasyblog.service.AccessLogService;
import cn.fantasyblog.utils.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description 访问日志切面
 * @Author Cy
 * @Date 2021-03-13 17:32
 */
@Aspect //定义切面从而定义切入点和通知
@Component
@Slf4j
public class AccessLogAspect {

    @Autowired
    AccessLogService accessLogService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    // 请求对象
    HttpServletRequest request;

    // 请求浏览器
    String browser;

    // 请求IP
    String ip;

    // 请求方法名
    String methodName;

    // 请求方法参数
    String params;

    // 请求描述
    String description;

    // 获取请求IP来源
    String address;

    // 获取请求用户名
    String userName;

    AccessLog accessLog = new AccessLog();

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(cn.fantasyblog.anntation.AccessLog)")
    public void AccessLog(){
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("AccessLog()")// execution(public ...)
    public void logBefore(JoinPoint joinPoint){
        // 获取当前请求对象
        request = RequestHolderUtil.getHttpServletRequest();
        // 获取请求浏览器
        browser = StringUtils.getBrowser(request);
        // 获取请求IP
        ip = StringUtils.getIp(request);
        // 获取请求方法名
        methodName = StringUtils.getMethodName(joinPoint);
        // 获取方法参数
        params = StringUtils.getParams(joinPoint);
        // 获取请求描述
        description = StringUtils.getAccessDescription(joinPoint);
        // 获取请求IP来源
        address = StringUtils.getCityInfo(ip);
        // 获取请求用户名
        userName = UserInfoUtil.getUserName();
        // 创建时间
        Date date = new Date();
        accessLog.setDescription(description);
        accessLog.setMethod(methodName);
        accessLog.setParams(params);
        accessLog.setRequestIp(ip);
        accessLog.setCreateTime(date);
        accessLog.setUsername(userName);
        accessLog.setBrowser(browser);
        accessLog.setAddress(address);
        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        log.info("描述:{}",description);
        log.info("方法名:{}",methodName);
        log.info("参数名称:{}",params);
        log.info("IP:{}",ip);
        log.info("当前时间:{}",date);
        log.info("用户名:{}",userName);
        log.info("浏览器:{}",browser);
        log.info("地址:{}",address);
    }

    /**
     * 配置环绕通知
     *
     */
    @Around("AccessLog()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        // 记录调用接口的开始时间
        currentTime.set(System.currentTimeMillis());
        // 执行切点
        result = joinPoint.proceed();
        // 执行处理耗时
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        log.info("方法耗时：{}",time);
        accessLog.setLogType("INFO");
        accessLog.setTime(time);
        accessLog.setStatus(Constant.SUCCESS);
        accessLogService.save(accessLog);
        return result;
    }

    /**
     * final通知
     */
    @After("AccessLog()")
    public void logAfter(){
        log.info("========================================== End ==========================================");
    }

    /**
     * 配置异常通知
     */
    @AfterThrowing(pointcut = "AccessLog()",throwing = "e")
    public void logAfterThrowing(Throwable e){
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        String error = ThrowableUtil.getStackTrace(e);
        log.error("错误耗时：{}",time);
        log.error("错误信息:{}",error);
        accessLog.setTime(time);
        accessLog.setLogType("ERROR");
        accessLog.setExceptionDetail(error.getBytes());
        accessLog.setStatus(Constant.FAILURE);
        accessLogService.save(accessLog);
    }
}
