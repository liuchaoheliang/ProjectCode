package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientMapper;
import com.froad.db.redis.ClientRedis;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientLogic;
import com.froad.po.Client;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: ClientLogic.java</p>
 * <p>Description: 描述 </p> 客户端表信息处理Logic
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ClientLogicImpl implements ClientLogic {


    /**
     * 增加 Client客户端信息
     * @param client
     * @return Long    主键ID
     */
	@Override
	public String addClient(Client client)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		ClientMapper clientMapper = null;
		String clientId="";
		try { 
			//验证clientId是否已存在
			Client filter=new CommonLogicImpl().getClientById(client.getClientId());
			if(Checker.isNotEmpty(filter)){
				throw new FroadServerException(client.getClientId()+"客户端名称已存在！");
			}
			
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMapper = sqlSession.getMapper(ClientMapper.class);

			
			client.setCreateTime(new Date());//获得系统时间
			clientMapper.addClient(client); 
			
			
			/**********************操作Redis缓存**********************/
			//缓存客户端信息
			ClientRedis.set_cbbank_client_client_id(client);
			
			sqlSession.commit(true); 
			clientId=client.getClientId();
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return clientId; 

	}


    /**
     * 删除 Client-不对外提供
     * @param client
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteClient(Client client) {

		return true;
//		Boolean result = false; 
//		SqlSession sqlSession = null;
//		ClientMapper clientMapper = null;
//		try { 
//			//控制id不能为null
//			if(client.getId()==null){
//				LogCvt.error("删除client主键id不能为空");
//				return result;
//			}
//			/**********************操作MySQL数据库**********************/
//			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//			clientMapper = sqlSession.getMapper(ClientMapper.class);
//
//			result = clientMapper.deleteClient(client); 
//			
//			/**********************操作Redis缓存**********************/
//			result=ClientRedis.del_cbbank_client_client_id(client);
//
//			if(result){
//				sqlSession.commit(true);
//			}
//			
//		} catch (Exception e) { 
//			sqlSession.rollback(true);  
//			LogCvt.error("删除Client失败，原因:" + e.getMessage(), e); 
//		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
//		} 
//		return result; 

	}


    /**
     * 根据clientId修改 Client
     * @param client
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateClient(Client client)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientMapper clientMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMapper = sqlSession.getMapper(ClientMapper.class);

			result = clientMapper.updateClient(client); 
			//操作成功，先提交mysq数据，后续可查最新数据set到缓存中
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Redis缓存**********************/
			// 先删除key
			ClientRedis.del_cbbank_client_client_id(client);
			client=new CommonLogicImpl().getClientById(client.getClientId());
			if(Checker.isNotEmpty(client)){
				//缓存客户端信息
				result=ClientRedis.set_cbbank_client_client_id(client);
			}
			
			LogCvt.info("修改客户端信息client完成！");
				
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
    /**
     * 查询 Client
     * @param client
     * @return List<Client>    结果集合 
     */
	@Override
	public List<Client> findClient(Client client) {

		SqlSession sqlSession = null;
		ClientMapper clientMapper = null;
		List<Client> clients = new ArrayList<Client>();
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMapper = sqlSession.getMapper(ClientMapper.class);

			clients = clientMapper.findClient(client); 
			
		} catch (Exception e) { 
			LogCvt.error("查询Client失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return clients; 

	}


    /**
     * 分页查询 Client
     * @param page
     * @param client
     * @return List<Client>    结果集合 
     */
	@Override
	public Page<Client> findClientByPage(Page<Client> page, Client client) {

		List<Client> result = new ArrayList<Client>(); 
		SqlSession sqlSession = null;
		ClientMapper clientMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMapper = sqlSession.getMapper(ClientMapper.class);

			result = clientMapper.findByPage(page, client); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询Client失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	


}
