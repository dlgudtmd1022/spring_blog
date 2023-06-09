package com.example.blog.entity;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

// 역직렬화 (디비 -> 자바객체)가 가능하도록 blog 테이블 구조에 맞춰서 맴버변수를 선언해주세요.
@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder // 빌더패턴 새엉자를 쓸수있게 해줌
public class Blog {

    private long blogId; //  블로그 아이디
    private String writer; // 작성자
    private String blogTitle; // 글 제목
    private String blogContent; // 글 내용

    private LocalDateTime publishedAt; // 작성 날짜
    private LocalDateTime updatedAt; // 수정 날짜
    private long blogCount; // 조회수

}
