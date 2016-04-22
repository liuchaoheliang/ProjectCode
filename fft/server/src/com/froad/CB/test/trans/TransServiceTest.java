package com.froad.CB.test.trans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.PreferType;
import com.froad.CB.common.RuleDetailType;
import com.froad.CB.common.Pager.OrderType;
import com.froad.CB.common.constant.PayCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.impl.TransDaoImpl;
import com.froad.CB.po.Pay;
import com.froad.CB.po.Trans;
import com.froad.CB.po.TransDetails;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.service.impl.AuthTicketServiceImpl;
import com.froad.CB.service.impl.MerchantServiceImpl;
import com.froad.CB.service.impl.TransServiceImpl;
import com.froad.util.MessageSourceUtil;
import com.froad.util.Result;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TransServiceTest{
	

	@Resource
	private TransServiceImpl transServiceImpl;
	
	@Resource
	private MerchantServiceImpl merchantServiceImpl;
	
	@Resource
	private AuthTicketServiceImpl authTicketServiceImpl;
	
	@Resource
	private TransDaoImpl transDaoImpl;
	
	@Test
	public void addTrans() {
		Trans trans=new Trans();
		trans.setTrackNo("2013030710000041");
		trans.setTransSn("2013030710000041");
//		trans.setTransType(TranCommand.POINTS_EXCH_CASH);
		trans.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		trans.setBuyersId("100001004");
		trans.setSellerId("100001025");
		trans.setBuyerChannelId("100001001");
		trans.setSellerChannelId("100001006");
		trans.setCurrency("RMB");
		trans.setCostpriceTotal("11");
		trans.setCurrencyValueAll("11");
		trans.setCurrencyValueRealAll("10");
		trans.setFftFactorage("1");
		trans.setState(TranCommand.TRAN_PROCESSING);
		trans.setClientType(PayCommand.CLIENT_PC);
		trans.setPayChannel(TranCommand.PAY_CHANNEL_PHONE);
		trans.setPayMethod(TranCommand.POINTS_FFT_CASH);
		trans.setRemark("测试时间是：2013-03-11");
		trans.setPresentRuleId(1000002321);
		
		TransDetails transDetails=new TransDetails();
		transDetails.setGoodsRackId("100001001");
		transDetails.setState("30");
		transDetails.setSellerRuleDesc("这是规则描述2013-03-11");
		List<TransDetails> transDetailsList=new ArrayList<TransDetails>();
		transDetailsList.add(transDetails);
		trans.setTransDetailsList(transDetailsList);
		
		Pay pointsPay=new Pay();
		pointsPay.setTrackNo("2013030720000041");
//		pointsPay.setTypeDetail(RuleDetailType.DEDUCT_POINTS.getValue());
		pointsPay.setTypeDetail(RuleDetailType.PAY_POINTS.getValue());
		pointsPay.setType(PayCommand.TYPE_POINTS);
		pointsPay.setFromAccountNo("34");
		pointsPay.setFromUsername("user68");
		pointsPay.setPayValue("11");
		
		Pay cashPay=new Pay();
		cashPay.setTrackNo("2013030720000042");
//		cashPay.setTypeDetail(RuleDetailType.PAY_TO_Buyer.getValue());
		cashPay.setTypeDetail(RuleDetailType.PAY_COLLECT.getValue());
		cashPay.setType(PayCommand.TYPE_CASH);
		cashPay.setFromAccountName("上海方付通商务服务有限公司珠海分公司");
		cashPay.setFromAccountNo("0000053868464012");
		cashPay.setFromPhone("13112344321");
		cashPay.setToAccountName("小二");
		cashPay.setToAccountNo("0000053868464011");
		cashPay.setPayValue("11");
		cashPay.setChannelId("100001001");
		
//		Pay feePay=new Pay();
//		feePay.setTrackNo("2013030720000013");
//		feePay.setTypeDetail(RuleDetailType.PAY_TO_FFT.getValue());
//		feePay.setType(PayCommand.TYPE_CASH);
//		feePay.setPayValue("1");
		
		List<Pay> payList=new ArrayList<Pay>();
		payList.add(pointsPay);
		payList.add(cashPay);
//		payList.add(feePay);
		trans.setPayList(payList);
		
		Trans tr=null;
		try {
			tr = transServiceImpl.addTrans(trans);
		} catch (AppException e) {
			e.printStackTrace();
		}
		System.out.println("trans id: "+tr.getId());//100001184
	}
	
	@Test
	public void withdraw(){
		Integer transId=100001121;
		boolean isOk=false;
		try {
			isOk = transServiceImpl.withdraw(transId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("是否成功："+isOk);
	}
	
	@Test
	public void doPay(){
		Integer transId=100001188;
		try {
			Result result=transServiceImpl.doPay(transId);
			System.out.println("是否成功："+result.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteById() {
		Integer id=100001002;
		
		transServiceImpl.deleteById(id);
	}

	@Test
	public void getTransById() {
		Integer id=100001024;
		
		Trans trans=transServiceImpl.getTransById(id);
		System.out.println("trans: "+trans);
		if(trans!=null){
			System.out.println("商品名称： "+trans.getTransDetailsList().get(0).getGoods().getGoodsName());
		}
//		System.out.println("sellerChannel===》"+trans.getSellerChannel());
//		System.out.println("buyerChannel===》"+trans.getBuyerChannel());
//		System.out.println("buyers====>"+trans.getBuyer().getCreateTime());
		
//		List<TransDetails> list=trans.getTransDetailsList();
//		if(list!=null&&list.size()>0){
//			System.out.println("transDetails.goodsCarryRack====>"+list.get(0).getGoodsCarryRack());
//		}
	}
	
	@Test
	public void getTransByPager(){
		Trans queryCon=new Trans();
//		queryCon.setSellerId(String.valueOf(100001025));
//		queryCon.setTransType(TranCommand.POINTS_FFT_CASH);
//		queryCon.setPageSize(10);
//		queryCon.setTransType(TranCommand.GROUP);
//		queryCon.setIsConsume("0");//已经消费
		queryCon.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		List<String>  typeList = new ArrayList<String>();
		queryCon.setPayMethodList(typeList);
		typeList.add(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"
//		List<Integer> sellerIdList=new ArrayList<Integer>();
//		queryCon.setSellerIdList(sellerIdList);
//		sellerIdList.add(new Integer("100001033"));
//		queryCon.getSellerIdList().addAll(sellerIdList);
		
		//添加支付方式查询条件
		queryCon.getPayMethodList().addAll(typeList);//方付通积分支付
		
		List<String> payStateList=new ArrayList<String>();
		payStateList.add("01");
		payStateList.add("03");
		queryCon.setPayStateList(payStateList);
				
		queryCon.setState("02");//01:处理中02:交易成功03:交易失败
		queryCon.setOrderType(OrderType.desc);

		Trans pager=transServiceImpl.getTransByPager(queryCon);
		Trans tmp=null;
		System.out.println("交易条数**********************"+pager.getList().size());
		for (int i = 0; i <pager.getList().size(); i++) {
			tmp=(Trans)pager.getList().get(i);
			if(tmp.getBuyer()!=null){
				System.out.println("createTime is: "+tmp.getCreateTime()+"authTicketId："+tmp.getAuthTicketId()+"tranId："+tmp.getId());
			}
			if(tmp.getTransGoodsAttrList()!=null && tmp.getTransGoodsAttrList().size()>0){
				System.out.println("类型："+tmp.getTransGoodsAttrList().get(0).getGoodsRackAttribute().getName());
			}
		}
	}

	@Test
	public void getGroupOrExchangeTransByPager(){
		Trans queryCon=new Trans();
//		queryCon.setSellerId(String.valueOf(100001025));
//		queryCon.setTransType(TranCommand.POINTS_FFT_CASH);
//		queryCon.setPageSize(10);
//		queryCon.setTransType(TranCommand.GROUP);
//		queryCon.setIsConsume("0");//已经消费
		queryCon.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		List<String>  typeList = new ArrayList<String>();
		queryCon.setPayMethodList(typeList);
		typeList.add(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"
//		List<Integer> sellerIdList=new ArrayList<Integer>();
//		queryCon.setSellerIdList(sellerIdList);
//		sellerIdList.add(new Integer("100001033"));
//		queryCon.getSellerIdList().addAll(sellerIdList);
		
		//添加支付方式查询条件
		queryCon.getPayMethodList().addAll(typeList);//方付通积分支付
				
		queryCon.setState("02");//01:处理中02:交易成功03:交易失败
		queryCon.setOrderType(OrderType.desc);

		Trans pager=transServiceImpl.getGroupOrExchangeTransByPager(queryCon);
		Trans tmp=null;
		System.out.println("交易条数**********************"+pager.getList().size());
		for (int i = 0; i <pager.getList().size(); i++) {
			tmp=(Trans)pager.getList().get(i);
			if(tmp.getBuyer()!=null){
				System.out.println("**************createTime is: "+tmp.getCreateTime()+"authTicketId："+tmp.getAuthTicketId()+"tranId："+tmp.getId());
			}
			if(tmp.getTransGoodsAttrList()!=null && tmp.getTransGoodsAttrList().size()>0){
				System.out.println("类型："+tmp.getTransGoodsAttrList().get(0).getGoodsRackAttribute().getName()+"tranId："+tmp.getId());
			}
		}
	}
	
	@Test
	public void updateById() {
		Trans trans=new Trans();
		trans.setId(1000015279);
		trans.setState("30");
//		trans.setBillNo("T1001");
//		trans.setBuyersId("100001002");
//		trans.setFftPointsValueRealAll("10");
//		trans.setBankPointsValueRealAll("10");
		transServiceImpl.updateById(trans);
	}

	@Test
	public void updateStateById() {
		Integer id=100001001;
		String state="50";
		
		transServiceImpl.updateStateById(id, state);
	}
	
	
	@Test
	public void getMerchantByType(){
		List<Merchant> list=merchantServiceImpl.getMerchantByType(PreferType.cash);
		System.out.println("list: "+list.size());
	}
	
	@Test
	public void getMessageSource(){
		MessageSource source=MessageSourceUtil.getSource();
		String[] msg={"iphone4s","123456","2013年3月12号","2013年3月15号","13112344321"};
		System.out.println("消息: "+source.getMessage("group", msg,Locale.CHINA));
//		System.out.println(SpringContextUtil.getContext().getMessage("group", msg,Locale.CHINA));
	}

	@Test
	public void isNotExist(){
		boolean isNotExist=authTicketServiceImpl.isNotExist("12345");
		System.out.println("不存在此券："+isNotExist);
	}
	@Test
	public void getAllRefundPointsTrans(){
		List<Trans> list=transDaoImpl.getAllRefundPointsTrans();
		System.out.println("++++++++++++++++++++++++++"+list.size());
	}
	
	@Test
	public void getNoNeedCloseTransId(){
		List<Integer> list=transDaoImpl.getNoNeedCloseTransId();
		System.out.println("总共记录条数为："+list.size());
		for(Integer n:list){
			System.out.println("++++++++++++++查询不需要关闭的交易ID："+n);
		}
		
	}
	
	@Test
	public void closeWithoutPayTrans(){
		HashSet<String> userId=new HashSet<String>();
		userId.add("100001006");
		userId.add("103453432");
		List<String> list=new ArrayList<String>();
		Iterator<String > it=userId.iterator();
		while(it.hasNext()){
			list.add(it.next());
		}
		int count=transDaoImpl.closeWithoutPayTrans(list);
		System.out.println("++++++++++++++++++++++++++"+count);
	}
	
	@Test
	public void getTransMgmt(){
		
		Trans t=new Trans();
		List<String> idList=new ArrayList<String>();
		idList.add("100001117");
		idList.add("100001118");
		idList.add("100001121");
//		t.setMerchantIdList(idList);
		Trans trans =transDaoImpl.getTransByPager(t);
		System.out.println("++++++++++++++++++++++++++"+trans.getList().size());
	}
	
	
	
}
