package com.froad.action.admin.seller.trans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.froad.action.support.TransActionSupport;
import com.froad.action.support.admin.trans.SellerTransActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.OrderType;
import com.froad.client.trans.Trans;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;
import com.froad.util.command.TransState;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * *******************************************************
 * @项目名称: communityBusiness_client
 * @类       名: ExportExcelAction.java
 * @功能描述: TODO 生成积分返利，积分兑换，团购的excel文件
 * @作       者: 赵肖瑶
 * @日       期: 2013-4-22 下午03:47:10
 *********************************************************
 */
public class ExportExcelAction extends BaseActionSupport{

	private static final long serialVersionUID = 4746564449373583071L;
	private static final Logger logger=Logger.getLogger(MerchantTransAction.class);
	private SellerTransActionSupport sellerTransActionSupport;
	private TransActionSupport transActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private String beCode;//商户管理员查询某个员工交易，作为查询条件userId|beCode
	private String beName;//查询交易条件
	private Trans pager;
	private String fileName; //用户下载的excel名
	private String downloadFilePath;//服务器对应生成的excel路径
	private Map<String,String> clerkMap = new HashMap<String,String>();
	private String isAdminSet;



	private String storeId;
	/**
	 * *******************************************************
	 * @函  数  名: deleteExcel
	 * @功能描述: TODO 删除历史的excel
	 * @输入参数: @param filePath excel路径
	 * @返回类型: void
	 * @作       者: 赵肖瑶
	 * @日       期: 2013-4-22 下午08:00:50
	 * @修       改: Zxy
	 * @日       期:
	 **********************************************************
	 */
	public void deleteExcel(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
			log.info("***************************服务器上没有该路径：" + filePath + ",该指定路径文件夹将**************************");
		}else{
			File[] files = file.listFiles();
			for (int i = 0 ; i < files.length ; i ++) {
				if(files[i].getName().endsWith("xls")){
					log.info("******************已经删除历史excel文件：" + files[i].getName() + "*******************");
					files[i].delete();
				}
			}//删除服务器的历史excel
		}
	}
	public String expGroupExcelFinance() throws FileNotFoundException{
		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
		deleteExcel(downloadFilePath);

		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.GROUP);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.GROUP);
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);

		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费


		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			//如果有操作员昵称查询条件工号找出来
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}


		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}
		}else{
			logger.info("==============非商户===============");
			return Action.SUCCESS;
		}
		boolean isNoTimeCondition = false;
		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
			isNoTimeCondition = true;
			//TODO: 搜索条件没有控制
			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, - 3);
			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
			pager.setEndTime(sdf.format(new Date())+"|23:59:59");
		}
		//TODO:---------------------------------------------------------------------
		String becode = (String)getSession(SessionKey.BE_CODE);
		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
				MerchantUserSet mus = new MerchantUserSet();
				mus.setUserId((String)getSession(SessionKey.USER_ID));
				mus.setBeCode(becode);
				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
			}
		}
		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
		pager.setBelongUserBecodeAuth(belongUserbecode);
		pager.setFinanceExcel("yes");
		pager = transActionSupport.getGroupOrExchangeFinance(pager);

		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		pager.getList().clear();
		pager.getList().addAll(newList);
		List<Object> transAll = new ArrayList<Object>();
		transAll.addAll(pager.getList());
		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		fileName = fromatTime + "GroupFinance.xls";
		//开始pio进行Excel导出
		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fromatTime + "团购详细信息（财务）");
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 3000);

		HSSFRow rowTitle = sheet.createRow(0);
		HSSFCell ct0 = rowTitle.createCell(0);
		ct0.setCellValue("订单号");
		HSSFCell ct1 = rowTitle.createCell(1);
		ct1.setCellValue("商品名称");
		HSSFCell ct2 = rowTitle.createCell(2);
		ct2.setCellValue("更新时间");
		HSSFCell ct3 = rowTitle.createCell(3);
		ct3.setCellValue("支付方式");
		HSSFCell ct4 = rowTitle.createCell(4);
		ct4.setCellValue("现金");
		HSSFCell ct5 = rowTitle.createCell(5);
		ct5.setCellValue("分分通积分");
		HSSFCell ct6 = rowTitle.createCell(6);
		ct6.setCellValue("银行积分");
		HSSFCell ct7 = rowTitle.createCell(7);
		ct7.setCellValue("操作员工号");
		HSSFCell ct8 = rowTitle.createCell(8);
		ct8.setCellValue("操作员姓名");
		HSSFCell ct9 = rowTitle.createCell(9);
		ct9.setCellValue("所属门店");
		HSSFCell ct10 = rowTitle.createCell(10);
		ct10.setCellValue("状态");

		int i = 0;
		for (Object tran:transAll) {
			Trans tr = (Trans)tran;
			HSSFRow row = sheet.createRow(++i);

			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(tr.getId());

			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(tr.getGoodsName());

			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(tr.getUpdateTime());

			HSSFCell c3 = row.createCell(3);
			if("00".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分");
			}else if("01".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分");
			}else if("02".equals(tr.getPayMethod())){
				c3.setCellValue("现金");
			}else if("03".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金");
			}else if("04".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金");
			}else if("05".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金（比例）");
			}else if("06".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金（比例）");
			}else if("07".equals(tr.getPayMethod())){
               c3.setCellValue("分分通积分+支付宝（比例）");
            }else if("08".equals(tr.getPayMethod())){
              	c3.setCellValue("银行积分+支付宝（比例）");
            }else if("09".equals(tr.getPayMethod())){
             	c3.setCellValue("支付宝");
  			}else{
				c3.setCellValue("其他");
			}

			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(tr.getCurrencyValueRealAll());

			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(tr.getFftPointsValueRealAll());

			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(tr.getBankPointsValueRealAll());

			HSSFCell c7 = row.createCell(7);
			c7.setCellValue(tr.getBeCode());

			HSSFCell c8 = row.createCell(8);
			c8.setCellValue(tr.getBeName());

			HSSFCell c9 = row.createCell(9);
			c9.setCellValue(tr.getStoreStortName() == null || "".equals(tr.getStoreStortName()) ? "-" : tr.getStoreStortName());

			HSSFCell c10 = row.createCell(10);
			if(tr.getState().equals("01")){
				c10.setCellValue("处理中");
			}else if(tr.getState().equals("02")){
				c10.setCellValue("成功");
			}else {
				c10.setCellValue("失败");
			}
		}
		if(isNoTimeCondition){
			HSSFRow row = sheet.createRow(++i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
		}
		try {
			workbook.write(os);
			os.close();
			log.info("******************积分兑换excel导出成功*******************");
		} catch (IOException e) {
			log.info("******************积分兑换excel文件生成失败*******************");
		} finally {
			pager = null;
		}
		return Action.SUCCESS;
	}


	public String expExchangeExcelFinance() throws FileNotFoundException{
		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
		deleteExcel(downloadFilePath);

		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
//		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);
//
//		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费


		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999(返回的列表应该是查不到记录)
			beCode = "999";
		}

		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}
		}else{
			logger.info("=============非商户============");
			return Action.SUCCESS;
		}

		boolean isNoTimeCondition = false;
		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
			isNoTimeCondition = true;
			//TODO: 搜索条件没有控制
			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, - 3);
			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
			pager.setEndTime(sdf.format(new Date())+"|23:59:59");
		}

		String becode = (String)getSession(SessionKey.BE_CODE);
		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
				MerchantUserSet mus = new MerchantUserSet();
				mus.setUserId((String)getSession(SessionKey.USER_ID));
				mus.setBeCode(becode);
				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
			}
		}
		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
		pager.setBelongUserBecodeAuth(belongUserbecode);
		pager.setFinanceExcel("yse");
		pager = transActionSupport.getGroupOrExchangeFinance(pager);
		logger.info("查询兑换交易结果结束pager返回");
		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		logger.info("设置兑换交易显示单价和操作员姓名");
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		logger.info("设置兑换交易显示单价和操作员姓名结束");
		pager.getList().clear();
		pager.getList().addAll(newList);
		List<Object> transAll = new ArrayList<Object>();
		transAll.addAll(pager.getList());
		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		fileName = fromatTime + "ExchangeFinance.xls";
		//开始pio进行Excel导出
		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fromatTime + "积分兑换详细信息（财务）");
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 3000);

		HSSFRow rowTitle = sheet.createRow(0);
		HSSFCell ct0 = rowTitle.createCell(0);
		ct0.setCellValue("订单号");
		HSSFCell ct1 = rowTitle.createCell(1);
		ct1.setCellValue("商品名称");
		HSSFCell ct2 = rowTitle.createCell(2);
		ct2.setCellValue("更新时间");
		HSSFCell ct3 = rowTitle.createCell(3);
		ct3.setCellValue("支付方式");
		HSSFCell ct4 = rowTitle.createCell(4);
		ct4.setCellValue("现金");
		HSSFCell ct5 = rowTitle.createCell(5);
		ct5.setCellValue("分分通积分");
		HSSFCell ct6 = rowTitle.createCell(6);
		ct6.setCellValue("银行积分");
		HSSFCell ct7 = rowTitle.createCell(7);
		ct7.setCellValue("操作员工号");
		HSSFCell ct8 = rowTitle.createCell(8);
		ct8.setCellValue("操作员姓名");
		HSSFCell ct9 = rowTitle.createCell(9);
		ct9.setCellValue("所属门店");
		HSSFCell ct10 = rowTitle.createCell(10);
		ct10.setCellValue("状态");

		int i = 0;
		for (Object tran:transAll) {
			Trans tr = (Trans)tran;
			HSSFRow row = sheet.createRow(++i);

			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(tr.getId());

			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(tr.getGoodsName());

			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(tr.getUpdateTime());

			HSSFCell c3 = row.createCell(3);
			if("00".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分");
			}else if("01".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分");
			}else if("02".equals(tr.getPayMethod())){
				c3.setCellValue("现金");
			}else if("03".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金");
			}else if("04".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金");
			}else if("05".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金（比例）");
			}else if("06".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金（比例）");
			}else if("07".equals(tr.getPayMethod())){
               c3.setCellValue("分分通积分+支付宝（比例）");
            }else if("08".equals(tr.getPayMethod())){
              	c3.setCellValue("银行积分+支付宝（比例）");
            }else if("09".equals(tr.getPayMethod())){
             	c3.setCellValue("支付宝");
  			}else{
				c3.setCellValue("其他");
			}

			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(tr.getCurrencyValueRealAll());

			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(tr.getFftPointsValueRealAll());

			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(tr.getBankPointsValueRealAll());

			HSSFCell c7 = row.createCell(7);
			c7.setCellValue(tr.getBeCode());

			HSSFCell c8 = row.createCell(8);
			c8.setCellValue(tr.getBeName());

			HSSFCell c9 = row.createCell(9);
			c9.setCellValue(tr.getStoreStortName() == null || "".equals(tr.getStoreStortName()) ? "-" : tr.getStoreStortName());

			HSSFCell c10 = row.createCell(10);
			if(tr.getState().equals("01")){
				c10.setCellValue("处理中");
			}else if(tr.getState().equals("02")){
				c10.setCellValue("成功");
			}else {
				c10.setCellValue("失败");
			}
		}
		if(isNoTimeCondition){
			HSSFRow row = sheet.createRow(++i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
		}
		try {
			workbook.write(os);
			os.close();
			log.info("******************积分兑换excel导出成功*******************");
		} catch (IOException e) {
			log.info("******************积分兑换excel文件生成失败*******************");
		} finally {
			pager = null;
		}
		return Action.SUCCESS;
	}


	public String expRebateExcelFinance() throws FileNotFoundException{
		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
		deleteExcel(downloadFilePath);

		logger.info("商户查询返利交易开始");
		pager.setPageSize(10);//每页10条
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		List<String>  typeList = new ArrayList<String>();
		typeList.add(TranCommand.POINTS_REBATE);
		typeList.add(TranCommand.COLLECT);
		typeList.add(TranCommand.PRESENT_POINTS);
		List<Seller>  sellerList = (List<Seller>)session.get(MallCommand.SELLER_LIST);
		List<Integer> sellerIdList= new ArrayList<Integer>();
		//List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
		for(Seller t:sellerList){
			if(TranCommand.POINTS_REBATE.equals(t.getSellerType())){
				sellerIdList.add(t.getId());
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.COLLECT.equals(t.getSellerType())){
				sellerIdList.add(t.getId());
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.PRESENT_POINTS.equals(t.getSellerType())){
				sellerIdList.add(t.getId());
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
		}
		if(sellerIdList.size()==0)
			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
		pager.getSellerIdList().addAll(sellerIdList);

		//添加支付方式查询条件
		pager.getPayMethodList().addAll(typeList);//方付通积分支付

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);

		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}

		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);
				}
			}else{//普通操作员
				pager.setBelongUserBecode(userId+"|"+clerkBeCode);
			}
		}
		boolean isNoTimeCondition = false;
		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
			isNoTimeCondition = true;
			//TODO: 搜索条件没有控制
			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, - 3);
			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
			pager.setEndTime(sdf.format(new Date())+"|23:59:59");
		}

		//TODO:---------------------------------------------------------------------
		String becode = (String)getSession(SessionKey.BE_CODE);
		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
				MerchantUserSet mus = new MerchantUserSet();
				mus.setUserId((String)getSession(SessionKey.USER_ID));
				mus.setBeCode(becode);
				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
			}
		}
		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
		pager.setBelongUserBecode(belongUserbecode);
		pager.setFinanceExcel("yes");
		pager = transActionSupport.getTransByPagerFinance(pager);
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") != -1){
				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
				newList.add(t);
			}else{
				newList.add(t);
			}
		}
		pager.getList().clear();
		pager.getList().addAll(newList);
		List<Object> transAll = new ArrayList<Object>();
		transAll.addAll(pager.getList());
		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		fileName = fromatTime + "RebateFinance.xls";
		//开始pio进行Excel导出
		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fromatTime + "积分返利详细信息（财务）");

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 8000);
		sheet.setColumnWidth(9, 3000);

		HSSFRow rowTitle = sheet.createRow(0);
		HSSFCell ct0 = rowTitle.createCell(0);
		ct0.setCellValue("订单号");
		HSSFCell ct1 = rowTitle.createCell(1);
		ct1.setCellValue("赠送手机号");
		HSSFCell ct2 = rowTitle.createCell(2);
		ct2.setCellValue("更新时间");
		HSSFCell ct3 = rowTitle.createCell(3);
		ct3.setCellValue("交易类型");
		HSSFCell ct4 = rowTitle.createCell(4);
		ct4.setCellValue("总金额");
		HSSFCell ct5 = rowTitle.createCell(5);
		ct5.setCellValue("返利积分");
		HSSFCell ct6 = rowTitle.createCell(6);
		ct6.setCellValue("操作员工号");
		HSSFCell ct7 = rowTitle.createCell(7);
		ct7.setCellValue("操作员姓名");
		HSSFCell ct8 = rowTitle.createCell(8);
		ct8.setCellValue("所属门店");
		HSSFCell ct9 = rowTitle.createCell(9);
		ct9.setCellValue("订单状态");

		int i = 0;
		for (Object tran:transAll) {
			Trans tr = (Trans)tran;
			HSSFRow row = sheet.createRow(++i);


			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(tr.getId());

			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(tr.getPhone());

			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(tr.getUpdateTime());

			HSSFCell c3 = row.createCell(3);
			if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("01")){
				c3.setCellValue("积分兑换");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("02")){
				c3.setCellValue("团购交易");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("03")){
				c3.setCellValue("积分返利");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("04")){
				c3.setCellValue("积分提现");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("05")){
				c3.setCellValue("现金收款");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("06")){
				c3.setCellValue("赠送积分");
			}else {
				c3.setCellValue("--");
			}

			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(tr.getCurrencyValueRealAll());

			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(tr.getFftPointsValueRealAll());

			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(tr.getBeCode());

			HSSFCell c7 = row.createCell(7);
			c7.setCellValue(tr.getBeName());

			HSSFCell c8 = row.createCell(8);
			if(tr.getStoreStortName() == null  || "".equals(tr.getStoreStortName())){
				c8.setCellValue("--");
			}else{
				c8.setCellValue(tr.getStoreStortName());
			}


			HSSFCell c9 = row.createCell(9);
			if(tr.getState().equals("01")){
				c9.setCellValue("处理中");
			}else if(tr.getState().equals("02")){
				c9.setCellValue("成功");
			}else {
				c9.setCellValue("失败");
			}

		}

		if(isNoTimeCondition){
			HSSFRow row = sheet.createRow(++i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
		}

		try {
			workbook.write(os);
			os.close();
			log.info("******************积分返利excel导出成功*******************");
		} catch (IOException e) {
			log.info("******************积分返利excel文件生成失败*******************");
		}finally{
			pager = null;
		}


		return Action.SUCCESS;
	}

	public String expRebateExcel() throws IOException{
		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
		deleteExcel(downloadFilePath);

		if(pager==null)
			 pager=new Trans();
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		List<String>  typeList = new ArrayList<String>();
		typeList.add(TranCommand.POINTS_REBATE);
		typeList.add(TranCommand.COLLECT);
		typeList.add(TranCommand.PRESENT_POINTS);
		List<Seller>  sellerList = (List<Seller>)session.get(MallCommand.SELLER_LIST);
		List<Integer> sellerIdList= new ArrayList<Integer>();
		//List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
		for(Seller t:sellerList){
			if(TranCommand.POINTS_REBATE.equals(t.getSellerType())){
				sellerIdList.add(t.getId());
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.COLLECT.equals(t.getSellerType())){
				sellerIdList.add(t.getId());
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.PRESENT_POINTS.equals(t.getSellerType())){
				sellerIdList.add(t.getId());
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
		}
		if(sellerIdList.size()==0)
			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
		pager.getSellerIdList().addAll(sellerIdList);

		//添加支付方式查询条件
		pager.getPayMethodList().addAll(typeList);//方付通积分支付

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}

		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);
				}
			}else{//普通操作员
				pager.setBelongUserBecode(userId+"|"+clerkBeCode);
			}
		}

		boolean isNoTimeCondition = false;
		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
			isNoTimeCondition = true;
			//TODO: 搜索条件没有控制
			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, - 3);
			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
			pager.setEndTime(sdf.format(new Date())+"|23:59:59");
		}


		pager = transActionSupport.getDataToRepExcel(pager);

		List<Object> transAll = new ArrayList<Object>();//组装所有记录
		transAll = pager.getList();

		List<Object> templist = transAll;
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") != -1){
				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
				newList.add(t);
			}else{
				newList.add(t);
			}
		}
		transAll.clear();
		transAll.addAll(newList);

		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		fileName = fromatTime + "Rebate.xls";
		//开始pio进行Excel导出
		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fromatTime + "积分返利详细信息");

		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);

		HSSFRow rowTitle = sheet.createRow(0);
		HSSFCell ct0 = rowTitle.createCell(0);
		ct0.setCellValue("商户");
		HSSFCell ct1 = rowTitle.createCell(1);
		ct1.setCellValue("订单号");
		HSSFCell ct2 = rowTitle.createCell(2);
		ct2.setCellValue("下单时间");
		HSSFCell ct3 = rowTitle.createCell(3);
		ct3.setCellValue("交易类型");
		HSSFCell ct4 = rowTitle.createCell(4);
		ct4.setCellValue("总价");
		HSSFCell ct5 = rowTitle.createCell(5);
		ct5.setCellValue("返利积分");
		HSSFCell ct6 = rowTitle.createCell(6);
		ct6.setCellValue("操作员姓名");
		HSSFCell ct7 = rowTitle.createCell(7);
		ct7.setCellValue("状态");

		int i = 0;
		for (Object tran:transAll) {
			Trans tr = (Trans)tran;
			HSSFRow row = sheet.createRow(++i);


			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(tr.getMerchant().getMstoreFullName());

			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(tr.getId() + "");

			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(tr.getCreateTime());

			HSSFCell c3 = row.createCell(3);
			if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("01")){
				c3.setCellValue("积分兑换");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("02")){
				c3.setCellValue("团购交易");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("03")){
				c3.setCellValue("积分返利");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("04")){
				c3.setCellValue("积分提现");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("05")){
				c3.setCellValue("现金收款");
			}else if(!Assert.empty(tr.getTransType()) && tr.getTransType().equals("06")){
				c3.setCellValue("赠送积分");
			}else {
				c3.setCellValue("--");
			}

			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(tr.getCurrencyValueRealAll());

			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(tr.getFftPointsValueRealAll());

			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(Assert.empty(tr.getBelongUserBecode())? "-" : tr.getBelongUserBecode());

			HSSFCell c7 = row.createCell(7);
			if(tr.getState().equals("01")){
				c7.setCellValue("处理中");
			}else if(tr.getState().equals("02")){
				c7.setCellValue("成功");
			}else {
				c7.setCellValue("失败");
			}

		}
		if(isNoTimeCondition){
			HSSFRow row = sheet.createRow(++i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
		}
		try {
			workbook.write(os);
			os.close();
			log.info("******************积分返利excel导出成功*******************");
		} catch (IOException e) {
			log.info("******************积分返利excel文件生成失败*******************");
		}finally{
			pager = null;
		}

		return Action.SUCCESS;
	}

	public String expExchangeExcel() throws IOException{
		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
		deleteExcel(downloadFilePath);
		if(pager==null)
			 pager=new Trans();
		pager.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
//		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);
//
//		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费


		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999(返回的列表应该是查不到记录)
			beCode = "999";
		}

		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}
		}else{
			logger.info("=============非商户============");
			return Action.SUCCESS;
		}


		boolean isNoTimeCondition = false;
		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
			isNoTimeCondition = true;
			//TODO: 搜索条件没有控制
			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, - 3);
			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
			pager.setEndTime(sdf.format(new Date())+"|23:59:59");
		}
		pager = transActionSupport.getDataToRepExcel(pager);


		List<Object> transAll = new ArrayList<Object>();//组装所有记录
		transAll = pager.getList();
