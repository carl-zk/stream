package com.example.stream.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author carl
 */
@Component
public class MyStreamListenerProcessor implements BeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(MyStreamListenerProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getAnnotation(MyStreamListener.class) != null)
                .collect(Collectors.toList());
        methods.forEach(method -> {
            MyStreamListener myStreamListener = method.getAnnotation(MyStreamListener.class);
            method.setAccessible(true);
            logger.info("==============================");
            logger.info("method: " + method.getDeclaringClass() + ", " + myStreamListener.value());
        });
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
