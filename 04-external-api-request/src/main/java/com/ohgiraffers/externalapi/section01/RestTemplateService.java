package com.ohgiraffers.externalapi.section01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/*
* RestTemplate
*
* Spirng에서 지원하는 객체로 간편하게 Rest 방식 API를 호출 할 수 있는 Spring 내장 클래스
* REST API 서비스를 요청 후 응답 받을 수 있도록 설계됨
*
* 특징
* 간단하고 직관적인 사용법
* 동기식 처리로 이해하기 쉬움
* Spring 5.0 이전부터 사용된 검증된 방식
* */
@Service
@Slf4j
public class RestTemplateService {

    private final RestTemplate restTemplate;

    private final String FAST_API_SERVER_URL = "http://localhost:8000/translate";

    // RestTemplate을 생성자 주입으로 변경
    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public RestTemplateService() {
//        this.restTemplate = new RestTemplate();
//    }

    public ResponseDTO translateText(RequestDTO requestDTO) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RequestDTO> entity = new HttpEntity<>(requestDTO, headers);

            ResponseEntity<ResponseDTO> response = restTemplate.exchange(
                    FAST_API_SERVER_URL, // 요청 url
                    HttpMethod.POST,     // http 요청 메서드
                    entity,              // 요청 entity (헤더 + 본문)
                    ResponseDTO.class    // 응답 본문을 변환할 타입 (JSON -> ResponseDTO)
            );

            log.info("=== 번역 서비스 응답 데이터 ===");
            log.info("번역 결과: {}", response.getBody().getResult());

            return response.getBody();
        } catch (RestClientException e) {
            log.error("번역 api 호출중 오류 발생");
            throw new RuntimeException(e);
        }
    }
}