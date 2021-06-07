package cn.fantasyblog.aspect;

import cn.fantasyblog.utils.RequestHolderUtil;
import cn.fantasyblog.utils.StringUtils;
import cn.fantasyblog.utils.ThrowableUtil;
import cn.fantasyblog.common.Constant;
import cn.fantasyblog.entity.OperationLog;
import cn.fantasyblog.service.OperationLogService;
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
 * @Description 操作日志切面
 * @Author Cy
 * @Date 2021-03-13 17:33
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Autowired
    OperationLogService operationLogService;

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

    OperationLog operationLog = new OperationLog();
    /**
     * 切入点方法
     */
    @Pointcut("@annotation(cn.fantasyblog.anntation.OperationLog)")
    public void OperationLog(){}

    /**
     * 在切点之前
     * @param joinPoint
     */
    @Before("OperationLog()")
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
        description = StringUtils.getOperationDescription(joinPoint);
        // 获取请求IP来源
        address = StringUtils.getCityInfo(ip);
        // 获取请求用户名
        userName = UserInfoUtil.getUserName();
        // 创建时间
        Date date = new Date();
        operationLog.setDescription(description);
        operationLog.setMethod(methodName);
        operationLog.setParams(params);
        operationLog.setRequestIp(ip);
        operationLog.setCreateTime(date);
        operationLog.setUsername(userName);
        operationLog.setBrowser(browser);
        operationLog.setAddress(address);
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
     * 环绕通知
     */
    @Around("OperationLog()")
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
        operationLog.setLogType("INFO");
        operationLog.setTime(time);
        operationLog.setStatus(Constant.SUCCESS);
        operationLogService.save(operationLog);
        return result;
    }

    @After("OperationLog()")
    public void logAfter(){
        log.info("========================================== End ==========================================");
    }

    /**
     * 配置异常通知
     */
    @AfterThrowing(pointcut = "OperationLog()",throwing = "e")
    public void logAfterThrowing(Throwable e){
        long time = System.currentTimeMillis() - currentTime.get();
        currentTime.remove();
        String error = ThrowableUtil.getStackTrace(e);
        log.error("错误耗时：{}",time);
        log.error("错误信息:{}",error);
        operationLog.setTime(time);
        operationLog.setLogType("ERROR");
        operationLog.setExceptionDetail(error.getBytes());
        operationLog.setStatus(Constant.FAILURE);
        operationLogService.save(operationLog);
    }
}
