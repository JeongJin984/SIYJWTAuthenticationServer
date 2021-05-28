package com.example.jwt;

import com.example.jwt.common.SimpleListener;
import com.example.jwt.feign.error.FeignErrorDecoder;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.addListeners(new SimpleListener());
        springApplication.run(JwtApplication.class, args);
    }

    @Bean
    Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
