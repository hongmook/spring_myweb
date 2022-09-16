package com.khm.service;

import java.util.List;
import java.util.Map;

import com.khm.dto.Member;



public interface MemberService {
	
	Map<String, String> login(String id, String pw);
	
	int insert(Member member);

	int idDoubleCheck(String id);

	List<Member> list();
}
