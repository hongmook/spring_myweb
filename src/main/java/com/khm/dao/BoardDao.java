package com.khm.dao;

import java.util.List;
import java.util.Map;

import com.khm.dto.AttachFile;
import com.khm.dto.Board;
import com.khm.dto.Criteria;

public interface BoardDao {

	public List<Board> boardList(Criteria cri);
	
	public Board boardDetail(String seqno);
	
	public String insert(Board board, AttachFile attachFile);
	
	String insertAttachFile(String seqno, AttachFile attachFile);
	
	void insertThumbName(String attach_no, AttachFile attachFile);
	
	public void update(Board board, AttachFile attachFile);

	public int getTotalRec(Criteria cri);
	
	public Map<String, String> deleteByNo(String seqno);

}
