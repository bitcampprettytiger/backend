package com.example.bitcamptiger.board.repository;

import com.example.bitcamptiger.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> getBoardPage(Pageable pageable);
}