package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Review")
@SequenceGenerator(
        name = "ReviewSeqGenerator",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "ReviewSeqGenerator")
    @Column(name = "reviewNum")
    private Long reviewNum; //리뷰번호

    private Long orderNum; //포장번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;//상점번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member member;

    @Column
    private String reviewContent; //리뷰내용
    @Column
    private LocalDateTime reviewRegDate = LocalDateTime.now(); //리뷰작성일자
    @Column
    private Integer reviewScore; //별점
    @Column
    private Integer likeCount = 0; //좋아요 수
    @Column
    private Integer disLikeCount = 0; // 싫어요 수


    public ReviewDto EntityToDto() {
        return ReviewDto.builder()
                .reviewNum(this.reviewNum)
                .orderNum(this.orderNum)
                .memberId(this.member.getId())
                .vendorId(this.vendor.getId())
                .reviewContent(this.reviewContent)
                .reviewRegDate(this.reviewRegDate.toString())
                .reviewScore(this.reviewScore)
                .likedCount(this.likeCount)
                .disLikedCount(this.disLikeCount)
                .build();
    }


        //리뷰 생성되거나 수정될 때 vendor 엔티티 업데이트
        public void setVendor(Vendor vendor){
            this.vendor = vendor;
            vendor.updateVendorReviewScore(this);
        }


}
