package com.example.bitcamptiger.jwt.repository;

import com.example.bitcamptiger.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByName(String name);
    boolean existsByName(String userEmail);
    void deleteByName(String userEmail);
}
