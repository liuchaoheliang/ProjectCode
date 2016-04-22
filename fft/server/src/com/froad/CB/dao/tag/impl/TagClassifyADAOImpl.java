package com.froad.CB.dao.tag.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.tag.TagClassifyADAO;
import com.froad.CB.po.tag.TagClassifyA;

public class TagClassifyADAOImpl  implements TagClassifyADAO {
	private SqlMapClientTemplate sqlMapClientTemplate;

    
    public void insert(TagClassifyA record) {
    	sqlMapClientTemplate.insert("tagClassifyA.insert", record);
    }

    
    public int updateByPrimaryKeySelective(TagClassifyA record) {
        int rows = sqlMapClientTemplate.update("tagClassifyA.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public TagClassifyA selectByPrimaryKey(Integer id) {
        TagClassifyA record = (TagClassifyA) sqlMapClientTemplate.queryForObject("tagClassifyA.selectByPrimaryKey", id);
        return record;
    }

    
    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("tagClassifyA.deleteByPrimaryKey", id);
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TagClassifyA> selectTagClassifyA(TagClassifyA queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("tagClassifyA.selectTagClassifyA", queryCon);
	}
	/**
	 * 描述：查询商户的分类一标签
	 * @param merchantId
	 * @return List<TagClassifyA>
	 */
	@Override
	public List<TagClassifyA> getMerchantTagClassifyA(String merchantId){
		return sqlMapClientTemplate.queryForList("tagClassifyA.GET-CLASSA-TAGVALUE-BY-MERCHANTID",merchantId);
	}


	@Override
	public List<TagClassifyA> getAllTagClassifyA() {
		return sqlMapClientTemplate.queryForList("tagClassifyA.getAllTagClassifyA");
	}

	@Override
	public List<TagClassifyA> getAllTagClassifyAByEnabled() {
		return sqlMapClientTemplate.queryForList("tagClassifyA.getAllTagClassifyAByEnabled");
	}
	
    
}