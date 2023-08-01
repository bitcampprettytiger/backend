package com.example.bitcamptiger.board.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class RegistrationController {

        @PostMapping("/check-business")
        public String checkBusiness(@RequestBody BusinessData businessData) {
            // 여기서 API 요청을 수행하고 결과를 처리하여 반환합니다.
            // 이 예시에서는 간단히 "성공! 요청이 처리되고 있습니다."를 반환합니다.
            return "성공! 요청이 처리되고 있습니다.";
        }

        // JSON 데이터를 매핑하기 위한 POJO 클래스를 정의합니다.
        private static class BusinessData {
            private String[] b_no;

            public String[] getB_no() {
                return b_no;
            }

            public void setB_no(String[] b_no) {
                this.b_no = b_no;
            }
        }

}
