package com.froad.CB.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.SellerChannelDao;
import com.froad.CB.po.SellerChannel;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 */
public class SellerChannelDaoImpl implements SellerChannelDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(SellerChannel sellerChannel) {
    	return (Integer) sqlMapClientTemplate.insert("sellerChannel.addSellerChannel", sellerChannel);
	}

	@Override
	public Integer updateById(SellerChannel sellerChannel) {
        Integer rows = sqlMapClientTemplate.update("sellerChannel.updateSellerChannelById", sellerChannel);
        return rows;
	}

	@Override
	public SellerChannel selectByPrimaryKey(Integer id) {
        return (SellerChannel) sqlMapClientTemplate.queryForObject("sellerChannel.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		SellerChannel key = new SellerChannel();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("sellerChannel.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		SellerChannel key = new SellerChannel();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("sellerChannel.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public SellerChannel getSellerChannelByUserId(String userId) {
		SellerChannel sellerChannel = (SellerChannel)sqlMapClientTemplate.queryForObject("sellerChannel.getSellerChannelByUserId",userId);
		return sellerChannel;
	}

	@Override
	public List<SellerChannel> getSellerChannelByMerchantId(
			String merchantId) {
		return sqlMapClientTemplate.queryForList("sellerChannel.getSellerChannelByMerchantId",merchantId);
	}
	
	@Override
	public SellerChannel getSellerChannelBySellerId(
			String sellerId) {
		SellerChannel sellerChannel = (SellerChannel)sqlMapClientTemplate.queryForObject("sellerChannel.getSellerChannelBySellerId",sellerId);
		return sellerChannel;
	}
	
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void batchInsert(final List<SellerChannel> sellerChannelList) {
		sqlMapClientTemplate.execute(new SqlMapClientCallback(){

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0; i <sellerChannelList.size(); i++) {
					executor.insert("sellerChannel.addSellerChannel",sellerChannelList.get(i));
				}
				executor.executeBatch();
				return null;
			}
			
		});
	}
	
}
