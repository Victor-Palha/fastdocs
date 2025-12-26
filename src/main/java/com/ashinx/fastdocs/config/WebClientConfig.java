package com.ashinx.fastdocs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient cognitoWebClient(
            @Value("${aws.cognito.domain}") String domain
    ) {
        return WebClient.builder()
                .baseUrl("https://" + domain)
                .build();
    }
}
