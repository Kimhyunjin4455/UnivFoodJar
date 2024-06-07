package org.zerock.univFoodJar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.univFoodJar.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
