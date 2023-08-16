package com.hisujung.web.controller;

import com.hisujung.web.dto.PortfolioListResponseDto;
import com.hisujung.web.dto.PortfolioResponseDto;
import com.hisujung.web.dto.PortfolioSaveRequestDto;
import com.hisujung.web.dto.PortfolioUpdateRequestDto;
import com.hisujung.web.entity.Member;
import com.hisujung.web.service.PortfolioService;
import com.hisujung.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolio")
public class PortfolioApiController {

    private final PortfolioService portfolioService;
    private final UserService userService;

    //회원의 포트폴리오 생성
    @PostMapping("/new")
    public Long save(@RequestBody PortfolioSaveRequestDto requestDto) { //dto에 멤버id 포함해 보내도록 함

        return portfolioService.save(requestDto);
    }

    //회원의 포트폴리오 업데이트
    @PostMapping("update/{id}")
    public Long update(@PathVariable Long id, @RequestBody PortfolioUpdateRequestDto requestDto) {
        return portfolioService.update(id, requestDto);
    }

    //회원의 포트폴리오 포트폴리오id(PK) 로 조회
    @GetMapping("{id}")
    public PortfolioResponseDto findById(@PathVariable Long id) {
        return portfolioService.findById(id);
    }

    //로그인한 회원의 포트폴리오 조회
    @GetMapping("portfoliolist")
    public List<PortfolioListResponseDto> findMemberPortfolioList(Authentication auth, Model model){

        Member loginUser = userService.getLoginUserByLoginId(auth.getName());

        if(loginUser != null) {
            model.addAttribute("userName", loginUser.getUsername());
        }
        List<PortfolioListResponseDto> resultList = portfolioService.findAllDescByMember(loginUser.getId());

        //model.addAttribute("portfolioList", resultList);
        //return model
        return resultList;
    }

    //테스트용. 회원id로 포트폴리오 리스트 조회되는지 확인용
    @GetMapping("list/{id}")
    public List<PortfolioListResponseDto> findMemberidPortfolioList(@PathVariable Long id){
        //model.addAttribute("portfolios", portfolioService.findAllDescByMember(id));

//        if(user != null) {
//            model.addAttribute("userName", user.getUserName());
//        }
        List<PortfolioListResponseDto> resultList = portfolioService.findAllDescByMember(id);

        resultList.forEach(p -> System.out.println(p));
        return resultList;
    }

    //포트폴리오 조회
    @GetMapping("/portfolio/{id}")
    public PortfolioResponseDto portfolioUpdate(@PathVariable long id, Model model) {
        PortfolioResponseDto dto = portfolioService.findById(id);
        return dto;
    }

    //포트폴리오 삭제
    @DeleteMapping("/portfolio/{id}")
    public Long delete(@PathVariable("id") Long id) {
        portfolioService.delete(id);
        return id;
    }
}
