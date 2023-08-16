package com.example.bitcamptiger.common;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.bitcamptiger.Review.entity.ReviewFile;
import com.example.bitcamptiger.configuration.NaverConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@Component
public class reviewFileUtils {

    private final AmazonS3 s3;

    public reviewFileUtils(NaverConfiguration naverConfiguration) {
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



    public ReviewFile parseFileInfo(MultipartFile file, String directoryPath) throws IOException {

        String bucketName = "springboot";

        //리턴할 ReviewDto 객체 생성
        ReviewFile reviewFile = new ReviewFile();

        String reviewFileOrigin = file.getOriginalFilename();

        //같은 파일명 파일이 올라오면 나중에 업로드 된 파일로 덮어써지기 때문에
        //파일명을 날짜_랜덤_...
        SimpleDateFormat formmater =
                new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowDate = new Date();
        String nowDateStr = formmater.format(nowDate);

        UUID uuid = UUID.randomUUID();

        //실제 db에 저장될 파일명
        String reviewFileName = nowDateStr + "_" + uuid.toString()
                + "_" + reviewFileOrigin;

        String reviewFilePath = directoryPath;

        //이미지인지 아닌지 검사
        File checkFile = new File(reviewFileOrigin);
        //파일의 형식 가져오기
        String type = Files.probeContentType(checkFile.toPath());

        if(type != null) {
            if(type.startsWith("image")) {
                reviewFile.setReviewFileCate("image");
            } else {
                reviewFile.setReviewFileCate("etc");
            }
        } else {
            reviewFile.setReviewFileCate("etc");
        }


        //리턴될 Dto 셋팅
        reviewFile.setReviewFileName(reviewFileName);
        reviewFile.setReviewFileOrigin(reviewFileOrigin);
        reviewFile.setReviewFilePath(reviewFilePath);


        //try 구문안에서만 사용할 객체나 변수를 선언할 수 있다.
        //주로 사용후에 close 해줘야되는 객체들을 선언한다.
        try(InputStream fileInputStream = file.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    directoryPath + reviewFileName,
                    fileInputStream,
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviewFile;
    }
}
