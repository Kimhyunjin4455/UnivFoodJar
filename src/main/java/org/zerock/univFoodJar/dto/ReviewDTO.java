package org.zerock.univFoodJar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    // 화면에 필요한 모든 내용을 가지고 있어야 하므로, 회원의 아이디/닉네임/이메일도 같이 처리할 수 있도록

    //review num
    private Long reviewnum;

    //UnivFood uno
    private Long uno;

    //Member id
    private Long mid;
    //Member nickname;
    private String nickname;
    //Member email
    private String email;
    private int grade;
    private String text;
    private LocalDateTime regDate, modDate;
}
