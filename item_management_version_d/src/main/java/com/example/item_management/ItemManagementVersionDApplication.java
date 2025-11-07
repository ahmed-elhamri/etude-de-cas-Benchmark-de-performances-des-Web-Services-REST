package com.example.item_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;

@SpringBootApplication
public class ItemManagementVersionDApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemManagementVersionDApplication.class, args);
        System.out.println("JVM arguments: " + ManagementFactory.getRuntimeMXBean().getInputArguments());
    }

}
