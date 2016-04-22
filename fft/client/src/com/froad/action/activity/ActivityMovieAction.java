package com.froad.action.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.support.activity.ActivityMovieActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.activityCustInfo.ActivityCustInfo;

import com.froad.client.activityLotteryResult.ActivityLotteryResult;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;
import com.opensymphony.xwork2.ActionContext;

public class ActivityMovieAction extends BaseActionSupport{
	private static final Logger logger=Logger.getLogger(ActivityMovieAction.class);
	private List<ActivityCustInfo> infoList = new ArrayList<ActivityCustInfo>();  
	private ActivityMovieActionSupport activityMovieActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private ActivityCustInfo user;
	private ActivityLotteryResult pager;
	private String mertchatId = null;

	/**
	 * *******************************************************
	 * @函数名: index  
	 * @功能描述: 进入活动页面
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 上午11:59:35
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public String index(){
		ActivityCustInfo condition = new ActivityCustInfo();
		condition.setBatchNumber(Command.ACTIVITY_BATCHNUMBER);
		infoList = activityMovieActionSupport.getAllCustInfo(condition);
		String card = null;
		String cardTop = null;
		String cardLast = null;
		String softCard = null;
		int cardLength = 0;
		
		for (ActivityCustInfo l : infoList) {
			card = l.getAccountNumber();
			cardLength = card.length();
			cardTop = card.substring(0,4);
			cardLast = card.substring(cardLength - 4, cardLength);
			softCard = cardTop;
			for (int i = 0; i < cardLength - 8; i ++) {
				softCard += "*";
			}
			softCard += cardLast;
			l.setIdentificationCard(softCard);
		}
		return "success";
	}
	
	/**
	 * *******************************************************
	 * @函数名: toGetTicket  
	 * @功能描述: 准备开始核实数据进行兑换
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 下午03:09:44
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public String toGetTicket(){
		 //true:领取成功   false:不具备领取资格 warm:已领取 overdue:活动过期

		String state = "false";
		user.setBatchNumber(Command.ACTIVITY_BATCHNUMBER);
		ActivityCustInfo acInfo = activityMovieActionSupport.getCustInfoByCondition(user);
		if(acInfo == null){
			log.info(getLoginUser().getUsername()+" 不具备领取条件，领取失败 原因：数据库无该用户中奖信息");
			return ajaxXml("<root>[{'state':'"+state+"','msg':'请核对您是否具有参与活动的资格！'}]</root>");
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			Date now = new Date();
			try {
				Date lockTime = sdf.parse(acInfo.getActivityEndTime());
				if(now.after(lockTime)){
					//活动已结束
					state = "overdue";
					return ajaxXml("<root>[{'state':'"+state+"','msg':'该活动已结束！'}]</root>");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if("1".equals(acInfo.getIsReceived())){
				state = "warm";
				log.info(getLoginUser().getUsername()+" 已领取参与该活动，领取失败 原因：活动已结束");
				return ajaxXml("<root>[{'state':'"+state+"','msg':'您已参与过该活动！'}]</root>");
			}
			
			Merchant m = new Merchant();
			m.setActivityId("1");
			//m.setPreferentialType("3");
			String html = "";
			List<Merchant> list = activityMovieActionSupport.getActivityMerchant(m);
			html += "<![CDATA[";
			int index = 0;
			for (Merchant merchant : list) {
				html += "<div class=\"divradio\">";
				html += "<span><img merchantId="+merchant.getId()+" src=\""+Command.IMAGE_SERVER_URL+merchant.getCompanyLogoUrl()+"\" "+(index == 0 ? "class=\"boredercl\"" :"")+"></span>";
				html += "<div class=\"radioinfor\"><p>"+merchant.getMstoreFullName()+"</p><p>地址："+merchant.getMstoreAddress()+"</p><p>电话："+merchant.getMstoreTel()+"3</p></div></div>";
				index ++;
			}
			html += "<input type=\"text\" id=\"merchantId\" style=\"display:none;\" value=\""+list.get(0).getId()+"\" />";
			html += "]]>";
			state = "true";
			log.info(getLoginUser().getUsername()+" 具备领取条件，将进入选票界面");
			return ajaxXml("<root>[{'state':'"+state+"','html':'"+html+"'}]</root>");
		}
	}

	/**
	 * *******************************************************
	 * @函数名: isLogin  
	 * @功能描述: 开始领取前的处理
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 下午03:49:14
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public String isLogin(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		Date now = new Date();
		try {
			Date lockTime = sdf.parse(Command.ACTIVITY_BEGINTIME);
			if(now.before(lockTime)){
				//活动未开启
				//TODO:   上线的时候需要加上该验证
				return ajaxXml("<root>[{'state':'04','msg':'活动暂未开启，敬请期待！'}]</root>");
			}
			Date endTime = sdf.parse(Command.ACTIVITY_ENDTIME);
			if(now.after(endTime)){
				//活动已结束
				return ajaxXml("<root>[{'state':'04','msg':'活动已结束，谢谢参与！'}]</root>");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = getLoginUser();
		if(user.getUserID() == null || user.getUserID().length() == 0){
			return ajaxXml("<root>[{'state':'01'}]</root>");
		}else{
			String isUserFlag = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
			if("0".equals(isUserFlag)){
				log.info("当前登录用户帐号： " + user.getUsername() + " 身份为普通会员，具有输入信息界面权限");
				return ajaxXml("<root>[{'state':'03'}]</root>");
			}else{
				log.info("当前登录用户帐号： " + user.getUsername() + " 不进入输入信息界面   原因：身份是商户，不具备领取资格");
				return ajaxXml("<root>[{'state':'02'}]</root>");
			}
		}		
	}
	/**
	 * *******************************************************
	 * @函数名: allUserInfo  
	 * @功能描述: 查询所有中奖用户
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 下午05:17:02
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public String allUserInfo(){
		ActivityCustInfo condition = new ActivityCustInfo();
		condition.setBatchNumber(Command.ACTIVITY_BATCHNUMBER);
		infoList = activityMovieActionSupport.getAllCustInfo(condition);
		String card = null;
		String cardTop = null;
		String cardLast = null;
		String softCard = null;
		int cardLength = 0;
		
		
		
		for (ActivityCustInfo l : infoList) {
			card = l.getAccountNumber();
			cardLength = card.length();
			cardTop = card.substring(0,4);
			cardLast = card.substring(cardLength - 4, cardLength);
			softCard = cardTop;
			for (int i = 0; i < cardLength - 8; i ++) {
				softCard += "*";
			}
			softCard += cardLast;
			l.setIdentificationCard(softCard);
		}
		return "success";
	}
	
	/**
	 * *******************************************************
	 * @函数名: queryActivityTrans  
	 * @功能描述: 商户获取所有活动交易查询（状态为成功兑换）
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:17:02
	 * @修改: LC
	 * @日期: 
	 **********************************************************
	 */
	public String queryActivityTrans(){
		logger.info("商户查询活动交易开始");
		if(pager==null)
			 pager=new ActivityLotteryResult();
		pager.setPageSize(10);
		pager.setIsConsume("1");
		pager.setOrderBy("consume_time");
		Map<String,Object> session=ActionContext.getContext().getSession();
		Merchant merchant=(Merchant)session.get(SessionKey.MERCHANT);
		logger.info("商户查询活动交易 info:MerchanId"+merchant.getId());
		String becode=(String) getSession("beCode");
		logger.info("操作员工号："+becode);
		if("1000".equals(becode)){
			pager.setBeCode(null);
		}else{
			pager.setBeCode(becode);
		}
		pager.setMerchantId(merchant.getId().toString());
		pager=activityMovieActionSupport.getMerchantActivityLotteryResult(pager);
		
		List<Object> list=pager.getList();
		for(int i=0;i<list.size();i++){
			ActivityLotteryResult activity=(ActivityLotteryResult) list.get(i);
			activity.setIsConsume(CastIsConsume(activity.getIsConsume()));
		}
		logger.info("查询结果条数："+list.size());
		logger.info("商户查询活动交易结束");
		return SUCCESS;
	}

