package com.example.bitcamptiger.menu.entity;

import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Menu {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    @JsonBackReference  //순환참조 문제를 해결하기 위해 참조속성 명시
    private Vendor vendor;

    @Column
    private String menuName;

    @Column
    private int price;

    @Column
    private String menuContent;

    @Column
    @Enumerated(EnumType.STRING)
    private MenuSellStatus menuSellStatus;

    @Column
    private String menuType;

    //메뉴 조회수
    @Column(nullable = false)
    private int views = 0; // 기본값 0으로 설정

    // 메뉴와 메뉴 이미지 간의 일대다 관계
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MenuImage> images = new ArrayList<>();



}
