package com.froad.CB.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.BuyerChannelDao;
import com.froad.CB.po.BuyerChannel;


	/**
	 * 类描述：买家资金渠道dao实现类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 2, 2013 4:59:00 PM 
	 */
public class BuyerChannelDaoImpl implements BuyerChannelDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(BuyerChannel buyerChannel) {
    	return (Integer) sqlMapClientTemplate.insert("buyerChannel.addBuyerChannel", buyerChannel);
	}

	@Override
	public Integer updateById(BuyerChannel buyerChannel) {
        Integer rows = sqlMapClientTemplate.update("buyerChannel.updateBuyerChannelById", buyerChannel);
        return rows;
	}

	@Override
	public BuyerChannel selectByPrimaryKey(Integer id) {
        return (BuyerChannel) sqlMapClientTemplate.queryForObject("buyerChannel.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		BuyerChannel key = new BuyerChannel();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("buyerChannel.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		BuyerChannel key = new BuyerChannel();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("buyerChannel.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public BuyerChannel getBuyerChannelByUserId(String userId) {
		BuyerChannel buyerChannel = (BuyerChannel)sqlMapClientTemplate.queryForObject("buyerChannel.getBuyerChannelByUserId",userId);
		return buyerChannel;
	}

	@Override
	public BuyerChannel getBuyerChannelByBuyerId(
			String buyerId) {
		BuyerChannel buyerChannel = (BuyerChannel)sqlMapClientTemplate.queryForObject("buyerChannel.getBuyerChannelByBuyerId",buyerId);
		return buyerChannel;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void initAllDefault() {
		sqlMapClientTemplate.update("buyerChannel.initAllDefault");
	}

	@Override
	public void setDefault(Integer id) {
		sqlMapClientTemplate.update("buyerChannel.setDefault",id);
	}

	@Override
	public int updateChannelByBuyerId(BuyerChannel buyerChannel) {
		return sqlMapClientTemplate.update("buyerChannel.updateChannelByBuyerId",buyerChannel);
	}
	
}
