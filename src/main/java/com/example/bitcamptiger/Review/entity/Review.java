package com.example.bitcamptiger.Review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNum; //리뷰번호
    private String orderNum; //주문번호
    private Long storeId; //상점번호
    private String userId; //유저아이디
    private String reviewContent; //리뷰내용
    private LocalDateTime regDate; //리뷰작성일자
    private Integer score; //별점
}
