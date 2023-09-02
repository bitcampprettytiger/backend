package com.example.bitcamptiger.lineUp.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class WebController {

    @GetMapping("/web-socket")
    public String webSocketPage() {
        return "WebSocketTest"; // .html 확장자를 제외한 템플릿 파일명
    }

    @GetMapping("/web-socket2")
    public String webSocketPage2() {
        return "hi"; // .html 확장자를 제외한 템플릿 파일명
    }
}
