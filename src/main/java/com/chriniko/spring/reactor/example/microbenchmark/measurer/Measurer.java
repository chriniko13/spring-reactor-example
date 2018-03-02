package com.chriniko.spring.reactor.example.microbenchmark.measurer;

import com.chriniko.spring.reactor.example.configuration.AppConfiguration;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

@Component
public class Measurer {

    public void measure(Pair<String, WorkToMeasure> workToMeasurePair) {

        long startTime = System.nanoTime();

        workToMeasurePair.getValue1().run();

        long estimatedTime = System.nanoTime() - startTime;

        System.out.printf("\n------METRICS FOR WORK WITH NAME = %s ------\n", workToMeasurePair.getValue0());

        System.out.println("    estimatedTime(ms) = " + TimeUnit.MILLISECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS));
        System.out.println("    estimatedTime(s) = " + TimeUnit.SECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS));

        System.out.println("    averageTime per unit(ms) = " + getDivideForEstimatedTimeInMs(estimatedTime));

        System.out.println("--------------------------------------\n");

    }

    private BigDecimal getDivideForEstimatedTimeInMs(long estimatedTime) {
        return BigDecimal
                .valueOf(TimeUnit.MILLISECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS))
                .divide(BigDecimal.valueOf(AppConfiguration.NUMBER_OF_TICKETS_TO_PURCHASE), RoundingMode.CEILING);
    }

}
