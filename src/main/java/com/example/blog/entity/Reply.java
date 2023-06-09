package com.example.blog.entity;

import lombok.*;

import java.time.LocalDateTime;

// Entity는 불변성을 지키기 위해 Setter를 제공하지 않습니다.
// 한 번 생성된 데이터가 변경될 가능성을 없앱니다.
@Getter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
public class Reply {

    private long replyId; // 댓글번호
    private long blogId; // 블로그번호
    private String replyWriter; // 댓글 작성자
    private String replyContent; // 댓글 내용
    private LocalDateTime publishedAt; // 댓글 작성 날짜
    private LocalDateTime updatedAt; // 댓글 수정 날짜

}
