package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Review")
@SequenceGenerator(
        name = "ReviewSeqGenerator",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "ReviewSeqGenerator")
    private Long reviewNum; //리뷰번호

    private Long orderNum; //포장번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    Vendor vendor;//상점번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    Member member;

    @Column
    private String reviewContent; //리뷰내용
    private LocalDateTime reviewRegDate = LocalDateTime.now(); //리뷰작성일자
    private Integer reviewScore; //별점


    public void setMember(Member member) {
        this.member = member;

    }


        public ReviewDto EntityToDto() {
            return ReviewDto.builder()
                    .reviewNum(this.reviewNum)
                    .orderNum(this.orderNum)
                    .vendorId(this.getVendor().getId())
                    .username(this.getMember().getUsername())
                    .reviewContent(this.reviewContent)
                    .reviewRegDate(this.reviewRegDate)
                    .reviewScore(this.reviewScore)
                    .build();
        }

}
