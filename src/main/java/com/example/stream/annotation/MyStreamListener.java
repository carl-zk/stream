package com.example.stream.annotation;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author carl
 */
@StreamListener
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyStreamListener {
    @AliasFor(annotation = StreamListener.class, value = "value")
    String value() default "";

    @AliasFor(annotation = StreamListener.class, value = "target")
    String target() default "";

    @AliasFor(annotation = StreamListener.class, value = "condition")
    String condition() default "";

    @AliasFor(annotation = StreamListener.class, value = "copyHeaders")
    String copyHeaders() default "true";

    String destination() default "";

    String group() default "";

    String binder() default "";
}
