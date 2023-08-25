package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.dto.ReviewWithFilesDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.entity.ReviewFileId;
import com.example.bitcamptiger.Review.entity.UserReviewAction;
import com.example.bitcamptiger.Review.entity.UserReviewAction;
import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.Review.repository.UserReviewActionRepository;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.common.reviewFileUtils;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final VendorRepository vendorRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final reviewFileUtils reviewFileUtils;
    private final UserReviewActionRepository userReviewActionRepository;


    @Override
    public Review getReview(Long id) {
        if (reviewRepository.findById(id).isEmpty())
            return null;

        return reviewRepository.findById(id).get();
    }

    //리뷰 등록하기
    @Override
    public void createReview(Review review, List<ReviewFile> uploadFileList) throws IOException {

        Vendor vendor = vendorRepository.findById(review.getVendor().getId()).orElseThrow();

        //리뷰 개수와 총 리뷰 점수 업데이트
        review.setVendor(vendor);
        // Review 엔티티 저장
        reviewRepository.save(review);
        //변경사항 커밋 후 저장
        reviewRepository.flush();

        for (ReviewFile reviewFile : uploadFileList) {
            reviewFile.setReview(review);
            System.out.println(reviewFile + "========================================");
            System.out.println("========================================================>" + review.getId());
            long reviewFileNo = reviewFileRepository.findMaxFileNo(review.getId());
            reviewFile.setReviewFileNo(reviewFileNo);

            reviewFileRepository.save(reviewFile);
        }
    }

    //리뷰 수정
    @Override
    public void updateReview(Review review, List<ReviewFile> ufileList) {
        reviewRepository.save(review);

        if (ufileList.size() > 0) {
            for (int i = 0; i < ufileList.size(); i++) {
                if (ufileList.get(i).getReviewFileStatus().equals("U")) {
                    reviewFileRepository.save(ufileList.get(i));
                } else if (ufileList.get(i).getReviewFileStatus().equals("D")) {
                    reviewFileRepository.delete(ufileList.get(i));
                } else if (ufileList.get(i).getReviewFileStatus().equals("I")) {
                    //추가한 파일들은 reviewNum은 가지고 있지만 reviewFileNo가 없는 상태라
                    //reviewFileNo를 추가
                    Long reviewFileNo = reviewFileRepository.findMaxFileNo(
                            ufileList.get(i).getReview().getId());

                    ufileList.get(i).setReviewFileNo(reviewFileNo);

                    reviewFileRepository.save(ufileList.get(i));
                }
            }
        }
    }

    //리뷰 삭제
    @Override
    public void deleteReview(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));

        String bucketName = "springboot";

        reviewFileRepository.findByReview(review).forEach(reviewFile -> {
            String key = reviewFile.getReviewFilePath() + reviewFile.getReviewFileName();
            reviewFileUtils.deleteImage(bucketName, key);

            // 파일 레코드 삭제
            reviewFileRepository.delete(reviewFile);
        });

        // 리뷰 레코드 삭제
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewFile> getReviewFileList(Long id) {
        return reviewFileRepository.findByReviewId(id);
    }


    //해당 vendor 리뷰 조회하기
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviewsWithFiles(Long vendorId) {
//        List<ReviewDto> reviewDtos = new ArrayList<>();
//
//        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//        System.out.println(vendor.get());
//        List<Review> reviewList = reviewRepository.findByVendor(vendor.orElseThrow(EntityNotFoundException::new));;
//
//        System.out.println(reviewList.size());
//        for (Review review : reviewList) {
//            ReviewDto reviewDto = ReviewDto.of(review);
//            System.out.println(reviewDto);
//
//            // 리뷰 파일 관련 정보 추가
////            ReviewFileDto reviewFileDto = new ReviewFileDto();
////            if (result[1] != null) {
////                ReviewFile reviewFile = (ReviewFile) result[1];
////                reviewFileDto = reviewFile.EntitytoDto();
////            }
////            reviewDto.setReviewFile(reviewFileDto);
//
//            List<ReviewFile> reviewFileList = reviewFileRepository.findByReview(review);
//            for(ReviewFile reviewFile: reviewFileList)
//            {
//
//            }
//            System.out.println(reviewFileList.size());
//            reviewDto.setReviewFileList(reviewFileList);
//            reviewDtos.add(reviewDto);
//        }

        List<ReviewDto> reviewDtos = new ArrayList<>();

        List<Object[]> results = reviewRepository.findByVendorId(vendorId);
        for (Object[] result : results) {
            Review review = (Review) result[0];
            ReviewDto reviewDto = ReviewDto.of(review);

            // 리뷰 파일 관련 정보 추가
            ReviewFileDto reviewFileDto = null;
            if (result[1] != null) {
                ReviewFile reviewFile = (ReviewFile) result[1];
                reviewFileDto = reviewFile.EntitytoDto();
            }
            reviewDto.setReviewFile(reviewFileDto);

            reviewDtos.add(reviewDto);
        }

        return reviewDtos;
    }


    //좋아요
    @Override
    public void likeReview(Member member, Review review) {
        Optional<UserReviewAction> userReivewAction = userReviewActionRepository.findByMemberAndReview(member, review);

        if(userReivewAction.isPresent()) {
            UserReviewAction action = userReivewAction.get();
            if(!action.isLiked()) {
                action.setLiked(true);
                action.setDisliked(false);
                review.setLikeCount(review.getLikeCount() + 1);
                userReviewActionRepository.save(action);
                reviewRepository.save(review);
            }
        } else  {
            UserReviewAction newAction = new UserReviewAction();
            newAction.setMember(member);
            newAction.setReview(review);
            newAction.setLiked(true);
            newAction.setDisliked(false);
            userReviewActionRepository.save(newAction);

            review.setLikeCount(review.getLikeCount() + 1);
            reviewRepository.save(review);
        }
    }


    //싫어요
    public void disLikeReview(Member member, Review review) {
        Optional<UserReviewAction> userReivewAction = userReviewActionRepository.findByMemberAndReview(member, review);

        if(userReivewAction.isPresent()) {
            UserReviewAction action = userReivewAction.get();
            if(!action.isDisliked()) {
                action.setDisliked(true);
                action.setLiked(false);
                review.setDisLikeCount(review.getDisLikeCount() + 1);
                userReviewActionRepository.save(action);
                reviewRepository.save(review);
            }
        } else {
            UserReviewAction newAction = new UserReviewAction();
            newAction.setMember(member);
            newAction.setReview(review);
            newAction.setDisliked(true);
            newAction.setLiked(false);
            userReviewActionRepository.save(newAction);

            review.setDisLikeCount(review.getDisLikeCount() + 1);
            reviewRepository.save(review);
        }
    }
}

