package com.froad.logic.impl;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantMonthCountMapper;
import com.froad.db.mysql.mapper.ProductMonthCountMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantMonthCountLogic;
import com.froad.po.MerchantMonthCount;
import com.froad.po.ProductMonthCount;
import com.froad.util.Arith;

public class MerchantMonthCountLogicImpl implements MerchantMonthCountLogic {

	/**
     * 查询 MerchantMonthCount
     * @param merchantMonthCount
     * @return MerchantMonthCount
     */
	@Override
	public MerchantMonthCount getOutletCommentById(
			MerchantMonthCount merchantMonthCount) {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession = null;
		MerchantMonthCountMapper merchantMapper = null;
		ProductMonthCountMapper productMapper = null;
		
		try{
			
			// 获取mysql操作
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMonthCountMapper.class);
			
			// 获取商户月销售统计
			merchantMonthCount = merchantMapper.findMerchantMonthCount(merchantMonthCount);
			LogCvt.debug("查询merchantMonthCount结果:"+JSON.toJSONString(merchantMonthCount));
			if( merchantMonthCount != null ){
				
				Double monthMoney = merchantMonthCount.getMonthMoney();
				if( monthMoney != null && monthMoney.intValue() != 0 ){
					monthMoney = Arith.div(monthMoney, 1000);
					merchantMonthCount.setMonthMoney(monthMoney);
				}
				
				Double groupOrderMoney = merchantMonthCount.getGroupOrderMoney();
				if( groupOrderMoney != null && groupOrderMoney.intValue() != 0 ){
					groupOrderMoney = Arith.div(groupOrderMoney, 1000);
					merchantMonthCount.setGroupOrderMoney(groupOrderMoney);
				}
				
				Double sellOrderMoney = merchantMonthCount.getSellOrderMoney();
				if( sellOrderMoney != null && sellOrderMoney.intValue() != 0 ){
					sellOrderMoney = Arith.div(sellOrderMoney, 1000);
					merchantMonthCount.setSellOrderMoney(sellOrderMoney);
				}
				
				Double faceOrderMoney = merchantMonthCount.getFaceOrderMoney();
				if( faceOrderMoney != null && faceOrderMoney.intValue() != 0 ){
					faceOrderMoney = Arith.div(faceOrderMoney, 1000);
					merchantMonthCount.setFaceOrderMoney(faceOrderMoney);
				}
				
				// 获取商品月销售统计
				productMapper = sqlSession.getMapper(ProductMonthCountMapper.class);
				ProductMonthCount productMonthCount =  productMapper.findProductMonthCountOfSellCountMax(merchantMonthCount);
				LogCvt.debug("查询productMonthCount结果:"+JSON.toJSONString(productMonthCount));
				if( productMonthCount != null ){
					merchantMonthCount.setProductId(productMonthCount.getProductId());
					merchantMonthCount.setMaxCount(productMonthCount.getMaxCount());
					merchantMonthCount.setProductName(productMonthCount.getProductName());
					Double maxMoney = productMonthCount.getMaxMoney();
					if( maxMoney != null && maxMoney.intValue() != 0 ){
						maxMoney = Arith.div(maxMoney, 1000);
						merchantMonthCount.setMaxMoney(maxMoney);
					}
				}
			}
			
			
			
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);  
			LogCvt.error("查询MerchantMonthCount失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return merchantMonthCount;
	}

}
