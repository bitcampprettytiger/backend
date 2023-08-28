package com.example.bitcamptiger.Review.controller;

import com.example.bitcamptiger.Review.dto.ReviewWithFilesDto;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.common.reviewFileUtils;
import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final reviewFileUtils fileUtils;
    private final ReviewRepository reviewRepository;
    private final VendorRepository vendorRepository;
    private final MemberRepository memberRepository;

    @Value("${reviewFile.path}")
    String attachPath;


    //vendor의 리뷰 조회
    @GetMapping("/review-list/{vendorId}")
    public ResponseEntity<?> getReviewList(@PathVariable(name = "vendorId") Long vendorId) {
        ResponseDTO<ReviewDto> responseDTO = new ResponseDTO<>();
        System.out.println(vendorId);
        try {
            List<ReviewDto> reviewsWithFiles = reviewService.getAllReviewsWithFiles(vendorId);
            System.out.println(reviewsWithFiles);
            responseDTO.setItemlist(reviewsWithFiles);
            responseDTO.setStatusCode(HttpStatus.OK.value());


            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    //리뷰 등록
    //multipart form 데이터 형식을 받기 위해 consumes 속성 지정
    @PostMapping(value = "/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(ReviewDto reviewDto,
                                          MultipartHttpServletRequest mphsRequest) {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();


        List<ReviewFile> uploadFileList = new ArrayList<>();

        Long reviewId = 1L;

        try{
//            Review review = Review.builder()
//                    .reviewContent(reviewDto.getReviewContent())
//                    .reviewScore(reviewDto.getReviewScore())
//                    .reviewRegDate(LocalDateTime.now())
//                    .build();
            Review review = reviewDto.createReview();
            System.out.println("=================" + review.getReviewRegDate());

            // vendorId 값으로 Vendor 엔티티를 조회합니다.
            Optional<Vendor> optionalVendor = vendorRepository.findById(reviewDto.getVendor().getId());
            if (optionalVendor.isPresent()) { // Vendor 엔티티가 존재하는 경우 Review 엔티티에 참조를 설정합니다.
                Vendor vendor = optionalVendor.get();
                review.setVendor(vendor);
            } else { // Vendor 엔티티가 존재하지 않는 경우 오류 처리를 수행합니다.
                throw new NoSuchElementException("Vendor not found");
            }

            // memberId 값으로 Member 엔티티를 조회합니다.
            Optional<Member> optionalMember = memberRepository.findById(reviewDto.getMember().getId());
            if (optionalMember.isPresent()) { // Member 엔티티가 존재하는 경우 Review 엔티티에 참조를 설정합니다.
                Member member = optionalMember.get();
                review.setMember(member);
            } else { // Member 엔티티가 존재하지 않는 경우 오류 처리를 수행합니다.
                throw new NoSuchElementException("Member not found");
            }



            Iterator<String> iterator = mphsRequest.getFileNames();

            while (iterator.hasNext()) {
                List<MultipartFile> fileList = mphsRequest.getFiles(iterator.next());

                for(MultipartFile multipartFile : fileList) {
                    if(!multipartFile.isEmpty()) {
                        ReviewFile reviewFile = new ReviewFile();

                        reviewFile = fileUtils.parseFileInfo(multipartFile,attachPath);

                        reviewFile.setReview(review);

                        uploadFileList.add(reviewFile);
                        System.out.println("=================" + reviewFile.getReview().getReviewRegDate());
                        reviewId = reviewId + 1L;
                    }
                }
            }

            System.out.println("11111111111111111111111");
                reviewService.createReview(review, uploadFileList);
            System.out.println("2222222222222222222222222");

            // 좋아요 처리
            if(reviewDto.isLiked()) {
                reviewService.likeReview(review.getMember(), review);
            }
            // 싫어요 처리
            if(reviewDto.isDisliked()) {
                reviewService.disLikeReview(review.getMember(), review);
            }

            Map<String, Object> returnMap =
                    new HashMap<>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");
            returnMap.put("review", uploadFileList);
            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    //리뷰 수정
    @PutMapping("/review")
    public ResponseEntity<?> updateReview(@RequestPart(value = "reviewDto") ReviewDto reviewDto,
                                          @RequestPart(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
                                          @RequestPart(value = "changeFileList", required = false) MultipartFile[] changeFileList,
                                          @RequestPart(value = "originFileList", required = false) String originFileList)
                    throws Exception {
        System.out.println(reviewDto);
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        List<ReviewFileDto> originFiles = null;

        if (originFileList != null) {
            originFiles = new ObjectMapper().readValue(originFileList,
                    new TypeReference<List<ReviewFileDto>>() {
                    });
        }

        //DB에서 수정, 삭제, 추가 될 파일 정보를 담는 리스트
        List<ReviewFile> uFileList = new ArrayList<>();

        try {
            Review review = reviewDto.createReview();
            System.out.println(reviewDto);
            if (originFiles != null) {
                //파일처리
                for (int i = 0; i < originFiles.size(); i++) {
                    //수정되는 파일 처리
                    if (originFiles.get(i).getReviewFileStatus().equals("U")) {
                        for (int j = 0; j < changeFileList.length; j++) {
                            if (originFiles.get(i).getNewFileName().equals(
                                    changeFileList[j].getOriginalFilename())) {
                                ReviewFile reviewFile = new ReviewFile();

                                MultipartFile file = changeFileList[j];

                                reviewFile = fileUtils.parseFileInfo(file,attachPath);

                                reviewFile.setReview(review);
                                reviewFile.setReviewFileNo(originFiles.get(i).getReviewFileNo());
                                reviewFile.setReviewFileStatus("U");

                                uFileList.add(reviewFile);
                            }
                        }
                        //삭제되는 파일 처리
                    } else if (originFiles.get(i).getReviewFileStatus().equals("D")) {
                        ReviewFile reviewFile = new ReviewFile();

                        reviewFile.setReview(review);
                        reviewFile.setReviewFileNo(originFiles.get(i).getReviewFileNo());
                        reviewFile.setReviewFileStatus("D");

                        uFileList.add(reviewFile);
                    }
                }
            }
            //추가된 파일 처리
            if (uploadFiles != null && uploadFiles.length > 0) {
                for (int i = 0; i < uploadFiles.length; i++) {
                    MultipartFile file = uploadFiles[i];

                    if (file.getOriginalFilename() != null &&
                            !file.getOriginalFilename().equals("")) {
                        ReviewFile reviewFile = new ReviewFile();

                        reviewFile = fileUtils.parseFileInfo(file, attachPath);

                        reviewFile.setReview(review);
                        reviewFile.setReviewFileStatus("I");

                        uFileList.add(reviewFile);
                    }
                }
            }

            if(reviewDto.isLiked()) {
                reviewService.likeReview(review.getMember(), review);
            }
            if(reviewDto.isDisliked()) {
                reviewService.disLikeReview(review.getMember(), review);
            }

            reviewService.updateReview(review, uFileList);

            Map<String, Object> returnMap = new HashMap<>();

            Review updateReview = reviewService.getReview(review.getId());
            List<ReviewFile> updateReviewFileList =
                    reviewService.getReviewFileList(review.getId());

            ReviewDto retrunReviewDto = ReviewDto.of(updateReview);

            List<ReviewFileDto> reviewFileDtoList = new ArrayList<>();

            for (ReviewFile reviewFile : updateReviewFileList) {
                ReviewFileDto reviewFileDto = reviewFile.EntitytoDto();
                reviewFileDtoList.add(reviewFileDto);
            }

            returnMap.put("review", retrunReviewDto);
            returnMap.put("reviewFileList", reviewFileDtoList);

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    //리뷰삭제
    @DeleteMapping("/review")
    public ResponseEntity<?> deleteReview (ReviewDto reviewDto){
        ResponseDTO<ReviewDto> responseDTO = new ResponseDTO<>();

        try {
            reviewService.deleteReview(reviewDto);

            responseDTO.setStatusCode(HttpStatus.OK.value());
            responseDTO.setMessage("정상적으로 삭제되었습니다.");

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
