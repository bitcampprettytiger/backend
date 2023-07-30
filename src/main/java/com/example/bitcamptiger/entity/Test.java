package com.example.bitcamptiger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Test {

    @Id
    private Long id;

}
