package com.feng.controller;

import com.feng.springcloud.entity.Member;
import com.feng.springcloud.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class MemberConsumerController {
    //public static final String MEMBER_SERVICE_PROVIDER_URL = "http://localhost:10000";
        public static final String MEMBER_SERVICE_PROVIDER_URL =  "http://MEMBER-SERVICE-PROVIDER";

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/member/consumer/save")
    public Result<Member> save(@RequestBody Member member) {
        /**
         * 老韩解读
         * 1. MEMBER_SERVICE_PROVIDER_URL + "/member/save" 请求的 url
         * 2. member 请求参数
         * 3. Result.class http 响应被转换的对象类型
         */
        return restTemplate.postForObject(MEMBER_SERVICE_PROVIDER_URL
                + "/member/save", member, Result.class);
    }

    @GetMapping("/member/consumer/get/{id}")
    public Result<Member> getMemberById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(MEMBER_SERVICE_PROVIDER_URL
                + "/member/get/" + id, Result.class);
    }

    @GetMapping(value = "/member/consumer/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("======== 服 务 名 " + element +
                    "=======================");
            List<ServiceInstance> instances = discoveryClient.getInstances(element);
            for (ServiceInstance instance : instances) {
                System.out.println(instance.getServiceId() + "\t" + instance.getHost()+ "\t" + instance.getPort() + "\t" + instance.getUri());
            }
        }
        return this.discoveryClient;
    }

}