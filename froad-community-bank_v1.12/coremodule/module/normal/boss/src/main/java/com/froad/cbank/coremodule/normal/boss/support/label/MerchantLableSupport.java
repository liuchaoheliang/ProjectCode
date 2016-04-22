package com.froad.cbank.coremodule.normal.boss.support.label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.AddMerchantActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.DeleteRelateMerchantReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.EnableMerchantReqVo;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ExportRelateActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantActivityDetailReqVo;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantLableActivityReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantLableActivityRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantRelatePageVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantWeightActivityTagRes;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantWeigthvoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.RelateMerchantActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.thrift.service.MerchantActivityTagService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.recommendactivitytag.AdjustMerchantWeightReqVo;
import com.froad.thrift.vo.recommendactivitytag.DeleteRelateMerchantReqVo;
import com.froad.thrift.vo.recommendactivitytag.EnableMerchantActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.ExportRelateActivityTagReqVo;
import com.froad.thrift.vo.recommendactivitytag.InputRelateMerchantActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.InputRelateMerchantActivityVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantActivityTagDetailReqVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantActivityTagDetailResVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantNameResVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantWeightActivityTagPageReqVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantWeightActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantWeightActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RelateMerchantActivityVo;

/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月22日 下午5:15:39
 * @desc 商户活动support
 */
@Service
public class MerchantLableSupport {
	
	@Resource
	MerchantActivityTagService.Iface  merchantActivityTagService;
	
	@Resource
	ClientSupport clientSupport;
	
	/**
	 * 
	 * @throws TException 
	 * @desc 分页查询商户活动
	 * @createTime 2015年10月22日 下午5:37:24
	 */
	public HashMap<String, Object> queryMerchantActivitysByPage(MerchantLableActivityReq req) throws TException{
		//拿到所有客户端
		List<ClientRes>clientList=clientSupport.getClient();
		PageVo pageVo=new PageVo();
		RecommendActivityTagVo recommendActivityTagVo=new RecommendActivityTagVo();
		
		if(StringUtil.isNotBlank(req.getClientId())){
			recommendActivityTagVo.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getActivityName())){
			recommendActivityTagVo.setActivityName(req.getActivityName());
		}
		if(StringUtil.isNotBlank(req.getStatus())){
			recommendActivityTagVo.setStatus(req.getStatus());
		}
		
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		List<MerchantLableActivityRes> list = null;
		MerchantLableActivityRes res = null;
		
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		Page page=new Page();
		
