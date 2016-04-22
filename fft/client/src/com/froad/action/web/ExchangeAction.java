package com.froad.action.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.action.support.*;
import com.froad.action.support.trans.ExchangeActionSupport;
import com.froad.client.PresentPointRule.PresentPointRule;
import net.sf.json.JSONObject;

import com.froad.baseAction.BaseActionSupport;
import com.froad.bean.ExchangeTempBean;
import com.froad.client.advert.Advert;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.lottery.Lottery;
import com.froad.client.tag.TagClassifyA;
import com.froad.client.tag.TagDistrictB;
import com.froad.client.trans.Trans;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.command.GoodsCommand;
import com.froad.util.command.MallCommand;
import com.froad.util.command.PayCommand;
import com.opensymphony.xwork2.Action;

/** 
 * @author FQ 
 * @date 2013-4-5 下午02:48:00
 * @version 1.0
 * 积分兑换
 */
public class ExchangeAction extends BaseActionSupport {
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	private TagActionSupport tagActionSupport;
	private AdvertActionSupport advertActionSupport;//广告
	private PointObtainActionSupport pointObtainActionSupport;//
    private GoodsPreSellRackActionSupport goodsPreSellRackActionSupport;
    private ExchangeActionSupport exchangeActionSupport;
    private TransActionSupport transActionSupport;

	private GoodsExchangeRack pager=new GoodsExchangeRack();
	private List<TagDistrictB> allTagDistrictBList;//所有二级商圈
	private List<TagClassifyA> allTagClassifyAList;//所有一级分类
	private List<Advert> advertList;//广告
	
	private GoodsExchangeRack goodsExchangeRack;//积分兑换商品
	private ExchangeTempBean exchangeTempBean;//临时BEAN
	/*****交易完成后传递到页面上的对象***/
	private Trans trans;
	private User user;
	private String errorMsg;
	private String payResult;
    private PresentPointRule pointRule;


	//列表
	public String index(){
		log.info("积分兑换列表");
		
		publicAdvertAndTag();
		
		pager.setState("30");
		pager.setIsRack(GoodsCommand.IS_RACK_YES);
		pager.setPageSize(9);
        pager.setOrderBy(" priority ");
        pager.setOrderType(com.froad.client.goodsExchangeRack.OrderType.DESC);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
		return "new_exchange_list";
	}

	//本地商品列表
	public String localGoodsRack(){
		log.info("积分兑换本地商品列表");
		
		publicAdvertAndTag();
		
		pager.setGoodsCategoryId("100001009");//本地商品分类
		pager.setState("30");
		pager.setIsRack(GoodsCommand.IS_RACK_YES);
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
		return "local_exchange_list";
	}
	
