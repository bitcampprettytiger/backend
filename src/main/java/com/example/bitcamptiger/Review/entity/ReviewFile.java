package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "REVIEW_FILE")
@Data
@IdClass(ReviewFileId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewFileNo; //파일번호
    private String reviewFileOrigin;// 원본 파일명
    private String reviewFileName; // 저장된 파일 명
    private String reviewFilePath;// 파일 저장 경로
    private String reviewFileCate;
    @Transient
    private String reviewFileStatus;
    @Transient
    private String newFileName;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewNum", referencedColumnName = "reviewNum")
    private Review review;

    public ReviewFileDto EntitytoDto() {
        ReviewFileDto reviewFIleDto = ReviewFileDto.builder()
                .reviewNum(this.review.getReviewNum()) // Review 참조
                .reviewFileNo(this.reviewFileNo)
                .reviewFileName(this.reviewFileName)
                .reviewFilePath(this.reviewFilePath)
                .reviewFileOrigin(this.reviewFileOrigin)
                .reviewFileCate(this.reviewFileCate)
                .build();
        return reviewFIleDto;
    }


}