//		for(int initPage = 1 ; initPage <= pager.getPageCount() ; initPage ++){
//			pager.setPageNumber(initPage);
//			Integer totalNum = initPage*5000;
//			if(totalNum <= 20000){
//				pager = transActionSupport.getGroupOrExchangeTransByPager(pager);
//				transAll.addAll(pager.getList());
//			}else{
//				break;
//			}
//		}

		logger.info("查询兑换交易结果结束pager返回");
		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		logger.info("设置兑换交易显示单价和操作员姓名");
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}else if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") > 0){
				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		logger.info("设置兑换交易显示单价和操作员姓名结束");
		pager.getList().clear();
		pager.getList().addAll(newList);

		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		fileName = fromatTime + "Exchange.xls";
		//开始pio进行Excel导出
		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fromatTime + "积分兑换详细信息");
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 3000);

		HSSFRow rowTitle = sheet.createRow(0);
		HSSFCell ct0 = rowTitle.createCell(0);
		ct0.setCellValue("商户");
		HSSFCell ct1 = rowTitle.createCell(1);
		ct1.setCellValue("订单号");
		HSSFCell ct2 = rowTitle.createCell(2);
		ct2.setCellValue("下单时间");
		HSSFCell ct3 = rowTitle.createCell(3);
		ct3.setCellValue("支付方式");
		HSSFCell ct4 = rowTitle.createCell(4);
		ct4.setCellValue("现金");
		HSSFCell ct5 = rowTitle.createCell(5);
		ct5.setCellValue("分分通积分");
		HSSFCell ct6 = rowTitle.createCell(6);
		ct6.setCellValue("银行积分");
		HSSFCell ct7 = rowTitle.createCell(7);
		ct7.setCellValue("操作员姓名");
		HSSFCell ct8 = rowTitle.createCell(8);
		ct8.setCellValue("状态");

		int i = 0;
		for (Object tran:transAll) {
			Trans tr = (Trans)tran;
			HSSFRow row = sheet.createRow(++i);

			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(tr.getMerchant().getMstoreFullName());

			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(tr.getId() + "");

			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(tr.getCreateTime());

			HSSFCell c3 = row.createCell(3);
			if("00".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分");
			}else if("01".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分");
			}else if("02".equals(tr.getPayMethod())){
				c3.setCellValue("现金");
			}else if("03".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金");
			}else if("04".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金");
			}else if("05".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金（比例）");
			}else if("06".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金（比例）");
			}else if("07".equals(tr.getPayMethod())){
               c3.setCellValue("分分通积分+支付宝（比例）");
            }else if("08".equals(tr.getPayMethod())){
              	c3.setCellValue("银行积分+支付宝（比例）");
            }else if("09".equals(tr.getPayMethod())){
             	c3.setCellValue("支付宝");
  			}else{
				c3.setCellValue("其他");
			}

			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(tr.getCurrencyValueRealAll());

			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(tr.getFftPointsValueRealAll());

			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(tr.getBankPointsValueRealAll());

			HSSFCell c7 = row.createCell(7);
			c7.setCellValue(Assert.empty(tr.getBelongUserBecode())? "-" : tr.getBelongUserBecode());

			HSSFCell c8 = row.createCell(8);
			if(tr.getState().equals("01")){
				c8.setCellValue("处理中");
			}else if(tr.getState().equals("02")){
				c8.setCellValue("成功");
			}else {
				c8.setCellValue("失败");
			}
		}
		if(isNoTimeCondition){
			HSSFRow row = sheet.createRow(++i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
		}
		try {
			workbook.write(os);
			os.close();
			log.info("******************积分兑换excel导出成功*******************");
		} catch (IOException e) {
			log.info("******************积分兑换excel文件生成失败*******************");
		} finally {
			pager = null;
		}
		return Action.SUCCESS;
	}

	public String expGroupExcel() throws IOException{
		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
		deleteExcel(downloadFilePath);
		logger.info("商户查询团购excel导出开始");
		if(pager==null)
			 pager=new Trans();
		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.GROUP);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.GROUP);
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);

		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费


		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			//如果有操作员昵称查询条件工号找出来
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}


		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}
		}else{
			logger.info("==============非商户===============");
			return Action.SUCCESS;
		}
		boolean isNoTimeCondition = false;
		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
			isNoTimeCondition = true;
			//TODO: 搜索条件没有控制
			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, - 3);
			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
			pager.setEndTime(sdf.format(new Date())+"|23:59:592");
		}
		pager = transActionSupport.getDataToRepExcel(pager);



		List<Object> transAll = new ArrayList<Object>();//组装所有记录
		transAll = pager.getList();
