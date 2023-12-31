package com.example.bitcamptiger.payments.dto;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.dto.OrderMenuDTO;
import com.example.bitcamptiger.order.entity.OrderMenu;
import com.example.bitcamptiger.payments.entity.Payments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    //결제 방법
    private String payMethod;

    //결제 고유 ID
    private String impUid;

    //상점 거래 ID
    private String merchantUid;

    //결제 금액
    private Long amount;

    //결제처명
    private String name;

    //카드 승인번호
//    private String applyNum;

    private LocalDateTime payDate;

    private String memberId;

    private int paymentCount; //총 결제 내역 개수

    private Long vendorId;

    private String vendorName;

    private Long orderId;

    private static ModelMapper modelMapper = new ModelMapper();

    public Payments createPayment() {
        return modelMapper.map(this, Payments.class);
    }

    public static  PaymentDTO of(Payments payment){
        return modelMapper.map(payment, PaymentDTO.class);
    }


}
