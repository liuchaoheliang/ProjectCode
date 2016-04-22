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
import com.froad.cbank.coremodule.module.normal.user.pojo.ReceiverDelPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ReceiverPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.vo.MerchantOutletFavoriteVORes;
import com.froad.thrift.vo.RecvInfoVo;
import com.froad.thrift.vo.ResultVo;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月2日 下午6:50:34
 * @version 1.0
 * @desc 收货地址支持类
 */
@Service
public class ReceiverSupport extends BaseSupport {

	@Resource
	private MerchantOutletFavoriteService.Iface merchantOutletFavoriteService;
	
	/**
	 * @desc 获取收货地址列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月8日 下午6:45:48
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getReceiverList(String clientId, Long memberCode, String isDefault, String type) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		List<RecvInfoVo> resp = null;
		List<ReceiverPojo> receiverList = new ArrayList<ReceiverPojo>();
		ReceiverPojo pojo = null;
		try {
			LogCvt.info("地址查询传入参数：clientId-"+clientId+",memberCode-"+memberCode+",isDefault-"+isDefault+",type-"+type);
			resp = merchantOutletFavoriteService.getRecvInfoVo(clientId, memberCode, isDefault, type);
			if(!ArrayUtil.empty(resp)) {
				for(RecvInfoVo tmp : resp) {
					pojo = new ReceiverPojo();
					BeanUtils.copyProperties(pojo, tmp);
					receiverList.add(pojo);
				}
			}
			resResult.put("receiverList", receiverList);
		} catch (TException e) {
			LogCvt.info("收货地址列表查询异常" + e.getMessage(), e);
			//resResult.put("code", "9999");
			//resResult.put("message", "收货地址列表查询异常");
		} 
		return resResult;
	}
	
	/**
	 * @desc 删除收货地址
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月8日 下午7:03:34
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> deleteReceiver(String clientId, Long memberCode, ReceiverDelPojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		try {
			if(!merchantOutletFavoriteService.deleteRecvInfoVo(clientId, memberCode, pojo.getRecvId())) {
				resResult.put("code", "9999");
				resResult.put("message", "删除收货地址失败");
			}
		} catch (TException e) {
			LogCvt.info("收货地址删除异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "收货地址删除异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 更新收货地址（新增 / 修改）
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月11日 上午10:16:37
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> updateReceiver(String clientId, Long memberCode, ReceiverPojo pojo) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		RecvInfoVo req = new RecvInfoVo();
		ResultVo resp = null;
		//封装请求对象
		if(!StringUtil.isEmpty(pojo.getRecvId())) {
			req.setRecvId(pojo.getRecvId());
		}
		req.setConsignee(pojo.getConsignee());
		req.setAddress(pojo.getAddress());
		req.setPhone(pojo.getPhone());
		req.setIsDefault(pojo.getIsDefault());
		if("1".equals(pojo.getType())){
			req.setAreaId(pojo.getAreaId() != null ? pojo.getAreaId() : 0);
		}
		req.setType(pojo.getType());
		req.setIsDeliverDefault(pojo.getIsDeliverDefault());
		
		try {
			if(!StringUtil.isEmpty(pojo.getRecvId())) {
				LogCvt.info(">>进入收货地址更新");
				MerchantOutletFavoriteVORes res =  merchantOutletFavoriteService.updateRecvInfoVo(clientId, memberCode, req);
				//封装更新返回结果
				if(StringUtil.equals(res.getResult().getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
					resResult.put("recvId", res.getId());
				}
				resResult.put("code", res.getResult().getResultCode());
				resResult.put("message", res.getResult().getResultDesc());				
			} else {
				LogCvt.info(">>进入收货地址新增");
				resp = merchantOutletFavoriteService.addRecvInfoVo(clientId, memberCode, req);
				//封装新增返回结果
				if(!StringUtil.equals(resp.getResultCode(), Constants.RESULT_CODE_SUCCESS)) {
					resResult.put("code", resp.getResultCode());
					resResult.put("message", resp.getResultDesc());
				}
			}
		} catch (TException e) {
			LogCvt.info("收货地址操作异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "收货地址操作异常");
		}
		return resResult;
	}
}
