package com.example.bitcamptiger.gyeongGiApi.service.impl;


import com.example.bitcamptiger.gyeongGiApi.dto.RowDTO;
import com.example.bitcamptiger.gyeongGiApi.entity.GyeonggiVenders;
import com.example.bitcamptiger.gyeongGiApi.repository.GyeonggiVendersRepository;
import com.example.bitcamptiger.gyeongGiApi.service.GyeongGiApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class GyeongGiApiServiceImpl implements GyeongGiApiService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.serviceKey}")
    private String apiKey;

    private final GyeonggiVendersRepository gyeonggiVendersRepository;

    public GyeongGiApiServiceImpl(GyeonggiVendersRepository gyeonggiVendersRepository) {
        this.gyeonggiVendersRepository = gyeonggiVendersRepository;
    }


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
            // rowList가 DTO 리스트이므로 여기에서 엔터티로 변환하고 저장
            // 엔터티를 저장할 리스트

            //필요한 정보 추출하여 엔티티에 저장
            for(RowDTO rowDTO : rowList){
                if(rowDTO.getInduTypeNm() != null && rowDTO.getInduTypeNm().endsWith("가게")){

                    GyeonggiVenders existingEntity = gyeonggiVendersRepository.findByPrmsnNmAndStoreNm(rowDTO.getPrmsnNm(), rowDTO.getStoreNm());

                    if(existingEntity == null){
                        GyeonggiVenders gyeonggiVenders = new GyeonggiVenders();
                        gyeonggiVenders.setPrmsnNm(rowDTO.getPrmsnNm());
                        gyeonggiVenders.setRefineLotnoAddr(rowDTO.getRefineLotnoAddr());
                        gyeonggiVenders.setRefineZipno(rowDTO.getRefineZipno());
                        gyeonggiVenders.setInduTypeNm(rowDTO.getInduTypeNm());
                        gyeonggiVenders.setLicensgDe(rowDTO.getLicensgDe());
                        gyeonggiVenders.setSigunNm(rowDTO.getSigunNm());
                        gyeonggiVenders.setOccupTnAr(rowDTO.getOccupTnAr());
                        gyeonggiVenders.setRefineRoadnmAddr(rowDTO.getRefineRoadnmAddr());
                        gyeonggiVenders.setRefineWgs84Lat(rowDTO.getRefineWgs84Lat());
                        gyeonggiVenders.setStoreNm(rowDTO.getStoreNm());
                        gyeonggiVenders.setClsbizYn(rowDTO.getClsbizYn());
                        gyeonggiVenders.setRefineWgs84Logt(rowDTO.getRefineWgs84Logt());

                        gyeonggiVendersRepository.save(gyeonggiVenders);
                    }
                }

            }

            return rowList;
//
//            for (RowDTO dto : rowList) {
//                if (dto.getInduTypeNm() != null && dto.getInduTypeNm().endsWith("가게")) {
//
//                    entityList.add(entity);
//                }
//            }

            // 이후 예외 처리 코드...
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