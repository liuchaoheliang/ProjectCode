package com.froad.fft.controller.shop.activity.o2oraffle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.LotteryTemp;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.bean.page.OrderDto.Direction;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.thirdparty.o2oraffle.impl.O2ORaffleSupportImpl;
import com.google.common.collect.Sets;
import com.o2obill.dto.AwardRes;
import com.o2obill.dto.CheckLotteryReadyRes;
import com.o2obill.dto.FindPageRes;
import com.o2obill.dto.LotteryRandomReq;
import com.o2obill.dto.LotteryRandomRes;
import com.o2obill.dto.LotteryWinnerDto;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: O2ORaffleController.java </p>
 *<p> 描述: *-- <b>O2O抽奖活动</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月25日 下午2:34:01 </p>
 ********************************************************
 */
@Controller("activity.o2oraffle")
@RequestMapping("/shop/activity/o2oraffle")
public class O2ORaffleController extends BaseController{
	
	@Resource
	private O2ORaffleSupportImpl o2ORaffleSupportImpl;
	

	@RequestMapping("index")
	public void index(){}
	
	
	/**
	  * 方法描述：用户抽奖（验证是否具有抽奖资格，然后进行抽奖）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年5月5日 上午9:26:38
	  */
	@RequestMapping(value = "lotteryRandom", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean lotteryRandom(HttpServletRequest req){
		//获取当前登录用户信息
		Object obj=getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		UserEngineDto user=null;
		if(obj!=null){
			user=(UserEngineDto) obj;
		}
		//检验抽奖活动是否可用
		CheckLotteryReadyRes res=o2ORaffleSupportImpl.checkLotteryReadyRes(user.getLoginID(), user.getMemberCode());
		if(res.getErrCode() == null || res.getErrCode().length() == 0){
			//抽奖活动可用，则进行抽奖
			LotteryRandomReq lotterRandomReq = new LotteryRandomReq(user.getLoginID(), user.getMemberCode()+"",null);
			LotteryRandomRes lotteryRandomRes = o2ORaffleSupportImpl.lotteryRandom(lotterRandomReq);
			
			//抽奖模块完毕
			if(lotteryRandomRes.getErrCode() != null){					
				String errorCode = lotteryRandomRes.getErrCode();
				logger.info("抽奖异常==============>>>code:"+lotteryRandomRes.getErrCode() + "|msg:"+lotteryRandomRes.getErrMsg());					
				if("5001".equals(errorCode)){
					return	new AjaxCallBackBean(false, "对不起，您的可用积分不足");
				}else if("2007".equals(errorCode)){
					return	new AjaxCallBackBean(false, "今天的抽奖次数已经使用完毕，请明天再来");
				}else if("2003".equals(errorCode)){
					return	new AjaxCallBackBean(false, "抱歉，活动已结束，谢谢参与");
				}else if("2002".equals(errorCode)){
					return	new AjaxCallBackBean(false, "活动暂未开始，敬请期待");
				}else if("2010".equals(errorCode)){
					return	new AjaxCallBackBean(false, "对不起，你的可用积分不足");
				}else if("2009".equals(errorCode)){
					return	new AjaxCallBackBean(false, "本次未能中奖，继续再接再厉！");
				}else{
					return	new AjaxCallBackBean(false, "抱歉，系统繁忙，请稍候再试");
				}					
			}
	
			//抽奖结果
			JSONObject json = new JSONObject();	
			
			if(lotteryRandomRes.getLotteryWinnerDto() == null){ // 未中奖
				//重新获取积分信息
				updateUserAllInfo(req);
				int stopNum = 8;
				json.put("stopNum", stopNum);	
				return	new AjaxCallBackBean(true, "本次未能中奖，继续再接再厉！",json);
			}else{
				//重新获取积分信息
				updateUserAllInfo(req);
				String info;
				LotteryWinnerDto lwd = lotteryRandomRes.getLotteryWinnerDto();
				String level;
				//谢谢额参与
				int stopNum = 8;
				if("一等奖".equals(lwd.getLotteryTerm().getName())){
					 stopNum = 2;
				}else if("二等奖".equals(lwd.getLotteryTerm().getName())){
					int[] num={5,7};
					int stop=new Random().nextInt(2);
					 stopNum = num[stop];
				}else if("三等奖".equals(lwd.getLotteryTerm().getName())){
					int[] num={3,9,10}; 
					int stop=new Random().nextInt(3);
					 stopNum = num[stop];
				}else if("四等奖".equals(lwd.getLotteryTerm().getName())){
					int[] num={1,4,6}; 
					int stop=new Random().nextInt(3);
					 stopNum = num[stop];
				}
				info = lwd.getPrizes().getCaptions();
				level=lwd.getLotteryTerm().getName();
				json.put("stopNum", stopNum);					
				json.put("winnerid",lwd.getId());	
				logger.info("中奖信息|"+user.getMemberCode()+"|帐号："+user.getLoginID() +"|奖级："+level+"|奖品信息："+info + "|winnerid：" + lwd.getId());
				return	new AjaxCallBackBean(true, "恭喜您中得"+level+"： "+info,json);
			}			
		}else{
			if("5001".equals(res.getErrCode())){
				return	new AjaxCallBackBean(false, "对不起，你的可用积分不足");
			}else{
				return	new AjaxCallBackBean(false, "活动暂未开始，尽请期待");
			}
		}
	} 
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>派奖ajax</b> --* </p>
	 *<p> 作者: 刘超 </p>
	 *<p> 时间: 2014-05-05 下午05:16:52 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "aldoParisotPrize", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean aldoParisotPrize(HttpServletRequest req,String winnerid){
		Object obj=getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		UserEngineDto user=null;
		if(obj==null){
			logger.info("登录信息失效");
			return new AjaxCallBackBean(false, "登录信息失效");
		}
		user=(UserEngineDto) obj;
		winnerid=req.getParameter("winnerid");
		if("".equals(winnerid)){
			logger.info("数据异常");
			return new AjaxCallBackBean(false, "数据异常");
		}
		logger.info("=====================开始派奖======>>memberCode：" + user.getMemberCode()+"winnerid|：" + winnerid);
		LotteryWinnerDto dto = o2ORaffleSupportImpl.getWinnerByWinnerID(winnerid);
		
		if(dto==null||dto.getId()==null||dto.getId().longValue()<1){
			logger.info("传入winnerId:"+winnerid+"无中奖信息");
			return new AjaxCallBackBean(false, "数据异常,中奖信息不存在");
		}
		AwardRes res=  o2ORaffleSupportImpl.award(dto);
		
		if("abandoned".equals(res.getLotteryWinnerDto().getLotteryLimit().getStatus().name())){
			logger.info("派奖异常 派奖数据为已失效(活动已作废)");
			return new AjaxCallBackBean(false, "对不起，该条中奖信息已过期");	
		}
		
		if((res.getErrCode() == null || res.getErrCode().length() == 0)&&(res.getErrMsg() == null || res.getErrCode().length() == 0)){
			logger.info("派奖完成");
			//重新获取积分信息
			updateUserAllInfo(req);
			return new AjaxCallBackBean(true, "恭喜领奖成功，请等待发奖，详情请至：个人中心-我的抽奖 查看");
		}else{
			logger.info("派奖异常 errorCode:"+res.getErrCode()+"|errorMsg:"+res.getErrMsg());
			return new AjaxCallBackBean(false, "对不起，系统繁忙，请至：个人中心-我的抽奖 领奖");
		}
	}
	
	
	
	
	/**
	  * 方法描述：加载中奖名单
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年5月5日 上午11:38:06
	  */
	@RequestMapping(value = "loadWinners", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean loadWinners(){
		 List<LotteryWinnerDto> list=o2ORaffleSupportImpl.loadWinners();
		 JSONArray array=new JSONArray();
		 array.addAll(list);
		 return new AjaxCallBackBean(true, "", array);
	}
	
	
	/**
	  * 方法描述：加载获取抽奖次数
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年5月5日 下午2:48:20
	  */
	@RequestMapping(value = "loadLotteryTimes", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean loadLotteryTimes(HttpServletRequest req){
		Object obj=getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		UserEngineDto user=null;
		if(obj==null){
			//没有登录则默认为0
			return new AjaxCallBackBean(true, "", "0");
		}else{
			user=(UserEngineDto) obj;
			CheckLotteryReadyRes res=o2ORaffleSupportImpl.checkLotteryReadyRes(user.getLoginID(), user.getMemberCode());
			String times="";
			if("-1".equals(res.getLesCount())){
				//无限次数
				times="*";
			}else{
				times=res.getLesCount();
			}
			return new AjaxCallBackBean(true, "", times);
		}
	}
	
}
