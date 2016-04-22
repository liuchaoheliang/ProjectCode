package com.froad.server.tran;

import java.util.List;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Trans;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.util.command.UseTime;

/**
  * 类描述：交易服务 
  * @version: 1.0
  * @Copyright www.f-road.com.cn Corporation 2012 
  * @author: 刘丽 liuli@f-road.com.cn
  * @time: 2012-12-19 下午04:48:12
 */
public interface TranService {
	/**
	  * 方法描述：计算该交易的转移信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-19 下午04:48:40
	 */
//	public void countTansferInfoTran(Trans tran,Seller seller,SellerChannel sellerDepositChannel,Buyers buyer,BuyerChannel buyerPayChannel,UseTime useTime) throws Exception;

	
	/**
	  * 方法描述：计算该交易的转移信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-19 下午04:48:40
	 */
	public void countTansferInfoTran(Trans tran,Seller seller,SellerChannel sellerDepositChannel,Buyers buyer,BuyerChannel buyerPayChannel,User user,UserCertification userCertification,UseTime useTime) throws Exception;

	

	
	
}
