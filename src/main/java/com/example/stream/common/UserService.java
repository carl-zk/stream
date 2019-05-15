package com.example.stream.common;

import com.example.stream.annotation.MyStreamListener;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * @author carl
 */
@Component
public class UserService {

    @MyStreamListener("user-service.listen")
    public void listen() {

    }

    @StreamListener(Sink.INPUT)
    public void listen2(String msg) {

    }
}
