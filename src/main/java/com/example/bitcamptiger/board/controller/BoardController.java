package com.example.bitcamptiger.board.controller;


import com.example.bitcamptiger.board.dto.BoardDto;
import com.example.bitcamptiger.board.service.BoardService;
import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
        private final BoardService boardService;



    @GetMapping("/board/search/{boardid}")
    public ResponseEntity<?> searchList(//security에 있는 authentication에 접근
                                        @PathVariable int boardid, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println(boardid);
        System.out.println(customUserDetails);

        ResponseDTO<BoardDto> response = new ResponseDTO<>();
        try {
            BoardDto searchboard = boardService.searchboard(boardid);
            response.setItem(searchboard);

            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }



    @GetMapping({"/board","/board/{page}"})
    public ResponseEntity<?> getTodoList(//security에 있는 authentication에 접근
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails,@PathVariable(value = "page", required = false) Integer page) {
        System.out.println(page);
        System.out.println(customUserDetails);

        ResponseDTO<BoardDto> response = new ResponseDTO<>();
        try {
            Pageable pageable = PageRequest.of(page!=null?page : 0,5);

            Page<BoardDto> boards = boardService.pageboardList(pageable);
//            List<BoardDto> boardDtoList = boardService.getboardlist();
            response.setItems(boards);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/board")
    public ResponseEntity<?> insertTodoList(@RequestBody BoardDto boardDto
            ,@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        System.out.println(customUserDetails);
        boardDto.setUsername(customUserDetails.getUsername());
        ResponseDTO<Integer> response = new ResponseDTO<>();
        try {
//            새로운 Todo저장
            int updateboardnum = boardService.saveboard(boardDto);
            response.setItem(updateboardnum);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/board")
    public ResponseEntity<?> updateTodoList(@RequestBody BoardDto boardDto,
                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println(customUserDetails);

        ResponseDTO<BoardDto> response = new ResponseDTO<>();
        try {

            BoardDto updateboard = boardService.updateTodo(boardDto,customUserDetails);


            response.setItem(updateboard);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/board")
    public ResponseEntity<?> deleteboard(@RequestBody BoardDto boardDto,
                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println(customUserDetails);

        ResponseDTO<BoardDto> response = new ResponseDTO<>();

        try {
            BoardDto delteboard =  boardService.deleteboard(boardDto.getId(),customUserDetails);
            response.setItem(delteboard);
            response.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }






}
