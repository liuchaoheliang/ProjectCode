package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientLogic;
import com.froad.logic.impl.ClientLogicImpl;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Client;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.vo.ClientAddVoRes;
import com.froad.thrift.vo.ClientPageVoRes;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: ClientImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ClientServiceImpl extends BizMonitorBaseService implements ClientService.Iface {
	private ClientLogic clientLogic = new ClientLogicImpl();
	public ClientServiceImpl(String name, String version) {
		super(name, version);
	}


    /**
     * 增加 Client
     * @param client
     * @return long    主键ID
     */
	@Override
	public ClientAddVoRes addClient(OriginVo originVo,ClientVo clientVo) throws TException {
		
		ClientAddVoRes voRes = new ClientAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加客户端信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			Client client = (Client)BeanUtil.copyProperties(Client.class, clientVo);
			
			if(Checker.isEmpty(client)){
				throw new FroadServerException("添加Client失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(client.getClientId())){
				throw new FroadServerException("添加Client失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(client.getUri())){
				throw new FroadServerException("添加Client失败,原因:Uri不能为空!");
			}
			if(Checker.isEmpty(client.getName())){
				throw new FroadServerException("添加Client失败,原因:Name不能为空!");
			}
			if(Checker.isEmpty(client.getPointPartnerNo())){
				throw new FroadServerException("添加Client失败,原因:PointPartnerNo不能为空!");
			}
			if(Checker.isEmpty(client.getOpenapiPartnerNo())){
				throw new FroadServerException("添加Client失败,原因:OpenapiPartnerNo不能为空!");
			}
			if(Checker.isEmpty(client.getOrderDisplay())){
				throw new FroadServerException("添加Client失败,原因:OrderDisplay不能为空!");
			}
			if(Checker.isEmpty(client.getBankType())){
				throw new FroadServerException("添加Client失败,原因:BankType不能为空!");
			}
			
			
			String clientId = clientLogic.addClient(client);
			if(Checker.isNotEmpty(clientId)){
				voRes.setClientId(clientId);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加客户端信息失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("添加客户端addClient失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加客户端addClient异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
		
	}



    /**
     * 删除 Client 暂不提供
     * @param client
     * @return boolean    
     */
//	@Override
//	public boolean deleteClient(ClientVo clientVo) throws TException {
//		// TODO Auto-generated method stub
//		LogCvt.info("删除Client");
//		Client client = new Client();
//		try {
//			BeanUtil.copyProperties(client, clientVo);
//		} catch (IllegalAccessException e) {
//			LogCvt.error(e.getMessage());
//		} catch (InvocationTargetException e) {
//			LogCvt.error(e.getMessage());
//		} 
//		Boolean result = clientLogic.deleteClient(client);
//		return null == result ? false : result;
//	}



    /**
     * 修改 Client
     * @param client
     * @return boolean    
     */
	@Override
	public ResultVo updateClient(OriginVo originVo,ClientVo clientVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改客户端信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			Client client = (Client)BeanUtil.copyProperties(Client.class, clientVo);
			
			//验证update和delete时clientId不能为空
			if(Checker.isEmpty(client.getClientId())){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("clientId不能为空");
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientLogic.updateClient(client)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改客户端DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改客户端updateClient失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改客户端updateClient失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
		
	}



    /**
     * 查询 Client
     * @param client
     * @return List<ClientVo>
     */
	@Override
	public List<ClientVo> getClient(ClientVo clientVo) throws TException {
		Client client = (Client)BeanUtil.copyProperties(Client.class, clientVo);
			
		List<Client> clientList = clientLogic.findClient(client);
		
		List<ClientVo> clientVoList =(List<ClientVo>)BeanUtil.copyProperties(ClientVo.class, clientList);
		
		return clientVoList==null?new ArrayList<ClientVo>():clientVoList;
	}


	 /**
     * 分页查询 Client
     * @param client
     * @return ClientPageVoRes
     */
	@Override
	public ClientPageVoRes getClientByPage(PageVo pageVo, ClientVo clientVo) throws TException {
		Page<Client> page = null;
		Client client = null;
		
		page=(Page<Client>)BeanUtil.copyProperties(Page.class, pageVo);
		client=(Client)BeanUtil.copyProperties(Client.class, clientVo);

		page = clientLogic.findClientByPage(page, client);
		
		List<ClientVo> clientVoList = (List<ClientVo>)BeanUtil.copyProperties(ClientVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		ClientPageVoRes clientPageVoRes = new ClientPageVoRes();
		clientPageVoRes.setClientVoList(clientVoList);
		clientPageVoRes.setPage(pageVo);
		
		return clientPageVoRes;
	}


	/**
     * 根据id查询Client
     * @param clientId 客户端id
     * @param paymentChannelId 支付渠道id
     * @return ClientPaymentChannelPageVoRes
     */
	@Override
	public ClientVo getClientById(String clientId) throws TException {
		if(Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ClientVo();
		}
		Client client=new CommonLogicImpl().getClientById(clientId);
		ClientVo clientVo=(ClientVo)BeanUtil.copyProperties(ClientVo.class, client);
		
		return clientVo==null?new ClientVo():clientVo;
	}


}
