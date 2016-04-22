package com.froad.CB.dao.tag.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.tag.TagDistrictADAO;
import com.froad.CB.po.tag.TagClassifyB;
import com.froad.CB.po.tag.TagDistrictA;

public class TagDistrictADAOImpl  implements TagDistrictADAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
    public void insert(TagDistrictA record) {
    	sqlMapClientTemplate.insert("tagDistrictA.insert", record);
    }
    
    public int updateByPrimaryKeySelective(TagDistrictA record) {
        int rows = sqlMapClientTemplate.update("tagDistrictA.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public TagDistrictA selectByPrimaryKey(Integer id) {
        TagDistrictA record = (TagDistrictA) sqlMapClientTemplate.queryForObject("tagDistrictA.selectByPrimaryKey", id);
        return record;
    }

    
    public int deleteByPrimaryKey(Integer id) {
        TagDistrictA key = new TagDistrictA();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("tagDistrictA.deleteByPrimaryKey", key);
        return rows;
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TagDistrictA> selectTagDistrictA(TagDistrictA queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("tagDistrictA.selectTagDistrictA", queryCon);
	}
    
	/**
	 * 描述：查询商户的商圈标签
	 * @param merchantId
	 * @return List<TagDistrictA>
	 */
	public List<TagDistrictA> getMerchantDistrictA(String merchantId){
		return sqlMapClientTemplate.queryForList("tagDistrictA.GET-DISTRICTA-TAGVALUE-BY-MERCHANTID",merchantId);
	}

	@Override
	public List<TagDistrictA> getAllTagDistrictA() {
		return sqlMapClientTemplate.queryForList("tagDistrictA.getAllTagDistrictA");
	}

	@Override
	public List<TagDistrictA> getAllTagDistrictAByEnabled() {
		return sqlMapClientTemplate.queryForList("tagDistrictA.getAllTagDistrictAByEnabled");
	}
    
    
}