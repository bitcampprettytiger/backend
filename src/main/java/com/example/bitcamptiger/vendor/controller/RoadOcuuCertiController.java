package com.example.bitcamptiger.vendor.controller;

import com.example.bitcamptiger.vendor.dto.RoadOcuuCertiData;
import com.example.bitcamptiger.vendor.service.RoadOccuCertiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoadOcuuCertiController {
    @Autowired
    private RoadOccuCertiService openApiService;

    @GetMapping("/api/data")
    public ResponseEntity<List<RoadOcuuCertiData>> fetchDataFromExternalApi() {
        List<RoadOcuuCertiData> dataList = openApiService.fetchDataFromExternalApi();

        if (dataList != null && !dataList.isEmpty()) {
            return ResponseEntity.ok(dataList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/data/{perNo}")
    public ResponseEntity<List<RoadOcuuCertiData>> searchDataByPerNo(@PathVariable String perNo) {
        List<RoadOcuuCertiData> dataList = openApiService.fetchDataFromExternalApi();
        List<RoadOcuuCertiData> result = openApiService.findByPerNo(dataList, perNo);

        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
