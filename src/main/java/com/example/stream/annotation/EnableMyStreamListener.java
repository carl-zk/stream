package com.example.stream.annotation;

import com.example.stream.config.MyStreamListenerConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author carl
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableBinding
@Documented
@Import(MyStreamListenerConfiguration.class)
public @interface EnableMyStreamListener {

    @AliasFor(annotation = EnableBinding.class, value = "value")
    Class<?>[] value() default {};
}
