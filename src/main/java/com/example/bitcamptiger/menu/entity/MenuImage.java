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
        name = "MenuImageSeqGenerator",
        sequenceName = "MENUIMAGE_SEQ", // 시퀀스 이름을 대문자로 지정
        initialValue = 1,
        allocationSize = 1
)
public class MenuImage {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "MenuImageSeqGenerator")
    @Column(name = "menu_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "menu_id"),
            @JoinColumn(name = "vendor_id")
    })
    private Menu menu;

    @Column
    private String fileName;

    @Column
    private String url;

    @Column
    private String originName;

    @Column
    private String fileCate;


}
