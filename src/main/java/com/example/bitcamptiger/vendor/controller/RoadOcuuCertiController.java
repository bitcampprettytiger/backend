package com.example.bitcamptiger.vendor.controller;

import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.vendor.dto.RoadOcuuCertiData;
import com.example.bitcamptiger.vendor.service.RoadOccuCertiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data") // 모든 엔드포인트에 공통 경로 "/api/data"를 설정
public class RoadOcuuCertiController {
    @Autowired
    private RoadOccuCertiService roadOccuCertiService;

    @GetMapping("/test")
    public ResponseEntity<List<RoadOcuuCertiData>> fetchDataFromExternalApi(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        // 외부 API에서 데이터 가져오기
        List<RoadOcuuCertiData> dataList = roadOccuCertiService.fetchDataFromExternalApi();

        if (dataList != null && !dataList.isEmpty()) {
            // 데이터가 존재하면 200 OK 상태코드와 함께 데이터 반환
            return ResponseEntity.ok().body(dataList);
        } else {
            // 데이터가 없으면 404 Not Found 상태코드 반환
            return ResponseEntity.badRequest().body(dataList);
        }
    }

    @GetMapping("/{perNo}/{rlAppiNm}")
    public ResponseEntity<?> authenticateAndReturnMessage(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable String perNo,
            @PathVariable String rlAppiNm
    )
    {
        // 인증 결과 메시지 반환
        RoadOcuuCertiData message = roadOccuCertiService.authenticateAndReturnMessage(perNo, rlAppiNm);

        if (message != null) {
            return ResponseEntity.ok().body(message);
        } else {
            return ResponseEntity.badRequest().body("인증실패했습니다.");
        }
    }





}
