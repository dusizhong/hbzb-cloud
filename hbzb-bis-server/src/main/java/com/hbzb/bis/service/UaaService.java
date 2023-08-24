package com.hbzb.bis.service;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.bis.exception.RestTemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * 自定义UAA服务
 * @author dusizhong
 * @since 2020-07-09
 */
@Service
public class UaaService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${uaa.server}")
    String UAA_SERVER;

    public JSONObject requestUserDetail() {

        // get access token
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(details);
        String access_token = jsonObject.getString("tokenValue");
        // prepare headers
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.set("Authorization", "bearer " + access_token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // set timeout
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(180000);
        requestFactory.setReadTimeout(180000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RestTemplateException());
        // send request
        ResponseEntity<JSONObject> response = restTemplate.exchange(UAA_SERVER + "/user/detail", HttpMethod.GET, httpEntity, JSONObject.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            logger.error(response.toString());
            return null;
        }
    }

    public JSONObject requestCorpDetail() {

        // get access token
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(details);
        String access_token = jsonObject.getString("tokenValue");
        // prepare headers
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.set("Authorization", "bearer " + access_token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // set timeout
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(180000);
        requestFactory.setReadTimeout(180000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RestTemplateException());
        // send request
        ResponseEntity<JSONObject> response = restTemplate.exchange(UAA_SERVER + "/corp/detail", HttpMethod.GET, httpEntity, JSONObject.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            logger.error(response.toString());
            return null;
        }
    }
}
