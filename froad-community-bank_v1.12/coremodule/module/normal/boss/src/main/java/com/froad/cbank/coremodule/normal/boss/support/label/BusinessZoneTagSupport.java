package com.froad.cbank.coremodule.normal.boss.support.label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.label.BusinessZoneTagDetailsRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.BusinessZoneTagListVo;
import com.froad.cbank.coremodule.normal.boss.pojo.label.BusinessZoneTagListVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.BusinessZoneTagReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BusinessZoneTagService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagVo;
/**
 * 商圈标签管理support
 * @author liaopeixin
 *	@date 2015年10月26日 上午9:14:57
 */
@Service
public class BusinessZoneTagSupport {

	@Resource
	BusinessZoneTagService.Iface businessZoneTagService;
	/**
	 * 商圈标签管理列表查询
	 * @param req
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 *	@date 2015年10月26日 上午9:38:41
	 */
	public Map<String, Object> list(BusinessZoneTagListVoReq voreq) throws TException{
		Map<String,Object> map=new HashMap<String,Object>();
		BusinessZoneTagListReq req=new BusinessZoneTagListReq();
		List<BusinessZoneTagListVo> bzoneTagList=new ArrayList<BusinessZoneTagListVo>();
		//客户端ID
		if(StringUtil.isNotBlank(voreq.getClientId())){
			req.setClientId(voreq.getClientId());
		}
		//商圈标签名称
		if(StringUtil.isNotBlank(voreq.getTagName())){
			req.setTagName(voreq.getTagName());
		}
		/**
		 * 状态
		 *  0：全部
			1：启用
			2：禁用
			3：新增审核中
			4：编辑审核中
			5：禁用审核中
		 */
		if(StringUtil.isNotBlank(voreq.getStatus())){
			req.setStatus(voreq.getStatus());
		}
		//封装分页对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(voreq.getPageNumber());
		pageVo.setPageSize(voreq.getPageSize());
		
		req.setPageVo(pageVo);
		
		 BusinessZoneTagListRes res=businessZoneTagService.getBusinessZoneTagList(req);
		//封装返回结果
		Page page = new Page();
		ResultVo result = res.getResultVo();
		BusinessZoneTagListVo bzonetag=null;
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			//封装返回分页对象
			BeanUtils.copyProperties(page, res.getPageVo());
			//封装返回订单列表
			if(!ArrayUtil.empty(res.getBusinessZoneTagList())) {
				for (BusinessZoneTagVo vo : res.getBusinessZoneTagList()) {
					bzonetag=new BusinessZoneTagListVo();
					BeanUtils.copyProperties(bzonetag, vo);
					bzoneTagList.add(bzonetag);
				}
			}
		}
		map.put("page", page);
		map.put("bzoneTagList", bzoneTagList);
		return map;
	}
	/**
	 * 商圈标签详情
	 * @param id
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2015年10月26日 上午9:39:00
	 */
	public Map<String,Object> details(String id) throws TException, BossException{
		Map<String,Object> map=new HashMap<String,Object>();
		BusinessZoneTagDetailReq req=new BusinessZoneTagDetailReq();
		req.setId(id);
		
		BusinessZoneTagDetailRes res = businessZoneTagService.getBusinessZoneTagDetail(req);
		
		ResultVo result=res.getResultVo();	
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			BusinessZoneTagDetailsRes vores =new BusinessZoneTagDetailsRes();
			BeanUtils.copyProperties(vores, res);
			map.put("bzoneTag", vores);
		}else{
			throw new BossException(result.getResultCode(),result.getResultDesc());
		}
		return map;
	}
	/**
	 * 商圈标签禁用
	 * @param id
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2015年10月26日 上午9:39:18
	 */
	public Map<String,Object> delete(String id) throws TException, BossException{
		Map<String,Object> map=new HashMap<String,Object>();
		BusinessZoneTagDeleteReq req=new BusinessZoneTagDeleteReq();		
		req.setId(id);
		
		BusinessZoneTagDeleteRes res = businessZoneTagService.deleteBusinessZoneTag(req);
		
		ResultVo result=res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())){
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		}else{
			throw new BossException(result.getResultCode(),result.getResultDesc());
		}
		return map;
	}
	/**
	 * 商圈标签商户导出
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2015年10月26日 上午9:39:47
	 */
	public Map<String,Object> export(String id) throws TException, BossException{
		Map<String,Object> map=new HashMap<String,Object>();
		//封装请求参数
		BusinessZoneTagExportOutletReq req=new BusinessZoneTagExportOutletReq();
		req.setId(id);
		
		BusinessZoneTagExportOutletRes res = businessZoneTagService.exportBusinessZoneTagOutlet(req);
		
		ResultVo result= res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())){
			map.put("url", res.getUrl());
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		}else{
			throw new BossException(result.getResultCode(),result.getResultDesc());
		}
		return map;
	}
	/**
	 * 商圈标签新增或修改
	 * @param req
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2015年10月26日 上午9:40:01
	 */
	public Map<String,Object> save(BusinessZoneTagReq voreq) throws TException, BossException {
		Map<String,Object> map=new HashMap<String,Object>();
		BusinessZoneTagAddReq req=new BusinessZoneTagAddReq();
		//判断是否为修改操作，操作标识：1：新增；2：编辑
		if(StringUtil.isNotBlank(voreq.getId())){
			req.setFlag("2");
		}else{
			req.setFlag("1");
		}
		//封装请求参数
		BeanUtils.copyProperties(req, voreq);
		BusinessZoneTagAddRes res = businessZoneTagService.addBusinessZoneTag(req);
		ResultVo result=res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())){
			map.put("code",result.getResultCode());
			map.put("message", result.getResultDesc());
		}else{
			throw new BossException(result.getResultCode(),result.getResultDesc());
		}
		return map;
	}
}
