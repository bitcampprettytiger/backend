package com.example.bitcamptiger.Review.repository;

import com.example.bitcamptiger.Review.entity.Review;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r, rf FROM Review r LEFT JOIN ReviewFile rf ON r.id = rf.review.id WHERE r.vendor.id = :vendorId")
    List<Object[]> findByVendorId(@Param("vendorId") Long vendorId);

    @Query("SELECT SUM(r.likeCount) FROM Review r")
    Long getTotalLikes();

    @Query("SELECT SUM(r.disLikeCount) FROM Review r")
    Long getTotalDisLikes();

    List<Review> findByVendor(Vendor vendor);
    Optional<Review> findById(Long id);

    List<Review> findByMember(Member member);

}
