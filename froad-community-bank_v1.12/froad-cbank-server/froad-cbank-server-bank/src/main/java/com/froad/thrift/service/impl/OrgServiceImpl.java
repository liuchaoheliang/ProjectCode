package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrgLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.OrgLogicImpl;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OrgPageVoRes;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;
import com.froad.util.OrgSuperUtil;


/**
 * 
 * <p>@Title: OrgServiceImpl.java</p>
 * <p>Description: 描述 </p>
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class OrgServiceImpl extends BizMonitorBaseService implements OrgService.Iface {
	private OrgLogic orgLogic = new OrgLogicImpl();
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	public OrgServiceImpl(String name, String version) {
		super(name, version);
	}


	/**
     * 增加机构信息Org
     * @param org
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addOrg(OriginVo originVo,OrgVo orgVo) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加银行机构信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po
			Org org = (Org)BeanUtil.copyProperties(Org.class, orgVo);
			
			if(Checker.isEmpty(org)){
				throw new FroadServerException("添加Org失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(org.getClientId())){
				throw new FroadServerException("添加Org失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(org.getOrgCode())){
				throw new FroadServerException("添加Org失败,原因:OrgCode不能为空!");
			}
			if(Checker.isEmpty(org.getOrgName())){
				throw new FroadServerException("添加Org失败,原因:OrgName不能为空!");
			}
			if(Checker.isEmpty(org.getAreaId())){
				throw new FroadServerException("添加Org失败,原因:AreaId不能为空!");
			}
			if(Checker.isEmpty(org.getOrgLevel())){
				throw new FroadServerException("添加Org失败,原因:OrgLevel不能为空!");
			}
			if(Checker.isEmpty(org.getNeedReview())){
				throw new FroadServerException("添加Org失败,原因:NeedReview不能为空!");
			}
			if(Checker.isEmpty(org.getOrgType())){
				throw new FroadServerException("添加Org失败,原因:OrgType不能为空!");
			}
			//业务机构商户id和门店id不可为空
			if(org.getOrgType()){
				if(Checker.isEmpty(org.getMerchantId())){
					throw new FroadServerException("添加Org失败,原因:MerchantId不能为空!");
				}
				if(Checker.isEmpty(org.getOutletId())){
					throw new FroadServerException("添加Org失败,原因:OutletId不能为空!");
				}
			}
			
			Long id = orgLogic.addOrg(org);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加银行机构失败");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("添加银行机构addOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加银行机构addOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
		
	}
	

	/**
     * 批量增加机构信息Org
     * @param org
     * @return bool    操作是否成功
     */
	@Override
	public ResultVo addOrgByBatch(OriginVo originVo,List<OrgVo> orgVoList) throws TException {
		
		CommonAddVoRes voRes = null;
		ResultVo result = new ResultVo();
		try{
			for(OrgVo orgVo:orgVoList){
				voRes=this.addOrg(originVo, orgVo);
				result=voRes.getResult();
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("批量添加银行机构addOrgByBatch失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("批量添加银行机构addOrgByBatch失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		return result;
		
	}
	
	
    /**
     * 删除 Org
     * @param org
     * @return boolean    
     */
	@Override
	public ResultVo deleteOrg(OriginVo originVo,OrgVo orgVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("禁用启用机构信息");
			LogUtils.addLog(originVo);
			
			// vo  转 po
			Org org =(Org)BeanUtil.copyProperties(Org.class, orgVo);
			
			//验证update和delete时id不能为空    或者  orgCode+clientId不可为空
			if(Checker.isEmpty(org.getId())){
				if(Checker.isEmpty(org.getOrgCode()) || Checker.isEmpty(org.getClientId())){
					result = new Result(ResultCode.no_id_error);
					return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
				}
			}else{
				if(org.getId()<=0){
					org.setId(null);
				}
			}
			
			if (!orgLogic.deleteOrg(org)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("禁用启用机构DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("禁用启用银行机构deleteOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("禁用启用银行机构deleteOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 Org
     * @param org
     * @return boolean    
     */
	@Override
	public ResultVo updateOrg(OriginVo originVo,OrgVo orgVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改银行机构信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po 
			Org org =(Org)BeanUtil.copyProperties(Org.class, orgVo);
			
			
			//验证update和delete时id不能为空    或者  orgCode+clientId不可为空
			if(Checker.isEmpty(org.getId())){
				if(Checker.isEmpty(org.getOrgCode()) || Checker.isEmpty(org.getClientId())){
					result = new Result(ResultCode.no_id_error);
					return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
				}
			}else{
				if(org.getId()<=0){
					org.setId(null);
				}
			}
			
			if (!orgLogic.updateOrg(org)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改机构DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改银行机构updateOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改银行机构updateOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 查询 Org
     * @param org
     * @return List<OrgVo>
     */
	@Override
	public List<OrgVo> getOrg(OrgVo orgVo) throws TException {
		Org org =(Org)BeanUtil.copyProperties(Org.class, orgVo);
		
		List<Org> orgList = orgLogic.findOrg(org);
		List<OrgVo> orgVoList =(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
	}
	
	/**
	 * 通过orgName查询机构信息列表.
	 * @param orgVo
	 * @return
	 * @throws TException
	 */
	@Override
	public List<OrgVo> getOrgInfoByOrgName(OrgVo orgVo,int limit,String loginOrgCode)throws TException{
		 List<OrgVo> orgVoList = new ArrayList<OrgVo>();
		    if (Checker.isEmpty(loginOrgCode)) {
		      LogCvt.error("loginOrgCode参数不可为空");
		      return orgVoList;
		    }

		    if ((Checker.isNotEmpty(orgVo)) && 
		      (Checker.isEmpty(orgVo.getClientId()))) {
		      LogCvt.error("所属机构有值，clientId不可为空");
		      return orgVoList;
		    }
		    Org org = (Org)BeanUtil.copyProperties(Org.class, orgVo);
		    List<Org> orgList =(List<Org>) orgLogic.findOrgByOrgNameOrClientId(org.getClientId(), org.getOrgName(), Integer.valueOf(limit), loginOrgCode);
		    orgVoList = (List)BeanUtil.copyProperties(OrgVo.class, orgList);
		    return orgVoList;
	}

	 /**
     * 分页查询 Org
     * @param orgVo 机构查询条件
     * @param loginOrgCode 当前登录loginOrgCode
     * @return OrgPageVoRes
     */
	@Override
	public OrgPageVoRes getOrgByPage(PageVo pageVo, OrgVo orgVo,String loginOrgCode) throws TException {
		if(Checker.isEmpty(loginOrgCode)){
			LogCvt.error("loginOrgCode参数不可为空");
			return new OrgPageVoRes();
		}
		
		//判断orgCode有值时，clientId不可为空
		if(Checker.isNotEmpty(orgVo)){
			if(Checker.isEmpty(orgVo.getClientId())){
				LogCvt.error("所属机构有值，clientId不可为空");
				return new OrgPageVoRes();
			}
		}
				
		Page<Org> page = (Page<Org>)BeanUtil.copyProperties(Page.class, pageVo);
		Org org = (Org)BeanUtil.copyProperties(Org.class, orgVo);

		page = orgLogic.findOrgByPage(page, org,loginOrgCode);

		
		List<OrgVo> orgVoList=(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
			
		OrgPageVoRes orgPageVoRes = new OrgPageVoRes();
		orgPageVoRes.setOrgVoList(orgVoList);
		orgPageVoRes.setPage(pageVo);
		
		
		return orgPageVoRes;
	}


	/**
     * 根据clientId+orgCode查询有效Org机构对象
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @return orgVo
     */
	@Override
	public OrgVo getOrgById(String clientId,String orgCode) throws TException {
		LogCvt.info("根据orgCode和clientId查询有效org对象getOrgById");
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new OrgVo();
		}
		
		Org org=commonLogic.getOrgByOrgCode(orgCode, clientId);
		OrgVo orgVo=(OrgVo)BeanUtil.copyProperties(OrgVo.class, org);
		
		return orgVo==null?new OrgVo():orgVo;
	}


	/**
     * 根据clientId+orgCode查询有效Org机构对象 并 查询上级机构名称
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @return orgVo
     */
	@Override
	public OrgVo getOrgByIdSuperOrgName(String clientId,String orgCode) throws TException {
		LogCvt.info("根据orgCode和clientId查询org对象getOrgByIdSuperOrgName");
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new OrgVo();
		}
		
		Org org=commonLogic.queryByOrgCode(clientId,orgCode);
		OrgVo orgVo=(OrgVo)BeanUtil.copyProperties(OrgVo.class, org);
		
		//取得上一级机构名称
		if(Checker.isNotEmpty(orgVo)){
			String orgTop=OrgSuperUtil.getOrgSuper(org);
			if(Checker.isNotEmpty(orgTop)){
				Org superOrg=commonLogic.queryByOrgCode(clientId, orgTop);
				if(Checker.isNotEmpty(superOrg)){
					orgVo.setSuperOrgName(superOrg.getOrgName());//设置上级机构名称
				}else{
					orgVo.setSuperOrgName("");//一级机构上级机构名称为空
				}
			}else{
				orgVo.setSuperOrgName("");//一级机构上级机构名称为空
			}
			
		}
		
		
		return orgVo==null?new OrgVo():orgVo;
	}
	
	
	
	/**
     * 根据机构码查出下级子机构，即只查叶子机构
     * @param orgCode 机构编号
     * @return List<OrgVo> 子机构集合
     */
	@Override
	public List<OrgVo> getSubOrgs(String clientId,String orgCode) throws TException {
		LogCvt.info("查出下级子机构列表getSubOrgs");
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		List<Org> orgList = orgLogic.findSubOrgs(clientId,orgCode);
		
		List<OrgVo> orgVoList = (List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
	}

   /**
    * 根据机构码查出所有下级子机构
    * @param orgCode 机构编号
    * @return List<OrgVo> 子机构集合
    */
	@Override
	public List<OrgVo> getAllSubOrgs(String clientId,String orgCode)
			throws TException {
		LogCvt.info("查出所有子机构getAllSubOrgs");
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		List<Org> orgList = orgLogic.findAllSubOrgs(clientId,orgCode);
		
		List<OrgVo> orgVoList = (List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
		
	}

	
	/**
    * 根据机构码查出所有下级子机构
    * @param orgCode 机构编号
    * @return List<OrgVo> 子机构集合
    */
	@Override
	public List<String> getAllSubOrgCodes(String clientId,String orgCode)
			throws TException {
		LogCvt.info("查出所有子机构getAllSubOrgCodes");
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<String>();
		}
		
		return orgLogic.findAllSubOrgCodes(clientId,orgCode);
		
	}
		
		
		
	/**
     * 查询市级区级机构(逗号隔开)
     * @param orgCode 机构编号
     * @param clientId 客户端编号
     * @return string 返回市级区级机构编号
     */
	@Override
	public String getSuperOrgCodeByType(String clientId,String orgCode)
			throws TException {
		LogCvt.info("查出市区级机构getSuperOrgCodeByType");
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new String();
		}
		return orgLogic.findSuperOrgCodeByType(clientId,orgCode);
	}


	/**
     * 根据orgCode集合获取机构对象(循环内部处理)
     * @param clientId 客户端编号
     * @param orgCodes 机构编号集合
     * @return list<OrgVo> 返回机构对象集合
     */
	@Override
	public List<OrgVo> getOrgByList(String clientId, List<String> orgCodes)
			throws TException {
		LogCvt.info("根据orgCode集合获取机构对象getOrgByList");
		if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCodes)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		
		List<Org> orgList = commonLogic.getOrgByList(clientId,orgCodes);
		List<OrgVo> orgVoList = (List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
		
	}


	/**
	 * 获取上级机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象
	 */
	@Override
	public OrgVo getSuperOrg(String clientId, String orgCode) throws TException {
		LogCvt.info("获取上级机构对象getSuperOrg");
		if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new OrgVo();
		}
		Org org = commonLogic.getSuperOrg(clientId, orgCode);
		OrgVo orgVo=(OrgVo)BeanUtil.copyProperties(OrgVo.class, org);
		
		return orgVo==null?new OrgVo():orgVo;
	}

	/**
	 * 获取上级(多级别)机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象集合
	 */
	@Override
	public List<OrgVo> getSuperOrgList(String clientId,String orgCode){
		LogCvt.info("获取上级(多级别)机构对象getSuperOrgList");
		if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		List<Org> orgList = commonLogic.getSuperOrgList(clientId,orgCode);
		List<OrgVo> orgVoList = (List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
	}


	/**
     * 根据clientId+areaId 查询有效网点
     * @param clientId 客户端编号
     * @param areaId  地区Id
     * @return list<OrgVo> 返回机构对象集合
     * 
     */
	@Override
	public List<OrgVo> getOrgByAreaId(String clientId, long areaId)
			throws TException {
		LogCvt.info("查询网点机构信息getOrgByAreaId");
		
		if(Checker.isEmpty(clientId) || areaId<=0){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		
		Org org = new Org();
		org.setClientId(clientId);
		org.setAreaId(areaId);
		org.setIsEnable(true);
		//取网点级别
		org.setOrgLevel(OrgSuperUtil.getPointOrgLevel(clientId));
		
			
		List<Org> orgList = orgLogic.findOrg(org);
		List<OrgVo> orgVoList =(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
	}

	/**
     * 查询areaId集合下的有效机构所属的法人行社列表
     * @param clientId 客户端编号
     * @param areaIds  区Id集合
     * @return list<OrgVo> 返回机构对象集合
     */
	@Override
	public List<OrgVo> getOrgByAreaIdsList(String clientId, List<Long> areaIds)
			throws TException {

		LogCvt.debug("查询areaId集合下的有效机构所属的法人行社列表");
		
		if(Checker.isEmpty(clientId) || Checker.isEmpty(areaIds)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		
		List<Org> orgList = orgLogic.findOrgByAreaIds(clientId,areaIds);
		List<OrgVo> orgVoList =(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgList);
		
		return orgVoList==null?new ArrayList<OrgVo>():orgVoList;
	
	}


	/**
	 * 获取网点交集集合
	 * @param clientId 客户端id
	 * @param loginOrgCode 登录人所属机构编号
	 * @param filterOrgCode 过滤条件机构编号
	 * @return 二者orgCode下级网点交集集合
	 */
	@Override
	public List<String> getIntersectionOrgCodeList(String clientId,String loginOrgCode, String filterOrgCode) throws TException {
		if(Checker.isEmpty(clientId) || Checker.isEmpty(loginOrgCode) || Checker.isEmpty(filterOrgCode)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<String>();
		}
		
		List<String> orgCodes = null;
		Set<String> set = new HashSet<String>();
		String[] orgArray = filterOrgCode.split(",");
		for (String orgcode : orgArray) {
			orgCodes = orgLogic.findIntersectionOrgCodeList(clientId,loginOrgCode,orgcode);
			
			set.addAll(orgCodes);
		}
		orgCodes = new ArrayList<String>();
		orgCodes.addAll(set);	
		
		return orgCodes==null?new ArrayList<String>():orgCodes;
	}
	
	
	
	
}
