package com.froad.CB.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.froad.CB.po.SuggestionReply;
import com.froad.CB.service.impl.SuggestionReplyServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SuggestionReplyServiceImplTest {
	@Resource
	private SuggestionReplyServiceImpl suggestionReplyServiceImpl;
	
	@Test
	public void testAddSuggestionReply() {
		SuggestionReply suggestionReply = new SuggestionReply();
		suggestionReply.setSuggestionId("100001001");
		suggestionReply.setReplyAccount("客服");
		suggestionReply.setContent("不好吃啊");
		suggestionReply.setTitle("吃货的建议");
		suggestionReply.setRemark("备注信息");
		suggestionReply.setState("20");
		suggestionReplyServiceImpl.addSuggestionReply(suggestionReply);
	}

	@Test
	public void testUpdateById() {
		SuggestionReply suggestionReply = new SuggestionReply();
		suggestionReply.setId(100001001);
		suggestionReply.setState("30");
		suggestionReplyServiceImpl.updateById(suggestionReply);
	}

	@Test
	public void testSelectById() {
		Integer id=100001001;
		suggestionReplyServiceImpl.selectById(id);
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		suggestionReplyServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetSuggestionReplyBySuggestionId() {
		String suggestionId="100001001";
		suggestionReplyServiceImpl.getSuggestionReplyBySuggestionId(suggestionId);
	}

	@Test
	public void testGetSuggestionReplys() {
		SuggestionReply suggestionReply = new SuggestionReply();
		suggestionReply.setState("50");
		suggestionReplyServiceImpl.getSuggestionReplys(suggestionReply);
	}

	@Test
	public void testGetSuggestionReplyListByPager() {
		SuggestionReply suggestionReply = new SuggestionReply();
		suggestionReply.setState("30");
		suggestionReplyServiceImpl.getSuggestionReplyListByPager(suggestionReply);
	}

	@Test
	public void testDeleteById() {
		String id="100001001";
		suggestionReplyServiceImpl.deleteById(id);
	}
}
