package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "REVIEW_FILE")
@Getter
@Setter
@IdClass(ReviewFileId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFile {
    @Id
    @Column
    private Long reviewFileNo; //파일번호

    @Column
    private String reviewFileOrigin;// 원본 파일명

    @Column
    private String reviewFileName; // 저장된 파일 명

    @Column
    private String reviewFilePath;// 파일 저장 경로

    @Column
    private String reviewFileCate; // 파일 종류

    @Transient
    private String reviewFileStatus;

    @Transient
    private String newFileName;

    @Id
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;


    public ReviewFileDto EntitytoDto() {
        ReviewFileDto reviewFileDto = ReviewFileDto.builder()
                .id(this.review.getId()) // Review 참조
                .reviewFileNo(this.reviewFileNo)
                .reviewFileName(this.reviewFileName)
                .reviewFilePath(this.reviewFilePath)
                .reviewFileOrigin(this.reviewFileOrigin)
                .reviewFileCate(this.reviewFileCate)
                .build();
        return reviewFileDto;
    }

}