package com.example.blog.repository;

import com.example.blog.dto.ReplyFindByIdDTO;
import com.example.blog.dto.ReplyInsertDTO;
import com.example.blog.dto.ReplyUpdateDTO;
import com.example.blog.entity.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired // 에스트 코드에서는 필드 주입을 써도 무방합니다.
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번글조회")
    public void findAllByBlogIdTest(){
        // given : 2번 글을 조회하기 위한 fixture 저장
        int blogId = 2;
        // when : findAllByBlogId() 호출 및 결과 자료 저장
        List<ReplyFindByIdDTO> idList = replyRepository.findAllByBlogId(blogId);
        // then : 2번글에 연동된 댓글이 4개일것이라고 단언
        assertEquals(4, idList.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번 자료의 댓글은 3번이고, 글쓴이는 고양이 ")
    public void findByReplyIdTest(){
        // given : replyId fixture 3저장
        long replyId = 3;
        // when
        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);
        // then
        assertEquals("바둑이", result.getReplyWriter());
        assertEquals(3, result.getReplyId());
    }

    @Test
    @Transactional
    @DisplayName("2번글에 연동된  댓글번호 2번을 삭제한 다음, 전체 데이터 개수가 3개로 그리고 2번으로 재 조회 시 null일것이다")
    public void deleteByReplyIdTest(){
        // given : fixture = 글번호 2반. 댓글번호 2번 생성
        long blogId = 2;
        long replyId = 2;
         // when : 댓글 삭제하기
        replyRepository.deleteByReplyId(replyId);

        // then : 2번 글에 연동된 댓글 개수는 3개일 것이고, 2번 댓글 재 좋회시 null
        assertEquals(3, replyRepository.findAllByBlogId(blogId).size());
        assertNull(replyRepository.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("픽스처를 이용해 INSERT후 ,전체 데이터를 가져와서 마지막 인데스 번호 요소를 얻어와서 입력했던 fixture와 비교")
    public void saveTest(){
        // given : 3번 글에 댓글 작성
        long blogId = 1;
        String writer = "이형승";
        String content = "새로운 댓글 작성 시도";
        ReplyInsertDTO reply = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(writer)
                .replyContent(content)
                .build();
        // when : inset 실행
        replyRepository.save(reply);
        List<ReplyFindByIdDTO> newReply = replyRepository.findAllByBlogId(blogId);
        // then : blogId번 글의 전체 댓글을 가지고 온 다음 마지막 인테스 요소만 변수에 저장한 다음
        // getter를 이용해 위에서 넣은 fixture와 일치하는지 체크
        assertEquals(1,newReply.size());
        assertEquals(writer, newReply.get(newReply.size() - 1).getReplyWriter());
    }

    @Test
    @Transactional
    @DisplayName("fixture로 수정할 댓글쓴이, 댓글내용, 3번 replyId를 지정합니다. 수정 후 3번자료를 DB에서 꺼내 fixture비교 및 published_at과 updated_at이 다른지 확인")
    public void updateTest(){
        long replyId = 3;
        String writer = "개똥이";
        String content = "나는 개똥벌레 친구가 없네";

        ReplyUpdateDTO updateReply = ReplyUpdateDTO.builder()
                .replyId(replyId)
                .replyWriter(writer)
                .replyContent(content)
                .build();

        replyRepository.update(updateReply);
        ReplyFindByIdDTO result =  replyRepository.findByReplyId(replyId);

//        assertNotEquals(result.getPublishedAt(), result.getUpdatedAt());
        // 업데이트 시간이 글을 작성한 시간보다 이후라고 단언함
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));
    }

    @Test
    @Transactional
    @DisplayName("blogId가 2인 글을 삭제하면, 삭제한 글의 전체 댓글 조회시 null일것이다.")
    public void deleteByBlogIdTest(){
        // given : fixture 작성
        long blogId = 2;
        // when : 삭제수행
        replyRepository.deleteByBlogId(blogId);

        List<ReplyFindByIdDTO> resultList = replyRepository.findAllByBlogId(blogId);

        // then
        assertEquals(0, resultList.size());
    }
}