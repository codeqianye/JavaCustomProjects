package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MemberNacosConsumerApplication80 {
    public static void main(String[] args) {
        SpringApplication.run(MemberNacosConsumerApplication80.class, args);
    }
}