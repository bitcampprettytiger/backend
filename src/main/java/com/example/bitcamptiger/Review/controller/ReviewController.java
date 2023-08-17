package com.example.bitcamptiger.Review.controller;

import com.example.bitcamptiger.common.reviewFileUtils;
import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.dto.ResponseDTO;
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

    @Value("${file.path}")
    String attachPath;


    @GetMapping("review-list")
    public ResponseEntity<?> getReviewList() {

        List<Review> reviewList = reviewService.getReviewList();

        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for(Review r : reviewList) {
            ReviewDto returnReviewDto = ReviewDto.builder()
                    .reviewNum(r.getReviewNum())
                    .reviewContent(r.getReviewContent())
                    .reviewScore(r.getReviewScore())
                    .reviewRegDate(r.getReviewRegDate())
                    .build();

            reviewDtoList.add(returnReviewDto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(reviewDtoList);
    }

    //mutipart form 데이터 형
    @PostMapping(value = "/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(ReviewDto reviewDto,
                                          MultipartHttpServletRequest mphsRequest) {
        ResponseDTO<Map<String, String>> responseDTO =
                new ResponseDTO<>();

        File directory = new File(attachPath);

        if(!directory.exists()) {
            directory.mkdir();
        }

        List<ReviewFile> uploadFileList = new ArrayList<>();

        try{
            Review review = Review.builder()
                    .reviewContent(reviewDto.getReviewContent())
                    .reviewScore(reviewDto.getReviewScore())
                    .reviewRegDate(LocalDateTime.now())
                    .build();

            Iterator<String> iterator = mphsRequest.getFileNames();

            while (iterator.hasNext()) {
                List<MultipartFile> fileList = mphsRequest.getFiles(iterator.next());

                for(MultipartFile multipartFile : fileList) {
                    if(!multipartFile.isEmpty()) {
                        ReviewFile reviewFile = new ReviewFile();

                        reviewFile = fileUtils.parseFileInfo(multipartFile, "review/");

                        reviewFile.setReview(review);

                        uploadFileList.add(reviewFile);
                    }
                }
            }

            reviewService.createReview(review, uploadFileList);

            Map<String, String> returnMap =
                    new HashMap<>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
//        try {
//            Review createdReview = reviewService.createReview(reviewDto);
//            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

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
            Review review = reviewDto.DtoToEntity();
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

                                reviewFile = fileUtils.parseFileInfo(file, "review/");

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

                        reviewFile = fileUtils.parseFileInfo(file, "review/");

                        reviewFile.setReview(review);
                        reviewFile.setReviewFileStatus("I");

                        uFileList.add(reviewFile);
                    }
                }
            }

            reviewService.updateReview(review, uFileList);

            Map<String, Object> returnMap = new HashMap<>();

            Review updateReview = reviewService.getReview(review.getReviewNum());
            List<ReviewFile> updateReviewFileList =
                    reviewService.getReviewFileList(review.getReviewNum());

            ReviewDto retrunReviewDto = updateReview.EntityToDto();

            List<ReviewFileDto> reviewFileDtoList = new ArrayList<>();

            for (ReviewFile reviewFile : updateReviewFileList) {
                ReviewFileDto reviewFileDto = reviewFile.EntitytoDto();
                reviewFileDtoList.add(reviewFileDto);
            }

            returnMap.put("review", retrunReviewDto);
            returnMap.put("reviewFileList", reviewFileDtoList);

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);


//            Review review = Review.builder()
//                    .reviewNum(reviewDto.getReviewNum())
//                    .reviewContent(reviewDto.getReviewContent())
//                    .reviewScore(reviewDto.getReviewScore())
//                    .reviewRegDate(reviewDto.getReviewRegDate())
//                    .build();
//        }
//        try {
//            Review updatedReview = reviewService.updateReview(reviewNum, reviewDto);
//            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

        @DeleteMapping("/review/{reviewNum}")
        public ResponseEntity<?> deleteReview (@PathVariable Long reviewNum){
            ResponseDTO<Map<String, String>> responseDTO =
                    new ResponseDTO<>();

            try {
                reviewService.deleteReview(reviewNum);

                Map<String, String> returnMap = new HashMap<>();

                returnMap.put("msg", "정상적으로 삭제되었습니다");

                responseDTO.setItem(returnMap);

                return ResponseEntity.ok().body(responseDTO);
            } catch (Exception e) {
                responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
                responseDTO.setErrorMessage(e.getMessage());
                return ResponseEntity.badRequest().body(responseDTO);
            }
        }
    }
