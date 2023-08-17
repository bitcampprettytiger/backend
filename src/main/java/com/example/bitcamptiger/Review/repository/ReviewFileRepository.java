package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.Review.entity.ReviewFileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewFileRepository extends JpaRepository<ReviewFile, ReviewFileId> {
    @Query(value = "SELECT IFNULL(MAX(F.REVIEW_FILE_NO), 0) + 1 " +
            "           FROM REVIEW_FILE F" +
            "           WHERE F.REVIEW_NUM = :reviewNum", nativeQuery = true)
    public long findMaxFileNo(@Param("reviewNum") long reviewNum);

    List<ReviewFile> findByReviewReviewNum(long reviewNum);

}