	//本地商品详情
	public String localGoodsRackInfo(){
		log.info("本地商品ID:"+id);
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(id);
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date   serverTime = goodsPreSellRackActionSupport.getServerDate();
        //装载送积分信息
        pointRule = new PresentPointRule();
        pointRule.setGoodExchangeRackId(String.valueOf(goodsExchangeRack.getId()));
        pointRule.setState( 1);
        List<PresentPointRule>  list = goodsExchangeRackActionSupport.getPresentPointRule(pointRule);
        if(null==list||0==list.size())
        {
            log.info("送积分数据为空！");
            pointRule.setPointValue("0");
        }
        else
        {
            pointRule = list.get(0);
           int flag = 0;
            try
            {
                Date activeTime = dateFormat.parse(pointRule.getActiveTime());
                Date expireTime = dateFormat.parse(pointRule.getExpireTime());
                if(activeTime.before(serverTime)&&serverTime.before(expireTime)&&1==(goodsExchangeRack.getIsPresentPoints().intValue()))
                {
                    flag = 1;
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
            goodsExchangeRack.setIsPresentPoints(flag);
        }
		return "local_exchange_info";
	}
	
	//立即兑换本地商品展示
	public String localGoodsRackView(){
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			log.info("商户暂时不能兑换商品");
			payResult="error";
			errorMsg ="商户暂时不能兑换商品";
			return "failse";
		}
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		boolean isLogin=checkUser();
		log.info("用户是否登录："+isLogin);
		if(!isLogin){
			return "login_page";
		}
		
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(exchangeTempBean.getGoodsRackId());
		user=getLoginUser();
		exchangeTempBean.setMerchantId(goodsExchangeRack.getMerchantId());
		exchangeTempBean.setSellerId(goodsExchangeRack.getSellerId()+"");
		exchangeTempBean.setUserId(user.getUserID());
		exchangeTempBean.setUser(user);
		exchangeTempBean.setClientType(PayCommand.CLIENT_PC);
		if(!isValid(exchangeTempBean)){
			log.error("===========页面参数校验不通过，非法的请求==========");
			payResult="error";
			errorMsg = "无效的请求";
			return Action.ERROR;
		}
		trans=exchangeActionSupport.makeExchangeTransForPC(exchangeTempBean);
		if(TranCommand.RESP_CODE_CREATE_FAIL.equals(trans.getRespCode())){
			log.error("=============交易组装失败，原因："+trans.getRespMsg());
			payResult="error";
			errorMsg=trans.getRespMsg();
			return "failse";
		}
		log.info("==========交易组装成功，正在添加交易==========");
		trans=transActionSupport.addTrans(trans);
		if(trans==null){
			log.error("===========交易添加失败===========");
			payResult="error";
			errorMsg="交易创建失败，请重试！";
			return "failse";
		}
		return "local_exchange_view";
	}
	
	/**
	  * 方法描述：校验页面参数
	  * @param: ExchangeTempBean
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 29, 2013 3:59:36 PM
	  */
	private boolean isValid(ExchangeTempBean tempExch){
		if(tempExch.getMobile()!=null&&!"".equals(tempExch.getMobile())){
			return tempExch.getMobile().matches("\\d{11}");
		}
		if(tempExch.getGoodsCategoryId()!=null&&!"".equals(tempExch.getGoodsCategoryId())){
			return tempExch.getGoodsCategoryId().matches("\\d{9,32}");
		}
		if(tempExch.getGoodsRackId()!=null&&!"".equals(tempExch.getGoodsRackId())){
			return tempExch.getGoodsRackId().matches("\\d{9,32}");
		}
		if(tempExch.getSellerId()!=null&&!"".equals(tempExch.getSellerId())){
			return tempExch.getSellerId().matches("\\d{9,32}");
		}
		if(tempExch.getPeriod()!=null&&!"".equals(tempExch.getPeriod())){
			return tempExch.getPeriod().matches("\\d{2,32}");
		}
		if(tempExch.getPayMethod()!=null&&!"".equals(tempExch.getPayMethod())){
			return tempExch.getPayMethod().matches("\\d{2}");
		}
		if(tempExch.getUsePointRaioValue()!=null&&!"".equals(tempExch.getUsePointRaioValue())){
			return tempExch.getUsePointRaioValue().matches("\\d{1,9}");
		}
		String lotteryValue=tempExch.getLotteryValue();
		if(lotteryValue!=null&&!"".equals(lotteryValue)){
			String[] arr={"%","+","--","*","&","$","^","~"};
			for (int i = 0; i < arr.length; i++) {
				if(lotteryValue.contains(arr[i])){
					return false;
				}
			}
		}
		return true;
	}
	
	//立即兑换本地商品下单
	public String localGoodsRackAffirm(){
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		return "local_exchange_affirm";
	}
	
	
	//话费充值商品
	public String telephoneFeeGoodsRack(){
		log.info("积分兑换话费充值商品列表");
		
		
		publicAdvertAndTag();
		
		pager.setGoodsCategoryId("100001006");//话费充值分类
		pager.setState("30");
		pager.setIsRack(GoodsCommand.IS_RACK_YES);
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);	
		
		return "telephone_fee_exchange_list";
	}
	
	//话费充值商品详情
	public String telephoneFeeGoodsRackInfo(){
		log.info("话费充值商品ID:"+id);
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(id);
		
		return "telephone_fee_exchange_info";
	}
	
