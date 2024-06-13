package com.example.LiveFootballStatistics.config;

import com.example.LiveFootballStatistics.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket setup.
 */
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

  @Autowired
  private SocketHandler socketHandler;

  /**
   * Registers a WebSocket handler for the "/score" endpoint and allows connections from any origin.
   *
   * @param registry The WebSocketHandlerRegistry to register the handler with
   */
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(socketHandler,"/score")
        .setAllowedOrigins("*");
  }
}
