package com.example.bitcamptiger.vendor.service;

import com.example.bitcamptiger.vendor.controller.JsonParserUtil;
import com.example.bitcamptiger.vendor.dto.RoadOcuuCertiData;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadOccuCertiService {

    private final JsonParserUtil jsonParserUtil;

    public List<RoadOcuuCertiData> fetchDataFromExternalApi() {
        try {
            // 외부 API 호출
            StringBuilder urlbuilder = new StringBuilder("https://www.calspia.go.kr/io/openapi/pm/selectIoPmPermitList.do");
            urlbuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=7775A10E-FF6D-4731-8D16-5640D301C3CF");
            urlbuilder.append("&" + URLEncoder.encode("searchEdBgDt", "UTF-8") + "=" + URLEncoder.encode("20230702", "UTF-8"));
            urlbuilder.append("&" + URLEncoder.encode("searchEdEdDt", "UTF-8") + "=" + URLEncoder.encode("20230802", "UTF-8"));
            urlbuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlbuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("465", "UTF-8"));
            urlbuilder.append("&type=json");

            URL url = new URL(urlbuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder resultBuffer = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultBuffer.append(line);
            }
            rd.close();
            conn.disconnect();

            System.out.println(resultBuffer);

            List<RoadOcuuCertiData> dataList = jsonParserUtil.parseData(resultBuffer.toString());
            return dataList;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<RoadOcuuCertiData> findByPerNo(List<RoadOcuuCertiData> dataList, String perNo) {

        List<RoadOcuuCertiData> result = new ArrayList<>();

        for (RoadOcuuCertiData data : dataList) {
            if (data.getPerNo().equals(perNo)) {
                // perNo와 일치하는 데이터를 찾은 경우 해당 데이터의 다른 변수 값을 추가
                RoadOcuuCertiData filteredData = new RoadOcuuCertiData();
                filteredData.setRqsNo(data.getRqsNo());
                filteredData.setPrmCd(data.getPrmCd());
                filteredData.setPrmNm(data.getPrmNm());
                filteredData.setOrcd(data.getOrcd());
                filteredData.setOrnm(data.getOrnm());
                filteredData.setLgnId(data.getLgnId());
                filteredData.setEdDt(data.getEdDt());
                filteredData.setEdTyCd(data.getEdTyCd());
                filteredData.setEdRsn(data.getEdRsn());
                filteredData.setFeeCts(data.getFeeCts());
                filteredData.setPerNo(data.getPerNo());
                filteredData.setPmsnRectMtdCd(data.getPmsnRectMtdCd());
                filteredData.setRlAppiNm(data.getRlAppiNm());
                filteredData.setOnlPmsnNo(data.getOnlPmsnNo());
                filteredData.setLstUpdDe(data.getLstUpdDe());
                filteredData.setMasterLinkCnt(data.getMasterLinkCnt());

                result.add(filteredData);
            }
        }

        return result;
    }

}