//		for(int initPage = 1 ; initPage <= pager.getPageCount() ; initPage ++){
//			pager.setPageNumber(initPage);
//			Integer totalNum = initPage*5000;
//			if(totalNum <= 20000){
//				pager = transActionSupport.getGroupOrExchangeTransByPager(pager);
//				transAll.addAll(pager.getList());
//			}else{
//				break;
//			}
//		}

		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}else if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") > 0){
				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		pager.getList().clear();
		pager.getList().addAll(newList);


		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		fileName = fromatTime + "Group.xls";
		//开始pio进行Excel导出
		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fromatTime + "团购详细信息");
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 2000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 3000);

		HSSFRow rowTitle = sheet.createRow(0);

		HSSFCell ct0 = rowTitle.createCell(0);
		ct0.setCellValue("商户");
		HSSFCell ct1 = rowTitle.createCell(1);
		ct1.setCellValue("订单号");
		HSSFCell ct2 = rowTitle.createCell(2);
		ct2.setCellValue("下单时间");
		HSSFCell ct3 = rowTitle.createCell(3);
		ct3.setCellValue("支付方式");

		HSSFCell ct4 = rowTitle.createCell(4);
		ct4.setCellValue("现金");
		HSSFCell ct5 = rowTitle.createCell(5);
		ct5.setCellValue("银行积分");
		HSSFCell ct6 = rowTitle.createCell(6);
		ct6.setCellValue("分分通积分");

		HSSFCell ct7 = rowTitle.createCell(7);
		ct7.setCellValue("操作员姓名");
		HSSFCell ct8 = rowTitle.createCell(8);
		ct8.setCellValue("状态");

		int i = 0;
		for (Object tran:transAll) {
			Trans tr = (Trans)tran;
			HSSFRow row = sheet.createRow(++i);

			HSSFCell c0 = row.createCell(0);
			c0.setCellValue(tr.getMerchant().getMstoreFullName());

			HSSFCell c1 = row.createCell(1);
			c1.setCellValue(tr.getId() + "");

			HSSFCell c2 = row.createCell(2);
			c2.setCellValue(tr.getCreateTime());

			HSSFCell c3 = row.createCell(3);
			if("00".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分");
			}else if("01".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分");
			}else if("02".equals(tr.getPayMethod())){
				c3.setCellValue("现金");
			}else if("03".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金");
			}else if("04".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金");
			}else if("05".equals(tr.getPayMethod())){
				c3.setCellValue("分分通积分+现金（比例）");
			}else if("06".equals(tr.getPayMethod())){
				c3.setCellValue("银行积分+现金（比例）");
			}else if("07".equals(tr.getPayMethod())){
               c3.setCellValue("分分通积分+支付宝（比例）");
            }else if("08".equals(tr.getPayMethod())){
              	c3.setCellValue("银行积分+支付宝（比例）");
            }else if("09".equals(tr.getPayMethod())){
             	c3.setCellValue("支付宝");
  			}else{
				c3.setCellValue("其他");
			}
			HSSFCell c4 = row.createCell(4);
			c4.setCellValue(tr.getCurrencyValueRealAll());

			HSSFCell c5 = row.createCell(5);
			c5.setCellValue(tr.getBankPointsValueRealAll());

			HSSFCell c6 = row.createCell(6);
			c6.setCellValue(tr.getFftPointsValueRealAll());


			HSSFCell c7 = row.createCell(7);
			c7.setCellValue((tr.getBelongUserBecode() == null || tr.getBelongUserBecode().length() ==0)?"-":tr.getBelongUserBecode());

			HSSFCell c8 = row.createCell(8);
			if(tr.getState().equals("01")){
				c8.setCellValue("处理中");
			}else if(tr.getState().equals("02")){
				c8.setCellValue("成功");
			}else {
				c8.setCellValue("失败");
			}

		}
		if(isNoTimeCondition){
			HSSFRow row = sheet.createRow(++i);
			HSSFCell c0 = row.createCell(0);
			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
		}
		try {
			workbook.write(os);
			os.close();
			log.info("******************团购excel导出成功*******************");
		} catch (IOException e) {
			log.info("******************团购excel文件生成失败*******************");
		} finally {
			pager = null;
		}
		return Action.SUCCESS;
	}

    /**
     * 精品预售导出
     * @return
     * @throws IOException
     */
    public String expPresellExcel() throws IOException{
  		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
  		deleteExcel(downloadFilePath);
  		logger.info("商户查询精品预售excel导出开始");
  		if(pager==null)
  			 pager=new Trans();
  		pager.setPageSize(10);//每页10条
  		pager.setTransType(TranCommand.PRE_SELL);
  		Map<String,Object> session=ActionContext.getContext().getSession();
  		String userId=(String)session.get("userId");
  		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
  		pager.setMerchantId(m.getId());
  		pager.setState(TransState.tran_success);
  		pager.setOrderType(OrderType.DESC);
  		pager.setIsConsume("1");//已经消费


  		MerchantUserSet merchantUserSet = new MerchantUserSet();
  		merchantUserSet.setUserId(userId);
  		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
  		Set set=clerkMap.entrySet();
  		Iterator it=set.iterator();
  		while(it.hasNext()) {
  			Map.Entry entry=(Map.Entry)it.next();
  			String name = (String)entry.getValue();
  			//如果有操作员昵称查询条件工号找出来
  			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
  				beCode=(String)entry.getKey();
  				break;
  			}
  		}
  		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
  			beCode = "999";
  		}


  		//操作员查询交易条件
  		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
  		isAdminSet = isAdmin;
  		String clerkBeCode=(String)session.get("beCode");
  		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
  		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
  			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
  				//no set value
  				if(!Assert.empty(beCode)){
  					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
  				}
  			}else{//普通操作员
  				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
  			}
  		}else{
  			logger.info("==============非商户===============");
  			return Action.SUCCESS;
  		}
  		boolean isNoTimeCondition = false;
  		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
  			isNoTimeCondition = true;
  			//TODO: 搜索条件没有控制
  			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
  			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  			Calendar calendar = Calendar.getInstance();
  			calendar.setTime(new Date());
  			calendar.add(Calendar.MONTH, - 3);
  			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
  			pager.setEndTime(sdf.format(new Date())+"|23:59:592");
  		}
  		pager = transActionSupport.getDataToRepExcel(pager);



  		List<Object> transAll = new ArrayList<Object>();//组装所有记录
  		transAll = pager.getList();

  		//更新交易积分和价格信息改为单个商品的积分和价格
  		List<Object> templist = pager.getList();
  		List<Trans> newList = new ArrayList<Trans>();
  		for(int i=0;i< templist.size();i++){
  			Trans t = (Trans)templist.get(i);
  			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
  			//实体商品（目前只有实体商品会发送消费券）
  			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
  					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
  				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
  					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
  					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
  					t.setCostpriceTotal(costpriceTotal.toString());
  				}
  				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
  					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
  					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
  					t.setBankPointsValueRealAll(bankPoints.toString());
  				}
  				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
  					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
  					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
  					t.setFftPointsValueRealAll(fftPoints.toString());
  				}
  				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
  					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
  					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
  					t.setCurrencyValueRealAll(currencyValue.toString());
  				}
  			}
  			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
  				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
  				t.setBelongUserBecode(clerkMap.get(str1));
  			}else if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") > 0){
  				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
  				t.setBelongUserBecode(clerkMap.get(str1));
  			}
  			newList.add(t);
  		}
  		pager.getList().clear();
  		pager.getList().addAll(newList);


  		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
  		fileName = fromatTime + "Presell.xls";
  		//开始pio进行Excel导出
  		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
  		HSSFWorkbook workbook=new HSSFWorkbook();
  		HSSFSheet sheet = workbook.createSheet(fromatTime + "精品预售详细信息");
  		sheet.setColumnWidth(0, 8000);
  		sheet.setColumnWidth(1, 5000);
  		sheet.setColumnWidth(2, 5000);
  		sheet.setColumnWidth(3, 6000);
  		sheet.setColumnWidth(4, 2000);
  		sheet.setColumnWidth(5, 3000);
  		sheet.setColumnWidth(6, 3000);
  		sheet.setColumnWidth(7, 4000);
  		sheet.setColumnWidth(8, 3000);

  		HSSFRow rowTitle = sheet.createRow(0);

  		HSSFCell ct0 = rowTitle.createCell(0);
  		ct0.setCellValue("商户");
  		HSSFCell ct1 = rowTitle.createCell(1);
  		ct1.setCellValue("订单号");
  		HSSFCell ct2 = rowTitle.createCell(2);
  		ct2.setCellValue("下单时间");
  		HSSFCell ct3 = rowTitle.createCell(3);
  		ct3.setCellValue("支付方式");

  		HSSFCell ct4 = rowTitle.createCell(4);
  		ct4.setCellValue("现金");
  		HSSFCell ct5 = rowTitle.createCell(5);
  		ct5.setCellValue("银行积分");
  		HSSFCell ct6 = rowTitle.createCell(6);
  		ct6.setCellValue("分分通积分");

  		HSSFCell ct7 = rowTitle.createCell(7);
  		ct7.setCellValue("操作员姓名");
  		HSSFCell ct8 = rowTitle.createCell(8);
  		ct8.setCellValue("状态");

  		int i = 0;
  		for (Object tran:transAll) {
  			Trans tr = (Trans)tran;
  			HSSFRow row = sheet.createRow(++i);

  			HSSFCell c0 = row.createCell(0);
  			c0.setCellValue(tr.getMerchant().getMstoreFullName());

  			HSSFCell c1 = row.createCell(1);
  			c1.setCellValue(tr.getId() + "");

  			HSSFCell c2 = row.createCell(2);
  			c2.setCellValue(tr.getCreateTime());

  			HSSFCell c3 = row.createCell(3);
  			if("00".equals(tr.getPayMethod())){
  				c3.setCellValue("分分通积分");
  			}else if("01".equals(tr.getPayMethod())){
  				c3.setCellValue("银行积分");
  			}else if("02".equals(tr.getPayMethod())){
  				c3.setCellValue("现金");
  			}else if("03".equals(tr.getPayMethod())){
  				c3.setCellValue("分分通积分+现金");
  			}else if("04".equals(tr.getPayMethod())){
  				c3.setCellValue("银行积分+现金");
  			}else if("05".equals(tr.getPayMethod())){
  				c3.setCellValue("分分通积分+现金（比例）");
  			}else if("06".equals(tr.getPayMethod())){
  				c3.setCellValue("银行积分+现金（比例）");
            }else if("07".equals(tr.getPayMethod())){
               c3.setCellValue("分分通积分+支付宝（比例）");
            }else if("08".equals(tr.getPayMethod())){
              	c3.setCellValue("银行积分+支付宝（比例）");
            }else if("09".equals(tr.getPayMethod())){
             	c3.setCellValue("支付宝");
  			}else{
  				c3.setCellValue("其他");
  			}
  			HSSFCell c4 = row.createCell(4);
  			c4.setCellValue(tr.getCurrencyValueRealAll());

  			HSSFCell c5 = row.createCell(5);
  			c5.setCellValue(tr.getBankPointsValueRealAll());

  			HSSFCell c6 = row.createCell(6);
  			c6.setCellValue(tr.getFftPointsValueRealAll());


  			HSSFCell c7 = row.createCell(7);
  			c7.setCellValue((tr.getBelongUserBecode() == null || tr.getBelongUserBecode().length() ==0)?"-":tr.getBelongUserBecode());

  			HSSFCell c8 = row.createCell(8);
  			if(tr.getState().equals("01")){
  				c8.setCellValue("处理中");
  			}else if(tr.getState().equals("02")){
  				c8.setCellValue("成功");
  			}else {
  				c8.setCellValue("失败");
  			}

  		}
  		if(isNoTimeCondition){
  			HSSFRow row = sheet.createRow(++i);
  			HSSFCell c0 = row.createCell(0);
  			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
  		}
  		try {
  			workbook.write(os);
  			os.close();
  			log.info("******************精品预售excel导出成功*******************");
  		} catch (IOException e) {
  			log.info("******************精品预售excel文件生成失败*******************");
  		} finally {
  			pager = null;
  		}
  		return Action.SUCCESS;
  	}


    public String expPresellExcelFinance() throws FileNotFoundException{
    		downloadFilePath = ServletActionContext.getServletContext().getRealPath("upload");
    		deleteExcel(downloadFilePath);

    		pager.setPageSize(10);//每页10条
    		pager.setTransType(TranCommand.PRE_SELL);
    		Map<String,Object> session=ActionContext.getContext().getSession();
    		String userId=(String)session.get("userId");
    		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
    		pager.setMerchantId(m.getId());


    		pager.setState(TransState.tran_success);
    		pager.setOrderType(OrderType.DESC);
    		pager.setIsConsume("1");//已经消费


    		MerchantUserSet merchantUserSet = new MerchantUserSet();
    		merchantUserSet.setUserId(userId);
    		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
    		Set set=clerkMap.entrySet();
    		Iterator it=set.iterator();
    		while(it.hasNext()) {
    			Map.Entry entry=(Map.Entry)it.next();
    			String name = (String)entry.getValue();
    			//如果有操作员昵称查询条件工号找出来
    			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
    				beCode=(String)entry.getKey();
    				break;
    			}
    		}
    		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
    			beCode = "999";
    		}


    		//操作员查询交易条件
    		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
    		isAdminSet = isAdmin;
    		String clerkBeCode=(String)session.get("beCode");
    		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
    		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
    			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
    				//no set value
    				if(!Assert.empty(beCode)){
    					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
    				}
    			}else{//普通操作员
    				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
    			}
    		}else{
    			logger.info("==============非商户===============");
    			return Action.SUCCESS;
    		}
    		boolean isNoTimeCondition = false;
    		if(Assert.empty(pager.getBeginTime()) && Assert.empty(pager.getEndTime())){
    			isNoTimeCondition = true;
    			//TODO: 搜索条件没有控制
    			log.info("搜索时间没有传入具体参数,系统将搜索时间确定在当前时间到前3个月之间");
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(new Date());
    			calendar.add(Calendar.MONTH, - 3);
    			pager.setBeginTime(sdf.format(calendar.getTime())+"|00:00:00");
    			pager.setEndTime(sdf.format(new Date())+"|23:59:59");
    		}
    		//TODO:---------------------------------------------------------------------
    		String becode = (String)getSession(SessionKey.BE_CODE);
    		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
    			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
    				MerchantUserSet mus = new MerchantUserSet();
    				mus.setUserId((String)getSession(SessionKey.USER_ID));
    				mus.setBeCode(becode);
    				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
    			}
    		}
    		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
    		pager.setBelongUserBecodeAuth(belongUserbecode);
    		pager.setFinanceExcel("yes");
    		pager = transActionSupport.getGroupOrExchangeFinance(pager);

    		//更新交易积分和价格信息改为单个商品的积分和价格
    		List<Object> templist = pager.getList();
    		List<Trans> newList = new ArrayList<Trans>();
    		for(int i=0;i< templist.size();i++){
    			Trans t = (Trans)templist.get(i);
    			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
    			//实体商品（目前只有实体商品会发送消费券）
    			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
    					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
    				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
    					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
    					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
    					t.setCostpriceTotal(costpriceTotal.toString());
    				}
    				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
    					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
    					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
    					t.setBankPointsValueRealAll(bankPoints.toString());
    				}
    				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
    					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
    					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
    					t.setFftPointsValueRealAll(fftPoints.toString());
    				}
    				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
    					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
    					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
    					t.setCurrencyValueRealAll(currencyValue.toString());
    				}
    			}
    			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
    				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
    				t.setBelongUserBecode(clerkMap.get(str1));
    			}
    			newList.add(t);
    		}
    		pager.getList().clear();
    		pager.getList().addAll(newList);
    		List<Object> transAll = new ArrayList<Object>();
    		transAll.addAll(pager.getList());
    		String fromatTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    		fileName = fromatTime + "PresellFinance.xls";
    		//开始pio进行Excel导出
    		OutputStream os = new FileOutputStream(new File(downloadFilePath +"/" +fileName));
    		HSSFWorkbook workbook=new HSSFWorkbook();
    		HSSFSheet sheet = workbook.createSheet(fromatTime + "精品预售详细信息（财务）");
    		sheet.setColumnWidth(0, 3000);
    		sheet.setColumnWidth(1, 5000);
    		sheet.setColumnWidth(2, 5000);
    		sheet.setColumnWidth(3, 4000);
    		sheet.setColumnWidth(4, 2000);
    		sheet.setColumnWidth(4, 2000);
    		sheet.setColumnWidth(5, 3000);
    		sheet.setColumnWidth(6, 3000);
    		sheet.setColumnWidth(7, 8000);
    		sheet.setColumnWidth(8, 3000);

    		HSSFRow rowTitle = sheet.createRow(0);
    		HSSFCell ct0 = rowTitle.createCell(0);
    		ct0.setCellValue("订单号");
    		HSSFCell ct1 = rowTitle.createCell(1);
    		ct1.setCellValue("商品名称");
    		HSSFCell ct2 = rowTitle.createCell(2);
    		ct2.setCellValue("更新时间");
    		HSSFCell ct3 = rowTitle.createCell(3);
    		ct3.setCellValue("支付方式");
    		HSSFCell ct4 = rowTitle.createCell(4);
    		ct4.setCellValue("现金");
    		HSSFCell ct5 = rowTitle.createCell(5);
    		ct5.setCellValue("分分通积分");
    		HSSFCell ct6 = rowTitle.createCell(6);
    		ct6.setCellValue("银行积分");
    		HSSFCell ct7 = rowTitle.createCell(7);
    		ct7.setCellValue("操作员工号");
    		HSSFCell ct8 = rowTitle.createCell(8);
    		ct8.setCellValue("操作员姓名");
    		HSSFCell ct9 = rowTitle.createCell(9);
    		ct9.setCellValue("所属门店");
    		HSSFCell ct10 = rowTitle.createCell(10);
    		ct10.setCellValue("状态");

    		int i = 0;
    		for (Object tran:transAll) {
    			Trans tr = (Trans)tran;
    			HSSFRow row = sheet.createRow(++i);

    			HSSFCell c0 = row.createCell(0);
    			c0.setCellValue(tr.getId());

    			HSSFCell c1 = row.createCell(1);
    			c1.setCellValue(tr.getGoodsName());

    			HSSFCell c2 = row.createCell(2);
    			c2.setCellValue(tr.getUpdateTime());

    			HSSFCell c3 = row.createCell(3);
    			if("00".equals(tr.getPayMethod())){
    				c3.setCellValue("分分通积分");
    			}else if("01".equals(tr.getPayMethod())){
    				c3.setCellValue("银行积分");
    			}else if("02".equals(tr.getPayMethod())){
    				c3.setCellValue("现金");
    			}else if("03".equals(tr.getPayMethod())){
    				c3.setCellValue("分分通积分+现金");
    			}else if("04".equals(tr.getPayMethod())){
    				c3.setCellValue("银行积分+现金");
    			}else if("05".equals(tr.getPayMethod())){
    				c3.setCellValue("分分通积分+现金（比例）");
    			}else if("06".equals(tr.getPayMethod())){
    				c3.setCellValue("银行积分+现金（比例）");
                }else if("07".equals(tr.getPayMethod())){
                    c3.setCellValue("分分通积分+支付宝（比例）");
                }else if("08".equals(tr.getPayMethod())){
                	c3.setCellValue("银行积分+支付宝（比例）");
                }else if("09".equals(tr.getPayMethod())){
                	c3.setCellValue("支付宝");
    			}else{
    				c3.setCellValue("其他");
    			}

    			HSSFCell c4 = row.createCell(4);
    			c4.setCellValue(tr.getCurrencyValueRealAll());

    			HSSFCell c5 = row.createCell(5);
    			c5.setCellValue(tr.getFftPointsValueRealAll());

    			HSSFCell c6 = row.createCell(6);
    			c6.setCellValue(tr.getBankPointsValueRealAll());

    			HSSFCell c7 = row.createCell(7);
    			c7.setCellValue(tr.getBeCode());

    			HSSFCell c8 = row.createCell(8);
    			c8.setCellValue(tr.getBeName());

    			HSSFCell c9 = row.createCell(9);
    			c9.setCellValue(tr.getStoreStortName() == null || "".equals(tr.getStoreStortName()) ? "-" : tr.getStoreStortName());

    			HSSFCell c10 = row.createCell(10);
    			if(tr.getState().equals("01")){
    				c10.setCellValue("处理中");
    			}else if(tr.getState().equals("02")){
    				c10.setCellValue("成功");
    			}else {
    				c10.setCellValue("失败");
    			}
    		}
    		if(isNoTimeCondition){
    			HSSFRow row = sheet.createRow(++i);
    			HSSFCell c0 = row.createCell(0);
    			c0.setCellValue("尊敬的商户，您当前未确定搜索时间范围，系统为您检索了当前时间到三个月之间的数据！（"+pager.getBeginTime().split("\\|", 2)[0]+" 到 "+pager.getEndTime().split("\\|", 2)[0]+"）");
    		}
    		try {
    			workbook.write(os);
    			os.close();
    			log.info("******************精品预售excel导出成功*******************");
    		} catch (IOException e) {
    			log.info("******************精品预售excel文件生成失败*******************");
    		} finally {
    			pager = null;
    		}
    		return Action.SUCCESS;
    	}




	//io返回流
	public InputStream getInputStream() throws IOException{
		File file = null;
		try {
			file = new File(downloadFilePath + "/" + fileName);
			log.info("******************IO获取的路径为："+downloadFilePath + "/" + fileName +"*******************");
		} catch (Exception e) {
			log.info("服务器获取路径异常",e);
		}

        return new FileInputStream(file);
	}

	public SellerTransActionSupport getSellerTransActionSupport() {
		return sellerTransActionSupport;
	}


	public void setSellerTransActionSupport(
			SellerTransActionSupport sellerTransActionSupport) {
		this.sellerTransActionSupport = sellerTransActionSupport;
	}

	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

	public String getBeCode() {
		return beCode;
	}

	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}

	public Trans getPager() {
		return pager;
	}
	public void setPager(Trans pager) {
		this.pager = pager;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}

	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public String getBeName() {
		return beName;
	}

	public void setBeName(String beName) {
		this.beName = beName;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public Map<String, String> getClerkMap() {
		return clerkMap;
	}

	public void setClerkMap(Map<String, String> clerkMap) {
		this.clerkMap = clerkMap;
	}

	public String getIsAdminSet() {
		return isAdminSet;
	}

	public void setIsAdminSet(String isAdminSet) {
		this.isAdminSet = isAdminSet;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

}
