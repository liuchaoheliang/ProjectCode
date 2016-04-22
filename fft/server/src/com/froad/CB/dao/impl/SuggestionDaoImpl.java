package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.SuggestionDao;
import com.froad.CB.po.Suggestion;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-4  
 * @version 1.0
 */
public class SuggestionDaoImpl implements SuggestionDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addSuggestion(Suggestion suggestion) {
		return (Integer) sqlMapClientTemplate.insert("suggestion.addSuggestion", suggestion);
	}

	@Override
	public Integer updateById(Suggestion suggestion) {
		Integer rows = sqlMapClientTemplate.update("suggestion.updateSuggestionById", suggestion);
        return rows;
	}

	@Override
	public Suggestion selectByPrimaryKey(Integer id) {
        return (Suggestion) sqlMapClientTemplate.queryForObject("suggestion.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		Suggestion key = new Suggestion();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("suggestion.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		Suggestion key = new Suggestion();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("suggestion.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<Suggestion> getSuggestionByUserId(String userId) {
		List<Suggestion> suggestions = sqlMapClientTemplate.queryForList("suggestion.getSuggestionByUserId",userId);
		return suggestions;
	}

	@Override
	public List<Suggestion> getSuggestions(Suggestion suggestion) {
		List<Suggestion> record = sqlMapClientTemplate.queryForList("suggestion.getSuggestionBySelective",suggestion);
		return record;
	}

	@Override
	public Suggestion getSuggestionListByPager(Suggestion suggestion) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("suggestion.getSuggestionListByPagerCount",suggestion);
		List<Suggestion> list = sqlMapClientTemplate.queryForList("suggestion.getSuggestionListByPager", suggestion);
		suggestion.setTotalCount(totalCount);
		suggestion.setList(list);
		return suggestion;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}
