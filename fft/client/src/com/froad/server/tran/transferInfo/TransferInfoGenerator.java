package com.froad.server.tran.transferInfo;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.RuleDetail;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;

public interface TransferInfoGenerator {
	/**
	  * 方法描述：根据一条规则明细，给该交易生成与该规则明细对应的流转信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-20 上午11:37:39
	 */
	Pay generateTransferInfo(Trans tran,Seller seller,RuleDetail ruleDetail,SellerChannel sellerDepositChannel,Buyers buyer,BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception;

}
