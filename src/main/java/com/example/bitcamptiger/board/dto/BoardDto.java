package com.example.bitcamptiger.board.dto;

import com.example.bitcamptiger.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {

    private int id;

    private String username;

    private String text;

    private boolean checked;

    private static ModelMapper modelMapper = new ModelMapper();

    public Board creatBoard(){
        return modelMapper.map(this,Board.class);
    }

    public static BoardDto of(Board board){
        return modelMapper.map(board,BoardDto.class);
    }
}
