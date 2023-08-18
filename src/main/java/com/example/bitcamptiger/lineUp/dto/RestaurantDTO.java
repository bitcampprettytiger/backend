package com.example.bitcamptiger.lineUp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantDTO {

    private String restaurantId;
    private List<WaitingUserDTO> waitingUsers; //// 대기자 목록

    public RestaurantDTO(String restaurantId) {
        this.restaurantId = restaurantId;
        this.waitingUsers = new ArrayList<>();
    }

    public void addWaitingUser(WaitingUserDTO waitingUser) {
        waitingUsers.add(waitingUser);
    }

    public List<WaitingUserDTO> getWaitingUsers() {
        return waitingUsers;
    }

    // Getter, Setter 및 기타 메서드
}
