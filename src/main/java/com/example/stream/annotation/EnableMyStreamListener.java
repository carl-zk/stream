package com.example.stream.annotation;

import com.example.stream.common.Config;
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
@Import(Config.class)
public @interface EnableMyStreamListener {

    @AliasFor(annotation = EnableBinding.class, value = "value")
    Class<?>[] value() default {};
}
