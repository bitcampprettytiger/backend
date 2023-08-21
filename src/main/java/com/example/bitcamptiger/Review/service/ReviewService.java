package com.example.bitcamptiger.Review.service;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.dto.ReviewWithFilesDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    Review getReview(Long reviewNum);

    void createReview(Review review,List<ReviewFile> uploadFiles ) throws IOException;

    void updateReview(Review review, List<ReviewFile> ufileList);

    void deleteReview(ReviewDto reviewDto);

    List<ReviewFile> getReviewFileList(Long reviewNum);

    @Transactional
    List<ReviewDto> getAllReviewsWithFiles();

    void likeReview(Member member, Review review);

    void disLikeReview(Member member, Review review);



}
