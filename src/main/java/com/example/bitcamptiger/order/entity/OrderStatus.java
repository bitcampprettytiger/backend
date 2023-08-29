package com.example.bitcamptiger.order.entity;

public enum OrderStatus {

    //예약 상태(결제 진행이 잘 안되었을 경우)
    RESERVATION,
    //결제 완료 상태
    CONFIRMED,
    //결제 취소 상태
    CANCELED
}
