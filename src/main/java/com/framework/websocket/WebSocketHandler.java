package com.framework.websocket;

import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpSession;

/**
 * Created by huhu on 2018/12/10.
 */
public interface WebSocketHandler {

    /**
     * 给某个用户发送消息
     * @param userCode  用户的编号
     * @param message
     */
    void sendMessageToUser(String userCode, TextMessage message);
    /**
     * 给所有登录用户发送消息
     * @param message
     */
    void sendMessageToUsers(TextMessage message);
    /**
     * 给所有游客用户发送消息
     * @param message
     */
    void sendMessageToVisitors(TextMessage message);

    /**
     * 给所有用户发送消息
     * @param message
     */
    void sendMessageToAllUsers(TextMessage message);
    /**
     * 给当前用户发送信息
     * @param message
     */
    void sendMessage(HttpSession session, TextMessage message);
}
