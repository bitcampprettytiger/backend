package com.example.bitcamptiger.menu.entity;

import com.example.bitcamptiger.vendor.entity.Vendor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
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
@IdClass(MenuId.class)
public class Menu {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
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
    private String menuType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuImage> images = new ArrayList<>();

}
