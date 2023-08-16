package com.example.bitcamptiger.lineUp.dto;

import lombok.Data;

@Data
public class WaitingUserDTO {

    // 대기자 명단 관련 DTO

    private String userId; //// 사용자 ID
    private String restaurantId;
    private int queueNumber; // 대기자의 번호

    public WaitingUserDTO(String userId, String restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }


}
