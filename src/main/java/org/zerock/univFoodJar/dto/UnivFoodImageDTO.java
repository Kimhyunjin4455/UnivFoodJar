package org.zerock.univFoodJar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "이미지 DTO")
public class UnivFoodImageDTO {
    @Schema(description = "UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;
    @Schema(description = "이미지 이름", example = "sample.jpg")
    private String imgName;
    @Schema(description = "이미지 경로", example = "/images/sample")
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
