package com.feng.springcloud.dao;

import com.feng.springcloud.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    Member queryMemberById(Long id);
    int save(Member member);
}