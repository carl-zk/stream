package com.example.stream.config;

import com.example.stream.MyStreamListenerAnnotationBeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author carl
 */
@Configuration
public class MyStreamListenerConfiguration {
    private static Logger logger = LoggerFactory.getLogger(MyStreamListenerConfiguration.class);

    @Bean(name = BindingServiceConfiguration.STREAM_LISTENER_ANNOTATION_BEAN_POST_PROCESSOR_NAME)
    public MyStreamListenerAnnotationBeanPostProcessor streamListenerAnnotationBeanPostProcessor() {
        return new MyStreamListenerAnnotationBeanPostProcessor();
    }
}
