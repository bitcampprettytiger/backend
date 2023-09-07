package com.example.bitcamptiger.member.service.Impl;

import com.example.bitcamptiger.Review.dto.ReviewDto;
import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.repository.ReviewRepository;
import com.example.bitcamptiger.common.service.S3UploadService;
import com.example.bitcamptiger.favoritePick.DTO.FavoriteVendorDTO;
import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.favoritePick.repository.FavoriteVendorRepository;
import com.example.bitcamptiger.favoritePick.service.FavoriteService;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.member.service.MyPageService;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.dto.OrderMenuDTO;
import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.order.repository.OrderRepository;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.dto.VendorImageDTO;
import com.example.bitcamptiger.vendor.entity.VendorImage;
import com.example.bitcamptiger.vendor.repository.VendorImageRepository;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.entity.Payments;
import com.example.bitcamptiger.payments.repository.PaymentRepository;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageServiceImpl implements MyPageService {

    public final MemberRepository memberRepository;
    public final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final FavoriteService favoriteService;
    private final FavoriteVendorRepository favoriteVendorRepository;
    private final VendorRepository vendorRepository;
    private final PaymentRepository paymentRepository;
    private final S3UploadService s3UploadService;
    private final VendorImageRepository vendorImageRepository;
    private final MenuImageRepository menuImageRepository;

    //내 정보 조회
    @Override
    public Optional<Member> getMyInfo(String username) {

        Optional<Member> memberDTO = memberRepository.findByUsername(username);
        return memberDTO;
    }


    private final PasswordEncoder passwordEncoder;

    //내 정보 수정
    @Override
    public MemberDTO updateMemberInfo(Member member, String updatedNickName, String newPassword) {
        // 업데이트할 필드들 설정
        if (updatedNickName != null) {
            member.setNickname(updatedNickName);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            member.setPassword(passwordEncoder.encode(newPassword));
        }

        // 데이터베이스에 저장하여 업데이트 수행
        Member updatedMember = memberRepository.save(member);

        // 업데이트된 엔터티를 DTO로 변환하여 반환
        MemberDTO updatedDTO = updatedMember.toMemberDTO();

        return updatedDTO;
    }

    // 회원 탈퇴
    // memberRepository를 사용하여 회원을 사용자명(username)으로 찾은 다음,
    // 회원이 존재하면 해당 회원 엔터티를 삭제 -> 회원이 존재하지 않을 경우에는 예외를 발생
    @Override
    public void outOfMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            memberRepository.delete(member);
        } else {
            throw new RuntimeException("탈퇴할 회원을 찾을 수 없습니다.");
        }
    }

    //결제내역
    @Override
    public List<PaymentDTO> getMyPaymentDTOs(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            List<Payments> paymentsList = paymentRepository.findByMemberIdOrderByPayDateDesc(member.getId());

            List<PaymentDTO> paymentDTOList = new ArrayList<>();

            for (Payments payments : paymentsList) {
                PaymentDTO paymentDTO = PaymentDTO.of(payments);

                // Payments 엔티티에서 Vendor 정보 가져오기
                Vendor vendor = payments.getVendor();
                if (vendor != null) {
                    paymentDTO.setVendorName(vendor.getVendorName());
                }

                paymentDTOList.add(paymentDTO);
            }

            return paymentDTOList;
        } else {
            throw new RuntimeException("결제 내역을 조회할 회원을 찾을 수 없습니다.");
        }
    }


    // 내 주문 내역
    @Override
    public List<OrderDTO> getMyOrderDTOs(String username) {
        // 현재 로그인한 사용자의 정보를 가져옴
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            // 사용자가 존재하는 경우, 사용자 정보를 가져옴
            Member member = optionalMember.get();

            // 사용자의 주문 내역을 조회
            List<Orders> ordersList = orderRepository.findByMemberId(member.getId());

            // 주문 내역을 DTO로 변환한 결과를 담을 리스트
            List<OrderDTO> orderDTOList = new ArrayList<>();

            for (Orders orders : ordersList) {
                // 주문 내역을 OrderDTO 형태로 변환
                OrderDTO orderDTO = OrderDTO.of(orders);
                orderDTO.setOrderId(orders.getId());

                // 주문한 가게의 vendorName 가져오기
                String vendorName = orders.getVendor().getVendorName();
                orderDTO.setVendorName(vendorName); // vendorName 추가

                // VendorImage 정보를 가져와서 VendorImageDto로 변환
                List<VendorImage> vendorImages = vendorImageRepository.findByVendor(orders.getVendor());
                List<VendorImageDTO> vendorImageDtos = vendorImages.stream()
                        .map(VendorImageDTO::of)
                        .collect(Collectors.toList());

                // OrderDTO에 VendorImageDto 리스트를 설정합니다.
                orderDTO.setVendorImageDtos(vendorImageDtos);

                // MenuImage 정보를 가져와서 MenuImageDto로 변환
                List<MenuImage> menuImages = menuImageRepository.findByMenu(orders.getOrderMenuList().get(0).getMenu());
                List<MenuImageDTO> menuImageDtos = menuImages.stream()
                        .map(MenuImageDTO::of)
                        .collect(Collectors.toList());

                // OrderDTO에 MenuImageDTO 리스트를 설정합니다.
                orderDTO.setMenuImageDTOList(menuImageDtos);


                // 주문 내역에 포함된 주문한 메뉴들을 변환하여 리스트에 추가
                List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
                for (OrderMenu orderMenu : orders.getOrderMenuList()) {
//                    vendorRepository.findByMenuList(orderMenu.getMenu())
                    // 주문한 메뉴를 OrderMenuDTO 형태로 변환
                    OrderMenuDTO orderMenuDTO = OrderMenuDTO.of(orderMenu);
                    orderMenuDTOList.add(orderMenuDTO);
                }
                // 주문 내역 DTO에 주문한 메뉴 정보를 추가
                orderDTO.setOrderedMenuDTOList(orderMenuDTOList);

                // 변환한 주문 내역 DTO를 리스트에 추가
                orderDTOList.add(orderDTO);
            }

            // 총 주문 내역 개수를 마지막에 한 번만 설정
            int totalOrderCount = orderDTOList.size();
            for (OrderDTO orderDTO : orderDTOList) {
                orderDTO.setOrderCount(totalOrderCount);
            }

            return orderDTOList;
        } else {
            // 사용자가 존재하지 않는 경우, 예외 발생
            throw new RuntimeException("주문 내역을 조회할 회원을 찾을 수 없습니다.");
        }
    }




    // 내 리뷰 내역
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getMyReviewDTOsWithVendorInfo(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            List<Review> reviews = reviewRepository.findByMember(member);

            List<ReviewDto> reviewDtoList = new ArrayList<>();

            for (Review review : reviews) {
                List<ReviewFile> byReview = reviewFileRepository.findByReview(review);
                List<ReviewFileDto> reviewFileDtos = new ArrayList<>();
                ReviewDto reviewDto = convertToReviewDto(review, reviews.size());

                // 리뷰와 연관된 가게(Vendor) 엔티티를 가져옵니다.
                Vendor vendor = review.getVendor();

                // 가게(Vendor) 엔티티를 VendorDto로 매핑합니다.
                VendorDTO vendorDto = VendorDTO.of(vendor);

                // ReviewDto에 연관된 VendorDto를 설정합니다.
                reviewDto.setVendorDto(vendorDto);

                // VendorImage 정보를 가져와서 VendorImageDto로 변환합니다.
                List<VendorImage> vendorImages = vendorImageRepository.findByVendor(vendor);
                List<VendorImageDTO> vendorImageDtos = vendorImages.stream()
                        .map(VendorImageDTO::of)
                        .collect(Collectors.toList());

                reviewDto.setVendorImageDTOs(vendorImageDtos);

                for (ReviewFile reviewFile : byReview) {
                    String geturl = s3UploadService.geturl(reviewFile.getReviewFilePath() + reviewFile.getReviewFileName());
                    ReviewFileDto of = ReviewFileDto.of(reviewFile);
                    of.setFileUrl(geturl);
                    reviewFileDtos.add(of);
                }

                reviewDto.setReviewFileList(reviewFileDtos);

                reviewDtoList.add(reviewDto);
            }

            return reviewDtoList;
        } else {
            throw new RuntimeException("리뷰 내역을 조회할 회원을 찾을 수 없습니다.");
        }
    }

    private ReviewDto convertToReviewDto(Review review, int numberOfReviews) {
        ReviewDto reviewDto = ReviewDto.of(review);
        reviewDto.setNumberOfReviews(numberOfReviews);
        return reviewDto;
    }

    //내 찜 가게 내역
    @Override
    public List<FavoriteVendorDTO> getMyFavoriteVendorDTOs(String username) {
        // 현재 로그인한 사용자의 정보를 가져옴
        Optional<Member> optionalMember = memberRepository.findByUsername(username);

        if (optionalMember.isPresent()) {
            // 사용자가 존재하는 경우, 사용자 정보를 가져옴
            Member member = optionalMember.get();

            // 사용자의 찜한 가게 내역을 조회
            List<FavoriteVendor> favoriteVendors = favoriteVendorRepository.findByMember(member);

            // 로그에 찜한 가게의 개수를 출력
            System.out.println("내 찜 가게 개수: " + favoriteVendors.size());

            // 찜한 가게 내역을 DTO로 변환한 결과를 담을 리스트
            List<FavoriteVendorDTO> favoriteVendorDTOList = new ArrayList<>();
            for (FavoriteVendor favoriteVendor : favoriteVendors) {
                // 찜한 가게 내역을 FavoriteVendorDTO 형태로 변환
                FavoriteVendorDTO favoriteVendorDTO = new FavoriteVendorDTO();
                favoriteVendorDTO.setId(favoriteVendor.getId());
                favoriteVendorDTO.setMember(favoriteVendor.getMember());
                favoriteVendorDTO.setVendor(favoriteVendor.getVendor());

                // 찜한 가게 개수를 DTO에 추가
                favoriteVendorDTO.setTotalCount(favoriteVendors.size());

                favoriteVendorDTOList.add(favoriteVendorDTO);
            }

            return favoriteVendorDTOList;
        } else {
            // 사용자를 찾을 수 없는 경우에 대한 처리
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }

    // 내 리뷰 삭제
    //여러개 가능
    @Override
    public void deleteReviewsByIds(List<Long> reviewIds, String username) {
        for (Long reviewId : reviewIds) {
            // 각 리뷰 ID에 대해 삭제
            deleteReviewById(reviewId, username);
        }
    }

    // 개별 리뷰 삭제 메서드
    @Override
    public void deleteReviewById(Long reviewId, String username) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();

            // 리뷰의 작성자와 현재 로그인한 사용자를 비교하여 권한 확인
            String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!loggedInUsername.equals(review.getMember().getUsername())) {
                throw new SecurityException("리뷰를 삭제할 권한이 없습니다.");
            }

            // 리뷰와 연결된 리뷰 파일(첨부 이미지) 정보를 조회
            List<ReviewFile> reviewFiles = reviewFileRepository.findByReview(review);

            // 리뷰 파일 정보를 삭제 (파일 시스템 또는 데이터베이스에서)
            for (ReviewFile reviewFile : reviewFiles) {
                // 파일 삭제 로직을 추가 (예: 파일 시스템에서 삭제)
                // 파일을 데이터베이스에서 삭제
                reviewFileRepository.delete(reviewFile);
            }

            // 리뷰 삭제
            reviewRepository.delete(review);
        } else {
            throw new RuntimeException("삭제할 리뷰를 찾을 수 없습니다.");
        }
    }



}



