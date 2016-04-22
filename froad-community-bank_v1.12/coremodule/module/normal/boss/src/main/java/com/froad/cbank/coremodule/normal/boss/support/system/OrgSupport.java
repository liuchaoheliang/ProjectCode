package com.froad.cbank.coremodule.normal.boss.support.system;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.normal.boss.enums.Platform;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgReVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgRoleVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVoListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVolistReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.DictionaryService;
import com.froad.thrift.service.FFTOrgService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.DictionaryItemVo;
import com.froad.thrift.vo.MerchantAddVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletAddVoRes;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgDetailVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgPageVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgReListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgReVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgVo;
import com.froad.thrift.vo.orgRoleManager.OrgRoleIdListVoRes;
import com.froad.thrift.vo.orgRoleManager.OrgRoleListVoRes;
import com.froad.thrift.vo.orgRoleManager.OrgRoleVo;
import com.froad.thrift.vo.orgRoleManager.isNextFFTOrgVo;

/**
 * 组织机构管理
 * @author yfy
 * @date: 2016年01月04日 下午14:34:01
 */
@Service
public class OrgSupport {
	
	@Resource
	private FFTOrgService.Iface fftOrgService;
	@Resource
	private OrgService.Iface orgService;
	@Resource
	private OutletService.Iface outletService;
	@Resource
	private MerchantService.Iface merchantService;
	@Resource
	private AreaService.Iface areaService;
	@Resource
	private DictionaryService.Iface dictionaryService;

