package com.chriniko.spring.reactor.example.configuration;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.config.DispatcherType;
import reactor.core.dispatch.ThreadPoolExecutorDispatcher;
import reactor.core.dispatch.WorkQueueDispatcher;
import reactor.core.dispatch.wait.AgileWaitingStrategy;
import reactor.core.dispatch.wait.ParkWaitStrategy;
import reactor.jarjar.com.lmax.disruptor.PhasedBackoffWaitStrategy;
import reactor.jarjar.com.lmax.disruptor.YieldingWaitStrategy;
import reactor.jarjar.com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;

@Log4j
@Configuration
public class AppConfiguration {

    public static final int NUMBER_OF_TICKETS_TO_PURCHASE = 1_000;


    /*
        NOTE:
        Reactor has four dispatchers to pick from: synchronous, ring buffer, thread pool, and event loop.

        Synchronous is typically used inside a consumer, especially if you use Stream s and Promise s.

        Ring buffer is used for large volumes of non-blocking events and is based on the LMAX disruptor.

        Thread pool is ideal for longer running tasks that might be IO bound, and when it doesn’t matter what thread they are run on.

        Event loop is used when you need all events on the exact same thread.
     */
    @Bean
    public EventBus eventBus() {


        return EventBus.create(
                environment(),
                //workQueueDispatcher() // Note: play with this.
                threadPoolExecutorDispatcher() // Note: play with this.
                //Environment.newDispatcher(1000, 60, DispatcherType.THREAD_POOL_EXECUTOR) // Note: play with this.
        );
    }


    // --- START: play ground ---

    @Bean
    public Boolean addIo() {
        return true; // Note: play with this.
    }

    private ThreadPoolExecutorDispatcher threadPoolExecutorDispatcher() {
        return new ThreadPoolExecutorDispatcher(70, 1800);
    }

    private WorkQueueDispatcher workQueueDispatcher() {
        return new WorkQueueDispatcher(
                "work-queue-dispatcher",
                72,
                2048,
                throwable -> log.error("Error occurred!", throwable),
                ProducerType.MULTI,
                new AgileWaitingStrategy()
        );
    }

    // --- END: play ground ---

    @Bean
    public CountDownLatch countDownLatch() {
        return new CountDownLatch(NUMBER_OF_TICKETS_TO_PURCHASE);
    }

    private Environment environment() {
        return Environment
                .initializeIfEmpty()
                .assignErrorJournal();
    }

}
