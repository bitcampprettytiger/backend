package com.example.bitcamptiger.lineUp.config;


import com.example.bitcamptiger.lineUp.dto.RestaurantDTO;
import com.example.bitcamptiger.lineUp.handler.AlarmHandler;
import com.example.bitcamptiger.lineUp.handler.LineUpHandler;

import com.example.bitcamptiger.lineUp.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RestaurantService restaurantService;

    public WebSocketConfig(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Bean // RestaurantDTO를 빈으로 등록
    public RestaurantDTO restaurantDTO() {
        return new RestaurantDTO("yourRestaurantId");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 위에서 생성한 빈을 주입하여 LineUpHandler를 생성
        registry.addHandler(new LineUpHandler(restaurantService, restaurantDTO()), "/lineup")
                .addHandler(new AlarmHandler(), "/alarm")
                .setAllowedOrigins("*");
//                .setAllowedOriginPatterns("*");
    }
}






