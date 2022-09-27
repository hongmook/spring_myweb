package com.khm.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khm.dto.Criteria;
import com.khm.dto.Reply;
import com.khm.dto.ReplyVO;
import com.khm.mapper.ReplyMapper;

@Service
public class ReplySerivceImp implements ReplyService {

	private static final Logger log = LoggerFactory.getLogger(ReplySerivceImp.class);
	
	@Autowired
	private ReplyMapper mapper;
	
	@Override
	public int register(Reply reply) {

		log.info("reply register service called.." + reply);
		
		return mapper.insert(reply);
		
	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		
		return mapper.getList(cri, bno);
	}

	@Override
	public ReplyVO get(Long rno) {
		
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		
		return mapper.update(vo);
	}

	@Override
	public int remove(Long rno) {

		return mapper.delete(rno);
	}
}
