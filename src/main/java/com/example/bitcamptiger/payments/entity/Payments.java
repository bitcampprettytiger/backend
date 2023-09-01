package com.example.bitcamptiger.payments.entity;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //결제 방법
    @Column
    private String payMethod;

    //결제 고유 ID
    @Column
    private String impUid;

    //상점 거래 ID
    @Column
    private String merchantUid;

    //결제 금액
    @Column
    private Long amount;

    //결제처명
    @Column
    private String name;

    //카드 승인번호
//    @Column
//    private String applyNum;

    //결제 일시
    @Column
    private LocalDateTime payDate;

    @ManyToOne(fetch = FetchType.LAZY) // Vendor 엔티티와 다대일 관계 설정
    @JoinColumn(name = "vendor_id") // 관계를 맺을 컬럼명 지정
    private Vendor vendor; // Vendor 엔티티와의 관계
}
