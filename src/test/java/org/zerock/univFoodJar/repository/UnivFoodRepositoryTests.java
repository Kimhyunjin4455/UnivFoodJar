package org.zerock.univFoodJar.repository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.univFoodJar.dto.UnivFoodDTO;
import org.zerock.univFoodJar.entity.UnivFood;
import org.zerock.univFoodJar.entity.UnivFoodImage;
import org.zerock.univFoodJar.service.UnivFoodService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnivFoodRepositoryTests {
    @Autowired
    private UnivFoodRepository univFoodRepository;
    @Autowired
    private UnivFoodImageRepository univFoodImageRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UnivFoodService univFoodService;

    @Commit
    @Transactional
    @Test
    public void 음식점정보삽입(){

        IntStream.rangeClosed(1,100).forEach(i->{
            UnivFood univFood = UnivFood.builder()
                    .restaurantName("음식점,,," +i)
                    .signatureMenu("대표메뉴,,,"+i)
                    .foodField("음식분야(일식/중식/한식/분식)")
                    .address("주소,,,"+i)
                    .contact("연락처,,,"+i)
                    .build();

            System.out.println("------------------------------");

            univFoodRepository.save(univFood);

            int count  = (int)(Math.random() * 5) + 1; // 1,2,3,4

            for(int j = 0; j < count; j++){
                UnivFoodImage univFoodImage = UnivFoodImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .univFood(univFood)
                        .imgName("test"+j+".jpg")
                        .build();
                univFoodImageRepository.save(univFoodImage);
                //                try {
//                    // 랜덤한 색상으로 이미지 생성
//                    BufferedImage image = generateRandomColorImage(200, 200);
//
//                    // 이미지 파일로 저장
//                    File output = new File( uploadPath + File.separator + "test" + j + ".jpg");
//                    ImageIO.write(image, "jpg", output);
//
//                    // UnivFoodImage 객체 생성 및 저장
//                    UnivFoodImage univFoodImage = UnivFoodImage.builder()
//                            .uuid(UUID.randomUUID().toString())
//                            .univFood(univFood)
//                            .imgName("test" + j + ".jpg")
//                            .build();
//                    univFoodImageRepository.save(univFoodImage);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println("==============================");
        });

    }
    @Test
    public void testGetUnivFoodWithAll(){
        List<Object[]> result = univFoodRepository.getUnivFoodWithAll(9L);
        System.out.println(result);
        for (Object[] arr: result){
            System.out.println(Arrays.toString(arr));
        }
    }

    //    @Commit
//    @Transactional
//    @Test
//    public void univFoodDeleteTest(){
//        UnivFood univFood = univFoodRepository.findById(99L).get();
//
//        reviewRepository.deleteByUnivFood(univFood);
//        univFoodImageRepository.deleteByUnivFoodImage(univFood);
//        univFoodRepository.deleteByUnivFood(univFood.getUno());
//    }


    @Commit
    @Transactional
    @Test
    public void testModify(){
        UnivFoodDTO univFoodDTO = UnivFoodDTO.builder()
                .uno(103L)
                .restaurantName("modify102")
                .signatureMenu("modify102Menu")
                .address("modifyAddress")
                .contact("modifyContact")
                .build();

        univFoodService.modify(univFoodDTO);

    }

}
