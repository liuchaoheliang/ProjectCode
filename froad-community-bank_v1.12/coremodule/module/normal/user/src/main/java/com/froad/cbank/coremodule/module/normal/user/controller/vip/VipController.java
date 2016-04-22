package com.froad.cbank.coremodule.module.normal.user.controller.vip;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.enums.ResultCode;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.enums.ClientChannelEnum;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.GenerateVipOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrgPojo;
import com.froad.cbank.coremodule.module.normal.user.support.AreaSupport;
import com.froad.cbank.coremodule.module.normal.user.support.OrgSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.support.VipSupport;
import com.froad.thrift.vo.VipProductVo;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPDto;
import com.pay.user.helper.ClientChannel;
import com.pay.user.helper.VIPLevel;
import com.pay.user.helper.VIPStatus;

/**
 * VIP功能控制器
 */
@Controller
public class VipController extends BasicSpringController{

	
	@Resource
	private OrgSupport orgSupport;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private AreaSupport areaSupport;
	
	
	/**
	 * 获取VIP功能介绍
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/vip/info", method = RequestMethod.GET)
	public void getBankVipInfo(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode){
		String clientId = getClient(request);
		
		//客户端所属银行已开通VIP功能
		VipProductVo product = vipSupport.getBankVipInfo(clientId);
		if(product != null && "1".equals(product.getStatus())){
			model.put("open", true);
			model.put("vipId", product.getVipId());
			model.put("price", product.getVipPrice());
			model.put("actName", product.getActivitiesName());
			model.put("introduce", product.getRemark());
			
			String bankType = vipSupport.queryClientOrgType(clientId);
			model.put("clientBankType", bankType);
			
			//退款后再次购买VIP会员时，将告知用户无法再申请退款
			Boolean isRefundWarn = vipSupport.isRefundWarn(memberCode);
			model.put("isRefundWarn", isRefundWarn);
			
		}else{
			model.put("open", false);
			model.put("result", null);
		}
	}
	
	
	/**
	 * 根据clientId获取有下属二级机构的行政区域列表
	 * @param request
	 * @param model
	 * @param areaId
	 */
	@RequestMapping(value = "/vip/getArea", method = RequestMethod.GET)
	public void getArea(HttpServletRequest request, ModelMap model, Long areaId){
		String clientId = getClient(request);
		
		//查询行政区域
		List<AreaPojo> arealist = areaSupport.getOrgAreaByClient(clientId);
		model.put("areaList",arealist);
	}
	

	
	/**
	 * 获取市级行政区域下的机构列表
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/vip/getAreaOrg", method = RequestMethod.GET)
	public void getAreaOrg(HttpServletRequest request, ModelMap model, Long areaId){
		String clientId = getClient(request);
		
		//查询市级行政区域下的机构信息
		List<OrgPojo> orgList = orgSupport.getCityAreaOrg(clientId, areaId);
		model.put("orgList", orgList);
	}
	
	
	/**
	 * @author Administrator
	 * @param request
	 * @param model
	 * @param memberCode
	 * @param orderVo
	 */
	@Token
	@RequestMapping(value = "/vip/addOrder", method = RequestMethod.POST)
	public void createVipOrder(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, @RequestBody GenerateVipOrderPojo orderVo){
		String clientId = getClient(request);
		String clientBankType = vipSupport.queryClientOrgType(clientId);
		
		ClientChannel clientChannel = ClientChannelEnum.getClientChannel(request);
		if(clientChannel == null){
			LogCvt.error("客户端渠道获取失败! http referer : " + request.getHeader(HttpHeaders.REFERER));
			model.put(Results.code, "9999");
			model.put(Results.msg, "客户端渠道错误");
			return;
		}
		//查询会员信息
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			model.put(Results.code, "9999");
			model.put(Results.msg, "查询用户信息失败");
			return;
		}
		if(StringUtil.isBlank(user.getMobile())){
			model.put(Results.code, "9999");
			model.put(Results.msg, "请先绑定手机号");
			return;
		}
		
		//ClientBankType = 0； 单级机构模式
		if("0".equals(clientBankType)){
			
			List<OrgPojo> orgList = orgSupport.getOrg(clientId, 1);
			if(orgList.size() > 0){
				//将此单级机构的orgCode作为参数
				orderVo.setOrgCode(orgList.get(0).getOrgCode());
			}else{
				model.put(Results.code, "9999");
				model.put(Results.msg, "银行机构信息为空");
				return;
			}
			
			AreaPojo cityArea = vipSupport.getCityAreaByClientId(clientId);
			if(cityArea != null){
				orderVo.setAreaId(cityArea.getId());
				orderVo.setAreaName(cityArea.getName());
			}else{
				model.put(Results.code, "9999");
				model.put(Results.msg, "地区数据异常");
				return;
			}
		}else if(StringUtil.isBlank(orderVo.getOrgCode())){
			model.put(Results.code, "9999");
			model.put(Results.msg, "银行机构信息为空");
			return;
		}
		
		LogCvt.info(String.format("用户开通VIP资格,创建订单>>	LoginID:%s	MemberCode:%s", user.getLoginID(), user.getMemberCode()));
		LogCvt.info(String.format("创建VIP订单信息>> %s",JSONObject.toJSONString(orderVo)));
				
