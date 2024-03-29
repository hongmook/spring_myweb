package com.khm.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.khm.dto.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MemberMapperTest {

	private static final Logger log = LoggerFactory.getLogger("MemberMapperTest.class");
	
	@Autowired
	private MemberMapper tm;
	
	@Test
	public void test() {
		Member m = tm.getById("joy2");
		
		log.info("마이바티스 멤버 결과 : " + m.getName());
	}
	
	

}
