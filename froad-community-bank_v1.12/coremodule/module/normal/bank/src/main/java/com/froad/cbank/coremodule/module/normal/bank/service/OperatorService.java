package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorVoRes;
import com.froad.thrift.service.ArtificialPersonService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.vo.BankOperatorPageVoRes;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 银行管理员
 * @author yfy
 * @date 2015-3-20 下午14:20:18
 */
@Service
public class OperatorService {
	
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	BankOrgService bankOrgService;
	@Resource
	ArtificialPersonService.Iface artificialPersonService;
	
	/**
	 * 分页列表条件查询
	 * @param loginName
	 * @param orgCode
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 */
	public QueryResult<OperatorVoRes> findPageByConditions(OperatorVoReq voReq){
		
		PageVo page = new PageVo();
		page.setPageNumber(voReq.getPageNumber());
		page.setPageSize(voReq.getPageSize());
		page.setLastPageNumber(voReq.getLastPageNumber());
		page.setFirstRecordTime(voReq.getFirstRecordTime());
		page.setLastRecordTime(voReq.getLastRecordTime());
		
		BankOperatorVo bankOperator = new BankOperatorVo();
		bankOperator.setClientId(voReq.getClientId());
		if(StringUtils.isNotEmpty(voReq.getOrgCode())){
			bankOperator.setOrgCode(voReq.getOrgCode());
		}
		if(StringUtils.isNotEmpty(voReq.getLoginName())){
			bankOperator.setUsername(voReq.getLoginName());
		}
		if(StringUtils.isNotEmpty(voReq.getName())){
			bankOperator.setName(voReq.getName());
		}
		if(StringUtils.isNotEmpty(voReq.getType())){
			bankOperator.setType(voReq.getType());
		}
		bankOperator.setIsDelete(false);
		
		QueryResult<OperatorVoRes> queryVo = new QueryResult<OperatorVoRes>();
		List<OperatorVoRes> operatorList = new ArrayList<OperatorVoRes>();
		BankOperatorPageVoRes bankOperatorPage = null;
		try {
			List<String> orgCodeList = new ArrayList<String>();
			
			if(StringUtils.isEmpty(voReq.getLoginName()) && StringUtils.isEmpty(voReq.getName()))//只查询当前机构用户信息
				bankOperatorPage=bankOperatorService.getBankOperatorByPageInCurrentOrg(page, bankOperator);
			else //递归查询机构以及以下用户列表
				bankOperatorPage = bankOperatorService.getBankOperatorByPage(page, bankOperator);
			
			List<BankOperatorVo> bankOperatorList = bankOperatorPage.getBankOperatorVoList();
			if(bankOperatorList != null && bankOperatorList.size() > 0){
				for(BankOperatorVo bankOperatorVo : bankOperatorList){
					OperatorVoRes operatorVo = new OperatorVoRes();
					operatorVo.setOperatorId(Long.valueOf(bankOperatorVo.getId()));//用户ID
					operatorVo.setLoginName(bankOperatorVo.getUsername());//登陆名
					if(StringUtil.isNotEmpty(bankOperatorVo.getName())){
						operatorVo.setOperatorName(bankOperatorVo.getName());//姓名
					}else{
						operatorVo.setOperatorName("");//姓名
					}
					operatorVo.setStatus(bankOperatorVo.isStatus());//状态 
					if(StringUtils.isNotEmpty(bankOperatorVo.getOrgCode())){
						operatorVo.setOrgCode(bankOperatorVo.getOrgCode());//所属机构
						orgCodeList.add(bankOperatorVo.getOrgCode());
					}
					if(StringUtil.isNotEmpty(bankOperatorVo.getDepartment())){
						operatorVo.setDepartment(bankOperatorVo.getDepartment());//部门
					}else{
						operatorVo.setDepartment("");//部门
					}
					if(StringUtil.isNotEmpty(bankOperatorVo.getPosition())){
						operatorVo.setPosition(bankOperatorVo.getPosition());//职务
					}else{
						operatorVo.setPosition("");//职务
					}
					operatorVo.setLastLoginIp(bankOperatorVo.getLastLoginIp());
					if(bankOperatorVo.getLastLoginTime()>0){
						operatorVo.setLastLoginTime(bankOperatorVo.getLastLoginTime());
					} 
					operatorList.add(operatorVo);
					
				}
				if(orgCodeList != null && orgCodeList.size() > 0){
					List<BankOrgVo> orgVoList = bankOrgService.getByOrgCode(voReq.getClientId(),orgCodeList);
					for(OperatorVoRes voRes : operatorList){
						for(BankOrgVo bankorgVo : orgVoList){
							if(StringUtil.isNotEmpty(voRes.getOrgCode())){
								if(voRes.getOrgCode().equals(bankorgVo.getOrgCode())){
									voRes.setOrgName(bankorgVo.getOrgName());
								}
							}
						}
					}
				}
			}
			if(bankOperatorPage.getPage() != null){
				queryVo.setPageCount(bankOperatorPage.getPage().getPageCount());
				queryVo.setTotalCount(bankOperatorPage.getPage().getTotalCount());
				queryVo.setPageNumber(bankOperatorPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(bankOperatorPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(bankOperatorPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(bankOperatorPage.getPage().getLastRecordTime());
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员列表条件查询"+e.getMessage(), e);
		}
		
		queryVo.setResult(operatorList);
		return queryVo;
		
	}
	/**
	 *  新增
	 * @param operator
	 * @return
	 */
	public MsgVo operatorAdd(OperatorVo operator,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			String loginName = operator.getLoginName();
			Boolean fag = loginName.toUpperCase().startsWith(operator.getPrefix());
			if(fag){
				msgVo.setResult(false);
				msgVo.setMsg("'"+operator.getPrefix()+"'是系统关键字，不能作为用户名前缀!");
			}else{
				BankOperatorVo bankOperatorVo = new BankOperatorVo();
				bankOperatorVo.setClientId(operator.getClientId());//客户端ID
				bankOperatorVo.setUsername(operator.getLoginName());//登录名
				bankOperatorVo.setName(operator.getOperatorName());//姓名
				String password = TargetObjectFormat.getPassword(operator.getUserPassword());
				bankOperatorVo.setPassword(password);//登陆密码
				bankOperatorVo.setOrgCode(operator.getOrgCode());//所属机构
				bankOperatorVo.setRoleId(operator.getRoleId());//角色
				bankOperatorVo.setDepartment(operator.getDepartment());//部门
				bankOperatorVo.setPosition(operator.getPosition());//职务
				bankOperatorVo.setStatus(operator.getState()); //状态
				bankOperatorVo.setRemark(operator.getRemark()); //备注
				
				CommonAddVoRes resultVo = bankOperatorService.addBankOperator(vo,bankOperatorVo);
				if(resultVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
					msgVo.setId(resultVo.getId());
					msgVo.setResult(true);
					msgVo.setMsg(resultVo.getResult().getResultDesc());
				}else{
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResult().getResultDesc());
				}
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员新增"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行管理员新增异常" + e);
		}
		return msgVo;
	}
	/**
	 * 修改
	 * @param operator
	 * @return
	 */
	public MsgVo operatorUpdate(OperatorVo operator,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			BankOperatorVo bankOperatorVo = new BankOperatorVo();
			bankOperatorVo.setClientId(operator.getClientId());//客户端ID
			bankOperatorVo.setId(operator.getOperatorId());//用户ID
			bankOperatorVo.setName(operator.getOperatorName());//姓名
			bankOperatorVo.setOrgCode(operator.getOrgCode());//所属机构
			bankOperatorVo.setRoleId(operator.getRoleId());//角色
			bankOperatorVo.setDepartment(operator.getDepartment());//部门
			bankOperatorVo.setPosition(operator.getPosition());//职务
			bankOperatorVo.setStatus(operator.getState());  //状态
			bankOperatorVo.setRemark(operator.getRemark()); //备注
			
			ResultVo resultVo = bankOperatorService.updateBankOperator(vo,bankOperatorVo);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("银行管理员修改"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行管理员修改异常" + e);
		}
		return msgVo;
	}
	/**
	 *  禁/启用
	 * @param operatorId
	 * @param state
	 * @return
	 */
	public MsgVo operatorIsEnable(String clientId,String orgCode,Long operatorId,Boolean state,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			
			BankOperatorVo bankOperatorVo = new BankOperatorVo();
			bankOperatorVo.setClientId(clientId);//客户端ID
			bankOperatorVo.setId(operatorId);//用户ID
			bankOperatorVo.setStatus(state);//是否禁用
			int count = bankOrgService.getOrgByOrgCode(clientId, orgCode);
			if(count > 0){
				ResultVo resultVo = bankOperatorService.updateBankOperator(vo,bankOperatorVo);
				if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
					msgVo.setResult(true);
					msgVo.setMsg(resultVo.getResultDesc());
				}else{
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
				}
			}else{
				msgVo.setResult(false);
				msgVo.setMsg("对应的机构是禁用状态,不能启用此操作员");
			}
		} catch (Exception e) {
			LogCvt.info("银行管理员"+(state?"启用":"禁用")+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行管理员"+(state?"启用":"禁用")+"异常" + e);
		}
		return msgVo;
	}
	/**
	 *  重置密码
	 * @param operatorId
	 * @param state
	 * @return
	 */
	public MsgVo operatorResetPassword(String clientId, Long operatorId,String loginPassword,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			BankOperatorVo bankOperatorVo = new BankOperatorVo();
			bankOperatorVo.setClientId(clientId);//客户端ID
			bankOperatorVo.setId(operatorId);//用户ID
			bankOperatorVo.setIsReset(false);
			bankOperatorVo.setPassword(TargetObjectFormat.getPassword(loginPassword));
			ResultVo resultVo = bankOperatorService.updateBankOperator(vo,bankOperatorVo);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("银行管理员密码重置"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行管理员密码重置异常" + e);
		}
		return msgVo;
	}
	/**
	 * 详情
	 * @param operatorId
	 * @return
	 */
	public OperatorVo operatorDetail(String clientId,Long operatorId){
		
		OperatorVo operatorVo = new OperatorVo();
		try {
			
			BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(clientId, operatorId);
			if(bankOperatorVo != null){
				operatorVo.setClientId(bankOperatorVo.getClientId());//客户端ID
				operatorVo.setOperatorId(Long.valueOf(bankOperatorVo.getId()));//用户ID
				operatorVo.setLoginName(bankOperatorVo.getUsername());//登陆名
				operatorVo.setOperatorName(bankOperatorVo.getName());//姓名
				operatorVo.setOrgCode(bankOperatorVo.getOrgCode());//所属机构
				if(StringUtil.isNotEmpty(bankOperatorVo.getOrgCode())){
					operatorVo.setOrgCode(bankOperatorVo.getOrgCode());
					BankOrgVo orgVo = bankOrgService.getOrgNameByOrgCode(clientId,bankOperatorVo.getOrgCode());
					if(orgVo!=null){
						operatorVo.setOrgCodeName(orgVo.getOrgName()); //机构名称
						operatorVo.setOrgLevel(orgVo.getOrgLevel());   //机构等级
						if(!orgVo.getOrgLevel().equals("1")){//如果非一级机构则获取上级机构心想你
							operatorVo.setPartenOrgCode(orgVo.getPartenOrgCode());//父级机构
							operatorVo.setPartenOrgCodeName(orgVo.getPartenOrgName()); //父级机构名称
						}
						if(operatorVo.getPartenOrgCodeName()==null && operatorVo.getPartenOrgCode()!=null){//获取父级名称
							orgVo = bankOrgService.getOrgNameByOrgCode(clientId,operatorVo.getPartenOrgCode());
							operatorVo.setPartenOrgCodeName(orgVo.getOrgName());//父级机构名称
						}
					} 
				}
				operatorVo.setRoleId(bankOperatorVo.getRoleId());//角色
				operatorVo.setDepartment(bankOperatorVo.getDepartment());//部门
				operatorVo.setPosition(bankOperatorVo.getPosition());//职务
				operatorVo.setState(bankOperatorVo.isStatus());
				operatorVo.setRemark(bankOperatorVo.getRemark());
			}
			
		} catch (Exception e) {
			LogCvt.info("银行管理员详情查询"+e.getMessage(), e);
		}
		return operatorVo;
	}
	/**
	 * 删除
	 * @param operatorId
	 * @return
	 */
	public MsgVo operatorDelete(String clientId,Long operatorId,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			
			BankOperatorVo bankOperatorVo = new BankOperatorVo();
			bankOperatorVo.setClientId(clientId);//客户端ID
			bankOperatorVo.setId(operatorId);
			
			ResultVo resultVo = bankOperatorService.deleteBankOperator(vo,bankOperatorVo);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("银行管理员删除"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行管理员删除异常");
		}
		return msgVo;
	}
	/**
	 * 获取法人行社
	 * @param orgCode
	 * @return
	 */
	public List<BankOrgVo> selectTwoOrgCode(String clientId,String orgCode){
		List<BankOrgVo> bankOrgList = new ArrayList<BankOrgVo>();
		List<OrgVo> orgList = new ArrayList<OrgVo>(); 
		try {
			orgList = artificialPersonService.getArtificialPerson(clientId,orgCode);
			if(orgList != null && orgList.size() > 0){
				for(OrgVo org : orgList){
					BankOrgVo bankOrg = new BankOrgVo();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrgList.add(bankOrg);
				}
			}
			
		} catch (Exception e) {
			LogCvt.info("获取法人行社列表"+e.getMessage(), e);
		}
		
		return bankOrgList;
	}
	
	/**
	 * 获取法人行社(只有新增部分)
	 * 
	 * @param orgCode
	 * @return
	 */
	public List<BankOrgVo> queryTwoOrgCode(String clientId, String orgCode) {
		List<BankOrgVo> bankOrgList = new ArrayList<BankOrgVo>();
		List<OrgVo> orgList = new ArrayList<OrgVo>();
		try {
			orgList = artificialPersonService.getArtificialPersonByNotAdd(
					clientId, orgCode);
			if (orgList != null && orgList.size() > 0) {
				for (OrgVo org : orgList) {
					BankOrgVo bankOrg = new BankOrgVo();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrgList.add(bankOrg);
				}
			}

		} catch (Exception e) {
			LogCvt.info("获取法人行社列表" + e.getMessage(), e);
		}

		return bankOrgList;
	}

	/**
	 * 获取已生成的法人行社
	 * @param orgCode
	 * @return
	 */
	public List<BankOrgVo> selectTwoOverOrgCode(String clientId,String orgCode){
		List<BankOrgVo> bankOrgList = new ArrayList<BankOrgVo>();
		List<OrgVo> orgList = new ArrayList<OrgVo>(); 
		try {
			orgList = artificialPersonService.getArtificialPersonByAdd(clientId,orgCode);
			if(orgList != null && orgList.size() > 0){
				for(OrgVo org : orgList){
					BankOrgVo bankOrg = new BankOrgVo();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrgList.add(bankOrg);
				}
			}
			
		} catch (Exception e) {
			LogCvt.info("获取已生成的法人行社"+e.getMessage(), e);
		}
		return bankOrgList;
	}
	
	/**
	 *  批量删除
	 * @param operatorId
	 * @param state
	 * @return
	 */
	public MsgVo operatorDelete(String clientId,List<String> orgCodeList,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ResultVo resultVo = artificialPersonService.deleteArtificialPerson(vo,clientId, orgCodeList);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.info("批量删除"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg(" 批量删除异常" + e);
		}
		return msgVo;
	}
	/**
	 *  批量重置密码
	 * @param operatorId
	 * @param state
	 * @return
	 */
	public MsgVo operatorLogPasswordUpdate(String clientId,List<String> orgCodeList,
			String userPassword,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ResultVo resultVo = artificialPersonService.updateArtificialPerson(vo,clientId,
					orgCodeList,TargetObjectFormat.getPassword(userPassword));
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("批量重置密码"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("批量重置密码异常" + e);
		}
		return msgVo;
	}
	
	/**
	 *  批量新增
	 * @param operator
	 * @return
	 */
	public MsgVo operatorAddAll(String clientId,List<String> orgCodeList,String userPassword,
			String prefix,OriginVo vo){
		MsgVo msgVo = new MsgVo();
		try {
			ResultVo resultVo = artificialPersonService.addArtificialPerson(vo,clientId, 
					orgCodeList,TargetObjectFormat.getPassword(userPassword),prefix);
			if(resultVo.getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResultDesc());
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}
			
		} catch (Exception e) {
			LogCvt.info("法人行社管理员新增"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("法人行社管理员新增异常" + e);
		}
		return msgVo;
	}
	
	/**
	 * 校验是否已存在
	 * @param clientId
	 * @param userName
	 * @return
	 */
	public int getOperatorByUserName(String clientId, String userName){
		BankOperatorVo bankOperatorVo = new BankOperatorVo();
		bankOperatorVo.setClientId(clientId);
		bankOperatorVo.setUsername(userName);
		List<BankOperatorVo> bankOperatorList = new ArrayList<BankOperatorVo>(); 
		try {
			bankOperatorList = bankOperatorService.getBankOperator(bankOperatorVo);
		} catch (TException e) {
			LogCvt.info("校验是否已存在此用户名"+e.getMessage(), e);
		}
		return bankOperatorList.size();
	}

	/**
	 * 校验
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verify(OperatorVo vo){
		
		MsgVo msgVo = new MsgVo();
		if(!StringUtil.isNotEmpty(vo.getOrgCode())){
			msgVo.setResult(false);
			msgVo.setMsg("组织编码不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getLoginName())){
			msgVo.setResult(false);
			msgVo.setMsg("用户名不能为空");
			return msgVo;
		}
		if(!StringUtil.isNotEmpty(vo.getUserPassword())){
			msgVo.setResult(false);
			msgVo.setMsg("密码不能为空");
			return msgVo;
		}
		if(vo.getRoleId() == null){
			msgVo.setResult(false);
			msgVo.setMsg("角色不能为空");
			return msgVo;
		}
		msgVo.setResult(true);
		return msgVo;
	}
		
}
