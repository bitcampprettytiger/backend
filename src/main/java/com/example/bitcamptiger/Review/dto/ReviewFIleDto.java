package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFIleDto {

    private Long id;
    private String originName;
    private String savedName;
    private String filePath;
    private Long fileSize;
    private Long reviewNum; // 리뷰 번호(Review 참조)

    public ReviewFile DtoToEntity() {
        ReviewFile reviewFile = ReviewFile.builder()
                .id(this.id)
                .review(Review.builder().reviewNum(this.reviewNum).build())
                .originName(this.originName)
                .savedName(this.savedName)
                .filePath(this.filePath)
                .fileSize(this.fileSize)
                .build();
        return reviewFile;
    }

    public void setReviewNum(Long reviewNum) {
        this.reviewNum =reviewNum;
    }
}
