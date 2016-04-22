package com.froad.action.support.activity;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.client.SpringFestivalCoupon.SpringFestivalCoupon;
import com.froad.client.SpringFestivalCoupon.SpringFestivalCouponService;
import com.froad.client.user.User;
import com.froad.util.Command;
import com.o2obill.api.service.ActiveService;
import com.o2obill.dto.AwardRes;
import com.o2obill.dto.CheckLotteryReadyReq;
import com.o2obill.dto.CheckLotteryReadyRes;
import com.o2obill.dto.CouponReq;
import com.o2obill.dto.CouponRes;
import com.o2obill.dto.FindPageRes;
import com.o2obill.dto.LoadLotterysReq;
import com.o2obill.dto.LoadLotterysRes;
import com.o2obill.dto.LotteryDto;
import com.o2obill.dto.LotteryRandomReq;
import com.o2obill.dto.LotteryRandomRes;
import com.o2obill.dto.LotteryWinnerDto;
import com.o2obill.enums.IssueType;

public class SpringFestivalAwardActionSupport {

	private static HessianProxyFactory factory = new HessianProxyFactory();	
	private static Logger log = Logger.getLogger(SpringFestivalAwardActionSupport.class);
	
	
	
	private SpringFestivalCouponService springFestivalCouponService;
	
