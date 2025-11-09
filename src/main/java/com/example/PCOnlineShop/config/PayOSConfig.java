package com.example.PCOnlineShop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Value("${spring.payos.client-id}") //
    private String clientId;

    @Value("${spring.payos.api-key}") //
    private String apiKey;

    @Value("${spring.payos.checksum-key}") //
    private String checksumKey;

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }
}