package com.example.bitcamptiger.webSoket.controller;

import com.example.bitcamptiger.webSoket.dto.RestaurantDTO;
import com.example.bitcamptiger.webSoket.handler.LineUpHandler;
import com.example.bitcamptiger.webSoket.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class WaitingUserController {

    private final RestaurantService restaurantService;
    private final LineUpHandler lineUpHandler;



    // 대기 인원 실시간 업데이트하는 컨트롤러
    @GetMapping("/waitingCount")
    public ResponseEntity<Integer> getWaitingCount() {
        // 현재 대기 인원 수를 가져와서 반환
        int waitingCount = lineUpHandler.waitingCount;
        return ResponseEntity.ok(waitingCount);
    }

    //대기 인원에서 삭제 컨트롤러 (대기"줄서기" 취소의 경우)
    @DeleteMapping("/{restaurantId}/cancel/{userId}")
    public ResponseEntity<?> cancelWaitingUser(
            @PathVariable String restaurantId,
            @PathVariable String userId
    ) {
        try {
            // 대기자 취소 서비스 메서드 호출
            RestaurantDTO restaurantDTO = new RestaurantDTO(restaurantId);
            restaurantService.cancelWaitingUser(restaurantDTO, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}