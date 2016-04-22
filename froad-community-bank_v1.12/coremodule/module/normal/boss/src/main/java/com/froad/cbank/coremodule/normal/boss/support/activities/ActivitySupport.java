package com.froad.cbank.coremodule.normal.boss.support.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.ActivityListVo;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.ActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.activities.ActivityVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.ActivitiesPageVoRes;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;

/**
 * 
 * <p>标题: 商品活动</p>
 * <p>说明: 商品活动的相关业务</p>
 * <p>创建时间：2015年5月13日下午2:09:02</p>
 * <p>作者: 陈明灿</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class ActivitySupport {
	
	@Resource
	ActivitiesService.Iface activitiesService;
	@Resource
	ClientService.Iface clientService;
	@Resource
	ProductService.Iface productService;
	/**
	 * 
	 * <p>功能简述：活动列表</p> 
	 * <p>使用说明：条件查询活动列表</p> 
	 * <p>创建时间：2015年5月13日下午2:28:49</p>
	 * <p>作者: 陈明灿</p>
	 * @param activityVoReq
	 * @return
	 * @throws TException 
	 */

	public Map<String, Object> list(ActivityListVo activityVoReq) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 封装页面信息
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(activityVoReq.getPageNumber());
		pageVo.setPageSize(activityVoReq.getPageSize());
		// 封装请求参数
		ActivitiesVo activitiesVo = new ActivitiesVo();
		if (StringUtil.isNotBlank(activityVoReq.getClientId())) {
			activitiesVo.setClientId(activityVoReq.getClientId());
		}
		activitiesVo.setActivitiesName(activityVoReq.getActivitiesName());
		// 活动状态
		if(StringUtils.isNotBlank(activityVoReq.getStatus())){
			activitiesVo.setStatus(activityVoReq.getStatus());
		}
		
		ActivitiesPageVoRes activitiesPageVoRes = activitiesService.getActivitiesByPage(pageVo, activitiesVo);
		
		PageVo pageVoRes = activitiesPageVoRes.getPage();
		Page page = new Page();
		BeanUtils.copyProperties(page, pageVoRes);
		map.put("page", page);
		
		List<ActivitiesVo> activitiesVoList = activitiesPageVoRes.getActivitiesVoList();
		
		List<ActivityVoRes> list = null;
		if (null != activitiesVoList && activitiesVoList.size() > 0) {
			list = new ArrayList<ActivityVoRes>();
			// 获取客户端列表
			List<ClientVo> client = clientService.getClient(new ClientVo());
			for (ActivitiesVo activitiesVo2 : activitiesVoList) {
				ActivityVoRes activityVoRes = new ActivityVoRes();
				BeanUtils.copyProperties(activityVoRes, activitiesVo2);
				// 转化client中方名字
				for (ClientVo clientVo : client) {
					if (activitiesVo2.getClientId().equals(clientVo.getClientId())) {
						activityVoRes.setClientName(clientVo.getName());
						break;
					}
				}
				if(StringUtil.isNotEmpty(activitiesVo2.getCreateTime()+"")){
					activityVoRes.setCreateTime(DateUtil.longToString(activitiesVo2.getCreateTime()));
				}
				// 活动状态
				activityVoRes.setStatus(Integer.parseInt(activitiesVo2.getStatus()));
				//积分转化
				if(StringUtil.isNotEmpty(activitiesVo2.getPoints()+"")){
					
					activityVoRes.setPoints(String.format("%.2f", Arith.div((double)activitiesVo2.getPoints(), 1000)));
				}
				list.add(activityVoRes);
			}
			map.put("list", list);
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：删除活动</p> 
	 * <p>使用说明：根据主键id删除活动</p> 
	 * <p>创建时间：2015年5月16日下午4:28:02</p>
	 * <p>作者: 陈明灿</p>
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> deleteById(Long id,String clientId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
			// 删除活动之前,解绑所有商品
			ResultVo vo = productService.removeActivtyFromProducts(originVo,id, "");
			if (Constants.RESULT_SUCCESS.equals(vo.getResultCode())) {
				//商品解绑成功.....
				originVo.setPlatType(PlatType.boss);
				ActivitiesVo activitiesVo = new ActivitiesVo();
				activitiesVo.setId(id);
				ResultVo resultVo = activitiesService.deleteActivities(originVo, clientId,id);
				LogCvt.info("删除活动server返回数据", JSON.toJSONString(resultVo));
				if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
					map.put("code", Constants.RESULT_SUCCESS);
					map.put("message", resultVo.getResultDesc());
				} else {
					throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
				}
			} else {
				throw new BossException(vo.getResultCode(),  vo.getResultDesc());
			}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：查询活动详情</p> 
	 * <p>使用说明：根据id查询活动详情</p> 
	 * <p>创建时间：2015年5月16日下午5:12:49</p>
	 * <p>作者: 陈明灿</p>
	 * @param id
	 * @return
	 * @throws TException 
	 * @throws NumberFormatException 
	 */
	public Map<String, Object> queryById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ActivitiesVo activitiesVo = activitiesService.getActivitiesById(null, Long.parseLong(id));
		if (null != activitiesVo && activitiesVo.getId()>0) {
			List<ClientVo> client = clientService.getClient(new ClientVo());
			ActivityVoRes voRes = new ActivityVoRes();
			BeanUtils.copyProperties(voRes, activitiesVo);
			if(StringUtil.isNotEmpty(activitiesVo.getCreateTime()+"")){
				voRes.setCreateTime(DateUtil.longToString(activitiesVo.getCreateTime()));
			}
			// 转化client中方名字
			for (ClientVo clientVo : client) {
				if (voRes.getClientId().equals(clientVo.getClientId())) {
					voRes.setClientName(clientVo.getName());
					break;
				}
			}
			// 活动状态
			voRes.setStatus(Integer.parseInt(activitiesVo.getStatus()));
			
			//积分转化
			if(StringUtil.isNotEmpty(activitiesVo.getPoints()+"")){
				
				voRes.setPoints(String.format("%.2f", Arith.div((double)activitiesVo.getPoints(), 1000)));
			}
			map.put("activity", voRes);
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：新增或者修改活动</p> 
	 * <p>使用说明：如果有id时为修改活动,当无id时为新增活动</p> 
	 * <p>创建时间：2015年5月16日下午5:18:03</p>
	 * <p>作者: 陈明灿</p>
	 * @param activityVoReq
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> saveOrUpdate(ActivityVoReq activityVoReq) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.boss);
		ActivitiesVo activitiesVo = new ActivitiesVo();
		activitiesVo.setActivitiesName(activityVoReq.getActivitiesName());
		activitiesVo.setBeginTime(activityVoReq.getBeginTime());
		activitiesVo.setClientId(activityVoReq.getClientId());
		activitiesVo.setEndTime(activityVoReq.getEndTime());
		activitiesVo.setStatus(activityVoReq.getStatus()+"");
		String points = activityVoReq.getPoints();
		if(StringUtil.isNotEmpty(points)){
			activitiesVo.setPoints(Arith.mul(Double.parseDouble(points), 1000));
		}
		if (null == activityVoReq.getId()) {
			//绑定默认数量0
			activitiesVo.setCount(0);
			//无id则为新增活动操作
			AddResultVo resultVo = activitiesService.addActivities(originVo, activitiesVo);
			if (Constants.RESULT_SUCCESS.equals(resultVo.getResultVo().getResultCode())) {
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", resultVo.getResultVo().getResultDesc());
			} else {
				throw new BossException(resultVo.getResultVo().getResultCode(),  resultVo.getResultVo().getResultDesc());
			}
		} else {
			//有活动id则为修改活动,设置活动id
			activitiesVo.setId(activityVoReq.getId());
			ResultVo resultVo = activitiesService.updateActivities(originVo, activitiesVo);
			if (Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())) {
				map.put("code", Constants.RESULT_SUCCESS);
				map.put("message", resultVo.getResultDesc());
			} else {
				throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
			}
		}
		return map;
	}
	
	/**
	 * 
	 * <p>功能简述：作废活动</p> 
	 * <p>使用说明：作废活动</p> 
	 * <p>创建时间：2015年5月26日下午1:42:26</p>
	 * <p>作者: 陈明灿</p>
	 * @param activityVoReq
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> discard(ActivityVoReq activityVoReq) throws Exception {
		LogCvt.info("废弃活动请求参数:", JSON.toJSONString(activityVoReq));
		Map<String, Object> map = new HashMap<String, Object>();
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.boss);
		// 解绑活动下的所有商品
		ResultVo vo = productService.removeActivtyFromProducts(originVo,activityVoReq.getId(), null);
		if (!Constants.RESULT_SUCCESS.equals(vo.getResultCode())) {
			throw new BossException(vo.getResultCode(), vo.getResultDesc());
		}
		return map;
	}
	
}
