package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// Entity는 불변성을 지키기 위해 Setter를 제공하지 않습니다.
// 한 번 생성된 데이터가 변경될 가능성을 없앱니다.
@Getter @Builder @ToString
@AllArgsConstructor @NoArgsConstructor
@Entity @Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) // not null
    private long replyId; // 댓글번호

    @Column(nullable = false) // not null
    private long blogId; // 블로그번호

    @Column(nullable = false) // not null
    private String replyWriter; // 댓글 작성자

    @Column(nullable = false) // not null
    private String replyContent; // 댓글 내용

    private LocalDateTime publishedAt; // 댓글 작성 날짜
    private LocalDateTime updatedAt; // 댓글 수정 날짜

    // @PrePErsist 어노테이션은 insert, update 되기 전 실행할 로직을 작성합니다.
    @PrePersist
    public void setDefaultValue() {
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    // @Preupdate 어노테이션은 update 되기 전 실행할 로직을 실행합니다.
    @PreUpdate
    public void setUpdateValue(){
        this.updatedAt = LocalDateTime.now();
    }
}