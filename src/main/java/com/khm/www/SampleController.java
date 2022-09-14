package com.khm.www;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khm.dto.Member;

@Controller
public class SampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@RequestMapping("doA")
	public void doo() {
		logger.info("doA called..");
	}
	
	//리턴타입 String, get방식으로 받아오기(modelattribute??)
	@RequestMapping("doB")
	public String doB(@ModelAttribute("msg") String message, Model model) {
		logger.info("doB called..");
		logger.info("doB called.. {}", message);
		
		Member m = new Member();
		m.setId("joy");
		m.setName("홍길동");
		
		model.addAttribute("member", m);
		model.addAttribute("msg", "곧 점심시간");
		
		//jsp이름
		return "result";
	}
	
	
	
	
	
}
