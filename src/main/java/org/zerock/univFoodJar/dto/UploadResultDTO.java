package org.zerock.univFoodJar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String fileName;
    private String uuid;
    private String folderPath;

    // 이 클래스는 실제 파일과 관련된 모든 정보를 가지며, 전체 경로가 필요한 경우를 대비해 getImageURL() 생성
    // UploadController에서 업로드 결과 반환을 위해 ResponseEntity를 이용해서 이를 처리
    public String getImageURL(){
        try{
            return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try{
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
