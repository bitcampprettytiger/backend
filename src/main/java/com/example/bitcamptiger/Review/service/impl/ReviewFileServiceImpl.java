package com.example.bitcamptiger.Review.service.impl;

import com.example.bitcamptiger.Review.dto.ReviewFileDto;
import com.example.bitcamptiger.Review.repository.ReviewFileRepository;
import com.example.bitcamptiger.Review.service.ReviewFileService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewFileServiceImpl implements ReviewFileService{

    private final ReviewFileRepository reviewFileRepository;

   //@Value("${file.path}")
    private String uploadFolder;

    @Override
    public void upload(ReviewFileDto reviewFileDto) {
        UUID uuid = UUID.randomUUID();
        //String imageFileName = uuid + "_" + reviewFileDto.getfile().
    }
}
