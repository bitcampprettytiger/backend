package com.example.bitcamptiger.gyeongGiApi.service.userValidationService;

import com.example.bitcamptiger.gyeongGiApi.entity.GyeonggiVenders;
import com.example.bitcamptiger.gyeongGiApi.repository.GyeonggiVendersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserValiService {

    private final GyeonggiVendersRepository gyeonggiVendersRepository;

    public String signUpForGyeongGi(String prmsnNm){
        GyeonggiVenders existingEntity = gyeonggiVendersRepository.findByPrmsnNm(prmsnNm);

        if(existingEntity != null){
            return "정보가 일치합니다.";
        }else{
            return "정보가 일치하지 않습니다. 다시 확인해주세ㅐ요.";
        }
    }







}