		LogCvt.info("查询条件.......recommendActivityTagVo："+JSON.toJSONString(recommendActivityTagVo));
		//执行查询
		RecommendActivityTagPageVo recommendActivityTagPageVo=merchantActivityTagService.findMerchantTagByPage(recommendActivityTagVo, pageVo);
		BeanUtils.copyProperties(page, recommendActivityTagPageVo.getPageVo());
		if( !ArrayUtil.empty(recommendActivityTagPageVo.recommendvos)){
			list=new   ArrayList<MerchantLableActivityRes>();
			for(RecommendActivityTagVo temp : recommendActivityTagPageVo.recommendvos ){
				res=new MerchantLableActivityRes();
				BeanUtils.copyProperties(res,temp);
				//转换时间格式yyyy-MM-dd
				res.setCreateTime(transLateDate(temp.getCreateTime(),false));
				//设置客户端名称
				res=setClientName(res,clientList);
				list.add(res);
			}
		}
		resMap.put("pageCount", page.getPageCount());
		resMap.put("totalCountExport", page.getTotalCount());
		resMap.put("pageNumber", page.getPageNumber());
		resMap.put("page", page);
		resMap.put("merchantActivityList", list);
		return  resMap;
	}
	
	/**
	 * 
	 * @throws TException 
	 * @throws NumberFormatException 
	 * @desc 活动详情
	 * @createTime 2015年10月23日 下午1:48:09
	 */
	public HashMap<String, Object> findMerchantTagDetail(MerchantActivityDetailReqVo req) throws NumberFormatException, TException{
		List<ClientRes>clientList=clientSupport.getClient();
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		MerchantLableActivityRes merchantLableActivityRes=null;
		
		MerchantActivityTagDetailReqVo tempVo=new MerchantActivityTagDetailReqVo();
		//实体拷贝
		BeanUtils.copyProperties(tempVo, req);
		//执行查询(查询推荐活动列表详情)
		MerchantActivityTagDetailResVo merchantActivityTagDetailResVo=merchantActivityTagService.findMerchantTagDetail(tempVo);
		if(merchantActivityTagDetailResVo.recommendVo!=null){
			merchantLableActivityRes=new MerchantLableActivityRes();
			//实体拷贝
			BeanUtils.copyProperties(merchantLableActivityRes,merchantActivityTagDetailResVo.recommendVo);
			//设置时间格式
			merchantLableActivityRes.setCreateTime(transLateDate(merchantActivityTagDetailResVo.recommendVo.getCreateTime(),true));
			//设置客户端名称
			merchantLableActivityRes=setClientName(merchantLableActivityRes,clientList);
		}
		resMap.put("merchantLableActivityRes", merchantLableActivityRes);
		return  resMap;
	}
	
	/**
	 * 
	 * @throws TException 
	 * @throws NumberFormatException 
	 * @throws BossException 
	 * @desc 删除关联商户
	 * @createTime 2015年10月26日 上午9:32:17
	 */
	public HashMap<String, Object> deleteRelateMerchant(DeleteRelateMerchantReq req) throws NumberFormatException, TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		DeleteRelateMerchantReqVo temp=new DeleteRelateMerchantReqVo();
		BeanUtils.copyProperties(temp, req);
		ResultVo resultVo=merchantActivityTagService.deleteRelateMerchant(temp);
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return resMap;
	}
	
	/**
	 * 
	 * @desc 调整关联商户权重
	 * @createTime 2015年10月26日 上午9:56:33
	 */
	public HashMap<String, Object> adjustMerchantWeight(MerchantWeigthvoReq req) throws NumberFormatException, TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		AdjustMerchantWeightReqVo temp=new AdjustMerchantWeightReqVo();
		BeanUtils.copyProperties(temp, req);
		ResultVo resultVo=merchantActivityTagService.adjustMerchantWeight(temp);
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return resMap;
	}
	
	/**
	 * 
	 * @desc 启用或者禁用
	 * @createTime 2015年10月28日 上午11:04:45
	 */
	public HashMap<String, Object> enableMerchantRecommendActivityTag(EnableMerchantReqVo req) throws NumberFormatException, TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		EnableMerchantActivityReqVo temp=new EnableMerchantActivityReqVo();
		BeanUtils.copyProperties(temp, req);
		ResultVo resultVo=merchantActivityTagService.enableMerchantRecommendActivityTag(temp);
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return resMap;
		
	}
	
	/**
	 * 
	 * @throws TException 
	 * @throws BossException 
	 * @desc 商户推荐活动新增
	 * @createTime 2015年10月27日 上午9:40:32
	 */
	public HashMap<String,Object> addMerchantLable(AddMerchantActivityVoReq req) throws TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		RecommendActivityTagVo temp=new RecommendActivityTagVo();
		BeanUtils.copyProperties(temp, req);
		ResultVo resultVo=merchantActivityTagService.addMerchantActivityTag(temp);
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return resMap;
	}
	
	/**
	 * 
	 * @desc 商户推荐活动修改
	 * @createTime 2015年11月3日 下午1:48:24
	 */
	public HashMap<String,Object> updateMerchantActivityTag(AddMerchantActivityVoReq req) throws TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		RecommendActivityTagVo temp=new RecommendActivityTagVo();
		BeanUtils.copyProperties(temp, req);
		ResultVo resultVo=merchantActivityTagService.updateMerchantActivityTag(temp);
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return resMap;
	}
	
	/**
	 * 
	 * @throws TException 
	 * @throws BossException 
	 * @desc 商户关联
	 * @createTime 2015年10月27日 下午5:32:20
	 */
	public HashMap<String,Object> relateMechantActivity(RelateMerchantActivityVoReq req) throws TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		RelateMerchantActivityVo temp=new RelateMerchantActivityVo();
		BeanUtils.copyProperties(temp, req);
		//执行关联
		ResultVo resultVo=merchantActivityTagService.relateMerchantInfo(temp);
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
		}
		return resMap;
	}
	
	/**
	 * 
	 * @desc 用户时间转换
	 * @createTime 2015年10月23日 下午3:00:03
	 */
	private String transLateDate(long time,boolean dateType){
		String dateTmp="";
		if(time==0){
			return dateTmp;
		}else{
		Date dd =DateUtil.longToDate(Long.valueOf(time));
		 dateTmp = DateUtil.formatDate(dd, dateType);
		 return dateTmp;
	}
		
	}
	
	/**
	 * 
	 * @desc 用于设置客户端名称
	 * @createTime 2015年10月23日 下午3:28:29
	 */
	private MerchantLableActivityRes setClientName(MerchantLableActivityRes res,List<ClientRes>clientList){
		for (ClientRes c : clientList) {
			if(c.getClientId().equals(res.getClientId())){
				res.setClientName(c.getClientName());
			}
		}
		return res;
	}
	
	/**
	 * 
	 * @throws TException 
	 * @throws NumberFormatException 
	 * @desc 查询关联商户列表
	 * @createTime 2015年10月26日 上午11:43:41
	 */
	public HashMap<String, Object> findRelate(MerchantRelatePageVoReq req) throws NumberFormatException, TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		PageVo pageVo=new PageVo();
		Page page=new Page();
		MerchantWeightActivityTagPageReqVo merchantWeightActivityTagPageReqVo=new MerchantWeightActivityTagPageReqVo();
		//集合返回关联商户列表
		List<MerchantWeightActivityTagRes> merchantVos=null;
		MerchantWeightActivityTagRes res=null;
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		//设置分页对象
		merchantWeightActivityTagPageReqVo.setPageVo(pageVo);
		//查询条件拷贝
		BeanUtils.copyProperties(merchantWeightActivityTagPageReqVo, req);
		//执行查询
		MerchantWeightActivityTagPageVo  merchantWeightActivityTagPageVo=merchantActivityTagService.findRelateMerchantInfoByPage(merchantWeightActivityTagPageReqVo);
		//分页对象拷贝
		BeanUtils.copyProperties(page, merchantWeightActivityTagPageVo.getPageVo());
		if( !ArrayUtil.empty(merchantWeightActivityTagPageVo.relateMerchants)){
			merchantVos=new ArrayList<MerchantWeightActivityTagRes>();
			for(MerchantWeightActivityTagVo temp : merchantWeightActivityTagPageVo.relateMerchants){
				res=new MerchantWeightActivityTagRes();
				BeanUtils.copyProperties(res, temp);
				//如果最后修改的时间为0，那么就返回创建时间
				if(temp.getUpdateTime()==0){
					res.setUpdateTime(transLateDate(temp.getCreateTime(),true));
				}else{
					res.setUpdateTime(transLateDate(temp.getUpdateTime(),true));
				}
				//时间格式转换
				//res.setCreateTime(transLateDate(temp.getCreateTime(),true));
				
				merchantVos.add(res);
			}
		}
		resMap.put("pageCount", page.getPageCount());
		resMap.put("totalCountExport", page.getTotalCount());
		resMap.put("pageNumber", page.getPageNumber());
		resMap.put("page", page);
		//关联商户列表
		resMap.put("merchantVos",merchantVos);
		return resMap;
	}
	
	/**
	 * 
	 * @throws IOException 
	 * @throws BossException 
	 * @throws TException 
	 * @desc 批量关联商户
	 * @createTime 2015年10月28日 下午3:18:52
	 */
	public HashMap<String, Object> inputRelateMerchantInfo(String activityNo,String clientId,String uname,List<List<String>> data,String activityId) throws BossException, IOException, TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		InputRelateMerchantActivityReqVo temp=new InputRelateMerchantActivityReqVo();
		temp.setClientId(clientId);
		temp.setActivityNo(activityNo);
		temp.setOperator(uname);
		temp.setActivityId(Long.parseLong(activityId));
		for(int i = 1;i <data.size();i++){
			List<String> l = data.get(i);
			InputRelateMerchantActivityVo tempVo=new InputRelateMerchantActivityVo();
			tempVo.setActivityNo(activityNo);
			tempVo.setMerchantName(l.get(1));
			tempVo.setMerchantId(l.get(2));
			tempVo.setId(i+1);
			tempVo.setWeight((int)Double.parseDouble(l.get(3))+"");
			temp.addToVos(tempVo);
		}
			//执行批量新增
			ResultVo resultVo=merchantActivityTagService.inputRelateMerchantInfo(temp);
			//判断响应是否成功
			if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
				resMap.put("code",Constants.RESULT_SUCCESS);
				resMap.put("message", "上传关联文件成功");
			}else{
				throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
			}
			return resMap;
	}
	
	/**
	 * 
	 * @throws TException 
	 * @throws BossException 
	 * @desc 根据营业执照查询商户名称
	 * @createTime 2015年11月2日 上午11:29:29
	 */
	public HashMap<String,Object> queryMerchantNameByLicense(String clientId, String license) throws TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		MerchantNameResVo res=merchantActivityTagService.queryMerchantNameByLicense(clientId, license);
		String merchantName=res.merchantName;
		if(StringUtil.isBlank(merchantName)){
			throw new BossException(res.resultVo.getResultCode(), res.resultVo.getResultDesc());
		}else{
			resMap.put("merchantName", merchantName);
		}
		return resMap;
	}
	
	/**
	 * 
	 * <p>Title:关联商户信息导出 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午4:26:11 
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> exportRelateActivity(ExportRelateActivityVoReq req) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ExportRelateActivityTagReqVo temp = new ExportRelateActivityTagReqVo();
		BeanUtils.copyProperties(temp, req);
		ExportResultRes res = merchantActivityTagService.exportMerchantRelateActivityTag(temp);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}else{
			map.put("code", res.getResultVo().getResultCode());
			map.put("message","操作成功！");
			map.put("url", res.getUrl());
		}
		return map;
	}
	
}
