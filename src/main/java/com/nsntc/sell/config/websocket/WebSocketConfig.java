package com.nsntc.sell.config.websocket;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Class Name: WebSocketConfig
 * Package: com.nsntc.sell.config.websocket
 * Description: WebSocket配置信息
 * @author wkm
 * Create DateTime: 2017/12/8 下午10:00
 * Version: 1.0
 */
//@Component
@SpringBootConfiguration
public class WebSocketConfig {

    /**
     * Method Name: serverEndpointExporter
     * Description: 实例化bean
     * Create DateTime: 2017/12/8 下午10:01
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
