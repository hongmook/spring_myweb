package com.khm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.khm.dto.Board;
import com.khm.dto.Criteria;



public interface BoardService {
	
	public Board searchBoard(String seqno);
	
	public String insertBoard(HttpServletRequest req, HttpServletResponse resp);

	public String insertBoard(Board board, MultipartFile files);

	String update(HttpServletRequest req, HttpServletResponse resp);

	List<Board> list(Criteria cri);

	void delete(String seqno);

	int getTotalRec(Criteria cri);

	
}
