package com.example.bitcamptiger.jwt.repository;

import com.example.bitcamptiger.jwt.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByName(String userEmail);
    void deleteByName(String userEmail);
}
