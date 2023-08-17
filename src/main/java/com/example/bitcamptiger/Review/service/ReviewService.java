package com.example.bitcamptiger.Review.service;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    Review getReview(Long reviewNum);

    void createReview(Review review, List<ReviewFile> uploadFileList) throws IOException;

    void updateReview(Review review, List<ReviewFile> ufileList);

    void deleteReview(ReviewDto reviewDto);

    List<ReviewFile> getReviewFileList(Long reviewNum);

    List<ReviewDto> getReviewList();

}
