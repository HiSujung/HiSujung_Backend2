package HiSujung.HiSujung_Backend2.controller;

import HiSujung.HiSujung_Backend2.dto.MemberSignupRequestDto;
import HiSujung.HiSujung_Backend2.service.EmailService;
import HiSujung.HiSujung_Backend2.service.MemberService;
//import HiSujung.HiSujung_Backend2.service.MemberServicelmpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;
//    private final MemberServicelmpl memberServicelmpl;
    private final EmailService emailService;

    @PostMapping("/join")
    public Long join(@RequestBody MemberSignupRequestDto requestDto) throws Exception {
        return memberService.join(requestDto);
        //return 1L;
    }

    @PostMapping("/join/mailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }
//
//    @PostMapping("/emails/verification-requests")
//    public ResponseEntity sendMessage(@RequestParam("email") @Valid @CustomEmail String email) {
//        memberServicelmpl.sendCodeToEmail(email);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/emails/verifications")
//    public ResponseEntity verificationEmail(@RequestParam("email") @Valid @CustomEmail String email,
//                                            @RequestParam("code") String authCode) {
//        EmailVerificationResult response = memberServicelmpl.verifiedCode(email, authCode);
//
//        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
//    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> member) {
        return memberService.login(member);
    }
}
