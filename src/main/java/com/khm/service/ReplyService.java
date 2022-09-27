package com.khm.service;

import java.util.List;


import com.khm.dto.Criteria;
import com.khm.dto.Reply;
import com.khm.dto.ReplyVO;

public interface ReplyService {

	public int register(Reply reply);

	public List<ReplyVO> getList(Criteria cri, Long bno);

	public ReplyVO get(Long rno);

	public int modify(ReplyVO vo);

	public int remove(Long rno);
}
