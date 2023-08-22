package com.example.bitcamptiger.dto;

import com.example.bitcamptiger.menu.dto.MenuDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ResponseDTO<T> {

    private Page<T> items;

    private List<T> itemlist;

    private T item;

    private String errorMessage;

    private int statusCode;

    private String message;

}
