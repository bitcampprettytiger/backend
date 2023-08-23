package com.example.bitcamptiger.common.controller;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.bitcamptiger.configuration.NaverConfiguration;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/S3")
public class S3Controller {


    private final AmazonS3 s3;

    public S3Controller(NaverConfiguration naverConfiguration) {
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        naverConfiguration.getEndPoint(), naverConfiguration.getRegionName()
                ))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                naverConfiguration.getAccessKey(), naverConfiguration.getSecretKey()
                        )
                ))
                .build();
    }


    @GetMapping("/download/{fileName}")
    public String downloadFile(@PathVariable String fileName,
                             HttpServletResponse response) throws IOException {

//        System.out.println(fileName);
        S3Object s3Object = s3.getObject("springboot", fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        // 파일 다운로드를 위한 헤더 설정
        response.setContentType(s3Object.getObjectMetadata().getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        // 파일 스트림을 response의 출력 스트림으로 복사
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.getOutputStream().write(buffer, 0, bytesRead);
        }

        inputStream.close();
        response.getOutputStream().flush();

        return s3.getUrl("springboot",fileName).toString();
    }
}
