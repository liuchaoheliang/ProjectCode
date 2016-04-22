package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientMerchantAuditLogic;
import com.froad.logic.impl.ClientMerchantAuditLogicImpl;
import com.froad.po.ClientMerchantAudit;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ClientMerchantAuditService;
import com.froad.thrift.vo.ClientMerchantAuditOrgCodeVo;
import com.froad.thrift.vo.ClientMerchantAuditPageVoRes;
import com.froad.thrift.vo.ClientMerchantAuditVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: ClientMerchantAuditImpl.java</p>
 * <p>Description: 描述 </p> 商户审核配置管理
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public class ClientMerchantAuditServiceImpl extends BizMonitorBaseService implements ClientMerchantAuditService.Iface {
	private ClientMerchantAuditLogic clientMerchantAuditLogic = new ClientMerchantAuditLogicImpl();
	public ClientMerchantAuditServiceImpl(String name, String version)
	{
		super(name, version);
	}


    /**
     * 增加 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addClientMerchantAudit(OriginVo originVo,ClientMerchantAuditVo clientMerchantAuditVo) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加商户审核配置信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			ClientMerchantAudit clientMerchantAudit = (ClientMerchantAudit)BeanUtil.copyProperties(ClientMerchantAudit.class, clientMerchantAuditVo);
			
			if(Checker.isEmpty(clientMerchantAudit)){
				throw new FroadServerException("添加ClientMerchantAudit失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(clientMerchantAudit.getClientId())){
				throw new FroadServerException("添加ClientMerchantAudit失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(clientMerchantAudit.getOrgLevel())){
				throw new FroadServerException("添加ClientMerchantAudit失败,原因:OrgLevel不能为空!");
			}
			if(Checker.isEmpty(clientMerchantAudit.getStartOrgLevel())){
				throw new FroadServerException("添加ClientMerchantAudit失败,原因:StartOrgLevel不能为空!");
			}
			if(Checker.isEmpty(clientMerchantAudit.getEndOrgLevel())){
				throw new FroadServerException("添加ClientMerchantAudit失败,原因:EndOrgLevel不能为空!");
			}
			
			
			Long id = clientMerchantAuditLogic.addClientMerchantAudit(clientMerchantAudit);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商户审核配置失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加商户审核配置addClientMerchantAudit失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加商户审核配置addClientMerchantAudit异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}



    /**
     * 删除 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return boolean    
     */
	@Override
	public ResultVo deleteClientMerchantAudit(OriginVo originVo,ClientMerchantAuditVo clientMerchantAuditVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除商户审核配置信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			ClientMerchantAudit clientMerchantAudit = (ClientMerchantAudit)BeanUtil.copyProperties(ClientMerchantAudit.class, clientMerchantAuditVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(clientMerchantAudit.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientMerchantAuditLogic.deleteClientMerchantAudit(clientMerchantAudit)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除商户审核配置DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除商户审核配置deleteClientMerchantAudit失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商户审核配置deleteClientMerchantAudit异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return boolean    
     */
	@Override
	public ResultVo updateClientMerchantAudit(OriginVo originVo,ClientMerchantAuditVo clientMerchantAuditVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改商户审核配置信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			ClientMerchantAudit clientMerchantAudit = (ClientMerchantAudit)BeanUtil.copyProperties(ClientMerchantAudit.class, clientMerchantAuditVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(clientMerchantAudit.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientMerchantAuditLogic.updateClientMerchantAudit(clientMerchantAudit)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改商户审核配置DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改商户审核配置updateClientMerchantAudit失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商户审核配置updateClientMerchantAudit异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}


	/**
     * 根据clientId+orgCode查询商户审核配置信息
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @param type 1-审核 2-商户重置密码
     * @return ClientMerchantAuditOrgCodeVo
     */
	@Override
	public ClientMerchantAuditOrgCodeVo getClientMerchantAuditByOrgCode(String clientId,String orgCode,String type){
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId) || Checker.isEmpty(type)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ClientMerchantAuditOrgCodeVo();
		}
		
		
		ClientMerchantAudit clientMerchantAudit = clientMerchantAuditLogic.findClientMerchantAuditByOrgCode(clientId,orgCode,type);
		ClientMerchantAuditOrgCodeVo clientMerchantAuditOrgCodeVo=(ClientMerchantAuditOrgCodeVo)BeanUtil.copyProperties(ClientMerchantAuditOrgCodeVo.class, clientMerchantAudit);
		
		return clientMerchantAuditOrgCodeVo==null?new ClientMerchantAuditOrgCodeVo():clientMerchantAuditOrgCodeVo;
		
	}

	
	

    /**
     * 查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return List<ClientMerchantAuditVo>
     */
	@Override
	public List<ClientMerchantAuditVo> getClientMerchantAudit(ClientMerchantAuditVo clientMerchantAuditVo) throws TException {
		
		ClientMerchantAudit clientMerchantAudit =(ClientMerchantAudit)BeanUtil.copyProperties(ClientMerchantAudit.class, clientMerchantAuditVo);
		
		List<ClientMerchantAudit> clientMerchantAuditList = clientMerchantAuditLogic.findClientMerchantAudit(clientMerchantAudit);
		List<ClientMerchantAuditVo> clientMerchantAuditVoList =(List<ClientMerchantAuditVo>)BeanUtil.copyProperties(ClientMerchantAuditVo.class, clientMerchantAuditList);
		
		return clientMerchantAuditVoList==null?new ArrayList<ClientMerchantAuditVo>():clientMerchantAuditVoList;
	}



    /**
     * 分页查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return ClientMerchantAuditPageVoRes
     */
	@Override
	public ClientMerchantAuditPageVoRes getClientMerchantAuditByPage(PageVo pageVo, ClientMerchantAuditVo clientMerchantAuditVo) throws TException {
		
		Page<ClientMerchantAudit> page = (Page<ClientMerchantAudit>)BeanUtil.copyProperties(Page.class, pageVo);
		ClientMerchantAudit clientMerchantAudit = (ClientMerchantAudit)BeanUtil.copyProperties(ClientMerchantAudit.class, clientMerchantAuditVo);

		page = clientMerchantAuditLogic.findClientMerchantAuditByPage(page, clientMerchantAudit);

		
		List<ClientMerchantAuditVo> clientMerchantAuditVoList=(List<ClientMerchantAuditVo>)BeanUtil.copyProperties(ClientMerchantAuditVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
			
		ClientMerchantAuditPageVoRes clientMerchantAuditPageVoRes = new ClientMerchantAuditPageVoRes();
		clientMerchantAuditPageVoRes.setClientMerchantAuditVoList(clientMerchantAuditVoList);
		clientMerchantAuditPageVoRes.setPage(pageVo);
		
		
		return clientMerchantAuditPageVoRes;
	}
	
}

