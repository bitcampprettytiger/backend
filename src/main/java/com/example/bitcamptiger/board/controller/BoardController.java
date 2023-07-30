package com.example.bitcamptiger.board.controller;


import com.example.bitcamptiger.board.dto.BoardDto;
import com.example.bitcamptiger.board.service.BoardService;
import com.example.bitcamptiger.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
        private final BoardService boardService;

        @GetMapping("/board")
        public ResponseEntity<?> getTodoList(@RequestBody BoardDto boardDto){

            ResponseDTO<BoardDto> response = new ResponseDTO<>();
            try {


                List<BoardDto> boardDtoList = boardService.getboardlist();
                response.setItems(boardDtoList);

                response.setStatusCode(HttpStatus.OK.value());
                return ResponseEntity.ok().body(response);
            }
            catch (Exception e){
                response.setErrorMessage(e.getMessage());
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.badRequest().body(response);
            }
        }



        @PostMapping("/board")
        public ResponseEntity<?> insertTodoList(@RequestBody BoardDto boardDto){

            ResponseDTO<Integer> response = new ResponseDTO<>();


            try {
//            새로운 Todo저장
              int updateboardnum =  boardService.saveboard(boardDto);

                response.setItem(updateboardnum);
                return ResponseEntity.ok().body(response);

            }catch (Exception e){
                response.setErrorMessage(e.getMessage());
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.badRequest().body(response);
            }
        }
//
//        @PutMapping("/todo")
//        public ResponseEntity<?> updateTodoList(@RequestBody Todo todo){
//
//            ResponseDTO<TodoDTO> response = new ResponseDTO<>();
//            try {
//
//                todoService.updateTodo(todo);
//
//
//                List<Todo> todoList = todoService.getTodoList(todo.getUsername());
//                List<TodoDTO> todoDTOList   = new ArrayList<>();
//                for(Todo t : todoList){
//                    todoDTOList.add(t.toTodoDTO());
//                }
//                response.setStatusCode(HttpStatus.OK.value());
//                return ResponseEntity.ok().body(response);
//            }catch (Exception e){
//                response.setErrorMessage(e.getMessage());
//                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
//                return ResponseEntity.badRequest().body(response);
//            }
//
//        }
//
//        @DeleteMapping("/todo")
//        public ResponseEntity<?> deleteTodoList(){
//
//
//
//            return null;
//        }






}
