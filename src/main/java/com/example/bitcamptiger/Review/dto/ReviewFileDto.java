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


    public static ModelMapper modelMapper = new ModelMapper();

    public static ReviewFileDto of(ReviewFile reviewFile) { return modelMapper.map(reviewFile, ReviewFileDto.class);}

}
