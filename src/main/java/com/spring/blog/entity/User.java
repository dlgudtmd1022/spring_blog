package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter // 엔터티를 DTO처러 쓰는 행위는 좋지 못합니다.
@NoArgsConstructor
@Table(name = "users") // 만약 클래스명과 테이블명이 다르게 매칭되기를 원하면 사용하는 어노테이션, USER는 MySQL의 예약어.
public class User implements UserDetails {

    // 필드로 사용할 맴버변수 선언

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) // 수정 불가 옵션
    private Long id; // null 체크 대비

    @Column(nullable = false, unique = true) // 프라이머리키는 아니지만 유일 키로 사용
    private String email;

    // 비밀번호는 null허용 (0Auth2.0을 활용한 소셜로그인은 비밀번호가 없음)
    private String password;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Builder                                   // 인증정보
    public User(String email, String password, String auth, String loginId) { // 생성자에서 인증 정보를 요구함
        this.email = email;
        this.password = password;
        this.loginId = loginId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { // 로그인에 사용할 아이디 입력(unique 요소만 가능)
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() { //로그인중이면 true리턴
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 벤당하지 않았다면 true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}