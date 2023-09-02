package com.example.bitcamptiger.lineUp.service;

import com.example.bitcamptiger.lineUp.dto.RestaurantDTO;
import com.example.bitcamptiger.lineUp.dto.WaitingUserDTO;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.*;

//대기자 명단을 관리하고, 입장 처리와 대기자 추가 및 취소를 처리하기 위한 서비스 역할
@Service
@RequiredArgsConstructor
@ServerEndpoint("/socket/chatt")
public class RestaurantService {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
    private static Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    // 특정 음식점의 대기자 명단 조회
    // -> 사장님이나 음식점 관리자 등이 이 메서드를 호출하여 특정 음식점의 대기자 명단을 확인

    @OnOpen
    public void OnOpen(Session session){

        if(!clients.contains(session)){
            clients.add(session);
        }else {

        }
    }
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("receive message : {}", message);

        for (Session s : clients) {
            logger.info("send data : {}", message);

            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("session close : {}", session);
        clients.remove(session);
    }


















    public List<WaitingUserDTO> getWaitingListByRestaurant(RestaurantDTO restaurantDTO) {
        return restaurantDTO.getWaitingUsers();
    }

    // 입장 처리
    // -> 음식점 관리자나 직원이 수동으로 호출해야 하는 것
    private void processEntry(RestaurantDTO restaurantDTO) {
        List<WaitingUserDTO> waitingList = restaurantDTO.getWaitingUsers();

        if (!waitingList.isEmpty()) {
            waitingList.remove(0); // 첫 번째 대기자 제거

            // 번호 앞당기기
            for (int i = 0; i < waitingList.size(); i++) {
                WaitingUserDTO waitingUser = waitingList.get(i);
                waitingUser.setQueueNumber(i + 1);
            }
        }
    }
    // 입장 완료 처리
    public void completeEntry(RestaurantDTO restaurantDTO) {
        processEntry(restaurantDTO);
    }

    // 대기자 추가
    public void addWaitingUser(RestaurantDTO restaurantDTO, WaitingUserDTO waitingUserDTO) {
        // 대기자 추가 후 입장 처리 로직 호출
        restaurantDTO.addWaitingUser(waitingUserDTO);
        processEntry(restaurantDTO);
    }

    // 대기자 취소
    public void cancelWaitingUser(RestaurantDTO restaurantDTO, String userId) {
        List<WaitingUserDTO> waitingList = restaurantDTO.getWaitingUsers();

        // 대기자 목록에서 userId에 해당하는 대기자 찾기
        Optional<WaitingUserDTO> cancelUserOptional = waitingList.stream()
                .filter(waitingUser -> waitingUser.getUserId().equals(userId))
                .findFirst();

        if (cancelUserOptional.isPresent()) {
            WaitingUserDTO cancelUser = cancelUserOptional.get();
            waitingList.remove(cancelUser); // 대기자 제거

            // 번호 다시 정렬
            for (int i = 0; i < waitingList.size(); i++) {
                WaitingUserDTO waitingUser = waitingList.get(i);
                waitingUser.setQueueNumber(i + 1);
            }

            // 대기자 취소 후에도 입장 처리
            processEntry(restaurantDTO);
        }
    }

}