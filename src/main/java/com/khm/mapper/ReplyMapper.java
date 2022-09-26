package com.khm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khm.dto.Criteria;
import com.khm.dto.Reply;
import com.khm.dto.ReplyVO;

public interface ReplyMapper {
	
	public int insert(Reply reply);

	public List<ReplyVO> getList(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno); //마이바티스 어노테이션
}
