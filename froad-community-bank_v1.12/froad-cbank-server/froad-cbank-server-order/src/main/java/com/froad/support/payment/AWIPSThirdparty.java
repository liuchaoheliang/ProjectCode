package com.froad.support.payment;


import java.util.Date;

import com.froad.common.beans.PointInfo;
import com.froad.common.beans.ResultBean;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.pay.user.dto.MemberBankSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.VIPLevel;

/**
 * 处理第三方交互系统
* <p>Function: AWIPSThirdparty</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-22 上午9:24:52
* @version 1.0
 */
public interface AWIPSThirdparty {
	
	/**
	 * 通过memberCode查询用户信息
	* <p>Function: queryByMemberCode</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:45:54
	* @version 1.0
	* @param memberCode
	* @return
	 */
	UserSpecDto queryUserByMemberCode(Long memberCode);

	/**
	 * 查询用户积分信息
	* <p>Function: queryUserPoints</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:56:10
	* @version 1.0
	* @param loginID
	* @param pointPartnerNo
	* @param clientId
	* @return
	 */
	PointInfo queryUserPoints(String loginID,String clientId);
	
	/**
	 * 积分消费
	* <p>Function: pointPayOfPointSys</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午9:32:02
	* @version 1.0
	* @param payment
	* @return
	 */
	public PointsRes pointConsume(PointsReq req);
	
	/**
	 * 现金消费
	* <p>Function: cashPayOfOpenAPISys</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 下午5:14:18
	* @version 1.0
	* @param order
	* @param payment
	* @return
	 */
	public OpenApiRes cashConsume(OpenApiReq req);
	
	/**
	 * 贴膜卡校验
	* <p>Function: verifyFoilCardNum</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-22 上午11:42:06
	* @version 1.0
	* @param clientId
	* @param foilCardNum
	* @return
	 */
	public ResultBean verifyFoilCardNumOfOpenAPI(String clientId,String foilCardNum);
	
	/**
	 * 积分退款
	* <p>Function: pointRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-25 下午3:01:53
	* @version 1.0
	* @param req
	* @return
	 */
	public PointsRes pointRefund(PointsReq req);
	
	/**
	 * 积分赠送
	* <p>Function: pointPresent</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-26 上午11:18:05
	* @version 1.0
	* @param req
	* @return
	 */
	public PointsRes pointPresent(PointsReq req);
	
	/**
	 * 现金退款
	* <p>Function: cashRefund</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-27 上午9:39:55
	* @version 1.0
	* @param req
	* @return
	 */
	public OpenApiRes cashRefund(OpenApiReq req);
	
	/**
	 * 查询支付结果 现金
	* <p>Function: verifyPayResult</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-5 下午3:15:30
	* @version 1.0
	* @param req
	* @return
	 */
	public OpenApiRes verifyPayResult(QueryParamApiReq req);
	
	/**
	 * 查询支付结果 积分
	* <p>Function: verfyPayResult</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-7 下午1:37:27
	* @version 1.0
	* @param req
	* @return
	 */
	public PointsRes verfyPayResult(PointsReq req);
	
	/**
	 * 开通会员VIP
	* <p>Function: processVIPOrder</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年6月23日 下午2:37:40
	* @version 1.0
	* @param memberCode
	* @param vipLevel
	* @param availableDays
	* @param bankLabelID
	* @param bankOrg
	* @param clientChanelName
	* @return
	 */
	public Result processVIPOrder(Long memberCode, VIPLevel vipLevel,Date vipExpirationTime, String bankLabelID, String bankOrg,String clientChannel);
	
	/**
	 * 查询在具体客户端下的某个用户签约的银行卡信息
	 * queryUserSignatoryBankCardOfClient:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年9月11日 上午11:14:55
	 * @param clientId
	 * @param memberCode
	 * @return
	 *
	 */
	public MemberBankSpecDto queryUserSignatoryBankCardOfClient(String clientId,Long memberCode);
	
	/**
	 * 查询某个会员的VIP状态
	 * @param memberCode
	 * @param clientId
	 * @return
	 */
	public VIPSpecDto queryVIPInfoByMemberCode(Long memberCode,String clientId);
	
	/**
	 * 取消会员
	 * cancelVIPOrder:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015年12月7日 下午3:24:26
	 * @param memberCode
	 * @param bankOrg
	 * @return
	 *
	 */
	public boolean cancelVIPOrder(Long memberCode,String bankOrg);
}
