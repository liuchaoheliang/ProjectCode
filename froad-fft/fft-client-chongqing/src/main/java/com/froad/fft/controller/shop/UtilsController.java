package com.froad.fft.controller.shop;

import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.enums.ClusterState;
import com.froad.fft.support.base.ProductPresellSupport;
import com.froad.fft.util.NullValueCheckUtil;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: AjaxLoginValiController.java </p>
 *<p> 描述: *-- <b>常用工具类请求</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月27日 下午2:04:26 </p>
 ********************************************************
 */
@Controller
@RequestMapping("/shop/utils")
public class UtilsController extends BaseController {

	@Resource
	ProductPresellSupport productPresellSupport;
	
	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "check_username", method = RequestMethod.GET)
	public @ResponseBody Boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		
		//操作
		
		return true;
	}
	
	/**
	 * 检查E-mail是否存在
	 */
	@RequestMapping(value = "check_email", method = RequestMethod.GET)
	public @ResponseBody Boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		
		//操作
		
		return true;
	}
	
	/**
	 * 检查手机号码是否存在
	 */
	@RequestMapping(value = "check_mobile", method = RequestMethod.GET)
	public @ResponseBody Boolean checkMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		
		//操作
		
		return true;
	}
	
	/**
	*<p>获得预售情况，已预订:还差</p>
	* @author larry
	* @datetime 2014-4-16上午09:55:42
	* @return AjaxCallBackBean
	* @throws 
	* @example<br/>
	*
	 */
	@RequestMapping(value = "get_presell_info", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean getpresellInfo(String productId){
		String presellInfo = "0:0";
		ProductPresellDto productPresellDto = null;
		if(StringUtils.isNotBlank(productId)){
			productPresellDto = productPresellSupport.findProductPresellById(Long.valueOf(productId));
			if(productPresellDto != null){
				Integer clusteringNumber = NullValueCheckUtil.initNullInt(productPresellDto.getClusteringNumber());//成功成团数
				Integer trueBuyerNumber = NullValueCheckUtil.initNullInt(productPresellDto.getTrueBuyerNumber());//真实购买
				Integer virtualBuyerNumber = NullValueCheckUtil.initNullInt(productPresellDto.getVirtualBuyerNumber());//虚拟添加
				Integer buyerNumber= (trueBuyerNumber+virtualBuyerNumber);//已购买
				presellInfo = buyerNumber+":"+(clusteringNumber-(buyerNumber));
			}
		}
		return new AjaxCallBackBean(true,presellInfo);
	}
	
	
	@RequestMapping(value = "get_time_down", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean getTimeDown(String productId,String type){
		Date now = new Date();
		String endTime = "00:00:00:00";
		ProductPresellDto productPresellDto = null;
		if("prsell".equals(type)){//presell start
			//该预售商品ID用于查询预售时间
			if(StringUtils.isNotBlank(productId)){
				productPresellDto = productPresellSupport.findProductPresellById(Long.valueOf(productId));
				if(productPresellDto == null){
					return new AjaxCallBackBean(false,"预售商品ID不正确");
				}else{
					 if(!now.after(productPresellDto.getStartTime())){
						endTime = setRemainingTime(productPresellDto.getStartTime());//开始倒计时（预售未开始）
						endTime+=":0";
					 }else if(now.after(productPresellDto.getStartTime())&&!now.after(productPresellDto.getEndTime())){//预售期内
						endTime = setRemainingTime(productPresellDto.getEndTime());//计算预售剩余时间
						endTime+=":1";
					}else if(now.after(productPresellDto.getEndTime())){//预售期已过
						endTime = setRemainingTime(productPresellDto.getEndTime());//计算预售剩余时间
						endTime+=":2";
					}
					//当前时间小于提货开始时间，提货未开始
					if(!now.after(productPresellDto.getDeliveryStartTime())&&productPresellDto.getClusterState()==ClusterState.success){
						endTime+=":0";
						//当前时间大于提货开始时间小于提货结束时间，已提货开始
					}else if(now.after(productPresellDto.getDeliveryStartTime())&&!now.after(productPresellDto.getDeliveryEndTime())&&productPresellDto.getClusterState()==ClusterState.success){
						endTime+=":1";
						//当前时间大于提货结束时间，提货已结束
					}else if(now.after(productPresellDto.getDeliveryEndTime())&&productPresellDto.getClusterState()==ClusterState.success){
						endTime+=":2";
					}else{
						endTime+=":3";//未成团
					}
				}
			}else{
				return new AjaxCallBackBean(false,"预售商品ID为空");
			}//presell end
		}else if("group".equals(type)){
			//团购......
		}else{
			//非法
			return new AjaxCallBackBean(false,"参数非法");
		}
		return new AjaxCallBackBean(true, endTime);
	}
	
	/**
	*<p>设置剩余时间</p>
	* @author larry
	* @datetime 2014-4-9下午04:26:07
	* @return String
	* @throws 
	* @example<br/>
	*  dd:HH:mm:ss
	 */
	private String setRemainingTime(Date endTime){
		Assert.notNull(endTime, "时间为空无法计算");
		StringBuilder time= new StringBuilder();
		Long shortTime = endTime.getTime() - new Date().getTime();//剩余时间
		Long day=shortTime/(24*60*60*1000);
		Long hour=(shortTime/(60*60*1000)-day*24);
		Long minute=((shortTime/(60*1000))-day*24*60-hour*60);
		Long second=(shortTime/1000-day*24*60*60-hour*60*60-minute*60);
		time.append(day).append(":").append(hour).append(":").append(minute).append(":").append(second);//拼接前台js可识别的格式
		return time.toString();
	}
}
