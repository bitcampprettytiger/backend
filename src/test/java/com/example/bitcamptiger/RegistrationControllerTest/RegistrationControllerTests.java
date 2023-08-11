package com.example.bitcamptiger.RegistrationControllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCheckBusiness() throws Exception {
        // 테스트하고자 하는 비즈니스 번호
        String businessNumber = "1062491692";

        // 테스트 API 호출
        mockMvc.perform(MockMvcRequestBuilders.get("/checkBusiness/{name}", businessNumber))
                // HTTP 상태코드가 200 (OK)인지 검증
                .andExpect(MockMvcResultMatchers.status().isOk())
                // HTTP 응답의 Content-Type이 JSON인지 검증
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // 응답 JSON 데이터의 특정 필드 값을 검증
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].b_no").value("1062491692"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].b_stt").value("계속사업자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].b_stt_cd").value("01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].tax_type").value("부가가치세 일반과세자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].tax_type_cd").value("01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].end_dt").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].utcc_yn").value("N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].tax_type_change_dt").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].invoice_apply_dt").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].rbf_tax_type").value("해당없음"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].rbf_tax_type_cd").value("99"));
    }


    @Test
    public void testCheckBusinessWithInvalidNumber() throws Exception {
        String invalidBusinessNumber = "123"; // 잘못된 형식의 사업자 번호

        // 테스트 API 호출
        mockMvc.perform(MockMvcRequestBuilders.get("/checkBusiness/{name}", invalidBusinessNumber))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Invalid business number"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The provided business number is invalid."));

    }

    @Test
    public void testCheckBusinessWithNonExistentNumber() throws Exception {
        String nonExistentBusinessNumber = "9876543210"; // 존재하지 않는 사업자 번호

        // 테스트 API 호출
        mockMvc.perform(MockMvcRequestBuilders.get("/checkBusiness/{name}", nonExistentBusinessNumber))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Business not found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The provided business number does not exist."));
        // 또는 다른 방식으로 검증 가능
        // .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404));

        // 로그 출력
        System.out.println("===== Test: testCheckBusinessWithNonExistentNumber =====");
        System.out.println("Mock API 호출 결과:");
        mockMvc.perform(MockMvcRequestBuilders.get("/checkBusiness/{name}", nonExistentBusinessNumber))
                .andDo(MockMvcResultHandlers.print());
        System.out.println("====================");
    }
}


