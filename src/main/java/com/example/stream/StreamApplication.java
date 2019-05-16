package com.example.stream;

import com.example.stream.annotation.EnableMyStreamListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author hero
 */
@SpringBootApplication
@EnableMyStreamListener(Processor.class)
public class StreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void listen2(String msg) {
        System.out.println(msg);
    }

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
    public MessageSource<String> timerMessageSource() {
        return () -> new GenericMessage<>("Hello Spring Cloud Stream");
    }
}
