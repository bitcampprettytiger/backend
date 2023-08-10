package com.example.bitcamptiger.vendor.service.Impl;

import com.example.bitcamptiger.vendor.controller.JsonParserUtil;
import com.example.bitcamptiger.vendor.dto.RoadOcuuCertiData;
import com.example.bitcamptiger.vendor.service.RoadOccuCertiService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadOccuCertiServiceImpl implements RoadOccuCertiService {

    private final JsonParserUtil jsonParserUtil;
    private static final String SERVICE_KEY = "7775A10E-FF6D-4731-8D16-5640D301C3CF";

    //검색시작일 => 임의로 설정해 둠
    private static final String SEARCH_ED_BG_DT = "19800101";

    //검색 종료일 => 현재 날짜로 지정해 놓음
    private static final String SEARCH_ED_ED_DT = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    @Override
    public List<RoadOcuuCertiData> fetchDataFromExternalApi() {
        try {
            // 외부 API 호출
            StringBuilder urlBuilder = new StringBuilder("https://www.calspia.go.kr/io/openapi/pm/selectIoPmPermitList.do");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + SERVICE_KEY);
            urlBuilder.append("&" + URLEncoder.encode("searchEdBgDt", "UTF-8") + "=" + SEARCH_ED_BG_DT);
            urlBuilder.append("&" + URLEncoder.encode("searchEdEdDt", "UTF-8") + "=" + SEARCH_ED_ED_DT);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("465", "UTF-8"));
            urlBuilder.append("&type=json");

            // URL을 이용하여 연결 및 데이터 수신
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            // API 응답 데이터를 문자열로 변환하여 저장
            StringBuilder resultBuffer = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuffer.append(line);
            }
            rd.close();
            conn.disconnect();

            // JSON 데이터를 파싱하여 리스트로 변환
            List<RoadOcuuCertiData> dataList = jsonParserUtil.parseData(resultBuffer.toString());
            return dataList;

        } catch (IOException | ParseException e) {
            //예외처리
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RoadOcuuCertiData> findByPerNoAndRlAppiNm(List<RoadOcuuCertiData> dataList, String perNo, String rlAppiNm) {
        List<RoadOcuuCertiData> result = new ArrayList<>();

        // 주어진 조건에 맞는 데이터 찾아서 결과 리스트에 추가
        for (RoadOcuuCertiData data : dataList) {
            if (data.getPerNo().equals(perNo) && data.getRlAppiNm().equals(rlAppiNm)) {
                result.add(data);
            }
        }

        return result;
    }


    @Override
    public RoadOcuuCertiData authenticateAndReturnMessage(String perNo, String rlAppiNm) {
        List<RoadOcuuCertiData> dataList = fetchDataFromExternalApi();
        RoadOcuuCertiData realData = new RoadOcuuCertiData();
        for (RoadOcuuCertiData data : dataList) {
            if (data.getPerNo().equals(perNo) && data.getRlAppiNm().equals(rlAppiNm)) {
                // 데이터가 일치하면 해당 데이터를 realData에 할당
                realData = data;
                break;// 데이터를 찾았으므로 더 이상 반복할 필요 없음
            }
        }


        return realData;
    }
}