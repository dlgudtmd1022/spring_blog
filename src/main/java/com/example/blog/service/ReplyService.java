package com.example.blog.service;

import com.example.blog.dto.ReplyFindByIdDTO;
import com.example.blog.dto.ReplyInsertDTO;
import com.example.blog.dto.ReplyUpdateDTO;
import com.example.blog.entity.Reply;

import java.util.List;

public interface ReplyService {

    // 글 번호 입력시 전체 조회해서 리턴해주는 findByBlogId() 메서드 정의
    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    // 단일 댓글 번호 입력시, 댓글 정보를 리턴해주는 findByReplyId()메서드 정의
    ReplyFindByIdDTO findByReplyId(long replyId);

    // 댓글번호 입력시 삭제되도록 해주는 deleteByReplyId() 메서드 정의
    void deleteByReplyId(long replyId);

    // insert 용도로 정의한 DTO를 넘겨서 save() 메서드 정의
    void save(ReplyInsertDTO replyInsertDTO);

    // update 용도로 정의한 DTO를 넘겨서 update() 메서드 정의
    void update(ReplyUpdateDTO replyUpdateDTO);
}