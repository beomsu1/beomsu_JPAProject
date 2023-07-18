package org.bs.x2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;

@Service
@Log4j2
public class SocialServiceImpl implements SocialService {

    // 변수명 정의

    @Value("${org.bs.kakao.token_url}")
    private String tokenURL;

    @Value("${org.bs.kakao.rest_key}")
    private String clientId;

    @Value("${org.bs.kakao.redirect_uri}")
    private String redirectURI;

    @Value("${org.bs.kakao.get_user}")
    private String getUser;


    // 카카오 이메일 가져오기
    @Override
    public String getKakaoEmail(String authCode) {

        log.info("--------------");
        log.info(authCode);
        log.info(tokenURL);
        log.info(clientId);
        log.info(redirectURI);
        log.info(getUser);

        // authcode -> accessToken 받기
        String accessToken = getAccessToken(authCode);

        // accessToken -> email 받기
        String email = getEmailFromAccessToken(accessToken);

        return email;

    }

    // 엑세스토큰 가져오기
    private String getAccessToken(String authCode) {

        String accessToken = null;

        // restTemplate로 호출
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        // 헤더이름, 값
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 특정한 URI 호출
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenURL)
                // 값을 하나씩 등록 이름 , 값
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectURI)
                .queryParam("code", authCode)
                .build(true);

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriComponents.toString(), HttpMethod.POST, entity, LinkedHashMap.class);

        log.info("-----------------------------------");
        log.info(response);

        LinkedHashMap<String, String> bodyMap = response.getBody();

        // accessToken 구하기 - bodyMap에서 뺴오기
        accessToken = bodyMap.get("access_token");

        log.info("Access Token: " + accessToken);

        return accessToken;

    }

    // 엑세스토큰으로 이메일 가져오기
    private String getEmailFromAccessToken(String accessToken){

        if(accessToken == null){
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type","application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(getUser).build();

        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriBuilder.toString(),
                        HttpMethod.GET,
                        entity,
                        LinkedHashMap.class);

        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("------------------------------------");
        log.info(bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        return kakaoAccount.get("email");

    }

}