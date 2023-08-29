package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.entity.UserReviewAction;
import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.Review.repository.UserReviewActionRepository;
import com.example.bitcamptiger.Review.service.ReviewService;
import com.example.bitcamptiger.common.reviewFileUtils;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.order.repository.OrderRepository;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final VendorRepository vendorRepository;
    private final MemberRepository memberRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final reviewFileUtils reviewFileUtils;
    private final UserReviewActionRepository userReviewActionRepository;
    private final OrderRepository orderRepository;

    @Value("${reviewFile.path}")
    String attachPath;


    @Override
    public Review getReview(Long id) {
        if (reviewRepository.findById(id).isEmpty())
            return null;

        return reviewRepository.findById(id).get();
    }

    //리뷰작성
    @Override
    public Map<String, Object> processReview(ReviewDto reviewDto, MultipartHttpServletRequest mphsRequest) throws IOException {
        Review review = createReviewEntity(reviewDto);

        Vendor vendor = getVendorFromRepository(reviewDto.getVendor().getId());
        Member member = getMemberFromRepository(reviewDto.getMember().getId());
        Orders completedOrders = getCompletedOrders(member.getId(), review.getOrders().getId());

        updateVendorReviewInfo(vendor, review);

        List<ReviewFile> uploadFileList = saveReviewFiles(review, mphsRequest);

        if (reviewDto.isLiked()) {
            likeReview(member, review);
        }
        if (reviewDto.isDisliked()) {
            disLikeReview(member, review);
        }

        saveReviewAndFiles(review, uploadFileList);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("msg", "정상적으로 저장되었습니다.");
        returnMap.put("review", uploadFileList);

        return returnMap;
    }

    private Review createReviewEntity(ReviewDto reviewDto) {
        Review review = reviewDto.createReview();
        Vendor vendor = getVendorFromRepository(reviewDto.getVendor().getId());
        Member member = getMemberFromRepository(reviewDto.getMember().getId());

        review.setVendor(vendor);
        review.setMember(member);

        return review;
    }

    private Vendor getVendorFromRepository(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NoSuchElementException("Vendor not found"));
    }

    private Member getMemberFromRepository(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found"));
    }

    private Orders getCompletedOrders(Long memberId, Long orderId) {
        Orders completedOrders = orderRepository.findByMemberIdAndId(memberId, orderId);
        if (completedOrders == null) {
            throw new RuntimeException("리뷰 작성 권한이 없습니다.");
        }
        return completedOrders;
    }

    private void updateVendorReviewInfo(Vendor vendor, Review review) {
        if (vendor.getReviewCount() == null) {
            vendor.setReviewCount(0);
        }
        if (vendor.getTotalReviewScore() == null) {
            vendor.setTotalReviewScore(0.0);
        }

        vendor.setReviewCount(vendor.getReviewCount() + 1);
        vendor.setTotalReviewScore(vendor.getTotalReviewScore() + review.getReviewScore());
        vendor.setAverageReviewScore(Math.round((vendor.getTotalReviewScore() / vendor.getReviewCount()) * 100) / 100.0);
    }

    private void saveReviewAndFiles(Review review, List<ReviewFile> uploadFileList) {
        // 리뷰 엔티티를 저장합니다.
        reviewRepository.save(review);

        // 변경사항을 커밋합니다.
        reviewRepository.flush();

        // 리뷰 파일 엔티티를 저장합니다.
        for (ReviewFile reviewFile : uploadFileList) {
            reviewFile.setReview(review);
            long reviewFileNo = reviewFileRepository.findMaxFileNo(review.getId());
            reviewFile.setReviewFileNo(reviewFileNo);

            reviewFileRepository.save(reviewFile);
        }
    }

    private List<ReviewFile> saveReviewFiles(Review review, MultipartHttpServletRequest mphsRequest) throws IOException {
        List<ReviewFile> uploadFileList = new ArrayList<>();
        Iterator<String> iterator = mphsRequest.getFileNames();

        while (iterator.hasNext()) {
            List<MultipartFile> fileList = mphsRequest.getFiles(iterator.next());

            for (MultipartFile multipartFile : fileList) {
                if (!multipartFile.isEmpty()) {
                    ReviewFile reviewFile = new ReviewFile();
                    reviewFile = reviewFileUtils.parseFileInfo(multipartFile, attachPath);
                    reviewFile.setReview(review);
                    uploadFileList.add(reviewFile);
                }
            }
        }

        return uploadFileList;
    }

    @Override
    @Transactional
    public void likeReview(Member member, Review review) {
        updateUserReviewAction(member, review, true, false);
        review.setLikeCount(review.getLikeCount() + 1);
    }

    @Override
    @Transactional
    public void disLikeReview(Member member, Review review) {
        updateUserReviewAction(member, review, false, true);
        review.setDisLikeCount(review.getDisLikeCount() + 1);
    }

    private void updateUserReviewAction(Member member, Review review, boolean liked, boolean disliked) {
        Optional<UserReviewAction> userReviewAction = userReviewActionRepository.findByMemberAndReview(member, review);

        if (userReviewAction.isPresent()) {
            UserReviewAction action = userReviewAction.get();
            action.setLiked(liked);
            action.setDisliked(disliked);
            userReviewActionRepository.save(action);
        } else {
            UserReviewAction newAction = new UserReviewAction();
            newAction.setMember(member);
            newAction.setReview(review);
            newAction.setLiked(liked);
            newAction.setDisliked(disliked);
            userReviewActionRepository.save(newAction);
        }
    }

    //리뷰 수정
    @Override
    public ResponseDTO<Map<String, Object>> processReviewUpdates(
            ReviewDto reviewDto, MultipartFile[] uploadFiles,
            MultipartFile[] changeFileList, String originFileList
    ) throws Exception {
        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();
        List<ReviewFileDto> originFiles = parseOriginFiles(originFileList);

        Review review = getReview(reviewDto.getReviewId());

        review.setReviewContent(reviewDto.getReviewContent());
        review.setReviewScore(reviewDto.getReviewScore());

        List<ReviewFile> uFileList = processReviewFiles(reviewDto, originFiles, uploadFiles, changeFileList);

        updateReviewActions(reviewDto, review);

        reviewRepository.save(review);

        updateReview(review, uFileList);

        Review updatedReview = getReview(review.getId());
        List<ReviewFile> updatedReviewFileList = getReviewFileList(review.getId());

        Map<String, Object> returnMap = createReturnMap(updatedReview, updatedReviewFileList);

        responseDTO.setItem(returnMap);
        return responseDTO;
    }

    private List<ReviewFileDto> parseOriginFiles(String originFileList) throws IOException {
        if (originFileList != null) {
            return new ObjectMapper().readValue(originFileList, new TypeReference<List<ReviewFileDto>>() {});
        }
        return Collections.emptyList();
    }

    @Override
    public void updateReview(Review review, List<ReviewFile> ufileList) {
        if (ufileList.size() > 0) {
            for (ReviewFile ufile : ufileList) {
                if ("U".equals(ufile.getReviewFileStatus())) {
                    reviewFileRepository.save(ufile);
                } else if ("D".equals(ufile.getReviewFileStatus())) {
                    reviewFileRepository.delete(ufile);
                } else if ("I".equals(ufile.getReviewFileStatus())) {
                    Long reviewFileNo = reviewFileRepository.findMaxFileNo(ufile.getReview().getId());
                    ufile.setReviewFileNo(reviewFileNo);
                    reviewFileRepository.save(ufile);
                }
            }
        }
    }

    private List<ReviewFile> processReviewFiles(
            ReviewDto reviewDto,
            List<ReviewFileDto> originFiles,
            MultipartFile[] uploadFiles,
            MultipartFile[] changeFileList
    ) throws IOException {
        //DB에서 수정, 삭제, 추가 될 파일 정보를 담는 리스트
        List<ReviewFile> uFileList = new ArrayList<>();

        Review review = reviewDto.createReview();

        if (originFiles != null) {
            //파일처리
            for (ReviewFileDto originFile : originFiles) {
                //수정되는 파일 처리
                if ("U".equals(originFile.getReviewFileStatus())) {
                    for (MultipartFile changeFile : changeFileList) {
                        if (originFile.getNewFileName().equals(changeFile.getOriginalFilename())) {
                            ReviewFile reviewFile = reviewFileUtils.parseFileInfo(changeFile, attachPath);
                            reviewFile.setReview(review);
                            reviewFile.setReviewFileNo(originFile.getReviewFileNo());
                            reviewFile.setReviewFileStatus("U");
                            uFileList.add(reviewFile);
                        }
                    }
                //삭제되는 파일처리
                } else if ("D".equals(originFile.getReviewFileStatus())) {
                    ReviewFile reviewFile = new ReviewFile();
                    reviewFile.setReview(review);
                    reviewFile.setReviewFileNo(originFile.getReviewFileNo());
                    reviewFile.setReviewFileStatus("D");
                    uFileList.add(reviewFile);
                }
            }
        }

        if (uploadFiles != null && uploadFiles.length > 0) {
            for (MultipartFile uploadFile : uploadFiles) {
                if (uploadFile.getOriginalFilename() != null && !uploadFile.getOriginalFilename().equals("")) {
                    ReviewFile reviewFile = reviewFileUtils.parseFileInfo(uploadFile, attachPath);
                    reviewFile.setReview(review);
                    reviewFile.setReviewFileStatus("I");
                    uFileList.add(reviewFile);
                }
            }
        }

        return uFileList;
    }

    private void updateReviewActions(ReviewDto reviewDto, Review review) {
        if (reviewDto.isLiked()) {
            likeReview(review.getMember(), review);
        }
        if (reviewDto.isDisliked()) {
            disLikeReview(review.getMember(), review);
        }
    }

    private Map<String, Object> createReturnMap(Review updatedReview, List<ReviewFile> updatedReviewFileList) {
        Map<String, Object> returnMap = new HashMap<>();
        ReviewDto retrunReviewDto = ReviewDto.of(updatedReview);

        List<ReviewFileDto> reviewFileDtoList = new ArrayList<>();
        for (ReviewFile reviewFile : updatedReviewFileList) {
            ReviewFileDto reviewFileDto = reviewFile.EntitytoDto();
            reviewFileDtoList.add(reviewFileDto);
        }

        returnMap.put("review", retrunReviewDto);
        returnMap.put("reviewFileList", reviewFileDtoList);

        return returnMap;
    }

    //리뷰 삭제
    @Override
    public void deleteReview(ReviewDto reviewDto, Member loggedInMember) {
        Review review = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("리뷰를 찾을 수 없습니다."));
        Member reviewAuthor = review.getMember();

        if (!loggedInMember.equals(reviewAuthor)) {
            throw new RuntimeException("리뷰를 삭제할 권한이 없습니다.");
        }

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
}

