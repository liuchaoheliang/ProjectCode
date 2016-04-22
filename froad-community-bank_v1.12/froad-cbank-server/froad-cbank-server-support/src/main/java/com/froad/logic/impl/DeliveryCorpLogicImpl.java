/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: DeliveryCorpLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.DeliveryCorpMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.DeliveryCorpLogic;
import com.froad.po.DeliveryCorp;

/**
 * 
 * <p>@Title: DeliveryCorpLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class DeliveryCorpLogicImpl implements DeliveryCorpLogic {


    /**
     * 增加 DeliveryCorp
     * @param deliveryCorp
     * @return Long    主键ID
     */
	@Override
	public ResultBean addDeliveryCorp(DeliveryCorp deliveryCorp) {

		ResultBean resultBean=new ResultBean(ResultCode.success,"增加物流信息成功");
		Long result = null; 
		SqlSession sqlSession = null;
		DeliveryCorpMapper deliveryCorpMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryCorpMapper = sqlSession.getMapper(DeliveryCorpMapper.class);
			//deliveryCorp.setCorpCode("");//物流新增字段调整
			if (deliveryCorpMapper.addDeliveryCorp(deliveryCorp) > -1) { 
				result = deliveryCorp.getId(); 
			}
			 

			/**********************操作Mongodb数据库**********************/
			if(result > 0)
			/**********************操作Redis缓存**********************/
			SupportsRedis.set_deliver_company_client_id_delivery_corp_id(deliveryCorp);
			sqlSession.commit(true);
			resultBean= new ResultBean(ResultCode.success,"添加物流信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			resultBean= new ResultBean(ResultCode.failed,"添加DeliveryCorp信息失败");
			LogCvt.error("添加DeliveryCorp失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 DeliveryCorp
     * @param deliveryCorp
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteDeliveryCorp(DeliveryCorp deliveryCorp) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除物流信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		DeliveryCorpMapper deliveryCorpMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryCorpMapper = sqlSession.getMapper(DeliveryCorpMapper.class);

			result = deliveryCorpMapper.deleteDeliveryCorp(deliveryCorp); 
			if(result)
			{

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			SupportsRedis.set_deliver_company_client_id_delivery_corp_id(deliveryCorp.getCorpCode(),deliveryCorp.getId(),"is_enable", "false");
			}
			sqlSession.commit(true);
			
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			resultBean= new ResultBean(ResultCode.failed,"删除物流失败");
			LogCvt.error("删除DeliveryCorp失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
//		return result;
		return resultBean;

	}


    /**
     * 修改 DeliveryCorp
     * @param deliveryCorp
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateDeliveryCorp(DeliveryCorp deliveryCorp) {
		ResultBean resultBean=null;
		Boolean result = null; 
		SqlSession sqlSession = null;
		DeliveryCorpMapper deliveryCorpMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryCorpMapper = sqlSession.getMapper(DeliveryCorpMapper.class);

			result = deliveryCorpMapper.updateDeliveryCorp(deliveryCorp); 
			if(result)
			{
			/**********************操作Mongodb数据库**********************/
			resultBean=	new ResultBean(ResultCode.success,"修改DeliveryCorp信息成功");
			/**********************操作Redis缓存**********************/
			SupportsRedis.set_deliver_company_client_id_delivery_corp_id(deliveryCorp);
			}else{
			resultBean=	new ResultBean(ResultCode.failed,"修改DeliveryCorp信息失败");	
			}
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);
			result = false; 
			resultBean= new ResultBean(ResultCode.failed,"修改DeliveryCorp失败");
			LogCvt.error("修改DeliveryCorp失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 DeliveryCorp
     * @param deliveryCorp
     * @return List<DeliveryCorp>    结果集合 
     */
	@Override
	public List<DeliveryCorp> findDeliveryCorp(DeliveryCorp deliveryCorp) {

		List<DeliveryCorp> result = new ArrayList<DeliveryCorp>(); 
		SqlSession sqlSession = null;
		DeliveryCorp delivery = new DeliveryCorp();
		DeliveryCorpMapper deliveryCorpMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
			Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_deliver_company_client_id_delivery_id(deliveryCorp.getCorpCode(), deliveryCorp.getId());
			if(operatorRedis!=null){
				delivery.setIsEnable(BooleanUtils.toBooleanObject(operatorRedis.get("is_enable")));
				delivery.setName(operatorRedis.get("name"));
				delivery.setOrderValue(Integer.parseInt(operatorRedis.get("orderValue")));
				delivery.setUrl(operatorRedis.get("url"));
				delivery.setCorpCode(operatorRedis.get("corp_code"));
				result.add(delivery);
			}else{
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				deliveryCorpMapper = sqlSession.getMapper(DeliveryCorpMapper.class);
				result = deliveryCorpMapper.findDeliveryCorp(deliveryCorp);
				for(DeliveryCorp dc : result){
					SupportsRedis.set_deliver_company_client_id_delivery_corp_id(dc);
				}
				// sqlSession.commit(true);
			}

		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询DeliveryCorp失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 DeliveryCorp
     * @param page
     * @param deliveryCorp
     * @return Page<DeliveryCorp>    结果集合 
     */
	@Override
	public Page<DeliveryCorp> findDeliveryCorpByPage(Page<DeliveryCorp> page, DeliveryCorp deliveryCorp) {

		List<DeliveryCorp> result = new ArrayList<DeliveryCorp>(); 
		SqlSession sqlSession = null;
		DeliveryCorpMapper deliveryCorpMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			deliveryCorpMapper = sqlSession.getMapper(DeliveryCorpMapper.class);

			result = deliveryCorpMapper.findByPage(page, deliveryCorp); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询DeliveryCorp失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}