package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.vendor.dto.BusinessResponseDto;
import com.example.bitcamptiger.vendor.dto.RoadOcuuCertiData;
import com.example.bitcamptiger.vendor.dto.VendorDTO;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.GeoService;
import com.example.bitcamptiger.vendor.service.RoadOccuCertiService;
import com.example.bitcamptiger.vendor.service.VendorAPIService;
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


    //모든 가게 조회
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


    //영업중인 가게만 조회
    @Override
    public List<VendorDTO> getOpenList(String vendorOpenStatus) {

        List<Vendor> openList = vendorRepository.findByVendorOpenStatus(vendorOpenStatus);

        List<VendorDTO> openDTOList = new ArrayList<>();

        for(Vendor vendor : openList){
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            openDTOList.add(vendorDTO);
        }
        return openDTOList;
    }


    // 해당 검색어를 포함한 모든 가게 조회.
    // 주소, 메뉴명, 가게명
    @Override
    public List<VendorDTO> getVendorByCategory(String address, String menuName, String vendorName){
        List<Vendor> vendorList = vendorRepository.findVendorByCategory(address, menuName, vendorName);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        // Page 인터페이스는 직접 Iterable 인터페이스를 구현하지 않음.
        // 따라서 .getContent() 메서드를 사용하여 List를 가져와야 함.
        for(Vendor vendor : vendorList){
            VendorDTO vendorDTO = VendorDTO.of(vendor);
            vendorDTOList.add(vendorDTO);
        }
        return vendorDTOList;
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
            RoadOcuuCertiData roadOcuuCertiData = roadOccuCertiService.authenticateAndReturnMessage(vendorDTO.getPerNo(), vendorDTO.getRlAppiNm());
            if (roadOcuuCertiData != null) {
                vendorDTO.setPerNo(roadOcuuCertiData.getPerNo());
                vendorDTO.setRlAppiNm(roadOcuuCertiData.getRlAppiNm());
                // VendorDTO를 Vendor 엔티티로 변환하여 저장
                Vendor vendor = vendorDTO.createVendor();
                vendorRepository.save(vendor);

            }else{
                throw  new RuntimeException("도로 점유증 유효성 검사에 실패하였습니다.");
            }

        }else{
            throw  new RuntimeException("사업자 유효성 검사에 실패하였습니다.");
        }


    }



    @Override
    public void updateVendor(VendorDTO vendorDTO) {

        Vendor vendor  =  vendorRepository.findById(vendorDTO.getId()).orElseThrow(EntityNotFoundException::new);
        //수정 가능한 필드만 업데이트
        vendor.setVendorOpenStatus(vendorDTO.getVendorOpenStatus());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setTel(vendorDTO.getTel());
        vendor.setBusinessDay(vendorDTO.getBusinessDay());
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
