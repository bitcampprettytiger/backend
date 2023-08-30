package com.example.bitcamptiger.Review.controller;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;

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
    public ResponseEntity<ResponseDTO<Map<String, Object>>> createReview(
            @ModelAttribute ReviewDto reviewDto,
            MultipartHttpServletRequest mphsRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            if (userDetails == null) {
                throw new RuntimeException("로그인한 사용자 정보를 찾을 수 없습니다.");
            }

            Member loggedInMember = userDetails.getUser(); // 로그인한 사용자 정보 가져오기

            Map<String, Object> result = reviewService.processReview(reviewDto, mphsRequest, loggedInMember);
            ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
            responseDTO.setItem(result);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            ResponseDTO<Map<String, Object>> errorResponseDTO = new ResponseDTO<>();
            errorResponseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            // 예외 상황에 따른 한국어 에러 메시지 설정
            if (e.getMessage().equals("로그인한 사용자 정보를 찾을 수 없습니다.")) {
                System.out.println(e.getMessage());
                errorResponseDTO.setErrorMessage("로그인한 사용자 정보를 찾을 수 없습니다.");
            } else {
                System.out.println(e.getMessage());
                errorResponseDTO.setErrorMessage("알 수 없는 오류가 발생했습니다.");
            }

            return ResponseEntity.badRequest().body(errorResponseDTO);
        }
    }



    //리뷰 수정
    @PutMapping("/review")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> updateReview(
            @RequestPart(value = "reviewDto") ReviewDto reviewDto,
            @RequestPart(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
            @RequestPart(value = "changeFileList", required = false) MultipartFile[] changeFileList,
            @RequestPart(value = "originFileList", required = false) String originFileList
    ) {
        try {
            ResponseDTO<Map<String, Object>> responseDTO = reviewService.processReviewUpdates(
                    reviewDto, uploadFiles, changeFileList, originFileList);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO<Map<String, Object>> errorResponseDTO = new ResponseDTO<>();
            errorResponseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorResponseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseDTO);
        }
    }

    //리뷰 삭제
    @DeleteMapping("/review")
    public ResponseEntity<?> deleteReview (ReviewDto reviewDto,
                                           @AuthenticationPrincipal CustomUserDetails userDetails){
        ResponseDTO<ReviewDto> responseDTO = new ResponseDTO<>();

        try {
            Optional<Member> loggedInMember = memberRepository.findByUsername(userDetails.getUsername());
            if (loggedInMember.isEmpty()) {
                throw new RuntimeException("로그인한 사용자 정보를 찾을 수 없습니다.");
            }

            reviewService.deleteReview(reviewDto, loggedInMember.get());

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
