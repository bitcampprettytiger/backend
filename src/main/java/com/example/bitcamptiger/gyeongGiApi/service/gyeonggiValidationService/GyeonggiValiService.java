package com.example.bitcamptiger.gyeongGiApi.service.gyeonggiValidationService;

import com.example.bitcamptiger.gyeongGiApi.entity.GyeonggiVenders;
import com.example.bitcamptiger.gyeongGiApi.repository.GyeonggiVendersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GyeonggiValiService {

    private final GyeonggiVendersRepository gyeonggiVendersRepository;

    public boolean signUpForGyeongGi(String prmsnNm, String storeNm){
        GyeonggiVenders existingEntity = gyeonggiVendersRepository.findByPrmsnNmAndStoreNm(prmsnNm, storeNm);


        if(existingEntity != null){
            //정보가 일치하는 경우, 검증 통과
            return true;
        }else{
            //정보가 일치하지 않는 경우
            return false;
        }
    }







}
