package com.example.bitcamptiger.gyeongGiApi.service.impl;


import com.example.bitcamptiger.gyeongGiApi.dto.RowDTO;
import com.example.bitcamptiger.gyeongGiApi.service.GyeongGiApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GyeongGiApiServiceImpl implements GyeongGiApiService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

//    @Override
//    public List<GGStreetVendorResponseDTO.Row> getAllStreetVendorInfo() throws JsonProcessingException {
//        List<GGStreetVendorResponseDTO.Row> allData = new ArrayList<>();
//        int currentPage = 1; // 시작 페이지
//
//        while (true) {
//            // API 호출을 위한 URL 생성
//            String url = apiUrl + "?key=" + apiKey + "&type=json" + "&pIndex=" + currentPage + "&pSize=100";
//
//            // RestTemplate을 사용하여 GET 요청 보내기
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//            String jsonResponse = response.getBody();
//
//            // ObjectMapper 객체 생성
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // JSON 응답을 GGStreetVendorResponseDTO 객체로 변환
//            GGStreetVendorResponseDTO responseDto = objectMapper.readValue(jsonResponse, GGStreetVendorResponseDTO.class);
//
//            // API 호출 결과에 대한 유효성 검사
//            if (responseDto != null && responseDto.getHeadRowWrapperList() != null) {
//                List<GGStreetVendorResponseDTO.Row> rowList = responseDto.getHeadRowWrapperList().get(0).getRowList();
//                if (rowList != null && rowList.size() > 0) {
//                    allData.addAll(rowList); // 현재 페이지의 데이터를 전체 데이터 리스트에 추가
//
//                    if (rowList.size() < 100) {
//                        break; // 페이지 데이터 수가 100개 미만이면 마지막 페이지이므로 반복 종료
//                    }
//                }
//            } else {
//                System.out.println("데이터가 확인되지 않습니다. API 응답을 확인해주세요.");
//                break;
//            }
//
//            currentPage++; // 다음 페이지로 이동
//        }
//
//        return allData;
//    }



    @Override
    public List<RowDTO> getAllStreetVendorInfo() {
        // 첫 번째 페이지 호출을 위한 URL 생성
        String url = apiUrl + "?key=341f55ed9a614bf5ada6255bc7465615" + "&type=json" + "&pIndex=1" + "&pSize=1000"; // 페이지 크기를 10으로 설정
        //String url = "https://openapi.gg.go.kr/GGSTREETVENDSTM?key=341f55ed9a614bf5ada6255bc7465615&type=json&pIndex=1&pSize=1000";
        // RestTemplate을 사용하여 GET 요청 보내기
        RestTemplate restTemplate = new RestTemplate();

        try {
            BufferedReader bf;
            URL requestUrl = new URL(url);
            bf = new BufferedReader(new InputStreamReader(requestUrl.openStream(), "UTF-8"));
            String result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);

            JSONArray ggstreetvendstm = (JSONArray) jsonObject.get("GGSTREETVENDSTM");

            JSONObject ggStreetVendorData = (JSONObject) ggstreetvendstm.get(1);
            JSONArray rowArray = (JSONArray) ggStreetVendorData.get("row");
            System.out.println("rowArray.size(): " + ggstreetvendstm.size());

            if (ObjectUtils.isEmpty(rowArray)) {
                throw new IllegalArgumentException("jsonArray is null");
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for (Object rowData : rowArray) {
                JSONObject jsonData = (JSONObject) rowData;
                list.add(new ObjectMapper().readValue(jsonData.toJSONString(), Map.class));
            }
            List<RowDTO> rowList = new ArrayList<>();

            for(Map<String, Object> data : list) {
                RowDTO rowDTO = RowDTO.builder()
                        .prmsnNm((String) data.get("PRMSN_NM"))
                        .refineLotnoAddr((String) data.get("REFINE_LOTNO_ADDR"))
                        .refineZipno((String) data.get("REFINE_ZIPNO"))
                        .induTypeNm((String) data.get("INDUTYPE_NM"))
                        .licensgDe((String) data.get("LICENSG_DE"))
                        .sigunNm((String) data.get("SIGUN_NM"))
                        .occupTnAr(Double.parseDouble(String.valueOf(data.get("OCCUPTN_AR") != null ? data.get("OCCUPTN_AR") : 0)))
                        .refineRoadnmAddr((String) data.get("REFINE_ROADNM_ADDR"))
                        .refineWgs84Lat((String) data.get("REFINE_WGS84_LAT"))
                        .storeNm((String) data.get("STORE_NM"))
                        .clsbizYn((String) data.get("CLSBIZ_YN"))
                        .refineWgs84Logt((String) data.get("REFINE_WGS84_LOGT"))
                        .build();

                rowList.add(rowDTO);
            }
            System.out.println("rowList.size(): " + rowList.size());
            return rowList;

        } catch (HttpServerErrorException.InternalServerError ex) {
            System.out.println("API 호출 중에 내부 서버 오류가 발생했습니다.");
            ex.printStackTrace();
            return null;
        } catch (Exception ex) {
            System.out.println("API 호출 중에 오류가 발생했습니다.");
            ex.printStackTrace();
            return null;
        }
    }

}