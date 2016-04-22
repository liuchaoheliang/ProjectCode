package com.froad.support;

import java.util.List;

import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.log.MerchantLogs;
import com.froad.log.vo.MerchantDetailLog;
import com.froad.log.vo.MerchantLog;
import com.froad.log.vo.OutLetDetailLog;
import com.froad.log.vo.OutletLog;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.OutletLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.logic.impl.OutletLogicImpl;
import com.froad.po.Area;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.Outlet;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.util.PropertiesUtil;

public class DataPlatLogSupport {
	
	private MerchantLogic merchantLogic = new MerchantLogicImpl();
	private OutletLogic outletLogic = new OutletLogicImpl();
	private CommonLogic  commonLogic = new CommonLogicImpl();
	private MerchantLogs logs = new MerchantLogs();
	
		/**
		 *  数据平台商户添加日志落地
		 * @param merchantAccount
		 */
		public void addMerchantLog(String clientId,String merchantId){
			
			try {
				MerchantLog log = merchantDataInit(clientId, merchantId);
				if (null != log) {
					logs.addMerchant(log);
				}
			} catch (Exception e) {
				LogCvt.error("添加商户新增数据日志异常...", e);
			}
		}

		/**
		 *  数据平台商户录入审核日志落地
		 * @param merchantAccount
		 */
		public void auditMerchantLog(String clientId,String merchantId){
			
			try {
				MerchantLog log = merchantDataInit(clientId, merchantId);
				if (null != log) {
					logs.auditMerchant(log);
				}
			} catch (Exception e) {
				LogCvt.error("修改商户新增数据日志异常...", e);
			}
		
		}
		
		/**
		 *  数据平台商户修改日志落地
		 * @param merchantAccount
		 */
		public void modifyMerchantLog(String clientId,String merchantId){
			
			try {
				MerchantLog log = merchantDataInit(clientId, merchantId);
				if (null != log) {
					logs.updateMerchant(log);
				}
			} catch (Exception e) {
				LogCvt.error("修改商户新增数据日志异常...", e);
			}
		
		}

		/**
		 *  添加修改商户日志模块，数据初始化
		 * @param clientId
		 * @param merchantId
		 * @return
		 */
		private MerchantLog merchantDataInit(String clientId, String merchantId) {
			MerchantAccount merchantAccount = commonLogic.getMerchantAccoutByRedis(clientId,merchantId,"0");
			
			Merchant merchant = merchantLogic.findMerchantByMerchantId(merchantId);
			if (merchant.getMerchantStatus()) {
				LogCvt.info(merchant.getMerchantId() + "虚拟商户不打印落地日志");
				return null;
			}
			
			MerchantDetailMongo merchantMongo = new MerchantDetailMongo();
			MerchantDetail detail = merchantMongo.findMerchantDetailById(merchantId);
			
			MerchantLog log = new MerchantLog();
			log.setClient_id(clientId);
			// data信息
			MerchantDetailLog detailLog = new MerchantDetailLog();
			detailLog.setMerchant_id(merchant.getMerchantId());
			detailLog.setMerchant_name(merchant.getMerchantName());
			detailLog.setOrg_code(merchant.getOrgCode());
			detailLog.setOrg_name(commonLogic.queryByOrgCode(clientId, merchant.getOrgCode()).getOrgName());
			detailLog.setCreate_time(merchant.getCreateTime().getTime());
			detailLog.setLicense(merchant.getLicense());
			detailLog.setAcct_name(merchantAccount.getAcctName());
			detailLog.setAcct_number(merchantAccount.getAcctNumber());
			detailLog.setPhone(merchant.getPhone());
			detailLog.setIs_enable(merchant.getIsEnable());
			detailLog.setMerchant_type(detail.getTypeInfo());
			List<CategoryInfo> categoryList = detail.getCategoryInfo();
			detailLog.setMerchant_category((categoryList !=null && !categoryList.isEmpty())?categoryList.get(0).getCategoryId().toString():"");
			detailLog.setAudit_state(merchant.getAuditState());
			detailLog.setContract_staff(merchant.getContractStaff());
			detailLog.setAudit_time(System.currentTimeMillis());
			log.setData(detailLog);
			return log;
		}
		
		
		/**
		 *  数据平台商户删除日志落地
		 * @param merchantAccount
		 */
		public void deleteMerchantLog(String clientId,String merchantId){
			
			try {
				logs.deleteMerchant(merchantId, clientId, System.currentTimeMillis(), "MERCHANTDELETE");
			} catch (Exception e) {
				LogCvt.error("商户删除数据日志异常...", e);
			}
		}
		
		

		
		/**
		 *  数据平台门店添加日志落地
		 * @param merchantAccount
		 */
		public void addOutletLog(String clientId,String outletId){
			
			try {
				OutletLog log = OutletDataInit(clientId, outletId);
				if (null != log) {
					logs.addOutlet(log);
				}
			} catch (Exception e) {
				LogCvt.error("添加门店新增数据日志异常...", e);
			}
		}
			
