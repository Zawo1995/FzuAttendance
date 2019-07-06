package com.framework.websocket;

import com.framework.autoscan.Scanner;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huhu on 2018/12/10.
 * 该对象提供了客户端连接,关闭,错误,发送等方法,重写这几个方法即可实现自定义业务逻辑
 */

public class SpringWebSocketHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final Map<Object, WebSocketSession> users;
    private static final Map<Object, WebSocketSession> visitor;
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    static {
        users = new HashMap<Object, WebSocketSession>();
        visitor = new HashMap<Object, WebSocketSession>();
    }

    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        String name = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        if (name == null) {
            name = "default:" + session.getId();
            visitor.put(name, session);
        } else {
            users.put(name, session);
        }
        System.out.print("connect to the websocket success......当前用户数量:" + users.size());
        System.out.println("，当前游客数量:" + visitor.size());

        //  请求警报信息
        Scanner.warn();
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        if (username == null){
            username = session.getId();
            System.out.println("游客"+username+"已退出！");
            visitor.remove("default:" + username);
        }else {
            System.out.println("用户"+username+"已退出！");
            users.remove(username);
        }
        System.out.print("当前用户数量:" + users.size());
        System.out.println("，当前游客数量:" + visitor.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()) session.close();
        logger.debug("websocket connection closed......");
        users.remove(session);
        visitor.remove(session);
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给用户发送消息
     * @param message
     */
    private void sendMessageToUser(WebSocketSession user, TextMessage message) {
        try {
            if (user.isOpen()) {
                user.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给当前用户发送信息
     * @param message
     */
    public void sendMessage(HttpSession session, TextMessage message){
        String username = (String) session.getAttribute("SESSION_USERNAME");
        if (username == null){
            username = "default:" + session.getId();
            sendMessageToVisitor(username, message);
        } else {
            sendMessageToUser(username, message);
        }
    }
    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        WebSocketSession user = users.get(userName);
        if (user != null) sendMessageToUser(user, message);
    }
    /**
     * 给某个游客发送消息
     * @param userName
     * @param message
     */
    private void sendMessageToVisitor(String userName, TextMessage message) {
        WebSocketSession user = visitor.get(userName);
        if (user != null) sendMessageToUser(user, message);
    }
    /**
     * 给所有在线用户发送消息
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (Map.Entry<Object, WebSocketSession> userEntry : users.entrySet()) {
            WebSocketSession user = userEntry.getValue();
            sendMessageToUser(user, message);
        }
    }
    /**
     * 给所有游客用户发送消息
     * @param message
     */
    public void sendMessageToVisitors(TextMessage message) {
        for (Map.Entry<Object, WebSocketSession> userEntry : visitor.entrySet()) {
            WebSocketSession user = userEntry.getValue();
            sendMessageToUser(user, message);
        }
    }

    /**
     * 给所有登录用户发送消息
     * @param message
     */
    public void sendMessageToAllUsers(TextMessage message) {
        sendMessageToUsers(message);
        sendMessageToVisitors(message);
    }
}
