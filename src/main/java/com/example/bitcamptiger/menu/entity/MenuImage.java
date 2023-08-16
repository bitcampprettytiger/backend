package com.example.bitcamptiger.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@IdClass(MenuImageId.class)
public class MenuImage {

    //fetchlazy 게으른 로딩. 실제로 해당 객체가 필요한 시점까지 데이터베이스에서 로딩을 지연시킨다는 것을 의미.
    //cascade = CascadeType.PERSIST는 MenuImage 엔티티가 저장될 때 연관된 Menu 엔티티도 함께 저장되어야 함을 의미.
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id")
    private Menu menu;

    @Id
    @Column(name = "menu_img_id")

    private Long id;

    @Column
    private String fileName;

    @Column
    private String url;

    @Column
    private String originName;

    @Column
    private String fileCate;




}
