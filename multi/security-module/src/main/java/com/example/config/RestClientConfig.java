package com.example.config;

import com.example.enumeration.ServiceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    @Bean
    public RestClient memberRestClient() {

        return RestClient.builder()
                .baseUrl(ServiceInfo.MEMBER_MODULE.getBaseUrl())
                .build();
    }

    @Bean
    public RestClient boardRestClient() {

        return RestClient.builder()
                .baseUrl(ServiceInfo.BOARD_MODULE.getBaseUrl())
                .build();
    }


}
