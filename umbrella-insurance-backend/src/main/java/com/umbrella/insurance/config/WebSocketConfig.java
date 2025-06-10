package com.umbrella.insurance.config;

import com.umbrella.insurance.endpoints.ws.SocketTextHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import static com.umbrella.insurance.core.utils.Security.PHONE_WS_CONNECTION_PASSWORD;
import static com.umbrella.insurance.core.utils.Security.WS_PHONE_ENDPOINT;
import static com.umbrella.insurance.core.utils.Security.WS_USER_ENDPOINT;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketTextHandler(), WS_USER_ENDPOINT).setAllowedOrigins("*");
        registry.addHandler(new SocketTextHandler(), WS_PHONE_ENDPOINT + PHONE_WS_CONNECTION_PASSWORD).setAllowedOrigins("*");
    }

}
