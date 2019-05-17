package com.example.stream;

import com.example.stream.annotation.EnableMyStreamListener;
import com.example.stream.annotation.MyStreamListener;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.stream.StreamApplicationTests.StreamApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StreamApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class StreamApplicationTests {
    static Logger logger = LoggerFactory.getLogger(StreamApplicationTests.class);

    @Autowired
    Processor processor;
    @Autowired
    CustomListener customListener;

    @Test
    public void contextLoads() {
        processor.input().send(MessageBuilder.withPayload("hello world").build());
    }

    @Test
    public void produceEvent() {
        customListener.listenOnEntitiesCompany().send(MessageBuilder.withPayload("company created").build());
    }


    @SpringBootApplication
    @EnableMyStreamListener({Processor.class, CustomListener.class, CustomProducer.class})
    public static class StreamApplication {

        public static void main(String[] args) {
            SpringApplication.run(StreamApplication.class, args);
        }

        @MyStreamListener(value = Sink.INPUT, group = "group-a")
        public void listen2(String msg) {
            logger.info("listen2 : {}", msg);
            Assert.assertTrue("hello world".equals(msg));
        }

        @StreamListener(CustomListener.ENTITIES_COMPANY)
        public void listen1(String msg) {
            logger.info("listen1 : {}", msg);
            Assert.assertTrue("company created".equals(msg));
        }

        @Bean
        @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "1"))
        public MessageSource<String> timerMessageSource() {
            return () -> new GenericMessage<>("Hello Spring Cloud Stream");
        }
    }

    public interface CustomListener {
        String ENTITIES_COMPANY = "entities-company";

        @Input(ENTITIES_COMPANY)
        SubscribableChannel listenOnEntitiesCompany();
    }

    public interface CustomProducer {
        String EVENTS_BOOKING_START = "events-booking-start";

        @Output(EVENTS_BOOKING_START)
        MessageChannel produceEventsBookingStart();
    }
}
