package org.zerock.univFoodJar.service;

import org.zerock.univFoodJar.dto.PageRequestDTO;
import org.zerock.univFoodJar.dto.PageResultDTO;
import org.zerock.univFoodJar.dto.UnivFoodDTO;
import org.zerock.univFoodJar.dto.UnivFoodImageDTO;
import org.zerock.univFoodJar.entity.UnivFood;
import org.zerock.univFoodJar.entity.UnivFoodImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface UnivFoodService {
    Long register(UnivFoodDTO univFoodDTO);

    // UnivFood객체, UnivFoodImage객체, double 값으로 나오는 식당의 평점, Lond타입 리뷰 개수를 Object[] 배열을 리스트에 담은 형태
    // Object[]를 UnivFoodDTO 라는 하나의 객체로 처리
    // 컨트롤러가 호출할 때 사용
    PageResultDTO<UnivFoodDTO, Object[]> getList(PageRequestDTO requestDTO); // 목록 처리

    // UnivFood를 JPA로 처리하기 위해 UnivFoodDTO를 UnivFood 객체로 변환해주어야 함
    // -> dtoToEntity() 추가 (UnivFoodImage 객체들도 같이 처리하기 위해 Map 타입을 이용해 반환)
    default Map<String, Object> dtoToEntity(UnivFoodDTO univFoodDTO){ // Map 타입으로 변환
        // Map 타입으로 UnivFood 객체와 UnivFoodImage 객체의 리스트를 처리
        Map<String, Object> entityMap = new HashMap<>();

        UnivFood univFood = UnivFood.builder()                        // DTO를 정보를 바탕으로 univFood객체 생성
                .uno(univFoodDTO.getUno())
                .restaurantName(univFoodDTO.getRestaurantName())
                .foodField(univFoodDTO.getFoodField())
                .signatureMenu(univFoodDTO.getSignatureMenu())
                .contact(univFoodDTO.getContact())
                .address(univFoodDTO.getAddress())
                .build();

        entityMap.put("univFood", univFood);

        List<UnivFoodImageDTO> imageDTOList = univFoodDTO.getImageDTOList();
        // UnivFoodImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() > 0){            // 이미지DTO가 유효하게 존재한다면
            List<UnivFoodImage> univFoodImageList = imageDTOList.stream().map(univFoodImageDTO -> { // univFoodImageDTO는 imageDTOList의 것들을 다루기 위한 변수             // 각 이미지별로 분리해서
                UnivFoodImage univFoodImage = UnivFoodImage.builder()   // 이미지DTO와 univFood객체를 이용해서
                        .path(univFoodImageDTO.getPath())               // 음식 이미지 객체 반환
                        .imgName(univFoodImageDTO.getImgName())
                        .uuid(univFoodImageDTO.getUuid())
                        .univFood(univFood)
                        .build();
                return univFoodImage;
            }).collect(Collectors.toList());                            // 닫는 스트림

            entityMap.put("imgList", univFoodImageList);
        }

        return entityMap;
    }

    // JPA를 통해 나오는 엔티티 객체와 double, Long등의 값을 UnivFoodDTO로 변환하는 메서드
    // 조회 화면에서 여러 개의 UnivFoodImage를 처리 하기 위해 List<UnivFoodImage> 사용
    default UnivFoodDTO entitiesToDTO(UnivFood univFood, List<UnivFoodImage> univFoodImages, Double avg, Long reviewCnt){

        UnivFoodDTO univFoodDTO = UnivFoodDTO.builder()
                .uno(univFood.getUno())
                .restaurantName(univFood.getRestaurantName())
                .signatureMenu(univFood.getSignatureMenu())
                .foodField(univFood.getFoodField())
                .contact(univFood.getContact())
                .address(univFood.getAddress())
                .regDate(univFood.getRegDate())
                .modDate(univFood.getModDate()).build();

        List<UnivFoodImageDTO> univFoodImageDTOList = univFoodImages.stream().map(univFoodImage -> {
            return UnivFoodImageDTO.builder()
                    .imgName(univFoodImage.getImgName())
                    .path(univFoodImage.getPath())
                    .uuid(univFoodImage.getUuid()).build();
        }).collect(Collectors.toList());

        univFoodDTO.setImageDTOList(univFoodImageDTOList);
        univFoodDTO.setAvg(avg);
        univFoodDTO.setReviewCnt(reviewCnt.intValue());

        return univFoodDTO;
    }

    UnivFoodDTO getUnivFood(Long uno);

    void remove(Long uno);

    Long modify(UnivFoodDTO univFoodDTO);
}

