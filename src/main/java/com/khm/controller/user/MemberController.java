package com.khm.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khm.dto.Member;
import com.khm.service.MemberService;
import com.khm.service.MemberServiceImp;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
//	MemberService ms = new MemberServiceImp();
	
	private MemberService ms;
	
	public MemberController(MemberService ms) {
		this.ms = ms;
	}
	
	@RequestMapping("memregform") //default get방식
	public void memRegForm() {
		logger.info("회원가입 맵핑");
	}
	
	//post 맵핑
	@PostMapping("register")
	public String register(Member member, RedirectAttributes model) {
		logger.info("회원가입처리 맵핑");
		
		logger.info("아이디 : {}", member.getId());
		logger.info("이름 : {}", member.getName());
		
		ms.insert(member);
		model.addFlashAttribute("msg","memberOk");
		
		return "redirect:/";
	}
	
	@GetMapping("idDoubleCheck")
	public ResponseEntity<String> idDoubleCheck(@RequestParam("id") String id) {
		logger.info("idDoubleCheck called..");
		
		String rs = Integer.toString(ms.idDoubleCheck(id));
		
		return new ResponseEntity<String>(rs, HttpStatus.OK);
	}
	
	

}
