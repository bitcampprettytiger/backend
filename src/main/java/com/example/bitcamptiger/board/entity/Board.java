package com.example.bitcamptiger.board.entity;


import com.example.bitcamptiger.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String text;

    private boolean checked;


}
