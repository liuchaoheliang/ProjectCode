package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.AdvertDao;
import com.froad.CB.po.Advert;

public class AdvertDaoImpl  implements AdvertDao {
	private SqlMapClientTemplate sqlMapClientTemplate;

    public Integer insert(Advert record) {
    	return (Integer) sqlMapClientTemplate.insert("advert.insert", record);
    }

    public boolean updateByPrimaryKeySelective(Advert record) {
    	boolean result=false;
    	if(record==null||record.getId()==null)
    		return result;
        int rows = sqlMapClientTemplate.update("advert.updateByPrimaryKeySelective", record);
        if(rows>0)
            result=true;
        return result;
    }

    public Advert selectByPrimaryKey(Integer id) {
        return (Advert) sqlMapClientTemplate.queryForObject("advert.selectByPrimaryKey", id);
    }

    public boolean deleteByPrimaryKey(Integer id) {
    	boolean result=false;
    	if(id==null)
    		return result;
        int rows = sqlMapClientTemplate.delete("advert.deleteByPrimaryKey", id);
        if(rows>0)
            result=true;
        return result;
    }

	@Override
	public List<Advert> selectAdverts(Advert queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("advert.selectAdverts", queryCon);
	}
	
	@Override
	public Advert getAdvertByPager(Advert pager) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("advert.getAdvertsByPagerCount",pager);
		List<Advert> list=sqlMapClientTemplate.queryForList("advert.getAdvertsByPager", pager);
		
		pager.setTotalCount(totalCount);
		pager.setList(list);
		return pager;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}