package com.hisujung.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hisujung.web.entity.UnivActivity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UnivActListResponseDto {

    private Long id;
    private String title;
    private String postDepartment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalDateTime deadline;

    private String link;

    public UnivActListResponseDto(UnivActivity entity) {
        this.id = entity.getId();;
        this.title = entity.getTitle();
        this.postDepartment = entity.getPostDepartment();
        this.startDate = entity.getStartDate();
        //this.deadline = entity.getDeadline();
        this.link = entity.getLink();
    }

}
