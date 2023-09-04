package com.example.bitcamptiger.Review.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewWithFilesDto {
    private Long reviewNum;
    private Long orderNum;
    @JsonIgnore
    private Long vendorId;
    private Long memberId;
    private String reviewContent;
    private LocalDateTime reviewRegDate;
    private int reviewScore;
    private List<ReviewFileDto> reviewFiles;

}
