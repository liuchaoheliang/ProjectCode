package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientProductAuditLogic;
import com.froad.logic.impl.ClientProductAuditLogicImpl;
import com.froad.po.ClientProductAudit;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.vo.ClientProductAuditOrgCodeVo;
import com.froad.thrift.vo.ClientProductAuditPageVoRes;
import com.froad.thrift.vo.ClientProductAuditVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: ClientProductAuditImpl.java</p>
 * <p>Description: 描述 </p> 商品审核配置管理
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public class ClientProductAuditServiceImpl extends BizMonitorBaseService implements ClientProductAuditService.Iface {
	private ClientProductAuditLogic clientProductAuditLogic = new ClientProductAuditLogicImpl();
	public ClientProductAuditServiceImpl(String name, String version) 
	{
		super(name, version);
	}


    /**
     * 增加 ClientProductAudit
     * @param clientProductAudit
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addClientProductAudit(OriginVo originVo,ClientProductAuditVo clientProductAuditVo) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加商品审核配置信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			ClientProductAudit clientProductAudit = (ClientProductAudit)BeanUtil.copyProperties(ClientProductAudit.class, clientProductAuditVo);
			
			if(Checker.isEmpty(clientProductAudit)){
				throw new FroadServerException("添加ClientProductAudit失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(clientProductAudit.getClientId())){
				throw new FroadServerException("添加ClientProductAudit失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(clientProductAudit.getProductType())){
				throw new FroadServerException("添加ClientProductAudit失败,原因:ProductType不能为空!");
			}
			if(Checker.isEmpty(clientProductAudit.getOrgLevel())){
				throw new FroadServerException("添加ClientProductAudit失败,原因:OrgLevel不能为空!");
			}
			if(Checker.isEmpty(clientProductAudit.getStartOrgLevel())){
				throw new FroadServerException("添加ClientProductAudit失败,原因:StartOrgLevel不能为空!");
			}
			if(Checker.isEmpty(clientProductAudit.getEndOrgLevel())){
				throw new FroadServerException("添加ClientProductAudit失败,原因:EndOrgLevel不能为空!");
			}
			
			Long id = clientProductAuditLogic.addClientProductAudit(clientProductAudit);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加商品审核配置失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("添加商品审核配置addClientProductAudit失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加商品审核配置addClientProductAudit异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
	}



    /**
     * 删除 ClientProductAudit
     * @param clientProductAudit
     * @return boolean    
     */
	@Override
	public ResultVo deleteClientProductAudit(OriginVo originVo,ClientProductAuditVo clientProductAuditVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除商品审核配置信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po
			ClientProductAudit clientProductAudit = (ClientProductAudit)BeanUtil.copyProperties(ClientProductAudit.class, clientProductAuditVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(clientProductAudit.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientProductAuditLogic.deleteClientProductAudit(clientProductAudit)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除商品审核配置DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除商品审核配置deleteClientProductAudit失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除商品审核配置deleteClientProductAudit异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 ClientProductAudit
     * @param clientProductAudit
     * @return boolean    
     */
	@Override
	public ResultVo updateClientProductAudit(OriginVo originVo,ClientProductAuditVo clientProductAuditVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改商品审核配置信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po
			ClientProductAudit clientProductAudit = (ClientProductAudit)BeanUtil.copyProperties(ClientProductAudit.class, clientProductAuditVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(clientProductAudit.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!clientProductAuditLogic.updateClientProductAudit(clientProductAudit)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改商品审核配置DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改商品审核配置updateClientProductAudit失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改商品审核配置updateClientProductAudit异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}


	/**
     * 根据clientId+orgCode查询商户审核配置信息
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @param productType商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
     * @return ClientProductAuditOrgCodeVo
     */
	@Override
	public ClientProductAuditOrgCodeVo getClientProductAuditByOrgCode(String clientId,String orgCode,String productType){
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId) || Checker.isEmpty(productType)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ClientProductAuditOrgCodeVo();
		}
		
		ClientProductAudit clientProductAudit = clientProductAuditLogic.findClientProductAuditByOrgCode(clientId,orgCode,productType);
		ClientProductAuditOrgCodeVo clientProductAuditOrgCodeVo=(ClientProductAuditOrgCodeVo)BeanUtil.copyProperties(ClientProductAuditOrgCodeVo.class, clientProductAudit);
		
		return clientProductAuditOrgCodeVo==null?new ClientProductAuditOrgCodeVo():clientProductAuditOrgCodeVo;
		
	}
	
	

    /**
     * 查询 ClientProductAudit
     * @param clientProductAudit
     * @return List<ClientProductAuditVo>
     */
	@Override
	public List<ClientProductAuditVo> getClientProductAudit(ClientProductAuditVo clientProductAuditVo) throws TException {
		
		ClientProductAudit clientProductAudit =(ClientProductAudit)BeanUtil.copyProperties(ClientProductAudit.class, clientProductAuditVo);
		
		List<ClientProductAudit> clientProductAuditList = clientProductAuditLogic.findClientProductAudit(clientProductAudit);
		List<ClientProductAuditVo> clientProductAuditVoList =(List<ClientProductAuditVo>)BeanUtil.copyProperties(ClientProductAuditVo.class, clientProductAuditList);
		
		return clientProductAuditVoList==null?new ArrayList<ClientProductAuditVo>():clientProductAuditVoList;

	}



    /**
     * 分页查询 ClientProductAudit
     * @param clientProductAudit
     * @return ClientProductAuditPageVoRes
     */
	@Override
	public ClientProductAuditPageVoRes getClientProductAuditByPage(PageVo pageVo, ClientProductAuditVo clientProductAuditVo) throws TException {
		
		Page<ClientProductAudit> page = (Page<ClientProductAudit>)BeanUtil.copyProperties(Page.class, pageVo);
		ClientProductAudit clientProductAudit = (ClientProductAudit)BeanUtil.copyProperties(ClientProductAudit.class, clientProductAuditVo);

		page = clientProductAuditLogic.findClientProductAuditByPage(page, clientProductAudit);

		
		List<ClientProductAuditVo> clientProductAuditVoList=(List<ClientProductAuditVo>)BeanUtil.copyProperties(ClientProductAuditVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
			
		ClientProductAuditPageVoRes clientProductAuditPageVoRes = new ClientProductAuditPageVoRes();
		clientProductAuditPageVoRes.setClientProductAuditVoList(clientProductAuditVoList);
		clientProductAuditPageVoRes.setPage(pageVo);
		
		
		return clientProductAuditPageVoRes;
	}


}
