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
 * @Title: SmsContentLogicImpl.java
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
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.db.mysql.mapper.SmsContentMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.SmsContentLogic;
import com.froad.po.SmsContent;

/**
 * 
 * <p>@Title: SmsContentLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class SmsContentLogicImpl implements SmsContentLogic {


    /**
     * 增加 SmsContent
     * @param smsContent
     * @return Long    主键ID
     */
	@Override
	public ResultBean addSmsContent(SmsContent smsContent) {

		Long result = null; 
		ResultBean resultBean=new ResultBean(ResultCode.success,"增加短信模板信息成功");
		SqlSession sqlSession = null;
		SmsContentMapper smsContentMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsContentMapper = sqlSession.getMapper(SmsContentMapper.class);

			if (smsContentMapper.addSmsContent(smsContent) > -1) { 
				result = smsContent.getId(); 
			}
			sqlSession.commit(true); 

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			SupportsRedis.set_cbbank_sms_template_client_id_sms_type(smsContent);
			resultBean= new ResultBean(ResultCode.success,"添加短信模板信息成功",result);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			resultBean= new ResultBean(ResultCode.failed,"添加短信模板信息失败");
			LogCvt.error("添加SmsContent失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 删除 SmsContent
     * @param smsContent
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteSmsContent(SmsContent smsContent) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		SmsContentMapper smsContentMapper = null;
		ResultBean resultBean=new ResultBean(ResultCode.success,"删除SmsContent信息成功");
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsContentMapper = sqlSession.getMapper(SmsContentMapper.class);

			Long id = smsContent.getId();
			if( id == null || id > 0 ){
				resultBean= new ResultBean(ResultCode.failed,"删除SmsContent信息失败");
				return resultBean;
			}
			
			result = smsContentMapper.deleteSmsContent(smsContent); 
			if(result){

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
				smsContent = smsContentMapper.findSmsContentById(smsContent.getId());
				if( smsContent != null ){
					SupportsRedis.set_cbbank_sms_template_client_id_sms_type(smsContent.getClientId(),smsContent.getSmsType(),"is_enable", "false");
				}
			}
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			resultBean= new ResultBean(ResultCode.failed,"删除SmsContent信息失败");
			LogCvt.error("删除SmsContent失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 修改 SmsContent
     * @param smsContent
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateSmsContent(SmsContent smsContent) {
		ResultBean resultBean=new ResultBean(ResultCode.success,"修改短信模板信息成功");
		Boolean result = null; 
		SqlSession sqlSession = null;
		SmsContentMapper smsContentMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsContentMapper = sqlSession.getMapper(SmsContentMapper.class);

			result = smsContentMapper.updateSmsContent(smsContent);
			if(result){

			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			SupportsRedis.set_cbbank_sms_template_client_id_sms_type(smsContent);
			}
			sqlSession.commit(true);
		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			resultBean=new ResultBean(ResultCode.failed,"修改短信模板信息成功");
			LogCvt.error("修改SmsContent失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultBean; 

	}


    /**
     * 查询 SmsContent
     * @param smsContent
     * @return List<SmsContent>    结果集合 
     */
	@Override
	public List<SmsContent> findSmsContent(SmsContent smsContent) {

		List<SmsContent> result = new ArrayList<SmsContent>(); 
		SqlSession sqlSession = null;
		SmsContent sms = new SmsContent();
		SmsContentMapper smsContentMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
//			Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_sms_template_client_id_sms_type(smsContent.getClientId(), smsContent.getSmsType());
//			if(operatorRedis!=null){
//				sms.setChannel(operatorRedis.get("channel"));
//				sms.setContent(operatorRedis.get("content"));
//				sms.setIsEnable(BooleanUtils.toBooleanObject(operatorRedis.get("is_enable")));
//				sms.setMsgSuffix(operatorRedis.get("msg_suffix"));
//				sms.setSmsType(Integer.parseInt(operatorRedis.get("sms_type")));
//				sms.setValidTime(Integer.parseInt(operatorRedis.get("valid_time")));
//				result.add(sms);
//			}else{
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				smsContentMapper = sqlSession.getMapper(SmsContentMapper.class);
				
				result = smsContentMapper.findSmsContent(smsContent); 
				for(SmsContent sc : result){
					SupportsRedis.set_cbbank_sms_template_client_id_sms_type(sc);
				}
//			}
			
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询SmsContent失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 SmsContent
     * @param page
     * @param smsContent
     * @return Page<SmsContent>    结果集合 
     */
	@Override
	public Page<SmsContent> findSmsContentByPage(Page<SmsContent> page, SmsContent smsContent) {

		List<SmsContent> result = new ArrayList<SmsContent>(); 
		SqlSession sqlSession = null;
		SmsContentMapper smsContentMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			smsContentMapper = sqlSession.getMapper(SmsContentMapper.class);

			result = smsContentMapper.findByPage(page, smsContent); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询SmsContent失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
     * 查询 SmsContent
     * @return SmsContentVo
     * 
     * @param clientId
     * @param smsType
     */
	@Override
	public SmsContent getSmsContentByClientIdAndType(String clientId,
			int smsType) {
		// TODO Auto-generated method stub
		
		SmsContent result = null;
		SqlSession sqlSession = null;
		SmsContentMapper smsContentMapper = null;
		try{
			
			Map<String, String> map = SupportsRedis.get_cbbank_sms_template_client_id_sms_type(clientId,smsType);
			
			if( map != null ){
				
				result = new SmsContent();
				result.setContent(map.get("content"));
				result.setMsgSuffix(map.get("msg_suffix"));
				result.setChannel(map.get("channel"));
				String type = map.get("sms_type");
				result.setSmsType(type!=null?Integer.parseInt(type):0);
				String time = map.get("valid_time");
				result.setValidTime(time!=null?Integer.parseInt(time):0);
				String enable = map.get("is_enable");
				result.setIsEnable(enable!=null?"true".equals(enable):false);
			}else{
				
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				smsContentMapper = sqlSession.getMapper(SmsContentMapper.class);
				
				result = smsContentMapper.findSmsContentByClientIdAndType(clientId, smsType);
				
				if( result == null ){
					result = new SmsContent();
				}else{
					SupportsRedis.set_cbbank_sms_template_client_id_sms_type(result);
				}
			}
			
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("查询SmsContent失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}


}