package com.example.bitcamptiger.favoritePick.DTO;

import com.example.bitcamptiger.favoritePick.entity.FavoriteVendor;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteVendorDTO {

    private Long id;
    private Member member;
    private Vendor vendor;

    private static ModelMapper modelMapper = new ModelMapper();

    public FavoriteVendor createFavoriteVendor(){
        return modelMapper.map(this, FavoriteVendor.class);
    }

    public static FavoriteVendorDTO of(FavoriteVendor favoriteVendor){
        return modelMapper.map(favoriteVendor, FavoriteVendorDTO.class);
    }
}
