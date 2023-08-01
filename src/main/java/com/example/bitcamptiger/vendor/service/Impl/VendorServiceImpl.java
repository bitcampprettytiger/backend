package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorOpenStatus;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    @Override
    public List<VendorDTO> getVendorList(String vendorName) {

        List<Vendor> vendorList = vendorRepository.findByVendorName(vendorName);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        //vendorList의 각 요소에 대해 반복하며, 각각의 Vendor 객체를 반복 변수인 "vendor"에 할당
        for(Vendor vendor : vendorList){
            //Vendor 객체를 VendorDTO 객체로 변환
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            vendorDTOList.add(vendorDTO);
        }

        return vendorDTOList;
    }

    @Override
    public List<VendorDTO> getOpenList(String vendorOpenStatus) {

        List<Vendor> openList = vendorRepository.findByVendorOpenStatus(VendorOpenStatus.OPEN);

        List<VendorDTO> openDTOList = new ArrayList<>();

        for(Vendor vendor : openList){
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            openDTOList.add(vendorDTO);
        }
        return openDTOList;
    }

    @Override
    public void insertVendor(VendorDTO vendorDTO) {

        Vendor vendor = vendorDTO.createVendor();
        vendorRepository.save(vendor);
    }

    @Override
    public void updateVendor(VendorDTO vendorDTO) {

        Vendor vendor = vendorDTO.createVendor();
        vendorRepository.save(vendor);
    }

    @Override
    public void deleteVendor(VendorDTO vendorDTO) {

        Vendor vendor = vendorDTO.createVendor();
        vendorRepository.delete(vendor);
    }
}
