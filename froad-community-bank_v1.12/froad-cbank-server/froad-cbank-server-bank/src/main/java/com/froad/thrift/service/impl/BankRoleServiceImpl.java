package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankRoleLogic;
import com.froad.logic.impl.BankRoleLogicImpl;
import com.froad.po.BankRole;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankRoleService;
import com.froad.thrift.vo.BankRolePageVoRes;
import com.froad.thrift.vo.BankRoleVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: BankRoleImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankRoleServiceImpl extends BizMonitorBaseService implements BankRoleService.Iface {
	private BankRoleLogic bankRoleLogic = new BankRoleLogicImpl();
	public BankRoleServiceImpl(String name, String version) {
		super(name, version);
	}


    /**
     * 增加 BankRole
     * @param bankRole
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addBankRole(OriginVo originVo,BankRoleVo bankRoleVo, List<Long> resourceIds) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加银行角色");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			BankRole bankRole=(BankRole)BeanUtil.copyProperties(BankRole.class, bankRoleVo);
			if(Checker.isEmpty(bankRole) || Checker.isEmpty(resourceIds)){
				throw new FroadServerException("添加BankRole失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(bankRole.getClientId())){
				throw new FroadServerException("添加BankRole失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(bankRole.getTag())){
				throw new FroadServerException("添加BankRole失败,原因:Tag不能为空!");
			}
			if(Checker.isEmpty(bankRole.getOrgCode())){
				throw new FroadServerException("添加BankRole失败,原因:OrgCode不能为空!");
			}
			
			
			Long id = bankRoleLogic.addBankRole(bankRole,resourceIds);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加银行角色失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加银行角色addBankRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加银行角色addBankRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}



    /**
     * 删除 BankRole
     * @param bankRole
     * @return boolean    
     */
	@Override
	public ResultVo deleteBankRole(OriginVo originVo,BankRoleVo bankRoleVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除银行角色");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			BankRole bankRole=(BankRole)BeanUtil.copyProperties(BankRole.class, bankRoleVo);
			//验证update和delete时id不能为空
			if(Checker.isEmpty(bankRole.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!bankRoleLogic.deleteBankRole(bankRole)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除银行角色DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除银行角色deleteBankRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除银行角色deleteBankRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 修改 BankRole
     * @param bankRole
     * @return boolean    
     */
	@Override
	public ResultVo updateBankRole(OriginVo originVo,BankRoleVo bankRoleVo, List<Long> resourceIds) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改银行角色");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			BankRole bankRole = (BankRole)BeanUtil.copyProperties(BankRole.class, bankRoleVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(bankRole.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			//参数不全
			if(Checker.isEmpty(resourceIds)){
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			
			if (!bankRoleLogic.updateBankRole(bankRole, resourceIds)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改银行角色DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改银行角色updateBankRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改银行角色updateBankRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 查询 BankRole
     * @param bankRole
     * @return List<BankRoleVo>
     */
	@Override
	public List<BankRoleVo> getBankRole(BankRoleVo bankRoleVo) throws TException {
		BankRole bankRole =(BankRole)BeanUtil.copyProperties(BankRole.class, bankRoleVo);
			
		List<BankRole> bankRoleList = bankRoleLogic.findBankRole(bankRole);
		List<BankRoleVo> bankRoleVoList=(List<BankRoleVo>)BeanUtil.copyProperties(BankRoleVo.class, bankRoleList);
		
		return bankRoleVoList==null?new ArrayList<BankRoleVo>():bankRoleVoList;
	}

	
	/**
     * 查询 BankRole(只查询当前机构下的，不查下属机构对应的角色)
     * @param bankRole
     * @return List<BankRoleVo>
     */
	@Override
	public List<BankRoleVo> getBankRoleInCurrentOrg(BankRoleVo bankRoleVo) throws TException {
		BankRole bankRole =(BankRole)BeanUtil.copyProperties(BankRole.class, bankRoleVo);
			
		List<BankRole> bankRoleList = bankRoleLogic.findBankRoleInCurrentOrg(bankRole);
		List<BankRoleVo> bankRoleVoList=(List<BankRoleVo>)BeanUtil.copyProperties(BankRoleVo.class, bankRoleList);
		
		return bankRoleVoList==null?new ArrayList<BankRoleVo>():bankRoleVoList;
	}
	
	

	 /**
     * 分页查询 BankRole
     * @param bankRole
     * @return BankRolePageVoRes
     */
	@Override
	public BankRolePageVoRes getBankRoleByPage(PageVo pageVo, BankRoleVo bankRoleVo) throws TException {
		Page<BankRole> page = null;
		BankRole bankRole = null;
		
		//判断orgCode有值时，clientId不可为空
		if(Checker.isNotEmpty(bankRoleVo)){
			if(Checker.isNotEmpty(bankRoleVo.getOrgCode())){
				if(Checker.isEmpty(bankRoleVo.getClientId())){
					LogCvt.error("所属机构有值，clientId不可为空");
					return new BankRolePageVoRes();
				}
			}
		}
				
				
		/**vo转po**/
		page=(Page<BankRole>)BeanUtil.copyProperties(Page.class, pageVo);
		bankRole=(BankRole)BeanUtil.copyProperties(BankRole.class, bankRoleVo);

		page = bankRoleLogic.findBankRoleByPage(page, bankRole);

		
		List<BankRoleVo> bankRoleVoList =(List<BankRoleVo>)BeanUtil.copyProperties(BankRoleVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		BankRolePageVoRes bankRolePageVoRes = new BankRolePageVoRes();
		bankRolePageVoRes.setBankRoleVoList(bankRoleVoList);
		bankRolePageVoRes.setPage(pageVo);
		
		
		return bankRolePageVoRes;
	}

}
