package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankResourceLogic;
import com.froad.logic.impl.BankResourceLogicImpl;
import com.froad.po.BankResource;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankResourceService;
import com.froad.thrift.vo.BankResourcePageVoRes;
import com.froad.thrift.vo.BankResourceVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: BankResourceImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankResourceServiceImpl extends BizMonitorBaseService implements BankResourceService.Iface {
	private BankResourceLogic bankResourceLogic = new BankResourceLogicImpl();
	public BankResourceServiceImpl(String name, String version) {
		super(name, version);
	}


    /**
     * 增加 BankResource
     * @param bankResource
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addBankResource(OriginVo originVo,BankResourceVo bankResourceVo) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加银行资源");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			BankResource bankResource=(BankResource)BeanUtil.copyProperties(BankResource.class, bankResourceVo);
			if(Checker.isEmpty(bankResource)){
				throw new FroadServerException("添加BankResource失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(bankResource.getClientId())){
				throw new FroadServerException("添加BankResource失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(bankResource.getResourceType())){
				throw new FroadServerException("添加BankResource失败,原因:ResourceType不能为空!");
			}
			if(Checker.isEmpty(bankResource.getParentResourceId())){
				throw new FroadServerException("添加BankResource失败,原因:ParentResourceId不能为空!");
			}
			if(Checker.isEmpty(bankResource.getResourceUrl())){
				throw new FroadServerException("添加BankResource失败,原因:ResourceUrl不能为空!");
			}
			if(Checker.isEmpty(bankResource.getTreePath())){
				throw new FroadServerException("添加BankResource失败,原因:TreePath不能为空!");
			}
			
			
			
			Long id = bankResourceLogic.addBankResource(bankResource);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加银行资源失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加银行资源addBankResource失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加银行资源addBankResource异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}



    /**
     * 删除 BankResource
     * @param bankResource
     * @return boolean    
     */
	@Override
	public ResultVo deleteBankResource(OriginVo originVo,long id) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除银行资源");
			LogUtils.addLog(originVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(id)){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			if (!bankResourceLogic.deleteBankResource(id)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除银行资源DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除银行资源deleteBankResource失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除银行资源deleteBankResource异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 修改 BankResource
     * @param bankResource
     * @return boolean    
     */
	@Override
	public ResultVo updateBankResource(OriginVo originVo,BankResourceVo bankResourceVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改银行资源");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			BankResource bankResource=(BankResource)BeanUtil.copyProperties(BankResource.class, bankResourceVo);
			//验证update和delete时id不能为空
			if(Checker.isEmpty(bankResource.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!bankResourceLogic.updateBankResource(bankResource)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改银行资源DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改银行资源updateBankResource失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改银行资源updateBankResource异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 查询 BankResource
     * @param bankResource
     * @return List<BankResourceVo>
     */
	@Override
	public List<BankResourceVo> getBankResource(BankResourceVo bankResourceVo) throws TException {
		BankResource bankResource=(BankResource)BeanUtil.copyProperties(BankResource.class, bankResourceVo);
		List<BankResource> bankResourceList = bankResourceLogic.findBankResource(bankResource);
		
		List<BankResourceVo> bankResourceVoList=(List<BankResourceVo>)BeanUtil.copyProperties(BankResourceVo.class, bankResourceList);
		
		return bankResourceVoList==null?new ArrayList<BankResourceVo>():bankResourceVoList;
	}


	 /**
     * 分页查询 BankResource
     * @param bankResource
     * @return BankResourcePageVoRes
     */
	@Override
	public BankResourcePageVoRes getBankResourceByPage(PageVo pageVo, BankResourceVo bankResourceVo) throws TException {
		Page<BankResource> page = null;
		BankResource bankResource = null;
		
		page=(Page<BankResource>)BeanUtil.copyProperties(Page.class ,pageVo);
		bankResource=(BankResource)BeanUtil.copyProperties(BankResource.class, bankResourceVo);

		page = bankResourceLogic.findBankResourceByPage(page, bankResource);

		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		List<BankResourceVo> bankResourceVoList=(List<BankResourceVo>)BeanUtil.copyProperties(BankResourceVo.class, page.getResultsContent());
		
		
		BankResourcePageVoRes bankResourcePageVoRes = new BankResourcePageVoRes();
		bankResourcePageVoRes.setBankResourceVoList(bankResourceVoList);
		bankResourcePageVoRes.setPage(pageVo);
		
		
		return bankResourcePageVoRes;
	}


}
