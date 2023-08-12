package com.example.bitcamptiger.Review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ReviewFile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id; //파일번호

    @Column(nullable = false)
    private String originName;// 원본 파일명

    @Column(nullable = false)
    private String savedName; // 저장된 파일 명

    @Column(name = "FILE_FATH")
    private String filePath;// 파일 저장 경로

    @Column(name = "FILE_SIZE")
    private Long fileSize; // 파일사이즈

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_NUM")
    private Review review;


}
