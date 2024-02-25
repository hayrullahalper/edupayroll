package com.incubator.edupayroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EdupayrollApplication {

    private final ServerProperties properties;

    @Autowired
    public EdupayrollApplication(ServerProperties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(EdupayrollApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        String port = properties.getPort().toString();
        String host = properties.getAddress().getHostAddress();

        System.out.printf("\nedupayroll application started at \n\t+ http://%s:%s \uD83D\uDE80\uD83D\uDE80\n", host, port);
    }

}
