package com.hisujung.web.dto;

import com.hisujung.web.entity.Portfolio;
import lombok.Getter;

@Getter
public class PortfolioResponseDto {

    private Long id;
    private String title;
    private String urlLink;
    private String description;

    public PortfolioResponseDto(Portfolio entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.urlLink = entity.getUrlLink();
        this.description = entity.getDescription();
    }
}
