package com.khm.dao;

import java.util.List;
import java.util.Map;

import com.khm.dto.Member;

public interface MemberDao {

	public Map<String, String> loginProc(String id, String pw);
	
	public int insertMember(Member member);
	
	public int selectByid(String id);
	
	public List<Member> getMember();
	
}
