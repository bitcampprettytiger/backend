package com.example.bitcamptiger.board.repository;

import com.example.bitcamptiger.board.entity.Board;
import com.example.bitcamptiger.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer> , QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {
    Optional<Board> findByMember(Member member);
}
