package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.FundsChannelDao;
import com.froad.CB.po.FundsChannel;

public class FundsChannelDAOImpl  implements FundsChannelDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

    public void insert(FundsChannel record) {
    	sqlMapClientTemplate.insert("fundsChannel.insert", record);
    }

    public int updateByPrimaryKeySelective(FundsChannel record) {
        int rows = sqlMapClientTemplate.update("fundsChannel.updateById", record);
        return rows;
    }

    public FundsChannel selectByPrimaryKey(Integer id) {
       return (FundsChannel)sqlMapClientTemplate.queryForObject("fundsChannel.selectByPrimaryKey", id);
    }

    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("fundsChannel.deleteByPrimaryKey", id);
    }

	@Override
	public List<FundsChannel> selectFundsChannels(FundsChannel record) {
		return sqlMapClientTemplate.queryForList("fundsChannel.selectFundsChannels", record);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<FundsChannel> getAll() {
		return sqlMapClientTemplate.queryForList("fundsChannel.selectAll");
	}

	@Override
	public boolean isExist(Integer id) {
		Object obj=sqlMapClientTemplate.queryForObject("fundsChannel.isExist",id);
		return obj!=null;
	}
	
	
}