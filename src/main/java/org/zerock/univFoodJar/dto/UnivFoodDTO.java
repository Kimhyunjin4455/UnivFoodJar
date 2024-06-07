package org.zerock.univFoodJar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnivFoodDTO {
    private Long uno;
    private String restaurantName;
    private String foodField;
    private String signatureMenu;
    private String contact;
    private String address;

    @Builder.Default
    private List<UnivFoodImageDTO> imageDTOList = new ArrayList<>();
    // 내부적으로 리스트를 이용해 이미지 수집


    // UnivFood객체, UnivFoodImage객체, double 값으로 나오는 식당의 평점, Lond타입 리뷰 개수를 Object[] 배열을 리스트에 담은 형태
    // Object[]를 UnivFoodDTO 라는 하나의 객체로 처리
    private double avg;

    //리뷰 갯수 (jpa의 count())
    private int reviewCnt;
    private LocalDateTime regDate, modDate;

}
