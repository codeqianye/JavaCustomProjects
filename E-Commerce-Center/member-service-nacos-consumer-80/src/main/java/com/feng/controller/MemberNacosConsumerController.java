package com.feng.controller;

import com.feng.springcloud.entity.Member;
import com.feng.springcloud.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class MemberNacosConsumerController {
    /**
     * http://member-service-nacos-provider Nacos要求这里的服务名要小写,和服
     * 务 Nacos Server 中注册名保持一致
     */
    public static final String MEMBER_SERVICE_NACOS_PROVIDER_URL =
            "http://member-service-nacos-provider";
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/member/nacos/consumer/save")
    public Result<Member> save(Member member) {
        return restTemplate.postForObject(MEMBER_SERVICE_NACOS_PROVIDER_URL +
                        "/member/save", member, Result.class);
    }

    @GetMapping("/member/nacos/consumer/get/{id}")
    public Result<Member> getMemberById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(MEMBER_SERVICE_NACOS_PROVIDER_URL
                + "/member/get/" + id, Result.class);
    }
}