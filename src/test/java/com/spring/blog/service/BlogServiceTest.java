package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BlogServiceTest {

    @Autowired
    BlogService blogService;

    @Test
    @Transactional // 이 테스트의 겨로가가 디비 커밋을 하지 않음
    @DisplayName("전체 데이터 조회")
    public void findAllTest(){
        // given 없음

        // when : 전체데이터 가져오기
        List<Blog> blogList = blogService.findAll();
        // then : 길이가 3일것이다.
//        assertThat(blogList.size()).isEqualTo(3); // import assertj...
        assertEquals(3, blogList.size());
    }

    // findById를 직접 테스트하는 코드를 작성해주세요.
    @Test
    @Transactional
    @DisplayName("ID에 맞는 데이터 조회")
    public void findByIdTest(){
        // given
            long blogId = 2;
            String writer = "2번유저";
            String blogTitle = "2번제목";
        // when
            Blog blog = blogService.findById(blogId);
        // then
            assertEquals(blogId, blog.getBlogId());
            assertEquals(writer, blog.getWriter());
            assertEquals(blogTitle, blog.getBlogTitle());
    }

    @Test
    @Transactional
//    @Commit // 트랜잰셔 적용된 테스트의 결과를 커밋해서 디비에 반영하도록 만듦
    @DisplayName("ID에 맞는 데이터 삭제")
    public void deleteByIdTest(){
        // given
        long blogId = 2;
        // when
        blogService.deleteById(blogId);
        // then
        assertEquals(2, blogService.findAll().size());
        assertNull(blogService.findById(blogId));
    }

    @Test
    @Transactional
    @DisplayName("입력한 데이터를 저장")
    public void saveTest(){
        String writer = "유저";
        String blogTitle = "제목";
        String blogContent = "본문";
        int lastBlogIndex = 3;
        // given : Blog 객체에 필요데이터인 writer, blogTitle, blogContent 를 주입해 builder 패턴으로 생성
        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
        // when : .save()를 호출해서 DB에 저장합니다.
        blogService.save(blog);
        // then : 전체 요소의 개수가 4개인지 확인하고, 현재 얻어온 마지막 포스팅의 writer, blogTitle, blogContent 가
        // 생성시 사용한 자료와 일치하는지 확인
        assertEquals(4, blogService.findAll().size());
        assertEquals(writer, blogService.findAll().get(lastBlogIndex).getWriter());
        assertEquals(blogTitle, blogService.findAll().get(lastBlogIndex).getBlogTitle());
        assertEquals(blogContent, blogService.findAll().get(lastBlogIndex).getBlogContent());
    }

    @Test
    @Transactional
    @DisplayName("입력한 새로운 데이터로 업데이트")
    public void updateTest(){
                String blogTitle = "업데이트제목";
        String blogContent = "업데이트본문";
        long blogId = 2;
        // given
        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
        // when
        blogService.update(blog);
        // then
        assertEquals(blogTitle, blogService.findById(blogId).getBlogTitle());
    }

    // blog와 함께 reply가 삭제되는 케이스는 따로 다시 테스트코드를 하나 더 작성해주는게 좋습니다.
}