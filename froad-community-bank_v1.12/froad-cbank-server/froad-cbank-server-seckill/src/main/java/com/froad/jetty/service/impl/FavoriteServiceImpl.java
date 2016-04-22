package com.froad.jetty.service.impl;

import org.apache.thrift.TException;

import com.froad.enums.H5ResultCode;
import com.froad.enums.ResultCode;
import com.froad.jetty.service.FavoriteService;
import com.froad.jetty.vo.FavoriteRequestVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.logic.SeckillProductLogic;
import com.froad.logic.impl.SeckillProductLogicImpl;
import com.froad.po.SeckillProduct;
import com.froad.thrift.client2.ServiceClient;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.StoreProductInfoVo;

/**
 * 秒杀商品收藏业务实现
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:20:55
 * @version v1.0
 */
public class FavoriteServiceImpl implements FavoriteService {
	
	private SeckillProductLogic seckillProductLogic;
	
	public FavoriteServiceImpl() {
		
		seckillProductLogic = new SeckillProductLogicImpl();
	}
	
	@Override
	public ResponseVo collect(FavoriteRequestVo reqVo) {
		SeckillProduct product = seckillProductLogic.getProduct(reqVo.getClientId(), reqVo.getProductId());
		if (product != null) {
			StoreProductInfoVo req = new StoreProductInfoVo();
			//封装请求对象
			req.setProductId(reqVo.getProductId());
			req.setMerchantId(product.getMerchantId());
			req.setProductName(product.getProductName());
			req.setProductImage(product.getProductImage());
			
			ResultVo result = null;
			ServiceClient serviceClient = new ServiceClient();
			try {
				ProductService.Iface productService = (ProductService.Iface)serviceClient.createClient(ProductService.Iface.class);
				result = productService.addStoreProductInfoVo(reqVo.getClientId(), reqVo.getMemberCode(), req);
				if (ResultCode.product_exits.getCode().equals(result.getResultCode())) { // 已经收藏的code
					// 认为是收藏成功
					result.setResultCode(H5ResultCode.success.getCode());
					result.setResultDesc("收藏成功");
				} else if (!H5ResultCode.success.getCode().equals(result.getResultCode())) {
					LogCvt.debug("收藏商品失败，" + result.getResultDesc() + "[clientId=" + reqVo.getClientId() + ", memberCode=" + reqVo.getMemberCode() + ", productId=" + req.getProductId() + "]");
				} 
				return new ResponseVo(result.getResultCode(), result.getResultDesc());
			} catch (TException e) {
				LogCvt.error("收藏商品异常", e);
				return new ResponseVo(H5ResultCode.userLoginCenterException);
			} finally {
				try {
					serviceClient.returnClient();
				} catch(Exception e) {
					LogCvt.error("释放Thrift连接异常", e);
					return new ResponseVo(H5ResultCode.thriftException);
				}
			}
			
		} else {
			LogCvt.debug("收藏未获取到商品信息[product=null]");
			return new ResponseVo(H5ResultCode.productNotExist);
		}
		
	}

	@Override
	public Boolean delete(FavoriteRequestVo reqVo) {
		Boolean deleted = false;
		ServiceClient serviceClient = new ServiceClient();
		try {
			ProductService.Iface productService = (ProductService.Iface)serviceClient.createClient(ProductService.Iface.class);
			deleted = productService.deleteStoreProductInfoVo(reqVo.getClientId(), reqVo.getMemberCode(), reqVo.getProductId());
		} catch (TException e) {
			LogCvt.error("取消收藏商品异常", e);
			return null;
		} finally {
			try {
				serviceClient.returnClient();
			} catch(Exception e) {
				LogCvt.error("释放Thrift连接异常", e);
				return null;
			}
		}
		
		return deleted;
	}

	@Override
	public Boolean isCollected(FavoriteRequestVo reqVo) {
		Boolean collected = false;
		ServiceClient serviceClient = new ServiceClient();
		try {
			ProductService.Iface productService = (ProductService.Iface)serviceClient.createClient(ProductService.Iface.class);
			collected = productService.isExitsStoreProductInfo(reqVo.getClientId(), reqVo.getMemberCode(), reqVo.getProductId());
		} catch (TException e) {
			LogCvt.error("查询是否收藏商品异常", e);
			return null;
		} finally {
			try {
				serviceClient.returnClient();
			} catch(Exception e) {
				LogCvt.error("释放Thrift连接异常", e);
				return null;
			}
		}
		
		return collected;
	}

}