	static String o2oActiveServiceURL = Command.O2OSERVER_URL;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>公共调取方法</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-19 下午05:11:17 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private static ActiveService getO2oServer(){
		try {
			ActiveService activeServic = (ActiveService)factory.create(ActiveService.class,o2oActiveServiceURL);
			return activeServic;
		} catch (MalformedURLException e) {
			log.error("hessian 调用 o2o 平台 url:" + o2oActiveServiceURL + "发生异常", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		ActiveService server = getO2oServer();
//		server.findPage("50000033144",1,10);
//		CheckLotteryReadyReq crr = new CheckLotteryReadyReq("admin", "50000033144", "41", "0");
//		CheckLotteryReadyRes res = server.checkLotteryReady(crr);
//		System.out.println(JSONObject.fromObject(res).toString());
		AwardRes res = server.award(server.getWinnerBy("1525"));
		LoadLotterysReq req = new LoadLotterysReq();
//		req.setIssueType(IssueType.FFT);
//		server.loadLotterysBy(req);
		
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>检查抽奖活动</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-19 上午10:58:54 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public CheckLotteryReadyRes checkLotteryReadyRes(User u){
		ActiveService server = getO2oServer();
		CheckLotteryReadyReq req = new CheckLotteryReadyReq(u.getUsername(), u.getMemberCode()+"", searchLotterId(), null);
		CheckLotteryReadyRes res = server.checkLotteryReady(req);
		log.info("o2o 接口checkLotteryReady 返回数据： "+ JSONObject.fromObject(res).toString());
		return res;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取所有中奖用户滚动列表</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-19 下午01:52:36 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<LotteryWinnerDto> loadWinners(){
		ActiveService server = getO2oServer();		
		return server.loadWinners(searchLotterId());
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>开始抽奖</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-19 下午02:13:05 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public LotteryRandomRes lotteryRandom(LotteryRandomReq req){
		ActiveService server = getO2oServer();
		req.setLotteryId(searchLotterId());//设置获取的活动ID
		LotteryRandomRes res = server.lotteryRandom(req);
		log.info("o2o 接口lotteryRandom 返回数据： "+ JSONObject.fromObject(res).toString());
		return res;
	}

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过中奖ID查询中奖人信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-22 下午08:07:08 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public LotteryWinnerDto getWinnerBy(String winnerId){
		ActiveService server = getO2oServer();
		LotteryWinnerDto dto = server.getWinnerBy(winnerId);
		log.info("o2o 接口getWinnerBy 返回数据： "+ JSONObject.fromObject(dto).toString());
		return dto;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过mermberCode查询所有中奖信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-20 下午03:21:31 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	 public FindPageRes findPage(String memberCode,int pageNumber,int pageSize){
		ActiveService server = getO2oServer();
		return server.findPage(IssueType.FFT,memberCode, pageNumber, pageSize);
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>根据券号修改券相关信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-19 下午05:22:00 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public String throughSecuritiesUpdate(SpringFestivalCoupon sfc){
		SpringFestivalCoupon condition = new SpringFestivalCoupon();
		condition.setSecuritiesNo(sfc.getSecuritiesNo());
		List<SpringFestivalCoupon> sfcs= springFestivalCouponService.getSpringFestivalCouponByCndition(condition);
		if(sfcs == null || sfcs.size() == 0){
			return "notexist";//券号不存在
		}else{
			sfc.setId(sfcs.get(0).getId());
			if(springFestivalCouponService.updateSpringFestivalCouponById(sfc)){
				return "success";
			}else{
				log.info("通过券号"+sfc.getSecuritiesNo() + "修改券相关信息失败");
				return "error";
			}
		}
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>派奖</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-22 下午02:16:27 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public AwardRes award(LotteryWinnerDto dto){		
		ActiveService server = getO2oServer();
		AwardRes awardRes = server.award(dto);
		log.info("开始派奖|O2O接口：award|传入参数"+JSONObject.fromObject(dto).toString()+"|返回参数："+JSONObject.fromObject(awardRes).toString());
		return awardRes;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询指定用户所有优惠劵</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-22 下午02:20:59 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public CouponRes loadCouponsBy(CouponReq req){
		ActiveService server = getO2oServer();
		CouponRes res = server.loadCouponsBy(req);
		log.info("O2O接口：loadCouponsBy|传入参数"+JSONObject.fromObject(req).toString()+"|返回参数："+JSONObject.fromObject(res).toString());
		return res;
	}
	
	private String searchLotterId(){
		ActiveService server = getO2oServer();
		LoadLotterysReq req = new LoadLotterysReq();
		req.setIssueType(IssueType.FFT);
		LoadLotterysRes res = server.loadLotterysBy(req);
		List<LotteryDto> list = res.getLotteryList();
		if(list == null || list.size() == 0){
			log.error("================================>>>>>  O2O没有 FFT可用的活动列表");
			return null;
		}else{
			//TODO:暂时获取第0个			
			List<LotteryDto> ld = new ArrayList<LotteryDto>();
			String state;
			for (int i=0 ; i < list.size() ; i++) {				
				state = list.get(i).getStatus().toString();
				if("effect".equals(state)){
					ld.add(list.get(i));
				}
			}
			
			if(ld.size() == 0){
				log.error("================================>>>>>  O2O没有 FFT可用的活动列表");
				return null;
			}
			log.info("活动id为："+ld.get(0).getId());
			return ld.get(0).getId()+"";
		}
	}
	
	 
	
	/**
	  * 方法描述：根据券号查询券的信息
	  * @param: SecuritiesNo
	  * @return: SpringFestivalCoupon
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-12-22 上午10:52:06
	  */
	public SpringFestivalCoupon getSFCBySecuritiesNo(String SecuritiesNo){
		SpringFestivalCoupon condition = new SpringFestivalCoupon();
		condition.setSecuritiesNo(SecuritiesNo);
		List<SpringFestivalCoupon> sfcs= springFestivalCouponService.getSpringFestivalCouponByCndition(condition);
		if(sfcs == null || sfcs.size() == 0){
			log.info("券信息不存在，SecuritiesNo："+SecuritiesNo);
			return new SpringFestivalCoupon();
		}else{
			return sfcs.get(0);
		}
	}
	
	
	public SpringFestivalCouponService getSpringFestivalCouponService() {
		return springFestivalCouponService;
	}

	public void setSpringFestivalCouponService(
			SpringFestivalCouponService springFestivalCouponService) {
		this.springFestivalCouponService = springFestivalCouponService;
	}
	
	
	
	
}
