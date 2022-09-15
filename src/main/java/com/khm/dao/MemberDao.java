package com.khm.dao;

import java.sql.*;
import java.util.*;

import oracle.jdbc.OracleResultSet;
import oracle.jdbc.internal.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import javax.servlet.http.HttpServletRequest;

import com.khm.common.OracleConn;
import com.khm.dto.Member;

public class MemberDao {
	
	private final Connection conn = OracleConn.getInstance().getConn();
	PreparedStatement stmt = null;
	
	
	public Map<String, String> loginProc(String id, String pw){

//		String[] status = new String[2]; //로그인 실패
		
		Map<String, String> map	= new HashMap<String, String>();
		
		String sql = "select * from member where id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			
			
		
			if(rs.next()) {

				if(rs.getString("pw").equals(pw)){ 
					/*
					status[0] = "ok"; //로그인 성공
					status[1] = rs.getString("name");
					*/
					
					map.put("login", "ok");
					map.put("name", rs.getString("name"));
					
				} else {
					/*
				 		status[0] = "pwfail"; //패스워드 틀림
					 */		
					map.put("login", "pwfail"); //패스워드 틀림

				}
				
			} else {
				/*
				   status[0] = "fail";
				 */	

				map.put("login", "pwfail");

			}

			
		} catch (SQLException e) {

			e.printStackTrace();
		
		} finally {
			
		//resourceClose();
			
		}
		
		return map;

	}

	
	
	private void resourceClose() {
		//자원반납
		try {
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}



	public int insertMember(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String[] hobby = request.getParameterValues("hobby");
		String email = request.getParameter("eid") + "@" + request.getParameter("Domain");
		String intro = request.getParameter("intro");

		
		CallableStatement stmt;
		int rs = 0;
		try {

			String sql = "call p_insert_member(?,?,?)";
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_desc = StructDescriptor.createDescriptor("OBJ_MEMBER", conn);
			Object[] obj_member = {id, pw, name, gender, email, intro};
			STRUCT member_rec = new STRUCT(st_desc, conn, obj_member);
			stmt.setObject(1, member_rec);
			
			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("STRING_NT", conn);
			ARRAY hobby_arr = new ARRAY(desc, conn, hobby); //(타입객체 / 오라클연결 / 던져주는 타입)
			stmt.setArray(2, hobby_arr);
			
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeUpdate();
			rs = stmt.getInt(3);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs;
	}


	public int selectByid(String id) {
		
		CallableStatement stmt = null;
		int rs = 0;
		String sql = "call p_idDoubleCheck(?, ?)";
		
		try {
			stmt = conn.prepareCall(sql);
			
			stmt.setString(1, id);
			stmt.registerOutParameter(2, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			//값 가져오기
			rs = stmt.getInt(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}



	public List<Member> getMember() {
		CallableStatement stmt = null;
		
		List<Member> member = new ArrayList<Member>();
		
		String sql = "call p_get_member(?)";
		
		try {
			stmt = conn.prepareCall(sql);
			
			stmt.registerOutParameter(1, OracleTypes.CURSOR);
			stmt.executeQuery();
			
			//커서는 object
			ResultSet rs = (ResultSet)stmt.getObject(1);
			
			while(rs.next()) {
				Member m = new Member();
				m.setId(rs.getString("id"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getString("gender"));
				m.setWdate(rs.getString("wdate"));

				//취미
				if(rs.getArray("hobby_nm") != null) {
					//컬렉션 중첩테이블 데이터 가져오기
					//resultset 타입인 rs를 OracleResultSet으로 강제타입변환
					ARRAY h_arr = ((OracleResultSet)rs).getARRAY("hobby_nm");
					
					System.out.println("취미 타입 : " + h_arr.getSQLTypeName());
					System.out.println("취미 타입코드 : " + h_arr.getBaseType());
					System.out.println("취미 갯수 : " + h_arr.length());
					
					String[] h_val = (String[])h_arr.getArray();
					
					for(int i=0; i < h_arr.length(); i++) {
						String hobby_str = h_val[i];
						System.out.println(">>>취미["+i+"] = " + hobby_str);
					}
					
					//문자열을 스트링으로 바꿔줌
					m.setHobby(Arrays.toString(h_val));
					
				}

				member.add(m);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return member;
	}
	
}
