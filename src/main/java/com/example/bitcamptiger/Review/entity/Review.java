package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_NUM")
    private Long reviewNum; //리뷰번호

    private Long orderNum; //포장번호
    private Long storeId; //상점번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    Member member;

    private String reviewContent; //리뷰내용
    private LocalDateTime regDate = LocalDateTime.now(); //리뷰작성일자
    private Integer score; //별점

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewFile> images = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
    }

    public void updateReview(String reviewContent, Integer score) {
        this.reviewContent = reviewContent;
        this.score = score;
    }


    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
                .reviewNum(review.getReviewNum())
                .orderNum(review.getOrderNum())
                .storeId(review.getStoreId())
                .userId(review.getMember().getId())
                .reviewContent(review.getReviewContent())
                .regDate(review.getRegDate())
                .score(review.getScore())
                .build();
    }

}
