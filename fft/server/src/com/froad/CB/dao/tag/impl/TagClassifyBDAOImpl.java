package com.froad.CB.dao.tag.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.tag.TagClassifyBDAO;
import com.froad.CB.po.tag.TagClassifyB;

public class TagClassifyBDAOImpl  implements TagClassifyBDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public TagClassifyBDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }

    
    public void insert(TagClassifyB record) {
    	sqlMapClientTemplate.insert("tagClassifyB.insert", record);
    }

    
    public int updateByPrimaryKeySelective(TagClassifyB record) {
        int rows = sqlMapClientTemplate.update("tagClassifyB.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public TagClassifyB selectByPrimaryKey(Integer id) {
        TagClassifyB record = (TagClassifyB) sqlMapClientTemplate.queryForObject("tagClassifyB.selectByPrimaryKey", id);
        return record;
    }

    
    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("tagClassifyB.deleteByPrimaryKey", id);
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TagClassifyB> selectTagClassifyB(TagClassifyB queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("tagClassifyB.selectTagClassifyB", queryCon);
	}
    
	/**
	 * 描述：查询商户的分类二标签
	 * @param merchantId
	 * @return List<TagClassifyB>
	 */
	@Override
	public List<TagClassifyB> getMerchantTagClassifyB(String merchantId){
		return sqlMapClientTemplate.queryForList("tagClassifyB.GET-CLASSB-TAGVALUE-BY-MERCHANTID",merchantId);
	}

	@Override
	public List<TagClassifyB> getAllTagClassifyBByEnabled() {
		return sqlMapClientTemplate.queryForList("tagClassifyB.getAllTagClassifyB");
	}

	@Override
	public List<TagClassifyB> getAllTagClassifyB() {
		return sqlMapClientTemplate.queryForList("tagClassifyB.getAllTagClassifyBByEnabled");
	}
    
}