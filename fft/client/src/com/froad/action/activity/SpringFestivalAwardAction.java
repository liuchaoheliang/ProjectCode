package com.froad.action.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.activity.SpringFestivalAwardActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.bean.LotteryTemp;
import com.froad.client.SpringFestivalCoupon.SpringFestivalCoupon;
import com.froad.client.Store.Store;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.point.PointsAccount;
import com.froad.client.user.User;
import com.froad.common.Pager;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.o2obill.dto.AwardRes;
import com.o2obill.dto.CheckLotteryReadyRes;
import com.o2obill.dto.FindPageRes;
import com.o2obill.dto.LotteryRandomReq;
import com.o2obill.dto.LotteryRandomRes;
import com.o2obill.dto.LotteryWinnerDto;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * *******************************************************
 *<p> 工程: communityBusiness_client </p>
 *<p> 类名: SpringFestivalAward.java </p>
 *<p> 描述: *-- <b>春节抽奖活动相关处理</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2013-12-13 下午02:33:48 </p>
 ********************************************************
 */
public class SpringFestivalAwardAction extends BaseActionSupport{	
	
	
	
	
	private static final long serialVersionUID = 269813323965478436L;
	
	
	
	
	//===================bean
	private Merchant merchant;
	private List<LotteryWinnerDto> lotteryWinners;
	private Pager pager;
	private String prizeJson;//派奖信息页面ajax参数
	private String loterryId;
	
	
	
	//====================support
	private MerchantActionSupport merchantActionSupport;
	private StoreActionSupport storeActionSupport;
	private SpringFestivalAwardActionSupport springFestivalAwardActionSupport;	
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private PointActionSupport pointActionSupport;
	
