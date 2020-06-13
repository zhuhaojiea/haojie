package com.fh.shop.admin.aspect;

import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.common.WebContext;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.admin.po.user.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
    @Resource(name="logService")
    private ILogService logService;

    public Object doLog(ProceedingJoinPoint pjp) {
        Object result = null;
        // 获取类名
        String className = pjp.getTarget().getClass().getCanonicalName();
        // 获取方法名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = methodSignature.getName();
        // 判断方法上是否有指定的注解
        String info = "";
        if (method.isAnnotationPresent(com.fh.shop.admin.annotation.Log.class)) {
            // 获取方法上的注解
            com.fh.shop.admin.annotation.Log log = method.getAnnotation(com.fh.shop.admin.annotation.Log.class);
            // 获取注解中的值
            info = log.info();
        }
        // 获取request
        HttpServletRequest request = WebContext.get();
        User user =  (User) request.getSession().getAttribute(SystemConstant.CURR_USER);
        // 通过request获取参数信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        StringBuilder paramInfo = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String[] value = next.getValue();
            paramInfo.append(key).append("=").append(StringUtils.join(value, ",")).append("|");
        }
        try {
            // 执行真正的方法
            result = pjp.proceed();
            // 如果方法是登录方法
            if (methodName.equals(SystemConstant.LOGIN_METHOD)) {
                // 再次获取用户信息
                user = (User) request.getSession().getAttribute(SystemConstant.CURR_USER);
                // 如果获取的用户信息为null,则证明登录失败
                if (user == null) {
                    // 直接返回错误结果，无需进行记录
                    return result;
                }
            }
            String userName = user.getUserName();
            LOGGER.info(userName+"执行"+className+"类的"+methodName+"方法成功");
            // 插入数据库
            Log log = new Log();
            log.setUserName(userName);
            log.setRealName(user.getRealName());
            log.setInsertTime(new Date());
            log.setInfo("执行"+className+"类的"+methodName+"方法成功");
            log.setParamInfo(paramInfo.toString());
            log.setStatus(SystemConstant.STATUS_OK);
            log.setContent(info);
            logService.addLog(log);
        } catch (Throwable throwable) {
            // 打印异常
            throwable.printStackTrace();
            // 日志记录异常
            LOGGER.error(user.getUserName()+"执行了"+className+"类的"+methodName+"方法失败:"+throwable.getMessage());
            // 插入数据库
            Log log = new Log();
            log.setUserName(user.getUserName());
            log.setRealName(user.getRealName());
            log.setInsertTime(new Date());
            log.setInfo("执行了"+className+"类的"+methodName+"方法失败:"+throwable.getMessage());
            log.setStatus(SystemConstant.STATUS_ERROR);
            log.setContent(info);
            log.setParamInfo(paramInfo.toString());
            logService.addLog(log);
            // 抛出异常
            throw new RuntimeException(throwable);
        }
        return result;
    }
}
