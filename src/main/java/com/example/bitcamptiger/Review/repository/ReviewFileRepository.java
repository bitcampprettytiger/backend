package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.ReviewFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewFileRepository extends JpaRepository<ReviewFile, Long> {
}