	private SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>跳转到抽奖活动页面</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-13 下午02:35:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public String index(){			
		lotteryWinners = springFestivalAwardActionSupport.loadWinners();
		String winnerDesc;
		String newDesc;
		int length;
		String temp ;
		for (LotteryWinnerDto winners : lotteryWinners) {
			temp ="";
			winnerDesc = winners.getWinnerDesc();
			newDesc = winnerDesc.split(" ")[0];
			length = newDesc.length();
			for(int i = 0 ; i < 4; i ++){
				temp +="*";
			}
			temp += newDesc.substring(length-3);
			newDesc = temp+" ";
			if("一等奖".equals(winners.getLotteryTerm().getName())){
				//newDesc += " 特等奖 ";
				newDesc += winners.getPrizes().getCaptions();
			}else if("二等奖".equals(winners.getLotteryTerm().getName())){
				//newDesc += " 一等奖 ";
				newDesc += winners.getPrizes().getCaptions();
			}else if("三等奖".equals(winners.getLotteryTerm().getName())){
				//newDesc += " 二等奖 ";
				newDesc += winners.getPrizes().getCaptions();
			}else if("四等奖".equals(winners.getLotteryTerm().getName())){
				//newDesc += " 三等奖 ";
				newDesc += winners.getPrizes().getCaptions();
			}else{
				//newDesc += " 四等奖 ";
				newDesc += winners.getPrizes().getCaptions();
			}	
			winners.setWinnerDesc(newDesc);
		}
		
		if(merchant == null){
			merchant = new Merchant();
		}
		
		if(Assert.empty(merchant.getMerchantPriority()) && Assert.empty(merchant.getTagClassifyAId())
				&& Assert.empty(merchant.getTagDistrictBId())){
			merchant.setTagDistrictAId("100001001");
		}
		
		merchant.setPageSize(10);
		merchant.setState(Command.FBU_USERED_STATE);
		merchant.setOrderBy("merchant_priority*1");//优先级
		merchant.setOrderType(com.froad.client.merchant.OrderType.DESC);
		merchant.setIsSpringActivity("1");
		merchant = merchantActionSupport.getMerchantListIndex(merchant);

		if(merchant == null){
			merchant = new Merchant();
		}else{
			List<Object> list=merchant.getList();
			for(int i=0;i<list.size();i++){
				Merchant m=(Merchant) list.get(i);
				List<Store> slist = storeActionSupport.getStoresByMerchantId(m.getId());
				com.froad.client.merchant.Store s ;				
				for (Store store : slist) {
					s = new com.froad.client.merchant.Store();
					s.setId(store.getId());
					s.setFullName(store.getFullName());
					s.setAddress(store.getAddress());					
					m.getStoreList().add(s);
				}
			}
		}
		
		return Action.SUCCESS;
	}

	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>开始进行抽奖并校验</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-18 下午01:20:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public void beginToLottery(){		
		
		//校验抽奖基本信息
		User user = (User)getSession(SessionKey.USER);
		Merchant merchant = (Merchant)getSession(SessionKey.MERCHANT);	
		JSONObject json = new JSONObject();	
		
		
		
		if(user == null && merchant == null){
			log.info("用户处于未登录，将转跳至登录页面");			
			json.put("result", false);
			json.put("code", "to_login");			
			ajaxJson(json.toString());
			return;
		}else{			
			//已确定用户处于登录状态，将判断用户身份					
			if(merchant != null){//登录用户为商户
				json.put("result", false);
				json.put("code", "common");
				json.put("msg", "抱歉，商户不能参与该活动，即将带您回到首页。");
				ajaxJson(json.toString());
				return;
			}else{
				
				//开始抽奖				
				//========开始验证抽奖基本条件
				CheckLotteryReadyRes res = springFestivalAwardActionSupport.checkLotteryReadyRes(user);
				if(res.getErrCode() == null || res.getErrCode().length() == 0){
					json.put("result", true);					
				}else{					
					if("5001".equals(res.getErrCode())){
						json.put("msg", "对不起，你的可用积分不足");
					}else{
						json.put("msg", "活动暂未开始，尽请期待");
					}
					json.put("result", false);
					json.put("code", "info");
					ajaxJson(json.toString());
					return;
				}
				//========校验完成
				
				
				//====开始进行抽奖模块				
				LotteryRandomReq lotterRandomReq = new LotteryRandomReq(user.getUsername(), user.getMemberCode()+"",null);
				LotteryRandomRes lotteryRandomRes = springFestivalAwardActionSupport.lotteryRandom(lotterRandomReq);
				
				
				
				//抽奖模块完毕
				if(lotteryRandomRes.getErrCode() != null){					
					String errorCode = lotteryRandomRes.getErrCode();
					log.info("抽奖异常==============>>>code:"+lotteryRandomRes.getErrCode() + "|msg:"+lotteryRandomRes.getErrMsg());					
					if("5001".equals(errorCode)){
						json.put("msg", "对不起，您的可用积分不足");
					}else if("2007".equals(errorCode)){
						json.put("msg", "今天的抽奖次数已经使用完毕，请明天再来");
					}else if("2003".equals(errorCode)){
						json.put("msg", "抱歉，活动已结束，谢谢参与");
					}else if("2002".equals(errorCode)){
						json.put("msg", "活动暂未开始，敬请期待");
					}else if("2010".equals(errorCode)){
						json.put("isGot", false);
						ajaxJson(json.toString());
						return;
					}else if("2009".equals(errorCode)){
						json.put("isGot", false);
						ajaxJson(json.toString());
						return;
					}else{
						json.put("msg", "抱歉，系统繁忙，请稍候再试");
					}					
					json.put("result", false);
					json.put("code", "info");					
					ajaxJson(json.toString());
					return;
				}
				
				
				
				
				//开始验证抽奖信息
				if(lotteryRandomRes.getLotteryWinnerDto() == null){ // 未中奖
					json.put("isGot", false);//是否中奖
					//重新获取积分信息
					Map<String,PointsAccount> pointsTypePointsAccountMap = pointActionSupport.queryPointsByUsername(user.getUsername());
					setSession(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
					PointsAccount pointaccount = null;
					PointsAccount pointaccountBank = null;
					pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
					pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
					json.put("fftPoints", pointaccount==null || pointaccount.getPoints()==null?"0":pointaccount.getPoints());	
					json.put("bankPoints", pointaccountBank==null || pointaccountBank.getPoints()==null?"0":pointaccountBank.getPoints());
				}else{
					
					//重新获取积分信息
					Map<String,PointsAccount> pointsTypePointsAccountMap = pointActionSupport.queryPointsByUsername(user.getUsername());
					setSession(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
					PointsAccount pointaccount = null;
					PointsAccount pointaccountBank = null;
					pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
					pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
					json.put("fftPoints", pointaccount==null || pointaccount.getPoints()==null?"0":pointaccount.getPoints());	
					json.put("bankPoints", pointaccountBank==null || pointaccountBank.getPoints()==null?"0":pointaccountBank.getPoints());
					
					
					json.put("isGot", true);//是否中奖
					String info;
					LotteryWinnerDto lwd = lotteryRandomRes.getLotteryWinnerDto();
					String level;
					int stopNum = 6;
					if("一等奖".equals(lwd.getLotteryTerm().getName())){
						 level = "特等奖" ;
						 stopNum = 1;
					}else if("二等奖".equals(lwd.getLotteryTerm().getName())){
						 level = "一等奖" ;
						 stopNum = 2;
					}else if("三等奖".equals(lwd.getLotteryTerm().getName())){
						 level = "二等奖" ;
						 stopNum = 3;
					}else if("四等奖".equals(lwd.getLotteryTerm().getName())){
						 level = "三等奖" ;
						 stopNum = 4;
					}else{
						level = "四等奖";
						stopNum = 5;
					}					
					
					info = lwd.getPrizes().getCaptions();
					json.put("stopNum", stopNum);					
					json.put("msg","恭喜您中得"+level+"： "+info+"");
					json.put("winnerid",lwd.getId());					
					log.info("中奖信息|"+user.getMemberCode()+"|帐号："+user.getUsername() +"|奖级："+level+"|奖品信息："+info + "|winnerid：" + lwd.getId());
				}			
				
				ajaxJson(json.toString());
			}
		}	
	} 	
	
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>派奖ajax</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-20 下午05:16:52 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public void aldoParisotPrize(){
		User u = (User)getSession(SessionKey.USER);
		JSONArray json = JSONArray.fromObject(prizeJson);
		Map<String, String> jsonData = (Map<String, String>) json.get(0);
		if(u == null){
			log.info("登录信息失效！");
			return;
		}
		if(jsonData.get("winnerid") == null || jsonData.get("winnerid").length() == 0){
			log.info("数据异常");
			return;
		}
		
		log.info("=====================开始派奖======>>memberCode：" + u.getMemberCode()+"|prizeJson：" + prizeJson);
		LotteryWinnerDto dto = springFestivalAwardActionSupport.getWinnerBy(jsonData.get("winnerid"));
		if(dto==null||dto.getId()==null||dto.getId().longValue()<1){
			log.info("传入winnerId:"+jsonData.get("winnerid")+"无中奖信息");
			return;
		}
		
		JSONObject jsonRes = new JSONObject();
		
		if("t".equals(jsonData.get("prizeLevel"))){
			//特等奖
			dto.setWinnerTel(jsonData.get("phone"));
			dto.setConsignee(jsonData.get("uname"));
			//dto.setWinnerPostCardNo(jsonData.get("idcard"));
			dto.setAddress(jsonData.get("idcard"));//原身份证信息已代表地址信息
		}else{
			//
			log.info("传入接收中奖短信号码"+u.getMobilephone());
			dto.setWinnerTel(u.getMobilephone());
		}
		
		//其他奖项
		
		AwardRes res = springFestivalAwardActionSupport.award(dto);
		if("abandoned".equals(res.getLotteryWinnerDto().getLotteryLimit().getStatus().name())){
			jsonRes.put("restlt", false);
			jsonRes.put("msg", "对不起，该条中奖信息已过期");
			ajaxJson(jsonRes.toString());
			log.info("派奖异常 派奖数据为已失效(活动已作废)");
			return ;	
		}
		
		if((res.getErrCode() == null || res.getErrCode().length() == 0)&&(res.getErrMsg() == null || res.getErrCode().length() == 0)){
			//派奖完成
			jsonRes.put("restlt", true);
			
			if(!"t".equals(jsonData.get("prizeLevel")) && !"4".equals(jsonData.get("prizeLevel"))){
				//开始记录券号信息于fft库
				SpringFestivalCoupon sfc = new SpringFestivalCoupon();
				String securitiesNo = res.getLotteryWinnerDto().getVirtualCard().getRedemptionCodeDecrypt();
				log.info("返回券号为：" + securitiesNo);
				sfc.setSecuritiesNo(securitiesNo);
				sfc.setIsGot("1");
				sfc.setO2OInfoId(jsonData.get("winnerid"));
				sfc.setMemberCode(u.getMemberCode()+"");
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				sfc.setGotTime(sdf.format(new Date()));
				String flag = springFestivalAwardActionSupport.throughSecuritiesUpdate(sfc);
				if("notexist".equals(flag)){
					log.info("券号不存在");
				}else if("error".equals(flag)){
					log.info("==============================>>>更改本地库异常|券号为："+securitiesNo);
				}else{
					log.info("==============================>>>更改本地库成功|券号为："+securitiesNo);
				}
			}			
			
			jsonRes.put("msg", "恭喜领奖成功，请等待发奖，详情请至：个人中心-我的活动 查看");
			ajaxJson(jsonRes.toString());
			log.info("派奖完成");
			return ;
		}else{
			jsonRes.put("restlt", false);
			jsonRes.put("msg", "对不起，系统繁忙，请至：个人中心-我的活动 领奖");
			ajaxJson(jsonRes.toString());
			log.info("派奖异常 errorCode:"+res.getErrCode()+"|errorMsg:"+res.getErrMsg());
			return ;			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//======================================================================
	
	
	/**
	  * 方法描述：根据用户membersCode查询用户的中奖信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-20 下午03:49:09
	  */
	public String  getMyLottery(){
		User user = (User)getSession(SessionKey.USER);
		if(user==null){
			log.info("用户处于未登录");
			return "false";
		}
		if(pager==null){
			pager=new Pager();
		}
		pager.setPageSize(10);
		FindPageRes pageRes=new FindPageRes();
		pageRes=springFestivalAwardActionSupport.findPage(user.getMemberCode().toString(), pager.getPageNumber(), pager.getPageSize());
		lotteryWinners=pageRes.getWinnerList();
		List<LotteryTemp> lotteryTemps=new ArrayList<LotteryTemp>();
		SpringFestivalCoupon sfc = new SpringFestivalCoupon();
		for(LotteryWinnerDto list:lotteryWinners){
			LotteryTemp temp=new LotteryTemp();
			temp.setLotteryWinnerDtoId(list.getId().toString());       //设置中奖人信息记录的Id号
			temp.setLotteryName(list.getLottery().getLotteryName());   //设置活动名称
			temp.setAwardInfo(list.getPrizes().getCaptions());      //设置奖品名称
			temp.setLotteryTerm(dealLotteryTerm(list.getLotteryTerm().getName()));   //设置奖品等级
			temp.setLotteryWinnerDto(list);      //设置中奖人信息
//			temp.setRedemptionCode(list.getVirtualCard().getRedemptionCode());     //设置券号
			temp.setState(list.getAwardStatus().name());      //设置状态
			temp.setStartTime(df.format(list.getLottery().getStartDate()));    //设置活动开始时间
			temp.setEndTime(df.format(list.getLottery().getEndDate()));       //设置活动结束时间
			if("award".equals(temp.getState()) &&"一等奖".equals(temp.getLotteryTerm())){
				//根据返回的券号查询券记录
				sfc=springFestivalAwardActionSupport.getSFCBySecuritiesNo(list.getVirtualCard().getRedemptionCodeDecrypt());
				temp.setIsConsume(sfc.getIsConsume());
			}
			lotteryTemps.add(temp);
			
		}
		pager.setTotalCount(Integer.parseInt(pageRes.getTotal()+""));
		pager.setList(lotteryTemps);
		
		return SUCCESS;
	}
	
	
	
	/**
	  * 方法描述：春节活动抽奖优惠券认证
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-23 上午10:30:20
	  */
	public void authenticationForSpringFestival(){
		log.info("验证活动认证开始：");
		JSONObject json = new JSONObject();
		Map<String,Object> session=ActionContext.getContext().getSession();
		String securitiesNo=getParameter("securitiesNo");
		log.info("查询兑换券的券号："+securitiesNo);
		SpringFestivalCoupon sfc=springFestivalAwardActionSupport.getSFCBySecuritiesNo(securitiesNo);
		if(sfc != null && sfc.getId()!=null && "1".equals(sfc.getIsGot())){
			if( "0".equals(sfc.getIsConsume()) ){
				Date now=new Date();
				Date endTime=null;
				try {
					endTime = df.parse(sfc.getExpireTime());
				} catch (ParseException e) {
					log.info("转换时间发生异常");
				}
				if(now.before(endTime)){
					//查询该操作员信息
					Merchant merchant=(Merchant)session.get(SessionKey.MERCHANT);
					MerchantUserSet merchantUserSet=new MerchantUserSet();
					merchantUserSet.setMerchantId(merchant.getId().toString());
					merchantUserSet.setBeCode(getSession("beCode").toString());
					log.info("操作员工号："+getSession("beCode").toString());
					MerchantUserSet mlist=merchantUserSetActionSupport.getMerchantUserSetListByPager(merchantUserSet);
					//认证成功，修改信息
					mlist=(MerchantUserSet) mlist.getList().get(0);
					sfc.setMerchantId(mlist.getMerchantId());
					sfc.setBeName(mlist.getBeName());
					sfc.setBeCode(mlist.getBeCode());
					sfc.setIsConsume("1");
					sfc.setConsumeTime(now.toString());
					sfc.setUpdateTime(now.toString());
					springFestivalAwardActionSupport.throughSecuritiesUpdate(sfc);
					log.info("验证活动认证结束");
					json.put("reno",Command.respCode_SUCCESS);
					String msg=dealLotteryInfo(sfc.getAwardLevel());
					json.put("message","兑换码验证成功！("+msg+")");
				}else{
					json.put("reno",Command.respCode_FAIL);
					json.put("message","验证失败！兑换码已经过期！");
				}
			}else{
				json.put("reno",Command.respCode_FAIL);
				json.put("message","验证失败！兑换码已被验证！");
			}			
		}else{
			json.put("reno",Command.respCode_FAIL);
			json.put("message","验证失败！兑换码无效！");
		}
		ajaxJson(json.toString());
	}
	
	
	/**
	  * 方法描述：根据LoterryId查询中奖信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-27 下午06:34:32
	  */
	public void getDetailByLoterryId(){
		LotteryWinnerDto lotteryWinnerDto=springFestivalAwardActionSupport.getWinnerBy(loterryId);
		JSONObject json = new JSONObject();
		json.put("phone", lotteryWinnerDto.getWinnerTel() == null?"":lotteryWinnerDto.getWinnerTel());
		json.put("idcard", lotteryWinnerDto.getWinnerPostCardNo()== null?"":lotteryWinnerDto.getWinnerPostCardNo());
		json.put("uname", lotteryWinnerDto.getConsignee()== null?"":lotteryWinnerDto.getConsignee());
		log.info("查询特等奖用户信息："+json.toString());
		ajaxJson(json.toString());
	}
	
	
	/**
	  * 方法描述：转换o2o平台奖级为本地奖级
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-25 下午05:04:54
	  */
	public String dealLotteryTerm(String LotteryTerm){
		if("一等奖".equals(LotteryTerm)){
			 return  "特等奖" ;
		}else if("二等奖".equals(LotteryTerm)){
			 return "一等奖" ;
		}else if("三等奖".equals(LotteryTerm)){
			 return "二等奖" ;
		}else if("四等奖".equals(LotteryTerm)){
			 return "三等奖" ;
		}else{
			 return "四等奖";
		}	
	}
	
	
	/**
	  * 方法描述：根据本地库奖级转换奖品名称
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-25 下午05:04:09
	  */
	public String dealLotteryInfo(int LotteryTerm){		
		if(1==LotteryTerm){
			return "电影票";
		}else if(2==LotteryTerm){
			return "10元优惠券";
		}else if(3==LotteryTerm){
			return "5元优惠券";
		}else{
			return "未知";
		}
	}
	
	
	
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}
	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}
	public SpringFestivalAwardActionSupport getSpringFestivalAwardActionSupport() {
		return springFestivalAwardActionSupport;
	}
	public void setSpringFestivalAwardActionSupport(
			SpringFestivalAwardActionSupport springFestivalAwardActionSupport) {
		this.springFestivalAwardActionSupport = springFestivalAwardActionSupport;
	}
	public List<LotteryWinnerDto> getLotteryWinners() {
		return lotteryWinners;
	}
	public void setLotteryWinners(List<LotteryWinnerDto> lotteryWinners) {
		this.lotteryWinners = lotteryWinners;
	}
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public String getPrizeJson() {
		return prizeJson;
	}
	public void setPrizeJson(String prizeJson) {
		this.prizeJson = prizeJson;
	}
	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}
	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}
	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}
	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public String getLoterryId() {
		return loterryId;
	}
	public void setLoterryId(String loterryId) {
		this.loterryId = loterryId;
	}
	
}