	/**
	 * *******************************************************
	 * @函数名: getTicket  
	 * @功能描述: 开始选择电影院发送电影票
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 下午07:17:37
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public String getTicket(){		
		User userLogin = getLoginUser();
		if(userLogin.getUserID() == null || userLogin.getUserID().length() == 0){
			//重置到登录页面
			return ajaxXml("<root>[{'state':'login'}]</root>");
		}
		if(mertchatId == null || mertchatId.length() == 0){
			log.info(userLogin.getUsername() + " 操作者通过工具修改过传入的数据值");
			return ajaxXml("<root>[{'state':'caution'}]</root>");
		}
		log.info("用户选择的商户Id是:"+mertchatId +"姓名为"+user.getAccountName()+"银行卡号"+user.getAccountNumber()+"身份证"+user.getIdentificationCard());		
		ActivityCustInfo acInfo = activityMovieActionSupport.getCustInfoByCondition(user);
		if("1".equals(acInfo.getIsReceived())){
			log.info(userLogin.getUsername() + " 已兑换验证券！");
			return ajaxXml("<root>[{'state':'used'}]</root>");
		}
		Integer acInfoId = acInfo.getId();
		com.froad.client.activityLotteryResult.ActivityCustInfo ac = new com.froad.client.activityLotteryResult.ActivityCustInfo();
		ac.setId(acInfoId);
		ac.setIsReceived("1");//将该用户信息设置为已领取状态
		
		ActivityLotteryResult alr = createSecuritiesNo(new ActivityLotteryResult());	
		
		alr.setUserId(userLogin.getUserID());
		alr.setMerchantId(mertchatId);
		alr.setLotteryCustId(acInfo.getId().toString());
		alr.setType("1");		
		alr.setSmsTime(new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss").format(new Date()));//短信时间
		alr.setIsConsume("0");
		alr.setExpireTime(acInfo.getActivityEndTime());
		alr.setSmsNumber(1);		
		alr.setState("30");
		
		if(mertchatId != null && mertchatId.length() != 0){
			Merchant m = new Merchant();
			m.setActivityId("1");
			m.setId(Integer.parseInt(mertchatId));
			//m.setPreferentialType("3");
			Merchant mt = activityMovieActionSupport.getActivityMerchant(m).get(0);			
			com.froad.client.activityLotteryResult.Merchant merchant = new com.froad.client.activityLotteryResult.Merchant();
			
			merchant.setMstoreFullName(mt.getMstoreFullName());
			merchant.setMstoreTel(mt.getMstoreTel());
			boolean flag = activityMovieActionSupport.updateActivityLotteryResultAndCustInfo(ac, alr,userLogin.getMobilephone(),merchant);		
			if(flag){
				log.info(userLogin.getUsername() + "领取券成功！将向手机号"+userLogin.getMobilephone() +" 放送短信,券号为" + alr.getSecuritiesNo());
				return ajaxXml("<root>[{'state':'success'}]</root>");
			}else{
				log.info(userLogin.getUsername() + "领取券失败 ，原因：向数据库插入数据异常");
				return ajaxXml("<root>[{'state':'error'}]</root>");
			}	
			
		}else{
			log.info("选择商家时没有指定正确的merchantId");
			return ajaxXml("<root>[{'state':'error'}]</root>");
		}
			
	}

	//转换是否已经兑换成功
	public String CastIsConsume(String isCousume){
		if("0".equals(isCousume)){
			return "未成功";
		}else if("1".equals(isCousume)){
			return "已成功";
		}else{
			return "--";
		}
	}

	/**
	 * *******************************************************
	 * @函数名: createSecuritiesNo  
	 * @功能描述: 生成券
	 * @输入参数: @param alr
	 * @输入参数: @return <说明>
	 * @返回类型: ActivityLotteryResult
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-25 上午11:08:59
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	private ActivityLotteryResult createSecuritiesNo(ActivityLotteryResult alr){
		Random random = new Random();
		while(true){
			String ticket = (random.nextLong() + "").substring(1, 15);
			alr.setSecuritiesNo(ticket);
			if (activityMovieActionSupport.checkIsSecuritiesNoExist(alr) == null){
				return alr;
			}
		}
	}

	/**
	 * *******************************************************
	 * @函数名: queryMyTicket  
	 * @功能描述: 用户查询自己的活动券信息
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:17:02
	 * @修改: LC
	 * @日期: 
	 **********************************************************
	 */

