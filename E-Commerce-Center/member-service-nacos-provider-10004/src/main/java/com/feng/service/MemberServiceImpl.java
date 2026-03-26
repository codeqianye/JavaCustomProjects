package com.feng.service;

import com.feng.springcloud.entity.Member;
import com.feng.dao.MemberDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberDao memberDao;

    @Override
    public Member queryMemberById(Long id) {
        return memberDao.queryMemberById(id);
    }

    @Override
    public int save(Member member) {
        return memberDao.save(member);
    }
}