package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFileDto {

    private Long reviewNum; // 리뷰 번호(Review 참조)
    private Long reviewFileNo;
    private String reviewFileOrigin;
    private String reviewFileName;
    private String reviewFilePath;
    private String reviewFileCate;
    private String reviewFileStatus;
    private String newFileName;

    public ReviewFile DtoToEntity() {
        Review review = Review.builder()
                .reviewNum(this.reviewNum)
                .build();

        ReviewFile reviewFile = ReviewFile.builder()
                .review(review)
                .reviewFileNo(this.reviewFileNo)
                .reviewFileOrigin(this.reviewFileOrigin)
                .reviewFileName(this.reviewFileName)
                .reviewFilePath(this.reviewFilePath)
                .reviewFileCate(this.reviewFileCate)
                .build();
        return reviewFile;
    }

}
