package com.example.blog.service;

import com.example.blog.dto.ReplyFindByIdDTO;
import com.example.blog.dto.ReplyInsertDTO;
import com.example.blog.dto.ReplyUpdateDTO;
import com.example.blog.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    // 서비스 객체 세팅
    @Autowired
    ReplyService replyService;
    // findAllByBlogIdTest()

    @Test
    @Transactional
    @DisplayName("2번 글과 연동된 댓글 전체를 가져오고 개수가 4개일 것이라고 단언")
    public void findAllByBlogIdTest(){
        // given : fixture 저장
        long blogId = 2;
        // when : 2번글의 댓글 전부 가져오기
        List<ReplyFindByIdDTO> list = replyService.findAllByBlogId(blogId);
        // then : 개수는 4개일 것이다.
        assertEquals(4,list.size());
    }

    @Test
    @Transactional
    @DisplayName("2번 글의 2번 댓글의 글쓴이가 강아지이다")
    public void findByReplyIdTest(){
        long blogId = 2;
        int replyId = 2;

        List<ReplyFindByIdDTO> list = replyService.findAllByBlogId(blogId);

        assertEquals("강아지", list.get(replyId - 1).getReplyWriter());
    }

    @Test
    @Transactional
    @DisplayName("2번글의 1번 댓글 삭제")
    public void deleteByReplyIdTest(){
        long blogId = 2;
        int replyId = 1;

        replyService.deleteByReplyId(replyId);

        List<ReplyFindByIdDTO> list = replyService.findAllByBlogId(blogId);

        assertEquals(3, list.size());
    }

    @Test
    @Transactional
    @DisplayName("새로운 댓글 저장")
    public void saveTest(){
        long blogId = 3;
        String writer = "이형승";
        String content = "새로운 댓글 작성";

        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(writer)
                .replyContent(content)
                .build();

        replyService.save(replyInsertDTO);

       List<ReplyFindByIdDTO> resultList = replyService.findAllByBlogId(blogId);

        assertEquals(2,resultList.size());
        assertEquals(writer,resultList.get(resultList.size() - 1).getReplyWriter());
    }

    @Test
    @Transactional
    @DisplayName("2번 댓글 업데이트")
    public void updateTest(){
        long replyId = 2;
        String writer = "개똥벌레";
        String content = "나는 개똥벌레~ 친구가 없네";

        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
                .replyId(replyId)
                .replyWriter(writer)
                .replyContent(content)
                .build();

        replyService.update(replyUpdateDTO);
        ReplyFindByIdDTO result = replyService.findByReplyId(replyId);

        assertNotEquals(result.getPublishedAt(), result.getUpdatedAt());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }
}