	/**
	 * 当前用户组织权限
	 * @author yfy
	 * @date: 2016年01月15日 下午11:39:04
	 * @param model
	 * @param request
	 */
	public Map<String, Object> list(Long userId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//调用server端组织列表拉取接口
		FFTOrgListVoRes voRes = fftOrgService.getFFTOrgByUserId(userId);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			OrgVoListRes orgVoRes = null;
			ArrayList<OrgVoListRes> listVoRes = null;
			ArrayList<OrgVoListRes> resultlistVoRes = null;
			List<FFTOrgVo> fftOrgVoList = voRes.getVoList();
			if(fftOrgVoList != null && fftOrgVoList.size() > 0){
				List<Long> idList = new ArrayList<Long>();
				listVoRes = new ArrayList<OrgVoListRes>();
				for(FFTOrgVo fftOrgVo : fftOrgVoList){
					orgVoRes = new OrgVoListRes();
					// 封装数据
					BeanUtils.copyProperties(orgVoRes, fftOrgVo);
					idList.add(fftOrgVo.getId());
					listVoRes.add(orgVoRes);
				}
				//判断是否有子节点
				if(idList != null && idList.size() > 0){
					List<isNextFFTOrgVo> isNextfftOrgList = fftOrgService.isNextFFTOrg(userId,idList);
					for(OrgVoListRes orgVo : listVoRes){
						for(isNextFFTOrgVo isNextOrgVo : isNextfftOrgList){
							if(StringUtil.isNotBlank(orgVo.getId())){
								if(orgVo.getId().equals(isNextOrgVo.getId())){
									orgVo.setIsParent(isNextOrgVo.isIsNextFFTOrg());
								}
							}
						}
					}
				}
				if(listVoRes != null && listVoRes.size() > 0){
					resultlistVoRes = new ArrayList<OrgVoListRes>();
					for(OrgVoListRes orgVo : listVoRes){
						int a=0;
						for(OrgVoListRes orgVo1 : listVoRes){
							if(orgVo.getParentId().equals(orgVo1.getId())){
								a=1;
							}
						}
						if(a!=1){
							resultlistVoRes.add(orgVo);
						}
					}
				}
			}
			map.put("list", resultlistVoRes);
			map.put("success", Constants.RESULT_SUCCESS);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 组织机构管理分页列表查询
	 * @author yfy
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> listByPage(OrgVolistReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 封装查询条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(voReq.getPageNumber());
		pageVo.setPageSize(voReq.getPageSize());
		
		FFTOrgVo fftOrgVoReq = new FFTOrgVo();
		fftOrgVoReq.setTreePath(String.valueOf(voReq.getId()));
		if(StringUtil.isNotBlank(voReq.getOrgName())){
			fftOrgVoReq.setOrgName(voReq.getOrgName());
		}
		fftOrgVoReq.setIsDelete(false);//默认查询有效的组织
		//调用server端组织查询page接口
		FFTOrgPageVoRes voRes = fftOrgService.getFFTOrgByPage(pageVo,fftOrgVoReq);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			OrgVoRes orgVoRes = null;
			ArrayList<OrgVoRes> listVoRes = null;
			List<FFTOrgDetailVo> fftOrgDetailList = voRes.getVoList();
			if(fftOrgDetailList != null && fftOrgDetailList.size() > 0){
				listVoRes = new ArrayList<OrgVoRes>();
				for(FFTOrgDetailVo fftOrgDetailVo : fftOrgDetailList){
					orgVoRes = new OrgVoRes();
					// 封装数据
					BeanUtils.copyProperties(orgVoRes, fftOrgDetailVo);
					listVoRes.add(orgVoRes);
				}
			}
			// 封装分页数据
			Page page = new Page();
			if(voRes.getPage() != null){
				BeanUtils.copyProperties(page, voRes.getPage());
			}
			map.put("page", page);
			map.put("list", listVoRes);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 当前用户下的一级和二级组织信息树列表
	 * @author yfy
	 * @param userId 用户ID
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> twoOrgList(Long userId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//调用server端组织列表拉取接口
		FFTOrgListVoRes voRes = fftOrgService.getFFTOrgByUserIdTwoLevel(userId);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			OrgVoListRes orgVoRes = null;
			ArrayList<OrgVoListRes> listVoRes = null;
			List<FFTOrgVo> fftOrgVoList = voRes.getVoList();
			if(fftOrgVoList != null && fftOrgVoList.size() > 0){
				List<Long> idList = new ArrayList<Long>();
				listVoRes = new ArrayList<OrgVoListRes>();
				for(FFTOrgVo fftOrgVo : fftOrgVoList){
					orgVoRes = new OrgVoListRes();
					// 封装数据
					BeanUtils.copyProperties(orgVoRes, fftOrgVo);
					idList.add(fftOrgVo.getId());
					listVoRes.add(orgVoRes);
				}
				//判断是否有子节点
				if(idList != null && idList.size() > 0){
					List<isNextFFTOrgVo> isNextfftOrgList = fftOrgService.isNextFFTOrg(userId,idList);
					for(OrgVoListRes orgVo : listVoRes){
						for(isNextFFTOrgVo isNextOrgVo : isNextfftOrgList){
							if(StringUtil.isNotBlank(orgVo.getId())){
								if(orgVo.getId().equals(isNextOrgVo.getId())){
									orgVo.setIsParent(isNextOrgVo.isIsNextFFTOrg());
								}
							}
						}
					}
				}
			}
			map.put("list", listVoRes);
			map.put("success", Constants.RESULT_SUCCESS);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	/**
	 * 查询当前组织子节点
	 * @author yfy
	 * @param userId 用户ID
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> childOrgList(Long userId, String orgId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//调用server端组织列表拉取接口
		FFTOrgListVoRes voRes = fftOrgService.getFFTOrgByUserIdOrgId(userId, orgId);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			OrgVoListRes orgVoRes = null;
			ArrayList<OrgVoListRes> listVoRes = null;
			List<FFTOrgVo> fftOrgVoList = voRes.getVoList();
			if(fftOrgVoList != null && fftOrgVoList.size() > 0){
				List<Long> idList = new ArrayList<Long>();
				listVoRes = new ArrayList<OrgVoListRes>();
				for(FFTOrgVo fftOrgVo : fftOrgVoList){
					orgVoRes = new OrgVoListRes();
					// 封装数据
					BeanUtils.copyProperties(orgVoRes, fftOrgVo);
					idList.add(fftOrgVo.getId());
					listVoRes.add(orgVoRes);
				}
				//判断是否有子节点
				if(idList != null && idList.size() > 0){
					List<isNextFFTOrgVo> isNextfftOrgList = fftOrgService.isNextFFTOrg(userId,idList);
					for(OrgVoListRes orgVo : listVoRes){
						for(isNextFFTOrgVo isNextOrgVo : isNextfftOrgList){
							if(StringUtil.isNotBlank(orgVo.getId())){
								if(orgVo.getId().equals(isNextOrgVo.getId())){
									orgVo.setIsParent(isNextOrgVo.isIsNextFFTOrg());
								}
							}
						}
					}
				}
			}
			map.put("list", listVoRes);
			map.put("success", Constants.RESULT_SUCCESS);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 组织数据权限查询
	 * @author yfy
	 * @param orgId 组织ID
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> orgReList(String orgId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		OrgReVoRes orgReVoRes = null;
		ArrayList<OrgReVoRes> listVoRes = null;
		//调用server端组织数据权限查询接口
		FFTOrgReListVoRes voRes = fftOrgService.getFFTOrgReByOrgId(orgId);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			List<FFTOrgReVo> fftOrgReVoList = voRes.getVoList();
			if(fftOrgReVoList != null && fftOrgReVoList.size() > 0){
				listVoRes = new ArrayList<OrgReVoRes>();
				for(FFTOrgReVo fftOrgReVo : fftOrgReVoList){
					orgReVoRes = new OrgReVoRes();
					// 封装数据
					BeanUtils.copyProperties(orgReVoRes, fftOrgReVo);
					orgReVoRes.setReOrgIdTreePath(fftOrgReVo.getReOrgIdtreePath());
					listVoRes.add(orgReVoRes);
				}
			}
			map.put("list", listVoRes);
			map.put("success", Constants.RESULT_SUCCESS);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 根据组织ID查询组织角色
	 * @author yfy
	 * @param orgId 组织ID
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> orgRoleList(String orgId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		OrgRoleVoRes orgRoleVoRes = null;
		ArrayList<OrgRoleVoRes> listVoRes = null;
		//调用server端组织数据权限查询接口
		OrgRoleListVoRes voRes = fftOrgService.getOrgRoleByOrgId(orgId);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			List<OrgRoleVo> orgRoleVoList = voRes.getVoList();
			if(orgRoleVoList != null && orgRoleVoList.size() > 0){
				listVoRes = new ArrayList<OrgRoleVoRes>();
				for(OrgRoleVo orgRoleVo : orgRoleVoList){
					orgRoleVoRes = new OrgRoleVoRes();
					// 封装数据
					BeanUtils.copyProperties(orgRoleVoRes, orgRoleVo);
					listVoRes.add(orgRoleVoRes);
				}
			}
			map.put("list", listVoRes);
			map.put("success", Constants.RESULT_SUCCESS);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 根据组织ID集合数组查询组织角色
	 * @author yfy
	 * @param orgIds 组织ID集合
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> orgRoleList(String[] orgIds) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> ids = new ArrayList<String>();
		for(String str : orgIds){
			ids.add(str);
		}
		ArrayList<Long> roleListRes = null;
		//调用server端组织数据权限查询接口
		OrgRoleIdListVoRes voRes = fftOrgService.getOrgRoleIds(ids);
		if(voRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			List<Long> roleList = voRes.getRoleIdVoList();
			if(roleList != null && roleList.size() > 0){
				roleListRes = new ArrayList<Long>();
				for(Long roleId : roleList){
					roleListRes.add(roleId);
				}
			}
			map.put("list", roleListRes);
			map.put("success", Constants.RESULT_SUCCESS);
		}else{
			throw new BossException(voRes.getResultVo().getResultCode(), voRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 组织机构管理新增
	 * @author yfy
	 * @param voReq
	 * @param originVo
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @throws ParseException 
	 */
	public Map<String, Object> add(OrgVoReq voReq, OriginVo originVo) throws TException, BossException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FFTOrgVo fftOrgVo = new FFTOrgVo();
		//封装数据基本信息
		BeanUtils.copyProperties(fftOrgVo, voReq);
		if(StringUtil.isNotBlank(voReq.getLevel()) && voReq.getLevel().equals(1)){
			fftOrgVo.setPlatform(Platform.boss.getCode());//只能新增boss,初始化bank
		}
		//角色分配
		List<Long> roleIds = new ArrayList<Long>();
		if(voReq.getRoleIds() != null && voReq.getRoleIds().size() > 0){
			roleIds = voReq.getRoleIds();
		}
		//数据权限
		List<String> reOrgIds = new ArrayList<String>();
		if(voReq.getReOrgIds() != null && voReq.getReOrgIds().size() > 0){
			reOrgIds = voReq.getReOrgIds();
		}
		//如果平台platform为bank的时候即银行组织则需要同步新增银行机构对应的虚拟商户、银行机构对应的虚拟门店和银行机构
		if(fftOrgVo.getPlatform().equals(Platform.bank.getCode())){
			// 新增机构对应的虚拟商户
			MerchantAddVoRes merchantVo = this.merchantAdd(voReq, originVo);
			LogCvt.info("同步新增银行机构对应的虚拟商户===》"+merchantVo.getMerchantId());
			if (Constants.RESULT_SUCCESS.equals(merchantVo.getResult().getResultCode())){
				// 新增机构对应的虚拟门店
				OutletAddVoRes outletVo = this.outletAdd(voReq, originVo,merchantVo.getMerchantId());
				LogCvt.info("同步新增银行机构对应的虚拟门店===》"+outletVo.getOutletId());
				if (Constants.RESULT_SUCCESS.equals(outletVo.getResult().getResultCode())){
					// 同步新增银行机构
					CommonAddVoRes bankOrgVo = this.bankOrgAdd(voReq,originVo,merchantVo.getMerchantId(),outletVo.getOutletId());
					LogCvt.info("同步新增银行机构===》"+bankOrgVo.getId());
					if(Constants.RESULT_SUCCESS.equals(bankOrgVo.getResult().getResultCode())){
						fftOrgVo.setOrgId(String.valueOf(bankOrgVo.getId()));
						//调用server端组织新增接口
						CommonAddVoRes addRes = fftOrgService.addFFTOrg(originVo,fftOrgVo,roleIds,reOrgIds);
						if(Constants.RESULT_SUCCESS.equals(addRes.getResult().getResultCode())){
							map.put("id", addRes.getId());
							map.put("code", Constants.RESULT_SUCCESS);
							map.put("message", "新增组织信息成功");
						}else{
							LogCvt.info("新增组织失败，删除机构对应的虚拟商户===》"+merchantVo.getMerchantId());
							merchantService.removeMerchant(originVo, merchantVo.getMerchantId());
							LogCvt.info("新增组织失败，删除机构对应的虚拟门店===》"+outletVo.getOutletId());
							outletService.removeOutlet(originVo, outletVo.getOutletId());
							LogCvt.info("新增组织失败，删除同步的银行机构===》"+bankOrgVo.getId());
							OrgVo orgVo = new OrgVo();
							orgVo.setId(bankOrgVo.getId());
							orgVo.setIsEnable(false);
							orgService.deleteOrg(originVo, orgVo);
							throw new BossException(addRes.getResult().getResultCode(), addRes.getResult().getResultDesc());
						}
					}else{
						LogCvt.info("同步新增银行机构失败，删除机构对应的虚拟商户===》"+merchantVo.getMerchantId());
						merchantService.removeMerchant(originVo, merchantVo.getMerchantId());
						LogCvt.info("同步新增银行机构失败，删除机构对应的虚拟门店===》"+outletVo.getOutletId());
						outletService.removeOutlet(originVo, outletVo.getOutletId());
						throw new BossException(bankOrgVo.getResult().getResultCode(), bankOrgVo.getResult().getResultDesc());
					}
				}else{
					LogCvt.info("新增门店失败，删除机构对应的虚拟商户===》"+merchantVo.getMerchantId());
					merchantService.removeMerchant(originVo, merchantVo.getMerchantId());
					throw new BossException(outletVo.getResult().getResultCode(), outletVo.getResult().getResultDesc());
				}
			}else{
				throw new BossException(merchantVo.getResult().getResultCode(), merchantVo.getResult().getResultDesc());
			}
		}else{
			//调用server端组织新增接口
			CommonAddVoRes addRes = fftOrgService.addFFTOrg(originVo,fftOrgVo,roleIds,reOrgIds);
			if(Constants.RESULT_SUCCESS.equals(addRes.getResult().getResultCode())){
				map.put("id", addRes.getId());
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", "新增组织信息成功");
			}else{
				throw new BossException(addRes.getResult().getResultCode(), addRes.getResult().getResultDesc());
			}
		}
		return map;
	}
	
	/**
	 * 组织机构管理修改
	 * @author yfy
	 * @param voReq
	 * @param originVo
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> update(OrgVoReq voReq, OriginVo originVo) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		FFTOrgVo fftOrgVo = new FFTOrgVo();
		//封装数据基本信息
		BeanUtils.copyProperties(fftOrgVo, voReq);
		//角色分配
		List<Long> roleIds = new ArrayList<Long>();
		if(voReq.getRoleIds() != null && voReq.getRoleIds().size() > 0){
			roleIds = voReq.getRoleIds();
		}
		//数据权限
		List<String> reOrgIds = new ArrayList<String>();
		if(voReq.getReOrgIds() != null && voReq.getReOrgIds().size() > 0){
			reOrgIds = voReq.getReOrgIds();
		}
		//如果平台platform为bank的时候即银行组织则需要同步修改银行机构对应的虚拟商户、银行机构对应的虚拟门店和银行机构
		if(voReq.getPlatform().equals(Platform.bank.getCode())){
			//调用server端组织详情接口
			FFTOrgDetailVo detailRes = fftOrgService.getFFTOrgDetail(voReq.getId(),null);
			if(detailRes!= null && StringUtil.isNotBlank(detailRes.getOrgId())){
				if(!voReq.getOrgName().equals(detailRes.getOrgName())//组织名称
					|| !voReq.getPhone().equals(detailRes.getPhone())//电话号码
					|| voReq.getAreaId() != detailRes.getAreaId()//地区ID
					|| !voReq.getAddress().equals(detailRes.getAddress())){//地址
					String merchantId = "";
					String outletId = "";
					ResultVo resultVo = null;
					OrgVo orgVo = new OrgVo();
					orgVo.setId(Long.valueOf(detailRes.getOrgId()));
					List<OrgVo> orgList = orgService.getOrg(orgVo);
					if(orgList != null && orgList.size() > 0){
						merchantId = orgList.get(0).getMerchantId();
						outletId = orgList.get(0).getOutletId();
					}
					if(StringUtil.isNotBlank(merchantId)){
						resultVo = this.merchantUpdate(voReq, originVo, merchantId);
						LogCvt.info("同步修改银行机构对应的虚拟商户===》"+resultVo.getResultCode());
						if(!Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
							throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
						}
					}
					if(StringUtil.isNotBlank(outletId)){
						resultVo = this.outletUpdate(voReq, originVo, merchantId, outletId);
						LogCvt.info("同步修改银行机构对应的虚拟门店===》"+resultVo.getResultCode());
						if(!Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
							throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
						}
					}
					voReq.setOrgId(detailRes.getOrgId());
					resultVo = this.bankOrgUpdate(voReq, originVo, merchantId, outletId);
					LogCvt.info("同步修改银行机构===》"+resultVo.getResultCode());
					if(!Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
						throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
					}
				}
			}	
		}
		//调用server端组织修改接口
		ResultVo resultVo = fftOrgService.updateFFTOrg(originVo,fftOrgVo,roleIds,reOrgIds);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "修改组织信息成功");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 组织机构管理详情
	 * @author yfy
	 * @param id 主键ID
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> detail(Long id,String orgId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isNotBlank(orgId)){
			id = 0l;
		}
		OrgVoRes voRes = null;
		//调用server端组织详情接口
		FFTOrgDetailVo detailRes = fftOrgService.getFFTOrgDetail(id,orgId);
		if(detailRes != null && StringUtil.isNotBlank(detailRes.getId())){
			voRes = new OrgVoRes();
			//封装数据基本信息
			BeanUtils.copyProperties(voRes, detailRes);
			if(StringUtils.isNotEmpty(detailRes.getTreePathName())){
				voRes.setParentName(detailRes.getTreePathName().replaceAll(",|，", "->"));
			}
			//获取省市区
			AreaVo areaVo = areaService.findAreaById(detailRes.getAreaId());
			if(areaVo != null && areaVo.getId() != 0){
				String[] treePath = null;	
				if(StringUtils.isNotEmpty(areaVo.getTreePath())){
					treePath = areaVo.getTreePath().split(",");
					if(treePath.length > 2){
						voRes.setAreaCode(treePath[0]);//省
						voRes.setCityCode(treePath[1]);//市
						voRes.setCountyCode(String.valueOf(areaVo.getId()));//区
					}else if(treePath.length > 1){
						voRes.setAreaCode(String.valueOf(areaVo.getParentId()));//省
						voRes.setCityCode(String.valueOf(areaVo.getId()));//市
					}else{
						voRes.setAreaCode(String.valueOf(areaVo.getId()));//省
					}
				}
			}
		}
		map.put("org", voRes);
		return map;
	}
	
	/**
	 * 组织机构管理删除
	 * @author yfy
	 * @param id 主键ID
	 * @param originVo
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> delete(Long id, OriginVo originVo) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		//调用server端组织删除接口
		ResultVo resultVo = fftOrgService.deleteFFTOrg(originVo,id);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "已删除该组织机构信息");
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 组织级别类型查询
	 * @author yfy
	 * @param dicCode 字典编码  组织级别类型传level
	 * @param dicCode
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> selectLevel(String dicCode, String clientId) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<HashMap<String, Object>> dicList = null;
		
		//调用server端字典查询删除接口
		List<DictionaryItemVo> dicVoList = dictionaryService.getDictionaryItemList(dicCode, clientId);
		if(dicVoList != null && dicVoList.size() > 0){
			dicList = new ArrayList<HashMap<String, Object>>();
			for(DictionaryItemVo dicVo : dicVoList){
				HashMap<String, Object> temp = new HashMap<String, Object>();
				temp.put("itemName", dicVo.getItemName());
				temp.put("itemValue", dicVo.getItemValue());
				dicList.add(temp);
			}
		}
		map.put("levelList", dicList);
		return map;
	}
	
	/**
	 * 新增机构对应虚拟商户
	 * @param voReq
	 * @param vo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	private MerchantAddVoRes merchantAdd(OrgVoReq voReq, OriginVo vo) throws TException, ParseException {
		MerchantVoReq merchantReq = new MerchantVoReq();
		MerchantVo merchant = new MerchantVo();
		merchant.setClientId(voReq.getClientId());// 客户端ID
		merchant.setMerchantName(voReq.getOrgName());// 商户名
		merchant.setMerchantFullname(voReq.getOrgName());//商户全名
		merchant.setOrgCode(voReq.getCode());// 所属组织机构代码
		merchant.setProOrgCode(voReq.getProCode());// 一级
		merchant.setCityOrgCode(voReq.getCityCode());// 二级
		merchant.setCountyOrgCode(voReq.getCountyCode());// 三级
		
		Integer orgLevel = voReq.getLevel(); // 机构级别
		switch (orgLevel) {
		case 1: // 省级机构
			merchant.setProOrgCode(merchant.getOrgCode());
			break;
		case 2: // 市级机构
			merchant.setCityOrgCode(merchant.getOrgCode());
			break;
		case 3: // 区级机构
			merchant.setCountyOrgCode(merchant.getOrgCode());
			break;
		case 4: // 网点
			merchant.setOrgCode(merchant.getOrgCode());
			break;
		default: // 默认
			merchant.setOrgCode(merchant.getOrgCode());
			break;
		}
		// 默认非外包
		merchant.setIsOutsource(false);
		merchant.setPhone(voReq.getPhone());// 商户电话
		merchant.setContactPhone(voReq.getPhone());// 联系人手机
		merchant.setAreaId(voReq.getAreaId());// 所在地区
		merchant.setAddress(voReq.getAddress());// 详细地址
		merchant.setMerchantStatus(true);// 是银行机构对应商户
		merchant.setAuditState(AuditFlagEnum.ACCEPTED.getCode());// 审核状态
		merchant.setAuditStartOrgCode("0");// 审核开始机构
		merchant.setAuditEndOrgCode("0");// 审核结束机构
		merchant.setIsEnable(true);// 有效
		merchant.setAuditOrgCode("0");// 待审核机构编号
		merchant.setIsTop(false);// 是否置顶
		merchant.setContractStaff("");
		merchant.setComplaintPhone("");
		merchant.setContractBegintime(System.currentTimeMillis());// 签约当前时间
		merchant.setContractEndtime(PramasUtil.DateFormat("9999-12-31"));// 签约到期时间
		merchantReq.setMerchantVo(merchant);
		merchantReq.setCategoryInfoVoList(null);
		merchantReq.setTypeInfoVoList(null);
		// 新增银行机构对应虚拟商户
		return merchantService.addMerchant(vo, merchantReq);
	}

	/**
	 * 修改银行机构对应虚拟商户
	 * @param voReq
	 * @param vo
	 * @param merchantId
	 * @return
	 * @throws TException
	 */
	private ResultVo merchantUpdate(OrgVoReq voReq, OriginVo vo,String merchantId) throws TException {
		MerchantVoReq merchantReq = new MerchantVoReq();
		MerchantVo merchant = new MerchantVo();
		merchant.setClientId(voReq.getClientId());// 客户端ID
		merchant.setMerchantId(merchantId);// 商户ID
		merchant.setMerchantName(voReq.getOrgName());// 商户名
		merchant.setMerchantFullname(voReq.getOrgName());// 商户全名
		merchant.setOrgCode(voReq.getCode());// 所属组织机构代码
		merchant.setProOrgCode(voReq.getProCode());// 一级
		merchant.setCityOrgCode(voReq.getCityCode());// 二级
		merchant.setCountyOrgCode(voReq.getCountyCode());// 三级
		
		Integer orgLevel = voReq.getLevel(); // 机构级别
		switch (orgLevel) {
		case 1: // 省级机构
			merchant.setProOrgCode(merchant.getOrgCode());
			break;
		case 2: // 市级机构
			merchant.setCityOrgCode(merchant.getOrgCode());
			break;
		case 3: // 区级机构
			merchant.setCountyOrgCode(merchant.getOrgCode());
			break;
		case 4: // 网点
			merchant.setOrgCode(merchant.getOrgCode());
			break;
		default: // 默认
			merchant.setOrgCode(merchant.getOrgCode());
			break;
		}
		// 默认非外包
		merchant.setIsOutsource(false);
		merchant.setPhone(voReq.getPhone());// 商户电话
		merchant.setAreaId(voReq.getAreaId());// 所在地区
		merchant.setAddress(voReq.getAddress());// 详细地址
		merchant.setMerchantStatus(true);// 是银行机构对应商户
		merchant.setAuditState(AuditFlagEnum.ACCEPTED.getCode());// 审核状态
		merchant.setAuditStartOrgCode("0");// 审核开始机构
		merchant.setAuditEndOrgCode("0");// 审核结束机构
		merchant.setIsEnable(true);// 有效
		merchant.setAuditOrgCode("0");// 待审核机构编号
		merchant.setIsTop(false);// 是否置顶
		merchantReq.setMerchantVo(merchant);
		merchantReq.setCategoryInfoVoList(null);
		merchantReq.setTypeInfoVoList(null);
		// 修改银行机构对应虚拟商户
		return merchantService.updateMerchant(vo, merchantReq);
	}

	/**
	 * 新增银行机构对应门店
	 * @param voReq
	 * @param vo
	 * @param merchantId
	 * @return
	 * @throws TException
	 */
	public OutletAddVoRes outletAdd(OrgVoReq voReq, OriginVo vo,String merchantId) throws TException {
		OutletVo outlet = new OutletVo();
		outlet.setClientId(voReq.getClientId());// 客户端ID
		outlet.setMerchantId(merchantId);// 商户ID
		outlet.setOutletName(voReq.getOrgName());// 门店名称
		outlet.setOutletFullname(voReq.getOrgName());// 门店全名
		outlet.setOutletStatus(true);// 是否银行机构对应门店
		outlet.setAreaId(voReq.getAreaId());// 地区
		outlet.setAddress(voReq.getAddress());// 详细地址
		outlet.setPhone(voReq.getPhone());// 电话
		outlet.setIsEnable(true);// 是否有效
		// 新增银行机构对应虚拟门店
		return outletService.addOutlet(vo, outlet);
	}

	/**
	 * 修改机构对应门店
	 * @param voReq
	 * @param vo
	 * @param merchantId
	 * @param outletId
	 * @return
	 * @throws TException
	 */
	public ResultVo outletUpdate(OrgVoReq voReq, OriginVo vo,String merchantId,String outletId) throws TException {
		OutletVo outlet = new OutletVo();
		outlet.setClientId(voReq.getClientId());// 客户端ID
		outlet.setMerchantId(merchantId);// 商户ID
		outlet.setOutletId(outletId);// 门店ID
		outlet.setOutletName(voReq.getOrgName());// 门店名称
		outlet.setOutletFullname(voReq.getOrgName());// 门店全名
		outlet.setAreaId(voReq.getAreaId());// 地区
		outlet.setAddress(voReq.getAddress());// 详细地址
		outlet.setPhone(voReq.getPhone());// 电话
		outlet.setIsEnable(true);// 是否有效
		// 修改银行机构对应虚拟门店
		return outletService.updateOutlet(vo, outlet);
	}
	
	/**
	 * 同步新增银行机构
	 * @param voReq
	 * @param vo
	 * @param merchantId
	 * @param outletId
	 * @return
	 * @throws TException
	 */
	public CommonAddVoRes bankOrgAdd(OrgVoReq voReq, OriginVo vo,String merchantId,String outletId) throws TException {
		// 新增机构信息表
		OrgVo orgVo = new OrgVo();
		// 默认非外包公司
		orgVo.setClientId(voReq.getClientId());// 客户端ID
		orgVo.setOrgCode(voReq.getCode());// 机构号
		orgVo.setOrgName(voReq.getOrgName());// 机构名称
		orgVo.setOrgLevel(String.valueOf(voReq.getLevel()));// 机构级别
		orgVo.setProvinceAgency(voReq.getProCode());// 一级
		orgVo.setCityAgency(voReq.getCityCode());// 二级
		orgVo.setCountyAgency(voReq.getCountyCode());// 三级
		orgVo.setMerchantId(merchantId);//对应的虚拟商户
		orgVo.setOutletId(outletId);//对应的虚拟门店
		orgVo.setPhone(voReq.getPhone());// 电话
		orgVo.setAreaId(voReq.getAreaId());// 地区ID
		orgVo.setOrgType(true);// 机构类型
		orgVo.setNeedReview(false);// 是否需要双人审核
		// 同步新增银行机构
		return orgService.addOrg(vo, orgVo);
	}

	/**
	 * 同步修改银行机构
	 * @param voReq
	 * @param vo
	 * @param merchantId
	 * @param outletId
	 * @return
	 * @throws TException
	 */
	public ResultVo bankOrgUpdate(OrgVoReq voReq, OriginVo vo,String merchantId,String outletId) throws TException {
		// 修改机构信息
		OrgVo orgVo = new OrgVo();
		orgVo.setId(Long.valueOf(voReq.getOrgId()));//主键ID
		// 默认非外包公司
		orgVo.setClientId(voReq.getClientId());// 客户端ID
		orgVo.setOrgCode(voReq.getCode());// 机构号
		orgVo.setOrgName(voReq.getOrgName());// 机构名称
		orgVo.setOrgLevel(String.valueOf(voReq.getLevel()));// 机构级别
		orgVo.setProvinceAgency(voReq.getProCode());// 一级
		orgVo.setCityAgency(voReq.getCityCode());// 二级
		orgVo.setCountyAgency(voReq.getCountyCode());// 三级
		orgVo.setMerchantId(merchantId);//对应的虚拟商户
		orgVo.setOutletId(outletId);//对应的虚拟门店
		orgVo.setPhone(voReq.getPhone());// 电话
		orgVo.setAreaId(voReq.getAreaId());// 地区ID
		orgVo.setOrgType(true);// 机构类型
		orgVo.setNeedReview(false);// 是否需要双人审核
		// 同步修改银行机构
		return orgService.updateOrg(vo, orgVo);
	}
}
