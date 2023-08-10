package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.dto.RoadOcuuCertiData;
import java.util.List;

public interface RoadOccuCertiService {

    // 외부 API에서 데이터를 가져오기
    List<RoadOcuuCertiData> fetchDataFromExternalApi();

    // 허가번호와 실제신청자명으로 데이터를 조회하는 기능을 정의
    List<RoadOcuuCertiData> findByPerNoAndRlAppiNm(List<RoadOcuuCertiData> dataList, String perNo, String rlAppiNm);

    // 사용자 인증 및 결과 반환 메서드
    RoadOcuuCertiData authenticateAndReturnMessage(String perNo, String rlAppiNm);
}
