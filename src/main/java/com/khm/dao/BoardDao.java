package com.khm.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.khm.common.OracleConn;
import com.khm.dto.AttachFile;
import com.khm.dto.Board;
import com.khm.dto.Criteria;
import com.khm.dto.Reply;
import com.khm.dto.Thumbnail;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class BoardDao {
	private final static Connection conn = OracleConn.getInstance().getConn();

	
	public List<Board> boardList(Criteria cri) {

		List<Board> board = new ArrayList<Board>();
		
		CallableStatement stmt;
		
		String search_title = null;
		String search_name = null;
		//제목검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("name")) {

			search_name = cri.getSearchText();
		}
		//이름검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("title")) {
		
			search_title = cri.getSearchText();
		}	
		
		String sql = "call p_getboardlist(?, ?, ?, ?, ?)";
		
		try {
			stmt = conn.prepareCall(sql);
			stmt.setInt(1, cri.getCurrentPage());
			stmt.setInt(2, cri.getRowPerPage());
			stmt.setString(3, search_name);
			stmt.setString(4, search_title);
			stmt.registerOutParameter(5, OracleTypes.CURSOR);
			stmt.executeQuery();

			//커서는 object로 받아와야함
			ResultSet rs = (ResultSet)stmt.getObject(5);
			
			while(rs.next()) {
				Board b = new Board();
				b.setNo(rs.getString("rn"));
				b.setTitle(rs.getString("title"));
				b.setWdate(rs.getString("wdate"));
				b.setName(rs.getString("name"));
				b.setCount(rs.getString("count"));
				b.setSeqno(rs.getString("seqno"));
				board.add(b);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return board;
	}

	public Board boardDetail(String seqno) {
		
		Board board = new Board();
		//조회수 카운트
		try {
			String sql = "call p_board_detail(?,?,?,?)";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setInt(1, Integer.parseInt(seqno));
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.executeQuery();

			ResultSet rs = (ResultSet)stmt.getObject(2);
				
			rs.next();
			
				board.setSeqno(seqno);
				board.setContent(rs.getString("content"));
				board.setCount(rs.getString("count"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setWdate(rs.getString("wdate"));
				board.setTitle(rs.getString("title"));
				board.setOpen(rs.getString("open"));
			
				
			List<Reply> reply = new ArrayList<Reply>();

			rs = (ResultSet)stmt.getObject(3);
			while(rs.next()) { 
				Reply rep = new Reply();
				 
				rep.setComment(rs.getString("content")); 
				rep.setWdate(rs.getString("wdate"));
				rep.setId(rs.getString("id"));
				rep.setName(rs.getString("name")); 
				reply.add(rep); 
			}
			board.setReply(reply);
				 
				 
			List<AttachFile> fileList = new ArrayList<AttachFile>();
			
			rs = (ResultSet)stmt.getObject(4);	 
			while(rs.next()) {
				AttachFile att = new AttachFile();
					 
				att.setNo(rs.getString("no"));
				att.setFilename(rs.getString("filename"));
				att.setSavefilename(rs.getString("savefilename"));
				att.setFilesize(rs.getString("filesize"));
				att.setFiletype(rs.getString("filetype"));
				att.setFilepath(rs.getString("filepath"));

							 
				Thumbnail th = new Thumbnail();
				th.setFilename(rs.getString("thumb_name"));
				th.setFilesize(rs.getString("thumb_size"));
				th.setFilepath(rs.getString("thumb_path"));
				att.setThumbnail(th);
							 
				fileList.add(att);
			}
				 
			board.setAttachfile(fileList);
				 
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return board;
	}

	public static String insert(Board board, AttachFile attachFile) {
		
		CallableStatement stmt;
		String seqno = null;
		
		try {
			String sql = "call p_insert_board(?,?,?)";
			stmt = conn.prepareCall(sql);
			
			StructDescriptor st_board = StructDescriptor.createDescriptor("OBJ_BOARD", conn);
			Object[] obj_board = {board.getTitle(), board.getContent(), board.getOpen(), board.getId()};
			STRUCT board_rec = new STRUCT(st_board, conn, obj_board);
			
			stmt.setObject(1, board_rec);

			ArrayDescriptor desc = ArrayDescriptor.createDescriptor("ATTACH_NT",conn);
			ARRAY attach_arr = null; 
			
			
			//첨부파일
			if(attachFile != null) {
				
				StructDescriptor st_thumb = StructDescriptor.createDescriptor("OBJ_ATTACH_THUMB", conn);
				STRUCT attach_thumb_rec = null;
				Object[] obj_thumb = null;
				
				if(attachFile.getThumbnail() != null) {
					
					obj_thumb = new Object[] {attachFile.getThumbnail().getFilename(),
										  	  attachFile.getThumbnail().getFilesize(), 
										  	  attachFile.getThumbnail().getFilepath()};
				} 

				attach_thumb_rec = new STRUCT(st_thumb, conn, obj_thumb);
				
				StructDescriptor st_attach = StructDescriptor.createDescriptor("OBJ_ATTACH",conn);
				
				Object[] obj_attach = {attachFile.getFilename(), attachFile.getFilesize(), 
									   attachFile.getSavefilename(), attachFile.getFiletype(), 
									   attachFile.getFilepath(),
									   attach_thumb_rec
									  };
				STRUCT[] attach_rec = new STRUCT[1];
				attach_rec[0] = new STRUCT(st_attach, conn, obj_attach);
				
				
				attach_arr = new ARRAY(desc, conn, attach_rec);
				
				
			} else {
				attach_arr = new ARRAY(desc, conn, null);
				
				
			}
			stmt.setArray(2, attach_arr);
			
			stmt.registerOutParameter(3, OracleType.VARCHAR2);
			stmt.executeQuery();
			
			seqno = stmt.getString(3);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return seqno;
	}

	static String insertAttachFile(String seqno, AttachFile attachFile) {
		
		//첨부파일 저장
		String sql = "INSERT INTO attachfile(no, filename, savefilename, filesize, filetype, filepath, board_seqno)"
					+ " VALUES (ATTACHFILE_SEQNO.NEXTVAL, ?,?,?,?,?,?)";
		
		PreparedStatement stmt;
		String attach_no = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, attachFile.getFilename());
			stmt.setString(2, attachFile.getSavefilename());
			stmt.setString(3, attachFile.getFilesize());
			stmt.setString(4, attachFile.getFiletype());
			stmt.setString(5, attachFile.getFilepath());
			stmt.setString(6, seqno);
			stmt.executeQuery();
			
			sql = "SELECT max(no) FROM attachfile";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			attach_no = rs.getString(1);
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attach_no;
	}
	
	static void insertThumbName(String attach_no, AttachFile attachFile) {
		
		//썸네일 저장
		String sql = "INSERT INTO attachfile_thumb (no, filename, filesize, filepath, attach_no) "
				+ " VALUES (attachfile_thumb_seqNo.nextval, ?, ?, ?, ?)";
		
		PreparedStatement stmt;
		try {
			
			stmt = conn.prepareStatement(sql);
			Thumbnail thumb = attachFile.getThumbnail();
			stmt.setString(1, thumb.getFilename());
			stmt.setString(2, thumb.getFilesize());
			stmt.setString(3, thumb.getFilepath());
			stmt.setString(4, attach_no);
			stmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(Board board, AttachFile attachFile) {
		
		//보드 update
		String sql="call p_updateBoard(?,?,?,?)";
		CallableStatement stmt;
		try {
			
			stmt = conn.prepareCall(sql);
			
			stmt.setString(1, board.getTitle());
			stmt.setString(2, board.getContent());
			stmt.setString(3, board.getOpen());
			stmt.setString(4, board.getSeqno());
			stmt.executeQuery();
			
			//첨부파일
			if(attachFile != null) {
				
				String attach_no = insertAttachFile(board.getSeqno(), attachFile);
				
				String fileType = attachFile.getFiletype();
				
				//썸네일
				if(fileType.substring(0, fileType.indexOf("/")).equals("image")) {
					insertThumbName(attach_no, attachFile);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public int getTotalRec(Criteria cri) {
		int total = 0;
		String search_title = null;
		String search_name = null;
		//제목검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("name")) {

			search_title = cri.getSearchText();
		}
		//이름검색
		if(cri.getSearchField() != null && cri.getSearchField().equals("title")) {
		
			search_name = cri.getSearchText();
		}	
		String sql = "call p_getboardtotal(?,?,?)";
		CallableStatement stmt;
		try {
			stmt = conn.prepareCall(sql);
			stmt.setString(1, search_name);
			stmt.setString(2, search_title);
			stmt.registerOutParameter(3, OracleTypes.INTEGER);
			stmt.executeQuery();
			
			total = stmt.getInt(3);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return total;
	}

	public Map<String, String> deleteByNo(String seqno) {
		Map<String, String> map = new HashMap<String, String>();
		
		CallableStatement stmt;
		String sql = "call p_deleteBoard(?,?,?,?)";
		
		try {
			stmt = conn.prepareCall(sql);
			stmt.setString(1, seqno);
			stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			stmt.registerOutParameter(3, OracleTypes.VARCHAR);
			stmt.registerOutParameter(4, OracleTypes.VARCHAR);
			stmt.executeQuery();
			map.put("savefilename", stmt.getString(2));
			map.put("filepath", stmt.getString(3));
			map.put("thumb_filename", stmt.getString(4));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}

}
