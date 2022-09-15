package com.khm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.khm.common.OracleConn;


public class FileDao {
	private final static Connection conn = OracleConn.getInstance().getConn();

	public int deleteByNo(String no) {
		int rs = 0;
		
		//첨부파일 레코드삭제
		String sql = "delete from attachfile_thumb where attach_no = ?";
		
		PreparedStatement stmt;
		try {
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			stmt.executeQuery();
			
			sql = "delete from attachfile where no = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, no);
			rs = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return rs;
		
	}
}
