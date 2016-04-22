package com.froad.thirdparty.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.logback.LogCvt;
import com.froad.thirdparty.UserSpecFunc;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.ExcelReader;
import com.froad.util.HessianUrlUtil;
import com.pay.user.dto.MemberSignSpecDto;
import com.pay.user.dto.Result;
import com.pay.user.service.UserSpecService;

public class UserSpecFuncImpl implements UserSpecFunc {

	private static HessianProxyFactory factory = new HessianProxyFactory();
	
	@Override
	public Result queryMemberByCardBin(String cardBin, String bankId, Date begDate, Date endDate) {
		Result result = null;
		try {
			result = ((UserSpecService)factory.create(UserSpecService.class, HessianUrlUtil.USER_URL)).queryMemberByCardBin(cardBin, bankId, begDate, endDate);
		} catch (Exception e) {
			LogCvt.error("hessian连接异常", e);
		}
		return result;
	}
	
	@Override
	public Map<String, List<MemberSignSpecDto>> queryMemberByBankId(String bankId, Date begDate, Date endDate) throws IOException {
		StringBuilder urlStr = null;
		Map<String, List<MemberSignSpecDto>> map = null;
		InputStream is = null;
		URL url = null;
		HttpURLConnection httpUrl = null;
		
		XSSFSheet sheet = null;
		XSSFRow row = null;
		List<MemberSignSpecDto> signs = null;
		MemberSignSpecDto sign = null;
		try {
			urlStr = new StringBuilder(HessianUrlUtil.USER_REPORT_URL);
			urlStr.append("&startDate=").append(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, begDate))
	    	.append("&endDate=").append(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, endDate))
	    	.append("&bankId=").append(bankId);
			
			LogCvt.info("签约用户报表下载url: "+urlStr.toString());
			
			url = new URL(urlStr.toString());
			httpUrl = (HttpURLConnection)url.openConnection();
            httpUrl.connect();
            is = new BufferedInputStream(httpUrl.getInputStream());
            
            XSSFWorkbook wb = ExcelReader.readExcelContent(is);
            map = new HashMap<String, List<MemberSignSpecDto>>();
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
    			sheet = wb.getSheetAt(numSheet);
    			
    			if (sheet == null) {
    				continue;
    			}
    			
    			// Read the Row
    			for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
    				row = sheet.getRow(rowNum);
    				if (row != null) {
    					sign = new MemberSignSpecDto();
    					XSSFCell memberCode = row.getCell(0);
    					XSSFCell cardHostName = row.getCell(1);
    					XSSFCell validStatus = row.getCell(2);
    					XSSFCell signTime = row.getCell(3);
    					XSSFCell loginID = row.getCell(4);
    					XSSFCell createChannel = row.getCell(5);
    					XSSFCell mobile = row.getCell(6);
    					XSSFCell createTime = row.getCell(7);
    					XSSFCell cardbin = row.getCell(8);
    					XSSFCell isVip = row.getCell(9);
    					
    					String memberCodeStr = ExcelReader.getCellFormatValue(memberCode).trim();
    					String cardHostNameStr = ExcelReader.getCellFormatValue(cardHostName).trim();
    					String validStatusStr = ExcelReader.getCellFormatValue(validStatus).trim();
    					String signTimeStr = ExcelReader.getCellFormatValue(signTime).trim();
    					String loginIDStr = ExcelReader.getCellFormatValue(loginID).trim();
    					String createChannelStr = ExcelReader.getCellFormatValue(createChannel).trim();
    					String mobileStr = ExcelReader.getCellFormatValue(mobile).trim();
    					String createTimeStr = ExcelReader.getCellFormatValue(createTime).trim();
    					String cardbinStr = ExcelReader.getCellFormatValue(cardbin).trim();
    					String isVipStr = ExcelReader.getCellFormatValue(isVip).trim();
    					
    					sign.setMemberCode(Checker.isNotEmpty(memberCodeStr) ? Long.valueOf(memberCodeStr) : null);
    					sign.setCardHostName(Checker.isNotEmpty(cardHostNameStr) ? cardHostNameStr : null);
    					sign.setValidStatus(Checker.isNotEmpty(validStatusStr) ? Integer.parseInt(validStatusStr) : null);
    					sign.setSignTime(Checker.isNotEmpty(signTimeStr) ? DateUtil.parse(DateUtil.DATE_FORMAT1, signTimeStr) : null);
    					sign.setLoginID(Checker.isNotEmpty(loginIDStr) ? loginIDStr : null);
    					sign.setCreateChannel(Checker.isNotEmpty(createChannelStr) ? createChannelStr : null);
    					sign.setMobile(Checker.isNotEmpty(mobileStr) ? mobileStr : null);
    					sign.setCreateTime(Checker.isNotEmpty(createTimeStr) ? DateUtil.parse(DateUtil.DATE_FORMAT1, createTimeStr) : null);
    					sign.setIsVip(Checker.isNotEmpty(isVipStr) ? BooleanUtils.toBooleanObject(isVipStr, "1", "0", "") : null);
    					
    					if(map.containsKey(cardbinStr)){
    						signs = map.get(cardbinStr);
    						signs.add(sign);
    						map.put(cardbinStr, signs);
    					}else{
    						signs = new ArrayList<MemberSignSpecDto>();
    						signs.add(sign);
    						map.put(cardbinStr, signs);
    					}
    					
    				}
    			}
    		}
			
		} catch (Exception e) {
			LogCvt.error("获取文件异常,url="+urlStr.toString(), e);
		} finally{
			if(is != null){
				is.close();
			}
			if(httpUrl != null){
				httpUrl.disconnect();
			}
		}
		
		return map;
	}
	
	
	public static void main(String[] args) {
		
	}



}
