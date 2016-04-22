package com.froad.CB.dao.tag.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.tag.TagMAPDAO;
import com.froad.CB.po.tag.TagMAP;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class TagMAPDAOImpl  implements TagMAPDAO {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
    
    
    public Integer insert(TagMAP record) {
    	return (Integer)sqlMapClientTemplate.insert("tagMAP.insert", record);
    }

    
    public void updateById(TagMAP record) {
        sqlMapClientTemplate.update("tagMAP.updateById", record);
    }

    
    public TagMAP selectByPrimaryKey(Integer id) {
        return (TagMAP) sqlMapClientTemplate.queryForObject("tagMAP.selectByPrimaryKey", id);
    }

    
    public void deleteByPrimaryKey(Integer id) {
        sqlMapClientTemplate.delete("tagMAP.deleteByPrimaryKey", id);
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TagMAP> selectTagMAPs(TagMAP queryCon) {
		return sqlMapClientTemplate.queryForList("tagMAP.selectTagMAPs", queryCon);
	}

	@Override
	public List<TagMAP> selectTagsByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("tagMAP.selectTagsByMerchantId", merchantId);
	}


	@Override
	public List<TagMAP> getTagMapsByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("tagMAP.getTagMapsByMerchantId",merchantId);
	}


	@Override
	public List<TagMAP> getTagMapsByMerchantName(String merchantName) {
		return sqlMapClientTemplate.queryForList("tagMAP.getTagMapsByMerchantName",merchantName);

	}


	@Override
	public List<TagMAP> getTopTagMaps() {
		return sqlMapClientTemplate.queryForList("tagMAP.getTopTagMaps");
	}
    
	public void insertBatch(final List tagMAP){
		sqlMapClientTemplate.execute(new SqlMapClientCallback() {
			
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				System.out.println(tagMAP.size());
				for (int i = 0; i < tagMAP.size(); i++) {
					executor.insert("tagMAP.insert",tagMAP.get(i));
				}
				executor.executeBatch();
				return null;
			}
			
		});
	}
    
}