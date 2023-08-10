package com.example.bitcamptiger.dto;

import com.example.bitcamptiger.menu.dto.MenuDTO;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO<T> {

    private List<T> items;

    private List<MenuDTO> menuDTOList;
    private T item;

    private String errorMessage;

    private int statusCode;



}
