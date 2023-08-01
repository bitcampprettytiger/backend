package com.example.bitcamptiger.Api.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class GeoController {

    // Vworld(브이월드) API를 사용하여 주소를 좌표(위도, 경도)로 변환하는 기능을 구현
    private static final String API_KEY = "EA9F9D49-DB41-316A-A4F2-E81F8E8EE6E1"; // Vworld API 인증키
    private static final String SEARCH_TYPE = "road "; // 주소 유형 (도로명 주소)
    private static final String EPSG = "epsg:4326"; // 좌표계 (기본값: WGS84 좌표계)
    private static final String GEOCODING_REQUEST_URL = "https://api.vworld.kr/req/address";

    //전체 url 주소
    //https://api.vworld.kr/req/address?service=address&request=getcoord&version=2.0&crs=epsg:4326&address=%EA%B0%95%EB%82%A8%EB%8C%80%EB%A1%9C94%EA%B8%B820&refine=true&format=json&type=road&key=EA9F9D49-DB41-316A-A4F2-E81F8E8EE6E1
    @GetMapping("/geocoding")
    public String geocoding(@RequestParam String address) {
        // Vworld API에서 주소를 좌표로 변환하는 요청 URL을 생성
        StringBuilder sb = new StringBuilder(GEOCODING_REQUEST_URL);
        sb.append("?service=address");
        //getCoord"로 설정하여 주소를 좌표로 변환하는 요청을 보낸다.
        sb.append("&request=getcoord");     //이거 왜 요청 url이랑 java에 나와있는거랑 달라
        sb.append("&version=2.0");
        sb.append("&crs=").append(EPSG);
//        sb.append("&address=").append(URLEncoder.encode(address, StandardCharsets.UTF_8));
        sb.append("&address=").append(address);
        sb.append("&refine=true");
        sb.append("&format=json");
        sb.append("&type=").append(SEARCH_TYPE);
        sb.append("&key=").append(API_KEY);


        try {
            //생성한 요청 URL로 API를 호출하고, API 응답 데이터를 파싱하여 위도(x)와 경도(y) 값을 출력
            URL url = new URL(sb.toString());

            //API 호출은 URL 객체를 사용하여 요청 URL을 열어서 스트림으로 읽어온다.
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            //읽어온 데이터는 JSONParser를 사용하여 JSON 형태로 파싱한 후, response-> result-> point 경로를 따라서 위도와 경도를 추출
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject result = (JSONObject) response.get("result");
            JSONObject point = (JSONObject) result.get("point");

            double latitude = (double) point.get("x");
            double longitude = (double) point.get("y");

            return "위도: " + latitude + ", 경도: " + longitude;



        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
