package com.hbzb.bis.controller;

import com.hbzb.bis.model.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApi {

    @Autowired
    SimpMessagingTemplate template;

    @RequestMapping("/")
    public String greetings() {
        return "Greetings from hbzb bid system server!";
    }

    @RequestMapping("/send")
    public Resp sendMessage(@RequestParam String msg) {
        template.convertAndSend("/message", msg);
        return new Resp("200","success");
    }
}
