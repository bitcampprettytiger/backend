package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r, rf FROM Review r LEFT JOIN ReviewFile rf ON r.reviewNum = rf.review.reviewNum")
    List<Object[]> findAllReviewsWithFiles();
    Optional<Review> findByReviewNum(Long reviewNum);
}
