package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientPaymentChannelLogic;
import com.froad.logic.impl.ClientPaymentChannelLogicImpl;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ClientPaymentChannelService;
import com.froad.thrift.vo.ClientPaymentChannelAddVoRes;
import com.froad.thrift.vo.ClientPaymentChannelPageVoRes;
import com.froad.thrift.vo.ClientPaymentChannelVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: ClientPaymentChannelImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ClientPaymentChannelServiceImpl extends BizMonitorBaseService implements ClientPaymentChannelService.Iface {
	private ClientPaymentChannelLogic clientPaymentChannelLogic = new ClientPaymentChannelLogicImpl();
	public ClientPaymentChannelServiceImpl(String name, String version) {
		super(name, version);
	}


    /**
     * 增加 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return long    主键ID
     */
	@Override
	public ClientPaymentChannelAddVoRes addClientPaymentChannel(OriginVo originVo,ClientPaymentChannelVo clientPaymentChannelVo) throws TException {
		
		ClientPaymentChannelAddVoRes voRes = new ClientPaymentChannelAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加客户端支付渠道信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			ClientPaymentChannel clientPaymentChannel =(ClientPaymentChannel)BeanUtil.copyProperties(ClientPaymentChannel.class, clientPaymentChannelVo);
			
			if(Checker.isEmpty(clientPaymentChannel)){
				throw new FroadServerException("添加ClientPaymentChannel失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(clientPaymentChannel.getClientId())){
				throw new FroadServerException("添加ClientPaymentChannel失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(clientPaymentChannel.getName())){
				throw new FroadServerException("添加ClientPaymentChannel失败,原因:Name不能为空!");
			}
			if(Checker.isEmpty(clientPaymentChannel.getFullName())){
				throw new FroadServerException("添加ClientPaymentChannel失败,原因:FullName不能为空!");
			}
			if(Checker.isEmpty(clientPaymentChannel.getType())){
				throw new FroadServerException("添加ClientPaymentChannel失败,原因:Type不能为空!");
			}
			if(Checker.isEmpty(clientPaymentChannel.getIsFroadCheckToken())){
				throw new FroadServerException("添加ClientPaymentChannel失败,原因:IsFroadCheckToken不能为空!");
			}
//			if(Checker.isEmpty(clientPaymentChannel.getPointRate())){
//				throw new FroadServerException("添加ClientPaymentChannel失败,原因:PointRate不能为空!");
//			}
			
			
			String paymentChannelId = clientPaymentChannelLogic.addClientPaymentChannel(clientPaymentChannel);
			if(Checker.isNotEmpty(paymentChannelId)){
				voRes.setPaymentChannelId(paymentChannelId);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加客户端支付渠道失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加客户端支付渠道addClientPaymentChannel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加客户端支付渠道addClientPaymentChannel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}



    /**
     * 删除 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return boolean    
     */
	@Override
	public ResultVo deleteClientPaymentChannel(OriginVo originVo,ClientPaymentChannelVo clientPaymentChannelVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除客户端支付渠道信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			ClientPaymentChannel clientPaymentChannel = (ClientPaymentChannel)BeanUtil.copyProperties(ClientPaymentChannel.class, clientPaymentChannelVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(clientPaymentChannel.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientPaymentChannelLogic.deleteClientPaymentChannel(clientPaymentChannel)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除客户端支付渠道DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除客户端支付渠道deleteClientPaymentChannel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除客户端支付渠道deleteClientPaymentChannel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return boolean    
     */
	@Override
	public ResultVo updateClientPaymentChannel(OriginVo originVo,ClientPaymentChannelVo clientPaymentChannelVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改客户端支付渠道信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			ClientPaymentChannel clientPaymentChannel =(ClientPaymentChannel)BeanUtil.copyProperties(ClientPaymentChannel.class, clientPaymentChannelVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(clientPaymentChannel.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientPaymentChannelLogic.updateClientPaymentChannel(clientPaymentChannel)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改客户端支付渠道DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改客户端支付渠道updateClientPaymentChannel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改客户端支付渠道updateClientPaymentChannel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannelVo>
     */
	@Override
	public List<ClientPaymentChannelVo> getClientPaymentChannel(ClientPaymentChannelVo clientPaymentChannelVo) throws TException {
		ClientPaymentChannel clientPaymentChannel =(ClientPaymentChannel)BeanUtil.copyProperties(ClientPaymentChannel.class, clientPaymentChannelVo);
		
		
		List<ClientPaymentChannel> clientPaymentChannelList = new CommonLogicImpl().findClientPaymentChannel(clientPaymentChannel);
		List<ClientPaymentChannelVo> clientPaymentChannelVoList=(List<ClientPaymentChannelVo>)BeanUtil.copyProperties(ClientPaymentChannelVo.class, clientPaymentChannelList);
		
		
		return clientPaymentChannelVoList==null?new ArrayList<ClientPaymentChannelVo>():clientPaymentChannelVoList;
	}


	 /**
     * 分页查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @return ClientPaymentChannelPageVoRes
     */
	@Override
	public ClientPaymentChannelPageVoRes getClientPaymentChannelByPage(PageVo pageVo, ClientPaymentChannelVo clientPaymentChannelVo) throws TException {
		Page<ClientPaymentChannel> page = null;
		ClientPaymentChannel clientPaymentChannel = null;
		
		page=(Page<ClientPaymentChannel>)BeanUtil.copyProperties(Page.class, pageVo);
		clientPaymentChannel=(ClientPaymentChannel)BeanUtil.copyProperties(ClientPaymentChannel.class, clientPaymentChannelVo);

		page = clientPaymentChannelLogic.findClientPaymentChannelByPage(page, clientPaymentChannel);

		
		List<ClientPaymentChannelVo> clientPaymentChannelVoList = (List<ClientPaymentChannelVo>)BeanUtil.copyProperties(ClientPaymentChannelVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class,page);
		
		
		ClientPaymentChannelPageVoRes clientPaymentChannelPageVoRes = new ClientPaymentChannelPageVoRes();
		clientPaymentChannelPageVoRes.setClientPaymentChannelVoList(clientPaymentChannelVoList);
		clientPaymentChannelPageVoRes.setPage(pageVo);
		
		return clientPaymentChannelPageVoRes;
	}


	/**
     * 根据id查询ClientPaymentChannel
     * @param clientId 客户端id
     * @param paymentChannelId 支付渠道id
     * @return ClientPaymentChannelPageVoRes
     */
	@Override
	public ClientPaymentChannelVo getClientPaymentChannelById(String clientId,String paymentChannelId) throws TException {
		if(Checker.isEmpty(clientId) || Checker.isEmpty(paymentChannelId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ClientPaymentChannelVo();
		}
		ClientPaymentChannel clientPaymentChannel =new CommonLogicImpl().getClientPaymentChannelById(clientId, paymentChannelId);
		ClientPaymentChannelVo clientPaymentChannelVo=(ClientPaymentChannelVo)BeanUtil.copyProperties(ClientPaymentChannelVo.class, clientPaymentChannel);
		
		return clientPaymentChannelVo==null?new ClientPaymentChannelVo():clientPaymentChannelVo;
	}

	/**
     * 根据clientId查询该客户端下支持的支付方式ClientPaymentChannel
     * @param clientId 客户端id
     * @return ClientPaymentChannelPageVoRes
     */
	@Override
	public List<ClientPaymentChannelVo> getClientPaymentChannelByClientId(String clientId) throws TException {
		if(Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<ClientPaymentChannelVo>();
		}
		List<ClientPaymentChannel> clientPaymentChannelList = new CommonLogicImpl().getClientPaymentChannelByClientId(clientId);
		List<ClientPaymentChannelVo> clientPaymentChannelVoList = (List<ClientPaymentChannelVo>)BeanUtil.copyProperties(ClientPaymentChannelVo.class, clientPaymentChannelList);
		
		return clientPaymentChannelVoList==null?new ArrayList<ClientPaymentChannelVo>():clientPaymentChannelVoList;
	}
	

}
