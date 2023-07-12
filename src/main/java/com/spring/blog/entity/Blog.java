package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

// 역직렬화 (디비 -> 자바객체)가 가능하도록 blog 테이블 구조에 맞춰서 맴버변수를 선언해주세요.
// 실무에서는 대부분의 경우 엔터티 클래스에는 세터를 만들지 않습니다.
@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder // 빌더패턴 새엉자를 쓸수있게 해줌
@Entity
@DynamicInsert // null인 필드값이
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false) // not null
    private long blogId; //  블로그 아이디
    @Column(nullable = false) // not null
    private String writer; // 작성자
    @Column(nullable = false) // not null
    private String blogTitle; // 글 제목
    @Column(nullable = false) // not null
    private String blogContent; // 글 내용

    private LocalDateTime publishedAt; // 작성 날짜
    private LocalDateTime updatedAt; // 수정 날짜

    @ColumnDefault("0")// 조회수는 기본값을 0으로 설정
    private Long blogCount; // Wrapper Long형을 사용, 기본형 변수는 null을 가질 수 없음

    // @PrePErsist 어노테이션은 insert, update 되기 전 실행할 로직을 작성합니다.
    @PrePersist
    public void setDefaultValue(){
        this.blogCount = this.blogCount == null ? 0 : this.blogCount;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    // @Preupdate 어노테이션은 update 되기 전 실행할 로직을 실행합니다.
    @PreUpdate
    public void setUpdateValue(){
        this.updatedAt = LocalDateTime.now();
    }
}
