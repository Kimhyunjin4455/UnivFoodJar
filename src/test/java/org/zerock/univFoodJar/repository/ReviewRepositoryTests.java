package org.zerock.univFoodJar.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.univFoodJar.entity.Member;
import org.zerock.univFoodJar.entity.Review;
import org.zerock.univFoodJar.entity.UnivFood;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

//    @Test
//    public void 음식점리뷰삽입(){
//        IntStream.rangeClosed(1,200).forEach(i->{
//            Long uno = (long)(Math.random()*100)+1;//(long)14;
//
//            Long mid = ((long)(Math.random()*100)+1);
//            Member member = Member.builder().mid(mid).build();
//
//            Review foodReview = Review.builder()
//                    .member(member) //회원 id 적용된 상태
//                    .univFood(UnivFood.builder().uno(uno).build())
//                    .grade((int)(Math.random()*5) + 1)
//                    .text("맛 평가,,,"+i)
//                    .build();
//
//            reviewRepository.save(foodReview);
//        });
//    }

    @Test
    public void 음식점리뷰조회테스트(){
        UnivFood univFood = UnivFood.builder().uno(9L).build();

        List<Review> result = reviewRepository.findByUnivFood(univFood);

        result.forEach(univFoodReview -> {
            System.out.println(univFoodReview.getReviewnum());
            System.out.println("\t" + univFoodReview.getGrade());
            System.out.println("\t" + univFoodReview.getText());
            System.out.println("\t" + univFoodReview.getMember().getEmail());
            System.out.println("------------------------------------");
        });
    }
}

