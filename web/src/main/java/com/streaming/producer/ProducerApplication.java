package com.streaming.producer;

import lombok.SneakyThrows;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class ProducerApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(ProducerApplication.class, args);
    }

    @SneakyThrows
    public static void restart() {
        Thread.sleep(1000);
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(ProducerApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }

}


