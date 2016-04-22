package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrgUserRoleBankResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OrgUserRoleReqVo;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.vo.OrgUserRolePageVo;
import com.froad.thrift.vo.OrgUserRolePageVoRes;
import com.froad.thrift.vo.OrgUserRoleVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 
 * 类名: OrgUserRoleService 
 * 描述: 银行用户管理相关的逻辑处理类 
 * 作者: 明灿 chenmingcan@f-road.com.cn
 * 日期: 2015年8月4日 上午10:03:48 
 *
 */
@Service
public class OrgUserRoleBankService {
	
	@Resource
	OrgUserRoleService.Iface orgUserRoleService;

	/**
	 * 
	 * 方法名称: getOrgUserRoleByPage 
	 * 简要描述: 银行用户列表查询
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月4日 上午11:43:31
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param voReq
	 * 方法参数: @return
	 * 方法参数: @throws Exception
	 * 返回类型: Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> getOrgUserRoleByPage(OrgUserRoleReqVo voReq)
			throws Exception {
		LogCvt.info("银行用户列表查询请求参数:" + JSON.toJSONString(voReq));
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrgUserRoleBankResVo> list = null;
		PageVo pageReq = new PageVo();
		// 查询条件封装
		OrgUserRoleVo orgUserRoleVo = verifyAndPackaged(voReq, pageReq);
		// 查询
		OrgUserRolePageVoRes pageVoRes = orgUserRoleService
				.getOrgUserRoleByPage(pageReq, orgUserRoleVo);
		LogCvt.info("银行用户列表查询返回数据:" + JSON.toJSONString(pageVoRes));
		// 返回数据
		PageVo pageVo = pageVoRes.getPage();
		putPageValue(map, pageVo);
		list = copyValue(map, list, pageVoRes);
		map.put("userRoleList", list);
		return map;
	}

	/**
	 * 
	 * putPageValue:封装页码信息
	 *
	 * @author 明灿
	 * 2015年8月11日 下午5:35:38
	 * @param map
	 * @param pageVo
	 *
	 */
	private void putPageValue(Map<String, Object> map, PageVo pageVo) {
		map.put("totalCount", pageVo.getTotalCount());// 总记录数
		map.put("pageCount", pageVo.getPageCount());// 总页数
		map.put("pageNumber", pageVo.getPageNumber());// 当前页
		map.put("lastPageNumber", pageVo.getLastPageNumber());
		map.put("firstRecordTime", pageVo.getFirstRecordTime());
		map.put("lastRecordTime", pageVo.getLastRecordTime());
	}
	
	/**
	 * 
	 * 方法名称: verify 
	 * 简要描述: 封装查询条件
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月6日 上午11:41:25
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param voReq
	 * 方法参数: @param pageReq
	 * 方法参数: @return
	 * 返回类型: OrgUserRoleVo
	 * @throws
	 */
	private OrgUserRoleVo verifyAndPackaged(OrgUserRoleReqVo voReq,
			PageVo pageReq) {
		pageReq.setPageNumber(voReq.getPageNumber() == null ? 1 : voReq
				.getPageNumber());
		pageReq.setPageSize(voReq.getPageSize() == null ? 10 : voReq
				.getPageSize());
		OrgUserRoleVo orgUserRoleVo = new OrgUserRoleVo();
		if (StringUtil.isNotBlank(voReq.getUsername())) {
			orgUserRoleVo.setUsername(voReq.getUsername());
		}
		if (StringUtil.isNotBlank(voReq.getOrgCode())) {
			orgUserRoleVo.setOrgCode(voReq.getOrgCode());
		}
		if (StringUtil.isNotBlank(voReq.getClientId())) {
			orgUserRoleVo.setClientId(voReq.getClientId());
		}
		return orgUserRoleVo;
	}
	
