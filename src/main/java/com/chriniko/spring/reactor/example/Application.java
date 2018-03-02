package com.chriniko.spring.reactor.example;

import com.chriniko.spring.reactor.example.microbenchmark.scenario.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    private List<Scenario> scenarios;

    @Override
    public void run(String... strings) {
        scenarios.forEach(Scenario::execute);
    }
}
