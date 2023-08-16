package com.hisujung.web.dto;

import com.hisujung.web.entity.Member;
import com.hisujung.web.entity.Portfolio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortfolioSaveRequestDto {

    private Long memberId;
    private String title;
    private String urlLink;
    private String description;

    @Builder
    public PortfolioSaveRequestDto(Long memberId, String title, String urlLink, String description) {
        this.memberId = memberId;
        this.title = title;
        this.urlLink = urlLink;
        this.description = description;
    }

    public Portfolio toEntity(Member member) {
        return Portfolio.builder()
                .member(member)
                .title(title)
                .urlLink(urlLink)
                .description(description)
                .build();
    }

}
