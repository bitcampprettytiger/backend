package com.example.bitcamptiger.vendor.entity;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
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
    private String vendorInfo;  //가게 설명

    @Column
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
    private String open;

    @Column
    private String close;

    @Column
    private String b_no;        //사업자 번호

    @Column
    private String perNo;       //도로 점유증 허가 번호

    @Column
    private String rlAppiNm;        //신청인명

    @Column
    private String location;

    @Column
    private String bank;    //은행명

    @Column
    private String accountOwner;    //계좌주명

    @Column
    private String accountNumber;   //사장님 계좌번호

    @Column
    private String toiletInfo;  //화장실 정보
    
    @Column
    private String toiletDistance;  //화장실까지 거리

    @Column
    private String coolerInfo;  //냉방기기 정보

    @OneToMany(mappedBy = "vendor")
    @JsonManagedReference
    private List<FavoriteVendor> favoriteVendors = new ArrayList<>();


    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference   //순환참조 문제를 해결하기 위해 주관리자 명시
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference   //순환참조 문제를 해결하기 위해 주관리자 명시
    private List<Review> reviewList = new ArrayList<>();

    //리뷰 별점 합계
    @Column
    private Double totalReviewScore = 0.0;

    //리뷰 개수
    @Column
    private Long reviewCount = 0L;

    //리뷰 평균점수
    @Column
    private Double averageReviewScore = 0.0;



    //리뷰가 생성되거나 수정될 때 vendor 엔티티 업데이트
    public void updateVendorReviewScore(Review review) {


        this.reviewCount++;
        this.totalReviewScore += review.getReviewScore();
        this.averageReviewScore = (this.totalReviewScore) / (this.reviewCount);


    }


}
