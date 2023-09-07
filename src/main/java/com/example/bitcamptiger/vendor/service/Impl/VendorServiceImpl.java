package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.common.FileUtils;
import com.example.bitcamptiger.common.service.S3UploadService;
import com.example.bitcamptiger.member.dto.MemberDTO;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.menu.dto.MenuDTO;
import com.example.bitcamptiger.menu.dto.MenuImageDTO;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.entity.MenuImage;
import com.example.bitcamptiger.menu.repository.MenuImageRepository;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import com.example.bitcamptiger.vendor.dto.*;
import com.example.bitcamptiger.vendor.entity.Randmark;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.entity.VendorImage;
import com.example.bitcamptiger.vendor.repository.NowLocationRepository;
import com.example.bitcamptiger.vendor.repository.VendorImageRepository;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import com.example.bitcamptiger.vendor.service.GeoService;
import com.example.bitcamptiger.vendor.service.RoadOccuCertiService;
import com.example.bitcamptiger.vendor.service.VendorAPIService;
import com.example.bitcamptiger.vendor.service.VendorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    private final FileUtils fileUtils;
    private final VendorImageRepository vendorImageRepository;
    private final NowLocationRepository nowLocationRepository;
    public  final S3UploadService s3UploadService;
    public  final MemberRepository memberRepository;
