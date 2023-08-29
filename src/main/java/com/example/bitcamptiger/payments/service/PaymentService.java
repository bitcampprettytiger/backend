package com.example.bitcamptiger.payments.service;

import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.entity.Payments;

import java.util.List;

public interface PaymentService {

    Payments addPayment(PaymentDTO paymentDTO, Member member);

    List<PaymentDTO> getPaymentList(String token);
}
