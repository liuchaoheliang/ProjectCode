package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientPaymentChannelMapper;
import com.froad.db.redis.ClientPaymentChannelRedis;
import com.froad.enums.ModuleID;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientPaymentChannelLogic;
import com.froad.po.ClientPaymentChannel;
import com.froad.util.Checker;
import com.froad.util.SimpleID;

/**
 * 
 * <p>@Title: ClientPaymentChannelLogic.java</p>
 * <p>Description: 描述 </p> 客户端支付渠道管理Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ClientPaymentChannelLogicImpl implements ClientPaymentChannelLogic {

    /**
     * 增加 ClientPaymentChannel客户端支付渠道
     * @param clientPaymentChannel
     * @return Long    主键ID
     */
	@Override
	public String addClientPaymentChannel(ClientPaymentChannel clientPaymentChannel)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		ClientPaymentChannelMapper clientPaymentChannelMapper = null;
		String clientPaymentChannelId="";
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientPaymentChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);

			//设置支付渠道id
			clientPaymentChannel.setPaymentChannelId(new SimpleID(ModuleID.paymentchannel).nextId());
			//设置是否删除
			clientPaymentChannel.setIsDelete(false);
			clientPaymentChannelMapper.addClientPaymentChannel(clientPaymentChannel); 
			
			
			/**********************操作Redis缓存**********************/
			ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(clientPaymentChannel);
			ClientPaymentChannelRedis.set_cbbank_client_channels_client_id(clientPaymentChannel);
			
			clientPaymentChannelId=clientPaymentChannel.getPaymentChannelId();
			sqlSession.commit(true); 
			
			
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
		return clientPaymentChannelId; 

	}


    /**
     * 删除 ClientPaymentChannel
     * @param clientPaymentChannel
     * @描述：将is_delte改为1 并移除2个key
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteClientPaymentChannel(ClientPaymentChannel clientPaymentChannel)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientPaymentChannelMapper clientPaymentChannelMapper = null;
		try { 
			
			/**********************操作Redis缓存**********************/
			//根据主键id查出改动前的支付渠道对象
			ClientPaymentChannel find = new ClientPaymentChannel();
			find.setId(clientPaymentChannel.getId());
			List<ClientPaymentChannel> findList = new CommonLogicImpl().findClientPaymentChannel(find);
			if(Checker.isNotEmpty(findList)){
				ClientPaymentChannel oldClientPaymentChannel = findList.get(0);
				//重新设置缓存
				result = ClientPaymentChannelRedis.del_cbbank_client_channel_client_id_payment_channel_id(oldClientPaymentChannel);
				result = ClientPaymentChannelRedis.srem_cbbank_client_channels_client_id(oldClientPaymentChannel);

			}
			
				
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientPaymentChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);
			//删除数据-更新is_delete改为1
			result = clientPaymentChannelMapper.deleteClientPaymentChannel(clientPaymentChannel); 
			if(result){
				sqlSession.commit(true);
			}else { // 删除失败
				sqlSession.rollback(true); 
			}
			
			
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
     * 修改 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateClientPaymentChannel(ClientPaymentChannel clientPaymentChannel)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientPaymentChannelMapper clientPaymentChannelMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientPaymentChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);

			result = clientPaymentChannelMapper.updateClientPaymentChannel(clientPaymentChannel); 
			
			//操作成功，先提交mysq数据，后续可查最新数据set到缓存中
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Redis缓存**********************/
			ClientPaymentChannel find = new ClientPaymentChannel();
			find.setId(clientPaymentChannel.getId());
			//根据主键过滤查询出支付渠道对象，将最新修改过后的数据重新set到缓存中
			List<ClientPaymentChannel> findList = new CommonLogicImpl().findClientPaymentChannel(find);
			if(Checker.isNotEmpty(findList)){
				clientPaymentChannel = findList.get(0);
				//缓存客户端支付渠道信息
				result=ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(clientPaymentChannel);
			}
			
			

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
     * 分页查询 ClientPaymentChannel
     * @param page
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannel>    结果集合 
     */
	@Override
	public Page<ClientPaymentChannel> findClientPaymentChannelByPage(Page<ClientPaymentChannel> page, ClientPaymentChannel clientPaymentChannel) {

		List<ClientPaymentChannel> result = new ArrayList<ClientPaymentChannel>(); 
		SqlSession sqlSession = null;
		ClientPaymentChannelMapper clientPaymentChannelMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientPaymentChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);

			result = clientPaymentChannelMapper.findByPage(page, clientPaymentChannel); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询ClientPaymentChannel失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}



}