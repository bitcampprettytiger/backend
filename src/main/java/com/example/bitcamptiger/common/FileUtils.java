package com.example.bitcamptiger.common;

import com.example.bitcamptiger.menu.entity.MenuImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtils {

    //업로드된 파일의 정보를 가공. 해당 정보를 MenuImage 객체로 반환.
    public static MenuImage parseFileInfo(MultipartFile file, String attachPath) throws IOException{

        //리턴할 객체 생성
        MenuImage menuImage = new MenuImage();

        //이미지 파일 원본 이름
        String ImageOrigin = file.getOriginalFilename();

        //파일명 날짜 랜덤으로 생성
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowDate = new Date();
        String nowDateStr = format.format(nowDate);

        //고유한 식별자 생성
        UUID uuid = UUID.randomUUID();

        //실제 db에 저장될 파일명
        String fileName = nowDateStr + "_" + uuid.toString() + "_" + ImageOrigin;

        //첨부파일이 저장될 경로
        String filePath = attachPath;

        //이미지인지 아닌지 검사
        File checkFile = new File(ImageOrigin);
        //파일의 형식 가져오기
        String type = Files.probeContentType(checkFile.toPath());

        if(type != null){
            if(type.startsWith("image")){
                menuImage.setFileCate("image");
            }else{
                menuImage.setFileCate("etc");
            }
        }else{
            menuImage.setFileCate("etc");
        }

        //파일 업로드를 위한 File 객체 생성
        File uploadFile = new File(attachPath + fileName);

        //menuImage 객체에 파일명, 원본파일명, 저장경로 설정
        menuImage.setFileName(fileName);
        menuImage.setOriginName(ImageOrigin);
        menuImage.setUrl(filePath);

        try{
            file.transferTo(uploadFile);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return menuImage;
    }
}
