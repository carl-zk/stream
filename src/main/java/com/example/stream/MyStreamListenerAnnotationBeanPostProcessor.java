package com.example.stream;

import com.example.stream.annotation.MyStreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.binding.StreamListenerAnnotationBeanPostProcessor;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author carl
 */
public class MyStreamListenerAnnotationBeanPostProcessor extends StreamListenerAnnotationBeanPostProcessor {
    private Logger logger = LoggerFactory.getLogger(MyStreamListenerAnnotationBeanPostProcessor.class);
    /**
     * StreamListener value only support '[a-z]' '-'
     */
    private static final String LISTENER_NAME_PATTERN = "(^[a-z]+[^.]$)|(^([a-z]+-)+[a-z]+[^.]$)";

    @Autowired
    BindingService bindingService;
    @Autowired
    Environment environment;

    @Override
    protected StreamListener postProcessAnnotation(StreamListener originalAnnotation, Method annotatedMethod) {
        String value = StringUtils.isEmpty(originalAnnotation.value()) ? originalAnnotation.target() : originalAnnotation.value();
        restrictListenerValue(value);

        logger.info("StreamListener.postProcessAnnotation : {}", value);

        BindingServiceProperties bindingServiceProperties = bindingService.getBindingServiceProperties();
        BindingProperties bindingProperties = bindingServiceProperties.getBindings().get(value);

        String defaultDestination = environment.getProperty("spring.cloud.stream.bindings.default.destination");
        String defaultGroup = formatGroup(environment.getProperty("spring.cloud.stream.bindings.default.group-prefix"),
                environment.getProperty("spring.application.name"), value);
        String defaultBinder = environment.getProperty("spring.cloud.stream.bindings.default.binder");

        MyStreamListener myStreamListener = extract(annotatedMethod);
        if (myStreamListener != null) {
            defaultDestination = StringUtils.isEmpty(myStreamListener.destination()) ? defaultDestination : myStreamListener.destination();
            defaultGroup = StringUtils.isEmpty(myStreamListener.group()) ? defaultGroup :
                    formatGroup(environment.getProperty("spring.cloud.stream.bindings.default.group-prefix"), myStreamListener.group(), value);
            defaultBinder = StringUtils.isEmpty(myStreamListener.binder()) ? defaultBinder : myStreamListener.binder();
        }

        if (bindingProperties == null) {
            bindingProperties = new BindingProperties();
        }

        bindingProperties.setDestination(defaultDestination);
        bindingProperties.setGroup(defaultGroup);
        bindingProperties.setBinder(defaultBinder);

        bindingServiceProperties.getBindings().put(value, bindingProperties);
        return super.postProcessAnnotation(originalAnnotation, annotatedMethod);
    }

    private void restrictListenerValue(String value) {
        if (StringUtils.isEmpty(value) ||
                !value.matches(LISTENER_NAME_PATTERN)) {
            throw new IllegalArgumentException("StreamListener's value Illegal : " + value);
        }
    }

    private String formatGroup(String prefix, String group, String value) {
        return prefix + "." + group + "." + value.replaceAll("-", ".");
    }

    private MyStreamListener extract(Method method) {
        MyStreamListener myStreamListener = AnnotatedElementUtils
                .findMergedAnnotation(method, MyStreamListener.class);
        if (myStreamListener != null && !method.isBridge()) {
            return myStreamListener;
        }
        return null;
    }
}
