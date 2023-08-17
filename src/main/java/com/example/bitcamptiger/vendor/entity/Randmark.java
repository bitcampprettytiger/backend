package com.example.bitcamptiger.vendor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Randmark {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    @Column(name = "Randmark_id")
    private Long id;


    @Column
    private String location;

    @Column
    private String Latitude;

    @Column
    private String Hardness;

}
