package com.example.item_management.config;

import com.example.item_management.controllers.CategoryRestJaxRSAPI;
import com.example.item_management.controllers.ItemRestJaxRSAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public ResourceConfig resourceConfig() {
        ResourceConfig jerseyServlet = new ResourceConfig();
        jerseyServlet.register(CategoryRestJaxRSAPI.class);
        jerseyServlet.register(ItemRestJaxRSAPI.class);
        return jerseyServlet;
    }
}