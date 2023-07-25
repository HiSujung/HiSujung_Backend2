package HiSujung.HiSujung_Backend2.service;

import HiSujung.HiSujung_Backend2.dto.MemberSignupRequestDto;
import java.util.Map;

public interface MemberService {
    //회원가입
    public Long signUp(MemberSignupRequestDto requestDro) throws Exception;
    public String login(Map<String, String> members);
}
