package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliveryDelPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.DeliveryPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.vo.DeliverInfoVo;
import com.froad.thrift.vo.ResultVo;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月24日 下午6:11:42
 * @version 1.0
 * @desc 
 */
@Service
public class DeliverySupport extends BaseSupport {

	@Resource
	private MerchantOutletFavoriteService.Iface merchantOutletFavoriteService;
	
	/**
	 * @desc 获取提货信息列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午7:28:37
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getDeliveryList(String clientId, Long memberCode, String isDefault) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		List<DeliverInfoVo> resp = null;
		List<DeliveryPojo> deliveryList = new ArrayList<DeliveryPojo>();
		DeliveryPojo pojo = null;
		try {
			resp = merchantOutletFavoriteService.getDeliverInfoVo(clientId, memberCode, isDefault);
			if(!ArrayUtil.empty(resp)) {
				for(DeliverInfoVo tmp : resp) {
					pojo = new DeliveryPojo();
					BeanUtils.copyProperties(pojo, tmp);
					deliveryList.add(pojo);
				}
			}
			resResult.put("deliveryList", deliveryList);
		} catch (TException e) {
			LogCvt.info("提货列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "提货列表查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 删除提货人信息
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午7:31:32
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> deleteDelivery(String clientId, Long memberCode, DeliveryDelPojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		try {
			if(!merchantOutletFavoriteService.deleteDeliverInfoVo(clientId, memberCode, pojo.getDeliveryId())) {
				resResult.put("code", "9999");
				resResult.put("message", "提货信息删除失败");
			}
		} catch (TException e) {
			LogCvt.info("提货人信息删除异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "提货人信息删除异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 更新提货人信息（新增提货人信息）
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月24日 下午7:31:40
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> updateDelivery(String clientId, Long memberCode, DeliveryPojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		DeliverInfoVo req = new DeliverInfoVo();
		ResultVo resp = null;
		//封装请求对象
		if(!StringUtil.isEmpty(pojo.getDeliveryId())) {
			req.setDeliveryId(pojo.getDeliveryId());
		}
		req.setConsignee(pojo.getConsignee());
		if(pojo.getAreaId() != null) {
			req.setAreaId(pojo.getAreaId());
		}
		req.setPhone(pojo.getPhone());
		req.setIsDefault(pojo.getIsDefault());
		try {
			if(!StringUtil.isEmpty(pojo.getDeliveryId())) {
				LogCvt.info(">>进入提货信息更新");
				resp = merchantOutletFavoriteService.updateDeliverInfoVo(clientId, memberCode, req);
				//封装更新返回结果
				if(!StringUtil.equals(resp.getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
					resResult.put("code", resp.getResultCode());
					resResult.put("message", resp.getResultDesc());
				}
			} else {
				LogCvt.info(">>进入提货信息新增");
				resp = merchantOutletFavoriteService.addDeliverInfoVo(clientId, memberCode, req);
				//封装新增返回结果
				if(!StringUtil.equals(resp.getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
					resResult.put("code", resp.getResultCode());
					resResult.put("message", resp.getResultDesc());
				}
			}
		} catch (TException e) {
			LogCvt.info("提货信息操作异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "提货信息操作异常");
		}
		return resResult;
	}
}
