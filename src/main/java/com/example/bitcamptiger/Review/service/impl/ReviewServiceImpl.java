package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.common.reviewFileUtils;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final VendorRepository vendorRepositor;
    private final ReviewFileRepository reviewFileRepository;
    private final reviewFileUtils reviewFileUtils;

    @Value("${file.path}")
    String attachPath;

    @Override
    public List<ReviewDto> getReviewList() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> {
                    ReviewDto reviewDto = review.EntityToDto();
                    List<ReviewFileDto> reviewFileDtos = review.getImages().stream()
                            .map(ReviewFile::EntitytoDto)
                            .collect(Collectors.toList());
                    reviewDto.setReviewFileList(reviewFileDtos);
                    return reviewDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Review getReview(Long reviewNum) {
        if (reviewRepository.findById(reviewNum).isEmpty())
            return null;

        return reviewRepository.findById(reviewNum).get();
    }

    @Override
    public void createReview(Review review, List<ReviewFile> uploadFileList) throws IOException {
//        File directory = new File(attachPath);
//
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//
//        List<ReviewFile> uploadFileList = new ArrayList<>();
//
//        Long reviewId = 1L;
//
//        try {
//            Review review = Review.builder()
//                    .reviewContent(reviewDto.getReviewContent())
//                    .reviewScore(reviewDto.getReviewScore())
//                    .reviewRegDate(LocalDateTime.now())
//                    .build();
//            System.out.println("=================" + review.getReviewRegDate());
//
//            Iterator<String> iterator = mphsRequest.getFileNames();
//
//            while (iterator.hasNext()) {
//                List<MultipartFile> fileList = mphsRequest.getFiles(iterator.next());
//
//                for (MultipartFile multipartFile : fileList) {
//                    if (!multipartFile.isEmpty()) {
//                        ReviewFile reviewFile = new ReviewFile();
//
//                        reviewFile = reviewFileUtils.parseFileInfo(multipartFile, attachPath);
//
//                        reviewFile.setReview(review);
//
//                        uploadFileList.add(reviewFile);
//
//                        reviewId = reviewId + 1L;
//                    }
//                }
//            }

            reviewRepository.save(review);
            //변경사항 커밋 후 저장
            reviewRepository.flush();

            for (ReviewFile reviewFile : uploadFileList) {
                reviewFile.setReview(review);
                System.out.println(reviewFile + "========================================");

                long reviewFileNo = reviewFileRepository.findMaxFileNo(review.getReviewNum());
                reviewFile.setReviewFileNo(reviewFileNo);

                reviewFileRepository.save(reviewFile);
            }
        }

        @Override
        public void updateReview (Review review, List < ReviewFile > ufileList){
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
                                ufileList.get(i).getReview().getReviewNum());

                        ufileList.get(i).setReviewFileNo(reviewFileNo);

                        reviewFileRepository.save(ufileList.get(i));
                    }
                }
            }
        }


            @Override
            public void deleteReview (ReviewDto reviewDto){
                // ReviewDto에서 id로 기존의 Review 엔티티를 찾음
                Review review = reviewRepository.findById(reviewDto.getReviewNum()).orElseThrow(EntityNotFoundException::new);

                //리뷰애 연결된 이미지도 함께 삭제
                for (ReviewFile reviewFile : review.getImages()) {
                    //s3에서 이미지 삭제
                    reviewFileUtils.deleteImage("springboot", reviewFile.getReviewFilePath() + reviewFile.getReviewFileName());
                    //db에서 이미지 삭제
                    reviewFileRepository.delete(reviewFile);
                }
                reviewRepository.delete(review);
            }

            @Override
            public List<ReviewFile> getReviewFileList (Long reviewNum){
                return reviewFileRepository.findByReviewReviewNum(reviewNum);
            }

        }
