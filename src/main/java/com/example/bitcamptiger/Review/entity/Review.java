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
@Data
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

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewFile> reviewFiles = new ArrayList<>();


    public ReviewDto EntityToDto() {
        return ReviewDto.builder()
                .reviewNum(this.reviewNum)
                .orderNum(this.orderNum)
                .memberId(this.member.getId())
                .vendorId(this.vendor.getId())
                .reviewContent(this.reviewContent)
                .reviewRegDate(this.reviewRegDate)
                .reviewScore(this.reviewScore)
                .build();
    }
}
