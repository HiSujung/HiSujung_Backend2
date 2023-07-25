package HiSujung.HiSujung_Backend2.service;

import HiSujung.HiSujung_Backend2.dto.MemberSignupRequestDto;
import HiSujung.HiSujung_Backend2.entity.Member;
import HiSujung.HiSujung_Backend2.jpa.MemberRepository;
import HiSujung.HiSujung_Backend2.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MemberServicelmpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public Long signUp(MemberSignupRequestDto requestDto) throws Exception {
        if (memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getCheckedPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        member.encodePassword(passwordEncoder);

        member.addUserAuthority();

        return member.getId();
    }

    @Override
    public String login(Map<String, String> members) {

        Member member = memberRepository.findByEmail(members.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 Email 입니다."));

        String password = members.get("password");
        if (!member.checkPassword(passwordEncoder, password)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().name());

        return jwtTokenProvider.createToken(member.getUsername(), roles);
    }

}
