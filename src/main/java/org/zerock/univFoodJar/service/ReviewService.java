package org.zerock.univFoodJar.service;

import org.zerock.univFoodJar.dto.ReviewDTO;
import org.zerock.univFoodJar.entity.Member;
import org.zerock.univFoodJar.entity.Review;
import org.zerock.univFoodJar.entity.UnivFood;

import java.util.List;

public interface ReviewService {

    // 식당의 모든 리뷰
    List<ReviewDTO> getListOfUnivFood(Long mno);

    // 식당 리뷰 추가
    Long register(ReviewDTO univFoodReviewDTO);

    // 특정 식당 리뷰 수정
    void modify(ReviewDTO univFoodReviewDTO);

    // 식당 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO univFoodReviewDTO){
        Review univFoodReview = Review.builder()
                .reviewnum(univFoodReviewDTO.getReviewnum())
                .univFood(UnivFood.builder().uno(univFoodReviewDTO.getUno()).build()) //dto의 uno를 이용하여 진짜 UnivFood 객체를 생성하여 이 값을 넣음
                .member(Member.builder().mid(univFoodReviewDTO.getMid()).build())
                .grade(univFoodReviewDTO.getGrade())
                .text(univFoodReviewDTO.getText()).build();

        return univFoodReview;
    }

    default ReviewDTO entityToDTO(Review univFoodReview){
        ReviewDTO univFoodReviewDTO = ReviewDTO.builder()
                .reviewnum(univFoodReview.getReviewnum())
                .uno(univFoodReview.getUnivFood().getUno())
                .mid(univFoodReview.getMember().getMid())
                .nickname(univFoodReview.getMember().getNickname())
                .email(univFoodReview.getMember().getEmail())
                .grade(univFoodReview.getGrade())
                .text(univFoodReview.getText())
                .regDate(univFoodReview.getRegDate())
                .modDate(univFoodReview.getModDate()).build();

        return univFoodReviewDTO;
    }


}

