package com.khm.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.khm.dto.Criteria;
import com.khm.dto.Reply;
import com.khm.dto.ReplyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ReplyMapperTest {

	private static final Logger log = LoggerFactory.getLogger("ReplyMapperTest.class");
	
	@Autowired
	private ReplyMapper mapper;
/*	
	@Test
	public void test() {

		Reply r = new Reply();
		r.setComment("안녕하세요");
		r.setId("joy2");
		r.setBoardNo("98");
		
		mapper.insert(r);
	}
*/	
	@Test
	public void testList() {
		Criteria cri = new Criteria(1,5);
		
		List<ReplyVO> list = mapper.getList(cri, 98L);
		
		for(ReplyVO r : list) {
			log.info("댓글 내용 : "+r.getContent());
		}
		
	}
	

}
