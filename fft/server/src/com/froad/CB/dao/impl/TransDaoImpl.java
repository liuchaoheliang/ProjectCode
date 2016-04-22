package com.froad.CB.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.TransDao;
import com.froad.CB.po.Trans;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class TransDaoImpl implements TransDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addTrans(Trans trans)throws SQLException{
		return (Integer)sqlMapClientTemplate.insert("trans.insert",trans);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("trans.deleteByPrimaryKey",id);
	}

	@Override
	public Trans getTransById(Integer id) {
		return (Trans)sqlMapClientTemplate.queryForObject("trans.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(Trans trans) {
		int rows=sqlMapClientTemplate.update("trans.updateById",trans);
		System.out.println("更新交易，受影响行数为："+rows);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("trans.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<Trans> getTransByBuyerId(String buyerId) {
		return sqlMapClientTemplate.queryForList("trans.getTransByBuyerId",buyerId);
	}

	@Override
	public List<Trans> getTransBySellerId(String sellerId) {
		return sqlMapClientTemplate.queryForList("trans.getTransBySellerId",sellerId);
	}

	@Override
	public Trans getTransByPager(Trans trans) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("trans.getTransByPagerCount",trans);
		List<?> list=sqlMapClientTemplate.queryForList("trans.getTransByPager",trans);
		trans.setTotalCount(totalCount);
		trans.setList(list);
		return trans;
	}
	
	@Override
	public Trans getGroupOrExchangeTransByPager(Trans trans) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("trans.getGroupOrExchangeTransByPagerCount",trans);
		List<?> list=sqlMapClientTemplate.queryForList("trans.getGroupOrExchangeTransByPager",trans);
		trans.setTotalCount(totalCount);
		trans.setList(list);
		return trans;
	}

	@Override
	public void updateBillNo(String billNo,Integer transId) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("billNo", billNo);
		params.put("id", transId);
		sqlMapClientTemplate.update("trans.updateBillNo",params);
	}

	@Override
	public void updatePointBillNo(String pointBillNo,Integer transId) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("pointBillNo", pointBillNo);
		params.put("id", transId);
		sqlMapClientTemplate.update("trans.updatePointBillNo",params);
	}

	@Override
	public void updateLotteryBillNo(String lotteryBillNo, Integer transId) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("lotteryBillNo", lotteryBillNo);
		params.put("id", transId);
		sqlMapClientTemplate.update("trans.updateLotteryBillNo",params);
	}

	@Override
	public Trans getTransByLotteryBillNo(String lotteryBillNo) {
		return (Trans)sqlMapClientTemplate.queryForObject("trans.getTransByLotteryBillNo",lotteryBillNo);
	}

	@Override
	public List<Trans> getTransByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("trans.getTransByMerchantId",merchantId);
	}

	@Override
	public String getTransStateById(Integer transId) {
		return (String)sqlMapClientTemplate.queryForObject("trans.getTransStateById",transId);
	}

	@Override
	public List<Trans> getDeductTrans() {
		return sqlMapClientTemplate.queryForList("trans.getDeductTrans");
	}

	@Override
	public List<Trans> getRebateTrans() {
		return sqlMapClientTemplate.queryForList("trans.getRebateTrans");
	}

	@Override
	public List<Trans> getRefundPointsTrans() {
		return sqlMapClientTemplate.queryForList("trans.getRefundPointsTrans");
	}

	@Override
	public List<Trans> getRefundTrans() {
		return sqlMapClientTemplate.queryForList("trans.getRefundTrans");
	}

	@Override
	public List<Trans> getTimeoutPointsTrans() {
		return sqlMapClientTemplate.queryForList("trans.getTimeoutPointsTrans");
	}

	@Override
	public Trans getExcelDataToExpTrans(Trans trans) {
		List<?> list=sqlMapClientTemplate.queryForList("trans.excelTrans",trans);
		trans.setList(list);
		return trans;
	}
	
	@Override
	public Trans getExcelDataToExpExchangeOrGroup(Trans trans) {
		List<?> list=sqlMapClientTemplate.queryForList("trans.excelGroupOrExchange",trans);
		trans.setList(list);
		return trans;
	}

	@Override
	public void closeTransByIdList(List<Integer> idList) {
		sqlMapClientTemplate.update("trans.closeTransByIdList",idList);
	}

	@Override
	public List<Trans> getAllRefundPointsTrans() {
		return sqlMapClientTemplate.queryForList("trans.getAllRefundPointsTrans");
	}

	@Override
	public Integer closeTimeoutCashTrans() {
		return (Integer)sqlMapClientTemplate.update("trans.closeTimeoutCashTrans");
	}

	@Override
	public List<Integer> getNoNeedCloseTransId() {
		return sqlMapClientTemplate.queryForList("trans.getNoNeedCloseTransId");
	}

	@Override
	public void updateRefundOrderIdById(String refundOrderId, Integer transId) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("refundOrderId", refundOrderId);
		params.put("id", transId);
		sqlMapClientTemplate.update("trans.updateRefundOrderIdById",params);
	}

	@Override
	public Integer closeWithoutPayTrans(List<String> transId) {
		return sqlMapClientTemplate.update("trans.closeWithoutPayTrans",transId);
		
	}

	@Override
	public Trans getTransByPagerFinance(Trans trans) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("trans.getTransByPagerCountFinance",trans);
		List<?> list=sqlMapClientTemplate.queryForList("trans.getTransByPagerFinance",trans);
		trans.setTotalCount(totalCount);
		trans.setList(list);
		return trans;
	}

	@Override
	public Trans getGroupOrExchangeTransByPagerFinance(Trans trans) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("trans.getGroupOrExchangeTransByPagerCountFinance",trans);
		List<?> list=sqlMapClientTemplate.queryForList("trans.getGroupOrExchangeTransByPagerFinance",trans);
		trans.setTotalCount(totalCount);
		trans.setList(list);
		return trans;
	}

	@Override
	public void batchSuccessStateById(final List<Integer> idList) {
		sqlMapClientTemplate.execute(new SqlMapClientCallback(){

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0; i < idList.size(); i++) {
					executor.update("trans.updateStateSuccessById", idList.get(i));
				}
				executor.executeBatch();
				return null;
			}
			
		});
		
	}

	@Override
	public List<Trans> queryPresellTransByGoodsRackId(Integer goodsRackId) {
		return sqlMapClientTemplate.queryForList("trans.queryPresellTransByGoodsRackId",goodsRackId);
	}

	@Override
	public List<Trans> queryPresellTrans() {
		return sqlMapClientTemplate.queryForList("trans.queryPresellTrans");
	}

	@Override
	public List<Trans> getPresellRefundTrans() {
		return sqlMapClientTemplate.queryForList("trans.getPresellRefundTrans");
	}

	@Override
	public List<Trans> getPresellRefundPontsTrans() {
		return sqlMapClientTemplate.queryForList("trans.getPresellRefundPontsTrans");
	}

	@Override
	public List<Trans> getPresellByRackId(String RackId) {
		return sqlMapClientTemplate.queryForList("trans.getPresellByRackId",RackId);
	}

	@Override
	public Integer closePresellOutOfTimeCash() {
		return sqlMapClientTemplate.update("trans.closePresellOutOfTimeCash");
	}

	@Override
	public List<Trans> getPresellOutOfTimePionts() {
		return sqlMapClientTemplate.queryForList("trans.getPresellOutOfTimePionts");
	}

	@Override
	public Trans getTransByRefundOrderId(String refundOrderId) {
		return (Trans)sqlMapClientTemplate.queryForObject("trans.getTransByRefundOrderId",refundOrderId);
	}

}