	/**
	 * 
	 * 方法名称: copyValue 
	 * 简要描述: 拷贝返回的数据
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月6日 上午11:30:17
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param map
	 * 方法参数: @param list
	 * 方法参数: @param pageRes
	 * 方法参数: @param pageVoRes
	 * 方法参数: @return
	 * 返回类型: List<OrgUserRoleBankResVo>
	 * @throws
	 */
	private List<OrgUserRoleBankResVo> copyValue(Map<String, Object> map,
 List<OrgUserRoleBankResVo> list,
			OrgUserRolePageVoRes pageVoRes) {
		OrgUserRoleBankResVo orgUserRoleBankResVo = null;
		if (pageVoRes != null) {
			List<OrgUserRolePageVo> orgUserRolePageVoList = pageVoRes
					.getOrgUserRolePageVoList();
			if (orgUserRolePageVoList != null
					&& orgUserRolePageVoList.size() > 0) {
				list = new ArrayList<OrgUserRoleBankResVo>();
				for (OrgUserRolePageVo orgUserRolePageVo : orgUserRolePageVoList) {
					orgUserRoleBankResVo = new OrgUserRoleBankResVo();
					BeanUtils.copyProperties(orgUserRoleBankResVo,
							orgUserRolePageVo);
					list.add(orgUserRoleBankResVo);
				}
			}

		}
		return list;
	}
	
	/**
	 * 
	 * 方法名称: getOrgUserRoleById 
	 * 简要描述: 银行用户详情请求 
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月5日 下午3:24:20
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param id
	 * 方法参数: @return
	 * 方法参数: @throws Exception
	 * 返回类型: OrgUserRoleBankResVo
	 * @throws
	 */
	public OrgUserRoleBankResVo getOrgUserRoleById(Long id) throws Exception {
		LogCvt.info("银行用户详情请求参数:", JSON.toJSONString(id));
		OrgUserRolePageVo userRolePageVo = orgUserRoleService
				.getOrgUserRoleById(id);
		LogCvt.info("银行用户详情返回数据:", JSON.toJSONString(userRolePageVo));
		OrgUserRoleBankResVo resVo = null;
		if (userRolePageVo != null) {
			resVo = new OrgUserRoleBankResVo();
			BeanUtils.copyProperties(resVo, userRolePageVo);
		}
		return resVo;
	}
	
	/**
	 * 
	 * 方法名称: updateOrgUserRole 
	 * 简要描述: 银行用户修改接口
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月5日 下午3:43:48
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param resVo
	 * 方法参数: @return
	 * 方法参数: @throws Exception
	 * 返回类型: Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> updateOrgUserRole(OrgUserRoleReqVo resVo,
			OriginVo originVo)
			throws Exception {
		LogCvt.info("银行用户修改请求参数:" + JSON.toJSONString(resVo));
		Map<String, Object> map = new HashMap<String, Object>();
		OrgUserRoleVo orgUserRoleVo = new OrgUserRoleVo();
		// 封装查询条件
		packagedValue(resVo, orgUserRoleVo);
		// LogCvt.info("银行用户修改请求参数:" + JSON.toJSONString(orgUserRoleVo));
		// BeanUtils.copyProperties(orgUserRoleVo, resVo);
		// 查询
		ResultVo resultVo = orgUserRoleService.updateOrgUserRole(originVo,
				orgUserRoleVo);
		LogCvt.info("银行用户修改返回数据:" + JSON.toJSONString(resultVo));
		if (resultVo != null) {
			map.put("code", resultVo.getResultCode());
			map.put("message", resultVo.getResultDesc());
		} else {
			map.put("code", EnumTypes.thrift_err.getCode());
			map.put("message", EnumTypes.thrift_err.getMessage());
		}
		return map;
	}
	
	/**
	 * 
	 * 方法名称: packagedValue 
	 * 简要描述: 封装查询条件
	 * 版本信息: V1.0  
	 * 创建时间: 2015年8月6日 下午4:10:42
	 * 创建作者: 明灿 chenmingcan@f-road.com.cn
	 * 方法参数: @param resVo
	 * 方法参数: @param orgUserRoleVo
	 * 返回类型: void
	 * @throws
	 */
	private void packagedValue(OrgUserRoleReqVo resVo,
			OrgUserRoleVo orgUserRoleVo) {
		orgUserRoleVo.setId(resVo.getId());
		orgUserRoleVo.setOrgCode(resVo.getOrgCode());
		orgUserRoleVo.setRoleId(resVo.getRoleId());
		orgUserRoleVo.setRoleName(resVo.getRoleName());
	}
}
