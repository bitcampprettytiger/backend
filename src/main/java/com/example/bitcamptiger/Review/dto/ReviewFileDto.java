package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFileDto {

    private Long id; // 리뷰 번호(Review 참조)
    private Long reviewFileNo;
    private String reviewFileOrigin;
    private String reviewFileName;
    private String reviewFilePath;
    private String reviewFileCate;
    private String reviewFileStatus;
    private String newFileName;

    public ReviewFile DtoToEntity() {
        ReviewFile reviewFile = ReviewFile.builder()
                .reviewFileNo(this.reviewFileNo)
                .review(Review.builder().id(this.id).build())
                .reviewFileName(this.reviewFileName)
                .reviewFilePath(this.reviewFilePath)
                .reviewFileOrigin(this.reviewFileOrigin)
                .reviewFileCate(this.reviewFileCate)
                .build();
        return reviewFile;
    }
}
