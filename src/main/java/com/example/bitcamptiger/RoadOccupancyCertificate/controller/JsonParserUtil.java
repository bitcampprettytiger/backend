package com.example.bitcamptiger.RoadOccupancyCertificate.controller;

import com.example.bitcamptiger.RoadOccupancyCertificate.dto.RoadOcuuCertiData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JsonParserUtil {
    public static List<RoadOcuuCertiData> parseData(String jsonString) throws ParseException {
        List<RoadOcuuCertiData> dataList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
        JSONObject bodyObject = (JSONObject) jsonObject.get("response");
        JSONObject itemsObject = (JSONObject) bodyObject.get("body");
        JSONArray itemsArray = (JSONArray) itemsObject.get("items");

        if (itemsArray != null) {
            for (Object itemObj : itemsArray) {
                JSONObject itemJson = (JSONObject) itemObj;
                RoadOcuuCertiData data = new RoadOcuuCertiData();
                data.setRqsNo((String) itemJson.get("rqsNo"));
                data.setPrmCd((String) itemJson.get("prmCd"));
                data.setPrmNm((String) itemJson.get("prmNm"));
                data.setOrcd((String) itemJson.get("orcd"));
                data.setOrnm((String) itemJson.get("ornm"));
                data.setLgnId((String) itemJson.get("lgnId"));
                data.setEdDt((String) itemJson.get("edDt"));
                data.setEdTyCd((String) itemJson.get("edTyCd"));
                data.setEdRsn((String) itemJson.get("edRsn"));
                data.setFeeCts((String) itemJson.get("feeCts"));
                data.setPerNo((String) itemJson.get("perNo"));
                data.setPmsnRectMtdCd((String) itemJson.get("pmsnRectMtdCd"));
                data.setRlAppiNm((String) itemJson.get("rlAppiNm"));
                data.setOnlPmsnNo((String) itemJson.get("onlPmsnNo"));
                data.setLstUpdDe((String) itemJson.get("lstUpdDe"));
                data.setMasterLinkCnt(String.valueOf(itemJson.get("masterLinkCnt")));
                dataList.add(data);
            }
        }

        return dataList;
    }

}
