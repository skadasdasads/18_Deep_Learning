package com.ohgiraffers.externalapi.section01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* Spring에서 외부 API요청 및 처리
*
* 대표적인 라이브러리
* - HttpClient
* - RestTemplate
* - WebClient
* - OpenFeign
*
* API 요청시 주의할 점
* request와 response를 외부서버가 원하는 요구대로 정확하게 보내주어야한다.
* */


@RestController
@RequestMapping("/translate")
@Slf4j
public class TranslationController {

    private final RestTemplateService restTemplateService;
    private final WebClientService webClientService;

    // 생성자 주입
    public TranslationController(RestTemplateService restTemplateService, WebClientService webClientService) {
        this.restTemplateService = restTemplateService;
        this.webClientService = webClientService;
    }

    @PostMapping("/resttemplate")
    public ResponseDTO translateByRestTemplate(@RequestBody RequestDTO requestDTO) {

        log.info("번역 Controller 요청 들어옴...");
        log.info("=== RestTemplate을 이용한 번역 서비스 요청 데이터 ===");
        log.info("text: {}, lang: {}", requestDTO.getText(), requestDTO.getLang());

        ResponseDTO result = restTemplateService.translateText(requestDTO);

        return result;
    }

    @PostMapping("/webclient")
    public ResponseDTO translateByWebClient(@RequestBody RequestDTO requestDTO) {

        log.info("번역 Controller 요청 들어옴...");
        log.info("=== WebClient를 이용한 번역 서비스 요청 데이터 ===");
        log.info("text: {}, lang: {}", requestDTO.getText(), requestDTO.getLang());

        ResponseDTO result = webClientService.translateText(requestDTO);

        return result;
    }
}