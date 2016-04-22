package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.SuggestionReplyDao;
import com.froad.CB.po.SuggestionReply;
import com.froad.CB.service.SuggestionReplyService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 * 意见建议回复信息service类
 */
@WebService(endpointInterface="com.froad.CB.service.SuggestionReplyService")
public class SuggestionReplyServiceImpl implements SuggestionReplyService {
	private static final Logger logger=Logger.getLogger(SuggestionReplyServiceImpl.class);
	private SuggestionReplyDao suggestionReplyDao;
	
	@Override
	public Integer addSuggestionReply(SuggestionReply suggestionReply) {
		if(suggestionReply==null){
			logger.info("增加意见建议回复参数不能为空！");
			return null;
		}
		return suggestionReplyDao.addSuggestionReply(suggestionReply);
	}

	@Override
	public Integer updateById(SuggestionReply suggestionReply) {
		if(suggestionReply==null){
			logger.info("更新意见建议回复不能为空！");
			return null;
		}
		return suggestionReplyDao.updateById(suggestionReply);
	}

	@Override
	public SuggestionReply selectById(Integer id) {
		if(Assert.empty(id)){
			logger.info("查询意见建议回复id不能为空！");
			return null;
		}
		return suggestionReplyDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(Assert.empty(id)){
			logger.info("删除意见建议回复id不能为空！");
			return null;
		}
		return suggestionReplyDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(Assert.empty(id)){
			logger.info("删除意见建议回复id不能为空！");
			return null;
		}
		return suggestionReplyDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public List<SuggestionReply> getSuggestionReplyBySuggestionId(
			String suggestionId) {
		if(Assert.empty(suggestionId)){
			logger.info("查询意见建议回复suggestionId不能为空！");
			return null;
		}
		return suggestionReplyDao.getSuggestionReplyBySuggestionId(suggestionId);
	}

	@Override
	public List<SuggestionReply> getSuggestionReplys(
			SuggestionReply suggestionReply) {
		if(suggestionReply==null){
			logger.info("查询意见建议回复不能为空！");
			return null;
		}
		return suggestionReplyDao.getSuggestionReplys(suggestionReply);
	}

	@Override
	public SuggestionReply getSuggestionReplyListByPager(
			SuggestionReply suggestionReply) {
		if(suggestionReply==null){
			logger.info("分页查询意见建议回复不能为空！");
			return null;
		}
		return suggestionReplyDao.getSuggestionReplyListByPager(suggestionReply);
	}

	public SuggestionReplyDao getSuggestionReplyDao() {
		return suggestionReplyDao;
	}

	public void setSuggestionReplyDao(SuggestionReplyDao suggestionReplyDao) {
		this.suggestionReplyDao = suggestionReplyDao;
	}
	
}
