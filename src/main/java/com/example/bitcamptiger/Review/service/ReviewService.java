package com.example.bitcamptiger.Review.service;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReviewService {

    Map<String, Object> processReview(ReviewDto reviewDto, MultipartHttpServletRequest mphsRequest, Member loggedInMember) throws IOException;

    Review getReview(Long id);

    ResponseDTO<Map<String, Object>> processReviewUpdates(
            ReviewDto reviewDto, MultipartFile[] uploadFiles,
            MultipartFile[] changeFileList, String originFileList
    ) throws Exception;

    void updateReview(Review review, List<ReviewFile> ufileList);

    void deleteReview(ReviewDto reviewDto, Member loggedInMember);

    List<ReviewFile> getReviewFileList(Long id);

    List<ReviewDto> getAllReviewsWithFiles(Long vendorId);

    void likeReview(Review review);

    void disLikeReview(Review review);


}
