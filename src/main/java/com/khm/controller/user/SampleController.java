package com.khm.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khm.dto.Board;
import com.khm.dto.Member;

@Controller
public class SampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@RequestMapping("doA")
	public ModelAndView doo() {
		
		logger.info("doA called..");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/member/memregform");
		mv.addObject("msg", "회원가입 폼");
		
		return mv;
	}
	
	//리턴타입 String, get방식으로 받아오기(modelattribute??)
	//@ModelAttribute는 자동으로 해당 객체를 뷰까지 전달
	@RequestMapping("doB")
	public String doB(@ModelAttribute("msg") String message, Model model) {
		logger.info("doB called..");
		logger.info("doB called.. {}", message);
		
		Member m = new Member();
		m.setId("joy");
		m.setName("홍길동");
		
		model.addAttribute("member", m);
		
		Board b = new Board();
		b.setTitle("자바");
		// 키를 던지지 않을때는 타입의 첫글자를 소문자로 바꿔서 view page에서 사용 가능 ex) Board -> board
		model.addAttribute(b);
//		model.addAttribute("msg", "곧 점심시간");
		
		//jsp이름
		return "redirect:/doA";
	}
	
	@RequestMapping("doC")
	public String doC(RedirectAttributes rttr) {
		
		Member m = new Member();
		m.setId("joy");
		m.setName("홍길동");
		
		Board b = new Board();
		b.setTitle("자바");
		
		rttr.addFlashAttribute("member",m);
		rttr.addFlashAttribute(b);
		
		return "redirect:/doA";
	}
	
	//json 라이브러리 추가
	@RequestMapping("doJASON")
	public @ResponseBody Member dojson() {
		Member m = new Member();
		m.setId("joy");
		m.setHobby_str("테니스, 골프");
		m.setName("홍길동");
		
		return m;
	}
	
	
	
	
	
	
}
