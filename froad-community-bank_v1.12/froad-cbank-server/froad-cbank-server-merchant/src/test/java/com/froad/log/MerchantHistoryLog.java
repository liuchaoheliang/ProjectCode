package com.froad.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.mysql.mapper.MerchantAccountMapper;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.mysql.mapper.OrgCommonMapper;
import com.froad.db.mysql.mapper.OutletMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OutletDisableStatusEnum;
import com.froad.po.Area;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.Org;
import com.froad.po.Outlet;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.TypeInfo;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MerchantHistoryLog {

	public static void main(String[] args) {
		String clientId = null;
		Map<String, String> proMap = null;
		String logPath = null;
		
		if (args.length < 3){
			System.out.println("参数格式：client_id start_date end_date");
		}
		
		clientId = args[0];
		
		proMap = PropertiesUtil.loadProperties("init");
		logPath = new StringBuffer(proMap.get("log.path")).append("historylog/merchant/").toString();
		
		generateMerchantLog(clientId, args[1], args[2], logPath);
	}

	/**
	 * 生成商户、门店历史日志
	 * 
	 * @param clientId
	 * @param startDateStr
	 * @param endDateStr
	 * @param logPath
	 */
	public static void generateMerchantLog(String clientId, String startDateStr, String endDateStr, String logPath){
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		MerchantMapper merchantMapper = null;
		MerchantAccountMapper accountMapper = null;
		Merchant merchant = null;
		List<MerchantAccount> merchantAccountList = null;
		MerchantAccount merchantAccount = null;
		List<Merchant> merchantList = null;
		MerchantDetail merchantDetail = null;
		StringBuffer addStr = null;
//		StringBuffer updateStr = null;
		StringBuffer deleteStr = null;
		MongoManager mongoManager = null;
		BufferedWriter addWriter = null;
//		BufferedWriter updateWriter = null;
		BufferedWriter deleteWriter = null;
		List<String> outletLogList = null;
		BufferedWriter outletAddWriter = null;
//		BufferedWriter outletUpdateWriter = null;
		BufferedWriter outletDeleteWriter = null;
		Map<String, String> orgMap = null;
		Page<Merchant> page = null;
		String merchantCategoryId = null;
		Integer totalMerchantCount = null;
		int totalChunks = 0;
		Date startDate = null;
		Date endDate = null;
		
		merchantMapper = sqlSession.getMapper(MerchantMapper.class);
		accountMapper = sqlSession.getMapper(MerchantAccountMapper.class);
		mongoManager = new MongoManager();
		
		try {
			startDate = DateUtil.parse(DateUtil.DATE_FORMAT1, startDateStr);
			endDate = DateUtil.parse(DateUtil.DATE_FORMAT1, endDateStr);
			
			addWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("merchant_add_history_").append(startDateStr).append("_").append(endDateStr).append(".log").toString())));
//			updateWriter = new BufferedWriter(new FileWriter(new StringBuffer(logPath).append("merchant_modify_history_").append(startDateStr).append("_").append(endDateStr).append(".log").toString()));
			deleteWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("merchant_delete_history_").append(startDateStr).append("_").append(endDateStr).append(".log").toString())));
			
			outletAddWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("outlet_add_history_").append(startDateStr).append("_").append(endDateStr).append(".log").toString())));
