package com.feng.service;

import com.feng.springcloud.entity.Member;

public interface MemberService {
    Member queryMemberById(Long id);

    int save(Member member);
}