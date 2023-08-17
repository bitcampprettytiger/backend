package com.example.bitcamptiger.vendor.entity;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vendor {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long id;

    @Column
    private String vendorType;

    @Column
    private String vendorName;

    @Column
//    @Enumerated(EnumType.STRING)
    private String vendorOpenStatus;

    @Column
    private String address;

    //좌표(위도)
    @Column
    private String x;

    //좌표(경도)
    @Column
    private String y;

    @Column
    private String tel;

    @Column
    private String businessDay;

    @Column
    private LocalTime open;

    @Column
    private LocalTime close;

    @Column
    private String b_no;        //사업자 번호

    @Column
    private String perNo;       //도로 점유증 허가 번호

    @Column
    private String rlAppiNm;        //신청인명

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();




    //리뷰 별점 합계
    @Column
    private Double totalReviewScore;

    //리뷰 개수
    @Column
    private Long reviewCount;

    //리뷰 가중평균점수(리뷰별점 + 리뷰갯수)
    @Column
    private Double weightedAverageScore;



    //리뷰가 생성되거나 수정될 때 vendor 엔티티 업데이트
    //(총 리뷰 점수, 총 리뷰 개수, 평균 리뷰 점수)
    private void updateVendorReviewScore(Review review) {

        if(this.reviewCount == null){
            this.reviewCount = 0L;
            this.totalReviewScore = 0.0;
        }
        this.reviewCount++;
        this.totalReviewScore += review.getReviewScore();
        this.weightedAverageScore = this.totalReviewScore / this.reviewCount;


    }


}
