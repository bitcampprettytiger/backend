package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id; //리뷰번호

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orders; //포장번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    @JsonBackReference  //순환참조 문제를 해결하기 위해 참조속성 명시
    private Vendor vendor;//상점번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Member member; //멤버 닉네임

    @Column
    private String reviewContent; //리뷰내용
    @Column
    private LocalDateTime reviewRegDate = LocalDateTime.now(); //리뷰작성일자
    @Column
    private Long reviewScore; //별점
    @Column
    private Integer likeCount = 0; //좋아요 수
    @Column
    private Integer disLikeCount = 0; // 싫어요 수

}
