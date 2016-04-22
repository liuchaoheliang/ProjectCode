/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:TaskReportTempExport.java
 * Package Name:com.froad.timer.task.impl
 * Date:2015年8月19日上午9:48:17
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.timer.task.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.froad.db.mysql.mappers.OrgMapper;
import com.froad.db.mysql.rp_mappers.ReportBossMerchantCategorySaleMapper;
import com.froad.db.mysql.rp_mappers.ReportBossOrgMerchantMapper;
import com.froad.db.mysql.rp_mappers.ReportBossOrgSaleMapper;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BatchChunk;
import com.froad.po.Org;
import com.froad.po.ReportBossMerchantCategorySale;
import com.froad.po.ReportBossOrgMerchant;
import com.froad.po.ReportBossOrgSale;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.task.AbstractProTask;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.ReportExcelUtil;
import com.froad.util.HttpClientUtil;
import com.froad.util.MessageServiceClient;
import com.froad.util.PropertiesUtil;
import com.froad.util.TaskTimeUtil;

/**
 * ClassName: TaskReportTempExport <br/>
 * Reason: 临时导出报表 <br/>
 * Date: 2015年8月19日 上午9:48:17
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskReportTempExport extends AbstractProTask {
	
	private static final String FILE_NAME = "boss-report";

	private static final String EXCEL_REPORT_USER_ROOTPATH = PropertiesUtil.getProperty(FILE_NAME, "excel.report.rootpath");
	
	private static final String EXCEL_FILE_USER_NAME = PropertiesUtil.getProperty(FILE_NAME, "excel.file.user.name");
	
	
	private static final String EXCEL_NAME_ORG_SALE_GROUP = PropertiesUtil.getProperty(FILE_NAME, "excel.name.org.sale.group");
	private static final String EXCEL_NAME_ORG_SALE_PRESELL = PropertiesUtil.getProperty(FILE_NAME, "excel.name.org.sale.presell");
	private static final String EXCEL_NAME_ORG_SALE_SPECIAL = PropertiesUtil.getProperty(FILE_NAME, "excel.name.org.sale.special");
	private static final String EXCEL_NAME_ORG_SALE_FACE2FACE = PropertiesUtil.getProperty(FILE_NAME, "excel.name.org.sale.face2face");
	private static final String EXCEL_NAME_ORG_SALE_TOTAL = PropertiesUtil.getProperty(FILE_NAME, "excel.name.org.sale.total");
	
	private static final String EXCEL_NAME_ORG_MERCHANT_TOTAL = PropertiesUtil.getProperty(FILE_NAME, "excel.name.org.merchant.total");
	
	private static final String EXCEL_NAME_MERCHANT_CATEGORY_TOTAL = PropertiesUtil.getProperty(FILE_NAME, "excel.name.merchant.category.total");
	
	
	private OrgMapper orgMapper;
	
	private ReportBossOrgSaleMapper reportBossOrgSaleMapper;
	
	private ReportBossOrgMerchantMapper reportBossOrgMerchantMapper;

	private ReportBossMerchantCategorySaleMapper reportBossMerchantCategorySaleMapper;

	public TaskReportTempExport(String name, TaskBean task) {
		super(name, task);
	}

	@Override
	public void initialize() {
		
		orgMapper = sqlSession.getMapper(OrgMapper.class);
		
		reportBossOrgSaleMapper = rpSqlSession.getMapper(ReportBossOrgSaleMapper.class);
		
		reportBossOrgMerchantMapper = rpSqlSession.getMapper(ReportBossOrgMerchantMapper.class);
		
		reportBossMerchantCategorySaleMapper = rpSqlSession.getMapper(ReportBossMerchantCategorySaleMapper.class);
		
	}

	@Override
	public void processByChunk(BatchChunk batchChunk) {
		// log message
		StringBuffer logMsg = null;
		logMsg  = new StringBuffer();
		logMsg.append("Start process batch_id:batchDate:jobName:chunkId ");
		logMsg.append(batchChunk.getBatchId());
		logMsg.append(":");
		logMsg.append(batchChunk.getBatchDate());
		logMsg.append(":");
		logMsg.append(batchChunk.getJobName());
		logMsg.append(":");
		logMsg.append(batchChunk.getChunkId());
		LogCvt.info(logMsg.toString());
		
		Date begDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayBegin(batchChunk.getLastBatchDate()) : null;
		Date endDate = batchChunk.getLastBatchDate() != 0 ? TaskTimeUtil.convertToDayEnd(batchChunk.getLastBatchDate()) : TaskTimeUtil.convertToDayBegin(batchChunk.getBatchDate());
		
		String folder = null, reportPath = null, userDataPath = null;
		Date today = new Date();
		// 获取一级机构
		List<Org> orgList1 = orgMapper.findOrgList(null, OrgLevelEnum.orgLevel_one.getLevel());
		for (int i = 0; orgList1 != null && i < orgList1.size(); i++) {
			Org org1 = orgList1.get(i);
			
			/**判断机构所属的client是否已配置生成报表的信息*/
			try{
				if(Checker.isEmpty(PropertiesUtil.getProperty(FILE_NAME, org1.getClientId() + ".excel.file.name"))){
					throw new Exception("配置不存在！");
				}
			}catch(Exception e){
				LogCvt.info(FILE_NAME+"下未配置"+org1.getClientId()+"报表的相关信息，不生成excel……"+e.getMessage(),e);
				continue;
			}
			
			
			// like 'd:/home/20150824/20150824184729/'
			folder = EXCEL_REPORT_USER_ROOTPATH + File.separator + org1.getClientId() + File.separator 
					+ DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, today) + File.separator 
					+ DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, today) + File.separator;
			File folderFile = new File(folder);
			if (!folderFile.exists()) {
				folderFile.mkdirs();
			}
			
			HSSFWorkbook workbook = null;
			// 销量数据
			workbook = exportOrgSale(batchChunk, org1, endDate, workbook);
			
			workbook = exportOrgMerchant(batchChunk, org1, endDate, workbook);
			
			workbook = exportMerchantCategorySale(batchChunk, org1, endDate, workbook);
			
			reportPath = folder + PropertiesUtil.getProperty(FILE_NAME, org1.getClientId() + ".excel.file.name") + showDateKey(begDate, endDate) + ".xls";
			writeExcel(reportPath, workbook);
			
			// 用户数据
			userDataPath = folder + EXCEL_FILE_USER_NAME + "_" + org1.getClientId() + ".xls";
			exportUser(begDate, endDate, org1, userDataPath);
			
			// 如果是周一，要生成日报和周报
			if (DateUtil.isMonday()) {
				LogCvt.debug("统计周报");
				
				BatchChunk weekBatch = (BatchChunk)BeanUtil.copyProperties(BatchChunk.class, batchChunk);
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
				calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				Date lastWeekBegDate = calendar.getTime();
				Date lastWeekEndDate = DateUtil.skipDateTime(calendar.getTime(), 6);
				
				int weekBatchDate = DateUtil.getWeekOfMonth(lastWeekBegDate);
				weekBatch.setBatchDate(weekBatchDate);
				
				HSSFWorkbook weekWb = null;
				
				weekWb = exportOrgSale(weekBatch, org1, lastWeekEndDate, weekWb);
				
				weekWb = exportOrgMerchant(weekBatch, org1, lastWeekEndDate, weekWb);
				
				weekWb = exportMerchantCategorySale(weekBatch, org1, lastWeekEndDate, weekWb);
				
				String weekReportPath = folder + PropertiesUtil.getProperty(FILE_NAME, org1.getClientId() + ".excel.file.name") + showDateKey(lastWeekBegDate, lastWeekEndDate) + "周报表.xls";
				writeExcel(weekReportPath, weekWb);
				
				// 发送日报+周报(周报不需要统计用户数据)邮件
				if(PropertiesUtil.getProperty(FILE_NAME, org1.getClientId() + ".mail.issend").equals("true")){
					sendEmail(org1.getClientId(), new File(reportPath), new File(userDataPath), new File(weekReportPath));
				}
			} else {
				// 发送日报邮件
				if(PropertiesUtil.getProperty(FILE_NAME, org1.getClientId() + ".mail.issend").equals("true")){
					sendEmail(org1.getClientId(), new File(reportPath), new File(userDataPath));
				}
			}
			
		}
		
	}
	
	public HSSFWorkbook exportOrgSale(BatchChunk batchChunk, Org org1, Date endDate, HSSFWorkbook book){
		List<String> header = new ArrayList<String>();
		header.add("机构号");
		header.add("行名");
		header.add("当期销售件数");
		header.add("当期销售金额（元）");
		header.add("累计销售件数");
		header.add("累计销售金额（元）");
		
		// 团购
		String subTitle = null, mmdd = DateUtil.formatDateTime("MM月dd日", endDate);
		subTitle = "截至 " + mmdd + "  “团购”销售情况表";
		List<ReportBossOrgSale> groupList = reportBossOrgSaleMapper.findByClientId(org1.getClientId(), batchChunk.getBatchDate(), OrderType.group_merchant.getCode());
		book = exportByOrderType(batchChunk, org1, groupList, EXCEL_NAME_ORG_SALE_GROUP, subTitle, header, book, 1);

		// 面对面
		subTitle = "截至 " + mmdd + "  “面对面”销售情况表";
		List<ReportBossOrgSale> face2faceList = reportBossOrgSaleMapper.findByClientId(org1.getClientId(), batchChunk.getBatchDate(), OrderType.face2face.getCode());
		book = exportByOrderType(batchChunk, org1, face2faceList, EXCEL_NAME_ORG_SALE_FACE2FACE, subTitle, header, book, 2);

		// 名优特惠
		subTitle = "截至 " + mmdd + "  “名优特惠”销售情况表";
		List<ReportBossOrgSale> specialList = reportBossOrgSaleMapper.findByClientId(org1.getClientId(), batchChunk.getBatchDate(), OrderType.special_merchant.getCode());
		book = exportByOrderType(batchChunk, org1, specialList, EXCEL_NAME_ORG_SALE_SPECIAL, subTitle, header, book, 3);
		
		// 预售
		subTitle = "截至 " + mmdd + " “精品预售”销售情况表";
		List<ReportBossOrgSale> presellList = reportBossOrgSaleMapper.findByClientId(org1.getClientId(), batchChunk.getBatchDate(), OrderType.presell_org.getCode());
		book = exportByOrderType(batchChunk, org1, presellList, EXCEL_NAME_ORG_SALE_PRESELL, subTitle, header, book, 4);
		
		// 汇总
		List<List<String>> data = new ArrayList<List<String>>();
		ReportBossOrgSale totalOrgSale = null; // 每一页的总计
		totalOrgSale = new ReportBossOrgSale();
		totalOrgSale.setOrgCode("");
		totalOrgSale.setOrgName("总计");
		totalOrgSale.setCurSoldCount(0L);
		totalOrgSale.setCurSoldAmount(0L);
		totalOrgSale.setTotalSoldCount(0L);
		totalOrgSale.setTotalSoldAmount(0L);
		
		Map<String, ReportBossOrgSale> presellMap = listToMap(presellList);
		Map<String, ReportBossOrgSale> specialMap = listToMap(specialList);
		Map<String, ReportBossOrgSale> face2faceMap = listToMap(face2faceList);
		ReportBossOrgSale tmpOrgSale = null;
		String key = null;
		// 团购的机构行数和汇总表的机构行数应该是一样的，所以遍历团购即可
		for (int i = 0; i < groupList.size(); i++) {
			tmpOrgSale = groupList.get(i);
			ReportBossOrgSale orgSale = (ReportBossOrgSale)BeanUtil.copyProperties(ReportBossOrgSale.class, tmpOrgSale);
			
			key = tmpOrgSale.getClientId() + "_" + tmpOrgSale.getOrgCode();
			
			tmpOrgSale = presellMap.get(key);
			orgSale.setCurSoldCount(orgSale.getCurSoldCount() + tmpOrgSale.getCurSoldCount());
			orgSale.setCurSoldAmount(orgSale.getCurSoldAmount() + tmpOrgSale.getCurSoldAmount());
			orgSale.setTotalSoldCount(orgSale.getTotalSoldCount() + tmpOrgSale.getTotalSoldCount());
			orgSale.setTotalSoldAmount(orgSale.getTotalSoldAmount() + tmpOrgSale.getTotalSoldAmount());
			
			tmpOrgSale = specialMap.get(key);
			orgSale.setCurSoldCount(orgSale.getCurSoldCount() + tmpOrgSale.getCurSoldCount());
			orgSale.setCurSoldAmount(orgSale.getCurSoldAmount() + tmpOrgSale.getCurSoldAmount());
			orgSale.setTotalSoldCount(orgSale.getTotalSoldCount() + tmpOrgSale.getTotalSoldCount());
			orgSale.setTotalSoldAmount(orgSale.getTotalSoldAmount() + tmpOrgSale.getTotalSoldAmount());
			
			tmpOrgSale = face2faceMap.get(key);
			orgSale.setCurSoldCount(orgSale.getCurSoldCount() + tmpOrgSale.getCurSoldCount());
			orgSale.setCurSoldAmount(orgSale.getCurSoldAmount() + tmpOrgSale.getCurSoldAmount());
			orgSale.setTotalSoldCount(orgSale.getTotalSoldCount() + tmpOrgSale.getTotalSoldCount());
			orgSale.setTotalSoldAmount(orgSale.getTotalSoldAmount() + tmpOrgSale.getTotalSoldAmount());
			
			if (orgSale.getTotalSoldCount() == 0) {
				LogCvt.info("orgName=" + orgSale.getOrgName() + ", no sale data.");
				continue;
			}
			
			data.add(convertOrgSaleRow(orgSale));
			
			totalOrgSale.setCurSoldCount(totalOrgSale.getCurSoldCount() + orgSale.getCurSoldCount());
			totalOrgSale.setCurSoldAmount(totalOrgSale.getCurSoldAmount() + orgSale.getCurSoldAmount());
			totalOrgSale.setTotalSoldCount(totalOrgSale.getTotalSoldCount() + orgSale.getTotalSoldCount());
			totalOrgSale.setTotalSoldAmount(totalOrgSale.getTotalSoldAmount() + orgSale.getTotalSoldAmount());
		}
		data.add(convertOrgSaleRow(totalOrgSale));
		
		subTitle = "截至 " + mmdd + "  “汇总”销售情况表";
		book = createSheet(EXCEL_NAME_ORG_SALE_TOTAL, subTitle, batchChunk.getBatchDate(), header, data, book, 0);
		
		return book;
	}
	
	public HSSFWorkbook exportOrgMerchant(BatchChunk batchChunk, Org org1, Date endDate, HSSFWorkbook book){
		List<String> header = new ArrayList<String>();
		header.add("机构号");
		header.add("行名");
		header.add("本地商圈新增（家）");
		header.add("本地商圈累计（家）");
		header.add("名优特惠新增（家）");
		header.add("名优特惠累计（家）");
		
		List<ReportBossOrgMerchant> orgMerchantList = reportBossOrgMerchantMapper.findByClientId(org1.getClientId(), batchChunk.getBatchDate());

		ReportBossOrgMerchant orgMerchant = null, totalOrgMerchant = new ReportBossOrgMerchant();
		totalOrgMerchant.setOrgCode("");
		totalOrgMerchant.setOrgName("总计");
		totalOrgMerchant.setAddnewGroupCount(0);
		totalOrgMerchant.setTotalGroupCount(0);
		totalOrgMerchant.setAddnewPreferentCount(0);
		totalOrgMerchant.setTotalPreferentCount(0);
		List<List<String>> data = new ArrayList<List<String>>();
		for (int i = 0; orgMerchantList != null && i < orgMerchantList.size(); i++) {
			orgMerchant = orgMerchantList.get(i);
			
			if (orgMerchant.getTotalGroupCount() == 0 && orgMerchant.getTotalPreferentCount() == 0) {
				continue;
			}
			
			data.add(convertOrgMerchantRow(orgMerchant));
			
			totalOrgMerchant.setAddnewGroupCount(totalOrgMerchant.getAddnewGroupCount() + orgMerchant.getAddnewGroupCount());
			totalOrgMerchant.setTotalGroupCount(totalOrgMerchant.getTotalGroupCount() + orgMerchant.getTotalGroupCount());
			totalOrgMerchant.setAddnewPreferentCount(totalOrgMerchant.getAddnewPreferentCount() + orgMerchant.getAddnewPreferentCount());
			totalOrgMerchant.setTotalPreferentCount(totalOrgMerchant.getTotalPreferentCount() + orgMerchant.getTotalPreferentCount());
		}
		data.add(convertOrgMerchantRow(totalOrgMerchant));
		
		String mmdd = DateUtil.formatDateTime("MM月dd日", endDate);
		String subTitle = "截至 " + mmdd + " 商户汇总表";
		return createSheet(EXCEL_NAME_ORG_MERCHANT_TOTAL, subTitle, batchChunk.getBatchDate(), header, data, book, 5);
	}

	public HSSFWorkbook exportMerchantCategorySale(BatchChunk batchChunk, Org org1, Date endDate, HSSFWorkbook book){
		List<String> header = new ArrayList<String>();
		header.add("商户分类");
		header.add("累计销售件数");
		header.add("累计销售件数占比");
		header.add("累计销售金额");
		header.add("累计销售金额占比");
		
		List<ReportBossMerchantCategorySale> mcsList = reportBossMerchantCategorySaleMapper.findByClientId(org1.getClientId(), batchChunk.getBatchDate());
		
		ReportBossMerchantCategorySale mcSale = null, totalMcSalce = new ReportBossMerchantCategorySale();
		totalMcSalce.setMerchantCategoryName("总计");
		totalMcSalce.setTotalSoldCount(0L);
		totalMcSalce.setTotalSoldCountPercent("100");
		totalMcSalce.setTotalSoldAmount(0L);
		totalMcSalce.setTotalSoldAmountPercent("100");
		List<List<String>> data = new ArrayList<List<String>>();
		for (int i = 0; mcsList != null && i < mcsList.size(); i++) {
			mcSale = mcsList.get(i);
			
			if (mcSale.getTotalSoldCount() == 0) {
				LogCvt.info("merchantCategoryName=" + mcSale.getMerchantCategoryName() + ", no sale data.");
				continue;
			}
			
			data.add(convertMerchantCategorySaleRow(mcSale));
			
			totalMcSalce.setTotalSoldCount(totalMcSalce.getTotalSoldCount() + mcSale.getTotalSoldCount());
			totalMcSalce.setTotalSoldAmount(totalMcSalce.getTotalSoldAmount() + mcSale.getTotalSoldAmount());
		}
		data.add(convertMerchantCategorySaleRow(totalMcSalce));
		
		String mmdd = DateUtil.formatDateTime("MM月dd日", endDate);
		String subTitle = "截至 " + mmdd + " 本地商圈版块商户分类表（含团购和面对面交易）";
		return createSheet(EXCEL_NAME_MERCHANT_CATEGORY_TOTAL, subTitle, batchChunk.getBatchDate(), header, data, book, 6);
	}

	public void exportUser(Date begin, Date end, Org org1, String filePath){
		downloadUserData(filePath, org1.getClientId(), begin, end);
	}
	
	/**
	 * 分订单类型导出
	 * 
	 * @param batchChunk 当前批次
	 * @param org1 一级机构
	 * @param orderType 订单类型
	 * @param fileName 文件名
	 * @param header 表格的标题数据
	 * 
	 * @return List<List<String>> 表格的数据
	 */
	private HSSFWorkbook exportByOrderType(BatchChunk batchChunk, Org org1, List<ReportBossOrgSale> orgSaleList, 
			String fileName, String subTitle, List<String> header, HSSFWorkbook book, int sheetIndex){
		List<List<String>> data = new ArrayList<List<String>>();
		
		ReportBossOrgSale totalOrgSale = null; // 每一页的总计
		totalOrgSale = new ReportBossOrgSale();
		totalOrgSale.setOrgCode("");
		totalOrgSale.setOrgName("总计");
		totalOrgSale.setCurSoldCount(0L);
		totalOrgSale.setCurSoldAmount(0L);
		totalOrgSale.setTotalSoldCount(0L);
		totalOrgSale.setTotalSoldAmount(0L);
		
		ReportBossOrgSale orgSale = null;
		for (int i = 0; orgSaleList != null && i < orgSaleList.size(); i++) {
			orgSale = orgSaleList.get(i);
			
			if (orgSale.getTotalSoldCount() == 0) {
				LogCvt.info("orgName=" + orgSale.getOrgName() + ", no sale data.");
				continue;
			}
			
			data.add(convertOrgSaleRow(orgSale));
			
			totalOrgSale.setCurSoldCount(totalOrgSale.getCurSoldCount() + orgSale.getCurSoldCount());
			totalOrgSale.setCurSoldAmount(totalOrgSale.getCurSoldAmount() + orgSale.getCurSoldAmount());
			totalOrgSale.setTotalSoldCount(totalOrgSale.getTotalSoldCount() + orgSale.getTotalSoldCount());
			totalOrgSale.setTotalSoldAmount(totalOrgSale.getTotalSoldAmount() + orgSale.getTotalSoldAmount());
			
		}
		data.add(convertOrgSaleRow(totalOrgSale));
		
		return createSheet(fileName, subTitle, batchChunk.getBatchDate(), header, data, book, sheetIndex);
		
	}
	
	private void writeExcel(String filePath, HSSFWorkbook book){
		//生成excel
		OutputStream output = null;
		try {
			output = new FileOutputStream(filePath);
			book.write(output);
		} catch (Exception e) {
			LogCvt.error("写Excel异常：filePath=" + filePath, e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 创建Sheet
	 */
	private HSSFWorkbook createSheet(String sheetName, String subTitle, int reportDate, List<String> header, List<List<String>> data, HSSFWorkbook book, int sheetIndex){
		return ReportExcelUtil.generate(book, sheetName, subTitle, header, data, "", sheetIndex);
	}
	
	private List<String> convertOrgSaleRow(ReportBossOrgSale orgSale){
		List<String> row = new ArrayList<String>();
		row.add(orgSale.getOrgCode());
		row.add(orgSale.getOrgName());
		row.add(orgSale.getCurSoldCount() + "");
		String curSoldAmount = new DecimalFormat("#.00").format(Arith.div(orgSale.getCurSoldAmount().doubleValue(), 1000d, 2));
		row.add(curSoldAmount.equals(".00") ? "0" : curSoldAmount);
		row.add(orgSale.getTotalSoldCount() + "");
		String totalSoldAmount = new DecimalFormat("#.00").format(Arith.div(orgSale.getTotalSoldAmount().doubleValue(), 1000d, 2));
		row.add(totalSoldAmount.equals(".00") ? "0" : totalSoldAmount);
		return row;
	}
	
	private List<String> convertMerchantCategorySaleRow(ReportBossMerchantCategorySale mcSale){
		List<String> row = new ArrayList<String>();
		row.add(mcSale.getMerchantCategoryName());
		row.add(mcSale.getTotalSoldCount() + "");
		row.add(Arith.div(new Double(mcSale.getTotalSoldCountPercent()), 100d) + "");
		String totalSoldAmount = new DecimalFormat("#.00").format(Arith.div(mcSale.getTotalSoldAmount().doubleValue(), 1000d, 2));
		row.add(totalSoldAmount.equals(".00") ? "0" : totalSoldAmount);
		row.add(Arith.div(new Double(mcSale.getTotalSoldAmountPercent()), 100d) + "");
		return row;
	}
	
	private List<String> convertOrgMerchantRow(ReportBossOrgMerchant orgMerchant){
		List<String> row = new ArrayList<String>();
		row.add(orgMerchant.getOrgCode());
		row.add(orgMerchant.getOrgName());
		row.add(orgMerchant.getAddnewGroupCount() + "");
		row.add(orgMerchant.getTotalGroupCount() + "");
		row.add(orgMerchant.getAddnewPreferentCount() + "");
		row.add(orgMerchant.getTotalPreferentCount() + "");
		return row;
	}
	
	/**
	 * 统计汇总表的数据
	 */
	public Map<String, ReportBossOrgSale> addToAll(Map<String, ReportBossOrgSale> totalData, List<ReportBossOrgSale> orgSaleList){
		String key = null;
		for (int i = 0; orgSaleList != null && i < orgSaleList.size(); i++) {
			ReportBossOrgSale orgSale = orgSaleList.get(i);
			key = orgSale.getClientId() + "_" + orgSale.getOrgCode();
			ReportBossOrgSale target = totalData.get(key);
			if (target == null) {
				totalData.put(key, orgSale);
			} else { 
				target.setCurSoldCount(target.getCurSoldCount() + orgSale.getCurSoldCount());
				target.setCurSoldAmount(target.getCurSoldAmount() + orgSale.getCurSoldAmount());
				target.setTotalSoldCount(target.getTotalSoldCount() + orgSale.getTotalSoldCount());
				target.setTotalSoldAmount(target.getTotalSoldAmount() + orgSale.getTotalSoldAmount());
			} 
		} 
		
		return totalData;
	}
	

	/**
	 * 统计汇总表的数据
	 */
	public Map<String, ReportBossOrgSale> listToMap(List<ReportBossOrgSale> orgSaleList){
		Map<String, ReportBossOrgSale> mapData = new HashMap<String, ReportBossOrgSale>();
		String key = null;
		for (int i = 0; orgSaleList != null && i < orgSaleList.size(); i++) {
			ReportBossOrgSale orgSale = orgSaleList.get(i);
			key = orgSale.getClientId() + "_" + orgSale.getOrgCode();
			mapData.put(key, orgSale);
		} 
		
		return mapData;
	}
	
	public static void sendEmail(String clientId, File... files){
		Map<String, byte[]> atts = new HashMap<String, byte[]>();
		
		try {
			
			for (File file : files) {
				atts.put(file.getName(), FileUtils.readFileToByteArray(file));
			}
			
			MessageServiceClient.sendEmailByBossReport(clientId, "", atts);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 下载用户数据
	 */
	public void downloadUserData(String filePath, String clientId, Date startDate, Date endDate){
		String url = PropertiesUtil.getProperty(FILE_NAME, clientId + ".excel.report.user.url") + "&startDate=" + DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, startDate) 
				+ "&endDate=" + DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, endDate);
		
		try {
			HttpClientUtil.downloadFile(filePath, url);
		} catch (IOException e) {
			LogCvt.error("下载用户数据报表异常: url=" + url, e);
		}
		
	}
	
	public String showDateKey(Date begDate, Date endDate) {
		String dateKey = "";
		String begStr = DateUtil.formatDateTime("MMdd", begDate);
		String endStr = DateUtil.formatDateTime("MMdd", endDate);
		if (begStr.endsWith(endStr)) {
			dateKey = "(" + begStr + ")";
		} else {
			dateKey = "(" + begStr + "-" + endStr + ")";
		}
		return dateKey;
	}
	
}
