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
    private Long reviewFileNo; //파일번호
    private String reviewFileOrigin;// 원본 파일명
    private String reviewFileName; // 저장된 파일 명
    private String reviewFilePath;// 파일 저장 경로
    private String reviewFileCate;
    @Transient
    private String reviewFileStatus;
    @Transient
    private String newFileName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewNum")
    private Review review;


    public ReviewFileDto EntitytoDto() {
        ReviewFileDto reviewFileDto = ReviewFileDto.builder()
                .reviewNum(this.review.getReviewNum()) // Review 참조
                .reviewFileNo(this.reviewFileNo)
                .reviewFileName(this.reviewFileName)
                .reviewFilePath(this.reviewFilePath)
                .reviewFileOrigin(this.reviewFileOrigin)
                .reviewFileCate(this.reviewFileCate)
                .build();
        return reviewFileDto;
    }

}
