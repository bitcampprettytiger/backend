package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.vendor.dto.*;
import com.example.bitcamptiger.vendor.entity.Randmark;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.NowLocationRepository;
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
    private final MenuRepository menuRepository;
    private final MenuImageRepository menuImageRepository;
    private final NowLocationRepository nowLocationRepository;

//    public final GeoService geoService;
    @Override
    public List<LocationDto> getNowLocationList(NowLocationDto nowLocationDto) {

//        List<NowLocationDto> nowLocationDtoList = new ArrayList<>();
//        List<Randmark> Randmark = nowLocationRepository.findAll();

        JSONObject geocoding = geoService.geocoding(nowLocationDto.getAddress());
        nowLocationDto.setHardness(geocoding.get("x").toString());
        nowLocationDto.setLatitude(geocoding.get("y").toString());
        List<Randmark> Location = nowLocationRepository.findAll();
        List<LocationDto> locationDtoList = new ArrayList<>();
//        List<>
        for(Randmark randmark : Location){
            if(Double.parseDouble(nowLocationDto.getLatitude())-Double.parseDouble(randmark.getHardness())>0.122699 && Double.parseDouble(nowLocationDto.getLatitude())-Double.parseDouble(randmark.getHardness()) > 0.244849){
//                locationDtoList.add(randmark.getLocation());
                LocationDto locationDto = new LocationDto();
                locationDto.setLocation(randmark.getLocation());
                locationDtoList.add(locationDto);
            }
//            randmark.getHardness();
//            randmark.getLatitude();
        }
        return locationDtoList;
    }


    @Override
    public List<VendorDTO> getVendorList() {

        List<Vendor> vendorList = vendorRepository.findAll();

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        //vendorList의 각 요소에 대해 반복하며, 각각의 Vendor 객체를 반복 변수인 "vendor"에 할당
        for(Vendor vendor : vendorList){
            //Vendor 객체를 VendorDTO 객체로 변환
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            List<Menu> menuList = menuRepository.findByVendor(vendor);

            //vendor 조회시 등록된 menu 정보도 조회
            List<MenuDTO> menuDTOList = new ArrayList<>();
            for(Menu menu : menuList){
                MenuDTO menuDTO = MenuDTO.of(menu);

                //해당 메뉴 이미지 조회
                List<MenuImage> menuImageList = menuImageRepository.findByMenu(menu);

                List<MenuImageDTO> menuImageDTOList = new ArrayList<>();
                for (MenuImage  menuImage: menuImageList){
                    //MenuImage 객체를 MenuImageDTO 객체로 변환
                    MenuImageDTO menuImageDTO = MenuImageDTO.of(menuImage);
                    menuImageDTOList.add(menuImageDTO);
                }
                // MenuDTO 객체에 메뉴 이미지 리스트를 설정
                menuDTO.setMenuImageList(menuImageDTOList);
                menuDTOList.add(menuDTO);
            }

            vendorDTO.setMenuDTOList(menuDTOList);
            vendorDTOList.add(vendorDTO);
        }

        return vendorDTOList;
    }

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


    @Override
    public List<VendorDTO> getVendorByAddressCategory(String address){
        List<Vendor> vendorList = vendorRepository.findVendorByAddressCategory(address);

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
