package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@AllArgsConstructor @Getter @Setter
@ToString @Builder @NoArgsConstructor
public class ReplyCreateRequestDTO {
    // 글번호, 댓글쓴이, 댓글내용
    private long blogId; // 글번호
    private String replyWriter; // 댓글쓴이
    private String replyContent; // 댓글내용

    // 엔터리 클래스를 DTO로 변경해주는 메서드
    public ReplyCreateRequestDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
