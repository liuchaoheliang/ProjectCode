
	 /**
  * 文件名：ActivityListController.java
  * 版本信息：Version 1.0
  * 日期：2014年5月5日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.controller.member.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.bean.LotteryTemp;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.bean.page.OrderDto.Direction;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.thirdparty.o2oraffle.impl.O2ORaffleSupportImpl;
import com.o2obill.dto.FindPageRes;
import com.o2obill.dto.LotteryWinnerDto;


	/**
 * 类描述：用户我的抽奖活动
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年5月5日 下午12:36:10 
 */
@Controller("member.activity")
@RequestMapping("/member/activity/")
public class ActivityListController extends BaseController {

	@Resource
	private O2ORaffleSupportImpl o2ORaffleSupportImpl;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
	
	/**
	  * 方法描述：根据用户membersCode查询用户的中奖信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-20 下午03:49:09
	  */
	@RequestMapping(value = "list")
	public String  getMyLottery(HttpServletRequest req,ModelMap model,PageDto page){
		Object obj=getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		UserEngineDto user=null;
		if(obj==null){
			logger.info("登录信息失效");
			return ILLEGALITY;
		}
		user=(UserEngineDto) obj;
		if(page==null){
			page=new PageDto();
		}
		if(page.getPageFilterDto()==null){
			PageFilterDto pageFilterDto=new PageFilterDto();
			pageFilterDto.setStartTime(new Date());
			pageFilterDto.setEndTime(new Date());
			page.setPageFilterDto(pageFilterDto);
		}
		page.setPageSize(10);
		
		FindPageRes pageRes=new FindPageRes();
		pageRes=o2ORaffleSupportImpl.findBypage(user.getMemberCode().toString(), page.getPageNumber(), page.getPageSize());
		List<LotteryWinnerDto> lotteryWinners=new ArrayList<LotteryWinnerDto>();
		lotteryWinners=pageRes.getWinnerList();
		List<LotteryTemp> lotteryTemps=new ArrayList<LotteryTemp>();
		for(LotteryWinnerDto list:lotteryWinners){
			LotteryTemp temp=new LotteryTemp();
			temp.setLotteryWinnerDtoId(list.getId().toString());       //设置中奖人信息记录的Id号
			temp.setLotteryName(list.getLottery().getLotteryName());   //设置活动名称
			temp.setAwardInfo(list.getPrizes().getCaptions());      //设置奖品名称
			temp.setLotteryTerm(list.getLotteryTerm().getName());   //设置奖品等级
			temp.setLotteryWinnerDto(list);      //设置中奖人信息   
			temp.setState(list.getAwardStatus().name());      //设置状态
			temp.setStartTime(sdf.format(list.getLottery().getStartDate()));    //设置活动开始时间
			temp.setEndTime(sdf.format(list.getLottery().getEndDate()));       //设置活动结束时间
			lotteryTemps.add(temp);
		}
		page.setTotalCount(Integer.parseInt(pageRes.getTotal()+""));
		page.setResultsContent(lotteryTemps);	
		
		// 设置总页数
		int pageCount = page.getTotalCount() % page.getPageSize() == 0 ? page.getTotalCount() / page.getPageSize(): page.getTotalCount() / page.getPageSize() + 1;
		page.setPageCount(pageCount);
		model.put("page", page);
		return "/member/activity/list";
	}
	
}
