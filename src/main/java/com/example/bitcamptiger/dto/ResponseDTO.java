package com.example.bitcamptiger.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ResponseDTO<T> {

    private Page<T> items;

    private T item;

    private String errorMessage;

    private int statusCode;
}
