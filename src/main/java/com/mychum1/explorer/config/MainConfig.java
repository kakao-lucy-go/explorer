package com.mychum1.explorer.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@Configuration
public class MainConfig {
    @Bean
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer().start();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