//    private final S3UploadService s3UploadService;
//    public final GeoService geoService;
    @Override
    public List<LocationDto> getNowLocationList(NowLocationDto nowLocationDto) {

//        List<NowLocationDto> nowLocationDtoList = new ArrayList<>();
//        List<Randmark> Randmark = nowLocationRepository.findAll();
        if(nowLocationDto.getHardness().equals(null)&&nowLocationDto.getLatitude().equals(null)){

            JSONObject geocoding = geoService.geocoding(nowLocationDto.getAddress());
            System.out.println(geocoding.toString());
            nowLocationDto.setHardness(geocoding.get("x").toString());
            nowLocationDto.setLatitude(geocoding.get("y").toString());
        }
        System.out.println(nowLocationDto);
        List<Randmark> Location = nowLocationRepository.findAll();
        List<LocationDto> locationDtoList = new ArrayList<>();
//        List<>
        for(Randmark randmark : Location){
            if(Double.parseDouble(nowLocationDto.getLatitude())-Double.parseDouble(randmark.getLatitude())<0.1 && Double.parseDouble(nowLocationDto.getHardness())-Double.parseDouble(randmark.getHardness()) < 0.1){
                System.out.println("!!!");
                System.out.println("test");
//                locationDtoList.add(randmark.getLocation());
                LocationDto locationDto = new LocationDto();
                locationDto.setLocation(randmark.getLocation());
                locationDtoList.add(locationDto);
            }
//            randmark.getHardness();
//            randmark.getLatitude();
        }
        System.out.println(nowLocationDto);
        return locationDtoList;
    }

    @Override
    public List<VendorDTO> giveLandmarkvendor(NowLocationDto nowLocationDto) {

        Randmark byLocation = nowLocationRepository.findByLocation(nowLocationDto.getName());

        if (nowLocationDto.getLatitude() == null && nowLocationDto.getHardness() == null) {
            nowLocationDto.setLatitude(byLocation.getLatitude());
            nowLocationDto.setHardness(byLocation.getHardness());
        }
        List<Vendor> byrandmart = vendorRepository.findByrandmart(nowLocationDto);
        List<VendorDTO> vendorDTOList = new ArrayList<>();
        for(Vendor vendor:byrandmart){
            VendorDTO of = VendorDTO.of(vendor);
            vendorDTOList.add(of);
        }

        return vendorDTOList;
    }

    @Override
    public NowLocationDto saverandmark(NowLocationDto nowLocationDto) {

//        List<NowLocationDto> nowLocationDtoList = new ArrayList<>();
//        List<Randmark> Randmark = nowLocationRepository.findAll();

        JSONObject geocoding = geoService.geocoding(nowLocationDto.getAddress());
        System.out.println(geocoding.toString());
        nowLocationDto.setHardness(geocoding.get("x").toString());
        nowLocationDto.setLatitude(geocoding.get("y").toString());
        Randmark createrandmark = nowLocationDto.createrandmark();
        if(!nowLocationDto.getName().equals(null)&&!nowLocationDto.getAddress().equals(null)) {
            createrandmark.setLocation(nowLocationDto.getName());
            createrandmark.setMapLocation(nowLocationDto.getAddress());
        }
        System.out.println(createrandmark);
        System.out.println(nowLocationDto);
        nowLocationRepository.save(createrandmark);
        return nowLocationDto;
    }



    //모든 가게 조회
    @Override
    public List<VendorDTO> getVendorList() {

        List<Vendor> vendorList = vendorRepository.findAll();

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        //vendorList의 각 요소에 대해 반복하며, 각각의 Vendor 객체를 반복 변수인 "vendor"에 할당
        for(Vendor vendor : vendorList){
            //Vendor 객체를 VendorDTO 객체로 변환
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            List<VendorImage> vendorImageList = vendorImageRepository.findByVendor(vendor);

            String geturl = new String();
            List<VendorImageDTO> vendorImageDTOList = new ArrayList<>();
            for(VendorImage vendorImage : vendorImageList){
                VendorImageDTO vendorImageDTO = VendorImageDTO.of(vendorImage);
                if(vendorImage.getFileCate().equals("defaultImage")) {
                    geturl = s3UploadService.geturl(vendorImage.getUrl() + vendorImage.getOriginName());
                }else {
                    geturl = s3UploadService.geturl(vendorImage.getUrl() + vendorImage.getFileName());
                }

                vendorImageDTOList.add(vendorImageDTO);

            }
             vendorDTO.setPrimaryimgurl(geturl);

            vendorDTO.setVendorImageDTOList(vendorImageDTOList);
//            vendorDTOList.add(vendorDTO);


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

    @Override
    public List<VendorDTO> getVendorByAddressCategory(String address) {
        return null;
    }



    // 해당 검색어를 포함한 모든 가게 조회.
    // 주소, 메뉴명, 가게명
    @Override
    @Transactional(readOnly = true)
    public List<VendorDTO> getVendorByCategory(String address, String menuName, String vendorName, String orderBy){
        List<Vendor> vendorList = vendorRepository.findVendorByCategory(address, menuName, vendorName, orderBy);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        //vendorList의 각 요소에 대해 반복하며, 각각의 Vendor 객체를 반복 변수인 "vendor"에 할당
        for(Vendor vendor : vendorList) {

            List<VendorImage> byVendor = vendorImageRepository.findByVendor(vendor);

            String geturl = new String();
            //Vendor 객체를 VendorDTO 객체로 변환
            VendorDTO vendorDTO = VendorDTO.of(vendor);
            if(!byVendor.isEmpty()) {
                geturl = s3UploadService.geturl(byVendor.get(0).getUrl() + byVendor.get(0).getFileName());
                vendorDTO.setPrimaryimgurl(geturl);

            }

            List<Menu> menuList = menuRepository.findByVendor(vendor);

            //vendor 조회시 등록된 menu 정보도 조회
            List<MenuDTO> menuDTOList = new ArrayList<>();
            for (Menu menu : menuList) {
                MenuDTO menuDTO = MenuDTO.of(menu);
                menuDTOList.add(menuDTO);
            }
            vendorDTO.setMenuDTOList(menuDTOList);
            vendorDTOList.add(vendorDTO);
        }
            return vendorDTOList;
    }



    //길거리 음식, 포장마차 타입 분류
    //해당 타입에 포함되는 가게 조회하기
    @Override
    public List<VendorDTO> getVendorByVendorType(String vendorType) {
        List<Vendor> vendorList = vendorRepository.findVendorByvendorType(vendorType);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        for(Vendor vendor : vendorList) {
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            List<Menu> menuList = menuRepository.findByVendor(vendor);

            //vendor 조회시 등록된 menu 정보도 조회
            List<MenuDTO> menuDTOList = new ArrayList<>();
            for(Menu menu : menuList){
                MenuDTO menuDTO = MenuDTO.of(menu);
                menuDTOList.add(menuDTO);
            }
            vendorDTO.setMenuDTOList(menuDTOList);
            vendorDTOList.add(vendorDTO);
        }
        return vendorDTOList;
    }



    //메뉴 타입별 가게 정보 조회
    @Override
    public List<VendorDTO> getVendorByMenuType(String menuType) {
        List<Vendor> vendorList = vendorRepository.findMenuByCategory(menuType);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        for(Vendor vendor : vendorList) {
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            List<Menu> menuList = menuRepository.findByVendor(vendor);

            List<MenuDTO> menuDTOList = new ArrayList<>();
            for(Menu menu : menuList) {
                MenuDTO menuDTO = MenuDTO.of(menu);
                menuDTOList.add(menuDTO);
            }
            vendorDTO.setMenuDTOList(menuDTOList);
            vendorDTOList.add(vendorDTO);
        }

        return vendorDTOList;
    }


    @Value("${vendorFile.path}")
    String attachPath;


    //리뷰 100개 이상인 vendor 중 별점 높은 순 정렬
    @Override
    public List<VendorDTO> getVendorByReview() {

        List<Vendor> vendorList = vendorRepository.findByReviewScore(10L);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        for(Vendor vendor : vendorList){
            VendorDTO vendorDTO = VendorDTO.of(vendor);

            vendorDTOList.add(vendorDTO);
        }

        return vendorDTOList;
    }



    // 가게 등록
    @Override
    public void insertVendor(Member member, VendorDTO vendorDTO, MultipartFile[] uploadFiles) throws IOException {

        //API를 사용해서 주소를 경도와 위도로 변환
        JSONObject point = geoService.geocoding(vendorDTO.getAddress());

        //경도와 위도 데이터를 VendorDTO에 설정
        vendorDTO.setX(point.get("x").toString());
        vendorDTO.setY(point.get("y").toString());

        //사업자 유효성 검사 인증 완료 후, 사업자등록 번호 꺼내오기
//        BusinessResponseDto responseDto = vendorAPIService.checkBusiness(vendorDTO.getB_no());
//        if (responseDto != null && responseDto.getData().length > 0) {
//            vendorDTO.setB_no(responseDto.getData()[0].getB_no());
//
//
//            //도로 점유증 유효성 검사 인증 완료 후, 도로 점유증 허가 번호/신청인명 꺼내오기
//            RoadOcuuCertiData roadOcuuCertiData = roadOccuCertiService.authenticateAndReturnMessage(vendorDTO.getPerNo(), vendorDTO.getRlAppiNm());
//            if (roadOcuuCertiData != null) {
//                vendorDTO.setPerNo(roadOcuuCertiData.getPerNo());
//                vendorDTO.setRlAppiNm(roadOcuuCertiData.getRlAppiNm());

//        Optional<Member> byUsername = memberRepository.findByUsername(vendorDTO.getUsername());
        Vendor vendor = vendorDTO.createVendor();
                vendor.setVendorType(vendorDTO.getVendorType());
                vendor.setVendorName(vendorDTO.getVendorName());
                vendor.setSIGMenu(vendorDTO.getSIGMenu());
                vendor.setVendorInfo(vendorDTO.getVendorInfo());
                vendor.setVendorOpenStatus(vendorDTO.getVendorOpenStatus());
                vendor.setTel(vendorDTO.getTel());
                vendor.setBusinessDay(vendorDTO.getBusinessDay());
                vendor.setOpen(vendorDTO.getOpen());
                vendor.setClose(vendorDTO.getClose());
                vendor.setHelpCheck(vendorDTO.getHelpCheck());
                vendor.setMember(member);
                Vendor savedVendor = vendorRepository.save(vendor);

                List<Randmark> randmarkBydistinct = nowLocationRepository.findRandmarkBydistinct(vendor);
                if(!randmarkBydistinct.isEmpty())
                vendor.setLocation(randmarkBydistinct.get(0).getLocation());
                else
                    vendor.setLocation("근처 역정보없음");

                System.out.println(vendor);
                vendorRepository.save(vendor);


                //vendor 이미지 저장
                File directory = new File(attachPath);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                List<VendorImage> uploadFileList = new ArrayList<>();


                if(uploadFiles != null && uploadFiles.length > 0){

                    //파일 처리
                    for (MultipartFile file : uploadFiles) {

                        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
//                            filesExist = true;
                            VendorImage vendorImage = fileUtils.vendorFileInfo(file, attachPath);

                            //이전에 저장된 가게 정보를 설정.
                            vendorImage.setVendor(savedVendor);

                            uploadFileList.add(vendorImage);

                        }
                    }

                }
                else {
                    //vendorImage가 등록되지 않았을 경우 기본이미지 설정
                    if (uploadFiles.equals(null)&&uploadFiles==null) {
                        System.out.println("defaultimage");
                        VendorImage defaultVendorImage = fileUtils.getDefaultVendorImage();
                        defaultVendorImage.setVendor(savedVendor);
                        uploadFileList.add(defaultVendorImage);
                    }
                }

                for (VendorImage vendorImage : uploadFileList) {
                    vendorImageRepository.save(vendorImage);
                }

//            } else {
//                throw new RuntimeException("도로 점유증 유효성 검사에 실패하였습니다.");
//            }
//
//        } else {
//            throw new RuntimeException("사업자 유효성 검사에 실패하였습니다.");
//        }

    }




    @Override
    public void updateVendor(Member member, VendorDTO vendorDTO, MultipartFile[] uploadFiles) throws IOException {

        Vendor vendor  =  vendorRepository.findByMember(member);

        //검증 : 로그인한 유저와 vendor의 등록자가 다르면 예외 발생
        if(!vendor.getMember().getId().equals(member.getId())){
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }


        //수정 가능한 필드만 업데이트
        vendor.setSIGMenu(vendorDTO.getSIGMenu());
        vendor.setVendorInfo(vendorDTO.getVendorInfo());
        vendor.setVendorOpenStatus(vendorDTO.getVendorOpenStatus());
        vendor.setAddress(vendorDTO.getAddress());
        vendor.setTel(vendorDTO.getTel());
        vendor.setBusinessDay(vendorDTO.getBusinessDay());
        vendor.setOpen(vendorDTO.getOpen());
        vendor.setClose(vendorDTO.getClose());
        vendor.setHelpCheck(vendorDTO.getHelpCheck());

        //주소가 변경된 경우에만 경도와 위도를 업데이트
        if(!vendor.getAddress().equals(vendorDTO.getAddress())){
            // API를 사용해서 주소를 경도와 위도로 변환
            JSONObject point = geoService.geocoding(vendorDTO.getAddress());

            //경도와 위도 데이터를 VendorDTO에 설정
            vendorDTO.setX(point.get("x").toString());
            vendorDTO.setY(point.get("y").toString());
        }
        vendorRepository.save(vendor);


        //기존 이미지 삭제
        List<VendorImage> existingImages = vendorImageRepository.findByVendorId(vendor.getId());
        if(existingImages != null && !existingImages.isEmpty()){
            for(VendorImage vendorImage : existingImages){
                //s3에서 이미지 삭제
                fileUtils.deleteImage("springboot", vendorImage.getUrl() + vendorImage.getFileName());

                //db에서 이미지 삭제
                vendorImageRepository.delete(vendorImage);
            }
        }

        //새로운 이미지 리스트 업데이트
        List<VendorImage> uploadFileList = new ArrayList<>();
        for(MultipartFile file : uploadFiles){
            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()){
                VendorImage vendorImage = fileUtils.vendorFileInfo(file, attachPath);
                vendorImage.setVendor(vendor);

                uploadFileList.add(vendorImage);
            }
        }

        //vendorImage가 등록되지 않았을 경우 기본이미지 설정
        if(uploadFileList.isEmpty()){
            VendorImage defaultVendorImage = fileUtils.getDefaultVendorImage();
            defaultVendorImage.setVendor(vendor);
            uploadFileList.add(defaultVendorImage);
        }

        //새로운 이미지 객체들을 가게이미지 데이터베이스에 저장
        for(VendorImage vendorImage : uploadFileList){
            vendorImageRepository.save(vendorImage);
        }

    }

    @Override
    public void deleteVendor(Member member, VendorDTO vendorDTO) {

        Vendor vendor  =  vendorRepository.findByMember(member);

        //검증 : 로그인한 유저와 vendor의 등록자가 다르면 예외 발생
        if(!vendor.getMember().getId().equals(member.getId())){
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }


        //연결될 이미지도 함께 삭제
        for(VendorImage vendorImage : vendorImageRepository.findByVendorId(vendor.getId())){
            //s3에서 이미지 삭제
            fileUtils.deleteImage("springboot", vendorImage.getUrl() + vendorImage.getFileName());

            //db에서 이미지 삭제
            vendorImageRepository.delete(vendorImage);
        }
        vendorRepository.delete(vendor);
    }

    @Override
    @Transactional(readOnly = true)
    public VendorDTO getVendorDetail(Long id) {
        String geturl = new String();

        Vendor vendor = vendorRepository.findById(id).orElseThrow();
        VendorDTO vendorDTO = VendorDTO.of(vendor);

        List<VendorImage> vendorImageList = vendorImageRepository.findByVendor(vendor);

        List<VendorImageDTO> vendorImageDTOList = new ArrayList<>();
        for(VendorImage vendorImage : vendorImageList){
            if(vendorImage.getFileCate().equals("defaultImage")) {
                geturl = s3UploadService.geturl(vendorImage.getUrl() + vendorImage.getOriginName());
            }else {
                geturl = s3UploadService.geturl(vendorImage.getUrl() + vendorImage.getFileName());
            }

            VendorImageDTO vendorImageDTO = VendorImageDTO.of(vendorImage);
            vendorImageDTO.setUrl(geturl);
            System.out.println(geturl);
            vendorImageDTOList.add(vendorImageDTO);
        }
        vendorDTO.setVendorImageDTOList(vendorImageDTOList);

        //vendor 조회시 등록된 menu 정보도 조회
        List<Menu> menuList = menuRepository.findByVendor(vendor);

        List<MenuDTO> menuDTOList = new ArrayList<>();
        for(Menu menu : menuList) {
            MenuDTO menuDTO = MenuDTO.of(menu);

            //해당 메뉴 이미지 조회
            List<MenuImage> menuImageList = menuImageRepository.findByMenu(menu);

            List<MenuImageDTO> menuImageDTOList = new ArrayList<>();
            for (MenuImage menuImage : menuImageList) {
                MenuImageDTO menuImageDTO = MenuImageDTO.of(menuImage);

                menuImageDTOList.add(menuImageDTO);
            }
            menuDTO.setMenuImageList(menuImageDTOList);
            menuDTOList.add(menuDTO);
        }

        vendorDTO.setMenuDTOList(menuDTOList);
        vendorDTO.setPrimaryimgurl(geturl);

        return vendorDTO;
    }



}
