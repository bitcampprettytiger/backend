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

    private Long orderNum; //주문번호
    private Long storeId; //상점번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member member;

    private String reviewContent; //리뷰내용
    private LocalDateTime regDate = LocalDateTime.now(); //리뷰작성일자
    private Integer score; //별점

    @OneToMany(mappedBy = "review")
    private List<ReviewFile> images = new ArrayList<>();

    public void setMember(Member member) { this.member = member;}

    private ReviewDto EntitiyToDto() {
        ReviewDto reviewDto = ReviewDto.builder()
                .reviewNum(this.reviewNum)
                .orderNum(this.orderNum)
                .storeId(this.storeId)
                .reviewContent(this.reviewContent)
                .regDate(this.regDate)
                .score(this.score)
                .build();
        return reviewDto;
    }


}
