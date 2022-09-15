package com.khm.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.khm.dto.Member;



public interface MemberService {
	
	Map<String, String> login(String id, String pw);
	
	int insert(HttpServletRequest req);

	int idDoubleCheck(String id);

	List<Member> list();
}
