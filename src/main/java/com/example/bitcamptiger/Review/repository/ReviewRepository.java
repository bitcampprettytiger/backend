package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReviewNum(Long reviewNum);
}
