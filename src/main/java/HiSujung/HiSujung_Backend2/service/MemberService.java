package HiSujung.HiSujung_Backend2.service;

import HiSujung.HiSujung_Backend2.dto.MemberSignupRequestDto;

public interface MemberService {
    //회원가입
    public Long join(MemberSignupRequestDto requestDro) throws Exception;
}
