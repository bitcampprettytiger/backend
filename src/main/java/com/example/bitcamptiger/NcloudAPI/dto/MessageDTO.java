package com.example.bitcamptiger.NcloudAPI.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageDTO {
    String to;      //발신자
    String content;     //내용
}
