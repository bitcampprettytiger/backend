package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.vendor.service.RoadOccuCertiService;
import com.example.bitcamptiger.vendor.dto.BusinessResponseDto;
import com.example.bitcamptiger.vendor.service.VendorAPIService;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.BusinessDay;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorOpenStatus;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.GeoService;
import com.example.bitcamptiger.vendor.service.VendorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final GeoService geoService;
    private final VendorAPIService vendorAPIService;
    private final RoadOccuCertiService roadOccuCertiService;

    @Override
    public List<VendorDTO> getVendorList() {

        List<Vendor> vendorList = vendorRepository.findAll();

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
    public void insertVendor(VendorDTO vendorDTO) throws JsonProcessingException {

        //API를 사용해서 주소를 경도와 위도로 변환
        JSONObject point = geoService.geocoding(vendorDTO.getAddress());

        //경도와 위도 데이터를 VendorDTO에 설정
        vendorDTO.setX(point.get("x").toString());
        vendorDTO.setY(point.get("y").toString());

        //사업자 유효성 검사 인증 완료 후, 사업자등록 번호 꺼내오기
        BusinessResponseDto responseDto = vendorAPIService.checkBusiness(vendorDTO.getB_no());
        if(responseDto != null && responseDto.getData().length > 0){
            vendorDTO.setB_no(responseDto.getData()[0].getB_no()) ;

            //도로 점유증 유효성 검사 인증 완료 후, 도로 점유증 허가 번호/신청인명 꺼내오기
//            List<RoadOcuuCertiData> roadOcuuCertiDataList = roadOccuCertiService.findByPerNo(vendorDTO.getPerNo());
//            if(!roadOcuuCertiDataList.isEmpty()){
//                vendorDTO.setPerNo(roadOcuuCertiDataList.get(0).getPerNo());
//                vendorDTO.setRlAppiNm(roadOcuuCertiDataList.get(0).getRlAppiNm());

                //VendorDTO를 Vendor 엔티티로 변환하여 저장
                Vendor vendor = vendorDTO.createVendor();
                vendorRepository.save(vendor);
//            }else{
//                throw  new RuntimeException("도로 점유증 유효성 검사에 실패하였습니다.");
//            }

        }else{
            throw  new RuntimeException("사업자 유효성 검사에 실패하였습니다.");
        }


    }


    @Override
    public void updateVendor(VendorDTO vendorDTO) {

        Vendor vendor  =  vendorRepository.findById(vendorDTO.getId()).orElseThrow(EntityNotFoundException::new);
        //수정 가능한 필드만 업데이트
        vendor.setVendorOpenStatus(VendorOpenStatus.valueOf(vendorDTO.getVendorOpenStatus()));
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setTel(vendorDTO.getTel());
<<<<<<< HEAD
        vendor.setBusinessDay(vendorDTO.getBusinessDay());
<<<<<<< HEAD
=======
        vendor.setBusinessDay(BusinessDay.valueOf(vendorDTO.getBusinessDay()));
>>>>>>> 4b4a551369b574f3bf3219f602b51bb007d02c9a
        vendor.setOpen(vendorDTO.getOpen());
        vendor.setClose(vendorDTO.getClose());
=======
        vendor.setOpen(LocalTime.parse(vendorDTO.getOpen()));
        vendor.setClose(LocalTime.parse(vendorDTO.getClose()));
        vendor.setMenu(vendorDTO.getMenu());

        //주소가 변경된 경우에만 경도와 위도를 업데이트
        if(!vendor.getAddress().equals(vendorDTO.getAddress())){
            // API를 사용해서 주소를 경도와 위도로 변환
            JSONObject point = geoService.geocoding(vendorDTO.getAddress());

            //경도와 위도 데이터를 VendorDTO에 설정
            vendorDTO.setX(point.get("x").toString());
            vendorDTO.setY(point.get("y").toString());
        }
        vendorRepository.save(vendor);

>>>>>>> 8fad26b2edd55a3abc95e73c6b26f7996886e569

    }

    @Override
    public void deleteVendor(VendorDTO vendorDTO) {

        Vendor vendor  =  vendorRepository.findById(vendorDTO.getId()).orElseThrow(EntityNotFoundException::new);

        vendorRepository.delete(vendor);
    }

    @Override
    public Vendor getVendorDetail(Long id) {
        Vendor vendorDetail = vendorRepository.findById(id).orElseThrow();
        return vendorDetail;
    }


}