		model.putAll(vipSupport.createVIPOrder(memberCode, user.getLoginID(), user.getMobile(), clientId,user.getVip(), clientChannel.getValue(), orderVo));
		
	}
	
	
	
	
	


	/**
	 * 我的VIP
	 * @param request
	 * @param model
	 * @param memberCode
	 */
	@Token
	@RequestMapping(value = "/vip/my", method = RequestMethod.GET)
	public void getMemberVipInfo(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, String areaId){
		String clientId = getClient(request);
		boolean isVip = false;
		boolean isCanRefund = false;
		double discount = 0;
		
		Map<String, Object> vipMap = vipSupport.getMemberVipInfomation(memberCode,clientId);
		isVip = Boolean.parseBoolean(vipMap.get("isVip").toString());
		if(isVip){
			VipProductVo product = vipSupport.getBankVipInfo(clientId);
			discount = vipSupport.getVipDiscount(clientId, memberCode);
			if(product != null ){
				model.put("vipId", product.getVipId());
				model.put("price", product.getVipPrice());
			}
			model.put("discount", discount);
			model.putAll(vipMap);
//			model.putAll(vipSupport.getVIPPresellProduct(clientId, areaId));
			model.putAll(vipSupport.getRecommendProductList(clientId));
			
			//获取当前用户是否可以VIP退款，可以返回true，不行返回false
			String bankOrgNo = vipMap.get("bankOrgNo").toString();
			isCanRefund = vipSupport.isCanRefund(memberCode, bankOrgNo);
			model.put("isCanRefund", isCanRefund);
			
		}else{
			model.put(Results.code, "9999");
			model.put(Results.msg, "用户不是VIP会员");
		}
	}
	
	/**
	 * VIP续费
	 * @author yfy 
	 * @date 2015年12月1日 下午1:23:11
	 * @param request
	 * @param model
	 * @param memberCode
	 * @param orderVo
	 */
	@Token
	@RequestMapping(value = "/vip/renewOrder", method = RequestMethod.POST)
	public void getMemberVipRenewOrder(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, @RequestBody GenerateVipOrderPojo orderVo) {
		String clientId = getClient(request);
		String clientBankType = vipSupport.queryClientOrgType(clientId);
		
		ClientChannel clientChannel = ClientChannelEnum.getClientChannel(request);
		if(clientChannel == null){
			LogCvt.error("客户端渠道获取失败! http referer : " + request.getHeader(HttpHeaders.REFERER));
			model.put(Results.code, ResultCode.failed.getCode());
			model.put(Results.msg, "客户端渠道错误");
			return;
		}
		//查询会员信息
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			model.put(Results.code, ResultCode.failed.getCode());
			model.put(Results.msg, "查询用户信息失败");
			return;
		}
		if(StringUtil.isBlank(user.getMobile())){
			model.put(Results.code, "9999");
			model.put(Results.msg, "请先绑定手机号");
			return;
		}
		boolean isVip = false;
		Map<String, Object> vipMap = vipSupport.getMemberVipInfomation(memberCode,clientId);
		String orgCode = vipMap.get("orgCode").toString();
		isVip = Boolean.parseBoolean(vipMap.get("isVip").toString());
		if(isVip){
			//校验续费VIP资格最长期限可支持10自然年
			Boolean falg = vipSupport.renewalsCheck(memberCode, clientId);
			if(!falg){
				model.put(Results.code, ResultCode.failed.getCode());
				model.put(Results.msg, "VIP资格最长期限可支持10自然年");
				return;
			}
			String vipLevel = vipMap.get("vipLevel").toString();
			String vipStatus = vipMap.get("vipStatus").toString();
			VIPDto vipDto = new VIPDto();
			vipDto.setVipLevel(VIPLevel.getInstanceByValue(vipLevel));
			vipDto.setVipStatus(VIPStatus.getInstanceByValue(vipStatus));
			user.setVip(vipDto);
		}
		//ClientBankType = 0； 单级机构模式
		if("0".equals(clientBankType)){
			
			List<OrgPojo> orgList = orgSupport.getOrg(clientId, 1);
			if(orgList.size() > 0){
				//将此单级机构的orgCode作为参数
				orderVo.setOrgCode(orgList.get(0).getOrgCode());
			}else{
				model.put(Results.code, ResultCode.failed.getCode());
				model.put(Results.msg, "银行机构信息为空");
				return;
			}
			
			AreaPojo cityArea = vipSupport.getCityAreaByClientId(clientId);
			if(cityArea != null){
				orderVo.setAreaId(cityArea.getId());
				orderVo.setAreaName(cityArea.getName());
			}else{
				model.put(Results.code, ResultCode.failed.getCode());
				model.put(Results.msg, "地区数据异常");
				return;
			}
		}else {
			if(StringUtil.isBlank(orgCode)){
				model.put(Results.code, ResultCode.failed.getCode());
				model.put(Results.msg, "银行机构信息为空");
				return;
			}
			orderVo.setOrgCode(orgCode);
		}
		
		
		LogCvt.info(String.format("用户续费VIP资格,创建订单>>	LoginID:%s	MemberCode:%s", user.getLoginID(), user.getMemberCode()));
		LogCvt.info(String.format("创建VIP订单信息>> %s",JSONObject.toJSONString(orderVo)));
				
		model.putAll(vipSupport.createVIPOrder(memberCode, user.getLoginID(), user.getMobile(), clientId,user.getVip(), clientChannel.getValue(), orderVo));
		
	}
	
}
