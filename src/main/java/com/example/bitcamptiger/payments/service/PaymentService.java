package com.example.bitcamptiger.payments.service;

import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.payments.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {

    void addPayment(PaymentDTO paymentDTO, String token);

    List<PaymentDTO> getPaymentList(String token);
}