	//立即兑换话费充值展示
	public String telephoneFeeGoodsRackView(){
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			log.info("商户暂时不能兑换商品");
			payResult="error";
			errorMsg ="商户暂时不能兑换商品";
			return "failse";
		}
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		boolean isLogin=checkUser();
		log.info("用户是否登录："+isLogin);
		if(!isLogin){
			return "login_page";
		}
		
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(exchangeTempBean.getGoodsRackId());
		user=getLoginUser();
		exchangeTempBean.setMerchantId(goodsExchangeRack.getMerchantId());
		exchangeTempBean.setSellerId(goodsExchangeRack.getSellerId()+"");
		exchangeTempBean.setUserId(user.getUserID());
		exchangeTempBean.setUser(user);
		exchangeTempBean.setClientType(PayCommand.CLIENT_PC);
		if(!isValid(exchangeTempBean)){
			log.error("===========页面参数校验不通过，非法的请求==========");
			payResult="error";
			errorMsg = "无效的请求";
			return Action.ERROR;
		}
		trans=exchangeActionSupport.makeExchangeTransForPC(exchangeTempBean);
		if(TranCommand.RESP_CODE_CREATE_FAIL.equals(trans.getRespCode())){
			log.error("=============交易组装失败，原因："+trans.getRespMsg());
			payResult="error";
			errorMsg=trans.getRespMsg();
			return "failse";
		}
		log.info("==========交易组装成功，正在添加交易==========");
		trans=transActionSupport.addTrans(trans);
		if(trans==null){
			log.error("===========交易添加失败===========");
			payResult="error";
			errorMsg="交易创建失败，请重试！";
			return "failse";
		}
	
