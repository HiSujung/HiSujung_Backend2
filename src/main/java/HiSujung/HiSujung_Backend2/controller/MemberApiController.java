package HiSujung.HiSujung_Backend2.controller;

import HiSujung.HiSujung_Backend2.dto.MemberSignupRequestDto;
import HiSujung.HiSujung_Backend2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public Long join(@RequestBody MemberSignupRequestDto requestDto) throws Exception {
        return memberService.join(requestDto);
    }
}
