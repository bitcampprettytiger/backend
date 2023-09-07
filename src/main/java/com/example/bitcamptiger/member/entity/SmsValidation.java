package com.example.bitcamptiger.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsValidation {

    @Id
    @Column
    private String tel;

    @Column
    private int code;


}
