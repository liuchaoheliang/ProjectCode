package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.User;
import com.froad.client.goodsGatherRack.GoodsGatherRack;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellersService;
import com.froad.client.trans.OrderType;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.common.TrackNoGenerator;
import com.froad.server.tran.TranService;
import com.froad.util.Assert;
import com.froad.util.CommonUtil;
import com.froad.util.command.Command;
import com.froad.util.command.PayChannel;
import com.froad.util.command.RuleDetailType;
import com.froad.util.command.SellerRuleType;
import com.froad.util.command.State;
import com.froad.util.command.TransState;
import com.froad.util.command.TransType;
import com.froad.util.command.UseTime;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 *卖家交易管理服务
 */
public class SellersActionSupport {
	private static Logger log = Logger.getLogger(SellersActionSupport.class);
	private SellersService sellersService;
	private TranService transServiceInClient;
	private TransActionSupport transActionSupport;
	public Seller getSellerById(String sellerId){
		if(sellerId==null||"".equals(sellerId)){
			log.error("卖家编号为空");
			return null;
		}
		return sellersService.selectById(Integer.parseInt(sellerId));
	}
	
	public Seller getSellerByUserId(String sellerId){
		return sellersService.selectById(Integer.parseInt(sellerId));
	}
	
	/**
	 * 根据商户ID 查询卖家
	 * @param merchantId
	 * @return
	 */
	public List<Seller> getSellerByMerchantId(String merchantId){
		return sellersService.getSellerByMerchantId(merchantId);
	}
	
	/**
	 * 多条件查询卖家
	 * @param seller
	 * @return
	 */
	public List<Seller> getSellerListBySelective(Seller seller){
		return sellersService.getBySelective(seller);
	}
	
	/**
	  * 方法描述：是否是送积分的卖家
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-7 下午05:01:57
	 */
	public boolean isSellerSendPoints(String userId) {
		boolean result =false;
		if(Assert.empty(userId))
			return result;
//		Cashdesk cashdesk = getCashdesk(userId);
//		if(cashdesk==null)
//			return result;
//		List<SellerChannel> sellerDepositChannelList = cashdesk.getSellerDepositChannelList();
//		if(Assert.empty(sellerDepositChannelList))
//			return result;
//		SellerChannel sellerDepositChannel=sellerDepositChannelList.get(0);
//		SellerRule sellerRule=sellerChannel.getSellerRule();
//		if(sellerRule!=null && SellerRuleType.POINTS.getValue().equals(sellerRule.getSellerRuleType()))
//			result=true;
		return result;
	}
	
	public SellersService getSellersService() {
		return sellersService;
	}
	public void setSellersService(SellersService sellersService) {
		this.sellersService = sellersService;
	}
	
}
