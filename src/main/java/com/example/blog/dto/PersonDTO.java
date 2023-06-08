package com.example.blog.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class PersonDTO {
    private long id;
    private String name;
    private int age;
}
