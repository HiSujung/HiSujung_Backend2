package com.hisujung.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
@RequiredArgsConstructor
public class SesConfig {
    private final Environment env;

    @Bean
    public SesClient sesClient() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(env.getProperty("aws.ses.access-id"), env.getProperty("aws.ses.secret-key"));
        return SesClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}
