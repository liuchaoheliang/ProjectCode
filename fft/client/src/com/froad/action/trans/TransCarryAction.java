package com.froad.action.trans;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.froad.action.support.PointActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.support.trans.TransCarryActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.goodsCarryRack.GoodsCarryRack;
import com.froad.client.point.PointsAccount;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.command.PayCommand;
import com.opensymphony.xwork2.Action;


	/**
	 * 类描述：PC端的积分提现
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 4, 2013 3:13:04 PM 
	 */
public class TransCarryAction extends BaseActionSupport{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final Logger logger=Logger.getLogger(TransCarryAction.class);
	
	private TransActionSupport transActionSupport;
	
	private TransCarryActionSupport transCarryActionSupport;
	
	private UserCertificationActionSupport userCertificationActionSupport;
	
	private PointActionSupport pointActionSupport;
	
	private UserActionSupport  userActionSupport;
	
	//积分提现pager,trans, transDetails, user,userCertification, transAddtionalInfoVo
	private Trans trans;
	
	private GoodsCarryRack pager;
	
	private TransDetails transDetails;
	
	/**用户提现认证信息，交易添加成功时查出来，以便成功页面显示**/
	private String accountNo;
	
	private String respMsg;//提示信息
	
	private PointsAccount pointsAccount;//积分账户
	
	private User user;//会员信息
	
	private UserCertification userCert;
	
	
	/**
	  * 方法描述：查询提现上架商品,进入提现页面
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 9, 2013 5:59:25 PM
	  */
	public String queryBeforeExch(){
		pager=new GoodsCarryRack();
		pager.setGoodsCategoryId(TranCommand.CATEGORY_WITHDRAW);
		List<GoodsCarryRack> list=transCarryActionSupport.getBySelective(pager);
		if(list==null||list.size()==0){
			logger.error("============积分提现的上架商品不存在============");
			respMsg="暂不支持积分提现";
			return Action.ERROR;
		}
		pager=list.get(0);
		String userId=(String)getSession().get("userId");
		if(userId==null||"".equals(userId)){
			return "login_page";
		}
		String username=(String)getSession().get(userId);
		User sessionUser=(User)getSession().get("user");
		if(sessionUser==null){
			try {
				user=userActionSupport.queryUserAllByUserID(userId);
			} catch (Exception e) {
				logger.error("会员信息查询异常",e);
				return "login_page";
			}
			getSession().put("user", user);
		}else{
			user=sessionUser;
		}
//		pointsAccount=pointActionSupport.queryfftPoints(username);
		
		Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(username);
		if(!Assert.empty(pointsTypePointsAccountMap)){
			pointsAccount=pointsTypePointsAccountMap.get("FFTPlatform");
			setSession(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
		}
		
		userCert=userCertificationActionSupport.getUserCertByUserId(userId);
		if(userCert==null){
			logger.error("===========用户未进行提现认证==========");
			return Action.SUCCESS;
		}
		String acctNo=userCert.getAccountNo();
		accountNo="********"+acctNo.substring(acctNo.length()-4);
		return Action.SUCCESS;
	}
	
	
	/**
	  * 方法描述：进入提现的确认页面
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 9, 2013 6:17:48 PM
	  */
	public String confirmExch(){
		String userId=(String)getSession("userId");
		if(trans==null||transDetails==null){
			logger.error("=============无效的提现请求=============");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		String goodsNumber=transDetails.getGoodsNumber();
		if(goodsNumber==null||"".equals(goodsNumber.trim())){
			logger.error("=============提现积分为空=============");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		if(goodsNumber.indexOf(".")!=-1){
			logger.error("提现积分数不能为小数");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		int number=Integer.parseInt(goodsNumber);
		if(number<10||number%10!=0){
			logger.error("最小提现积分数为10分，并且只能是10的整倍数");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		trans.setUserId(userId);
		trans.setClientType(PayCommand.CLIENT_PC);
		try {
			trans=transCarryActionSupport.makeCarryTrans(trans, transDetails);
		} catch (Exception e) {
			logger.error("组装交易异常",e);
		}
		
		if(trans==null){
			logger.error("=============交易组装失败=============");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		transDetails=trans.getTransDetailsList().get(0);
		userCert=userCertificationActionSupport.getUserCertByUserId(userId);
		if(userCert==null){
			logger.error("=============用户未进行提现认证=============");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		String acctNo=userCert.getAccountNo();
		accountNo="********"+acctNo.substring(acctNo.length()-4);
		return Action.SUCCESS;
	}
	
	
	/**
	  * 方法描述：执行提现操作(提现下单)
	  * @param: 下面参数必须从页面传递过来(trans.sellerId,trans.channelId,
	  *                               transDetails.goodsRackId,transDetails.goodsNumber)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 9, 2013 6:22:37 PM
	  */
	public String doCarry(){
		logger.info("===========积分提现开始==========");
		String userId=(String)getSession("userId");
		logger.info("提现用户的会员编号为："+userId);
		if(trans==null||transDetails==null){
			logger.error("=============无效的提现请求=============");
			this.queryBeforeExch();
			return Action.INPUT;
		}
		trans.setUserId(userId);
		trans.setClientType(PayCommand.CLIENT_PC);
		//TODO 待验证的参数channelId goodsRackId
		Trans transObj=null;
		try {
			transObj=transCarryActionSupport.makeCarryTrans(trans, transDetails);
		} catch (Exception e) {
			logger.error("组装交易异常",e);
		}
		if(transObj==null){
			logger.error("=============交易组装失败=============");
			respMsg="积分不够或提现积分数有误。";
			return Action.ERROR;
		}
		trans=transActionSupport.doTrans(transObj);
		if(trans==null){
			logger.error("============交易添加失败===========");
			respMsg="积分不够，或提现积分数有误。";
			return Action.ERROR;
		}
		if(!TranCommand.RESP_CODE_PAY_REQ_OK.equals(trans.getRespCode())){
			logger.error("============积分扣除失败===========");
			respMsg="积分不够，或提现积分数有误。";
			return Action.ERROR;
		}
		UserCertification userCert=userCertificationActionSupport.getUserCertByUserId(userId);
		String acctNo=userCert.getAccountNo();
		accountNo="********"+acctNo.substring(acctNo.length()-4);
		logger.info("============交易添加成功===========");
		logger.info("===========积分提现结束==========");
		return Action.SUCCESS;
	}
	
	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public GoodsCarryRack getPager() {
		return pager;
	}

	public void setPager(GoodsCarryRack pager) {
		this.pager = pager;
	}

	public TransDetails getTransDetails() {
		return transDetails;
	}

	public void setTransDetails(TransDetails transDetails) {
		this.transDetails = transDetails;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}


	public void setUserCertificationActionSupport(
			UserCertificationActionSupport userCertificationActionSupport) {
		this.userCertificationActionSupport = userCertificationActionSupport;
	}

	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setTransCarryActionSupport(
			TransCarryActionSupport transCarryActionSupport) {
		this.transCarryActionSupport = transCarryActionSupport;
	}


	public String getRespMsg() {
		return respMsg;
	}


	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}


	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}


	public PointsAccount getPointsAccount() {
		return pointsAccount;
	}


	public void setPointsAccount(PointsAccount pointsAccount) {
		this.pointsAccount = pointsAccount;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}


	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}


	public UserCertification getUserCert() {
		return userCert;
	}


	public void setUserCert(UserCertification userCert) {
		this.userCert = userCert;
	}

	
}
