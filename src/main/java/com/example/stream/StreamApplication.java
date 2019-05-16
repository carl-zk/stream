package com.example.stream;

import com.example.stream.annotation.MyStreamListener;
import com.example.stream.annotation.MyStreamListenerProcessor;
import com.example.stream.common.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author hero
 */
@SpringBootApplication
@EnableBinding(Sink.class)
@Import(Config.class)
public class StreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }

    @MyStreamListener("user-service.listen")
    public void listen() {

    }

    @StreamListener(Sink.INPUT)
    public void listen2(String msg) {

    }
}
