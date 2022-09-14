package com.khm.www;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@RequestMapping("member/memregform")
	public void memRegForm() {
		logger.info("회원가입 맵핑");
	}
	

}
