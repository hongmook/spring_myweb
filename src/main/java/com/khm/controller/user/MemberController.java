package com.khm.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khm.dto.Member;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("memregform") //default get방식
	public void memRegForm() {
		logger.info("회원가입 맵핑");
	}
	
	//post 맵핑
	@PostMapping("register")
	public String register(Member member) {
		logger.info("회원가입처리 맵핑");
		
		logger.info("아이디 : {}", member.getId());
		logger.info("이름 : {}", member.getName());
		
		return "redirect:/";
	}

}