	public String queryMyTicket(){
		logger.info("用户查询我的活动开始");
		if(pager==null)
			 pager=new ActivityLotteryResult();
		pager.setPageSize(10);//每页10条
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		logger.info("用户UserId:"+userId);
		if(userId != null){		
			pager.setUserId(userId);
			//pager.setOrderBy("");
			pager=activityMovieActionSupport.getMyTicket(pager);
			List<Object> list=pager.getList();
			for(Object rlist:list){
				ActivityLotteryResult lotteryResult=(ActivityLotteryResult) rlist;
				String startTime=lotteryResult.getActivityCustInfo().getActivityStartTime();
				String endTime=lotteryResult.getActivityCustInfo().getActivityEndTime();
				endTime=endTime.substring(0, endTime.indexOf("|"));
				startTime=startTime.substring(0, startTime.indexOf("|"));
				lotteryResult.getActivityCustInfo().setActivityStartTime(startTime);
				lotteryResult.getActivityCustInfo().setActivityEndTime(endTime);				
			}
			logger.info("查询结果条数："+list.size());
			logger.info("用户查询我的活动结束");
			return SUCCESS;
		}else{
			logger.info("查询我的活动出错，用户没有登录");
			return "failse";
		}
	}
	
	
	
	/**
	 * *******************************************************
	 * @函数名: authenticationForActivity  
	 * @功能描述: 活动对换券的认证
	 * @输入参数: @return <说明>
	 * @返回类型: 
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:17:02
	 * @修改: LC
	 * @日期: 
	 **********************************************************
	 */
	
