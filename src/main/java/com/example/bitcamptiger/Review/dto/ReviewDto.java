package com.example.bitcamptiger.Review.dto;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
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
    private int likeCount;
    private int disLikeCount;
    private List<ReviewFile> reviewFileList;
    private ReviewFileDto reviewFile;


    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview() {
        return modelMapper.map(this, Review.class);
    }

    public static ReviewDto of(Review review){
        return modelMapper.map(review,ReviewDto.class);
    }


    public boolean isLiked() {
        return likeCount > 0;
    }

    public boolean isDisliked() {
        return disLikeCount > 0;
    }
}


