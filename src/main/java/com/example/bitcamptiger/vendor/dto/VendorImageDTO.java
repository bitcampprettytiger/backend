package com.example.bitcamptiger.vendor.dto;

import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorImageDTO {
    private Long id;
    private String fileName;
    private String url;
    private String originName;

    private static ModelMapper modelMapper = new ModelMapper();

    public Vendor createVendorImage(){
        return modelMapper.map(this, Vendor.class);
    }

    public static VendorImageDTO of(VendorImage vendorImage){return modelMapper.map(vendorImage, VendorImageDTO.class);}
}
