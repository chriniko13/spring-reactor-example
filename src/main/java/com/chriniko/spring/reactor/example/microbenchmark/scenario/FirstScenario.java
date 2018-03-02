package com.chriniko.spring.reactor.example.microbenchmark.scenario;

import com.chriniko.spring.reactor.example.configuration.AppConfiguration;
import com.chriniko.spring.reactor.example.dto.TicketRequestDto;
import com.chriniko.spring.reactor.example.microbenchmark.measurer.Measurer;
import com.chriniko.spring.reactor.example.microbenchmark.measurer.WorkToMeasure;
import com.chriniko.spring.reactor.example.publisher.TicketPurchasedEventPublisher;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@Component
public class FirstScenario extends Scenario {

    private final Measurer measurer;
    private final TicketPurchasedEventPublisher ticketPurchasedEventPublisher;
    private final CountDownLatch countDownLatch;

    @Autowired
    public FirstScenario(Measurer measurer, TicketPurchasedEventPublisher ticketPurchasedEventPublisher, CountDownLatch countDownLatch) {
        this.measurer = measurer;
        this.ticketPurchasedEventPublisher = ticketPurchasedEventPublisher;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        WorkToMeasure workToMeasure = () -> {

            Random random = new Random();

            IntStream.rangeClosed(1, AppConfiguration.NUMBER_OF_TICKETS_TO_PURCHASE)
                    .forEach(idx -> {

                        int randomInt = random.nextInt(200);

                        ticketPurchasedEventPublisher.purchase(
                                new TicketRequestDto(
                                        "event " + randomInt,
                                        BigDecimal.valueOf(randomInt),
                                        "seat " + randomInt)
                        );
                    });

            System.out.println("!!!ALL WORK SUBMITTED SUCCESSFULLY!!!");

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        measurer.measure(Pair.with(scenarioName(), workToMeasure));
    }

    @Override
    String scenarioName() {
        return "Scenario which produces " + AppConfiguration.NUMBER_OF_TICKETS_TO_PURCHASE + " tickets.";
    }
}
