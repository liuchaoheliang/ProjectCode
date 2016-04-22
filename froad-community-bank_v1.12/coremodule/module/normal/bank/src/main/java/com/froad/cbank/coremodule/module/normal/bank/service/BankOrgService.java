package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.AreaVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrg4MistyQuery;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.InvalidProductBatchVo;
import com.froad.thrift.vo.MerchantAddVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OrgPageVoRes;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletAddVoRes;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 银行机构信息
 * 
 * @author yfy
 * @date 2015-03-25 上午 9:35:31
 */
@Service
public class BankOrgService {

	@Resource
	OrgService.Iface orgService;
	@Resource
	OutletService.Iface outletService;
	@Resource
	MerchantService.Iface merchantService;
	@Resource
	AreaService.Iface areaService;
	@Resource
	ProductService.Iface productService;
	@Resource
	private LoginService loginService;

	/**
	 * OrgVo 换成成Map 2015年12月2日 下午1:40:34 <br/>
	 * 
	 * @author KaiweiXiang
	 * @param org
	 * @param partenOrgCode
	 * @return
	 */
	private Map<String, Object> convertOrgVoToMap(OrgVo org,
			String partenOrgCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", org.getId());
		map.put("orgCode", org.getOrgCode());
		map.put("orgName", org.getOrgName());

		if (StringUtil.isNotBlank(partenOrgCode)) {
			map.put("partenOrgCode", partenOrgCode);
		} else {
			// 设置父节点
			if (StringUtil.equals(org.getOrgLevel(), "2")
					&& org.getProvinceAgency() != null) {// 一级机构号
				map.put("partenOrgCode", org.getProvinceAgency());
			} else if (StringUtil.equals(org.getOrgLevel(), "3")
					&& org.getCityAgency() != null) {// 二级机构号
				map.put("partenOrgCode", org.getCityAgency());
			} else if (StringUtil.equals(org.getOrgLevel(), "4")
					&& org.getCountyAgency() != null) {// 三级机构号
				map.put("partenOrgCode", org.getCountyAgency());
			} else {// 顶级级
				map.put("partenOrgCode", "0");// 机构为总行上级机构为空
			}
		}
		return map;
	}

