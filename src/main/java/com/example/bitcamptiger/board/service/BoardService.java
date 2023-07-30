package com.example.bitcamptiger.board.service;

import com.example.bitcamptiger.board.dto.BoardDto;
import com.example.bitcamptiger.board.entity.Board;
import com.example.bitcamptiger.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {


    private final BoardRepository boardRepository;

    public List<BoardDto> getboardlist(){

        List<Board> boardList = boardRepository.findAll();

        List<BoardDto> boardDtoList = new ArrayList<>();

        for( Board board: boardList){
            BoardDto boardDto = BoardDto.of(board);

            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    public int saveboard(BoardDto boardDto){

        Board board = boardDto.creatBoard();

      Board saveboBoard = boardRepository.save(board);


        return saveboBoard.getId();
    }





}
