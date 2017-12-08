package com.nsntc.sell.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Class Name: WebSocket
 * Package: com.nsntc.sell.component
 * Description:
 * @author wkm
 * Create DateTime: 2017/12/8 下午10:04
 * Version: 1.0
 */
@Component
/** url协议: ws://xxx.xxx.com */
@ServerEndpoint("webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    /** session容器 */
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * Method Name: onOpen
     * Description: 对应h5 js事件
     * Create DateTime: 2017/12/8 下午10:06
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("【websocket消息】有新的连接, 总数:{}", webSocketSet.size());
    }

    /**
     * Method Name: onClose
     * Description: 对应h5 js事件
     * Create DateTime: 2017/12/8 下午10:06
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", webSocketSet.size());
    }

    /**
     * Method Name: onMessage
     * Description: 对应h5 js事件
     * Create DateTime: 2017/12/8 下午10:06
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    /**
     * Method Name: sendMessage
     * Description: 后台发送消息
     * Create DateTime: 2017/12/8 下午10:11
     * @param message
     */
    public void sendMessage(String message) {
        for (WebSocket webSocket: webSocketSet) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error(" sessionId={}, 发送消息异常: {} ", session.getId(), e);
            }
        }
    }

}
