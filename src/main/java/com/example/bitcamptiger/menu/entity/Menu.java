package com.example.bitcamptiger.menu.entity;

import com.example.bitcamptiger.vendor.entity.Vendor;
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
            strategy = GenerationType.IDENTITY,
            generator = "MenuSeqGenerator")
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
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



}
