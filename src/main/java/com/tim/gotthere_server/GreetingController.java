package com.tim.gotthere_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private GotthereDatabase databaseController;
    
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings2")
    public MessageSent greeting(LocationsRequest message) throws Exception {
        System.out.println(message.getStartDateTime() + " " + message.getEndDateTime());
        return new MessageSent("Hello, ");
    }

    public void autoSendingMessage(double bearing, double latitude, double longitude, double speed) throws Exception {
        Thread.sleep(1000); // simulated delay
        this.template.convertAndSend("/topic/greetings", new Location(bearing, latitude, longitude, speed));
    }
}
