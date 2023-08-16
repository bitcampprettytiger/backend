package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ReviewFileRepository reviewFileRepository;

    @Override
    public List<Review> getReviewList() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReview(Long reviewNum) {
        if(reviewRepository.findById(reviewNum).isEmpty())
            return null;

        return reviewRepository.findById(reviewNum).get();
    }

    @Override
    @Transactional
    public void createReview(Review review, List<ReviewFile> uploadFileList) {

        reviewRepository.save(review);
        //변경사항 커밋 후 저장
        reviewRepository.flush();

        for(ReviewFile reviewFile: uploadFileList) {
            reviewFile.setReview(review);

            long reviewFileNo = reviewFileRepository.findMaxFileNo(review.getReviewNum());
            reviewFile.setReviewFileNo(reviewFileNo);

            reviewFileRepository.save(reviewFile);
        }
    }

    @Override
    @Transactional
    public void updateReview(Review review, List<ReviewFile> ufileList) {
        reviewRepository.save(review);

        if(ufileList.size() > 0) {
            for(int i = 0; i < ufileList.size(); i++) {
                if(ufileList.get(i).getReviewFileStatus().equals("U")) {
                    reviewFileRepository.save(ufileList.get(i));
                } else if(ufileList.get(i).getReviewFileStatus().equals("D")) {
                    reviewFileRepository.delete(ufileList.get(i));
                } else if(ufileList.get(i).getReviewFileStatus().equals("I")) {
                    //추가한 파일들은 reviewNum은 가지고 있지만 reviewFileNo가 없는 상태라
                    //reviewFileNo를 추가
                    Long reviewFileNo = reviewFileRepository.findMaxFileNo(
                            ufileList.get(i).getReview().getReviewNum());

                    ufileList.get(i).setReviewFileNo(reviewFileNo);

                    reviewFileRepository.save(ufileList.get(i));
                }
            }
        }
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewNum) {
       reviewRepository.deleteById(reviewNum);
    }

    @Override
    public List<ReviewFile> getReviewFileList(Long reviewNum) {
        return reviewFileRepository.findByReviewReviewNum(reviewNum);
    }

}
