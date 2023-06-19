package com.example.blog.dto;

import com.example.blog.entity.Reply;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class ReplyUpdateDTO {

    // 글번호, 댓글쓴이, 댓글내용
    private long replyId; // 댓글번호
    private String replyWriter; // 댓글쓴이
    private String replyContent; // 댓글내용

    // 엔터리 클래스를 DTO로 변경해주는 메서드
    public ReplyUpdateDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}