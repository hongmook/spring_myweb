package com.khm.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController  {

	@GetMapping("/chat")
	public String regForm() {
		
		return "chat/chat";
	}
	
	@GetMapping("/chatList")
	public String chatList(){
		
		return "chat/chatList";
	}
	
	@GetMapping("/chat3")
	public String mychat() {
		
		return "chat/chat3";
	}
}