		return "telephone_fee_exchange_view";
	}
	
	//立即兑换话费充值下单
	public String telephoneFeeGoodsRackAffirm(){
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		return "telephone_fee_exchange_affirm";
	}
	
	
	//彩票商品
	public String lotteryGoodsRack(){
		log.info("积分兑换彩票商品列表");
		
		publicAdvertAndTag();
		
		pager.setGoodsCategoryId("100001005");//彩票商品分类
		pager.setState("30");
		pager.setIsRack(GoodsCommand.IS_RACK_YES);
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
		return "lottery_exchange_list";
	}
	
	//彩票商品详情
	public String lotteryGoodsRackInfo(){
		log.info("彩票商品ID:"+id);
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(id);
		
		return "lottery_exchange_info";
	}
	
	//立即兑换彩票展示
	public String lotteryGoodsRackView(){
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			log.info("商户暂时不能兑换商品");
			payResult="error";
			errorMsg ="商户暂时不能兑换商品";
			return "failse";
		}
		
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		boolean isLogin=checkUser();
		log.info("用户是否登录："+isLogin);
		if(!isLogin){
			return "login_page";
		}
		
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(exchangeTempBean.getGoodsRackId());
		user=getLoginUser();
		exchangeTempBean.setMerchantId(goodsExchangeRack.getMerchantId());
		exchangeTempBean.setSellerId(goodsExchangeRack.getSellerId()+"");
		exchangeTempBean.setUserId(user.getUserID());
		exchangeTempBean.setUser(user);
		exchangeTempBean.setClientType(PayCommand.CLIENT_PC);
		if(!isValid(exchangeTempBean)){
			log.error("===========页面参数校验不通过，非法的请求==========");
			payResult="error";
			errorMsg = "无效的请求";
			return Action.ERROR;
		}
		trans=exchangeActionSupport.makeExchangeTransForPC(exchangeTempBean);
		if(TranCommand.RESP_CODE_CREATE_FAIL.equals(trans.getRespCode())){
			log.error("=============交易组装失败，原因："+trans.getRespMsg());
			payResult="error";
			errorMsg=trans.getRespMsg();
			return "failse";
		}
		log.info("==========交易组装成功，正在添加交易==========");
		trans=transActionSupport.addTrans(trans);
		if(trans==null){
			log.error("===========交易添加失败===========");
			payResult="error";
			errorMsg="交易创建失败，请重试！";
			return "failse";
		}
	
		return "lottery_exchange_view";
	}
	
	//立即兑换彩票下单
	public String lotteryGoodsRackAffirm(){
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		return "lottery_exchange_affirm";
	}
	
	
	
	//农特产品商品
	public String specialtyGoodsRack(){
		log.info("积分兑换农特产品商品列表");
		
		publicAdvertAndTag();
		
		pager.setGoodsCategoryId("100001003");//农特产品商品分类
		pager.setState("30");
		pager.setIsRack(GoodsCommand.IS_RACK_YES);
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
		return "specialty_exchange_list";
	}

	//农特产品商品详情
	public String specialtyGoodsRackInfo(){
		log.info("农特产品商品ID:"+id);
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(id);
		
		return "specialty_exchange_info";
	}
	
	//立即兑换农特产品展示
	public String specialtyGoodsRackView(){
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			log.info("商户暂时不能兑换商品");
			payResult="error";
			errorMsg ="商户暂时不能兑换商品";
			return "failse";
		}
		
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		boolean isLogin=checkUser();
		log.info("用户是否登录："+isLogin);
		if(!isLogin){
			return "login_page";
		}
		
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(exchangeTempBean.getGoodsRackId());
		user=getLoginUser();
		exchangeTempBean.setMerchantId(goodsExchangeRack.getMerchantId());
		exchangeTempBean.setSellerId(goodsExchangeRack.getSellerId()+"");
		exchangeTempBean.setUserId(user.getUserID());
		exchangeTempBean.setUser(user);
		exchangeTempBean.setClientType(PayCommand.CLIENT_PC);
		if(!isValid(exchangeTempBean)){
			log.error("===========页面参数校验不通过，非法的请求==========");
			payResult="error";
			errorMsg = "无效的请求";
			return Action.ERROR;
		}
		trans=exchangeActionSupport.makeExchangeTransForPC(exchangeTempBean);
		if(TranCommand.RESP_CODE_CREATE_FAIL.equals(trans.getRespCode())){
			log.error("=============交易组装失败，原因："+trans.getRespMsg());
			payResult="error";
			errorMsg=trans.getRespMsg();
			return "failse";
		}
		log.info("==========交易组装成功，正在添加交易==========");
		trans=transActionSupport.addTrans(trans);
		if(trans==null){
			log.error("===========交易添加失败===========");
			payResult="error";
			errorMsg="交易创建失败，请重试！";
			return "failse";
		}
	
		return "specialty_exchange_view";
	}
	
	//立即兑换农特产品下单
	public String specialtyGoodsRackAffirm(){
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		return "specialty_exchange_affirm";
	}
	
	
	//查询广告和标签
	private void publicAdvertAndTag() {
//		//查询返利模块广告
//		Advert advert=new Advert();
//		advert.setModule("2");
//		advert.setType("3");
//		advert.setState("30");
//		advertList=advertActionSupport.getAdvertById(advert);
		
		//全部商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();
		
		//全部分类
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
	}
	
	/**
	 * 查询期号
	 * @return
	 */
	public String ajaxLotteryInfoBeforeOrder(){
		String lotteryNo="FC_SSQ";
		Lottery lottery=pointObtainActionSupport.queryLotteryPeridListNow(lotteryNo);
		
		Map<String, String> jsonMap = new HashMap<String, String>();
		
		if(lottery==null){
			jsonMap.put("status", ERROR);
			jsonMap.put("message", " 彩票期号查询失败,稍后再试... ");
		}
		else{
			log.info("查询期号返回数据："+JSONObject.fromObject(lottery));
			jsonMap.put("status", "success");
			jsonMap.put("period", lottery.getPeriod());
			jsonMap.put("endtime", lottery.getEndTime());
		}
		
		return ajaxJson(jsonMap);
	}
	
	//验证会员是否登录
	private boolean checkUser(){
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(userId)){
			return false;
		}
		return true;
	}
	
	private boolean isMerchant(){
		//是否是商户
		String isMerchant = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
		if("1".equals(isMerchant)){
			return true;
		}
		return false;
	}
	
	//珠海农商积分兑换专区列表
	public String bankPointGoodsRack(){
		log.info("珠海农商积分兑换专区列表");
		
		publicAdvertAndTag();
		
		pager.setGoodsCategoryId("100001011");//珠海专区
		pager.setState("30");
		pager.setIsRack(GoodsCommand.IS_RACK_YES);
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
		return "bankPoint_exchange_list";
	}
	
	//珠海农商积分兑换专区详情
	public String bankPointGoodsRackInfo(){
		log.info("珠海专区商品ID:"+id);
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(id);
		
		return "bankPoint_exchange_info";
	}
	
	//珠海农商积分兑换专区展示
	public String bankPointGoodsRackView(){
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			log.info("商户暂时不能兑换商品");
			payResult="error";
			errorMsg ="商户暂时不能兑换商品";
			return "failse";
		}
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		boolean isLogin=checkUser();
		log.info("用户是否登录："+isLogin);
		if(!isLogin){
			return "login_page";
		}
		
		goodsExchangeRack=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(exchangeTempBean.getGoodsRackId());
		user=getLoginUser();
		exchangeTempBean.setMerchantId(goodsExchangeRack.getMerchantId());
		exchangeTempBean.setSellerId(goodsExchangeRack.getSellerId()+"");
		exchangeTempBean.setUserId(user.getUserID());
		exchangeTempBean.setUser(user);
		exchangeTempBean.setClientType(PayCommand.CLIENT_PC);
		if(!isValid(exchangeTempBean)){
			log.error("===========页面参数校验不通过，非法的请求==========");
			payResult="error";
			errorMsg = "无效的请求";
			return Action.ERROR;
		}
		trans=exchangeActionSupport.makeExchangeTransForPC(exchangeTempBean);
		if(TranCommand.RESP_CODE_CREATE_FAIL.equals(trans.getRespCode())){
			log.error("=============交易组装失败，原因："+trans.getRespMsg());
			payResult="error";
			errorMsg=trans.getRespMsg();
			return "failse";
		}
		log.info("==========交易组装成功，正在添加交易==========");
		trans=transActionSupport.addTrans(trans);
		if(trans==null){
			log.error("===========交易添加失败===========");
			payResult="error";
			errorMsg="交易创建失败，请重试！";
			return "failse";
		}
	
		return "bankPoint_exchange_view";
	}
	
	
	//珠海农商积分兑换专区商品下单
	public String bankPointGoodsRackAffirm(){
		if(exchangeTempBean!=null){
			log.info("商品兑换参数："+JSONObject.fromObject(exchangeTempBean));
		}else{
			log.info("============无效请求============");
			return Action.ERROR;
		}
		
		return "bankPoint_exchange_affirm";
	}
	
	
	//
	public String ajaxLotteryCheckDate() throws ParseException{
		log.info("校验购买彩票是否过期");
		String lotteryNo="FC_SSQ";
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Lottery lottery=pointObtainActionSupport.queryLotteryPeridListNow(lotteryNo);
		Map<String, String> jsonMap = new HashMap<String, String>();
		Date date=df.parse(lottery.getEndTime());
		Date nowdate=new Date();
		if(nowdate.after(date)){
			jsonMap.put("res", "no");
		}else{
			jsonMap.put("res", "ok");
		}
		return ajaxJson(jsonMap);
	}
	
	
	public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
		return goodsExchangeRackActionSupport;
	}


	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}

	public GoodsExchangeRack getPager() {
		return pager;
	}

	public void setPager(GoodsExchangeRack pager) {
		this.pager = pager;
	}
	
	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}

	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}

	public AdvertActionSupport getAdvertActionSupport() {
		return advertActionSupport;
	}

	public void setAdvertActionSupport(AdvertActionSupport advertActionSupport) {
		this.advertActionSupport = advertActionSupport;
	}

	public List<TagClassifyA> getAllTagClassifyAList() {
		return allTagClassifyAList;
	}

	public void setAllTagClassifyAList(List<TagClassifyA> allTagClassifyAList) {
		this.allTagClassifyAList = allTagClassifyAList;
	}
	
	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}
	
	public List<TagDistrictB> getAllTagDistrictBList() {
		return allTagDistrictBList;
	}
	
	public void setAllTagDistrictBList(List<TagDistrictB> allTagDistrictBList) {
		this.allTagDistrictBList = allTagDistrictBList;
	}
	
	public GoodsExchangeRack getGoodsExchangeRack() {
		return goodsExchangeRack;
	}

	public void setGoodsExchangeRack(GoodsExchangeRack goodsExchangeRack) {
		this.goodsExchangeRack = goodsExchangeRack;
	}
	
	public ExchangeTempBean getExchangeTempBean() {
		return exchangeTempBean;
	}

	public void setExchangeTempBean(ExchangeTempBean exchangeTempBean) {
		this.exchangeTempBean = exchangeTempBean;
	}
	
	public PointObtainActionSupport getPointObtainActionSupport() {
		return pointObtainActionSupport;
	}

	public void setPointObtainActionSupport(
			PointObtainActionSupport pointObtainActionSupport) {
		this.pointObtainActionSupport = pointObtainActionSupport;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

    public PresentPointRule getPointRule()
    {
        return pointRule;
    }

    public void setPointRule(PresentPointRule pointRule)
    {
        this.pointRule = pointRule;
    }

    public GoodsPreSellRackActionSupport getGoodsPreSellRackActionSupport()
    {
        return goodsPreSellRackActionSupport;
    }

    public void setGoodsPreSellRackActionSupport(GoodsPreSellRackActionSupport goodsPreSellRackActionSupport)
    {
        this.goodsPreSellRackActionSupport = goodsPreSellRackActionSupport;
    }

	public void setExchangeActionSupport(ExchangeActionSupport exchangeActionSupport) {
		this.exchangeActionSupport = exchangeActionSupport;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
}
