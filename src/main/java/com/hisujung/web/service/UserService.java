package com.hisujung.web.service;

import com.hisujung.web.dto.LoginRequestDto;
import com.hisujung.web.entity.Member;
import com.hisujung.web.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getLoginUserByLoginId(String loginId) {
        return memberRepository.findByEmail(loginId).orElseThrow(() -> new IllegalStateException("해당되는 회원이 존재하지 않습니다."));
    }

    public Member login(LoginRequestDto user) {
        Member member = memberRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("The email is not registered."));

        //Member nullM = memberRepository.findByEmail("null").orElseThrow();
        String password = user.getPassword();
        if (!member.checkPassword(passwordEncoder, password)) {
            //return Optional.ofNullable(nullM);
            throw new IllegalArgumentException("Invalid password.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().name());

        return member;
    }
}
