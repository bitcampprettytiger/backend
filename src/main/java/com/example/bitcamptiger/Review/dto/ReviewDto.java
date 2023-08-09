package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewNum;
    private Long orderNum;
    private Long storeId;
    private Long userId;
    private String reviewContent;
    private LocalDateTime regDate;
    private Integer score;

    public Review DtoToEntity() {
        Review review = Review.builder()
                .reviewNum(this.reviewNum)
                .orderNum(this.orderNum)
                .storeId(this.storeId)
                .member(Member.builder().id(this.userId).build())
                .reviewContent(this.reviewContent)
                .regDate(this.regDate)
                .score(this.score)
                .build();

        return review;
    }
}
