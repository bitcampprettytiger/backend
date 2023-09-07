package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.dto.VendorImageDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private Long id;
    private Orders orders;
    @JsonIgnore
    private Vendor vendor;
    private VendorDTO vendorDto;
    private Member member;
    private String reviewContent;
    private String reviewRegDate;
    private long reviewScore;
    private long likeCount;
    private long disLikeCount;
    private Boolean isLike;
    private List<ReviewFileDto> reviewFileList;
    private ReviewFileDto reviewFile;

    private int numberOfReviews;
    private List<VendorImageDTO> vendorImageDTOs; // VendorImageDto 리스트 추가


    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview() {
        return modelMapper.map(this, Review.class);
    }

    public static ReviewDto of(Review review){
        return modelMapper.map(review, ReviewDto.class);
    }


    // 포스트맨 출력시 변환된 형식으로 반환하는 메서드 추가
    public String getReviewRegDateTimeFormatted() {
        LocalDateTime dateTime = LocalDateTime.parse(reviewRegDate, DateTimeFormatter.ISO_DATE_TIME);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}


