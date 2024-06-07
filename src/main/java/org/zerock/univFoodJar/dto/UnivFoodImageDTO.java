package org.zerock.univFoodJar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnivFoodImageDTO {
    private String uuid;
    private String imgName;
    private String path;

    // getImageURL, getThumbnailURL은 타임리프로 출력하여 사용
    public String getImageURL(){
        try{
            return URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }return "";
    }

    public String getThumbnailURL(){
        try{
            return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }return "";
    }

}
