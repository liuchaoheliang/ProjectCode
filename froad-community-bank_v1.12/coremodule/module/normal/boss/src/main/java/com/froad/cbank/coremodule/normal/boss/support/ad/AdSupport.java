/**
 * <p>Project: ubank</p>
 * <p>module: coremouule</p>
 * <p>@version: Copyright © 2015 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.normal.boss.support.ad;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdRes;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdResp;
import com.froad.cbank.coremodule.normal.boss.pojo.ad.AdUpdReq;
import com.froad.cbank.coremodule.normal.boss.pojo.area.AreaPojo;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantCategoryRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCategoryVoRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.AdImgZipUtil;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ImgUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.AdLocationService;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.service.AdService;
import com.froad.thrift.service.AdvertisingService;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BossMerchantCategoryService;
import com.froad.thrift.service.BossProductCategoryService;
import com.froad.thrift.vo.AdPageVoRes;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AdVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.AdvertisingVo;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.FindAdLocationResultVo;
import com.froad.thrift.vo.FindAdvertisingResultVo;
import com.froad.thrift.vo.FindPageAdvertisingResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.merchant.BossMerchantCategoryInfoRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryListRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryVo;
import com.froad.thrift.vo.product.BossProductCategoryInfoRes;
import com.froad.thrift.vo.product.BossProductCategoryListRes;
import com.froad.thrift.vo.product.BossProductCategoryVo;

/**
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-5-11下午2:25:50</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class AdSupport {
	
	@Resource
	AdService.Iface adService;
	
	@Resource
	AdPositionService.Iface adPositionService;
	
	@Resource
	AdvertisingService.Iface advertisingService;
	
	@Resource
	ClientSupport clientSupport;
	
	@Resource
	private AreaService.Iface areaService;
	
	@Resource
	BossMerchantCategoryService.Iface BossmerchantCategoryService;
	
	@Resource
	BossProductCategoryService.Iface bossProductCategoryService;
	
	@Resource
	AdLocationService.Iface adLocationService;
	
	/** 
	 * 
	 * <p>功能简述：—— 查询广告列表</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-5-15下午01:40:57</p>
	 * <p>作者: 吴菲</p>
	 * @param adRes
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> list(AdRes adRes) throws TException{
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		// 查询条件
		AdVo advo = new AdVo();
		if(StringUtil.isNotBlank(adRes.getClientId())){
			advo.setClientId(adRes.getClientId());
		}
		if(StringUtil.isNotBlank(adRes.getTitle())){
			advo.setTitle(adRes.getTitle());
		}
		if(StringUtil.isNotBlank(adRes.getIsEnabled())){
			if(adRes.getIsEnabled() == 0){
				advo.setIsEnabled(false);
			}else{
				advo.setIsEnabled(true);
			}
		}
		// 分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(adRes.getPageNumber());
		pageVo.setPageSize(adRes.getPageSize());
	
		AdPageVoRes adPagevo = adService.getAdByPage(pageVo, advo);
		LogCvt.info("广告列表返回参数:", JSON.toJSONString(adPagevo));
		// 转换分页实体
		Page page = new Page();
		BeanUtils.copyProperties(page, adPagevo.getPage());
		
		AdResp adResp = null;
		List<AdResp> adResList = new ArrayList<AdResp>();
		if(adPagevo.getAdVoList() != null  && adPagevo.getAdVoListSize() != 0){
			for(AdVo temp : adPagevo.getAdVoList()){
				adResp = new AdResp();
				BeanUtils.copyProperties(adResp, temp);
				adResp.setEnabled(temp.isEnabled);
				adResp.setBlankTarge(temp.isBlankTarge);
				
				SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd" );
				if(StringUtil.isNotEmpty(advo.getBeginTime()+"")){
					Long btime=new Long(temp.getBeginTime());
					String stTime = format.format(btime);
					adResp.setBeginDate(stTime);
				}
				if(StringUtil.isNotEmpty(advo.getEndTime()+"")){
					Long etime=new Long(temp.getEndTime());
				    String endTime = format.format(etime);
				    adResp.setTerminalDate(endTime);
				}
				
				AdPositionVo adpovo=adPositionService.getAdPositionById(temp.getClientId(), temp.getAdPositionId());
				if(adpovo != null){
					adResp.setAdPositionName(adpovo.getName());
					adResp.setWidth(adpovo.getWidth());
					adResp.setHeight(adpovo.getHeight());
					adResp.setSizeLimit(adpovo.getSizeLimit());
				}
				adResList.add(adResp);
			}
			respMap.put("page", page);
			respMap.put("adList", adResList);
		} 
		return respMap;
	}
	
	/**
	 * 
	 * <p>功能简述：—— 查询广告详情</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-5-15下午01:37:15</p>
	 * <p>作者: 吴菲</p>
	 * @param id
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public Map<String, Object> detail(Long id) throws TException{
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		AdResp adResp = new AdResp();
		
		AdVo advo = adService.getAdById(id);
		LogCvt.info("广告详情返回参数:", JSON.toJSONString(advo));
		if(advo != null){
			BeanUtils.copyProperties(adResp, advo);
			adResp.setEnabled(advo.isEnabled);
			adResp.setBlankTarge(advo.isBlankTarge);
			SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd" );
			if(StringUtil.isNotEmpty(advo.getBeginTime()+"")){
			    Long btime=new Long(advo.getBeginTime());
			    String stTime = format.format(btime);
			    adResp.setBeginDate(stTime);
			}
			if(StringUtil.isNotEmpty(advo.getEndTime()+"")){
			    Long etime=new Long(advo.getEndTime());
			    String endTime = format.format(etime);
			    adResp.setTerminalDate(endTime);
			}
			AdPositionVo adpovo=adPositionService.getAdPositionById(advo.getClientId(), advo.getAdPositionId());
			
			if(adpovo != null){
				adResp.setAdPositionName(adpovo.getName());
				adResp.setWidth(adpovo.getWidth());
				adResp.setHeight(adpovo.getHeight());
				adResp.setSizeLimit(adpovo.getSizeLimit());
			}
			//封装图片 
			
			String path = advo.getPath();
			
			
			List<FileVo> piclist = new ArrayList<FileVo>();
			
			FileVo fileVo = new FileVo();
			if((!"".equals(path) && path != null) ){
				String[] paths = path.split("\\.");
				if(paths.length>=2){
					fileVo.setLarge(paths[0]+"-large."+paths[1]);
					fileVo.setMedium(paths[0]+"-medium."+paths[1]);
					fileVo.setThumbnail(paths[0]+"-thumbnail."+paths[1]);
					fileVo.setSource(advo.getPath());
					piclist.add(fileVo);
				}
				adResp.setFiles(piclist);
			}
			
			respMap.put("ad", adResp);
		}
		return respMap;
		
		
	}
	
	/**
	 * 
	 * <p>功能简述：—— 添加广告</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-5-11下午2:36:17</p>
	 * <p>作者: 高峰</p>
	 * @throws BossException 
	 * @throws TException 
	 * @throws ParseException 
	 */
	public  HashMap<String,Object>  add(AdRes adRes ) throws BossException, TException, ParseException { 
		HashMap<String, Object> respMap=new HashMap<String, Object>();
		OriginVo org = new OriginVo();
		org.setClientId(adRes.getClientId());
		org.setPlatType(PlatType.boss);

		AdVo ad = new AdVo();
		ad.setClientId(adRes.getClientId());
		ad.setTitle(adRes.getTitle());
		ad.setType(adRes.getType());
		
		if(adRes.getIsEnabled()==0){
			ad.setIsEnabled(false);
		}else{
			ad.setIsEnabled(true);
		}
		ad.setIsBlankTarge(adRes.isBlankTarge);
		ad.setAdPositionId(adRes.getAdPositionId());
		ad.setLink(adRes.getLink());
		ad.setContent(adRes.getContent());
		
		List<FileVo> files = adRes.getFiles();
		if (files != null && files.size() > 0) {
			for (FileVo fileVo : files) {
				ad.setPath(fileVo.getSource());
			}
		}
	 
		SimpleDateFormat srcSdf = new SimpleDateFormat("yyyy-MM-dd");
		long betime = srcSdf.parse(adRes.getBeginDate()).getTime();
		long etime = srcSdf.parse(adRes.getTerminalDate()).getTime();
		ad.setBeginTime(betime);
		ad.setEndTime(etime);
		
		AddResultVo resultVo = adService.addAd(org, ad);
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultVo().getResultCode())) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resultVo.getResultVo().getResultDesc());
		} else {
			throw new BossException(resultVo.getResultVo().getResultCode(),  resultVo.getResultVo().getResultDesc());
		}
		return respMap;
	}

	/**
	 * 
	 * <p>功能简述：—— 删除广告</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-5-15下午01:30:16</p>
	 * <p>作者: 吴菲</p>
	 * @param adRes
	 * @return
	 * @throws BossException 
	 * @throws ParseException 
	 * @throws TException 
	 */
	public HashMap<String,Object> update(AdRes adRes ) throws BossException, ParseException, TException{
		HashMap<String, Object> respMap=new HashMap<String, Object>();
		
		OriginVo org = new OriginVo();
		org.setClientId(adRes.getClientId());
		org.setPlatType(PlatType.boss);

		AdVo ad = new AdVo();
		ad.setId(adRes.getId());
		ad.setClientId(adRes.getClientId());
		ad.setTitle(adRes.getTitle());
		ad.setType(adRes.getType());
		if(adRes.getIsEnabled()==0){
			ad.setIsEnabled(false);
		}else{
			ad.setIsEnabled(true);
		}
		ad.setIsBlankTarge(adRes.isBlankTarge);
		ad.setAdPositionId(adRes.getAdPositionId());
		ad.setLink(adRes.getLink());
		ad.setContent(adRes.getContent());
		
		List<FileVo> files = adRes.getFiles();
		if (files != null && files.size() > 0) {
			for (FileVo fileVo : files) {
				ad.setPath(fileVo.getSource());
			}
		}
		SimpleDateFormat srcSdf = new SimpleDateFormat("yyyy-MM-dd");
		long betime = srcSdf.parse(adRes.getBeginDate()).getTime();
		long etime = srcSdf.parse(adRes.getTerminalDate()).getTime();
		ad.setBeginTime(betime);
		ad.setEndTime(etime);
		
		ResultVo resultVo = adService.updateAd(null, ad);
		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resultVo.getResultDesc());
		} else {
			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
		}
		return respMap;
	}
	
	/**
	 * 
	 * <p>功能简述：—— 删除广告</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-5-15下午01:35:23</p>
	 * <p>作者: 吴菲</p>
	 * @param id
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> delete(Long id) throws TException, BossException {
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		OriginVo org = new OriginVo();
//		AdPositionVo adpovo = new AdPositionVo();
//		adpovo.setId(advo.getAdPositionId());
		
		AdVo advo = adService.getAdById(id);
		org.setClientId(advo.getClientId());
		ResultVo resp = adService.deleteAd(org, advo);
		if (Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(),  resp.getResultDesc());
		}
		return respMap;
	}
	
	/**
	 * 获取广告列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:28:55
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getAdList(AdListReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<AdDetailRes> list = new ArrayList<AdDetailRes>();
		AdDetailRes temp = null;
		Page page = new Page();
		//封装分页对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		//封装请求对象
		AdvertisingVo req = new AdvertisingVo();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		FindPageAdvertisingResultVo resp = advertisingService.getAdvertisingByPage(pageVo, req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			BeanUtils.copyProperties(page, resp.getAdvertisingPageVoRes().getPage());
			if(!ArrayUtil.empty(resp.getAdvertisingPageVoRes().getAdvertisingVoList())) {
				List<ClientRes> client = clientSupport.getClient();
				for(AdvertisingVo tmp : resp.getAdvertisingPageVoRes().getAdvertisingVoList()) {
					temp = new AdDetailRes();
					BeanUtils.copyProperties(temp, tmp);
					for(ClientRes c : client){
						if(c.getClientId().equals(tmp.getClientId())){
							temp.setClientName(c.getClientName());
							break;
						}
					}
					//联查第一参数
					if(StringUtil.isNotBlank(tmp.getParamOneValue())) {
						temp.setParamOneName(getParamName(tmp.getParamOneType(), tmp.getParamOneValue(), tmp.getClientId(), org));
					}
					//联查第二参数
					if(StringUtil.isNotBlank(tmp.getParamTwoValue())) {
						temp.setParamTwoName(getParamName(tmp.getParamTwoType(), tmp.getParamTwoValue(), tmp.getClientId(), org));
					}
					//联查第三参数
					if(StringUtil.isNotBlank(tmp.getParamThreeValue())) {
						temp.setParamThreeName(getParamName(tmp.getParamThreeType(), tmp.getParamThreeValue(), tmp.getClientId(), org));
					}
					list.add(temp);
				}
			}
			map.put("adList", list);
			map.put("page", page);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	public String getParamName(String paramType, String paramValue, String clientId, OriginVo org) throws Exception {
		if("1".equals(paramType)) {//地区
			AreaVo area = areaService.findAreaById(Long.parseLong(paramValue));
			return area.getName();
		} else if("2".equals(paramType)) {//商户分类
			BossMerchantCategoryInfoRes res = BossmerchantCategoryService.getBossMerchantCategoryById(org, Long.parseLong(paramValue), clientId);
			if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())) {
				return res.getVo().getName();
			}
		} else if("3".equals(paramType) || "4".equals(paramType)) {//3-普通商品分类、4-精品商品分类
			BossProductCategoryInfoRes res = bossProductCategoryService.getBossProductCategoryById(org, Long.parseLong(paramValue), clientId);
			if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())) {
				return res.getVo().getName();
			}
		}
		return "";
	}
	
	/**
	 * 获取广告详情
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:28:55
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getAdDetail(AdDetailReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		AdDetailRes ad = null;
		List<?> paramOneList = null;
		List<?> paramTwoList = null;
		List<?> paramThreeList = null;
		//调用SERVER端接口
		FindAdvertisingResultVo resp = advertisingService.getAdvertisingById(pojo.getClientId(), pojo.getId());
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(resp.getAdvertisingVo().getId() > 0) {
				AdvertisingVo res = resp.getAdvertisingVo();
				List<ClientRes> client = clientSupport.getClient();
				ad = new AdDetailRes();
				BeanUtils.copyProperties(ad, res);
				for(ClientRes c : client){
					if(c.getClientId().equals(res.getClientId())){
						ad.setClientName(c.getClientName());
						break;
					}
				}
				//联查第一参数列表
				if(StringUtil.isNotBlank(res.getParamOneType()) && StringUtil.isNotBlank(res.getClientId())) {
					paramOneList = getParamList(res.getClientId(), res.getParamOneType(), org);
				}
				//联查第二参数列表
				if(StringUtil.isNotBlank(res.getParamTwoType()) && StringUtil.isNotBlank(res.getClientId())) {
					paramTwoList = getParamList(res.getClientId(), res.getParamTwoType(), org);				
				}
				//联查第三参数列表
				if(StringUtil.isNotBlank(res.getParamThreeType()) && StringUtil.isNotBlank(res.getClientId())) {
					paramThreeList = getParamList(res.getClientId(), res.getParamThreeType(), org);
				}
				//联查第一参数
				if(StringUtil.isNotBlank(res.getParamOneValue()) && StringUtil.isNotBlank(res.getParamOneType())) {
					ad.setParamOneName(getParamName(res.getParamOneType(), res.getParamOneValue(), res.getClientId(), org));
				}
				//联查第二参数
				if(StringUtil.isNotBlank(res.getParamTwoValue()) && StringUtil.isNotBlank(res.getParamTwoType())) {
					ad.setParamTwoName(getParamName(res.getParamTwoType(), res.getParamTwoValue(), res.getClientId(), org));
				}
				//联查第三参数
				if(StringUtil.isNotBlank(res.getParamThreeValue()) && StringUtil.isNotBlank(res.getParamThreeType())) {
					ad.setParamThreeName(getParamName(res.getParamThreeType(), res.getParamThreeValue(), res.getClientId(), org));
				}
			}
			map.put("paramOneList", paramOneList);
			map.put("paramTwoList", paramTwoList);
			map.put("paramThreeList", paramThreeList);
			map.put("ad", ad);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	public List<?> getParamList(String clientId, String paramType, OriginVo org) throws Exception {
		if("1".equals(paramType)) {
			List<AreaPojo> areaList = new ArrayList<AreaPojo>();
			AreaPojo temp = null;
			AreaVo req = new AreaVo();
			AreaVo req2 = new AreaVo();
			//调用SERVER端接口
			req.setClientId(clientId);
			req.setParentId(0);
			List<AreaVo> resp = areaService.getArea(req);
			if(!ArrayUtil.empty(resp)) {
				req2.setParentId(resp.get(0).getId());
				List<AreaVo> res = areaService.getArea(req2);//查询地级市
				if(!ArrayUtil.empty(res)) {
					if("chongqing".equals(clientId) || "shanghai".equals(clientId) || "beijing".equals(clientId) || "tianjin".equals(clientId)) {
						req2.setParentId(res.get(0).getId());
						res = areaService.getArea(req2);
					}
					for(AreaVo tmp : res) {
						temp = new AreaPojo();
						BeanUtils.copyProperties(temp, tmp);
						areaList.add(temp);
					}
				}
			}
			return areaList;
		} else if("2".equals(paramType)) {
			List<MerchantCategoryRes> list = new ArrayList<MerchantCategoryRes>();
			MerchantCategoryRes temp = null;
			//调用SERVER端接口
			BossMerchantCategoryListRes resp = BossmerchantCategoryService.findAll(clientId, false, org);
			//封装返回对象
			ResultVo result = resp.getResultVo();
			if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
				if(!ArrayUtil.empty(resp.getVoList())) {
					for(BossMerchantCategoryVo tmp : resp.getVoList()) {
						temp = new MerchantCategoryRes();
						BeanUtils.copyProperties(temp, tmp);
						list.add(temp);
					}
				}
			}
			return list;
		} else if("3".equals(paramType)) {//普通商品分类列表
			List<ProductCategoryVoRes> list = new ArrayList<ProductCategoryVoRes>();
			ProductCategoryVoRes temp = null;
			//调用SERVER端接口
			BossProductCategoryListRes resp = bossProductCategoryService.findAll(org, clientId, false, false);
			//封装返回结果对象
			if(!ArrayUtil.empty(resp.getVoList())) {
				for(BossProductCategoryVo tmp : resp.getVoList()) {
					temp = new ProductCategoryVoRes();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
			return list;
		} else if("4".equals(paramType)) {//精品商品分类列表
			List<ProductCategoryVoRes> list = new ArrayList<ProductCategoryVoRes>();
			ProductCategoryVoRes temp = null;
			//调用SERVER端接口
			BossProductCategoryListRes resp = bossProductCategoryService.findAll(org, clientId, false, true);
			//封装返回结果对象
			if(!ArrayUtil.empty(resp.getVoList())) {
				for(BossProductCategoryVo tmp : resp.getVoList()) {
					temp = new ProductCategoryVoRes();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 新增广告
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:40:12
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> addAd(AdAddReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		AdvertisingVo req = new AdvertisingVo();
		BeanUtils.copyProperties(req, pojo);
		req.setBeginTime(PramasUtil.DateFormat(pojo.getBeginTime()));
		req.setEndTime(PramasUtil.DateFormatEnd(pojo.getEndTime()));
		if(req.getBeginTime() > req.getEndTime()) {
			throw new BossException("开始时间不可大于结束时间");
		}
		req.setIsBlankTarge(pojo.getIsBlankTarge());
		//调用SERVER端接口
		AddResultVo resp = advertisingService.addAdvertising(org, req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 修改广告
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月21日 下午4:40:12
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> updateAd(AdUpdReq pojo, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		AdvertisingVo req = new AdvertisingVo();
		BeanUtils.copyProperties(req, pojo);
		req.setBeginTime(PramasUtil.DateFormat(pojo.getBeginTime()));
		req.setEndTime(PramasUtil.DateFormatEnd(pojo.getEndTime()));
		if(req.getBeginTime() > req.getEndTime()) {
			throw new BossException("开始时间不可大于结束时间");
		}
		//调用SERVER端接口
		ResultVo resp = advertisingService.updateAdvertising(org, req);
		//封装返回对象
		if(Constants.RESULT_SUCCESS.equals(resp.getResultCode())) {
			map.put("code", resp.getResultCode());
			map.put("message", resp.getResultDesc());
		} else {
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 广告图上传接口
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月23日 下午4:25:56
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, String> upload(MultipartFile file, String adLocationId) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		String fileName = file.getOriginalFilename();
		String det = fileName.substring(fileName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();
		String normal = uuid + det;
		LogCvt.info("filesize:"+file.getSize());
		//小于150k不压缩
		if(file.getSize() < 150*1024){
			//把原图临时保存到本地
			ImgUtil.saveFile(normal, file);
			//上传SCP
			String[] localFilePath = new String[]{
				Constants.getImgLocalUrl() + "/" + normal
			};
			String[] scpFileName = new String[]{
				normal
			};
			ScpUtil.uploadFile(localFilePath, scpFileName, Constants.getScpImgUploadPath(), Constants.SCPCONFIG);
			String url = Constants.getImgRemoteUrl();
			File tmpfile = new File(Constants.getImgLocalUrl() + "/" + normal);
			if(tmpfile.exists()){
				tmpfile.delete();
			}
			map.put("path", url + normal);
			return map;
		}
		
		//查询对应的广告位
		FindAdLocationResultVo resp = adLocationService.getAdLocationById(Long.parseLong(adLocationId));
		if(Constants.RESULT_SUCCESS.equals(resp.getResultVo().getResultCode())) {
			if(resp.getAdLocationVo().getId() > 0) {
				if(resp.getAdLocationVo().getHeight() <= 0 || resp.getAdLocationVo().getWidth() <= 0) {
					throw new BossException("广告图片压缩的宽高比例按广告位设置的宽高值来计算，请先设置该广告位的宽高");
				}
				//把原图临时保存到本地
				ImgUtil.saveFile(normal, file);
				//压缩图片
				AdImgZipUtil imgCom = new AdImgZipUtil(Constants.getImgLocalUrl() + "/" + normal);
				imgCom.resizeFix(resp.getAdLocationVo().getWidth(), resp.getAdLocationVo().getHeight(), Constants.getImgLocalUrl() + "/" + normal);
				//上传SCP
				String[] localFilePath = new String[]{
					Constants.getImgLocalUrl() + "/" + normal
				};
				String[] scpFileName = new String[]{
					normal
				};
				ScpUtil.uploadFile(localFilePath, scpFileName, Constants.getScpImgUploadPath(), Constants.SCPCONFIG);
				String url = Constants.getImgRemoteUrl();
				//删除本地临时保存的图片
				File tmpfile = new File(Constants.getImgLocalUrl() + "/" + normal);
				if(tmpfile.exists()){
					tmpfile.delete();
				}
				map.put("path", url + normal);
			}
		} else {
			throw new BossException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		return map;
	}
}
