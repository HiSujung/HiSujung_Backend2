package com.hisujung.web.service;


import com.hisujung.web.dto.MemberSignupRequestDto;

public interface MemberService {
    //회원가입
    public Long signUp(MemberSignupRequestDto requestDro) throws Exception;
    public Long join(MemberSignupRequestDto requestDto) throws Exception;
    //public String login(Map<String, String> members);
}

