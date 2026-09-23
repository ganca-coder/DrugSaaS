package com.adrug.erp.common.core.aspect;

import com.adrug.erp.common.core.anno.ProviderService;
import com.adrug.erp.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * TODO
 *
 * @author 甘成安
 * @date 2026/9/18 12:33
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Slf4j
@Aspect
@Component
public class OpenFeignAspect {
    @Autowired
    private ApplicationContext applicationContext;

    @Around("@within(org.springframework.cloud.openfeign.FeignClient)")
    public Object aroundFeignClient(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前调用的方法信息
        if (joinPoint.getSignature() instanceof MethodSignature methodSignature) {
            Method method = methodSignature.getMethod();
            ProviderService providerService = method.getAnnotation(ProviderService.class);
            Class clazz = providerService.provider();
            String name = providerService.method();
            Class[] argTypes = providerService.args();
            if (argTypes.length == 0) {
                argTypes = methodSignature.getParameterTypes();
            }
            Map<String, Object> providerObjMap = applicationContext.getBeansOfType(clazz);
            Object providerObj;
            Object value;
            if (!providerObjMap.isEmpty()) {
                providerObj = providerObjMap.values().stream().findFirst().get();
                value = providerInvoke(providerObj, name, argTypes, joinPoint.getArgs());
                return Result.success(value);
            }

//            Class<?> declaringClass = method.getDeclaringClass(); // @FeignClient 注解的接口
//            FeignClient feignClient = declaringClass.getAnnotation(FeignClient.class);
//            String bizName = feignClient.name();
//
//            try {
//                if (ArkClient.getBizManagerService() == null) {
//                    return joinPoint.proceed();
//                }
//                providerObjMap = SpringServiceFinder.listModuleServices(bizName, "1.0.0-SNAPSHOT", clazz);
//                if (providerObjMap.isEmpty()) {
//                    return joinPoint.proceed();
//                }
//                providerObj = providerObjMap.values().stream().findFirst().get();
//                value = providerInvoke(providerObj, name, argTypes, joinPoint.getArgs());
//                return Result.success(value);
//            } catch (BizRuntimeException e) {
//                log.error("BizRuntimeException异常" + e.getErrorCode(), e);
//                if (e.getErrorCode().equals("100003")) {
//                    return joinPoint.proceed();
//                } else {
//                    throw e;
//                }
//            } catch (ClassNotFoundException e) {
//                if (e.getMessage().indexOf("com.alipay.sofa.ark") > 0) {
//                    return joinPoint.proceed();
//                } else {
//                    throw e;
//                }
//            }
        }
        // 不满足条件，继续执行原有Feign调用
        return joinPoint.proceed();
    }

    private Object providerInvoke(Object providerObj, String name, Class[] argTypes, Object[] args) throws Exception {
        Method providerMethod = providerObj.getClass().getMethod(name, argTypes);
        Object value = providerMethod.invoke(providerObj, args);
        return value;
    }
}
