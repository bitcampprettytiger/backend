package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.UserReviewAction;
import com.example.bitcamptiger.Review.entity.UserReviewAction;
import com.example.bitcamptiger.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReviewActionRepository extends JpaRepository<UserReviewAction, Long> {
    Optional<UserReviewAction> findByReview(Review review);

    void deleteByReview(Review review);

}
