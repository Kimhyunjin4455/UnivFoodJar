package org.zerock.univFoodJar.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.univFoodJar.dto.UploadResultDTO;
import org.zerock.univFoodJar.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.zerock.upload.path}") // application.properties의 변수
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){    // 동시에 여러 개의 파일 정보 처리 위해 MultipartFile[]
    // 업로드 결과 반환하기 위해 ResponseEntity 이용하여 처리
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile: uploadFiles){

            // 이미지 파일만 업로드 가능하도록
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
            }

            // 실제 파일 이름(IE나 Edge는 전체 경로가 들어옴)
            String originalName = uploadFile.getOriginalFilename();     // 업로드 하는 파일의 이름 파악
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
            log.info("fileName: " + fileName);


            // 날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            // 저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            // Path 클래스는 OS에 독립적으로 파일, 디렉토리의 경로를 표현하고 다룰 수 있는 방법을 제공
            Path savePath = Paths.get(saveName); // Paths 클래스는 Path 클래스를 다룰 수 있는 여러 메서드를 제공

            try{
                uploadFile.transferTo(savePath); // 원본 파일 저장

                // 섬네일 파일 명 생성
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);

                // 섬네일 생성 (가로 세로가 100px인 섬네일 생성, thumbnailFile)
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);


                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }

        }//for
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }
    // 브라우저는 업로드 처리 후에 json의 배열 형태로 결과 전달 받음
    // 이 업로드 결과를 화면에서 확인하기 위해
    // 브라우저에서 링크를 통해 <img>태그 후가 + 해당URL이 호출되는 경우 서버에서 이미지 파일 데이터를 브라우저로 전송
    // 이를 위해 '/display?fileName=xx'와 같은 URL 호출 시 이미지가 전송되도록 메서드 추가
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size){
        // size 파라미터는 원본 파일인지 섬네일인지 구분하기 위한 것(값이 1인 경우 원본 파일 전송)
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName: " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            if(size != null && size.equals("1")){
                file = new File(file.getParent(), file.getName().substring(2));
            }


            log.info("file: "+ file);

            HttpHeaders header = new HttpHeaders();

            // MIME타입 처리 (파일의 확장자에 따라 브라우저에 전송하는 MIME타입이 달라져야 하는 문제를 probeContentType()통해 처리)
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            // 파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);


        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    // URL인코딩된 파일 이름을 파라미터로 받아 해당 파일을 byte[]로 만들어 브라우저로 전송
    // 브라우저에 업로드된 결과 중 UploadResultDTO의 getImageURL()을 통한 imageURL 속성이 있음

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        // 원본 파일의 이름을 파라미터로 받아, File 객체를 이용해 원본과 섬네일을 같이 삭제
        String srcFileName = null;
        try{
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // 파일 저장 시 고려할 점
    // 업로드된 확장자가 이미지만 가능하도록 검사 >> if문과 log.warn 통한 해결
    // 동일한 이름의 파일이 업로드 된다면 기존 파일을 덮어쓰는 문제 >> 고유의 UUID 값을 붙여 해결
    // 업로드된 파일을 저장하는 폴더의 용량

    private String makeFolder(){
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        // // 아래 API(File.separator)를 이용하면 OS에 호환되는 파일 경로를 구성할 수 있음 (프로그램이 실행 중인 OS에 해당하는 구분자를 리턴)
        String folderPath = str.replace("//", File.separator);


        // 폴더 생성
        File uploadPathFolder = new File(uploadPath, folderPath);
        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }


}
