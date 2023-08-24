package com.hbzb.tas.util;

import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HttpUtils工具
 */
public class HttpUtils {

    public static Object get(String url, OAuth2Authentication authentication, Map<String, String> params) {

        if(url.isEmpty()) return "url参数不能为空";
        if(authentication == null) return "authentication参数不能为空";
        if(params.isEmpty()) return "params参数不能为空";
        // get access token
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) properties.get("details");
        String access_token = details.get("tokenValue").toString();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.set("Authorization", "bearer " + access_token);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // set timeout
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(180000);//设置超时
        requestFactory.setReadTimeout(180000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // map to url params
        if(params.size() > 0) url = url + "?";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : params.entrySet()) {
            if (sb.length() > 0) { sb.append("&"); }
            sb.append(String.format("%s=%s", entry.getKey().toString(), entry.getValue().toString()));
        }
        url = url + sb;
        System.out.println(url);
        // send request
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        int code = response.getStatusCode().value();
        if(200 == code) {
            return response.getBody();
        } else if(400 == code) {
            return "参数有误";
        } else if(401 == code) {
            return "authentication无效";
        } else return "获取数据失败";
    }

    public static Object post(String url, OAuth2Authentication authentication, Map<String, String> params) {

        if(url.isEmpty()) return "url参数不能为空";
        if(authentication == null) return "authentication参数不能为空";
        if(params.isEmpty()) return "params参数不能为空";
        // get access token
        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();
        LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) properties.get("details");
        String access_token = details.get("tokenValue").toString();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(mediaType);
        headers.set("Authorization", "bearer " + access_token);
        // map to MultiValueMap
        MultiValueMap<String, String> myParams = new LinkedMultiValueMap<>();
        for (Map.Entry<?,?> entry : params.entrySet()) {
            myParams.add(entry.getKey().toString(), entry.getValue().toString());
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(myParams, headers);
        // set timeout
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(180000);//设置超时
        requestFactory.setReadTimeout(180000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // send request
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
        int code = response.getStatusCode().value();
        if(200 == code) {
            return response.getBody();
        } else if(400 == code) {
            return "参数有误";
        } else if(401 == code) {
            return "authentication无效";
        } else return "获取数据失败";
    }
}
