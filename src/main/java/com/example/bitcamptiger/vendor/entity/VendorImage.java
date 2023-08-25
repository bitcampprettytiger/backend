package com.example.bitcamptiger.vendor.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class VendorImage {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_img_id")
    private Long id;

    @Column
    private String fileName;

    @Column
    private String url;

    @Column
    private String originName;

    @Column
    private String fileCate;

    @Column
    private String S3url;


}
