package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewId;
    private Long orderNum;

    @JsonIgnore
    private Vendor vendor;
    private Member member;
    private String reviewContent;
    private String reviewRegDate;
    private int reviewScore;
    private int likedCount;
    private int disLikedCount;
    private ReviewFileDto reviewFile;



    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview() {
        return modelMapper.map(this, Review.class);
    }

    public static ReviewDto of(Review review){
        return modelMapper.map(review, ReviewDto.class);
    }

//    public static ReviewDto of(Review review) {
//        ModelMapper modelMapper = new ModelMapper();
//        ReviewDto dto = modelMapper.map(review, ReviewDto.class);
//        dto.setReviewRegDate(review.getReviewRegDate());
//        return dto;
//    }
//
//    public Review DtoToEntity() {
//        ModelMapper modelMapper = new ModelMapper();
//        Review review = modelMapper.map(this, Review.class);
//        review.setVendor(Vendor.builder().id(this.vendorIdFromDto).build());
//        review.setMember(Member.builder().id(this.memberIdFromDto).build());
//        return review;
//    }

    public boolean isLiked() {
        return likedCount > 0;
    }

    public boolean isDisliked() {
        return disLikedCount > 0;
    }
}