	/**
	 * 
	 * 按条件查询所有
	 * 
	 * @param orgName
	 * @param orgCode
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 */
	public List<Map<String, Object>> findOrgTree(BankOrgVoReq voReq)
			throws TException {

		PageVo page = new PageVo();
		page.setPageNumber(1);
		page.setPageSize(10000);

		// 查询条件
		OrgVo orgVo = new OrgVo();
		orgVo.setClientId(voReq.getClientId());
		orgVo.setIsEnable(true);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		try {
			OrgPageVoRes orgPage = orgService.getOrgByPage(page, orgVo,
					voReq.getMyOrgCode());
			List<OrgVo> orgList = orgPage.getOrgVoList();
			if (orgList != null && orgList.size() > 0) {
				for (OrgVo org : orgList) {
					resultList.add(convertOrgVoToMap(org, null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 
	 * 根据父节点查询子节点，并且把该父节点也加入集合返回
	 * 
	 * @param orgName
	 * @param orgCode
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 */
	public List<Map<String, Object>> findOrgTreeByOrgCode(BankOrgVoReq voReq)
			throws Exception {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (voReq == null || StringUtils.isEmpty(voReq.getOrgCode())) {
			return resultList;
		}

		// 查询当前机构节点
		OrgVo orgVo = orgService.getOrgById(voReq.getClientId(),
				voReq.getOrgCode());
		if (orgVo == null) {
			LogCvt.info("根据机构号查询结果为空，OrgCode=" + voReq.getOrgCode());
			return resultList;
		}
		resultList.add(convertOrgVoToMap(orgVo, null));

		// 查询子机构
		List<OrgVo> orgList = orgService.getAllSubOrgs(voReq.getClientId(),
				voReq.getOrgCode());
		if (orgList != null && orgList.size() > 0) {
			for (OrgVo org : orgList) {
				resultList.add(convertOrgVoToMap(org, voReq.getOrgCode()));
			}
		} else {
			LogCvt.info("根据机构号获取所有下属机构为空，parentCode=" + voReq.getOrgCode());
		}
		return resultList;
	}

	/**
	 * 
	 * 分页列表条件查询
	 * 
	 * @param orgName
	 * @param orgCode
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 */
	public QueryResult<BankOrgVoRes> findPageByConditions(BankOrgVoReq voReq)
			throws TException {

		PageVo page = new PageVo();
		page.setPageNumber(voReq.getPageNumber());
		page.setPageSize(voReq.getPageSize());
		page.setLastPageNumber(voReq.getLastPageNumber());
		page.setFirstRecordTime(voReq.getFirstRecordTime());
		page.setLastRecordTime(voReq.getLastRecordTime());

		// 查询条件
		OrgVo orgVo = new OrgVo();
		orgVo.setClientId(voReq.getClientId());
		if (StringUtils.isNotEmpty(voReq.getOrgCode())) {
			orgVo.setOrgCode(voReq.getOrgCode());
		}
		if (StringUtils.isNotEmpty(voReq.getOrgName())) {//// 如果传入下orgName则需要查询orgCode以及下属机构
			orgVo.setOrgName(voReq.getOrgName());
			voReq.setMyOrgCode(orgVo.getOrgCode());
			orgVo.setOrgCode(null);
		}
		if (voReq.getState()) {
			orgVo.setIsEnable(true);
		} else {
			orgVo.setIsEnable(false);
		}
		List<String> orgCodeList = new ArrayList<String>();
		QueryResult<BankOrgVoRes> queryVo = new QueryResult<BankOrgVoRes>();
		List<BankOrgVoRes> bankOrgList = new ArrayList<BankOrgVoRes>();
		OrgPageVoRes orgPage = orgService.getOrgByPage(page, orgVo,
				voReq.getMyOrgCode());
		List<OrgVo> orgList = orgPage.getOrgVoList();
		if (orgList != null && orgList.size() > 0) {
			for (OrgVo org : orgList) {
				BankOrgVoRes bankOrg = new BankOrgVoRes();
				bankOrg.setOrgId(org.getId());// 机构ID
				bankOrg.setOrgCode(org.getOrgCode());// 机构号
				bankOrg.setOrgName(org.getOrgName());// 机构名
				bankOrg.setState(org.isIsEnable());// 状态
				bankOrg.setOrgLevel(org.getOrgLevel());// 机构级别
				bankOrg.setOrgType(org.isOrgType()); // 机构类型
				if (StringUtil.equals(org.getOrgLevel(), "2")
						&& org.getProvinceAgency() != null) {
					bankOrg.setPartenOrgCode(org.getProvinceAgency());
					orgCodeList.add(org.getProvinceAgency());// 一级机构号
				} else if (StringUtil.equals(org.getOrgLevel(), "3")
						&& org.getCityAgency() != null) {
					bankOrg.setPartenOrgCode(org.getCityAgency());
					orgCodeList.add(org.getCityAgency());// 二级机构号
				} else if (StringUtil.equals(org.getOrgLevel(), "4")
						&& org.getCountyAgency() != null) {
					bankOrg.setPartenOrgCode(org.getCountyAgency());
					orgCodeList.add(org.getCountyAgency());// 三级机构号
				} else {// 一级
					bankOrg.setPartenOrgCode("");// 机构为总行上级机构为空
				}
				bankOrgList.add(bankOrg);
			}
			if (orgCodeList != null && orgCodeList.size() > 0) {
				List<BankOrgVo> orgVoList = this
						.getByOrgCode(voReq.getClientId(), orgCodeList);
				for (BankOrgVoRes voRes : bankOrgList) {
					for (BankOrgVo bankorgVo : orgVoList) {
						if (StringUtil.isNotEmpty(voRes.getPartenOrgCode())) {
							if (voRes.getPartenOrgCode()
									.equals(bankorgVo.getOrgCode())) {
								voRes.setPartenOrgCode(bankorgVo.getOrgName());
							}
						}
					}
				}
			}
			if (orgPage.getPage() != null) {
				queryVo.setPageCount(orgPage.getPage().getPageCount());
				queryVo.setTotalCount(orgPage.getPage().getTotalCount());
				queryVo.setPageNumber(orgPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(
						orgPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(
						orgPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(
						orgPage.getPage().getLastRecordTime());
			}
		}
		queryVo.setResult(bankOrgList);
		return queryVo;
	}

	/**
	 * 新增
	 * 
	 * @param bankOrg
	 * @return
	 */
	public MsgVo bankOrgAdd(BankOrgVo bankOrg, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {

			// 新增机构信息表
			OrgVo orgVo = new OrgVo();
			// 默认非外包公司
			orgVo.setClientId(bankOrg.getClientId());// 客户端ID
			orgVo.setOrgCode(bankOrg.getOrgCode());// 机构号
			orgVo.setOrgName(bankOrg.getOrgName());// 机构名称
			orgVo.setOrgLevel(bankOrg.getOrgLevel());// 机构级别
			if (StringUtil.equals(bankOrg.getOrgLevel(), "2")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "3")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "4")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
				orgVo.setCountyAgency(bankOrg.getCountyAgency());// 三级机构
			}
			orgVo.setPhone(bankOrg.getTel());// 电话
			orgVo.setAreaId(bankOrg.getCountyCode());// 地区ID
			orgVo.setOrgType(bankOrg.getOrgType());// 机构类型
			// 是否需要双人审核
			orgVo.setNeedReview(bankOrg.getIsMutualAudit());
			// 新增商户表
			LogCvt.info("======新增机构对应商户:开始======");
			MerchantAddVoRes merchantVo = this.merchantAdd(bankOrg, vo);
			LogCvt.info("======新增机构对应商户:结束======");
			if (merchantVo.getResult().getResultCode()
					.equals(EnumTypes.success.getCode())) {
				orgVo.setMerchantId(merchantVo.getMerchantId());// 商户ID
				bankOrg.setMerchantId(merchantVo.getMerchantId());
				// 新增门店表
				LogCvt.info("======新增机构对应门店:开始======");
				OutletAddVoRes outletVo = this.outletAdd(bankOrg, vo);
				LogCvt.info("======新增机构对应门店:结束======");
				if (outletVo.getResult().getResultCode()
						.equals(EnumTypes.success.getCode())) {
					orgVo.setOutletId(outletVo.getOutletId());// 门店ID
					// 新增机构信息
					LogCvt.info("======新增机构:开始======");
					CommonAddVoRes resultVo = orgService.addOrg(vo, orgVo);
					LogCvt.info("======新增机构:结束======");
					if (resultVo.getResult().getResultCode()
							.equals(EnumTypes.success.getCode())) {
						msgVo.setResult(true);
						msgVo.setMsg("新增成功!");
					} else {
						LogCvt.info("======新增失败，删除对应商户:开始======");
						merchantService.removeMerchant(vo,
								merchantVo.merchantId);
						LogCvt.info("======新增失败，删除对应商户:结束======");
						LogCvt.info("======新增失败，删除对应门店:开始======");
						outletService.removeOutlet(vo, outletVo.getOutletId());
						LogCvt.info("======新增失败，删除对应门店:结束======");
						msgVo.setResult(false);
						msgVo.setMsg(resultVo.getResult().getResultDesc());
					}
				} else {
					LogCvt.info("======新增失败，删除对应商户:开始======");
					merchantService.removeMerchant(vo, merchantVo.merchantId);
					LogCvt.info("======新增失败，删除对应商户:结束======");
					msgVo.setResult(false);
					msgVo.setMsg(outletVo.getResult().getResultDesc());
				}
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(merchantVo.getResult().getResultDesc());
			}

		} catch (Exception e) {
			LogCvt.info("银行机构信息新增" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行机构信息新增异常");
		}
		return msgVo;
	}

	/**
	 * 
	 * bankOrgAdd4Department:部门机构的添加方法
	 *
	 * @author 明灿 2015年8月21日 下午2:23:59
	 * @param bankOrg
	 * @param vo
	 * @return
	 *
	 */
	public MsgVo bankOrgAdd4Department(BankOrgVo bankOrg, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			// 新增机构信息表
			OrgVo orgVo = new OrgVo();
			// 部门机构修改
			orgVo.setOrgType(false);
			orgVo.setMerchantId("");
			orgVo.setClientId(bankOrg.getClientId());// 客户端ID
			orgVo.setOrgCode(bankOrg.getOrgCode());// 机构号
			orgVo.setOrgName(bankOrg.getOrgName());// 机构名称
			orgVo.setOrgLevel(bankOrg.getOrgLevel());// 机构级别
			if (StringUtil.equals(bankOrg.getOrgLevel(), "2")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "3")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "4")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
				orgVo.setCountyAgency(bankOrg.getCountyAgency());// 三级机构
			}
			orgVo.setPhone(bankOrg.getTel());// 电话
			orgVo.setAreaId(bankOrg.getCountyCode());// 地区ID
			// orgVo.setOrgType(bankOrg.getOrgType());// 机构类型
			// 是否需要双人审核
			orgVo.setNeedReview(bankOrg.getIsMutualAudit());
			// 新增机构信息
			LogCvt.info("======新增机构:开始======");
			CommonAddVoRes resultVo = orgService.addOrg(vo, orgVo);
			LogCvt.info("======新增机构:结束======");
			if (EnumTypes.success.getCode()
					.equals(resultVo.getResult().getResultCode())) {
				msgVo.setResult(true);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResult().getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息新增" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行机构信息新增异常");
		}
		return msgVo;
	}

	/**
	 * 修改
	 * 
	 * @param bankOrg
	 * @return
	 */
	public MsgVo bankOrgUpdate(BankOrgVo bankOrg, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			// 修改机构信息
			OrgVo orgVo = new OrgVo();
			orgVo.setClientId(bankOrg.getClientId());
			orgVo.setId(bankOrg.getOrgId());// 机构ID
			orgVo.setOrgCode(bankOrg.getOrgCode());// 机构号
			orgVo.setOrgName(bankOrg.getOrgName());// 机构名称
			orgVo.setOrgLevel(bankOrg.getOrgLevel());// 机构级别
			if (StringUtil.equals(bankOrg.getOrgLevel(), "2")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "3")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "4")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
				orgVo.setCountyAgency(bankOrg.getCountyAgency());// 三级机构
			}
			orgVo.setPhone(bankOrg.getTel());// 电话
			orgVo.setAreaId(bankOrg.getCountyCode());// 地区ID
			orgVo.setOrgType(bankOrg.getOrgType());// 机构类型
			// 是否需要双人审核
			orgVo.setNeedReview(bankOrg.getIsMutualAudit());

			ResultVo resultVo = new ResultVo();
			LogCvt.info("======修改机构对应商户:开始======");
			if (StringUtil.isNotEmpty(bankOrg.getMerchantId())) {// 修改机构对应商户
				resultVo = this.merchantUpdate(bankOrg, vo);
				if (!resultVo.getResultCode()
						.equals(EnumTypes.success.getCode())) {
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
					return msgVo;
				}
				// }else{
				// MerchantAddVoRes merchantVo = merchantAdd(bankOrg,vo);
				// if(!merchantVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				// msgVo.setResult(false);
				// msgVo.setMsg(merchantVo.getResult().getResultDesc());
				// return msgVo;
				// }else{
				// bankOrg.setMerchantId(merchantVo.getMerchantId());
				// }
			}
			LogCvt.info("======修改机构对应商户:结束======");
			LogCvt.info("======修改机构对应门店:开始======");
			if (StringUtil.isNotEmpty(bankOrg.getOutletId())) {// 修改机构对应门店
				resultVo = outletUpdate(bankOrg, vo);
				if (!resultVo.getResultCode()
						.equals(EnumTypes.success.getCode())) {
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
					return msgVo;
				}
				// }else{
				// //新增门店表
				// OutletAddVoRes outletVo = outletAdd(bankOrg,vo);
				// if(!outletVo.getResult().getResultCode().equals(EnumTypes.success.getCode())){
				// msgVo.setResult(false);
				// msgVo.setMsg(outletVo.getResult().getResultDesc());
				// return msgVo;
				// }else{
				// bankOrg.setOutletId(outletVo.getOutletId());
				// }
			}
			LogCvt.info("======修改机构对应门店:结束======");
			// 修改机构信息
			LogCvt.info("======修改机构:开始======");
			resultVo = orgService.updateOrg(vo, orgVo);
			LogCvt.info("======修改机构:开始======");
			if (resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg("修改成功!");
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}

		} catch (Exception e) {
			LogCvt.info("银行机构信息修改" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行机构信息修改异常");
		}
		return msgVo;
	}

	/**
	 * 
	 * bankOrgUpdate4Department:部门机构的修改
	 *
	 * @author 明灿 2015年8月21日 下午2:35:26
	 * @param bankOrg
	 * @param vo
	 * @return
	 *
	 */
	public MsgVo bankOrgUpdate4Department(BankOrgVo bankOrg, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			// 修改机构信息
			OrgVo orgVo = new OrgVo();
			// 部门机构
			orgVo.setOrgType(false);
			orgVo.setClientId(bankOrg.getClientId());
			orgVo.setId(bankOrg.getOrgId());// 机构ID
			orgVo.setOrgCode(bankOrg.getOrgCode());// 机构号
			orgVo.setOrgName(bankOrg.getOrgName());// 机构名称
			orgVo.setOrgLevel(bankOrg.getOrgLevel());// 机构级别
			if (StringUtil.equals(bankOrg.getOrgLevel(), "2")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "3")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
			} else if (StringUtil.equals(bankOrg.getOrgLevel(), "4")) {
				orgVo.setProvinceAgency(bankOrg.getProinceAgency());// 上层机构
				orgVo.setCityAgency(bankOrg.getCityAgency());// 二级机构
				orgVo.setCountyAgency(bankOrg.getCountyAgency());// 三级机构
			}
			orgVo.setPhone(bankOrg.getTel());// 电话
			orgVo.setAreaId(bankOrg.getCountyCode());// 地区ID
			// orgVo.setOrgType(bankOrg.getOrgType());// 机构类型
			// 是否需要双人审核
			orgVo.setNeedReview(bankOrg.getIsMutualAudit());
			ResultVo resultVo = new ResultVo();
			// 修改机构信息
			LogCvt.info("======修改机构:开始======");
			resultVo = orgService.updateOrg(vo, orgVo);
			LogCvt.info("======修改机构:开始======");
			if (resultVo.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg("修改成功!");
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(resultVo.getResultDesc());
			}

		} catch (Exception e) {
			LogCvt.info("银行机构信息修改" + e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行机构信息修改异常");
		}
		return msgVo;
	}

	/**
	 * 禁/启用
	 * 
	 * @param orgId
	 * @param state
	 * @return
	 */
	public MsgVo bankOrgIsEnable(BankOrgVoReq voReq, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			OrgVo orgVo = new OrgVo();
			orgVo.setClientId(voReq.getClientId());
			orgVo.setId(Long.valueOf(voReq.getOrgId()));
			orgVo.setOrgCode(voReq.getOrgCode());
			orgVo.setIsEnable(voReq.getState());
			if (!voReq.getState()) {
				ResultVo resultVo = merchantService.disableMerchant(vo,
						voReq.getClientId(), voReq.getOrgCode());
				if (resultVo.getResultCode()
						.equals(EnumTypes.success.getCode())) {
					InvalidProductBatchVo invalidVo = new InvalidProductBatchVo();
					invalidVo.setClientId(voReq.getClientId());
					invalidVo.setOrgCode(voReq.getOrgCode());
					resultVo = productService.invalidProductBatch(vo,
							invalidVo);
					if (resultVo.getResultCode()
							.equals(EnumTypes.success.getCode())) {
						resultVo = orgService.deleteOrg(vo, orgVo);
						if (resultVo.getResultCode()
								.equals(EnumTypes.success.getCode())) {
							msgVo.setResult(true);
							msgVo.setMsg(resultVo.getResultDesc());
						} else {
							msgVo.setResult(false);
							msgVo.setMsg(resultVo.getResultDesc());
						}
					} else {
						msgVo.setResult(false);
						msgVo.setMsg(resultVo.getResultDesc());
					}
				} else {
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
				}
			} else {
				MerchantVo merchantVo = new MerchantVo();
				merchantVo.setClientId(voReq.getClientId());
				merchantVo.setOrgCode(voReq.getOrgCode());
				merchantVo.setMerchantStatus(true);
				ResultVo resultVo = merchantService.enableMerchant(vo,
						merchantVo);
				if (resultVo.getResultCode()
						.equals(EnumTypes.success.getCode())) {
					resultVo = orgService.deleteOrg(vo, orgVo);
					if (resultVo.getResultCode()
							.equals(EnumTypes.success.getCode())) {
						msgVo.setResult(true);
						msgVo.setMsg(resultVo.getResultDesc());
					} else {
						msgVo.setResult(false);
						msgVo.setMsg(resultVo.getResultDesc());
					}
				} else {
					msgVo.setResult(false);
					msgVo.setMsg(resultVo.getResultDesc());
				}
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息" + (voReq.getState() ? "启用" : "禁用")
					+ e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行机构信息" + (voReq.getState() ? "启用" : "禁用") + "异常");
		}
		return msgVo;

	}

	/**
	 * 
	 * bankOrgIsEnable4Department:部门机构禁/启用
	 *
	 * @author 明灿 2015年8月21日 下午2:39:06
	 * @param voReq
	 * @param vo
	 * @return
	 *
	 */
	public MsgVo bankOrgIsEnable4Department(BankOrgVoReq voReq, OriginVo vo) {
		MsgVo msgVo = new MsgVo();
		try {
			OrgVo orgVo = new OrgVo();
			// 部门机构
			orgVo.setOrgType(false);
			orgVo.setClientId(voReq.getClientId());
			orgVo.setId(Long.valueOf(voReq.getOrgId()));
			orgVo.setOrgCode(voReq.getOrgCode());
			orgVo.setIsEnable(voReq.getState());
			// 禁用机构
			ResultVo deleteOrg = orgService.deleteOrg(vo, orgVo);
			if (deleteOrg.getResultCode().equals(EnumTypes.success.getCode())) {
				msgVo.setResult(true);
				msgVo.setMsg(deleteOrg.getResultDesc());
			} else {
				msgVo.setResult(false);
				msgVo.setMsg(deleteOrg.getResultDesc());
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息" + (voReq.getState() ? "启用" : "禁用")
					+ e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("银行机构信息" + (voReq.getState() ? "启用" : "禁用") + "异常");
		}
		return msgVo;
	}

	/**
	 * 详情
	 * 
	 * @param orgId
	 * @return
	 */
	public BankOrgVo bankOrgDetail(String clientId, String orgCode) {
		BankOrgVo bankOrgVo = new BankOrgVo();
		try {
			OrgVo orgVo = new OrgVo();
			orgVo.setOrgCode(orgCode);
			orgVo.setClientId(clientId);
			OrgVo vo = orgService.getOrgByIdSuperOrgName(clientId, orgCode);
			if (vo != null) {
				bankOrgVo.setClientId(clientId);// 客户端ID
				bankOrgVo.setOrgId(vo.getId());// 机构ID
				bankOrgVo.setOrgCode(vo.getOrgCode());// 机构号
				bankOrgVo.setOrgName(vo.getOrgName());// 机构名称
				bankOrgVo.setOrgLevel(vo.getOrgLevel());// 机构等级
				if (StringUtils.isNotEmpty(vo.getProvinceAgency())) {
					bankOrgVo.setProinceAgency(vo.getProvinceAgency());// 一级机构
					OrgVo tVo = orgService.getOrgById(clientId,
							vo.getProvinceAgency());
					bankOrgVo.setProinceAgencyName(tVo.getOrgName());
				}
				if (StringUtils.isNotEmpty(vo.getCityAgency())) {
					bankOrgVo.setCityAgency(vo.getCityAgency());// 二级机构
					OrgVo tVo = orgService.getOrgById(clientId,
							vo.getCityAgency());
					bankOrgVo.setCityAgencyName(tVo.getOrgName());
				}
				if (StringUtils.isNotEmpty(vo.getCountyAgency())) {
					bankOrgVo.setCountyAgency(vo.getCountyAgency());// 三级机构
					OrgVo tVo = orgService.getOrgById(clientId,
							vo.getCountyAgency());
					bankOrgVo.setCountyAgencyName(tVo.getOrgName());
				}
				bankOrgVo.setOrgType(vo.isOrgType());
				bankOrgVo.setIsMutualAudit(vo.isNeedReview());// 是否需要双人审核
				bankOrgVo.setTel(vo.getPhone());// 电话
				bankOrgVo.setMerchantId(vo.getMerchantId());// 商户ID
				bankOrgVo.setOutletId(vo.getOutletId());// 门店ID
				OutletVo outlet = new OutletVo();
				LogCvt.info("=======机构返回的outLetId: " + vo.getOutletId());
				if (StringUtils.isNotEmpty(vo.getOutletId())) {
					outlet = this.outletDetail(vo.getClientId(), vo.getOutletId());
					LogCvt.info("outletId:" + vo.getOutletId()
							+ " ======================查询门店详情后端返回数据 "
							+ JSON.toJSONString(outlet));
					if (outlet != null) {
						bankOrgVo.setLatitude(outlet.getLatitude());// 纬度
						bankOrgVo.setLongitude(outlet.getLongitude());// 经度
						bankOrgVo.setAddress(outlet.getAddress());// 详细地址
					}
				}
				// 部门机构回显省市区
				long areaId = vo.getAreaId();
				LogCvt.info("=============server返回的areaId: "
						+ String.valueOf(areaId));
				AreaVo areaVo = this.findAreaById(areaId);
				if (areaVo != null && areaVo.getId() != null) {
					String[] treePath = null;
					if (StringUtils.isNotEmpty(areaVo.getTreePath())) {
						treePath = areaVo.getTreePath().split(",");
						LogCvt.info("==============根据findAreaById返回数据: "
								+ treePath.toString());
						if (treePath.length > 2) {
							bankOrgVo.setAreaCode(Long.valueOf(treePath[0]));// 省
							bankOrgVo.setCityCode(Long.valueOf(treePath[1]));// 市
							bankOrgVo.setCountyCode(areaVo.getId());// 区
						} else if (treePath.length > 1) {
							bankOrgVo.setAreaCode(areaVo.getPartenId());// 省
							bankOrgVo.setCityCode(areaVo.getId());// 市
						} else {
							bankOrgVo.setAreaCode(areaVo.getId());// 省
						}
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息详情查询" + e.getMessage(), e);
		}
		return bankOrgVo;
	}

	/**
	 * 新增机构对应商户
	 * 
	 * @param bankOrg
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	private MerchantAddVoRes merchantAdd(BankOrgVo bankOrg, OriginVo vo)
			throws BankException, TException, ParseException {
		MerchantVoReq merchantReq = new MerchantVoReq();
		MerchantVo merchant = new MerchantVo();
		merchant.setClientId(bankOrg.getClientId());// 客户端ID
		merchant.setOrgCode(bankOrg.getOrgCode());// 所属组织机构代码
		merchant.setProOrgCode(bankOrg.getProinceAgency());// 一级
		merchant.setCityOrgCode(bankOrg.getCityAgency());// 二级
		merchant.setCountyOrgCode(bankOrg.getCountyAgency());// 三级

		if (!NumberUtils.isNumber(bankOrg.getOrgLevel())) {
			throw new BankException("机构码:" + merchant.getOrgCode() + "的级别非法!");
		}
		Integer orgLevel = Integer.parseInt(bankOrg.getOrgLevel()); // 机构级别
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
		merchant.setMerchantName(bankOrg.getOrgName());// 商户名
		// merchant.setMerchantFullname(bankOrg.getOrgName());//商户全名
		merchant.setPhone(bankOrg.getTel());// 商户电话
		merchant.setContactPhone(bankOrg.getTel());// 联系人手机
		merchant.setAreaId(bankOrg.getCountyCode());// 所在地区
		merchant.setAddress(bankOrg.getAddress());// 详细地址
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
		merchant.setContractEndtime(DateUtil.DateFormat("9999-12-31"));// 签约到期时间
		merchantReq.setMerchantVo(merchant);
		merchantReq.setCategoryInfoVoList(null);
		merchantReq.setTypeInfoVoList(null);
		// 新增
		return merchantService.addMerchant(vo, merchantReq);
	}

	/**
	 * 修改机构对应商户
	 * 
	 * @param bankOrg
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	private ResultVo merchantUpdate(BankOrgVo bankOrg, OriginVo vo)
			throws BankException, TException, ParseException {
		MerchantVoReq merchantReq = new MerchantVoReq();
		MerchantVo merchant = new MerchantVo();
		merchant.setClientId(bankOrg.getClientId());// 客户端ID
		merchant.setMerchantId(bankOrg.getMerchantId());// 商户ID
		merchant.setMerchantName(bankOrg.getOrgName());// 商户名
		merchant.setMerchantFullname(bankOrg.getOrgName());// 商户全名
		merchant.setProOrgCode(bankOrg.getProinceAgency());// 一级
		merchant.setCityOrgCode(bankOrg.getCityAgency());// 二级
		merchant.setCountyOrgCode(bankOrg.getCountyAgency());// 三级
		if (!NumberUtils.isNumber(bankOrg.getOrgLevel())) {
			throw new BankException("机构码:" + merchant.getOrgCode() + "的级别非法!");
		}
		Integer orgLevel = Integer.parseInt(bankOrg.getOrgLevel()); // 机构级别
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
		merchant.setPhone(bankOrg.getTel());// 商户电话
		merchant.setAreaId(bankOrg.getCountyCode());// 所在地区
		merchant.setAddress(bankOrg.getAddress());// 详细地址
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
		// 修改
		return merchantService.updateMerchant(vo, merchantReq);
	}

	/**
	 * 新增机构对应门店
	 * 
	 * @param bankOrg
	 * @return
	 * @throws TException
	 */
	public OutletAddVoRes outletAdd(BankOrgVo bankOrg, OriginVo vo)
			throws TException {
		OutletVo outlet = new OutletVo();
		outlet.setClientId(bankOrg.getClientId());// 客户端ID
		outlet.setMerchantId(bankOrg.getMerchantId());// 商户ID
		outlet.setOutletId(bankOrg.getOutletId());// 门店ID
		outlet.setOutletName(bankOrg.getOrgName());// 门店名称
		outlet.setOutletFullname(bankOrg.getOrgName());// 门店全名
		outlet.setOutletStatus(true);// 是否银行机构对应门店
		outlet.setAreaId(bankOrg.getCountyCode());// 地区
		outlet.setAddress(bankOrg.getAddress());// 详细地址
		outlet.setLatitude(bankOrg.getLatitude());// 纬度
		outlet.setLongitude(bankOrg.getLongitude());// 经度
		outlet.setPhone(bankOrg.getTel());// 电话
		outlet.setIsEnable(true);// 是否有效
		// 新增
		return outletService.addOutlet(vo, outlet);
	}

	/**
	 * 修改机构对应门店
	 * 
	 * @param bankOrg
	 * @return
	 * @throws TException
	 */
	public ResultVo outletUpdate(BankOrgVo bankOrg, OriginVo vo)
			throws TException {
		OutletVo outlet = new OutletVo();
		outlet.setClientId(bankOrg.getClientId());// 客户端ID
		outlet.setMerchantId(bankOrg.getMerchantId());// 商户ID
		outlet.setOutletId(bankOrg.getOutletId());// 门店ID
		outlet.setOutletName(bankOrg.getOrgName());// 门店名称
		outlet.setOutletFullname(bankOrg.getOrgName());// 门店全名
		outlet.setAreaId(bankOrg.getCountyCode());// 地区
		outlet.setAddress(bankOrg.getAddress());// 详细地址
		outlet.setLatitude(bankOrg.getLatitude());// 纬度
		outlet.setLongitude(bankOrg.getLongitude());// 经度
		outlet.setPhone(bankOrg.getTel());// 电话
		outlet.setIsEnable(true);// 是否有效
		// outlet.setStoreCount(0);//数量
		// 修改
		return outletService.updateOutlet(vo, outlet);
	}

	/**
	 * 根据门店ID获取门店信息
	 * 
	 * @param outletId
	 * @return
	 * @throws TException
	 */
	public OutletVo outletDetail(String clientId, String outletId) {
		OutletVo outlet = new OutletVo();
		outlet.setClientId(clientId);
		if (StringUtils.isNotEmpty(outletId)) {
			outlet.setOutletId(outletId);
			// List<OutletVo> outletList = new ArrayList<OutletVo>();
			try {
				// outletList = outletService.getOutlet(outlet);
				OutletVo outletVo = outletService.getOutletByOutletId(outletId);
				// OutletDetailVo outletDetailVo =
				// outletService.getOutletDetail(outletId);
				if (outletVo != null) {
					outlet.setLatitude(
							StringUtil.isNotBlank(outletVo.getLatitude())
									? String.valueOf(outletVo.getLatitude())
									: "");
					outlet.setLongitude(
							StringUtil.isNotBlank(outletVo.getLongitude())
									? String.valueOf(outletVo.getLongitude())
									: "");
					outlet.setAddress(outletVo.getAddress());
					outlet.setAreaId(outletVo.getAreaId());
				}
				// if(outletList != null && outletList.size() > 0){
				// outlet = outletList.get(0);
				// }
			} catch (Exception e) {
				LogCvt.info("根据门店ID获取门店信息" + e.getMessage(), e);
			}
		}
		return outlet;
	}

	/**
	 * 根据机构号获取机构名称
	 * 
	 * @param orgCode
	 * @return
	 */
	public BankOrgVo getOrgNameByOrgCode(String clientId, String orgCode) {
		BankOrgVo orgVo = new BankOrgVo();
		try {
			if (StringUtil.isNotEmpty(orgCode)) {
				OrgVo orgvo = new OrgVo();
				orgvo.setOrgCode(orgCode);
				orgvo.setClientId(clientId);
				List<OrgVo> orgVoList = new ArrayList<OrgVo>();
				orgVoList = orgService.getOrg(orgvo);
				if (orgVoList != null && orgVoList.size() > 0) {
					orgVo.setOrgName(orgVoList.get(0).getOrgName());
					orgVo.setOrgCode(orgVoList.get(0).getOrgCode());
					orgVo.setOutletId(orgVoList.get(0).getOutletId());
					orgVo.setOrgLevel(orgVoList.get(0).getOrgLevel());
					if (orgVoList.get(0).getOrgLevel().equals("2")) {
						orgVo.setPartenOrgCode(
								orgVoList.get(0).getProvinceAgency());
					} else if (orgVoList.get(0).getOrgLevel().equals("3")) {
						orgVo.setPartenOrgCode(
								orgVoList.get(0).getCityAgency());
					} else if (orgVoList.get(0).getOrgLevel().equals("4")) {
						orgVo.setPartenOrgCode(
								orgVoList.get(0).getCountyAgency());
					} else {
						orgVo.setPartenOrgCode("");
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("根据机构号获取机构信息" + e.getMessage(), e);
		}
		return orgVo;
	}

	/**
	 * 根据开户行机构号获取上一级机构名称及本身的机构名称
	 * 
	 * @param orgCode
	 * @return
	 */
	public BankOrgRes getBankOrgByOrgCode(String clientId, String orgCode,
			String myOrgCode) {
		BankOrgRes vo = new BankOrgRes();
		try {
			if (StringUtil.isNotEmpty(orgCode)) {
				OrgVo org = orgService.getOrgByIdSuperOrgName(clientId,
						orgCode);
				if (StringUtil.isNotEmpty(org.getOrgName())) {
					if (orgCode.equals(myOrgCode)) {
						vo.setPartenOrgName(org.getSuperOrgName());
						vo.setOrgName(org.getOrgName());
					} else {
						List<BankOrgRes> orgCodeList = this
								.selectPreateOrgCode(clientId, myOrgCode);
						if (orgCodeList != null && orgCodeList.size() > 0) {
							for (BankOrgRes codeVo : orgCodeList) {
								if (codeVo.getOrgCode().equals(orgCode)) {
									vo.setPartenOrgName(org.getSuperOrgName());
									vo.setOrgName(org.getOrgName());
									break;
								} else {
									vo.setPartenOrgName("无权限操作此开户行机构号");
									vo.setOrgName("");
								}
							}
						}
					}
				} else {
					vo.setPartenOrgName("不存在此开户行机构号");
					vo.setOrgName("");
				}
			}
		} catch (Exception e) {
			LogCvt.info("根据开户行机构号获取上一级机构名称及本身的机构名称" + e.getMessage(), e);
		}
		return vo;
	}

	/**
	 * 根据机构号验证是否已存在此机构
	 * 
	 * @param orgCode
	 * @return
	 */
	public int getOrgByOrgCode(String clientId, String orgCode) {
		int count = 0;
		try {
			OrgVo orgVo = new OrgVo();
			orgVo.setClientId(clientId);
			if (StringUtils.isNotEmpty(orgCode)) {
				orgVo.setOrgCode(orgCode);
				orgVo.setIsEnable(true);
				List<OrgVo> orgList = orgService.getOrg(orgVo);
				if (orgList != null && orgList.size() > 0) {
					count = orgList.size();
				}
			}
		} catch (Exception e) {
			LogCvt.info("根据机构号验证是否已存在此机构" + e.getMessage(), e);
		}
		return count;
	}

	/**
	 * 根据机构号获取上一级机构名称
	 * 
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public String getPartenOrgNameByOrgCode(String clientId, String orgCode) {
		String partenOrgName = "";
		try {
			OrgVo org = orgService.getOrgByIdSuperOrgName(clientId, orgCode);
			if (org != null) {
				if (org.getOrgLevel().equals("3")
						|| org.getOrgLevel().equals("4")) {
					partenOrgName = org.getSuperOrgName();
				}
			}
		} catch (Exception e) {
			LogCvt.info("根据机构号获取上一级机构名称" + e.getMessage(), e);
		}
		return partenOrgName;
	}

	/**
	 * 根据机构号获取上一级机构名称(新增方法:增加返回orgCode)
	 * 
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public Map<String, String> getPartenOrgByOrgCode(String clientId,
			String orgCode) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			OrgVo superOrg = orgService.getSuperOrg(clientId, orgCode);
			if (superOrg != null) {
				// if (superOrg.getOrgLevel().equals("3")
				// || superOrg.getOrgLevel().equals("4")) {
				map.put("partenOrgName", superOrg.getOrgName());
				map.put("partenOrgCode", superOrg.getOrgCode());
				// }
			}
		} catch (Exception e) {
			LogCvt.info("根据机构号获取上一级机构名称" + e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 根据机构号获取所有下属
	 * 
	 * @param orgCode
	 * @return
	 * @throws TException
	 */
	public List<BankOrgRes> selectPreateOrgCode(String clientId,
			String orgCode) {
		List<BankOrgRes> orgCodeList = new ArrayList<BankOrgRes>();
		try {
			if (StringUtils.isNotEmpty(orgCode)) {
				List<OrgVo> orgList = orgService.getAllSubOrgs(clientId,
						orgCode);
				if (orgList != null && orgList.size() > 0) {
					for (OrgVo org : orgList) {
						BankOrgRes bankOrg = new BankOrgRes();
						bankOrg.setOrgCode(org.getOrgCode());
						bankOrg.setOrgName(org.getOrgName());
						orgCodeList.add(bankOrg);
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("根据机构号获取所有下属" + e.getMessage(), e);
		}

		return orgCodeList;
	}

	/**
	 * 银行机构信息联动查询网点
	 * 
	 * @param orgCode
	 * @return
	 * @throws TException
	 */
	public List<BankOrgRes> selectOrgCode(BankOrgVoReq voReq) {
		if (LogCvt.isDebugEnabled()) {
			LogCvt.debug("机构列表请求参数:" + JSON.toJSONString(voReq));
		}
		List<BankOrgRes> bankOrgList = new ArrayList<BankOrgRes>();
		List<OrgVo> orgList = new ArrayList<OrgVo>();
		try {
			if (StringUtils.isNotEmpty(voReq.getOrgLevel())) {
				OrgVo orgVo = new OrgVo();
				orgVo.setClientId(voReq.getClientId());
				orgVo.setOrgLevel("1");
				orgVo.setIsEnable(true);
				orgList = orgService.getOrg(orgVo);
				if (LogCvt.isDebugEnabled()) {
					LogCvt.debug("机构列表请求返回数据:" + JSON.toJSONString(orgList));
				}
			} else {
				if (StringUtils.isNotEmpty(voReq.getOrgCode())) {
					orgList = orgService.getSubOrgs(voReq.getClientId(),
							voReq.getOrgCode());
				}
			}
			if (orgList != null && orgList.size() > 0) {
				for (OrgVo org : orgList) {
					BankOrgRes bankOrg = new BankOrgRes();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrg.setOrgLevel(org.getOrgLevel());
					bankOrg.setOrgType(org.isOrgType());
					bankOrgList.add(bankOrg);
				}
			}

		} catch (Exception e) {
			LogCvt.info("机构联动查询" + e.getMessage(), e);
		}

		return bankOrgList;
	}

	/**
	 * 获取所有机构
	 * 
	 * @param clientId
	 * @param orgLevel
	 * @return
	 */
	public List<BankOrgRes> getAllOrg(String clientId, String orgCode) {
		List<BankOrgRes> bankOrgList = new ArrayList<BankOrgRes>();
		try {
			OrgVo orgVo = new OrgVo();
			orgVo.setClientId(clientId);
			orgVo.setIsEnable(true);
			List<OrgVo> orgList = orgService.getAllSubOrgs(clientId, orgCode);
			if (orgList != null && orgList.size() > 0) {
				for (OrgVo org : orgList) {
					BankOrgRes bankOrg = new BankOrgRes();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrg.setOrgLevel(org.getOrgLevel());
					bankOrgList.add(bankOrg);
				}
			}

		} catch (Exception e) {
			LogCvt.info("获取所有机构" + e.getMessage(), e);
		}
		return bankOrgList;
	}

	/**
	 * 获取所有机构
	 * 
	 * @param clientId
	 * @param orgLevel
	 * @return
	 */
	public List<BankOrgRes> getAllOrgCode(String clientId, String orgCode) {
		List<BankOrgRes> bankOrgList = new ArrayList<BankOrgRes>();
		try {
			OrgVo orgVo = new OrgVo();
			orgVo.setClientId(clientId);
			orgVo.setIsEnable(true);
			List<OrgVo> orgList = orgService.getSubOrgs(clientId, orgCode);
			orgVo.setOrgLevel("1");
			List<OrgVo> orgCodeList = orgService.getOrg(orgVo);
			if (orgCodeList != null && orgCodeList.size() > 0) {
				for (OrgVo org : orgCodeList) {
					BankOrgRes bankOrg = new BankOrgRes();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrg.setOrgLevel(org.getOrgLevel());
					bankOrgList.add(bankOrg);
				}
			}
			if (orgList != null && orgList.size() > 0) {
				for (OrgVo org : orgList) {
					BankOrgRes bankOrg = new BankOrgRes();
					bankOrg.setOrgCode(org.getOrgCode());
					bankOrg.setOrgName(org.getOrgName());
					bankOrg.setOrgLevel(org.getOrgLevel());
					bankOrgList.add(bankOrg);
				}
			}
		} catch (Exception e) {
			LogCvt.info("获取所有机构" + e.getMessage(), e);
		}
		return bankOrgList;
	}

	/**
	 * 批量获取机构名称
	 * 
	 * @param clientId
	 * @param orgLevel
	 * @return
	 */
	public List<BankOrgVo> getByOrgCode(String clientId,
			List<String> orgCodeList) {
		List<BankOrgVo> bankOrgList = new ArrayList<BankOrgVo>();
		try {
			if (orgCodeList != null && orgCodeList.size() > 0) {
				List<OrgVo> orgList = orgService.getOrgByList(clientId,
						orgCodeList);
				if (orgList != null && orgList.size() > 0) {
					for (OrgVo org : orgList) {
						BankOrgVo bankOrg = new BankOrgVo();
						bankOrg.setOrgCode(org.getOrgCode());
						bankOrg.setOrgName(org.getOrgName());
						bankOrgList.add(bankOrg);
					}
				}
			}
		} catch (Exception e) {
			LogCvt.info("根据机构号批量获取机构名称" + e.getMessage(), e);
		}
		return bankOrgList;
	}

	/**
	 * 地区联动查询(根据id获取子集地区)
	 * 
	 * @param areaId
	 * @return
	 */
	public List<AreaVo> findChildrenInfoById(Long areaId, String areaCode) {

		List<AreaVo> areaList = new ArrayList<AreaVo>();
		List<com.froad.thrift.vo.AreaVo> list = new ArrayList<com.froad.thrift.vo.AreaVo>();
		try {
			if (areaId != null) {
				list = areaService.findChildrenInfoById(areaId, areaCode);
				if (list != null && list.size() > 0) {
					for (com.froad.thrift.vo.AreaVo vo : list) {
						AreaVo areaVo = new AreaVo();
						areaVo.setId(vo.getId());
						areaVo.setName(vo.getName());
						areaVo.setTreePath(vo.getTreePath());
						areaVo.setTreePathName(vo.getTreePathName());
						areaList.add(areaVo);
					}
				}
			}
		} catch (TException e) {
			LogCvt.info("地区联动查询" + e.getMessage(), e);
		}
		return areaList;

	}

	/**
	 * 地区联动查询(根据id获取子集地区)
	 * 
	 * @param areaId,clientId
	 * @return
	 */
	public List<AreaVo> findChildrenInfoById(Long areaId, String areaCode,
			String clientId) {

		List<AreaVo> areaList = new ArrayList<AreaVo>();
		List<com.froad.thrift.vo.AreaVo> list = new ArrayList<com.froad.thrift.vo.AreaVo>();
		try {
			if (areaId != null) {
				list = areaService.findChildrenInfo(areaId, areaCode, clientId);
				if (list != null && list.size() > 0) {
					for (com.froad.thrift.vo.AreaVo vo : list) {
						AreaVo areaVo = new AreaVo();
						areaVo.setId(vo.getId());
						areaVo.setName(vo.getName());
						areaVo.setTreePath(vo.getTreePath());
						areaVo.setTreePathName(vo.getTreePathName());
						areaList.add(areaVo);
					}
				}
			}
		} catch (TException e) {
			LogCvt.info("地区联动查询" + e.getMessage(), e);
		}
		return areaList;

	}

	/**
	 * 根据id获取地区信息
	 * 
	 * @param areaId
	 * @return
	 */
	public AreaVo findAreaById(Long areaId) {

		AreaVo areaVo = new AreaVo();
		try {
			if (areaId != null) {
				com.froad.thrift.vo.AreaVo vo = areaService.findAreaById(areaId);
				LogCvt.info("areaId:" + areaId
						+ "============================================= BankOrgService.findAreaById查询地区信息后端返回数据 "
						+ JSON.toJSONString(vo));
				areaVo.setId(vo.getId());
				areaVo.setName(vo.getName());
				areaVo.setPartenId(vo.getParentId());
				areaVo.setTreePath(vo.getTreePath());
				areaVo.setTreePathName(vo.getTreePathName());
			}
		} catch (TException e) {
			LogCvt.error(e.getMessage(), e);
		}
		return areaVo;
	}

	/**
	 * 校验
	 * 
	 * @param bankOrg
	 * @return
	 */
	public MsgVo verify(BankOrgVo bankOrg) {

		MsgVo msgVo = new MsgVo();
		if (!StringUtil.isNotEmpty(bankOrg.getOrgCode())) {
			msgVo.setResult(false);
			msgVo.setMsg("组织编码不能为空");
			return msgVo;
		}
		if (!StringUtil.isNotEmpty(bankOrg.getOrgName())) {
			msgVo.setResult(false);
			msgVo.setMsg("机构名称不能为空");
			return msgVo;
		} else if (bankOrg.getOrgName().length() > 32) {
			msgVo.setResult(false);
			msgVo.setMsg("机构名称不能超过32个字符");
			return msgVo;
		}
		if (!StringUtil.isNotEmpty(bankOrg.getOrgLevel())) {
			msgVo.setResult(false);
			msgVo.setMsg("机构级别不能为空");
			return msgVo;
		}
		if (StringUtil.equals(bankOrg.getOrgLevel(), "2")) {
			if (!StringUtil.isNotEmpty(bankOrg.getProinceAgency())) {
				msgVo.setResult(false);
				msgVo.setMsg("一级机构不能为空");
				return msgVo;
			}
		} else if (StringUtil.equals(bankOrg.getOrgLevel(), "3")) {
			if (!StringUtil.isNotEmpty(bankOrg.getProinceAgency())) {
				msgVo.setResult(false);
				msgVo.setMsg("一级机构不能为空");
				return msgVo;
			}
			if (!StringUtil.isNotEmpty(bankOrg.getCityAgency())) {
				msgVo.setResult(false);
				msgVo.setMsg("二级机构不能为空");
				return msgVo;
			}
		} else if (StringUtil.equals(bankOrg.getOrgLevel(), "4")) {
			if (!StringUtil.isNotEmpty(bankOrg.getProinceAgency())) {
				msgVo.setResult(false);
				msgVo.setMsg("一级机构不能为空");
				return msgVo;
			}
			if (!StringUtil.isNotEmpty(bankOrg.getCityAgency())) {
				msgVo.setResult(false);
				msgVo.setMsg("二级机构不能为空");
				return msgVo;
			}
			if (!StringUtil.isNotEmpty(bankOrg.getCountyAgency())) {
				msgVo.setResult(false);
				msgVo.setMsg("三级机构不能为空");
				return msgVo;
			}
		}
		if (bankOrg.getCountyCode() == null) {
			msgVo.setResult(false);
			msgVo.setMsg("区不能为空");
			return msgVo;
		}
		if (bankOrg.getOrgType()
				&& !StringUtil.isNotEmpty(bankOrg.getAddress())) {
			msgVo.setResult(false);
			msgVo.setMsg("详细地址不能为空");
			return msgVo;
		}

		msgVo.setResult(true);
		return msgVo;
	}

	/**
	 * 
	 * getOrgListByName:根据机构名称查询机构树(模糊查询)
	 * 
	 * @author chenmingcan@froad.com.cn 2015年11月27日 上午11:03:39
	 * @param orgName
	 * @param clientId
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> getOrgListByName(String orgName,
			String loginOrgCode, String clientId) throws TException {
		LogCvt.info("根据机构名称查询机构树请求参数: orgName=" + orgName + "loginOrgCode="
				+ loginOrgCode + " clientId=" + clientId);
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<BankOrg4MistyQuery> orgResList = new ArrayList<BankOrg4MistyQuery>();
		BankOrg4MistyQuery bankOrg4MistyQuery = null;
		OrgVo orgVo = new OrgVo();
		orgVo.setClientId(clientId);
		orgVo.setOrgName(orgName);
		List<OrgVo> orgList = orgService.getOrgInfoByOrgName(orgVo, 10,
				loginOrgCode);
		LogCvt.info("根据机构名称查询机构树返回数据:" + JSON.toJSONString(orgList));
		if (null != orgList && orgList.size() > 0) {
			for (OrgVo org : orgList) {
				bankOrg4MistyQuery = new BankOrg4MistyQuery();
				bankOrg4MistyQuery.setOrgCode(org.getOrgCode());
				bankOrg4MistyQuery.setOrgName(org.getOrgName());

				// 设置父节点
				this.setParentOrgCode(bankOrg4MistyQuery, org);
				orgResList.add(bankOrg4MistyQuery);
			}
			resMap.put("orgList", orgResList);
		}
		return resMap;
	}

	/**
	 * 
	 * setParentOrgCode:设置父节点
	 * 
	 * @author chenmingcan@froad.com.cn 2015年11月27日 下午2:19:31
	 * @param bankOrg4MistyQuery
	 * @param org
	 *
	 */
	private void setParentOrgCode(BankOrg4MistyQuery bankOrg4MistyQuery,
			OrgVo org) {
		if (StringUtil.equals(org.getOrgLevel(), "2")
				&& org.getProvinceAgency() != null) {// 一级机构号
			bankOrg4MistyQuery.setPartenOrgCode(org.getProvinceAgency());
		} else if (StringUtil.equals(org.getOrgLevel(), "3")
				&& org.getCityAgency() != null) {// 二级机构号
			bankOrg4MistyQuery.setPartenOrgCode(org.getCityAgency());
		} else if (StringUtil.equals(org.getOrgLevel(), "4")
				&& org.getCountyAgency() != null) {// 三级机构号
			bankOrg4MistyQuery.setPartenOrgCode(org.getCountyAgency());
		} else {// 顶级级
			bankOrg4MistyQuery.setPartenOrgCode("0");
		}
	}
}
