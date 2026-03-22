package com.feng;

import com.feng.config.RibbonRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

//加入排除 DataSourceAutoConfiguration 自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableEurekaClient 将该程序标识为 EurekaClient
@EnableEurekaClient
//@EnableDiscoveryClient 注解启用服务发现
@EnableDiscoveryClient
//使用指定的 Ribbon 负载均衡算法规则
@RibbonClient(name = "MEMBER_SERVICE_PROVIDER_URL", configuration = RibbonRule.class)
public class MemberConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberConsumerApplication.class, args);
    }
}