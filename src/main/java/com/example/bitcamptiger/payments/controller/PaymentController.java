package com.example.bitcamptiger.payments.controller;

import com.example.bitcamptiger.dto.ResponseDTO;
import com.example.bitcamptiger.jwt.JwtTokenProvider;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.payments.dto.PaymentDTO;
import com.example.bitcamptiger.payments.entity.Payments;
import com.example.bitcamptiger.payments.repository.PaymentRepository;
import com.example.bitcamptiger.payments.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate restTemplate;


    //생성자로 rest api key와 secret을 입력해서 토큰 생성
    @Autowired
    public PaymentController(PaymentRepository paymentRepository, JwtTokenProvider jwtTokenProvider, PaymentService paymentService, RestTemplate restTemplate){
        this.paymentRepository = paymentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
        this.iamportClient = new IamportClient(restApiKey, restApiSecret);
    }

    // 여기서 사용된 Payment 객체는 Entity가아닌 IamPort에서 지원하는 Payment 객체
    //imp_uid로 결제내역 조회
    //아임포트서버에서 imp_uid(거래 고유번호)를 검사하여, 데이터를 보내준다.
    @Operation(summary = "paymentByImpUid", description = "imp_uid로 결제내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("paymentByImUid 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }


    //결제하기
    @Operation(summary = "addPayment", description = "결제하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/addPayment")
    public ResponseEntity<?> addPayment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PaymentDTO paymentDTO){

        ResponseDTO<PaymentDTO> response = new ResponseDTO<>();
        try{
            //CustomUserDetails로부터 회원 정보를 가져옴
            Member member = customUserDetails.getUser();

            Payments payments = paymentService.addPayment(paymentDTO, member);

            response.setItem(PaymentDTO.of(payments));
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(response);
        }catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }


    }

    //결제 내역 조회
    @Operation(summary = "paymentList", description = "결제내역 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/paymentList")
    public ResponseEntity<?> paymentList(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        ResponseDTO<PaymentDTO> response = new ResponseDTO<>();
        try{
            Member member = customUserDetails.getUser();
            List<PaymentDTO> paymentDTOList = paymentService.getPaymentList(member);

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
    @Operation(summary = "cancelPayment", description = "결제 취소하기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "통과"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/cancelPayment/{id}")
    public ResponseEntity<?> cancelPayment(@PathVariable String id){
        ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
        try{
            String accessToken = getToken();

            String url = "https://api.iamport.kr/payments/cancel";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            Map<String, String> body = new HashMap<>();
            body.put("imp_uid", id);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

            CancelResponse cancelResponse = restTemplate.postForObject(url, requestEntity, CancelResponse.class);
            Map<String, Object> returnMap = new HashMap<>();

            if (cancelResponse.getCode() == 0) {
                returnMap.put("msg", "취소가 완료되었습니다.");

                response.setItem(returnMap);
                response.setStatusCode(HttpStatus.OK.value());
                return ResponseEntity.ok().body(response);
            } else {
                returnMap.put("msg", "결제 취소가 실패하였습니다.");

                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setErrorMessage(cancelResponse.getMessage());
                response.setItem(returnMap);

                return ResponseEntity.badRequest().body(response);
            }



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

        return response.getResponse().getAccess_token();

    }

    @Data
    public static class TokenResponse {
        private int code;
        private String message;
        private TokenDetails response;
    }
    @Data
    public static class TokenDetails {
        private String access_token;
        private long now;
        private long expired_at;
    }
    @Data
    public static class CancelResponse {
        private int code;
        private String message;
        private Map<String, Object> response;
    }


}
