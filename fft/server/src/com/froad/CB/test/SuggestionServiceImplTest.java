package com.froad.CB.test;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.froad.CB.po.Suggestion;
import com.froad.CB.service.impl.SuggestionServiceImpl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SuggestionServiceImplTest {
	@Resource
	private SuggestionServiceImpl suggestionServiceImpl;
	
	@Test
	public void testAddSuggestion() {
		Suggestion suggestion = new Suggestion();
		suggestion.setUserName("lqp5");
		suggestion.setUserMobile("13918140393");
		suggestion.setUserEmail("liqiaopeng@f-road.com.cn");
		suggestion.setIsReply("0");
		suggestion.setContent("不好吃啊");
		suggestion.setTitle("吃货的建议");
		suggestion.setRemark("备注信息");
		suggestion.setState("20");
		suggestion.setUserId("c3bb0ee3c8085601255a45bd369b3593");
		suggestionServiceImpl.addSuggestion(suggestion);
	}

	@Test
	public void testUpdateById() {
		Suggestion suggestion = new Suggestion();
		suggestion.setId(100001001);
		suggestion.setState("30");
		suggestionServiceImpl.updateById(suggestion);
	}

	@Test
	public void testSelectById() {
		Integer id=100001001;
		suggestionServiceImpl.selectById(id);
	}

	@Test
	public void testDeleteStateById() {
		String id="100001001";
		suggestionServiceImpl.deleteStateById(id);
	}

	@Test
	public void testGetSuggestionByUserId() {
		String userId="c3bb0ee3c8085601255a45bd369b3593";
		suggestionServiceImpl.getSuggestionByUserId(userId);
	}

	@Test
	public void testGetSuggestions() {
		Suggestion suggestion = new Suggestion();
		suggestion.setState("50");
		suggestionServiceImpl.getSuggestions(suggestion);
	}

	@Test
	public void testGetSuggestionListByPager() {
		Suggestion suggestion = new Suggestion();
		suggestion.setState("50");
		suggestionServiceImpl.getSuggestions(suggestion);
	}	

	@Test
	public void testDeleteById() {
		String id="100001001";
		suggestionServiceImpl.deleteById(id);
	}
}
