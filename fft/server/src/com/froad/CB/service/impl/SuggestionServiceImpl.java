package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.SuggestionDao;
import com.froad.CB.po.Suggestion;
import com.froad.CB.service.SuggestionService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 * 意见建议service类
 */
@WebService(endpointInterface="com.froad.CB.service.SuggestionService")
public class SuggestionServiceImpl implements SuggestionService {
	private static final Logger logger=Logger.getLogger(SuggestionServiceImpl.class);
	private SuggestionDao suggestionDao;
	
	@Override
	public Integer addSuggestion(Suggestion suggestion) {
		if(suggestion==null){
			logger.info("增加意见建议参数不能为空！");
			return null;
		}
		return suggestionDao.addSuggestion(suggestion);
	}

	@Override
	public Integer updateById(Suggestion suggestion) {
		if(suggestion==null){
			logger.info("更新意见建议不能为空！");
			return null;
		}
		return suggestionDao.updateById(suggestion);
	}

	@Override
	public Suggestion selectById(Integer id) {
		if(Assert.empty(id)){
			logger.info("查询意见建议id不能为空！");
			return null;
		}
		return suggestionDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(Assert.empty(id)){
			logger.info("删除意见建议id不能为空！");
			return null;
		}
		return suggestionDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(Assert.empty(id)){
			logger.info("删除意见建议id不能为空！");
			return null;
		}
		return suggestionDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<Suggestion> getSuggestionByUserId(String userId) {
		if(Assert.empty(userId)){
			logger.info("查询意见建议userId不能为空！");
			return null;
		}
		return suggestionDao.getSuggestionByUserId(userId);
	}

	@Override
	public List<Suggestion> getSuggestions(Suggestion suggestion) {
		if(suggestion==null){
			logger.info("查询意见建议参数对象不能为空！");
			return null;
		}
		return suggestionDao.getSuggestions(suggestion);
	}

	@Override
	public Suggestion getSuggestionListByPager(Suggestion suggestion) {
		if(suggestion==null){
			logger.info("分页查询意见建议参数对象不能为空！");
			return null;
		}
		return suggestionDao.getSuggestionListByPager(suggestion);
	}

	public SuggestionDao getSuggestionDao() {
		return suggestionDao;
	}

	public void setSuggestionDao(SuggestionDao suggestionDao) {
		this.suggestionDao = suggestionDao;
	}
	
}
