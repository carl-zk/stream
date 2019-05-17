ref: [https://www.baeldung.com/spring-cloud-stream](https://www.baeldung.com/spring-cloud-stream)

```sh
# visit localhost:15672
docker run -it --hostname rabbit-server \
 --name my-rabbit \
 -v /opt/rabbitmq/var/lib/rabbitmq:/var/lib/rabbitmq \
 -p 5672:5672 -p 15671:15671 -p 15672:15672 \
 rabbitmq:3-management
```

## get start
1. add `@EnableMyStreamListener` annotation;
```java
@SpringBootApplication
@EnableMyStreamListener({Processor.class, CustomListener.class, CustomProducer.class})
public static class StreamApplication {

}
```

2. create custom source and sink;
```java
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
```

3. add listeners
```java
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
```
By default, you won't config destination/group/binder for individual listener. You can use `@MyStreamListener` annotation to custom destination/group/binder.

4. application.yml
```yaml
spring:
  application:
    name: stream-service
  cloud:
    stream:
      bindings:
        default:
          destination: staging
          group-prefix: golden-unicorn
          binder: rabbit
        output:
          destination: staging
          binder: rabbit
        events-booking-start:
          destination: staging
          binder: rabbit
      rabbit:
        bindings:
          input:
            consumer:
              binding-routing-key: entities.user
          output:
            producer:
              routing-key-expression: '"entities.user"'
```
