package com.example.bitcamptiger.seoulApi.service.userValidaionService;

import com.example.bitcamptiger.seoulApi.entity.DobongguVenders;
import com.example.bitcamptiger.seoulApi.entity.DongJakVenders;
import com.example.bitcamptiger.seoulApi.entity.DongdaemunVenders;
import com.example.bitcamptiger.seoulApi.entity.GangNamVenders;
import com.example.bitcamptiger.seoulApi.repository.DobongguVendersRepository;
import com.example.bitcamptiger.seoulApi.repository.DongJakVendersRepository;
import com.example.bitcamptiger.seoulApi.repository.DongdaemunVendersRepository;
import com.example.bitcamptiger.seoulApi.repository.GangNamVendersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserValiService {

    @Autowired
    private GangNamVendersRepository gangNamVendersRepository;
    @Autowired
    private DongJakVendersRepository dongJakVendersRepository;
    @Autowired
    private DongdaemunVendersRepository dongdaemunVendersRepository;
    @Autowired
    private DobongguVendersRepository dobongguVendersRepository;


    //강남구 검증 : 소재지도로명주소로 검색하거나 소재지번주소로 검색
    public boolean signUpForGangNam(String userAddress) {
        GangNamVenders existingEntityByRoad = gangNamVendersRepository.findBy소재지도로명주소(userAddress);
        GangNamVenders existingEntityByJibun = gangNamVendersRepository.findBy소재지지번주소(userAddress);

        if (existingEntityByRoad != null || existingEntityByJibun != null) {
            return true;
        } else {
            return false;
        }
    }

    //동작구 검증 : 거리가게명+위치
    public boolean signUpForDongJak(String storeName, String location) {
        List<DongJakVenders> existingEntity = dongJakVendersRepository.findBy거리가게명And위치(storeName, location);

        if (!existingEntity.isEmpty()) {
            // 정보가 일치하는 경우, 검증 통과
            return true;
        } else {
            // 정보가 일치하지 않는 경우
            return false;
        }
    }


    //노량진 검증 : **호점 이라고 검색하면 가능
    public boolean signUpForNoRyangJin(String 호점) {
        // "노량진"이 구분에 포함되는 엔티티 조회
        List<DongJakVenders> existingEntities = dongJakVendersRepository.findBy구분ContainingAnd위치Containing("노량진", 호점 + "호점");

        if (!existingEntities.isEmpty()) {
            // 정보가 일치하는 경우, 검증 통과
            return true;
        } else {
            // 정보가 일치하지 않는 경우
            return false;
        }
    }

    //도봉구 검증 : 도로주소 + 지정번호
    public boolean signUpForDoBongGu(String location, String storeNum) {
        DobongguVenders existingEntities = dobongguVendersRepository.findBy도로주소And지정번호(location,storeNum);
        if (existingEntities != null) {
            // 정보가 일치하는 경우, 검증 통과
            return true;
        } else {
            // 정보가 일치하지 않는 경우
            return false;
        }
    }
    //동대문 검증 : 거리가게명 + 주소
    public boolean signUpForDongDaeMun(String storeName, String location) {
        DongdaemunVenders existingEntities  = dongdaemunVendersRepository.findBy거리가게명And주소(storeName,location);
        if (existingEntities != null) {
            // 정보가 일치하는 경우, 검증 통과
            return true;
        } else {
            // 정보가 일치하지 않는 경우
            return false;
        }
    }




}
