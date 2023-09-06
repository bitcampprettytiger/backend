package com.example.bitcamptiger.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserRes {
    private Long id;
    private String jwt;

    public PostUserRes(Long id) {
        this.id = id;
    }
}
