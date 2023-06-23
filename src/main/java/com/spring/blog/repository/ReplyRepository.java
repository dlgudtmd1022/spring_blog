package com.spring.blog.repository;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    // 댓글번호 입력시 특정 댓글 하나만 가져오는 메서드 findMyReplyId()를 선언해주세요.
    ReplyResponseDTO findByReplyId(long replyId);

    // 삭제는 replyId를 요청합니다.
    void deleteByReplyId(long replyId);

    // 삽입 구문은 ReplyInsertDTO을 이용해서 , save메서드에 저장
    // ReplyInsertDTO에 내장된 맴버변수인 blogId(맻번글애), replyWriter(누가), replyContent(뭔내용)
    // 내용들을 전달해서 INSERT구문을 완성시키기 위함
    void save (ReplyCreateRequestDTO replyInsertDTO);

    // 수정로직은 ReplyUpdateDTO를 이용해서 update 메서드를 호출해 처리합니다.
    // 수정로직은 replyId를 WHERE절에 집어넣고, replyWriter, replyContent의 내용을 업데이트 해주고
    // update 역시 now()로 바꿔줍니다.
    void update(ReplyUpdateRequestDTO replyUpdateDTO);

    // blogId 를 받아서 특정 글과 얀계된 댓글 전체를 삭제하는 메서드를 정의만 해주세요
    void deleteByBlogId(long blogId);


}