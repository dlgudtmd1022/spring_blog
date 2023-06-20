package com.example.blog.service;

import com.example.blog.dto.ReplyFindByIdDTO;
import com.example.blog.dto.ReplyInsertDTO;
import com.example.blog.dto.ReplyUpdateDTO;
import com.example.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }
    @Override
    public List<ReplyFindByIdDTO> findAllByBlogId(long blogId) {
        return replyRepository.findAllByBlogId(blogId);
    }

    @Override
    public ReplyFindByIdDTO findByReplyId(long replyId) {
        return replyRepository.findByReplyId(replyId);
    }

    @Override
    public void deleteByReplyId(long replyId) {
        replyRepository.deleteByReplyId(replyId);
    }

    @Override
    public void save(ReplyInsertDTO replyInsertDTO) {
        replyRepository.save(replyInsertDTO);
    }

    public void update(ReplyUpdateDTO replyUpdateDTO){
        replyRepository.update(replyUpdateDTO);
    }
}