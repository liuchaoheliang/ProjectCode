package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ShoppingCartLogic;
import com.froad.logic.impl.ShoppingCartLogicImpl;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.po.shoppingcart.mongo.ShoppingCart;
import com.froad.po.shoppingcart.req.ShoppingCartReq;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ShoppingCartService;
import com.froad.thrift.service.ShoppingCartService.Iface;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.shopingcart.ShoppingCartVoReq;
import com.froad.thrift.vo.shopingcart.ShoppingCartVoRes;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;


/**
 *  购物车实现类
  * @ClassName: ShoppingCartServiceImpl
  * @author share 2015年3月17日
  * @modify share 2015年3月17日
 */
public class ShoppingCartServiceImpl extends BizMonitorBaseService implements ShoppingCartService.Iface{
	public ShoppingCartServiceImpl() {}
	public ShoppingCartServiceImpl(String name, String version) {
		super(name, version);
	}
	/**
	 *  购物车业务逻辑
	 */
	private ShoppingCartLogic shoppingCartLogic = new ShoppingCartLogicImpl();
	
	/**
	 *  拉取单个商品的购物车信息
	  * @Title: getCartByProductId
	  * @author: share 2015年3月17日
	  * @modify: share 2015年3月17日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#getCartByProductId(long, long, long, long)
	 */
	@Override
	public ShoppingCartVoRes getCartByProductId(long memberCode, String clientId,String merchantId, String productId,int vipLevel) throws TException {
		long start = System.currentTimeMillis();
		if(memberCode <= 0 || clientId == null || "".equals(clientId) || StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(productId)){
			ShoppingCartVoRes res = new ShoppingCartVoRes();
			res.setErrCode(ResultCode.failed.getCode());
			res.setErrMsg("参数信息不能为空");
			return res;
		}
		ShoppingCart res = shoppingCartLogic.getCartByProductId(memberCode, clientId,merchantId,productId,vipLevel);
		LogCvt.info("拉取单个购物车耗时："+(System.currentTimeMillis() - start)+"，响应结果："+JSON.toJSONString(res,true));
		return convertToVo(res);
	}

	/**
	 *  拉取购物信息
	  * @Title: getCart
	  * @author: share 2015年3月17日
	  * @modify: share 2015年3月17日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#getCart(long, long)
	 */
	@Override
	public List<ShoppingCartVoRes> getCart(long memberCode, String clientId,int vipLevel)throws TException {
		long start = System.currentTimeMillis();
		List<ShoppingCart> resList = shoppingCartLogic.getCart(memberCode, clientId, vipLevel);
		LogCvt.info("拉取购物车耗时："+(System.currentTimeMillis() - start));
		return convert(resList);
	}

	/**
	 *  清空购物车
	  * @Title: deleteCart
	  * @author: share 2015年3月17日
	  * @modify: share 2015年3月17日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#deleteCart(long, long)
	 */
	@Override
	public boolean deleteCart(long memberCode, String clientId) throws TException {
		if(memberCode <= 0 || clientId == null || "".equals(clientId)){
			return false;
		}
		return shoppingCartLogic.deleteCart(memberCode, clientId);
	}

	/**
	 *  删除购物车单个商品
	  * @Title: deleteCartByProductId
	  * @author: share 2015年3月17日
	  * @modify: share 2015年3月17日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @param productId
	  * @param @param num
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#deleteCartByProductId(long, long, long, int)
	 */
	@Override
	public ResultVo updateCartByProductNum(ShoppingCartVoReq cartReq) throws TException {
		ResultVo result = new ResultVo();
		
		long memberCode = cartReq.getMemberCode();
		String clientId = cartReq.getClientId();
		String merchantId = cartReq.getMerchantId();
		String productId = cartReq.getProductId();
		int num = cartReq.getNum();
		int vipLevel = cartReq.getVipLevel();
		
		if(memberCode <= 0 || clientId == null || "".equals(clientId) || StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(productId) || num <= 0){
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("添加购物车信息不能为空");
			return result;
		}
		ResultBean bean = shoppingCartLogic.updateCartByProductNum(memberCode, clientId, merchantId, productId, num,vipLevel);
		result.setResultCode(bean.getCode());
		result.setResultDesc(bean.getMsg());
		
		return result;
	}

	/**
	 *  添加购物车
	  * @Title: addCart
	  * @author: share 2015年3月17日
	  * @modify: share 2015年3月17日
	  * @param @param cartReq
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#addCart(com.froad.thrift.vo.ShoppingCartVoReq)
	 */
	@Override
	public ResultVo addCart(ShoppingCartVoReq cartReq) throws TException {
		long start = System.currentTimeMillis();
		ResultVo result = new ResultVo();
		
		long memberCode = cartReq.getMemberCode();
		String clientId = cartReq.getClientId();
		String merchantId = cartReq.getMerchantId();
		String productId = cartReq.getProductId();
		int num = cartReq.getNum();
		
		if(memberCode <= 0 || clientId == null || "".equals(clientId) || StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(productId) || num <= 0){
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("添加购物车信息不能为空");
			return result;
		}
		
		ShoppingCartReq req = new ShoppingCartReq();
		req.setClientId(cartReq.getClientId());
		req.setMemberCode(cartReq.getMemberCode());
		req.setMerchantId(cartReq.getMerchantId());
		req.setNum(cartReq.getNum());
		req.setOutletId(cartReq.getOutletId());
		req.setProductId(cartReq.getProductId());
		req.setVipLevel(cartReq.getVipLevel());
		
		ResultBean bean = shoppingCartLogic.addCart(req);
		
		result.setResultCode(bean.getCode());
		result.setResultDesc(bean.getMsg());
		LogCvt.info("添加购物车耗时："+(System.currentTimeMillis() - start));
		return result;
				
	}

