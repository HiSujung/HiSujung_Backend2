package HiSujung.HiSujung_Backend2.controller;

import HiSujung.HiSujung_Backend2.dto.MemberSignupRequestDto;

import HiSujung.HiSujung_Backend2.jpa.MemberRepository;
import HiSujung.HiSujung_Backend2.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Long join(@Valid @RequestBody MemberSignupRequestDto request) throws Exception {
        return memberService.signUp(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> member) {
        return memberService.login(member);
    }
}