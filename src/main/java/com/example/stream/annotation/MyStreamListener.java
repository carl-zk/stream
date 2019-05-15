package com.example.stream.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.lang.annotation.*;

/**
 * @author carl
 */

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@MessageMapping
@Documented
public @interface MyStreamListener {
    @AliasFor("target")
    String value() default "";

    @AliasFor("value")
    String target() default "";

    String condition() default "";

    String copyHeaders() default "true";

    String routingKey() default "";
}
