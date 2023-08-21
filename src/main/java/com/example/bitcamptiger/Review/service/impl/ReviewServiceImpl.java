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
import com.example.bitcamptiger.menu.entity.MenuImage;
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
    private final VendorRepository vendorRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final reviewFileUtils reviewFileUtils;

    @Override
    public List<ReviewDto> getReviewList() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> {
                    ReviewDto reviewDto = ReviewDto.entityToDto(review);
                    return reviewDto;
                })
                .collect(Collectors.toList());
    }


//    @Override
//    public List<ReviewFileDto> findReviewFilesByReviewNum(Long reviewNum) {
//        List<ReviewFile> reviewFiles = reviewFileRepository.findByReviewNum(reviewNum);
//        return reviewFiles.stream().map(reviewFile -> ReviewFileDto.entityToDto(reviewFile)).collect(Collectors.toList());
//    }

    @Override
    public Review getReview(Long reviewNum) {
        if (reviewRepository.findById(reviewNum).isEmpty())
            return null;

        return reviewRepository.findById(reviewNum).get();
    }

    @Override
    public void createReview(Review review, List<ReviewFile> uploadFileList ) throws IOException {

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
            System.out.println("========================================================>" + review.getReviewNum());
            long reviewFileNo = reviewFileRepository.findMaxFileNo();
            reviewFile.setReviewFileNo(reviewFileNo);

            reviewFileRepository.save(reviewFile);
        }
    }

//        Review save = reviewRepository.save(reviewDto.createReview());
//
//        File directory = new File(attachPath);
//
//        if(!directory.exists()){
//            directory.mkdirs();
//        }
//
//        List<ReviewFile> uploadFileList = new ArrayList<>();
//
//        Long imageId = 1L;
//
//        for(MultipartFile file : uploadFiles){
//
//            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
//                ReviewFile reviewFile = reviewFileUtils.parseFileInfo(file, attachPath);
//
//                //이전에 저장된 메뉴 정보를 설정.
//                reviewFile.setReview(save);
//                //menuImage 객체에 imageId 설정.
//                reviewFile.setReviewFileNo(imageId);
//
//                uploadFileList.add(reviewFile);
//                //다음 이미지에 사용될 id값 증가.
//                imageId = imageId + 1L;
//            }
//
//        }
//
//        for (ReviewFile reviewFile : uploadFileList) {
//            reviewFileRepository.save(reviewFile);
//        }

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
                        Long reviewFileNo = reviewFileRepository.findMaxFileNo();

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


                reviewRepository.delete(review);
            }

            @Override
            public List<ReviewFile> getReviewFileList (Long reviewNum){
                return reviewFileRepository.findByReviewReviewNum(reviewNum);
            }

        }