//			outletUpdateWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("outlet_modify_history.log").append(startDateStr).append("_").append(endDateStr).append(".log").toString())));
			outletDeleteWriter = new BufferedWriter(new FileWriter(new File(new StringBuffer(logPath).append("outlet_delete_history_").append(startDateStr).append("_").append(endDateStr).append(".log").toString())));
			
			orgMap = getOrgMap(sqlSession);
			
			merchant = new Merchant();
			merchant.setMerchantStatus(false);
			merchant.setClientId(clientId);
			merchant.setStartCreateTime(startDate);
			merchant.setEndCreateTime(endDate);
			page = new Page<Merchant>();
			
			totalMerchantCount = merchantMapper.countMerchant(merchant);
			totalChunks = totalMerchantCount == null ? 0 : (totalMerchantCount + 500) / 1000;
			
			for (int m = 0; m < totalChunks; m++) {
				System.out.println(new StringBuffer((m+1)).append(" - ").append((m+1) * 10000).append(" is started...").toString());
				
				page.setPageNumber(m);
				page.setPageSize(10000);
				merchantList = merchantMapper.findByPage(page, merchant);
				
				if (Checker.isEmpty(merchantList)){
					break;
				}
				
				for (int i = 0; i < merchantList.size(); i++){
					merchant = merchantList.get(i);
					
					merchantAccount = new MerchantAccount();
					merchantAccount.setClientId(merchant.getClientId());
					merchantAccount.setMerchantId(merchant.getMerchantId());
					merchantAccountList = accountMapper.findMerchantAccount(merchantAccount);
					if (Checker.isNotEmpty(merchantAccountList)){
						merchantAccount = merchantAccountList.get(0);
					}
					
					DBObject queryObj = new BasicDBObject();
					queryObj.put("_id", merchant.getMerchantId());
					merchantDetail = mongoManager.findOne(queryObj, "cb_merchant_detail", MerchantDetail.class);
					List<TypeInfo> typeList = null;
					TypeInfo typeInfo = null;
					if (Checker.isNotEmpty(merchantDetail)){
						typeList = merchantDetail.getTypeInfo();
					}
					
					merchantCategoryId = (merchantDetail != null && merchantDetail
							.getCategoryInfo() != null) ? String
							.valueOf(merchantDetail.getCategoryInfo().get(0)
									.getCategoryId()) : "";
					
					// 商户新增
					addStr = new StringBuffer();
					addStr.append("{");
					addStr.append("\"key\": { \"merchant_id\": \"");
					addStr.append(merchant.getMerchantId());
					addStr.append("\"},");
					addStr.append("\"action\": \"MERCHANTADD\",");
					addStr.append("\"client_id\": \"");
					addStr.append(merchant.getClientId());
					addStr.append("\",");
					addStr.append("\"time\": ");
					addStr.append(merchant.getCreateTime().getTime());
					addStr.append(",");
					addStr.append("\"data\": {");
					addStr.append("\"merchant_id\":\"");
					addStr.append(merchant.getMerchantId());
					addStr.append("\",");
					addStr.append("\"org_code\":\"");
					addStr.append(merchant.getOrgCode());
					addStr.append("\",");
					addStr.append("\"org_name\":\"");
					addStr.append(orgMap.get(merchant.getClientId() + merchant.getOrgCode()));
					addStr.append("\",");
					addStr.append("\"create_time\":");
					addStr.append(merchant.getCreateTime().getTime());
					addStr.append(",");
					addStr.append("\"license\": \"");
					addStr.append(merchant.getLicense());
					addStr.append("\",");
					addStr.append("\"acct_name\": \"");
					addStr.append(merchantAccount != null ? merchantAccount.getAcctName() : "");
					addStr.append("\",");
					addStr.append("\"acct_number\": \"");
					addStr.append(merchantAccount != null ? merchantAccount.getAcctNumber() : "");
					addStr.append("\",");
					addStr.append("\"phone\": \"");
					addStr.append(merchant.getPhone());
					addStr.append("\",");
					addStr.append("\"is_enable\": \"");
					addStr.append(merchant.getIsEnable());
					addStr.append("\",");
					addStr.append("\"merchant_type\": [");
					if (Checker.isNotEmpty(typeList)){
						typeInfo = typeList.get(0);
						addStr.append("{");
						addStr.append("\"merchant_type_id\": ");
						addStr.append(typeInfo.getMerchantTypeId());
						addStr.append(",\"type\": \"");
						addStr.append(typeInfo.getType());
						addStr.append("\",\"type_name\": \"");
						addStr.append(typeInfo.getTypeName());
						addStr.append("\"}");
						
						for (int j = 1; j < typeList.size(); j++){
							typeInfo = typeList.get(j);
							addStr.append(",{");
							addStr.append("\"merchant_type_id\": ");
							addStr.append(typeInfo.getMerchantTypeId());
							addStr.append(",\"type\": \"");
							addStr.append(typeInfo.getType());
							addStr.append("\",\"type_name\": \"");
							addStr.append(typeInfo.getTypeName());
							addStr.append("\"}");
						}
					}
					addStr.append("],");
					addStr.append("\"merchant_category\":\"");
					addStr.append(merchantCategoryId);
					addStr.append("\",");
					addStr.append("\"contract_staff\":\"");
					addStr.append(merchant.getContractStaff());
					addStr.append("\",");
					addStr.append("\"audit_state\":\"");
					addStr.append(merchant.getAuditState());
					addStr.append("\",");
					addStr.append("\"audit_pro_proson\":\"\",");
					addStr.append("\"audit_pro_time\":0,");
					addStr.append("\"audit_pro_orgcode\":\"\",");
					addStr.append("\"audit_city_person\":\"\",");
					addStr.append("\"audit_city_time\":0,");
					addStr.append("\"audit_city_orgcode\":\"\"");
					addStr.append("}");
					addStr.append("}");
					addStr.append("\n");
					addWriter.write(addStr.toString());
					
//					// 商户修改
//					updateStr = new StringBuffer();
//					updateStr.append("{");
//					updateStr.append("\"key\": { \"merchant_id\": \"");
//					updateStr.append(merchant.getMerchantId());
//					updateStr.append("\"},");
//					updateStr.append("\"action\": \"MERCHANTMODIFY\",");
//					updateStr.append("\"client_id\": \"");
//					updateStr.append(merchant.getClientId());
//					updateStr.append("\",");
//					updateStr.append("\"time\": ");
//					updateStr.append(merchant.getCreateTime().getTime());
//					updateStr.append(",");
//					updateStr.append("\"data\": {");
//					updateStr.append("\"merchant_id\":\"");
//					updateStr.append(merchant.getMerchantId());
//					updateStr.append("\",");
//					updateStr.append("\"org_code\":\"");
//					updateStr.append(merchant.getOrgCode());
//					updateStr.append("\",");
//					updateStr.append("\"org_name\":\"");
//					updateStr.append(orgMap.get(merchant.getClientId() + merchant.getOrgCode()));
//					updateStr.append("\",");
//					updateStr.append("\"update_time\":");
//					updateStr.append(merchant.getCreateTime().getTime());
//					updateStr.append(",");
//					updateStr.append("\"license\": \"");
//					updateStr.append(merchant.getLicense());
//					updateStr.append("\",");
//					updateStr.append("\"acct_name\": \"");
//					updateStr.append(merchantAccount != null ? merchantAccount.getAcctName() : "");
//					updateStr.append("\",");
//					updateStr.append("\"acct_number\": \"");
//					updateStr.append(merchantAccount != null ? merchantAccount.getAcctNumber() : "");
//					updateStr.append("\",");
//					updateStr.append("\"phone\": \"");
//					updateStr.append(merchant.getPhone());
//					updateStr.append("\",");
//					updateStr.append("\"is_enable\": \"");
//					updateStr.append(merchant.getIsEnable());
//					updateStr.append("\",");
//					updateStr.append("\"merchant_type\": [");
//					if (Checker.isNotEmpty(typeList)){
//						typeInfo = typeList.get(0);
//						updateStr.append("{");
//						updateStr.append("\"merchant_type_id\": ");
//						updateStr.append(typeInfo.getMerchantTypeId());
//						updateStr.append(",\"type\": \"");
//						updateStr.append(typeInfo.getType());
//						updateStr.append("\",\"type_name\": \"");
//						updateStr.append(typeInfo.getTypeName());
//						updateStr.append("\"}");
//						
//						for (int j = 1; j < typeList.size(); j++){
//							typeInfo = typeList.get(j);
//							updateStr.append(",{");
//							updateStr.append("\"merchant_type_id\": ");
//							updateStr.append(typeInfo.getMerchantTypeId());
//							updateStr.append(",\"type\": \"");
//							updateStr.append(typeInfo.getType());
//							updateStr.append("\",\"type_name\": \"");
//							updateStr.append(typeInfo.getTypeName());
//							updateStr.append("\"}");
//						}
//					}
//					updateStr.append("],");
//					updateStr.append("\"merchant_category\":\"");
//					updateStr.append(merchantCategoryId);
//					updateStr.append("\",");
//					updateStr.append("\"contract_staff\":\"");
//					updateStr.append(merchant.getContractStaff());
//					updateStr.append("\",");
//					updateStr.append("\"audit_state\":\"");
//					updateStr.append(merchant.getAuditState());
//					updateStr.append("\",");
//					updateStr.append("\"audit_pro_proson\":\"\",");
//					updateStr.append("\"audit_pro_time\":0,");
//					updateStr.append("\"audit_pro_orgcode\":\"\",");
//					updateStr.append("\"audit_city_person\":\"\",");
//					updateStr.append("\"audit_city_time\":0,");
//					updateStr.append("\"audit_city_orgcode\":\"\"");
//					updateStr.append("}");
//					updateStr.append("}");
//					updateStr.append("\n");
//					updateWriter.write(updateStr.toString());
					
					// 商户解约
					if (merchant.getDisableStatus().equals(MerchantDisableStatusEnum.unregistered.getCode())){
						deleteStr = new StringBuffer();
						deleteStr.append("{");
						deleteStr.append("\"key\": { \"merchant_id\": \"");
						deleteStr.append(merchant.getMerchantId());
						deleteStr.append("\"},");
						deleteStr.append("\"action\": \"MERCHANTDELETE\",");
						deleteStr.append("\"client_id\": \"");
						deleteStr.append(merchant.getClientId());
						deleteStr.append("\",");
						deleteStr.append("\"time\": ");
						deleteStr.append(merchant.getCreateTime().getTime());
						deleteStr.append(",");
						deleteStr.append("\"data\": {");
						deleteStr.append("\"merchant_id\":\"");
						deleteStr.append(merchant.getMerchantId());
						deleteStr.append("\"");
						deleteStr.append("}");
						deleteStr.append("}");
						deleteStr.append("\n");
						deleteWriter.write(deleteStr.toString());
					}
					
					outletLogList = generateOutletLog(sqlSession,
							merchant.getClientId(), merchant.getMerchantId(),
							merchant.getMerchantName(), merchantCategoryId,
							mongoManager);
					if (Checker.isNotEmpty(outletLogList)){
						for (int k = 0; k < outletLogList.size(); k++){
							if (k % 3 == 0){
								outletAddWriter.write(outletLogList.get(k));
							} else if (k % 3 == 1){
//								outletUpdateWriter.write(outletLogList.get(k));
							} else {
								outletDeleteWriter.write(outletLogList.get(k));
							}
						}
					}
				}
				
				sqlSession.commit(true);
				
				System.out.println(new StringBuffer((m+1)).append(" - ").append((m+1) * 10000).append(" is done!").toString());
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				addWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				addWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				updateWriter.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			try {
//				updateWriter.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			try {
				deleteWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				deleteWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				outletAddWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outletAddWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			try {
//				outletUpdateWriter.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			try {
//				outletUpdateWriter.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			try {
				outletDeleteWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outletDeleteWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<String> generateOutletLog(SqlSession sqlSession,
			String clientId, String merchantId, String merchantName,
			String merchantCategoryId, MongoManager mongoManager) {
		OutletMapper outletMapper = null;
		List<Outlet> outletList = null;
		Outlet outlet = null;
		List<String> logList = null;
		StringBuffer addStr = null;
		StringBuffer updateStr = null;
		StringBuffer deleteStr = null;
		OutletDetail outletDetail = null;
		CategoryInfo categoryInfo = null;
		AreaMapper areaMapper = null;
		Area area = null;
		String[] areaIds = null;
		String provinceAreaId = "";
		String cityAreaId = "";
		String countyAreaId = "";
		
		areaMapper = sqlSession.getMapper(AreaMapper.class);
		
		outletMapper = sqlSession.getMapper(OutletMapper.class);
		outlet = new Outlet();
		outlet.setClientId(clientId);
		outlet.setMerchantId(merchantId);
		outlet.setOutletStatus(false);
		outletList = outletMapper.findOutlet(outlet);
		
		if (Checker.isEmpty(outletList)){
			return null;
		}
		
		logList = new ArrayList<String>();
		
		for (int i = 0; i < outletList.size(); i++){
			outlet = outletList.get(i);
			
			DBObject queryObj = new BasicDBObject();
			queryObj.put("_id", outlet.getOutletId());
			outletDetail = mongoManager.findOne(queryObj, "cb_outlet_detail", OutletDetail.class);
			
			area = areaMapper.findAreaById(outlet.getAreaId());
			areaIds = area.getTreePath().split(",");
			if (areaIds != null){
				provinceAreaId = areaIds[0];
				cityAreaId = areaIds.length > 0 ? areaIds[1] : "";
				countyAreaId = areaIds.length > 1 ? areaIds[2] : "";
			}
			
			// 门店添加
			addStr = new StringBuffer();
			addStr.append("{");
			addStr.append("\"key\": { \"outlet_id\": \"");
			addStr.append(outlet.getOutletId());
			addStr.append("\"},");
			addStr.append("\"action\": \"OUTLETADD\",");
			addStr.append("\"client_id\": \"");
			addStr.append(outlet.getClientId());
			addStr.append("\",");
			addStr.append("\"time\": ");
			addStr.append(outlet.getCreateTime().getTime());
			addStr.append(",");
			addStr.append("\"data\": {");
			addStr.append("\"outlet_id\":\"");
			addStr.append(outlet.getOutletId());
			addStr.append("\",");
			addStr.append("\"outlet_name\":\"");
			addStr.append(outlet.getOutletName());
			addStr.append("\",");
			addStr.append("\"merchant_id\":\"");
			addStr.append(outlet.getMerchantId());
			addStr.append("\",");
			addStr.append("\"merchant_name\":\"");
			addStr.append(merchantName);
			addStr.append("\",");
			addStr.append("\"create_time\":");
			addStr.append(outlet.getCreateTime().getTime());
			addStr.append(",");
			addStr.append("\"address\": \"");
			addStr.append(outlet.getAddress());
			addStr.append("\",");
			addStr.append("\"phone\": \"");
			addStr.append(outlet.getPhone());
			addStr.append("\",");
			addStr.append("\"province\": \"").append(provinceAreaId).append("\",");
			addStr.append("\"city\": \"").append(cityAreaId).append("\",");
			addStr.append("\"county\":\"").append(countyAreaId).append("\",");
			addStr.append("\"audit_state\": \"");
			addStr.append(outlet.getAuditState());
			addStr.append("\",");
			addStr.append("\"is_enable\": \"");
			addStr.append(outlet.getIsEnable());
			addStr.append("\",");
			addStr.append("\"location\": {");
			addStr.append("\"longitude\": \"");
			addStr.append(outlet.getLongitude());
			addStr.append("\",");
			addStr.append("\"latitude\": \"");
			addStr.append(outlet.getLatitude());
			addStr.append("\"");
			addStr.append("},");
			addStr.append("\"category_id\": \"");
			addStr.append(merchantCategoryId);
			addStr.append("\",");
			addStr.append("\"category_info\": [");
			if (Checker.isNotEmpty(outletDetail.getCategoryInfo())){
				categoryInfo = outletDetail.getCategoryInfo().get(0);
				addStr.append("{");
				addStr.append("\"category_id\": ");
				addStr.append(categoryInfo.getCategoryId());
				addStr.append(",\"name\": \"");
				addStr.append(categoryInfo.getName());
				addStr.append("\"}");
				for (int j = 1; j < outletDetail.getCategoryInfo().size(); j++){
					categoryInfo = outletDetail.getCategoryInfo().get(j);
					addStr.append(",{");
					addStr.append("\"category_id\": ");
					addStr.append(categoryInfo.getCategoryId());
					addStr.append(",\"name\": \"");
					addStr.append(categoryInfo.getName());
					addStr.append("\"}");
				}
			}
			addStr.append("]");
			addStr.append("}");
			addStr.append("}");
			addStr.append("\n");
			logList.add(addStr.toString());
			
			// 门店修改
			updateStr = new StringBuffer();
			updateStr.append("{");
			updateStr.append("\"key\": { \"outlet_id\": \"");
			updateStr.append(outlet.getOutletId());
			updateStr.append("\"},");
			updateStr.append("\"action\": \"OUTLETMODIFY\",");
			updateStr.append("\"client_id\": \"");
			updateStr.append(outlet.getClientId());
			updateStr.append("\",");
			updateStr.append("\"time\": ");
			updateStr.append(outlet.getCreateTime().getTime());
			updateStr.append(",");
			updateStr.append("\"data\": {");
			updateStr.append("\"outlet_id\":\"");
			updateStr.append(outlet.getOutletId());
			updateStr.append("\",");
			updateStr.append("\"outlet_name\":\"");
			updateStr.append(outlet.getOutletName());
			updateStr.append("\",");
			updateStr.append("\"merchant_id\":\"");
			updateStr.append(outlet.getMerchantId());
			updateStr.append("\",");
			updateStr.append("\"merchant_name\":\"");
			updateStr.append(merchantName);
			updateStr.append("\",");
			updateStr.append("\"create_time\":");
			updateStr.append(outlet.getCreateTime().getTime());
			updateStr.append(",");
			updateStr.append("\"address\": \"");
			updateStr.append(outlet.getAddress());
			updateStr.append("\",");
			updateStr.append("\"phone\": \"");
			updateStr.append(outlet.getPhone());
			updateStr.append("\",");
			updateStr.append("\"province\": \"").append(provinceAreaId).append("\",");
			updateStr.append("\"city\": \"").append(cityAreaId).append("\",");
			updateStr.append("\"county\":\"").append(countyAreaId).append("\",");
			updateStr.append("\"audit_state\": \"");
			updateStr.append(outlet.getAuditState());
			updateStr.append("\",");
			updateStr.append("\"is_enable\": \"");
			updateStr.append(outlet.getIsEnable());
			updateStr.append("\",");
			updateStr.append("\"location\": {");
			updateStr.append("\"longitude\": \"");
			updateStr.append(outlet.getLongitude());
			updateStr.append("\",");
			updateStr.append("\"latitude\": \"");
			updateStr.append(outlet.getLatitude());
			updateStr.append("\"");
			updateStr.append("},");
			updateStr.append("\"category_id\": \"");
			updateStr.append(merchantCategoryId);
			updateStr.append("\",");
			updateStr.append("\"category_info\": [");
			if (Checker.isNotEmpty(outletDetail.getCategoryInfo())){
				categoryInfo = outletDetail.getCategoryInfo().get(0);
				updateStr.append("{");
				updateStr.append("\"category_id\": ");
				updateStr.append(categoryInfo.getCategoryId());
				updateStr.append(",\"name\": \"");
				updateStr.append(categoryInfo.getName());
				updateStr.append("\"}");
				for (int j = 1; j < outletDetail.getCategoryInfo().size(); j++){
					categoryInfo = outletDetail.getCategoryInfo().get(j);
					updateStr.append(",{");
					updateStr.append("\"category_id\": ");
					updateStr.append(categoryInfo.getCategoryId());
					updateStr.append(",\"name\": \"");
					updateStr.append(categoryInfo.getName());
					updateStr.append("\"}");
				}
			}
			updateStr.append("]");
			updateStr.append("}");
			updateStr.append("}");
			updateStr.append("\n");
			logList.add(updateStr.toString());
			
			// 门店删除
			if (outlet.getDisableStatus().equals(OutletDisableStatusEnum.delete.getCode())){
				deleteStr = new StringBuffer();
				deleteStr.append("{");
				deleteStr.append("\"key\": { \"outlet_id\": \"");
				deleteStr.append(outlet.getOutletId());
				deleteStr.append("\"},");
				deleteStr.append("\"action\": \"OUTLETDELETE\",");
				deleteStr.append("\"client_id\": \"");
				deleteStr.append(outlet.getClientId());
				deleteStr.append("\",");
				deleteStr.append("\"time\": ");
				deleteStr.append(outlet.getCreateTime().getTime());
				deleteStr.append(",");
				deleteStr.append("\"data\": {");
				deleteStr.append("\"outlet_id\":\"");
				deleteStr.append(outlet.getOutletId());
				deleteStr.append("\"");
				deleteStr.append("}");
				deleteStr.append("}");
				deleteStr.append("\n");
				logList.add(deleteStr.toString());
			}

		}
		
		return logList;
	}
	
	private static Map<String, String> getOrgMap(SqlSession sqlSession){
		OrgCommonMapper orgMapper = null;
		List<Org> orgList = null;
		Org org = null;
		Map<String, String> orgMap = null;
		StringBuffer key = null;
		
		orgMapper = sqlSession.getMapper(OrgCommonMapper.class);
		orgList = orgMapper.findOrg(new Org());
		
		orgMap = new HashMap<String, String>();
		key = new StringBuffer();
		for (int i = 0; i < orgList.size(); i++){
			org = orgList.get(i);
			key.delete(0, key.length());
			key.append(org.getClientId());
			key.append(org.getOrgCode());
			orgMap.put(key.toString(), org.getOrgName());
		}
		
		return orgMap;
	}
}
