package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//加入排除 DataSourceAutoConfiguration 自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableEurekaClient 将该程序标识为 EurekaClient
@EnableEurekaClient
public class MemberConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberConsumerApplication.class, args);
    }
}