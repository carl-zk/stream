package com.example.stream.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author carl
 */
public class Init implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        BindingServiceProperties bindingServiceProperties = applicationContext.getBean(BindingServiceProperties.class);
        System.out.println("start init");
        BindingProperties bindingProperties = new BindingProperties();
        bindingProperties.setBinder("rabbit-server");
        bindingProperties.setDestination("staging");
        bindingProperties.setGroup("stream-dev");

        bindingServiceProperties.getBindings().put("input", bindingProperties);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
/*    BindingServiceProperties bindingServiceProperties;

    public Init(BindingServiceProperties bindingServiceProperties) {
        this.bindingServiceProperties = bindingServiceProperties;
    }

    public void init() {
        System.out.println("start init");
        BindingProperties bindingProperties = new BindingProperties();
        bindingProperties.setBinder("rabbit-server");
        bindingProperties.setDestination("staging");
        bindingProperties.setGroup("stream-dev");

        bindingServiceProperties.getBindings().put("input", bindingProperties);
    }*/
}
