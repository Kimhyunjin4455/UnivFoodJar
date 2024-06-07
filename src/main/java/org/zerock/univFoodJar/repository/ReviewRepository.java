package org.zerock.univFoodJar.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.univFoodJar.entity.Member;
import org.zerock.univFoodJar.entity.Review;
import org.zerock.univFoodJar.entity.UnivFood;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByUnivFood(UnivFood univFood);

    @Transactional
    @Modifying
    @Query("delete from Review ur where ur.member = :member")
    void deleteByMember(Member member);


    @Modifying
    @Query("delete from Review ur where ur.univFood = :univFood")
    void deleteByUnivFood(UnivFood univFood);


}

