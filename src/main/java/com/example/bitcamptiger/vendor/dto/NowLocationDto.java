package com.example.bitcamptiger.vendor.dto;

import com.example.bitcamptiger.vendor.entity.Randmark;
import lombok.*;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NowLocationDto {

    private String address;

    private String Latitude;

    private String Hardness;

    private String name;


    private static ModelMapper modelMapper = new ModelMapper();

    public static NowLocationDto createNowLocation(Randmark randmark){

     return  modelMapper.map(randmark,NowLocationDto.class);

    }

    public Randmark createrandmark(){
        return modelMapper.map(this,Randmark.class);
    }
}
