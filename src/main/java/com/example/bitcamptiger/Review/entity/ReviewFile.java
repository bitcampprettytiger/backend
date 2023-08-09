package com.example.bitcamptiger.Review.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ReviewFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originName;
    private String saveName;
    private Long size;
    private LocalDateTime createDate;
    @ManyToOne
    private Review review;
}
