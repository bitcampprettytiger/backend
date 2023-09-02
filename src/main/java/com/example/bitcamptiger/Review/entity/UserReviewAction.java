package com.example.bitcamptiger.Review.entity;

import com.example.bitcamptiger.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id") //리뷰 id 조인
    private Review review;

    private boolean liked = false;
    private boolean disliked = false;


}
