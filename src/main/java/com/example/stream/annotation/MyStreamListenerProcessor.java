package com.example.stream.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author carl
 */
public class MyStreamListenerProcessor implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(MyStreamListenerProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> targetClass = AopUtils.isAopProxy(bean) ? AopUtils.getTargetClass(bean)
                : bean.getClass();
        Method[] uniqueDeclaredMethods = ReflectionUtils
                .getUniqueDeclaredMethods(targetClass);
        for (Method method : uniqueDeclaredMethods) {
            MyStreamListener myStreamListener = AnnotatedElementUtils
                    .findMergedAnnotation(method, MyStreamListener.class);
            if (myStreamListener != null && !method.isBridge()) {
                method.setAccessible(true);
                logger.info("==============================");
                logger.info("method: " + method.getDeclaringClass() + ", " + myStreamListener.value());
            }
        }

        return bean;
    }
}
