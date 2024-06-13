package com.example.LiveFootballStatistics.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles incoming text messages from WebSocket sessions.
 *
 * @param session The WebSocket session that received the message
 * @param message The text message received
 * @throws Exception if an error occurs while handling the message
 */
@Component
public class SocketHandler extends TextWebSocketHandler {
  private final List<WebSocketSession> sessions = new ArrayList<>();

  /**
   * Handles a text message received from a WebSocket session.
   *
   * @param session The WebSocket session that received the message
   * @param message The text message received
   * @throws Exception if an error occurs while handling the message
   */
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    System.out.println(message.toString());
  }

  /**
   * Called after a new WebSocket connection is established.
   *
   * @param session The WebSocket session that was established
   * @throws Exception If an error occurs during the handling of the connection
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("New Session Established !");
    sessions.add(session);
  }

  /**
   * Sends a sports update to all connected WebSocket sessions.
   *
   * @param matchData The match data to send as a JSON string
   * @throws JsonProcessingException If there is an error processing the match data as JSON
   */
  public void sendSportsUpdate(String matchData) throws JsonProcessingException {
    for (WebSocketSession ses : sessions) {
      try {
        if (!ses.isOpen()) {
          continue;
        }

        ses.sendMessage(new TextMessage(matchData));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };
  }
}
