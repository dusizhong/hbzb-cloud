package com.hbzb.cloud.tds.controller;


import com.hbzb.cloud.tds.util.HttpUtils;
import io.swagger.annotations.Api;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Api(tags = "2. 企业接口", value = "企业接口服务")
@RestController
public class IndexController {

    @GetMapping("/")
    public String greetings() {
        return "Greetings from tds server!";
    }

    @GetMapping("/guarantee")
    public String getGuarantee(Principal principal) {
        System.out.println(principal.getName());
        return "authorized guarantee detail!";
    }

    @PostMapping("/corp/create")
    public Object createCorp(OAuth2Authentication authentication,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String uniformCode) {

        String url = "http://192.168.33.200:9999/uaa/corp/create";
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("uniformCode", uniformCode);
        Object result = HttpUtils.post(url, authentication, params);
        return result;
    }

    @GetMapping("/corp/list")
    public Object listCorp(OAuth2Authentication authentication,
                           @RequestParam(defaultValue = "") String type) {

        String url = "http://192.168.33.200:9999/uaa/corp/list";
        Map<String, String> params = new HashMap<>();
//        params.put("type", type);
//        params.put("ff", "会话");
//        params.put("kk", "看看");
        Object response = HttpUtils.get(url, authentication, params);
        return response;
    }



    @GetMapping("/user_info")
    public String getUserInfo(OAuth2Authentication authentication) {

        if(authentication == null) return "null";

        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) authentication.getUserAuthentication().getDetails();

        System.out.println(properties.get("details"));

        LinkedHashMap<String, Object> dt = (LinkedHashMap<String, Object>) properties.get("details");


        System.out.println(dt.get("tokenValue"));
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)properties.get("details");
//        System.out.println(details.getTokenValue());

        String access_token = dt.get("tokenValue").toString();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
        headers.set("Authorization", "bearer " + access_token);
        Map<String,Object> params = new HashMap<>();

        HttpEntity<String> formEntity = new HttpEntity<String>(params.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("http://192.168.33.200:9999/uaa/user/detail", formEntity, String.class);

        return result;
    }
}
