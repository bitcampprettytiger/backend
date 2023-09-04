package com.example.bitcamptiger.payments.service.Impl;

import com.example.bitcamptiger.jwt.JwtAuthenticationFilter;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.member.reposiitory.MemberRepository;
import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.entity.OrderStatus;
import com.example.bitcamptiger.order.entity.Orders;
import com.example.bitcamptiger.order.repository.OrderRepository;
import com.example.bitcamptiger.order.service.OrderService;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.entity.Payments;
import com.example.bitcamptiger.payments.repository.PaymentRepository;
import com.example.bitcamptiger.payments.service.PaymentService;
import com.example.bitcamptiger.vendor.entity.Vendor;
import com.example.bitcamptiger.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
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
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final VendorRepository vendorRepository;


    //결제하기
    public Payments addPayment(PaymentDTO paymentDTO, Member member){

//        String newtoken = new String();
//        if(StringUtils.hasText(token)&& token.startsWith("Bearer ")){
//        //실제 token의 값만 리턴
//            newtoken = token.substring(7);
//        }
//
//        String userId = jwtTokenProvider.validateAndGetUsername(newtoken);

        //Member 엔티티 조회
//        Member member = memberRepository.findByUsername(userId).orElseThrow();

        //결제 전에 주문을 생성하고 예약 상태로 설정
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setMember(member);
        orderDTO.setVendorId(paymentDTO.getVendorId());
        Orders orders = orderService.createOrder(orderDTO);
        orders.setOrderStatus(OrderStatus.RESERVATION);
        orderRepository.save(orders);

        //결제 생성
        Payments payment = new Payments();
        payment.setPayMethod(paymentDTO.getPayMethod());
        payment.setImpUid(paymentDTO.getImpUid());
        payment.setMerchantUid(paymentDTO.getMerchantUid());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPayDate(LocalDateTime.now());
        payment.setName(paymentDTO.getName());

        ///////////////////////// jwt //////////////////////////////////
        payment.setMember(member);
        payment.setVendor(vendorRepository.findById(paymentDTO.getVendorId()).orElseThrow(EntityNotFoundException::new));


        paymentRepository.save(payment);

        //결제 서비스를 호출해서 결제가 진행된 결과
        boolean paymentOK = true;

        if(paymentOK) {
            orders.setPayments(payment);
            orders.setOrderStatus(OrderStatus.CONFIRMED);
            orderRepository.save(orders);

            //결제와 주문이 완료된 후, Node.js 서버에 알림 보내기
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.postForObject("http://localhost:3030/new-order", orders, Orders.class);

        } else{
            orders.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.save(orders);
        }

        return payment;

    }

    //결제 내역 조회
    @Override
    public List<PaymentDTO> getPaymentList(Member member) {

//        String newtoken = new String();
//        if(StringUtils.hasText(token)&& token.startsWith("Bearer ")){
//            //실제 token의 값만 리턴
//            newtoken = token.substring(7);
//        }

        //jwt에서 사용자 추출
//        String userName = jwtTokenProvider.validateAndGetUsername(newtoken);

//        Member member = memberRepository.findByUsername(userName).get();

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
