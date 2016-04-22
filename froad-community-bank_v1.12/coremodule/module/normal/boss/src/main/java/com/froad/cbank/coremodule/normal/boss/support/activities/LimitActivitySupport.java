package com.froad.cbank.coremodule.normal.boss.support.activities;
//package com.froad.cbank.coremodule.normal.boss.support;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.thrift.TException;
//import org.springframework.stereotype.Service;
//
//import com.alibaba.fastjson.JSON;
//import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
//import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
//import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
//import com.froad.cbank.coremodule.normal.boss.utils.Constants;
//import com.froad.cbank.coremodule.normal.boss.exception.BossException;
//import com.froad.cbank.coremodule.normal.boss.pojo.LimitActivityInfoVo;
//import com.froad.cbank.coremodule.normal.boss.pojo.LimitActivityInfoVoReq;
//import com.froad.cbank.coremodule.normal.boss.pojo.Page;
//import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
//import com.froad.thrift.service.ActivitiesService;
//import com.froad.thrift.service.ClientService;
//import com.froad.thrift.service.ProductService;
//import com.froad.thrift.vo.ActivitiesPageVoRes;
//import com.froad.thrift.vo.ActivitiesVo;
//import com.froad.thrift.vo.AddResultVo;
//import com.froad.thrift.vo.ClientVo;
//import com.froad.thrift.vo.OriginVo;
//import com.froad.thrift.vo.PageVo;
//import com.froad.thrift.vo.PlatType;
//import com.froad.thrift.vo.ResultVo;
//
///**
// * 类描述：限购活动相关业务类
// * @version: 1.0
// * @Copyright www.f-road.com.cn Corporation 2015 
// * @author: f-road.com.cn
// * @time: 2015-5-23下午1:13:33 
// */
//
//@Service
//public class LimitActivitySupport {
//	@Resource
//	ActivitiesService.Iface activitiesService;
//	
//	@Resource
//	ClientService.Iface clientService;
//	
//	@Resource
//	ProductService.Iface productService;
//	/**
//	 * 
//	 * <p>功能简述：限购活动列表</p> 
//	 * <p>使用说明：条件查询限购活动列表</p> 
//	 * <p>创建时间：2015年5月23日下午1:28:49</p>
//	 * @param LimitActivityInfoVoReq
//	 * @return
//	 * @throws TException 
//	 */
//
//	public Map<String, Object> list(LimitActivityInfoVoReq activityVoReq) throws TException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		LimitActivityInfoVo activityVoRes = null;
//		// 封装页面信息
//		PageVo pageVo = new PageVo();
//		pageVo.setPageNumber(activityVoReq.getPageNumber());
//		pageVo.setPageSize(activityVoReq.getPageSize());
//		pageVo.setFirstRecordTime(activityVoReq.getFirstRecordTime());
//		pageVo.setLastPageNumber(activityVoReq.getLastPageNumber());
//		pageVo.setLastRecordTime(activityVoReq.getLastRecordTime());
//		pageVo.setBegDate(activityVoReq.getBegDate()==0?activityVoReq.getStartDate():activityVoReq.getBegDate());
//		pageVo.setEndDate(activityVoReq.getEndDate());
//		
//		Page page=new Page();
//		// 封装请求参数
//		ActivitiesVo activitiesVo = new ActivitiesVo();
//		if (StringUtil.isNotBlank(activityVoReq.getClientId())) {
//			activitiesVo.setClientId(activityVoReq.getClientId());
//		}
//		if (StringUtil.isNotBlank(activityVoReq.getActivitiesType())) {
//			activitiesVo.setActivitiesType(activityVoReq.getActivitiesType());
//		}
////		try {
////			String title =new String(activityVoReq.getActName().getBytes("iso8859-1"), "UTF8");
////			activityVoReq.setActName(title);
////		} catch (UnsupportedEncodingException e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
//		activitiesVo.setActivitiesName(activityVoReq.getActName());
//		// activitiesVo.setIsEnable("0".equals(activityVoReq.getActStatu())?true:false);
//		// //限购状态
//		if (StringUtil.isNotBlank(activityVoReq.getActStatus())) {
//			activitiesVo.setEnableType(Integer.parseInt(activityVoReq
//					.getActStatus()));
//		}
//		activitiesVo.setActivitiesName(activityVoReq.getActName()); 
//		ActivitiesPageVoRes activitiesPageVoRes = activitiesService
//				.getActivitiesByPage(pageVo, activitiesVo);
//		PageVo pageVoRes = activitiesPageVoRes.getPage();
//		BeanUtils.copyProperties(page, pageVoRes);
//		map.put("page", page);
//		List<ActivitiesVo> activitiesVoList = activitiesPageVoRes
//				.getActivitiesVoList();
//		List<LimitActivityInfoVo> list = null;
//		if (null != activitiesVoList && activitiesVoList.size() > 0) {
//			list = new ArrayList<LimitActivityInfoVo>();
//			// 获取客户端列表
//			List<ClientVo> client = clientService.getClient(new ClientVo());
//
//			for (ActivitiesVo activitiesVo2 : activitiesVoList) {
//				activityVoRes = new LimitActivityInfoVo();
//				BeanUtils.copyProperties(activityVoRes, activitiesVo2);
//				if(StringUtil.isNotBlank(activitiesVo2.getId())){
//					activityVoRes.setId(String.valueOf(activitiesVo2.getId()));
//				}
//				activityVoRes.setActName(activitiesVo2.getActivitiesName());
//				activityVoRes.setClientId(activitiesVo2.getClientId());
//				activityVoRes.setActivitiesType(activitiesVo2.getActivitiesType());
//				if(StringUtil.isNotBlank(activitiesVo2.getPoints())){
//					activityVoRes.setPoints(String.valueOf(activitiesVo2.getPoints()));
//				}
//				if(StringUtil.isNotBlank(activitiesVo2.getVipPrice())){
//					activityVoRes.setVIPprice(activitiesVo2.getVipPrice());
//				}
//				if(StringUtil.isNotBlank(activitiesVo2.getVipLimit())){
//					activityVoRes.setVIPlimit(String.valueOf(activitiesVo2.getVipLimit()));
//				}
//				if(StringUtil.isNotBlank(activitiesVo2.getLimitNum())){
//					activityVoRes.setLimitNum(String.valueOf(activitiesVo2.getLimitNum()));
//				}
//				// 活动状态
//				if(StringUtil.isNotBlank(activitiesVo2.getEnableType())){
//					activityVoRes.setActStatu(String.valueOf(activitiesVo2.getEnableType()));						
//				}
//				if(StringUtil.isNotBlank(activitiesVo2.getCreateTime())){
//					activityVoRes.setCreateTime(String.valueOf(activitiesVo2.getCreateTime()));
//				}
//				for (ClientVo clientVo : client) {
//					if (activitiesVo2.getClientId().equals(
//							clientVo.getClientId())) {
//						activityVoRes.setClientName(clientVo.getName());
//						break;
//					}
//				}
//				list.add(activityVoRes);
//			}
//		}
//		map.put("page", page);
//		map.put("actList", list);
//		return map;
//	}
//	
//	/**
//	 * 
//	 * <p>功能简述：删除限购活动</p> 
//	 * <p>使用说明：根据主键id删除限购活动</p> 
//	 * <p>创建时间：2015年5月23日下午1:28:02</p>
//	 * @param id
//	 * @return
//	 * @throws TException 
//	 * @throws BossException 
//	 */
//	public Map<String,Object> deleteById(Long id,String clientId) throws TException, BossException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		OriginVo originVo = new OriginVo();
//		originVo.setPlatType(PlatType.boss);
//		ActivitiesVo activitiesVo = new ActivitiesVo();
//		activitiesVo.setId(id);
//		ResultVo resultVo = activitiesService.deleteActivities(originVo,
//				clientId,id);
//		LogCvt.info("删除限购活动server返回数据", JSON.toJSONString(resultVo));
//		if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
//			map.put("code", Constants.RESULT_SUCCESS);
//			map.put("message", resultVo.getResultDesc());
//		} else {
//			throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
//		}
//		return map;
//	}
//	
//	/**
//	 * 
//	 * <p>功能简述：查询限购活动详情</p> 
//	 * <p>使用说明：根据id查询限购活动详情</p> 
//	 * <p>创建时间：2015年5月23日下午1:12:49</p>
//	 * @param id
//	 * @return
//	 * @throws TException 
//	 */
//	public Map<String, Object> queryById(String id) throws TException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		LimitActivityInfoVo voRes = null;
//		Long activityId = Long.parseLong(id);
//		ActivitiesVo activitiesVo = activitiesService.getActivitiesById(null, activityId);
//		if (null != activitiesVo) {
//			List<ClientVo> client = clientService.getClient(new ClientVo());
//			voRes = new LimitActivityInfoVo();
//			BeanUtils.copyProperties(voRes, activitiesVo);
//			if(StringUtil.isNotBlank(activitiesVo.getEnableType())){
//				voRes.setActStatu(String.valueOf(activitiesVo.getEnableType()));						
//			}
//			if(StringUtil.isNotBlank(activitiesVo.getCreateTime())){
//				voRes.setCreateTime(DateUtil.longToString(activitiesVo
//						.getCreateTime()));
//			}
//			voRes.setActivitiesType(activitiesVo.getActivitiesType());
//			if(StringUtil.isNotBlank(activitiesVo.getPoints())){
//				voRes.setPoints(String.valueOf(activitiesVo.getPoints()));
//			}
//			if(StringUtil.isNotBlank(activitiesVo.getVipPrice())){
//				voRes.setVIPprice(activitiesVo.getVipPrice());
//			}
//			if(StringUtil.isNotBlank(activitiesVo.getActivitiesName())){
//				voRes.setActName(activitiesVo.getActivitiesName());
//			}
//			if(StringUtil.isNotBlank(activitiesVo.getVipLimit())){
//				voRes.setVIPlimit(String.valueOf(activitiesVo.getVipLimit()));
//			}
//			if(StringUtil.isNotBlank(activitiesVo.getLimitNum())){
//				voRes.setLimitNum(String.valueOf(activitiesVo.getLimitNum()));
//			}
//
//			if(StringUtil.isNotBlank(String.valueOf(activitiesVo.getCreateTime()))){
//				voRes.setCreateTime(String.valueOf(activitiesVo.getCreateTime()));
//			}
//			// 转化client中方名字
//			for (ClientVo clientVo : client) {
//				if (voRes.getClientId().equals(clientVo.getClientId())) {
//					voRes.setClientName(clientVo.getName());
//					break;
//				}
//			}
//			if(StringUtil.isNotBlank(String.valueOf(activitiesVo.getLimitNum()))){
//				voRes.setLimitNum(String.valueOf(activitiesVo.getLimitNum()));
//			}
//			map.put("activity", voRes);
//		}
//		return map;
//	}
//	
//	/**
//	 * 
//	 * <p>功能简述：新增或者修改限购活动</p> 
//	 * <p>使用说明：如果有id时为修改限购活动,当无id时为新增限购活动</p> 
//	 * <p>创建时间：2015年5月23日下午1:18:03</p>
//	 * @param activityVoReq
//	 * @return
//	 * @throws TException 
//	 * @throws BossException 
//	 */
//	public Map<String, Object> saveOrUpdate(LimitActivityInfoVoReq activityVoReq) throws TException, BossException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		OriginVo originVo = new OriginVo();
//		originVo.setPlatType(PlatType.boss);
//		ActivitiesVo activitiesVo = new ActivitiesVo();
//		activitiesVo.setActivitiesName(activityVoReq.getActName());
//		activitiesVo.setActivitiesType(activityVoReq.getActivitiesType());
//		activitiesVo.setClientId(activityVoReq.getClientId());
//		activitiesVo.setBeginTime(0L);
//		activitiesVo.setEndTime(9999999999999L);
//		if(StringUtil.isNotBlank(activityVoReq.getVIPprice())){
//			activitiesVo.setVipPrice(activityVoReq.getVIPprice());
//		}
//		if(StringUtil.isNotBlank(activityVoReq.getActStatus())){
//			activitiesVo.setEnableType(Integer.valueOf(activityVoReq.getActStatus()));		
//		}
//		if(StringUtil.isNotEmpty(activityVoReq.getLimitNum())){
//			activitiesVo.setLimitNum(Integer.parseInt(activityVoReq
//				.getLimitNum()));
//		}
//		if(StringUtil.isNotEmpty(activityVoReq.getActStatus())){
//			activitiesVo.setEnableType(Integer.parseInt(activityVoReq
//				.getActStatus()));
//		}
//		if(StringUtil.isNotBlank(activityVoReq.getPoints())){
//			activitiesVo.setPoints(Integer.valueOf(activityVoReq.getPoints()));			
//		}
//		if(StringUtil.isNotBlank(activityVoReq.getRemark())){
//			activitiesVo.setRemark(activityVoReq.getRemark());			
//		}
//		if(StringUtil.isNotBlank(activityVoReq.getVIPprice())){
//			activitiesVo.setVipPrice(activityVoReq.getVIPprice());
//		}	
//		if (null == activityVoReq.getId()) {
//			if(StringUtil.isBlank(activitiesVo.getCreateTime()+"")){
//				activitiesVo.setCreateTime(new Date().getTime());
//			}
//			AddResultVo resultVo = activitiesService.addActivities(originVo, activitiesVo);
//			if (Constants.RESULT_SUCCESS.equals(resultVo.getResultVo().getResultCode())) {
//				map.put("code", Constants.RESULT_SUCCESS);
//				map.put("message", resultVo.getResultVo().getResultDesc());
//			} else {
//				throw new BossException(resultVo.getResultVo().getResultCode(),  resultVo.getResultVo().getResultDesc());
//			}
//		} else {
//			activitiesVo.setId(Long.valueOf(activityVoReq.getId()));
//			ResultVo resultVo = activitiesService.updateActivities(
//					originVo, activitiesVo);
//			if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
//				map.put("code", Constants.RESULT_SUCCESS);
//				map.put("message", resultVo.getResultDesc());
//			} else {
//				throw new BossException(resultVo.getResultCode(),  resultVo.getResultDesc());
//			}
//		}
//		return map;
//	}
//
//	/**
//	 * 
//	 * <p>功能简述：作废活动</p> 
//	 * <p>使用说明：作废活动</p> 
//	 * <p>创建时间：2015年5月26日下午3:42:26</p>
//	 * @param LimitActivityInfoVoReq
//	 * @return
//	 * @throws TException 
//	 * @throws BossException 
//	 */
//	public Map<String,Object> discard(LimitActivityInfoVoReq activityVoReq) throws TException, BossException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		OriginVo originVo = new OriginVo();
//		originVo.setPlatType(PlatType.boss);
//		// 解绑活动下的所有商品
//		ResultVo vo = productService.removeActivtyFromProducts(originVo, Long.valueOf(activityVoReq.getId()), null);
//		if (!Constants.RESULT_SUCCESS.equals(vo.getResultCode())) {
//			throw new BossException( vo.getResultCode(),  vo.getResultDesc());
//		}
//		return map;
//	}
//
//}
