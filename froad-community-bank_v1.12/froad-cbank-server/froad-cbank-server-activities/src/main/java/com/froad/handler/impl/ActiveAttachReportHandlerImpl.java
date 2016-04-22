package com.froad.handler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActiveAttachReportMapper;
import com.froad.exceptions.FroadBusinessException;
import com.froad.handler.ActiveAttachReportHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveAttachReport;
import com.froad.util.DateUtil;
import com.froad.util.ExcelWriter;

public class ActiveAttachReportHandlerImpl implements ActiveAttachReportHandler {

	/**
	 * generateActiveAttachReport
	 * 
	 * @param activeId,clientId,mainReportDir
	 * @param activeBaseRule
	 * @return String 受影响行数
	 * 
	 */
	@Override
	public String generateActiveAttachReport(String activeId, String clientId, String mainReportDir, String activeType)
	{

		List<ActiveAttachReport> result = null;
		SqlSession sqlSession = null;
		ActiveAttachReportMapper activeAttachReportMapper = null;
		String path = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeAttachReportMapper = sqlSession.getMapper(ActiveAttachReportMapper.class);
			Page<ActiveAttachReport> initPage = new Page<ActiveAttachReport>();
			result = activeAttachReportMapper.getActiveAttachReport(activeId, clientId);
			initPage.setResultsContent(result);
			ExcelWriter excelWriter = new ExcelWriter(initPage.getPageSize());
			path = generateExcel(initPage, excelWriter, mainReportDir , activeId, clientId, activeType);		
							
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			try {
			} catch (Exception e1) {
				LogCvt.error(e.getMessage(), e);
			}
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return path;
	}
	
	
	private String generateExcel(Page<ActiveAttachReport> page,
			ExcelWriter excelWriter, String mainReportDir,
			String activeId, String clientId, String activeType) throws IOException, FroadBusinessException
	{
		String sheetName = "满减活动附表"; // sheetName
		String []header = null;
		
		header = new String[]{"序号", "订单编号", "订单金额", "支付时间", "所属商户", "商品名称", 
				"商品单价", "商品数量", "金额小计", "返现内容", "用户编号" , "电话号码", "所属客户端","账单号","退款金额","退款编号"};
		System.out.println(" head : " + header.length);
		List<ActiveAttachReport> mainList = (List<ActiveAttachReport>) page.getResultsContent();
		
		int count = null == mainList ? 0 : mainList.size();
		
		List<List<String>> data = null; // 表数据
		
		data = new ArrayList<List<String>>();
		for (int i = 0; i < count; i++) {
			ActiveAttachReport activeAttachReport = mainList.get(i);
			if(null == activeAttachReport)continue;
			
			Integer priceCount = activeAttachReport.getPriceCount();
			Integer VipPriceCount = activeAttachReport.getVipPriceCount();
			Double price = (double)activeAttachReport.getProductPrice();
			Double vipPrice = (double)activeAttachReport.getProductVipPrice();
			Double priceMarket = (double)activeAttachReport.getPriceMarket();
			Double vipPriceMarket = (double)activeAttachReport.getVipPriceMarket();
			
			String[] row = new String[header.length]; // 行数据
			row[0] = (i + 1) + "";
			row[1] = activeAttachReport.getOrderId();
			row[2] = ((double)activeAttachReport.getOrderMoney())/1000 +"";
			if(activeAttachReport.getPayTime()!= null)
			{
				row[3] = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(activeAttachReport.getPayTime().getTime()));
			}
			else
			{
				row[3] = null;
			}
			row[4] = activeAttachReport.getMerchantName();
			//row[4] = activeAttachReport.getMerchantId();
			row[5] = activeAttachReport.getProductName();
			row[6] = "普通单价: " + price/1000 +" 元, VIP单价: " + vipPrice/1000 + " 元";
			row[7] = "普通价格数量: "+activeAttachReport.getPriceCount()+", VIP价格数量: "+activeAttachReport.getVipPriceCount();
			row[8] = "普通价实际小计金额: "+ (price * priceCount - priceMarket)/1000 +" 元, VIP实际小计金额:" + (vipPrice * VipPriceCount - vipPriceMarket)/1000+" 元";
			row[9] = "普通价格优惠金额: "+priceMarket/1000 +" 元 , VIP价格优惠金额: "+vipPriceMarket/1000+" 元";
			row[10] = activeAttachReport.getMemberCode();
			row[11] = activeAttachReport.getPhone();
			//row[12] = activeAttachReport.getClientId();
			row[12] = activeAttachReport.getClientName();
			row[13] = activeAttachReport.getPayBillNO();
			row[14] = activeAttachReport.getReturnMoney();
			row[15] = activeAttachReport.getReturnBillNO();
			LogCvt.debug("ROW:" + ArrayUtils.toString(row));
			data.add(Arrays.asList(row));
		}
		//客户端编号_ MJ主表_yyyy-MM-dd
		
		String date = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, new Date());
		String excelName = clientId+"_"+ activeType + "附表_"+date;
		//excelWriter.setCurrentExcelName(mainReportDir+excelName);
		excelWriter.writeCsv(Arrays.asList(header), data, sheetName, excelName, mainReportDir);
		String name = excelWriter.getLocalFileDir()+"/"+excelWriter.getCurrentExcelName(); //路径地址
		return name;
	}

}
