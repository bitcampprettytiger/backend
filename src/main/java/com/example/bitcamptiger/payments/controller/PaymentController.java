package com.example.bitcamptiger.payments.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.repository.PaymentRepository;
import com.example.bitcamptiger.payments.service.PaymentService;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {


    @Value("${iamport.key}")
    private static String restApiKey;

    @Value("${iamport.secret}")
    private static String restApiSecret;

    //토큰 발급을 위해 아임포트에서 제공해주는 rest api 사용
    private final IamportClient iamportClient;

    private final PaymentRepository paymentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PaymentService paymentService;

    //생성자로 rest api key와 secret을 입력해서 토큰 생성
    @Autowired
    public PaymentController(PaymentRepository paymentRepository, JwtTokenProvider jwtTokenProvider, PaymentService paymentService){
        this.paymentRepository = paymentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.paymentService = paymentService;
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    // 여기서 사용된 Payment 객체는 Entity가아닌 IamPort에서 지원하는 Payment 객체
    //imp_uid로 결제내역 조회
    //아임포트서버에서 imp_uid(거래 고유번호)를 검사하여, 데이터를 보내준다.
    @PostMapping("/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("paymentByImUid 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }



    //결제하기
    @PostMapping("/addPayment")
    public ResponseEntity<?> addPayment(@RequestHeader("Authorization")String token, @RequestBody PaymentDTO paymentDTO){

        ResponseDTO<PaymentDTO> response = new ResponseDTO<>();

        try{
            paymentService.addPayment(paymentDTO, token);

            List<PaymentDTO> paymentDTOList = paymentService.getPaymentList(token);

            response.setItemlist(paymentDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }


    }

    //결제 내역 조회
    @GetMapping("/paymentList")
    public ResponseEntity<?> paymentList(@RequestHeader("Authorization")String token){
        ResponseDTO<PaymentDTO> response = new ResponseDTO<>();
        try{
            List<PaymentDTO> paymentDTOList = paymentService.getPaymentList(token);

            response.setItemlist(paymentDTOList);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

    }


    //결제 취소하기
    @PostMapping("cancelPayment/{id}")
    public ResponseEntity<?> cancelPayment(@PathVariable String id){
        ResponseDTO<Map<String, Objects>> response = new ResponseDTO<>();
        try{
            String accessToken = getToken();

            String url = "https://api.iamport.kr/payments/cancel";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            Map<String, String> body = new HashMap<>();
            body.put("imp_uid", id);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

            CancelResponse response = restTemplate.postForObject(url, requestEntity, CancelResponse.class);
            Map<String, Object> returnMap = new HashMap<>();



        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    public static String getToken(){

        RestTemplate restTemplate = new RestTemplate();

        //REST API사용을 위한 인증(access_token취득)
        String url = "https://api.iamport.kr/users/getToken";

        //REST API 키와 REST Secret을 설정
        Map<String, String> body = new HashMap<>();
        body.put("iamport.key",restApiKey);
        body.put("iamport.secret", restApiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);
        //API 호출 및 응답 수신
        TokenResponse response = restTemplate.postForObject(url, requestEntity, TokenResponse.class);

        return response.get

    }

}