	public void authenticationForActivity() throws ParseException{
		logger.info("验证活动认证开始：");
		JSONObject json = new JSONObject();
		Map<String,Object> session=ActionContext.getContext().getSession();
		String securitiesNo=getParameter("securitiesNo");
		logger.info("查询兑换券的券号："+securitiesNo);
		Merchant merchant=(Merchant)session.get(SessionKey.MERCHANT);
		MerchantUserSet merchantUserSet=new MerchantUserSet();
		List<ActivityLotteryResult> list=activityMovieActionSupport.getTicketBysecuritiesNo(securitiesNo);
		if(list != null && list.size()>0 && merchant.getId().toString().equals(list.get(0).getMerchantId())){
			ActivityLotteryResult activityLotteryResult=list.get(0);
			String batchNumber=list.get(0).getActivityCustInfo().getBatchNumber();
			if(Command.ACTIVITY_BATCHNUMBER.equals(batchNumber)){
				if( "0".equals(activityLotteryResult.getIsConsume()) ){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				Date now=new Date();
				Date endTime = sdf.parse(activityLotteryResult.getExpireTime());
				if(now.before(endTime)){
					//查询该操作员信息
					merchantUserSet.setMerchantId(merchant.getId().toString());
					merchantUserSet.setBeCode(getSession("beCode").toString());
					logger.info("操作员工号："+getSession("beCode").toString());
					MerchantUserSet mlist=merchantUserSetActionSupport.getMerchantUserSetListByPager(merchantUserSet);
					//认证成功，修改信息
					mlist=(MerchantUserSet) mlist.getList().get(0);
					activityLotteryResult.setBeName(mlist.getBeName());
					activityLotteryResult.setBeCode(mlist.getBeCode());
					activityLotteryResult.setIsConsume("1");
					activityLotteryResult.setConsumeTime(now.toString());
					activityLotteryResult.setUpdateTime(now.toString());
					activityMovieActionSupport.updateActivityLotteryResultById(activityLotteryResult);
					logger.info("验证活动认证结束");
					json.put("reno",Command.respCode_SUCCESS);
					json.put("message","兑换码验证成功！");
				}else{
					json.put("reno",Command.respCode_FAIL);
					json.put("message","验证失败！兑换码已经过期！");
				}
			}else{
				json.put("reno",Command.respCode_FAIL);
				json.put("message","验证失败！兑换码已经领取！");
			}
			}else{
				json.put("reno",Command.respCode_FAIL);
				json.put("message","验证失败！活动已经过期！");
			}			
		}else{
			json.put("reno",Command.respCode_FAIL);
			json.put("message","验证失败！兑换码无效！");
		}
		ajaxJson(json.toString());
	}
	
