package Servizi.Web.Socket1.controllers;

import Servizi.Web.Socket1.entities.MessageDTO;
import Servizi.Web.Socket1.entities.MessageFromClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class Notification {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity sendNotificationToTheClient(@RequestBody MessageDTO messageDTO){
        messagingTemplate.convertAndSend("/topic/messages",messageDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public MessageDTO handleMessageFromWebSocket(@RequestBody MessageFromClientDTO message){
        System.out.printf("Something arrived on /app/hello : %s",message.toString());
        return new MessageDTO("message from client : " +  message.getFrom()," - " + message.getText());

    }
}