package com.froad.fft.controller.shop.presell;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.bean.Result;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.ClusterState;
import com.froad.fft.support.base.impl.ProductPresellSupportImpl;
import com.froad.fft.support.base.impl.TransSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.PresellDynamicUtil;

@Controller
@RequestMapping("/shop/presell")
public class PresellController extends BaseController {

	@Resource
	private ProductPresellSupportImpl productPresellSupportImpl;
	
	@Resource
	private TransSupportImpl transSupportImpl;

	// **预售详情页面
	@RequestMapping(value = "details", method = RequestMethod.GET)
	public String presellDetails(String productId, ModelMap model,HttpServletRequest req) {
		if (NullValueCheckUtil.isStrEmpty(productId)) {
			return ILLEGALITY;
		}
		Long id = null;
		try {
			id = Long.parseLong(productId);
		} catch (Exception e) {
			logger.error("精品预售详情，传入数据非法 productId " + productId);
			model.put("illMsg", "抱歉，您查找的商品不存在");
			return ILLEGALITY;
		}
		ProductPresellDto productPresellDto = productPresellSupportImpl
				.findProductPresellById(id);

		// 条件查询该预售商品的基础商品信息
		ProductDto productDto = new ProductDto();
		productDto.setProductPresellId(id);
		List<ProductDto> productDtos = productPresellSupportImpl
				.getProductByCondition(productDto);

		if (NullValueCheckUtil.isListArrayEmpty(productDtos)) {
			logger.info("加载预售商品的基础商品信息失败， presellid" + productId);
			model.put("illMsg", "抱歉，该商品不是有效的预售商品");
			return ILLEGALITY;
		}
		productDto = productDtos.get(0);
		if (!productDto.getIsMarketable()) {
			model.put("illMsg", "抱歉，该商品已下架");
			return ILLEGALITY;
		}
		//0表示不限购
		Integer maxNum = productPresellDto.getPerNumber();//最大购买
		Integer minNum = productPresellDto.getPerminNumber();//最小购买
		//设置最低购买、限购数量（登录、未登录）
		Object loginObj = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);//获取用户登录信息
		if(loginObj!=null){
			UserEngineDto userEngineDto = (UserEngineDto)loginObj;
			//当前用户 当前最低购买数量、最大购买数量
			Integer number = transSupportImpl.getMemberBuyNum(userEngineDto.getMemberCode(), productDto.getId());
			if(maxNum!=0){
				maxNum = maxNum-number;
				if(maxNum<=0){//小于等于0，已达到限购数量
					model.put("cantBuy", true);//不能购买的标识
				}
			}
			if(minNum!=0){
				minNum = minNum-number;
				if(minNum<=0){//小于等于0，已达到最低购买。
					minNum= 1;
				}
			}else{
				minNum= 1;
			}
		}else{
			//用户没登陆，默认最低购买1
			minNum = minNum==0?1:minNum;
		}
		model.put("maxNum", maxNum);
		model.put("minNum", minNum);
		model.put("productPresellDto", productPresellDto);
		model.put("productDto", productDto);
		model.put("presellState", getPresellState(productPresellDto));// 预售状态
		Integer buyNum = NullValueCheckUtil.initNullInt(
				productPresellDto.getTrueBuyerNumber())+NullValueCheckUtil.initNullInt(productPresellDto.getVirtualBuyerNumber());
		model.put("presellDynamics", PresellDynamicUtil.getPresellDynamic(productPresellDto.getStartTime(),productPresellDto.getEndTime(),buyNum));
		return "shop/presell/details";
	}

	/**
	 * <p>
	 * 预售当前状态
	 * </p>
	 * 
	 * @author larry
	 * @datetime 2014-4-16下午03:51:19
	 * @return Integer
	 * @throws
	 * @example<br/>
	 * 
	 */
	private Integer getPresellState(ProductPresellDto productPresellDto) {
		Integer state = null;
		Date now = new Date();// 当前时间
		ClusterState clusterState = productPresellDto.getClusterState();// 成团状态
		Date startTime = productPresellDto.getStartTime();// 预售开始时间
		Date endTime = productPresellDto.getEndTime();// 预售结束时间
		Date deliveryStartTime = productPresellDto.getDeliveryStartTime();// 提货开始时间
		Date deliveryEndTime = productPresellDto.getDeliveryEndTime();// 提货结束时间
		
		if (!now.after(startTime)) {
			state = 0;// 预售未开始
		} else if (now.after(startTime) && !now.after(endTime)) {
			state = 1;// 预售期内
		} else if (now.after(endTime) && !now.after(deliveryStartTime)
				&& clusterState == ClusterState.success) {
			state = 2;// 备货期
		} else if (now.after(deliveryStartTime) && !now.after(deliveryEndTime)
				&& clusterState == ClusterState.success) {
			state = 3;// 提货期
		} else if (now.after(deliveryEndTime)
				&& clusterState == ClusterState.success) {
			state = 4;// 提货已完成
		} else if (clusterState != ClusterState.success) {
			state = 5;// 未成团
		}
		return state;
	}
}
