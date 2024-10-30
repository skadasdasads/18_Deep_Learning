package com.ohgiraffers.externalapi.section01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
/*
* WebClient
*
* Spring WebFlux에 포함되어있는 HTTP 요청 라이브러리
* Reactor(반응형) 기반의 API를 가지고 있다.
* 기본적으로 비동기 논블로킹이지만 동기방식도 지원한다.
*
* 리엑티브 프로그래밍
* -> 스트림을 사용하는 비동기 프로그래밍 패러다임
* ( 예를들면 기존에 는 커피를 주문하면 계속 대기를 했다면, 커피를 주문하고 다되면 알람을 받는식으로 구현됨)
* */
@Service
@Slf4j
public class WebClientService {

    private final WebClient webClient;

    private final String FAST_API_SERVER_URL = "http://localhost:8000";  // application.yml의 값을 직접 주입


    public WebClientService(WebClient.Builder webClientBuilder) {
        log.info("Translation API URL: {}", FAST_API_SERVER_URL);  // URL 확인용 로그

        this.webClient = webClientBuilder
                .baseUrl(FAST_API_SERVER_URL)
                .build();
    }


    public ResponseDTO translateText(RequestDTO requestDTO) {
        log.info("번역 요청 시작 - text: {}, lang: {}", requestDTO.getText(), requestDTO.getLang());

        return webClient.post()
                .uri("/translate")              // base url + uri에 해당하는 곳으로 요청을 보낸다.
                .bodyValue(requestDTO)              // body에 담을 값
                .retrieve()                         // 요청 보내기
                .bodyToMono(ResponseDTO.class)      // 응답 본문을 지정된 타입으로 변환 (JSON -> ResponseDTO) 역직렬화
                .doOnSuccess(response -> log.info("번역 완료 - result: {}", response.getResult()))
                // 성공시 수행할 적업 (대부분 로깅 후 처리에 사용한다.)
                .doOnError(error -> log.error("번역 API 호출 중 오류 발생", error))
                // 오류 발생시 처리할 작업 (로깅 및 예외처리에 사용한다.)
                .block();  // 비동기 작업을 동기식으로 변환, 결과가 반환될 때까지 대기
    }
}
