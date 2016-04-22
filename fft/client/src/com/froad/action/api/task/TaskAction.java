package com.froad.action.api.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.DailyTaskActionSupport;
import com.froad.action.support.DailyTaskRuleActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.PayActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.client.buyers.Buyers;
import com.froad.client.dailyTask.DailyTask;
import com.froad.client.dailyTaskRule.DailyTaskRule;
import com.froad.client.merchant.Merchant;
import com.froad.client.point.Points;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.user.User;
import com.froad.common.PayState;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.command.PayCommand;



public class TaskAction extends BaseApiAction {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TaskAction.class);
	private String reno;// 操作结果码
	@SuppressWarnings("unused")
	private String remsg;// 响应信息
	private Map<String, Object> rebody = new HashMap<String, Object>();// 主体
	@SuppressWarnings("unused")
	private String recount;// 返回请求标识号（原样返回）
	private DailyTaskActionSupport dailyTaskActionSupport;
	private DailyTaskRuleActionSupport dailyTaskRuleActionSupport;
	private UserActionSupport userActionSupport;
	private BuyersActionSupport buyersActionSupport;
	private PointActionSupport pointActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private TransActionSupport transActionSupport;
	private PayActionSupport payActionSupport;
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	public String sign() {
		logger.info("[TaskAction- sign]body:" + body);

		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			if (Assert.empty(userId)) {
				reno = "0009";
				logger.error("TaskAction.sign：userId 为空.");
				return SUCCESS;
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			User user = userActionSupport.queryUserAllByUserID(userId);
			if(user == null || user.getUserID() == null || "".equals(user.getUserID().trim())){
				reno = "0101";
				logger.error("TaskAction.sign：该用户不存在.");
				return SUCCESS;
			}
			Buyers buyer =buyersActionSupport.getBuyerByUserId(userId);
			if(buyer == null || buyer.getId() == null  ){
				logger.info("该用户不是买家。");
				reno="0302"; 
				return SUCCESS;
			}
			DailyTask dailyTask = dailyTaskActionSupport.getDailyTaskByUserId(userId);
			if (dailyTask == null) {
				dailyTask = new DailyTask();
				dailyTask.setUserId(user.getUserID());
				dailyTask.setCreateTime(df.format(new Date()).toString());
				dailyTask.setSignNo("1");
				dailyTask.setState("30");
				dailyTask.setUpdateTime(df.format(new Date()).toString());
				dailyTask.setLastSignTime(df.format(new Date()).toString());
				dailyTask.setRemark("");
				dailyTaskActionSupport.addDailyTask(dailyTask);
			}
			
			DailyTaskRule dailyTaskRule = dailyTaskRuleActionSupport.getByPrimaryKey(100001001);//默认值
			if (dailyTaskRule == null) {
				reno = "0402";
				logger.error("TaskAction.sign：dailyTaskRule 为空.");
				return SUCCESS;
			}
			
			String signo = dailyTask.getSignNo();
			String rule_fate = dailyTaskRule.getFate();//规则天数
			String rule_isContinuation = dailyTaskRule.getIsContinuation();//是否要求连续登录：0-否 1-是
			String point_value = dailyTaskRule.getPointValue();//赠送积分数
			
			if (rule_fate == null || rule_isContinuation == null || point_value == null) {
				reno = "0402";
				logger.error("TaskAction.sign：dailyTaskRule属性值 为空.");
				return SUCCESS;
			}
			
			//规则天数
			Integer fateInt = Integer.parseInt(rule_fate);
			Integer num = 1;
			Integer countVal = 0;
			
			Integer id = null;
			 
			id = isYesterday(dailyTask.getLastSignTime());
			 
			
			boolean bol = true;
			if ("0".equals(signo.trim()) && id != 1) {  //上次签到不是今天
				dailyTask.setSignNo("1");
				countVal = 1;
			}else{
				num = Integer.parseInt(signo);
				num++;
				countVal = num;
				
				//是否要求连续登录：rule_isContinuation：0-否 1-是
				if("0".equals(rule_isContinuation)){
					if(id == 1){
						countVal--;
						bol = false;
					}else{
						dailyTask.setSignNo(num >= fateInt ? "0" : num + "");
					}
				}
				
				if("1".equals(rule_isContinuation) && fateInt != 1){
					//上次登录是昨天
					if(id == 0){
						dailyTask.setSignNo(num >= fateInt ? "0" : num + "");
					}
					//上次登录是昨天之前
					if(id == 2){
						dailyTask.setSignNo("1");
						countVal = 1;
					}
					//其他
					if(id == 1){
						countVal--;
						bol = false;
					}
				}
				
				if(fateInt ==1) countVal--;
			}
			
			if(bol){
				dailyTask.setLastSignTime(df.format(new Date()));
				dailyTask.setUpdateTime(df.format(new Date()));
				dailyTaskActionSupport.updateDailyTaskByUserId(dailyTask);
			}
			
			if(countVal >= fateInt){
				//执行签到的赠送积分交易
				this.doTrans(user, PayCommand.CLIENT_ANDROID, point_value);
			}
			
			rebody.put("signPercentage", countVal == fateInt ? rule_fate +"/"+ rule_fate: countVal +"/"+ rule_fate);
			rebody.put("point", countVal == fateInt ? dailyTaskRule.getPointValue() : "0");
			
		} catch (Exception e) {
			reno="9999";
			logger.error("TaskAction.sign Exception",e);
		}
		
		reno = "0000";
		remsg="签到成功";
		return SUCCESS;
		
	}
	
	//0:上次签到是昨天
	//1：上次签到是今天
	//2:上次签到是昨天以前
	public Integer isYesterday(String lastLoginDate) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		lastLoginDate = lastLoginDate.substring(0,10);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = df.format(cal.getTime());
		String today = df.format(new Date());
		
		if(lastLoginDate.equals(yesterday))return 0;
		if(lastLoginDate.equals(today))return 1;
		
		return 2;
		
	}	
		
	
	/**
	  * 方法描述：做用户签到交易
	  * @param: User(userId,username,phone)
	  * @param: clientType 客户端类型
	  * @param: points 赠送积分数
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 9, 2013 2:32:20 PM
	  */
	private void doTrans(User user,String clientType,String points){
		//组织交易所需数据
		Trans trans=this.makeTrans(user, clientType, points);
		if(trans==null){
			logger.error("=======签到交易组装失败=======");
			return;
		}
		//添加交易
		trans=transActionSupport.addTrans(trans);
		if(trans==null){
			logger.error("======签到交易添加失败=======");
			return;
		}
		//赠送积分
		Points pointsRes=this.presentPoints(user.getUsername(), points);
		//修改交易和支付记录的状态
		Pay pay=trans.getPayList().get(0);
		com.froad.client.pay.Pay tmpPay=new com.froad.client.pay.Pay();
		tmpPay.setTransId(pay.getTransId());
		tmpPay.setType(pay.getType());
		tmpPay.setTypeDetail(pay.getTypeDetail());
		tmpPay.setResultCode(pointsRes.getResultCode());
		Trans tmpTrans=new Trans();
		tmpTrans.setId(trans.getId());
		tmpTrans.setPointBillNo(pointsRes.getPresentPointsNo());
		if(PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
			tmpTrans.setState(TranCommand.TRAN_SUCCESS);
			tmpPay.setState(PayState.POINT_PRESENT_SUCCESS);
			tmpPay.setResultDesc("积分赠送成功");
		}else{
			tmpTrans.setState(TranCommand.TRAN_FAIL);
			tmpPay.setState(PayState.POINT_PRESENT_FAIL);
			tmpPay.setResultDesc("积分赠送失败");
		}
		transActionSupport.updateByTransId(tmpTrans);
		payActionSupport.updatePay(tmpPay);
	}
	
	
	/**
	  * 方法描述：赠送积分
	  * @param: username 用户名
	  * @param: point_value 赠送积分数
	  * @return: Points(resultCode为0000表示赠送成功，否则失败)
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 9, 2013 2:14:46 PM
	  */
	private Points presentPoints(String username,String point_value){
		Points points = new Points();
		points.setAccountMarked(username);
		points.setPartnerNo(TranCommand.PARTNER_ID);
		points.setAccountMarkedType(FroadAPICommand.ACCOUNT_MARKED_TYPE);
		points.setOrgNo(TranCommand.FFT_ORG_NO);
		points.setPoints(point_value);
		points = pointActionSupport.presentPoints(points);
		logger.info("========赠送积分的结果："+points.getResultCode());
		return points;
	}
	
	
	/**
	  * 方法描述：组装签到的交易(即只赠送积分)
	  * @param: clientType 客户端类型
	  * @param: points 赠送积分数
	  * @param: User(userId,username,phone)
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 9, 2013 10:50:02 AM
	  */
	private Trans makeTrans(User user,String clientType,String points){
		List<Merchant> merchantList=merchantActionSupport.getInnerMerchant();
		if(merchantList==null||merchantList.size()==0){
			logger.error("=========内部商户的记录不存在，签到交易组装失败==========");
			return null;
		}
		Trans trans=new Trans();
		trans.setTransType(TranCommand.CHECK_IN);
		trans.setUserId(user.getUserID());
		trans.setPhone(user.getMobilephone());
		trans.setMerchantId(merchantList.get(0).getId());
		trans.setSellerId("");//只增送积分，不需要设置
		trans.setSellerChannelId("");//只增送积分，不需要设置
		trans.setCurrency("");//只增送积分，不需要设置
		trans.setCostpriceTotal("0");
		trans.setCurrencyValueAll("0");
		trans.setCurrencyValueRealAll("0");
		trans.setState(TranCommand.TRAN_PROCESSING);
		trans.setClientType(clientType);
		trans.setPayChannel("");//只增送积分，不需要设置
		trans.setPayMethod(TranCommand.POINTS_FFT);
		trans.setFftPointsValueAll(points);
		trans.setFftPointsValueRealAll(points);
		//将pay记录添加到trans对象
		trans.getPayList().add(this.makePay(user.getUsername(), points));
		return trans;
	}
	
	
	/**
	  * 方法描述：组织签到赠送积分的pay记录
	  * @param: username 用户名
	  * @param: 赠送的积分数
	  * @return: Pay
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 9, 2013 1:59:14 PM
	  */
	private Pay makePay(String username,String points){
		Pay pay=new Pay();
		pay.setState(PayState.PAY_WAIT);
		pay.setTypeDetail(TranCommand.CHECK_IN_PRESENT_POINTS);
		pay.setType(TranCommand.TYPE_POINTS);
		pay.setToUsername(username);
		pay.setPayValue(points);
		pay.setRemark("用户签到赠送积分");
		return pay;
	}

		
	public String getReno() {
		logger.info("调用结束[TaskAction]reno:" + reno + ",remsg:" + getRemsg()
				+ ",rebody:" + rebody);
		return reno;
	}

	public void setReno(String reno) {
		this.reno = reno;
	}

	public String getRemsg() {
		if(Assert.empty(remsg)){
			return FroadAPICommand.getValidateMsg(reno);
		}
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}

	public Map<String, Object> getRebody() {
		return rebody;
	}

	public void setRebody(Map<String, Object> rebody) {
		this.rebody = rebody;
	}

	public String getRecount() {
		return count;
	}

	public void setRecount(String recount) {
		this.recount = recount;
	}

	@JSON(serialize=false)
	public DailyTaskRuleActionSupport getDailyTaskRuleActionSupport() {
		return dailyTaskRuleActionSupport;
	}

	public void setDailyTaskRuleActionSupport(
			DailyTaskRuleActionSupport dailyTaskRuleActionSupport) {
		this.dailyTaskRuleActionSupport = dailyTaskRuleActionSupport;
	}
	
	@JSON(serialize=false)
	public DailyTaskActionSupport getDailyTaskActionSupport() {
		return dailyTaskActionSupport;
	}

	public void setDailyTaskActionSupport(
			DailyTaskActionSupport dailyTaskActionSupport) {
		this.dailyTaskActionSupport = dailyTaskActionSupport;
	}
	
	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	@JSON(serialize=false)
	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}
	@JSON(serialize=false)
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

	public void setPayActionSupport(PayActionSupport payActionSupport) {
		this.payActionSupport = payActionSupport;
	}
	
}
