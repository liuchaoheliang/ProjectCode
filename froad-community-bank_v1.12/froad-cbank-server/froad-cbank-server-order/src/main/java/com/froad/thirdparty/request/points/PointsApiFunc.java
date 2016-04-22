package com.froad.thirdparty.request.points;

import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.exception.AppException;

/**
 * 积分相关接口
 * @author FQ
 *
 */
public interface PointsApiFunc {
	
	/**
	 * 积分查询
	 */
	public PointsRes queryParAccountPoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>消费积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes consumePoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退还积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 下午5:19:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes refundPoints(PointsReq pointsReq) throws AppException;

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>赠送积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月14日 上午9:54:30 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes presentPoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>兑充积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月14日 下午1:42:14 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes fillPoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>积分提现申请接口 （异步）</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年2月10日 上午11:49:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes applyForPointsToCash(PointsReq pointsReq) throws AppException;

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询积分比例</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年6月12日 下午4:56:52 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes queryPointsExchageRate(PointsReq pointsReq) throws AppException;
	
	/**
	  * 方法描述：签约关系通知接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年7月25日 下午12:29:17
	  */
	public PointsRes notifyAccountRelation(PointsReq pointsReq) throws AppException;
	
	
	/**
	  * 方法描述：充值卡信息查询接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年8月18日 上午11:12:32
	  */
	public PointsRes queryDeposit(PointsReq pointsReq)throws AppException;
	
	
	
	/**
	  * 方法描述：账户充值接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年8月18日 上午11:13:24
	  */
	public PointsRes deposit(PointsReq pointsReq)throws AppException;
	
	
	/**
	  * 方法描述：发送验证码（消费安徽银行积分前使用）
	  * @param: PointsReq
	  * @return: PointsRes
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年10月2日 下午5:06:07
	  */
	public PointsRes sendCheckCode(PointsReq pointsReq)throws AppException;
	
	
	/**
	  * 方法描述：校验验证码
	  * @param: PointsReq
	  * @return: PointsRes
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年10月2日 下午5:07:44
	  */
	public PointsRes validateCheckCode(PointsReq pointsReq)throws AppException;
	
	
	/**
	  * 方法描述：查询银行积分
	  * @param: PointsReq
	  * @return: PointsRes
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年10月2日 下午5:09:10
	  */
	public PointsRes queryBankPointsByMobile(PointsReq pointsReq)throws AppException;
	
	
	/**
	  * 方法描述：安徽按手机号消费积分接口
	  * @param: PointsReq
	  * @return: PointsRes
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年10月2日 下午5:10:05
	  */
	public PointsRes payPointsByMobile(PointsReq pointsReq)throws AppException;
	
	
	/**
	  * 方法描述：查询积分比例
	  * @param: PointsReq
	  * @return: PointsRes
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年10月19日 下午6:11:06
	  */
	public PointsRes queryRatio(PointsReq pointsReq);

	/**
	  * 方法描述：银行账户签约接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年11月30日 上午11:35:29
	  */
	public PointsRes bindBankAccount(PointsReq pointsReq)throws AppException;
	
	
	
	/**
	  * 方法描述：银行账户解约接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年11月30日 上午11:35:25
	  */
	public PointsRes unBindBankAccount(PointsReq pointsReq)throws AppException;
	
	
	
	/**
	  * 方法描述：积分消费接口,分页查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年12月1日 下午4:28:15
	  */
	public PointsRes getMyPointsTrans(PointsReq pointsReq) throws AppException;

	/**
	 * 方法描述：查询订单状态
	 * @param request
	 * @return
	 */
	public PointsRes queryOrderStatus(PointsReq request);
	
	/**
	 * 查询积分签约关系
	 * @param pointsReq
	 * @return
	 */
	public PointsRes contractRelationshipQuery(PointsReq pointsReq);
}