		/**
		 *  数据平台门店修改日志落地
		 * @param merchantAccount
		 */
		public void updateOutletLog(String clientId,String outletId){
			
			try {
				OutletLog log = OutletDataInit(clientId, outletId);
				if (null != log) {
					logs.updateOutlet(log);
				}
			} catch (Exception e) {
				LogCvt.error("修改门店数据日志异常...", e);
			}
		
		}

		/**
		 *  数据平台门店录入审核日志落地
		 * @param merchantAccount
		 */
		public void auditOutletLog(String clientId,String outletId){
			
			try {
				OutletLog log = OutletDataInit(clientId, outletId);
				if (null != log) {
					logs.auditOutlet(log);
				}
			} catch (Exception e) {
				LogCvt.error("修改门店数据日志异常...", e);
			}
		
		}
			
		/**
		 *  数据平台门店删除日志落地
		 * @param merchantAccount
		 */
		public void deleteOutletLog(String clientId, String outletId){
			
			try {
				logs.deleteOutlet(outletId, clientId, System.currentTimeMillis(), "OUTLETELETE");
			} catch (Exception e) {
				LogCvt.error("删除门店数据日志异常...", e);
			}
		}
	
		/**
		 *  添加修改门店日志模块，数据初始化
		 * @param clientId
		 * @param merchantId
		 * @return
		 * @throws Exception 
		 */
		private OutletLog OutletDataInit(String clientId, String outletId) throws Exception {
			Outlet outlet = outletLogic.findOutletByOutletId(outletId);
			
			if (outlet.getOutletStatus()) {
				LogCvt.info(outlet.getOutletId() + "虚拟门店不打印落地日志");
				return null;
			}
			
			OutletDetailMongo outletMongo = new OutletDetailMongo();
			OutletDetail detail = outletMongo.findOutletDetailByoutletId(outletId);
			MerchantDetailMongo merchantMongo = new MerchantDetailMongo();
			MerchantDetail merchantDetail = merchantMongo.findMerchantDetailById(detail.getMerchantId());
			
			
			Area  area = commonLogic.findAreaById(detail.getAreaId());
			String [] areaArray = area.getTreePath().split(",");
			String province = "";
			String city = "";
			String county = "";
			if (areaArray.length == 1) {
				province = areaArray[0];
			}else if (areaArray.length == 2) {
				province = areaArray[0];
				city = areaArray[1];
			}else{
				province = areaArray[0];
				city = areaArray[1];
				county = areaArray[2];
			}
			
			OutletLog log = new OutletLog();
			log.setClient_id(clientId);
			// data信息
			OutLetDetailLog detailLog = new OutLetDetailLog();
			detailLog.setOutlet_id(outlet.getOutletId());
			detailLog.setOutlet_name(outlet.getOutletName());
			detailLog.setMerchant_id(outlet.getMerchantId());
			detailLog.setMerchant_name(detail.getMerchantName());
			detailLog.setCreate_time(outlet.getCreateTime().getTime());
			detailLog.setAddress(outlet.getAddress());
			detailLog.setPhone(outlet.getPhone());
			detailLog.setProvince(province);
			detailLog.setCity(city);
			detailLog.setCounty(county);
			detailLog.setAudit_state(outlet.getAuditState());
			detailLog.setIs_enable(outlet.getIsEnable());
			detailLog.setLocation(detail.getLocation());
			List<CategoryInfo> categoryList = detail.getCategoryInfo();
			List<CategoryInfo> merchantCategoryList = merchantDetail.getCategoryInfo();
			detailLog.setCategory_id((merchantCategoryList !=null && !merchantCategoryList.isEmpty())?merchantCategoryList.get(0).getCategoryId().toString():"");
			detailLog.setCategory_info(categoryList);
			log.setData(detailLog);
			return log;
		}
		
		public static void main(String[] args) {
			PropertiesUtil.load();
			new DataPlatLogSupport().auditOutletLog("chongqing", "14DA5EF40000");
		}
		
}
