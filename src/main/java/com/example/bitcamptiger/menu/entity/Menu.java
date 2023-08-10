package com.example.bitcamptiger.menu.entity;

import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SequenceGenerator(
        name = "MenuSeqGenerator",
        sequenceName = "MENU_SEQ", // 시퀀스 이름을 대문자로 지정
        initialValue = 1,
        allocationSize = 1
)
public class Menu {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "MenuSeqGenerator")
    @Column(name = "menu_id")
    private Long id;

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
    private String MenuType;

    @Column
    private int menuViews = 0; //메뉴 조회수, 초깃값 0으로 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

}
