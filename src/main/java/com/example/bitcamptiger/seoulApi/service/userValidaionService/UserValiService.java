package com.example.bitcamptiger.seoulApi.service.userValidaionService;

import com.example.bitcamptiger.seoulApi.entity.DongJakVenders;
import com.example.bitcamptiger.seoulApi.entity.GangNamVenders;
import com.example.bitcamptiger.seoulApi.repository.DongJakVendersRepository;
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

//    public String APIValidateSignUp(String userSelectedArea, String userAddress) {
//        if ("강남구".equals(userSelectedArea)) {
//            return signUpForGangNam(userAddress);
//        } else if ("다른지역".equals(userSelectedArea)) {
//            return signUpForOtherArea(userAddress);
//        }
//
//        // 기본적으로 회원 가입 처리
//        // 예: 회원 가입에 필요한 정보 저장 등
//        return "회원 가입이 완료되었습니다.";
//    }


    public boolean signUpForGangNam(String userAddress) {
        GangNamVenders existingEntityByRoad = gangNamVendersRepository.findBy소재지도로명주소(userAddress);
        GangNamVenders existingEntityByJibun = gangNamVendersRepository.findBy소재지지번주소(userAddress);

        if (existingEntityByRoad != null || existingEntityByJibun != null) {
            return true;
        } else {
            return false;
        }
    }

    //거리가게명+위치
    public String signUpForDongJak(String storeName, String location) {
        List<DongJakVenders> existingEntity = dongJakVendersRepository.findBy거리가게명And위치(storeName, location);
        existingEntity.forEach(System.out::println);
        if (existingEntity != null) {
            // 정보가 일치하는 경우, 검증 통과
            return "정보가 일치합니다.";
        } else {
            // 정보가 일치하지 않는 경우, 에러 처리
            return "정보가 일치하지 않습니다. 다시 확인해주세요.";
        }
    }

    public String signUpForNoRyangJin(String 호점) {
        // "노량진"이 구분에 포함되는 엔티티 조회
        List<DongJakVenders> existingEntities = dongJakVendersRepository.findBy구분ContainingAnd위치Containing("노량진", 호점 + "호점");

        if (!existingEntities.isEmpty()) {
            // 정보가 일치하는 경우, 검증 통과
            return "정보가 일치합니다.";
        } else {
            // 정보가 일치하지 않는 경우, 에러 처리
            return "정보가 일치하지 않습니다. 다시 확인해주세요.";
        }
    }
}
