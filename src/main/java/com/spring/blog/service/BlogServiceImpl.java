package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 빈 컨테이너에 적재
public class BlogServiceImpl implements BlogService {

    BlogRepository blogRepository;

    ReplyRepository replyRepository;

    BlogJPARepository blogJPARepository;

    ReplyJPARepository replyJPARepository;

    @Autowired // 생성자 주입이 속도가 더 빠름.
    public BlogServiceImpl(BlogRepository blogRepository, ReplyRepository replyRepository, BlogJPARepository blogJPARepository, ReplyJPARepository replyJPARepository){
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
        this.blogJPARepository = blogJPARepository;
        this.replyJPARepository = replyJPARepository;
    }
    @Override
    public Page<Blog> findAll(Integer pageNum){
//        List<Blog> blogList = blogRepository.findAll();
//        return blogList;
//        return blogRepository.findAll(); < Mybatis를 활용한 전체 글 자져오기
//        return  blogJPARepository.findAll(); // < jpa를 활용한 전체 글 가져오기
        int calibratedPageNum = getCalibratedPageNum(pageNum);

        // 페이징 처리에 과련된 정보를 먼저 객체로 생성합니다.
        Pageable pageable = PageRequest.of(calibratedPageNum - 1,10);
        // 생성된 페이징 정보를 파나미터로 제공해서 findAll()을 호출합니다.
        return  blogJPARepository.findAll(pageable);
    }

    @Override
    public Blog findById(long blogId) {
//        return blogRepository.findById(blogId);
        // JPA의 findbyId는 Optinal를 리턴하므로, 일반 객체로 만들기 위해 뒤에 .get()을 사용합니다.
        // Optinal은 참조형 변수에 대해서 null검사 및 처리를 쉽게 할 수 있도록 제공하는 제네릭입니다.
        // JPA는 Optinal을 쓰는것을 권장하기 위해 리턴 자료형으로 강제해뒀습니다.
        return  blogJPARepository.findById(blogId).get();
    }

    @Transactional
    @Override
    public void deleteById(long blogId) {
//        replyRepository.deleteByBlogId(blogId);
//        blogRepository.deleteById(blogId);

        // 댓글 삭제가 진행되도록 만들어라
        replyJPARepository.deleteAllByBlogId(blogId);
        blogJPARepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {

//        blogRepository.save(blog);
        blogJPARepository.save(blog);
    }
    @Override
    public void update(Blog blog){
        // JPA의 수정은, findByID()를 이용해 얻어온 엔터리 클래스 객체의 내부 내용물을 수정한 다음
        // 해당 요소를 save() 해서 이뤄집니다.

        Blog updateBlog = blogJPARepository.findById(blog.getBlogId()).get();

//        blog.setBlogId(updateBlog.getBlogId());

//        updateBlog.setBlogContent(updateBlog.getBlogContent());
//        updateBlog.setBlogTitle(updateBlog.getBlogTitle());

        updateBlog.setBlogContent(blog.getBlogContent());
        updateBlog.setBlogTitle(blog.getBlogTitle());

        blogJPARepository.save(updateBlog);
    }

    // 보정된 pageNum으로 가공해주는 메서드
    public int getCalibratedPageNum(Integer pageNum){
        if(pageNum == null || pageNum <= 0){
            pageNum = 1;
        }else {
            // 총 페이지 개수를 구하는 로직
            int totalPagesCount = (int) (Math.ceil(blogJPARepository.count() / 10.0));

            pageNum = pageNum > totalPagesCount ? totalPagesCount : pageNum;
        }
        return pageNum.intValue();
    }
}
