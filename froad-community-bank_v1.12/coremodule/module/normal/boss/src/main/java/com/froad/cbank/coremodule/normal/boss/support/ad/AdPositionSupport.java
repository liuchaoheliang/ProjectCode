/**
 * <p>Project: ubank</p>
 * <p>module: coremouule</p>
 * <p>@version: Copyright © 2015 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.normal.boss.support.ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionDisReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionRes;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionResp;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdPositionUpdReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.AdLocationService;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.vo.AdLocationPageVoRes;
import com.froad.thrift.vo.AdLocationVo;
import com.froad.thrift.vo.AdPositionPageVoRes;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.FindAdLocationResultVo;
import com.froad.thrift.vo.FindAllAdLocationResultVo;
import com.froad.thrift.vo.FindPageAdLocationResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;

/**
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-5-11下午2:26:03</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class AdPositionSupport {

	@Resource
	AdPositionService.Iface adPositionService;
	
	@Resource
	AdLocationService.Iface adLocationService;
	
	/**
	 * <p>功能简述：—— 查询广告位列表</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-5-12下午2:06:10</p>
	 * <p>作者: 吴菲</p>
	 * @throws TException 
	 */
	public Map<String, Object> list(AdPositionRes adRes) throws TException{
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		// 查询条件
		AdPositionVo adpovo = new AdPositionVo();
		 if (StringUtil.isNotBlank(adRes.getClientId())) {
			adpovo.setClientId(adRes.getClientId());
		 }
		 if (StringUtil.isNotBlank(adRes.getName())) {
			adpovo.setName(adRes.getName());
		}
		adpovo.setIsEnable(true);
		// 分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(adRes.getPageNumber());
		pageVo.setPageSize(adRes.getPageSize());
		
		AdPositionPageVoRes adPositionPageVo = adPositionService.getAdPositionByPage(pageVo, adpovo);
		LogCvt.info("返回广告位列表参数:", JSON.toJSONString(adPositionPageVo));
		// 转换分页实体
		Page page = new Page();
		BeanUtils.copyProperties(page, adPositionPageVo.getPage());
		
		// 转换分类数据实体
		AdPositionResp adporesp = null;
		List<AdPositionResp> adpolist = new  ArrayList<AdPositionResp>();
		if(adPositionPageVo.getAdPositionVoList() !=null && adPositionPageVo.getAdPositionVoList().size()!=0){
			for(AdPositionVo temp:adPositionPageVo.getAdPositionVoList()){
				adporesp = new AdPositionResp();
				//adporesp.setClientId(temp.getClientId());
				BeanUtils.copyProperties(adporesp, temp);
				adporesp.setEnable(temp.isIsEnable());
				adpolist.add(adporesp);
			}
			respMap.put("page", page);
			respMap.put("adPositionList", adpolist);
		}
		return respMap;
	}
	
	/**
	 * <p>功能简述：—— 根据clientId获取广告位</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-5-22下午02:11:27</p>
	 * <p>作者: 吴菲</p>
	 * @param clientId
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> listAll(String clientId) throws TException{
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		AdPositionVo adPVo = new AdPositionVo();
		adPVo.setClientId(clientId);
		AdPositionResp adporesp = null;
		List<AdPositionResp> adpolist = new  ArrayList<AdPositionResp>();
		List<AdPositionVo> adPVoList =  adPositionService.getAdPosition(adPVo);
		if(adPVoList != null && adPVoList.size()>0){
			for(AdPositionVo temp:adPVoList){
				adporesp = new AdPositionResp();
				BeanUtils.copyProperties(adporesp, temp);
				adporesp.setEnable(temp.isIsEnable());
				adpolist.add(adporesp);
			}
			respMap.put("adPositionList", adpolist);
		}
		return respMap;
	}

	/**
	 * <p>功能简述：—— 查询广告位详情</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-5-12下午4:06:10</p>
	 * <p>作者: 吴菲</p>
	 * @throws TException 
	 */
	public Map<String, Object> detail(Long id) throws TException{
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		AdPositionVo adpovo = null;
		AdPositionResp adResp = new AdPositionResp();
		adpovo= adPositionService.getAdPositionById(null, id);
		if(adpovo != null) {
			BeanUtils.copyProperties(adResp, adpovo);
			adResp.setEnable(adpovo.isEnable);
			respMap.put("adPosition", adResp);
		}
		return respMap;
	}
	
	/**
	 * <p>功能简述：—— 添加广告位</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-5-12下午5:11:31</p>
	 * <p>作者: 吴菲</p>
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> add(AdPositionRes adRes) throws TException, BossException {
		LogCvt.info("新增广告位请求参数:", JSON.toJSONString(adRes));
		HashMap<String, Object> respMap=new HashMap<String, Object>();
		OriginVo org = new OriginVo();
		org.setClientId(adRes.getClientId());
		org.setPlatType(PlatType.boss);
		
		AdPositionVo adPvo = new AdPositionVo();
		adPvo.setClientId(adRes.getClientId());
		adPvo.setName(adRes.getName());
		adPvo.setPositionStyle(adRes.getPositionStyle());
		adPvo.setPositionPage(adRes.getPositionPage());
		adPvo.setDescription(adRes.getDescription());
		adPvo.setWidth(adRes.getWidth());
		adPvo.setHeight(adRes.getHeight());
		adPvo.setPositionPoint(adRes.getPositionPoint());
		adPvo.setTerminalType(adRes.getTerminalType());
		adPvo.setSizeLimit(adRes.getSizeLimit());
		AddResultVo resultVo =  adPositionService.addAdPosition(org, adPvo);
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultVo().getResultCode())) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resultVo.getResultVo().getResultDesc());
		} else {
			throw new BossException(resultVo.getResultVo().getResultCode(),  resultVo.getResultVo().getResultDesc());
		}
		return respMap;
	}
	
	/**
	 * <p>功能简述：—— 修改广告位</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-5-12下午6:12:57</p>
	 * <p>作者: 吴菲</p>
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> update(AdPositionRes adRes) throws TException, BossException {
		HashMap<String, Object> respMap=new HashMap<String, Object>();
		OriginVo org = new OriginVo();
		
		org.setClientId(adRes.getClientId());
		org.setPlatType(PlatType.boss);
		
		AdPositionVo adPvo = new AdPositionVo();
		adPvo.setId(adRes.getId());
		adPvo.setClientId(adRes.getClientId());
		adPvo.setName(adRes.getName());
		adPvo.setPositionStyle(adRes.getPositionStyle());
		adPvo.setPositionPage(adRes.getPositionPage());
		adPvo.setDescription(adRes.getDescription());
		adPvo.setWidth(adRes.getWidth());
		adPvo.setHeight(adRes.getHeight());
		adPvo.setPositionPoint(adRes.getPositionPoint());
		adPvo.setTerminalType(adRes.getTerminalType());
		adPvo.setSizeLimit(adRes.getSizeLimit());
		ResultVo resultVo =  adPositionService.updateAdPosition(org, adPvo);
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resultVo.getResultDesc());
		} else {
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return respMap;
	}
	
	/**
	 * <p>功能简述：—— 删除广告位</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-5-12下午7:14:02</p>
	 * <p>作者: 吴菲</p>
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> delete(Long id) throws TException, BossException {
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		AdPositionVo adpPvo = new AdPositionVo();
		OriginVo org = new OriginVo();
		//封装请求对象
		adpPvo.setId(id);
		List<AdPositionVo> adpovoList= adPositionService.getAdPosition(adpPvo);
		for(AdPositionVo vo : adpovoList) {
			adpPvo = vo;
			break;
		}
		org.setClientId(adpPvo.getClientId());
		ResultVo resp = adPositionService.deleteAdPosition(org, adpPvo);
		if (Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(),  resp.getResultDesc());
		}
		return respMap;
	}
	
	/**
	 * 获取广告位列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午2:37:11
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getAdPositionList(AdPositionListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page page = new Page();
		List<AdPositionDetailRes> list = new ArrayList<AdPositionDetailRes>();
		AdPositionDetailRes temp = null;
		//封装分页对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		//封装请求对象
		AdLocationVo req = new AdLocationVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		FindPageAdLocationResultVo resp = adLocationService.getAdLocationByPage(pageVo, req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			AdLocationPageVoRes res = resp.getAdLocationPageVoRes();
			BeanUtils.copyProperties(page, res.getPage());
			if(!ArrayUtil.empty(res.getAdLocationVoList())) {
				for(AdLocationVo tmp : res.getAdLocationVoList()) {
					temp = new AdPositionDetailRes();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
			map.put("page", page);
			map.put("adPositionList", list);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 获取广告位详情
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:06:00
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getAdPositionDetail(AdPositionDetailReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		AdPositionDetailRes adPosition = null;
		//调用SERVER端接口
		FindAdLocationResultVo resp = adLocationService.getAdLocationById(pojo.getId());
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(resp.getAdLocationVo().getId() > 0) {
				adPosition = new AdPositionDetailRes();
				BeanUtils.copyProperties(adPosition, resp.getAdLocationVo());
			}
			map.put("adPosition", adPosition);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 新增广告位
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:06:15
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> addAdPosition(AdPositionAddReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		AdLocationVo req = new AdLocationVo();
		BeanUtils.copyProperties(req, pojo);
		req.setEnableStatus("0");//启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中
		//调用SERVER端接口
		AddResultVo resp = adLocationService.addAdLocation(org, req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(!Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		} else {
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 修改广告位
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:45:14
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> updateAdPosition(AdPositionUpdReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		AdLocationVo req = new AdLocationVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		ResultVo resp = adLocationService.updateAdLocation(org, req);
		//封装返回对象
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 禁用广告位
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:45:14
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> disableAdPosition(AdPositionDisReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		ResultVo resp = adLocationService.disabledAdLocation(org, pojo.getId());
		//封装返回对象
		if(!Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		} else {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 获取所有广告位列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午3:45:14
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getAllAdPosition(AdPositionListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<AdPositionDetailRes> list = new ArrayList<AdPositionDetailRes>();
		AdPositionDetailRes temp = new AdPositionDetailRes();
		//封装请求对象
		AdLocationVo req = new AdLocationVo();
		BeanUtils.copyProperties(req, pojo);
		req.setEnableStatus("0");//只查询已启用
		//调用SERVER端接口
		FindAllAdLocationResultVo resp = adLocationService.getAdLocation(req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(!ArrayUtil.empty(resp.getAdLocationVoList())) {
				for(AdLocationVo tmp : resp.getAdLocationVoList()) {
					temp = new AdPositionDetailRes();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
			map.put("adPositionList", list);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
}
