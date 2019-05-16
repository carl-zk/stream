package com.example.stream.annotation;

import com.example.stream.StreamApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binding.BindingBeanDefinitionRegistryUtils;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.binding.StreamListenerAnnotationBeanPostProcessor;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.cloud.stream.messaging.Sink;

import java.lang.reflect.Method;

/**
 * @author carl
 */
public class MyStreamListenerAnnotationBeanPostProcessor extends StreamListenerAnnotationBeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(MyStreamListenerAnnotationBeanPostProcessor.class);

    @Autowired
    BindingService bindingService;

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;



    @Override
    protected StreamListener postProcessAnnotation(StreamListener originalAnnotation, Method annotatedMethod) {
        logger.info("==============================");
        logger.info("stream processor ");

        BindingServiceProperties bindingServiceProperties = bindingService.getBindingServiceProperties();
        BindingProperties bindingProperties = new BindingProperties();
        bindingProperties.setDestination("dest-a");
        bindingProperties.setGroup("group-a");
        bindingProperties.setBinder("rabbit-server");

        bindingServiceProperties.getBindings().put("input", bindingProperties);

/*        BindingBeanDefinitionRegistryUtils.registerBindingTargetBeanDefinitions(
                Sink.class, Sink.class.getName(), defaultListableBeanFactory);
        BindingBeanDefinitionRegistryUtils
                .registerBindingTargetsQualifiedBeanDefinitions(StreamApplication.class, Sink.class, defaultListableBeanFactory);*/

        return super.postProcessAnnotation(originalAnnotation, annotatedMethod);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //bindingService.bindConsumer(DirectWithAttributesChannel.class, "input");
        return bean;
    }
}
