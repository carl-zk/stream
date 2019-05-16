package com.example.stream.common;

import com.example.stream.annotation.MyStreamListenerAnnotationBeanPostProcessor;
import com.example.stream.annotation.MyStreamListenerProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.binder.rabbit.RabbitMessageChannelBinder;
import org.springframework.cloud.stream.binder.rabbit.config.RabbitMessageChannelBinderConfiguration;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.binding.StreamListenerAnnotationBeanPostProcessor;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author carl
 */
@Configuration
public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean(name = BindingServiceConfiguration.STREAM_LISTENER_ANNOTATION_BEAN_POST_PROCESSOR_NAME)
    public MyStreamListenerAnnotationBeanPostProcessor streamListenerAnnotationBeanPostProcessor() {
        return new MyStreamListenerAnnotationBeanPostProcessor();
    }

    @Bean
    public MyStreamListenerProcessor myStreamListenerProcessor() {
        return new MyStreamListenerProcessor();
    }
}
