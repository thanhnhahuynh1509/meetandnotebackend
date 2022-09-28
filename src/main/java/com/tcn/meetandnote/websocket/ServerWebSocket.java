package com.tcn.meetandnote.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ServerWebSocket {

    @MessageMapping("/{token}")
    @SendTo("/topic/{token}")
    public Object response(@DestinationVariable String token, Object obj) {
        return obj;
    }

}
