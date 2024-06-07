package org.zerock.univFoodJar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.univFoodJar.dto.ReviewDTO;
import org.zerock.univFoodJar.service.ReviewService;

import java.util.List;

// Ajax로 동작하기 때문에 @RestController 사용, ReviewDTO는 json으로 변환되어 처리됨, 새로운 식당 리뷰 등록도 json포맷으로 전송
@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor

public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{uno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("uno") Long uno){
        log.info("---------------list----------------");
        log.info("UNO: " + uno);
        List<ReviewDTO> reviewDTOList = reviewService.getListOfUnivFood(uno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{uno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO univFoodReveiwDTO){
        log.info("---------------add UnivFoodReview----------------");
        log.info("reviewDTO: " + univFoodReveiwDTO);

        Long reviewnum = reviewService.register(univFoodReveiwDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{uno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum
                                            ,@RequestBody ReviewDTO univFoodReviewDTO){
        log.info("--------------modify UnivFoodReview----------------");
        log.info("reviewDTO: " + univFoodReviewDTO);

        reviewService.modify(univFoodReviewDTO);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{uno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum){
        log.info("--------------modify removeReview-------------------");
        log.info("reviewnum: "  + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
