package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.SuggestionReplyDao;
import com.froad.CB.po.SuggestionReply;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 */
public class SuggestionReplyDaoImpl implements SuggestionReplyDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addSuggestionReply(SuggestionReply suggestionReply) {
		return (Integer) sqlMapClientTemplate.insert("suggestionReply.addSuggestionReply", suggestionReply);
	}

	@Override
	public Integer updateById(SuggestionReply suggestionReply) {
		Integer rows = sqlMapClientTemplate.update("suggestionReply.updateSuggestionReplyById", suggestionReply);
        return rows;
	}

	@Override
	public SuggestionReply selectByPrimaryKey(Integer id) {
        return (SuggestionReply) sqlMapClientTemplate.queryForObject("suggestionReply.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		SuggestionReply key = new SuggestionReply();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("suggestionReply.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		SuggestionReply key = new SuggestionReply();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("suggestionReply.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<SuggestionReply> getSuggestionReplyBySuggestionId(
			String suggestionId) {
		List<SuggestionReply> suggestionReplys = sqlMapClientTemplate.queryForList("suggestionReply.getSuggestionReplyBySuggestionId",suggestionId);
		return suggestionReplys;
	}

	@Override
	public List<SuggestionReply> getSuggestionReplys(
			SuggestionReply suggestionReply) {
		List<SuggestionReply> record = sqlMapClientTemplate.queryForList("suggestionReply.getSuggestionReplyBySelective",suggestionReply);
		return record;
	}

	@Override
	public SuggestionReply getSuggestionReplyListByPager(
			SuggestionReply suggestionReply) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("suggestionReply.getSuggestionReplyListByPagerCount",suggestionReply);
		List<SuggestionReply> list = sqlMapClientTemplate.queryForList("suggestionReply.getSuggestionReplyListByPager", suggestionReply);
		suggestionReply.setTotalCount(totalCount);
		suggestionReply.setList(list);
		return suggestionReply;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}
