package com.froad.cbank.coremodule.module.normal.user.controller.refund;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.RefundInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.RefundProduct;
import com.froad.cbank.coremodule.module.normal.user.support.RefundSupport;
import com.froad.cbank.coremodule.module.normal.user.support.VipSupport;
import com.froad.thrift.vo.refund.RefundProductVo;

/**
 * 类描述：退款
	 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年3月23日 下午5:00:58 
 */

@Controller
@RequestMapping
public class RefundController extends BasicSpringController{
	
	@Resource
	private RefundSupport refundSupport;
	
	@Resource
	private VipSupport vipSupport;
	
	
	/**
	  * 方法描述：退款接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 下午3:48:01
	  */
	@Token
	@RequestMapping(value="/refund/do",method=RequestMethod.POST)
	public void doRefund(ModelMap model,HttpServletRequest req,@RequestBody RefundInfoPojo infoPojo){
		Long startTime = System.currentTimeMillis();
		String clientId = getClient(req);
		
		if(infoPojo != null && !ArrayUtil.empty(infoPojo.getProductList()) && !StringUtil.empty(clientId) ){
			List<RefundProductVo> productVos=new ArrayList<RefundProductVo>();
			
			//转换实体为Vo对象
			RefundProductVo productVo=null;
			for(RefundProduct temp : infoPojo.getProductList()){
				productVo=new RefundProductVo();
				productVo.setProductId(temp.getProductId());
				productVo.setQuantity(temp.getQuantity());
				productVos.add(productVo);
			}
				
 			model.putAll(refundSupport.doOrderRefund(clientId,infoPojo.getSubOrderId(),infoPojo.getOption(),infoPojo.getReason(),productVos));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
		Monitor.send(MonitorEnums.user_do_refund, String.valueOf(System.currentTimeMillis() - startTime));
	}
	
	
	
	
	/**
	  * 方法描述：获取退款详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 下午3:48:50
	  */
	@Token
	@RequestMapping(value="/refund/details",method=RequestMethod.GET)
	public void details(ModelMap model,HttpServletRequest req,String refundId){
		String clientId=getClient(req);
		if(!StringUtil.empty(clientId) && !StringUtil.empty(refundId)){
			model.putAll(refundSupport.getRefundDetails(clientId,refundId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
	
	/**
	  * 方法描述：获取用户退款列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月24日 下午3:50:23
	  */
	@Token
	@RequestMapping(value="/refund/list",method=RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest req,@RequestHeader String  memberCode,PagePojo pagePojo ){
		model.clear();
		String clientId=getClient(req);
		String timeType=req.getParameter("timeType");
		String status=req.getParameter("status");
		
		model.putAll(refundSupport.getRefundListByMember(clientId,memberCode,timeType,status,pagePojo));
	}
	
	/**
	 * 方法描述：vip退款接口
	 * @author: yfy
	 * @time: 2015年12月2日 下午13:23:08
	 * @param model
	 * @param req
	 * @param orderId
	 */
	@Token
	@RequestMapping(value="/refund/vip/do",method=RequestMethod.GET)
	public void doOrderRefundVip(ModelMap model,HttpServletRequest req,String orderId,@RequestHeader Long memberCode){
		Long startTime = System.currentTimeMillis();	
		model.clear();
		String clientId=getClient(req);
		//如果是订单号为空，则调接口查询订单号
		if(StringUtil.isBlank(orderId)){
			orderId = vipSupport.getVipOrderNo(memberCode,clientId);
		}
		if(StringUtil.isNotBlank(orderId)){
			model.putAll(refundSupport.doOrderRefundVip(orderId,clientId,memberCode));
		}else{
			model.put("code", "9999");
			model.put("message", "无效会员资格订单");
		}
		Monitor.send(MonitorEnums.user_do_refund, String.valueOf(System.currentTimeMillis() - startTime));
	}
	
}
