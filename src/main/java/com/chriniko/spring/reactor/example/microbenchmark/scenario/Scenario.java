package com.chriniko.spring.reactor.example.microbenchmark.scenario;

public abstract class Scenario {

    public void execute() {

        System.out.println("Will start to run scenario with name ---> " + scenarioName());
        run();
        System.out.println("Finished running scenario with name ---> " + scenarioName());

    }

    abstract void run();

    abstract String scenarioName();
}
