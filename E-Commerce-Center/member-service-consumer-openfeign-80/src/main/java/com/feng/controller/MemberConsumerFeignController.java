package com.feng.controller;

import com.feng.service.MemberFeignService;
import com.feng.springcloud.entity.Member;
import com.feng.springcloud.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberConsumerFeignController {
    @Resource
    private MemberFeignService memberFeignService;

    @GetMapping(value = "/member/consumer/openfeign/get/{id}")
    public Result<Member> getMemberById(@PathVariable("id") Long id) {
        return memberFeignService.getMembertById(id);
    }
}