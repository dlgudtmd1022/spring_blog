package com.example.blog.controller;

import com.example.blog.dto.ReplyFindByIdDTO;
import com.example.blog.dto.ReplyInsertDTO;
import com.example.blog.repository.ReplyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MVC테스트는 브라우저를 켜야 원래 테스트가 가능하므로 브라우저를 대체할 객체를 만들어 수행
class ReplyControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    // 임시적으로 Replyrepository를 생성
    // 레퍼지토리 레이어의 메서드는 쿼리문을 하나를 호출하는것이 보장되지만
    // 서비스 레이어의 메서드는 추후에 쿼리문을 두 개 이상 호출할수도 있고 그런 변경이 생겼을때 테스트코드도 같이 수행할 가능성이 생김
    @Autowired
    private ReplyRepository replyRepository;

    // 컨트롤러를 테스트 해야하는데 컨트롤러는 서버에 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지는 않습니다.
    // 각 테스트전에 설정하기
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @DisplayName("2번 글에 대한 전체 댓글을 조회했을때 0번째 요소의 replyWriter는 댓글쓴사람, reply는 1")
    void findAllReplies() throws Exception {
        // given : fixture 설정, 접속 주소 설정
        String repplyWriter = "댓글쓴사람";
        long replyId = 1;
        String url = "/reply/2/all";

        // when : 위에 설정한 URL로 접속 후 json 데이터 리턴받아 저장하기, ResultActions 형 자료로 json저장하기
        // get() 메서드의 경우 작성훈 alt + enter 눌러서 mockmvc 관련 요소로 import

                                    // fetch(rel).then(res => res.json()); 에 대응하는 코드
        final ResultActions result = mockMvc.perform(get(url) // url 주소로 get요청 넣기
                .accept(MediaType.APPLICATION_JSON)); // 리턴자료가 json임을 명시
        // then : 리턴받은 json 목록의 0번째 요소의 rplyWriter와 replyId가 예상과 일칯하는지확인
        result
                .andExpect(status().isOk()) // 200 코드가 출력되었는지 확인
                .andExpect(jsonPath("$[0].replyWriter").value(repplyWriter)) // 첫 json의 replyWriter 검사
                .andExpect(jsonPath("$[0].replyId").value(replyId)); // 첫 json의 replyId 검사
    }

    // replyId를 주소에 포함시켜서 요청하면 해당 번호 댓글 정보를 json으로 리턴하는메서드
    // 예시> /reply5 -> replyId 변수에 5가 대입되도록 주소 설정 및 메서드 선언
    @Test
    @Transactional
    @DisplayName("replyId 3번 조회시, replyWriter는 고양이, replyId는 2반")
    public void findReplyIdTest() throws Exception {
        // given : fixture 세팅 및 요청 주소 세팅
        String replywriter = "고양이";
        long replyId = 3;
        String url = "/reply/3";

        // when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replywriter)) // 단일 json객체는 $생략 가능
                .andExpect(jsonPath("$.replyId").value(replyId));
    }

    @Test
    @Transactional
    @DisplayName("1번 블로그에 개똥벌레라는 이름으로 나는 개똥벌레~ 친구가 없네~ 댓글 추가")
    public void insertReplyTest() throws Exception{
        // given
        String replyWriter = "개똥벌레";
        String replyContent = "나는 개똥벌레~ 친구가 없네~";
        long blogId = 1;
        String url = "/reply";
        String url2 = "/reply/1/all";


        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // 데이터 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyInsertDTO);


        // when : 직렬화된 데이터를 이용해 post방식으로 ur에 요청
        mockMvc.perform(post(url) // /reply 주소에 post방식으로 요청을 넣고
                .contentType(MediaType.APPLICATION_JSON) // 전달 자료는 json이며
                .content(requestBody)); // 위에서 직렬화만 requestBody 변수를 전달할것이다.

       // then : 위에서 blogId로 지정한 1번글의 전체 데이터를 가져와서, 픽스처와 replyWriter, replyContent가 일치하는지 확인
        final ResultActions result = mockMvc.perform(get(url2)
                                             .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyContent").value(replyContent));
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번을 삭제시, 글번호 2번의 댓글수는 3개, 그리고 단일댓글 조회시 null")
    public void deleteByReplyTest() throws Exception{
        // given
        long replyId = 3;
        long blogId = 2;
        String url = "http://localhost:8080/reply/3";

        // when : 삭제 수행
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));
        // .accept는 리턴 데이터가 있는 경우에 해당 데이터를 어떤 형식으로 받아올지 기술

        // then : repository를 이용해 전체 데이터를 가져온 후, 개수 비교 및 삭제한 3번댓글은 null이 리턴되는지 확인
        List<ReplyFindByIdDTO> resultList = replyRepository.findAllByBlogId(blogId);
//        assertEquals("바둑이", replyRepository.findByReplyId(replyId).getReplyWriter());
        assertEquals(3, resultList.size());
        ReplyFindByIdDTO result = replyRepository.findByReplyId(replyId);
        assertNull(result);
    }
}