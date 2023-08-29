package com.example.bitcamptiger.payments.service.Impl;

import com.example.bitcamptiger.jwt.JwtAuthenticationFilter;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.entity.Payments;
import com.example.bitcamptiger.payments.repository.PaymentRepository;
import com.example.bitcamptiger.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    //결제하기
    public Payments addPayment(PaymentDTO paymentDTO, String token){

        String newtoken = new String();
        if(StringUtils.hasText(token)&& token.startsWith("Bearer ")){
        //실제 token의 값만 리턴
            newtoken = token.substring(7);
        }

        String userId = jwtTokenProvider.validateAndGetUsername(newtoken);

        //Member 엔티티 조회
        Member member = memberRepository.findByUsername(userId).orElseThrow();

        Payments payment = new Payments();
        payment.setPayMethod(paymentDTO.getPayMethod());
        payment.setImpUid(paymentDTO.getImpUid());
        payment.setMerchantUid(paymentDTO.getMerchantUid());
        payment.setPaidAmount(paymentDTO.getPaidAmount());
        payment.setApplyNum(paymentDTO.getApplyNum());
        payment.setPayDate(LocalDateTime.now());

        ///////////////////////// jwt //////////////////////////////////
        payment.setMember(member);

        return paymentRepository.save(payment);



    }

    //결제 내역 조회
    @Override
    public List<PaymentDTO> getPaymentList(String token) {

        String newtoken = new String();
        if(StringUtils.hasText(token)&& token.startsWith("Bearer ")){
            //실제 token의 값만 리턴
            newtoken = token.substring(7);
        }

        //jwt에서 사용자 추출
        String userName = jwtTokenProvider.validateAndGetUsername(newtoken);

        Member member = memberRepository.findByUsername(userName).get();

        //해당 멤버의 결제 내역 조회
        List<Payments> paymentsList = paymentRepository.findByMemberIdOrderByPayDateDesc(member.getId());

        List<PaymentDTO> paymentDTOList = new ArrayList<>();

        for(Payments payments : paymentsList){
            PaymentDTO paymentDTO = PaymentDTO.of(payments);

            paymentDTOList.add(paymentDTO);
        }

        return paymentDTOList;
    }

}