	/**
	  * 获取购物车商品数量
	  * @Title: getCartCount
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#getCartCount(long, long)
	 */
	@Override
	public int getCartCount(long memberCode, String clientId) throws TException {
		if(memberCode <= 0 || clientId == null || "".equals(clientId)){
			return 0;
		}
		return shoppingCartLogic.getCartCount(memberCode, clientId);
	}
	
	/**
	 *  属性转换
	  * @Title: convert
	  * @author: share 2015年3月24日
	  * @modify: share 2015年3月24日
	  * @param @param res
	  * @param @return    
	  * @return ShoppingCartVoRes    
	  * @throws
	 */
	private List<ShoppingCartVoRes> convert(List<ShoppingCart> resList){
		List<ShoppingCartVoRes> resVoList = new ArrayList<ShoppingCartVoRes>();
		for(ShoppingCart res: resList){
			resVoList.add(convertToVo(res));
		}
		return resVoList;
	}

	private ShoppingCartVoRes convertToVo(ShoppingCart res) {
		ShoppingCartVoRes voRes = new ShoppingCartVoRes();
		voRes.setClientId(res.getClientId());
		voRes.setMemberCode(res.getMemberCode());
		voRes.setErrCode(res.getErrCode());
		voRes.setErrMsg(res.getErrMsg());
		voRes.setMerchantId(res.getMerchantId());
		voRes.setMerchantName(res.getMerchantName());
		voRes.setVipQuantity(res.getVipQuantity());
		voRes.setVipMoney(Arith.div(res.getVipMoney(), 1000));
		voRes.setType(res.getType());
		voRes.setTime(res.getTime());
		voRes.setStatus(res.getStatus());
		voRes.setQuantity(res.getQuantity());
		voRes.setProductName(res.getProductName());
		voRes.setProductImage(res.getProductImage());
		voRes.setProductId(res.getProductId());
		voRes.setMerchantStatus(BooleanUtils.toBoolean(res.getMerchantStatus()));
		voRes.setMoney(Arith.div(res.getMoney(), 1000));
		voRes.setQuantity(res.getQuantity());
		voRes.setOrgCode(res.getOrgCode());
		voRes.setOrgName(res.getOrgName());
		voRes.setDeliveryMoney(Arith.div(res.getDeliveryMoney(), 1000));
		voRes.setOrgStatus(BooleanUtils.toBoolean(res.getOrgStatus()==null?0:res.getOrgStatus()));
		return voRes;
	}
	

	/**
	 *  更新购物车商品提货网点
	  * @Title: updateDelivery
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param productId
	  * @param @param outletId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#updateDelivery(long, long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultVo updateDelivery(long memberCode, String clientId,String merchantId, String productId, String outletId)
			throws TException {

		ResultVo result = new ResultVo();
		
		if(memberCode <= 0 || clientId == null || "".equals(clientId) || StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(productId)){
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("更新购物车商品信息不能为空");
			return result;
		}
		// 更新服务
		ResultBean bean = shoppingCartLogic.updateDelivery(clientId,memberCode,merchantId,productId,outletId);
		
		result.setResultCode(bean.getCode());
		result.setResultDesc(bean.getMsg());
		return result;
	}

	/**
	 *  批量删除购车信息
	  * @Title: deleteBatchCart
	  * @author: share 2015年4月10日
	  * @modify: share 2015年4月10日
	  * @param @param shoppingCartVoReq
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#deleteBatchCart(java.util.List)
	 */
	@Override
	public ResultVo deleteBatchCart(List<ShoppingCartVoReq> shoppingCartVoReq)throws TException {
		List<ShoppingCartReq> reqs = new ArrayList<ShoppingCartReq>();
		for(ShoppingCartVoReq cartReq : shoppingCartVoReq){
			ShoppingCartReq req = new ShoppingCartReq();
			req.setClientId(cartReq.getClientId());
			req.setMemberCode(cartReq.getMemberCode());
			req.setMerchantId(cartReq.getMerchantId());
			req.setProductId(cartReq.getProductId());
			reqs.add(req);
		}
		
		ResultBean bean = shoppingCartLogic.deleteBatchCart(reqs);
		ResultVo result = new ResultVo();
		result.setResultCode(bean.getCode());
		result.setResultDesc(bean.getMsg());
		return result;
	}
	
	/**
	 *  获取购物车商品总数量
	  * @Title: getCartCount
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @param @throws TException
	  * @throws
	  * @see com.froad.thrift.service.ShoppingCartService.Iface#getCartCount(long, long)
	 */
	@Override
	public int getCartProductCount(long memberCode, String clientId) throws TException {
		if(memberCode <= 0 || clientId == null || "".equals(clientId)){
			return 0;
		}
		return shoppingCartLogic.getCartProductCount(memberCode, clientId);
	}
	
}

