package com.example.bitcamptiger.webSoket.handler;

import com.example.bitcamptiger.webSoket.dto.AlarmDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

public class AlarmHandler extends TextWebSocketHandler {

    @Autowired
    private AlarmDAO alarmDAO;

    private static List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (WebSocketSession webSocketSession : sessions) {
            String memberId = message.getPayload();
            int count = alarmDAO.selectAlarmUncheckCount(memberId);  // DB 연동 필요

            if (webSocketSession.getId().equals(session.getId()) && count != 0) {
                TextMessage msg = new TextMessage(memberId + "님 새 알림이 있습니다.");
                webSocketSession.sendMessage(msg);
            }
        }
    }


    //연결이 닫힌 세션을 세션 목록에서 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }


}
