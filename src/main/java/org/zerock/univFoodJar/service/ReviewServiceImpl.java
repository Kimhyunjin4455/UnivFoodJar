package org.zerock.univFoodJar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.univFoodJar.dto.ReviewDTO;
import org.zerock.univFoodJar.entity.Review;
import org.zerock.univFoodJar.entity.UnivFood;
import org.zerock.univFoodJar.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    @Override
    public List<ReviewDTO> getListOfUnivFood(Long uno) {
        UnivFood univFood = UnivFood.builder().uno(uno).build();
        List<Review> result = reviewRepository.findByUnivFood(univFood);

        return result.stream().map(univFoodReview -> entityToDTO(univFoodReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO univFoodReviewDTO) {
        Review univFoodReview = dtoToEntity(univFoodReviewDTO);
        reviewRepository.save(univFoodReview);

        return univFoodReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO univFoodReviewDTO) {
        Optional<Review> result = reviewRepository.findById(univFoodReviewDTO.getReviewnum());

        if (result.isPresent()){
            Review univFoodReview = result.get();
            univFoodReview.changeGrade(univFoodReviewDTO.getGrade());
            univFoodReview.changeText(univFoodReviewDTO.getText());

            reviewRepository.save(univFoodReview);
        }
    }

    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }
}

