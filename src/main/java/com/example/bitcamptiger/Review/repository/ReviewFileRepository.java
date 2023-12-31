package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.entity.ReviewFileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewFileRepository extends JpaRepository<ReviewFile, ReviewFileId> {

    @Query(value="SELECT IFNULL(MAX(F.review_file_no), 0) + 1 " +
            "           FROM review_file F" +
            "           WHERE F.review_id = :id", nativeQuery = true)
    public long findMaxFileNo(@Param("id") long id);

    List<ReviewFile> findByReviewId(long id);

    List<ReviewFile> findByReview(Review review);

}
