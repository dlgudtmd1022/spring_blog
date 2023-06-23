package com.spring.blog.controller;

import com.spring.blog.dto.BmiDTO;
import com.spring.blog.dto.PersonDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller // 컨트롤러로 지정
//@ResponseBody // REST형식 전환, 메서드 위에 붙으면 해당 메서드만 REST형식으로
@RestController // 위 2개 어노테이션을 한 번에 지정해줌
@RequestMapping("/resttest")
@CrossOrigin(origins = "http://127.0.0.1:5500") // 해당 출처의 비동기 요청 허용
public class RESTApiController {
    // REST 컨트롤러는 크게 json을 리턴하거나, String을 리턴하게 만들 수 있습니다.

    @GetMapping("/hello")
    public String hello(){
        return "안녕하세요";
    }

    @GetMapping("/foods")
    public List<String> foods(){
        List<String> foodlList = List.of("탕수육", "똠양꿍", "돈카츠");
        return foodlList;
    }
    @GetMapping("/person")
    public PersonDTO person(){
        PersonDTO p = PersonDTO.builder()
                .id(1L)
                .name("좋코더")
                .age(20)
                .build();
        return p;
    }

    @GetMapping("/person-list")
    public ResponseEntity<?> personList(){
        PersonDTO p = PersonDTO.builder().id(1L).name("첫째").age(11).build();
        PersonDTO p2 = PersonDTO.builder().id(2L).name("둘째").age(12).build();
        PersonDTO p3 = PersonDTO.builder().id(3L).name("셋째").age(13).build();
        List<PersonDTO> personList = List.of(p,p2,p3);

        //.ok()는 200코드를 반환하고, 뒤에 연달아 붙은 body()에 실제 리턴자료를 입력합니다.
        return ResponseEntity.ok().body(personList);
    }

    @GetMapping("/bmi")
    public ResponseEntity<?> bmi (BmiDTO bmi){ // 커맨드 객체 형식으로 사용됨

        // 예외처리 들어갈 자리
        if(bmi.getHeight() == 0){
            return ResponseEntity
                    .badRequest() // 400
                    .body("키가 0임? 다시");
        }
        double result = bmi.getWeight() / ((bmi.getHeight()/100) * (bmi.getHeight()/100));

        // 헤더 추가해보기
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits","melon");
        headers.add("lunch","pizza");

        return ResponseEntity
                .ok() // 200
                .headers(headers) // 헤더 추가
                .body(result); // 사용자에게 보여질 데이터
    }

    // Postman을 활용한 json 데이터 파라미터로 전송해 요청넣기
    @GetMapping("/bmi2")
    public ResponseEntity<?> bmi2(@RequestBody BmiDTO bmi){ // 파라미터로 받지 않고 json등으로 받겠따.

        // 예외처리 들어갈 자리
        if(bmi.getHeight() == 0){
            return ResponseEntity
                    .badRequest() // 400
                    .body("키가 0임? 다시");
        }
        double result = bmi.getWeight() / ((bmi.getHeight()/100) * (bmi.getHeight()/100));

        // 헤더 추가해보기
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits","melon");
        headers.add("lunch","pizza");

        return ResponseEntity
                .ok() // 200
                .headers(headers) // 헤더 추가
                .body(result); // 사용자에게 보여질 데이터
    }
}
