package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.service.ReviewFileService;
import com.example.bitcamptiger.Review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewFileServiceImpl implements ReviewFileService{

    private final ReviewFileRepository reviewFileRepository;
}
