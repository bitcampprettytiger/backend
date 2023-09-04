package com.example.bitcamptiger.webSoket.controller;

import com.example.bitcamptiger.webSoket.dto.RestaurantDTO;
import com.example.bitcamptiger.webSoket.dto.WaitingUserDTO;
import com.example.bitcamptiger.webSoket.handler.LineUpHandler;
import com.example.bitcamptiger.webSoket.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final LineUpHandler lineUpHandler;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<WaitingUserDTO>> getWaitingListByRestaurant(
            @PathVariable String restaurantId) {
        try {
            // 특정 음식점의 대기자 명단 조회 서비스 메서드 호출
            RestaurantDTO restaurantDTO = new RestaurantDTO(restaurantId);
            List<WaitingUserDTO> waitingList = restaurantService.getWaitingListByRestaurant(restaurantDTO);
            return ResponseEntity.ok(waitingList);
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{restaurantId}/add")
    public ResponseEntity<?> addWaitingUser(
            @PathVariable String restaurantId,
            @RequestBody WaitingUserDTO waitingUserDTO
    ) {
        System.out.println(restaurantId+"여거"+waitingUserDTO);
        try {
            // 대기자를 특정 음식점의 대기자 명단에 추가하는 서비스 메서드 호출
            RestaurantDTO restaurantDTO = new RestaurantDTO(restaurantId);
            restaurantService.addWaitingUser(restaurantDTO, waitingUserDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{restaurantId}/cancel")
    public ResponseEntity<?> cancelWaitingUser(
            @PathVariable String restaurantId,
            @RequestBody WaitingUserDTO waitingUserDTO
    ) {
        try {
            // 대기자를 특정 음식점의 대기자 명단에 추가하는 서비스 메서드 호출
            RestaurantDTO restaurantDTO = new RestaurantDTO(restaurantId);
            restaurantService.cancelWaitingUser(restaurantDTO, waitingUserDTO.getUserId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 입장 완료 처리하는 컨트롤러
    @PostMapping("/{restaurantId}/completeEntry")
    public ResponseEntity<?> completeEntry(
            @PathVariable String restaurantId
    ) {
        try {
            RestaurantDTO restaurantDTO = new RestaurantDTO(restaurantId);
            restaurantService.completeEntry(restaurantDTO);

            // WebSocket 핸들러의 업데이트 메서드 호출
            lineUpHandler.updateWaitingInfoAfterEntryComplete(restaurantDTO);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}