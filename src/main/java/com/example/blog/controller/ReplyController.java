package com.example.blog.controller;

import com.example.blog.dto.ReplyFindByIdDTO;
import com.example.blog.dto.ReplyInsertDTO;
import com.example.blog.entity.Reply;
import com.example.blog.exception.NotFoundReplyByReplyIdException;
import com.example.blog.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reply")
public class ReplyController {

    // 컨트롤러는 서비스를 호출합니다.

    ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    // 글 번호에 맞는 전체 댓글을 가져오는 메서드
    // 어떤 자원에 접근할것인지만 url에 명시(메서드가 행동을 결정함)
    // http://localhost:8080/reply/{번호}/all
    // http://localhost:8080/reply/300/all -> blogId 파라미터에 300이 전달된 것으로 간주
    @GetMapping("/{blogId}/all")
    // rest서버는 응답시 응답코드와 응답객체를 넘기기 대문에 ResponseEntity<자료형>
    // 을 리턴합니다.
    public ResponseEntity<List<ReplyFindByIdDTO>> findAllReplies(@PathVariable long blogId) {
        // 서비스에서 리품 목록을 들고옵니다.
        List<ReplyFindByIdDTO> replies = replyService.findAllByBlogId(blogId);

        return ResponseEntity
                .ok()// body를 생략하고 ok내부에 replies를 작성하여도 정상 동작 // 200코드
                .body(replies); // 리플목록
    }

    // replyId를 주소에 포함시켜서 요청하면 해당 번호 댓글 정보를 json으로 리턴하는메서드
    // 예시> /reply5 -> replyId 변수에 5가 대입되도록 주소 설정 및 메서드 선언
    @GetMapping("/{replyId}")
    public ResponseEntity<?> findByReplyId(@PathVariable long replyId) {
        // 서비스에서 특정 번호를 리플에 가져옵니다.
        ReplyFindByIdDTO replyFindByIdDTO = replyService.findByReplyId(replyId);

        //예외 처리는 추후에 만들 예정

        if (replyFindByIdDTO == null) {
            try {
                throw new NotFoundReplyByReplyIdException("없는 리플 번호를 조회했습니다.");
                //
            } catch (NotFoundReplyByReplyIdException e) {
                e.printStackTrace();
                return new ResponseEntity<>("찾는 댓글 번호가 없습니다.", HttpStatus.NOT_FOUND);
            }
        }
        return ResponseEntity.ok(replyFindByIdDTO);
    }

    // post 방식으로 /reply 주소로 요청이 들어왔을 때 실행되는 메서드 insertReply()


    @PostMapping("")
                                // Rest컨트롤러는 데이터를 json으로 주고받음.
                                // 따라서 @RequestBody 를 이용해 json으로 등러온 데이터를 역직렬화 하도록 설정
    public ResponseEntity<String> insertReply(@RequestBody ReplyInsertDTO replyInsertDTO){
            replyService.save(replyInsertDTO);
            return ResponseEntity
                    .ok("댓글 등록 잘 했네~");
    }
}
