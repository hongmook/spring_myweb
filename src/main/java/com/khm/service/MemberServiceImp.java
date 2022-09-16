package com.khm.service;

import java.util.List;
import java.util.Map;

import com.khm.dao.MemberDao;
import com.khm.dto.Member;

public class MemberServiceImp implements MemberService {

	MemberDao dao = new MemberDao();
	
	@Override
	public Map<String, String> login(String id, String pw) {
		//memberdao의 loginproc호출
		
		
		return dao.loginProc(id, pw);
	}

	@Override
	public int insert(Member member) {

		return dao.insertMember(member);
	}

	@Override
	public int idDoubleCheck(String id) {
		
		return dao.selectByid(id);
	}
	
	@Override
	public List<Member> list() {

		return dao.getMember();
	}

}
