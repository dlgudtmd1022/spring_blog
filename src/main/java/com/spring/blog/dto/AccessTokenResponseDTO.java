package com.spring.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@AllArgsConstructor
public class AccessTokenResponseDTO {
    private String accessToken;
}
