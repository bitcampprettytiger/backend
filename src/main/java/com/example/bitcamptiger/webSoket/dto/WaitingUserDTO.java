package com.example.bitcamptiger.lineUp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaitingUserDTO {

    // 대기자 명단 관련 DTO
    @JsonProperty("userId")
    private String userId; //// 사용자 ID
    @JsonProperty("restaurantId")
    private String restaurantId;
    @JsonProperty("queueNumber")
    private int queueNumber; // 대기자의 번호

    public WaitingUserDTO(String userId, String restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public WaitingUserDTO(Long userId) {
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }


}
