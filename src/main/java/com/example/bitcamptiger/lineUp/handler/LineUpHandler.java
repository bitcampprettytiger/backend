package com.example.bitcamptiger.lineUp.handler;

import com.example.bitcamptiger.lineUp.dto.RestaurantDTO;
import com.example.bitcamptiger.lineUp.dto.WaitingUserDTO;
import com.example.bitcamptiger.lineUp.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
public class LineUpHandler extends TextWebSocketHandler {
    private final RestaurantService restaurantService; // RestaurantService를 주입받음
    private final RestaurantDTO restaurantDTO; // 사용할 RestaurantDTO 인스턴스

    public LineUpHandler(RestaurantService restaurantService, RestaurantDTO restaurantDTO) {
        this.restaurantService = restaurantService;
        this.restaurantDTO = restaurantDTO;
    }

    // WebSocket 세션 저장을 위한 Set
    private Set<WebSocketSession> sessions = new HashSet<>();
    // 각 사용자의 대기 순번을 저장하기 위한 Map
    private Map<WebSocketSession, Long> userQueueNumbers = new HashMap<>();
    // 대기 인원 수
    public int waitingCount = 0;




    // afterConnectionEstablished ->
    // 대기 인원 수와 대기 순번 정보를 해당 세션에 전송하며, 대기 순번에 따른 알림도 전송
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 연결된 세션을 세션 목록에 추가
        sessions.add(session);
        // 대기 인원 수 전송
        sendWaitingCount(session);
        // 대기 순번 전송
        sendQueueNumber(session);
        // 대기 순번에 따른 알림 전송
        sendPositionNotification(session, userQueueNumbers.get(session));
        sendWaitingList(session); // 추가: 대기자 명단 초기 전송
    }

    // handleTextMessage ->
    // 사용자의 대기 순번을 확인하고, 대기 순번 정보가 없으면
    // 대기 인원 수 및 대기 순번을 갱신하고 관련 정보를 해당 세션에 전송
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws ParseException {
        // 해당 세션의 대기 순번 가져오기
        Long queueNumber = userQueueNumbers.get(session);
        System.out.println(message.getPayload());

        JSONParser parser = new JSONParser();
        Object obj = parser.parse( message.getPayload().toString() );

        JSONObject jsonObj = (JSONObject) obj;
        // 대기 순번이 없을 경우 새로운 대기자로 처리
        if (queueNumber == null) {
            // 대기 인원 수 증가
            waitingCount++;
            // 대기 순번 지정 및 세션에 저장
            queueNumber = (long) waitingCount;
            userQueueNumbers.put(session, queueNumber);
            // 모든 로그인한 사용자에게 대기 인원 수 전송
            sendWaitingCountToAllLoggedInUsers();
            // 대기 순번 전송
            sendQueueNumber(session);
            // 대기 순번에 따른 알림 전송
            sendPositionNotification(session, queueNumber);
            sendWaitingListToAllLoggedInUsers(); // 추가: 대기자 명단 변경 시 전송
        } else {
            if(jsonObj.get("type").equals("joinQueue")) {
                // 대기 인원 수 증가
                waitingCount++;
                // 대기 순번 지정 및 세션에 저장
                queueNumber = (long) waitingCount;
                userQueueNumbers.put(session, queueNumber);
                // 모든 로그인한 사용자에게 대기 인원 수 전송
                sendWaitingCountToAllLoggedInUsers();
                // 대기 순번 전송
                sendQueueNumber(session);
                // 대기 순번에 따른 알림 전송
                sendPositionNotification(session, queueNumber);
                sendWaitingListToAllLoggedInUsers(); // 추가: 대기자 명단 변경 시 전송
            } else {
                // 대기 인원 수 증가
                waitingCount--;
                // 대기 순번 지정 및 세션에 저장
                queueNumber = (long) waitingCount;
                userQueueNumbers.put(session, queueNumber);
                // 모든 로그인한 사용자에게 대기 인원 수 전송
                sendWaitingCountToAllLoggedInUsers();
                // 대기 순번 전송
                sendQueueNumber(session);
                // 대기 순번에 따른 알림 전송
                sendPositionNotification(session, queueNumber);
                sendWaitingListToAllLoggedInUsers(); // 추가: 대기자 명단 변경 시 전송
            }
        }
    }


    // afterConnectionClosed ->
    // 해당 사용자의 대기 순번 정보를 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 연결이 닫힌 세션을 세션 목록에서 제거
        sessions.remove(session);
        // 대기 순번 정보도 제거
        userQueueNumbers.remove(session);
    }

    // sendWaitingCount ->
    // 특정 세션으로 현재 대기 인원 수를 전송하는 메소드
    public void sendWaitingCount(WebSocketSession session) {
        try {
            Map<String, String> jsonMap = new HashMap<>();

            jsonMap.put("type", "waitingCount");
            jsonMap.put("waitingCount", String.valueOf(waitingCount));
            // 대기 인원 수를 해당 세션으로 전송
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(jsonMap);
            session.sendMessage(new TextMessage(jsonData));
        } catch (IOException e) {
            System.out.println("세션에 대기 인원 수 전송 중 오류 발생: " + session.getId());
            e.printStackTrace();
        }
    }

    // sendWaitingCountToAllLoggedInUsers ->
    // 모든 로그인한 사용자에게 현재 대기 인원 수를 전송하는 메소드
    public void sendWaitingCountToAllLoggedInUsers() {
        // 모든 로그인한 사용자에게 대기 인원 수 전송
        for (WebSocketSession s : sessions) {
            sendWaitingCount(s);
        }
    }

    // sendQueueNumber ->
    // 특정 세션으로 해당 사용자의 대기 순번을 전송하는 메소드
    public void sendQueueNumber(WebSocketSession session) {
        Long queueNumber = userQueueNumbers.get(session);
        if (queueNumber != null) {
            try {// JSON 데이터 생성
                Map<String, String> jsonMap = new HashMap<>();
                jsonMap.put("type", "queueNumber");
                jsonMap.put("queueNumber", String.valueOf(queueNumber));
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonData = objectMapper.writeValueAsString(jsonMap);

                // JSON 데이터를 해당 세션으로 전송
                session.sendMessage(new TextMessage(jsonData));
                // 사용자의 대기 순번을 해당 세션으로 전송
                session.sendMessage(new TextMessage("대기 순번: " + queueNumber));
            } catch (IOException e) {
                System.out.println("세션에 대기 순번 전송 중 오류 발생: " + session.getId());
                e.printStackTrace();
            }
        }
    }

    // sendPositionNotification ->
    // 대기 순번에 따라 해당 사용자와 일정 범위 내의 사용자에게 알림을 전송하는 메소드
    // 3번째 전일때 전송하게 해둠
    public void sendPositionNotification(WebSocketSession session, Long queueNumber) {
        for (WebSocketSession s : sessions) {
            Long sQueueNumber = userQueueNumbers.get(s);
            if (sQueueNumber != null && sQueueNumber > queueNumber && sQueueNumber <= queueNumber + 3) {
                try {
                    // JSON 데이터 생성
                    Map<String, String> jsonMap = new HashMap<>();
                    jsonMap.put("type", "positionNotification");
                    jsonMap.put("message", "준비하세요! 곧 차례가 됩니다.");
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonData = objectMapper.writeValueAsString(jsonMap);

                    // JSON 데이터를 해당 세션으로 전송
                    s.sendMessage(new TextMessage(jsonData));
                    // 알림 메시지 전송
                    s.sendMessage(new TextMessage("준비하세요! 곧 차례가 됩니다."));
                } catch (IOException e) {
                    System.out.println("세션에 알림 메시지 전송 중 오류 발생: " + s.getId());
                    e.printStackTrace();
                }
            }
        }
    }

    //대기자 명단 변경 시 전송
    public void sendWaitingListToAllLoggedInUsers() {
        for (WebSocketSession session : sessions) {
            sendWaitingList(session);
        }
    }
    // sendWaitingList ->
    // 대기자 명단을 JSON 형태로 변환하여 해당 세션에 전송하는 메소드
    public void sendWaitingList(WebSocketSession session) {
        // 대기자 명단 데이터를 가져오는 로직을 여기에 작성
        // RestaurantService를 이용하여 대기자 명단 데이터 가져오기
        List<WaitingUserDTO> waitingList = restaurantService.getWaitingListByRestaurant(restaurantDTO);

        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper를 사용하여 객체를 JSON으로 변환

        try {
            // 대기자 명단을 JSON 형태로 변환
            String waitingListJson = objectMapper.writeValueAsString(waitingList);

            // JSON 형태의 대기자 명단을 해당 세션으로 전송
            session.sendMessage(new TextMessage(waitingListJson));
        } catch (IOException e) {
            System.out.println("세션에 대기자 명단 전송 중 오류 발생: " + session.getId());
            e.printStackTrace();
        }
    }

    // 입장 완료 처리 후 대기 인원 수 및 대기 순번 정보를 클라이언트로 업데이트
    // ->  입장 완료 처리 후 대기 인원 수와 대기 순번 정보를 업데이트하고,
    // 해당 정보를 모든 로그인한 사용자에게 실시간으로 전송
    public void updateWaitingInfoAfterEntryComplete(RestaurantDTO restaurantDTO) {
        // 대기 인원 수 업데이트
        waitingCount = restaurantDTO.getWaitingUsers().size();
        // 모든 로그인한 사용자에게 대기 인원 수 전송
        sendWaitingCountToAllLoggedInUsers();
        // 모든 로그인한 사용자에게 대기 순번 전송
        sendQueueNumberToAllLoggedInUsers();
    }

    // sendQueueNumberToAllLoggedInUsers ->
    // 모든 로그인한 사용자에게 현재 대기 순번 정보를 전송하는 메소드
    public void sendQueueNumberToAllLoggedInUsers() {
        // 모든 로그인한 사용자에게 대기 순번 전송
        for (WebSocketSession s : sessions) {
            sendQueueNumber(s);
        }
    }


}