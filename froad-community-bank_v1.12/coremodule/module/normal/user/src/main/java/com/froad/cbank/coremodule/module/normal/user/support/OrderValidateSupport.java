package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.module.normal.user.enums.DeliveryType;
import com.froad.cbank.coremodule.module.normal.user.pojo.AddProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.GenerateOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductVo;

@Service
public class OrderValidateSupport {
	
	@Resource
	private ProductSupport productSupport;

	
	/**
	 * 校验预售订单数据
	 * @param order
	 * @param product
	 * @param clientId
	 * @return
	 */
	public ResultBean validatePresell(GenerateOrderPojo order, AddProductPojo product, String clientId){
		ResultBean result = new ResultBean();
		//预售商品校验VIP数量
		if((product.getQuantity() == null || product.getQuantity() == 0) && (product.getVipQuantity() == null || product.getVipQuantity() == 0)){
			result.setSuccess(false);
			result.setMsg("商品数量为空");
			return result;
		}
		if(StringUtil.isBlank(product.getDeliveryType())){
			result.setSuccess(false);
			result.setMsg("配送类型为空");
			return result;
		}
		if(DeliveryType.home.getCode().equals(product.getDeliveryType())){
			if(StringUtil.isBlank(order.getRecvId())){
				result.setSuccess(false);
				result.setMsg("送货上门，收货人信息为空");
				return result;
			}
		}else if(DeliveryType.take.getCode().equals(product.getDeliveryType())){
			if(StringUtil.isBlank(order.getPhone())){
				result.setSuccess(false);
				result.setMsg("手机号为空");
				return result;
			}
			if(StringUtil.isBlank(order.getRecvId())){
				result.setSuccess(false);
				result.setMsg("网点自提，提货人信息为空");
				return result;
			}
			if(StringUtil.isBlank(product.getOrgCode())){
				result.setSuccess(false);
				result.setMsg("网点自提，网点编号为空");
				return result;
			}
			if(StringUtil.isBlank(product.getOrgName())){
				result.setSuccess(false);
				result.setMsg("网点自提，网点名称为空");
				return result;
			}
		}else{
			result.setSuccess(false);
			result.setMsg("无法识别的配送类型");
			return result;
		}
		
		//校验商品信息
		ProductInfoVo productInfoVo = productSupport.getMerchantProductDetail(clientId, product.getProductId(), product.getMerchantId());
		if(productInfoVo != null && productInfoVo.getProduct() != null){
			ProductVo productVo = productInfoVo.getProduct();
			Date now = new Date();
			if(productVo.isSetStartTime() || productVo.isSetEndTime()){
				if(now.before(new Date(productVo.getStartTime()))){
					result.setSuccess(false);
					result.setMsg("["+productVo.getName()+"]预售活动尚未开始");
					return result;
				}
				if(now.after(new Date(productVo.getEndTime()))){
					result.setSuccess(false);
					result.setMsg("["+productVo.getName()+"]预售活动已结束");
					return result;
				}
			}
		}else{
			result.setSuccess(false);
			result.setMsg("查询商品信息失败：["+product.getProductId()+"]");
			return result;
		}
		
		result.setSuccess(true);
		return result;
	}
	
	
	
	/**
	 * 校验积分兑换订单数据
	 * @param order
	 * @param product
	 * @return
	 */
	public ResultBean validatePointExchange(GenerateOrderPojo order, AddProductPojo product){
		ResultBean result = new ResultBean();
		if(product.getQuantity() == null || product.getQuantity() == 0){
			result.setSuccess(false);
			result.setMsg("商品数量为空");
			return result;
		}
		if(StringUtil.isBlank(order.getPhone())){
			result.setSuccess(false);
			result.setMsg("手机号为空");
			return result;
		}
		if(StringUtil.isBlank(order.getRecvId())){
			result.setSuccess(false);
			result.setMsg("收货人信息为空");
			return result;
		}
		
		result.setSuccess(true);
		return result;
	}
	
	
	
	/**
	 * 校验名优特惠订单数据
	 * @param order
	 * @param product
	 * @return
	 */
	public ResultBean validateFamousBand(GenerateOrderPojo order, AddProductPojo product){
		ResultBean result = new ResultBean();
		if(product.getQuantity() == null || product.getQuantity() == 0){
			result.setSuccess(false);
			result.setMsg("商品数量为空");
			return result;
		}
		if(StringUtil.isBlank(order.getPhone())){
			result.setSuccess(false);
			result.setMsg("手机号为空");
			return result;
		}
		if(StringUtil.isBlank(order.getRecvId())){
			result.setSuccess(false);
			result.setMsg("收货人信息为空");
			return result;
		}
		
		result.setSuccess(true);
		return result;
	}
	
	
	
	/**
	 * 校验团购订单数据
	 * @param order
	 * @param product
	 * @param clientId
	 * @return
	 */
	public ResultBean validateGroup(GenerateOrderPojo order, AddProductPojo product, String clientId){
		ResultBean result = new ResultBean();
		if(product.getQuantity() == null || product.getQuantity() == 0){
			result.setSuccess(false);
			result.setMsg("商品数量为空");
			return result;
		}
		if(StringUtil.isBlank(order.getPhone())){
			result.setSuccess(false);
			result.setMsg("手机号为空");
			return result;
		}
		if(StringUtil.isBlank(order.getRecvId())){
			result.setSuccess(false);
			result.setMsg("收货人信息为空");
			return result;
		}
		
		//校验商品信息
		ProductInfoVo productInfoVo = productSupport.getMerchantProductDetail(clientId, product.getProductId(), product.getMerchantId());
		if(productInfoVo != null && productInfoVo.getProduct() != null){
			ProductVo productVo = productInfoVo.getProduct();
			Date now = new Date();
			if(productVo.isSetStartTime() || productVo.isSetEndTime()){
				if(now.before(new Date(productVo.getStartTime()))){
					result.setSuccess(false);
					result.setMsg("["+productVo.getName()+"]团购活动尚未开始");
					return result;
				}
				if(now.after(new Date(productVo.getEndTime()))){
					result.setSuccess(false);
					result.setMsg("["+productVo.getName()+"]团购活动已结束");
					return result;
				}
			}
		}else{
			result.setSuccess(false);
			result.setMsg("查询商品信息失败：["+product.getProductId()+"]");
			return result;
		}

		result.setSuccess(true);
		return result;
	}
	
	/**
	 * 校验精品商城商品订单数据
	 * @param order
	 * @param product
	 * @return
	 */
	public ResultBean validateBoutiqueExchange(GenerateOrderPojo order, AddProductPojo product){
		ResultBean result = new ResultBean();
		if(product.getQuantity() == null || product.getQuantity() == 0){
			result.setSuccess(false);
			result.setMsg("商品数量为空");
			return result;
		}
		if(DeliveryType.home.getCode().equals(product.getDeliveryType())){
			if(StringUtil.isBlank(order.getRecvId())){
				result.setSuccess(false);
				result.setMsg("配送，收货人信息为空");
				return result;
			}
		}
		
		result.setSuccess(true);
		return result;
	}
}
