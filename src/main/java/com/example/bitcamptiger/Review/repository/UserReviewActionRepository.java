package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.UserReivewAction;
import com.example.bitcamptiger.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReviewActionRepository extends JpaRepository<UserReivewAction, Long> {
    Optional<UserReivewAction> findByMemberAndReview(Member member, Review review);
}
