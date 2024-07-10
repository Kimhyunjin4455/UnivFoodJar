package org.zerock.univFoodJar.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.univFoodJar.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

//    @Test
//    public void 회원삽입(){
//        IntStream.rangeClosed(1,100).forEach(i -> {
//            Member member = Member.builder()
//                    .email("r"+i+"@zerock.org")
//                    .pw("1111")
//                    .nickname("reviewer"+i)
//                    .build();
//            memberRepository.save(member);
//
//        });
//    }

    @Commit
    @Transactional
    @Test
    public void 회원삭제테스트(){
        Long mid = 100L;
        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }


}