	/**
	 * *
	 * @throws ParseException ******************************************************
	 * @函数名: reSendActivityMessage  
	 * @功能描述: 短信重发
	 * @输入参数: @return <说明>
	 * @返回类型: 
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:17:02
	 * @修改: LC
	 * @日期: 
	 **********************************************************
	 */
	
	public void reSendActivityMessage() throws ParseException{
		log.info("用户重发短信开始");
		JSONObject json = new JSONObject();
		//User userLogin = getLoginUser();
		String LotteryResultInfoId=getRequest().getParameter("lotteryResultInfoId");
		log.info("活动消费记录ID为："+LotteryResultInfoId);
		pager=activityMovieActionSupport.getActivityLotteryResultById(Integer.parseInt(LotteryResultInfoId));
		String Phone=loginUser.getMobilephone();
		log.info("用户手机号为："+Phone);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		Date now=new Date();
		Date endTime = sdf.parse(pager.getExpireTime());
		if(now.after(endTime)){
			json.put("reno", Command.respCode_FAIL);
			json.put("message", "兑换码已经过期，发送短信失败");
		}else if("1".equals(pager.getIsConsume())){
			json.put("reno", Command.respCode_FAIL);
			json.put("message", "兑换码已消费，发送短信失败");
		}else if(pager.getSmsNumber()<4){
			boolean falg=activityMovieActionSupport.resendActivityMessage(pager, Phone, pager.getMerchant());
			if(falg){
				pager.setSmsNumber(pager.getSmsNumber()+1);
				pager.setSmsTime(new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss").format(new Date()));
				activityMovieActionSupport.updateActivityLotteryResultById(pager);
				json.put("reno", Command.respCode_SUCCESS);
				json.put("message", "发送短信成功");
				
			}else{
				json.put("reno", Command.respCode_FAIL);
				json.put("message", "发送短信失败");
			}
		}else{
			json.put("reno", Command.respCode_FAIL);
			json.put("message", "发送次数受限，发送短信失败");
		}

		log.info("用户重发短信结束");
		ajaxJson(json.toString());
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>跳转活动查询总列表</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-17 下午04:13:46 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public String activity_list_index(){
		return SUCCESS;		
	}
	
	
	
	
	
	
	
	public List<ActivityCustInfo> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<ActivityCustInfo> infoList) {
		this.infoList = infoList;
	}
	@JSON(serialize=false)
	public ActivityMovieActionSupport getActivityMovieActionSupport() {
		return activityMovieActionSupport;
	}
	public void setActivityMovieActionSupport(
			ActivityMovieActionSupport activityMovieActionSupport) {
		this.activityMovieActionSupport = activityMovieActionSupport;
	}
	
	@JSON(serialize=false)
	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}

	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public ActivityCustInfo getUser() {
		return user;
	}
	public void setUser(ActivityCustInfo user) {
		this.user = user;
	}


	public ActivityLotteryResult getPager() {
		return pager;
	}

	public void setPager(ActivityLotteryResult pager) {
		this.pager = pager;
	}
	

	public String getMertchatId() {
		return mertchatId;
	}
	public void setMertchatId(String mertchatId) {
		this.mertchatId = mertchatId;
	}

}
