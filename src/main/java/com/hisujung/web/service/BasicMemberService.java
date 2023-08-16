package com.hisujung.web.service;

import com.hisujung.web.entity.Member;
import com.hisujung.web.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BasicMemberService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow();
    }
}
