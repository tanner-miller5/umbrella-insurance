package com.umbrella.insurance.ws;

import com.umbrella.insurance.endpoints.ws.SocketTextHandler;
import com.umbrella.insurance.website.WebsiteApplication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {WebsiteApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocketTextHandlerTests {

    private SocketTextHandler socketTextHandler = new SocketTextHandler();

    @Test
    void contextLoads() throws Exception {
        assertThat(socketTextHandler).isNotNull();
    }

    @LocalServerPort
    private int port;

    //@Test
    @Order(1)
    void createAdRecordByAdName() throws Exception {
        String url = "ws://localhost:" + port + "/user";
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        CompletableFuture<WebSocketSession> futureWebSocketSession = webSocketClient.execute(socketTextHandler, url);
        WebSocketSession webSocketSession = futureWebSocketSession.get();
        WebSocketMessage<String> webSocketMessage = new TextMessage("hello");
        webSocketSession.sendMessage(webSocketMessage);
        webSocketSession.close();
    }
}
