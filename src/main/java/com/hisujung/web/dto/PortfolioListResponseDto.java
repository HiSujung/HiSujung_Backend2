package com.hisujung.web.dto;

import com.hisujung.web.entity.Member;
import com.hisujung.web.entity.Portfolio;
import lombok.Getter;

@Getter
public class PortfolioListResponseDto {
    private Long id;
    private String title;
    private String urlLink;
    private Member member;
    private String description;

    public PortfolioListResponseDto(Portfolio entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.urlLink = entity.getUrlLink();
        this.member = entity.getMember();
        this.description = entity.getDescription();
    }
}
