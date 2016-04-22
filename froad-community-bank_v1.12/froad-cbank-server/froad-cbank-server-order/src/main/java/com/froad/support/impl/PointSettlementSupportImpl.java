package com.froad.support.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.mongo.MongoService;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.enums.FieldMapping;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriterPointReport;
import com.froad.ftp.FtpConstants;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Client;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.PointSettlementDetailMongo;
import com.froad.support.PointSettlementSupport;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementDetailResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantDetailResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementMerchantResVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementReqVo;
import com.froad.thrift.vo.pointsettlement.PointSettlementResVo;
import com.froad.util.Arith;
import com.froad.util.DateUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.pay.user.dto.UserResult;


public class PointSettlementSupportImpl implements PointSettlementSupport {
	
	private static final String MONGO_TABLE = MongoTableName.CB_POINT_SETTLEMENT_DETAIL;
	
	private static final int querySize = 2000;//导出时，每次查询条数
//	private static final int maxSize = 3;//导出是，每个sheet页查询几次
	
	private static final String[] boss_titile1 = {"序号","订单编号","用户会员名","用户手机号","商品名称","商品单价","商品数量","商品总价","结算总价","支付方式","银行积分","银行积分比例","联盟积分","现金","结算时间","所属商户","所属客户端"};
	private static final String[] boss_titile2 = {"序号","订单编号","用户会员名","用户手机号","结算总价","支付方式","银行积分","银行积分比例","联盟积分","现金","结算时间","所属商户","所属客户端"};
	private static final String[] boss_titile3 = {"序号","所属商户","银行积分","银行积分比例","联盟积分","订单数量"};
	
	private static final String[] bank_titile1 = {"序号","订单编号","用户会员名","用户手机号","商品名称","商品单价","商品数量","商品总价","结算总价","支付方式","银行积分","银行积分比例","联盟积分","现金","结算时间","所属商户"};
	private static final String[] bank_titile2 = {"序号","订单编号","用户会员名","用户手机号","结算总价","支付方式","银行积分","银行积分比例","联盟积分","现金","结算时间","所属商户"};
	private static final String[] bank_titile3 = {"序号","所属商户","银行积分","银行积分比例","联盟积分","订单数量"};
	
	private static final String[] merchant_titile1 = {"序号","订单编号","用户会员名","用户手机号","商品名称","商品单价","商品数量","商品总价","结算总价","支付方式","银行积分","银行积分比例","联盟积分","现金","结算时间"};
	private static final String[] merchant_titile2 = {"序号","订单编号","用户会员名","用户手机号","结算总价","支付方式","银行积分","银行积分比例","联盟积分","现金","结算时间"};
	
	
	private static final String head1 = "购物订单明细";
	private static final String head2 = "面对面惠付订单明细";
	private static final String head3 = "商户汇总";
	
	private static final String boss_excelName1 = "{0}积分报表购物订单明细（{1}）" + FtpConstants.CSV_SUFFIX;
	private static final String boss_excelName2 = "{0}积分报表面对面惠付订单明细（{1}）" + FtpConstants.CSV_SUFFIX;
	private static final String boss_excelName3 = "{0}积分报表商户汇总（{1}）" + FtpConstants.CSV_SUFFIX;
	
	private static final String bank_excelName1 = "{0}积分报表购物订单明细（{1}）" + FtpConstants.CSV_SUFFIX;
	private static final String bank_excelName2 = "{0}积分报表面对面惠付订单明细（{1}）" + FtpConstants.CSV_SUFFIX;
	private static final String bank_excelName3 = "{0}积分报表商户汇总（{1}）" + FtpConstants.CSV_SUFFIX;
	
	private static final String merchant_excelName1 = "{0}银行{1}积分报表购物订单明细（{2}）" + FtpConstants.CSV_SUFFIX;
	private static final String merchant_excelName2 = "{0}银行{1}积分报表面对面惠付订单明细（{2}）" + FtpConstants.CSV_SUFFIX;
	
	
	private static final String boss_zipName = "{0}银行积分报表（{1}）";
	private static final String bank_zipName = "{0}银行积分报表（{1}）";
	private static final String merchant_zipName = "{0}银行{1}积分报表（{2}）";
	
	
//	private static final String sheelName1 = "购物订单";
//	private static final String sheelName2 = "面对面惠付订单";
//	private static final String sheelName3 = "商户汇总";
	
	
	private MongoManager mongo = new MongoManager();
	
	private UserEngineFunc userEngineFunc = new UserEngineFuncImpl();
	private CommonLogic commonLogic = new CommonLogicImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public PointSettlementResVo queryPointCount(PointSettlementReqVo req) {
		ResultVo resultVo = new ResultVo();
		
		//响应的对象
		PointSettlementResVo resVo = new PointSettlementResVo();
		resVo.setResultVo(resultVo);
		
		DBObject where = getQueryDBObject(req);
		
		//分组字段
		Map<String,String> dimMap = new HashMap<String, String>();
		
		//统计字段
		Map<String,String> forIdxMap = new HashMap<String, String>();
		forIdxMap.put("sum_bankPoint", "sum(bank_point)");
		forIdxMap.put("sum_froadPoint", "sum(froad_point)");
		
		//统计操作用到的字段
		Map<String,String> indexMap = new HashMap<String, String>();
		indexMap.put("bank_point", "bank_point");
		indexMap.put("froad_point", "froad_point");
		
		try {
			BasicDBList basicDBList = mongo.group(MONGO_TABLE, where, dimMap, forIdxMap, indexMap);
			
			if(basicDBList != null && basicDBList.size() > 0){
				Map<String,Object> resultMap = (Map<String,Object>) basicDBList.get(0);
				
				Double bankPointCount = Double.valueOf(String.valueOf(resultMap.get("sum_bankPoint")));
				Double froadPointCount = Double.valueOf(String.valueOf(resultMap.get("sum_froadPoint")));
				
				resVo.setBankPointCount(Arith.div(bankPointCount, 1000, 2));
				resVo.setFroadPointCount(Arith.div(froadPointCount, 1000, 2));
			}else{
				resVo.setBankPointCount(0);
				resVo.setFroadPointCount(0);
			}
		} catch (Exception e) {
			LogCvt.error("查询积分结算汇总信息异常", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("查询积分结算汇总信息异常。");
			return resVo;
		}
		
		
		resultVo.setResultCode(ResultCode.success.getCode());
		return resVo;
	}

	@Override
	public PointSettlementResVo queryPointDetail(PointSettlementReqVo req) {

		//响应的对象，封装积分统计的信息
		PointSettlementResVo resVo = queryPointCount(req);
		ResultVo resultVo = resVo.getResultVo();
		if(!ResultCode.success.getCode().equals(resultVo.getResultCode())){
			LogCvt.error("查询积分结算详情是，查询积分结算汇总信息异常,isQrcode=" + req.getIsQrcode());
			return resVo;
		}
		
		//查询并封装明细数据
		this.queryPointDetail(req,resVo);
		
		return resVo;
	}

	//查询积分报表详情，将结果封装到resVo中;
	private void queryPointDetail(PointSettlementReqVo req,PointSettlementResVo resVo){
		ResultVo resultVo = resVo.getResultVo();
		if(resultVo == null){
			resultVo = new ResultVo();
			resVo.setResultVo(resultVo);
		}
		
		//查询条件
		DBObject where = getQueryDBObject(req);
		
		//分页信息
		PageVo pageVo = req.getPage();
		
		//组装mongo的分页信息
		MongoPage mongoPage =  convertMongoPage(pageVo);
		
		//排序条件
//		mongoPage.setSort(new Sort("create_time", OrderBy.ASC));
		
		try {
			
			mongoPage = mongo.findByPageWithRedis(mongoPage, where,  MONGO_TABLE, PointSettlementDetailMongo.class);
			
			//分页查询记录
//			mongoPage = mongo.findByPageWithSortObject(mongoPage, where,new BasicDBObject(),  MONGO_TABLE, PointSettlementDetailMongo.class);
			
			// 组装响应信息
			resVo.setDetailResVoList(convertVo(mongoPage.getItems()));
			pageVo.setTotalCount(mongoPage.getTotalCount());
			pageVo.setPageCount(mongoPage.getPageCount());
			pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
			pageVo.setLastPageNumber(mongoPage.getPageNumber());
			pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
			resVo.setPage(pageVo);
		} catch (Exception e) {
			LogCvt.error("积分结算明细查询异常,isQrcode=" + req.getIsQrcode(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("积分结算明细查询异常。");
			return;
		}
		
		resultVo.setResultCode(ResultCode.success.getCode());
	}

	@SuppressWarnings("unchecked")
	@Override
	public PointSettlementMerchantResVo queryMerchantPointCount(PointSettlementReqVo req) {

		//查询条件
		DBObject where = getQueryDBObject(req);
		
		//分页信息
		PageVo pageVo = req.getPage();
		
		//组装mongo的分页信息
		MongoPage mongoPage =  convertMongoPage(pageVo);
		
		mongoPage = mongo.findDistinctArrayByPage(MONGO_TABLE, where, "merchant_id", mongoPage);
		
		// 组装响应信息
		PointSettlementMerchantResVo resVo = new PointSettlementMerchantResVo();
		
		pageVo.setTotalCount(mongoPage.getTotalCount());
		pageVo.setPageCount(mongoPage.getPageCount());
		pageVo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pageVo.setLastPageNumber(mongoPage.getPageNumber());
		pageVo.setLastRecordTime(mongoPage.getLastRecordTime());
		resVo.setPage(pageVo);
		
		//积分统计
		List<String> merchantIds = (List<String>)mongoPage.getItems();
		if(merchantIds != null && merchantIds.size() > 0){
			Object [] obj = new Object[merchantIds.size()];
			for(int i=0;i<merchantIds.size();i++){
				obj[i] = new BasicDBObject("merchant_id", merchantIds.get(i));
			}
			where.put(QueryOperators.OR, obj);
			
			//分组字段
			Map<String,String> dimMap = new HashMap<String, String>();
			dimMap.put("merchant_id", "merchant_id");
			dimMap.put("merchant_name", "merchant_name");
			
			//统计字段
			Map<String,String> forIdxMap = new HashMap<String, String>();
			forIdxMap.put("sum_bankPoint", "sum(bank_point)");
			forIdxMap.put("sum_froadPoint", "sum(froad_point)");
			
			//统计操作用到的字段
			Map<String,String> indexMap = new HashMap<String, String>();
			indexMap.put("bank_point", "bank_point");
			indexMap.put("froad_point", "froad_point");
			
			//银行积分兑换比例
			String bankPointRate = this.queryNewestbankPointRate(req);
			
			List<PointSettlementMerchantDetailResVo> voList = new ArrayList<PointSettlementMerchantDetailResVo>();
			
			DBObject where1 = getQueryDBObject(req);
			//统计查询
			BasicDBList basicDBList = mongo.group(MONGO_TABLE, where, dimMap, forIdxMap, indexMap);
			for(int i=0;i<basicDBList.size();i++){
				Map<String,Object> resultMap = (Map<String,Object>) basicDBList.get(i);
				Double bankPointCount = Double.valueOf(String.valueOf(resultMap.get("sum_bankPoint")));
				Double froadPointCount = Double.valueOf(String.valueOf(resultMap.get("sum_froadPoint")));
				
				PointSettlementMerchantDetailResVo vo = new PointSettlementMerchantDetailResVo();
				vo.setMerchantName(String.valueOf(resultMap.get("merchant_name")));
				vo.setBankPointCount(Arith.div(bankPointCount, 1000, 2));
				vo.setFroadPointCount(Arith.div(froadPointCount, 1000, 2));
				vo.setBankPointRate(StringUtils.isEmpty(bankPointRate) ? "": (bankPointRate + ":1"));
				
				//订单总数，
				vo.setOrderCount(this.queryOrderCount(where1, String.valueOf(resultMap.get("merchant_id")),String.valueOf(resultMap.get("merchant_name"))));
				voList.add(vo);
			}
			resVo.setDetailResVoList(voList);
		}	
			
		ResultVo resultVo = new ResultVo(ResultCode.success.getCode(),ResultCode.success.getMsg());
		resVo.setResultVo(resultVo);
		return resVo;
	}

	private String getExcelName(String excelName,String clientName,String exportDate,String merchantName){
		if(merchantName != null){
			excelName = excelName.replace("{0}", clientName).replace("{1}", merchantName).replace("{2}", exportDate);
		}else{
			excelName = excelName.replace("{0}", clientName).replace("{1}", exportDate);
		}
		return excelName;
	}
	@Override
	public ExportResultRes exportPointSettlement(PointSettlementReqVo req) {
		
		ExportResultRes resultResVo = new ExportResultRes();
		
		ExcelWriterPointReport excelWriter = new ExcelWriterPointReport();
		Client client = commonLogic.getClientById(req.getClientId());
		String clientName = (client != null ? client.getName() : "");//获取clientId对应的银行名
		String exportDate = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, req.getEndTime());
		
		ResultVo resultVo = new ResultVo();
		try {
			excelWriter.createFolder();
		} catch (FroadBusinessException e) {
			LogCvt.error("导出积分报表，创建文件夹异常");
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("创建文件夹异常");
			resultResVo.setResultVo(resultVo);
			return resultResVo;
		}
		
		//判断是否是商户端导出
		boolean isMerchant = StringUtils.isEmpty(req.getMerchantId()) ? false : true;
		
		String merchantName = null;
		String excelName1 = boss_excelName1;
		String[] tableTitle1 = boss_titile1;
		
		String excelName2 = boss_excelName2;
		String[] tableTitle2 = boss_titile2;
		
		String excelName3 = boss_excelName3;
		String[] tableTitle3 = boss_titile3;
		
		String zipName = boss_zipName;
		if(isMerchant){
			excelName1 = merchant_excelName1;
			tableTitle1 = merchant_titile1;
			
			excelName2 = merchant_excelName2;
			tableTitle2 = merchant_titile2;
			
			zipName = merchant_zipName;
			//查询商户名
			merchantName = getMerchantName(req.getMerchantId());
		}
		
		//1、导出积分结算购物明细
		excelName1 = this.getExcelName(excelName1, clientName, exportDate, merchantName);
		req.setIsQrcode("0");
		resultVo = this.exportTable1(req, excelWriter, clientName, exportDate,excelName1,tableTitle1,isMerchant);
		if(!ResultCode.success.getCode().equals(resultVo.getResultCode())){
			resultResVo.setResultVo(resultVo);
			return resultResVo;
		}
		
		//2、导出积分结算面对面明细
		excelName2 = this.getExcelName(excelName2, clientName, exportDate, merchantName);
		req.setIsQrcode("1");
		resultVo = this.exportTable2(req, excelWriter, clientName, exportDate,excelName2,tableTitle2,isMerchant);
		if(!ResultCode.success.getCode().equals(resultVo.getResultCode())){
			resultResVo.setResultVo(resultVo);
			return resultResVo;
		}
		
		//3、导出商户积分汇总统计(商户端，不导出商户汇总表)
		if(StringUtils.isEmpty(req.getMerchantId())){
			excelName3 = this.getExcelName(excelName3, clientName, exportDate, merchantName);
			req.setIsQrcode(null);
			resultVo = this.exportTable3(req, excelWriter, clientName, exportDate,excelName3,tableTitle3);
			if(!ResultCode.success.getCode().equals(resultVo.getResultCode())){
				resultResVo.setResultVo(resultVo);
				return resultResVo;
			}
		}
		
		zipName = this.getExcelName(zipName, clientName, exportDate, merchantName);
		String url = excelWriter.getUrl(zipName);
		resultResVo.setUrl(url);
		
		resultVo.setResultCode(ResultCode.success.getCode());
		resultResVo.setResultVo(resultVo);
		return resultResVo;
	}
	
	private String[] getExportMsg(PointSettlementReqVo req,Double bankPointCount,Double froadPointCount){
		String[] msg = new String[9];
		msg[0] = "结算时间";
		msg[1] = "开始时间";
		if(req.getStartTime() > 0){
			msg[2] = DateUtil.formatDateTime(DateUtil.DATE_FORMAT4, req.getStartTime());
		}else{
			msg[2] = "";
		}
		msg[3] = "结束时间";
		if(req.getEndTime() > 0){
			msg[4] = DateUtil.formatDateTime(DateUtil.DATE_FORMAT4, req.getEndTime());
		}else{
			msg[4] = "";
		}
		msg[5] = "银行积分合计";
		msg[6] = bankPointCount == null ? "" : String.valueOf(bankPointCount);
		
		msg[7] = "联盟积分合计";
		msg[8] = froadPointCount == null ? "" : String.valueOf(froadPointCount);
		
		return msg;
	}
	//组装查询条件
	private DBObject getQueryDBObject(PointSettlementReqVo req){
		DBObject where = new BasicDBObject();
		if(StringUtils.isNotEmpty(req.getClientId())){
			where.put(FieldMapping.CLIENT_ID.getMongoField(), req.getClientId());
		}
		if(StringUtils.isNotEmpty(req.getMerchantId())){
			where.put(FieldMapping.MERCHANT_ID.getMongoField(), req.getMerchantId());
		}
		if(StringUtils.isNotEmpty(req.getIsQrcode())){
			where.put("is_qrcode", Integer.parseInt(req.getIsQrcode()));
		}
		
		if (req.getStartTime() > 0 && req.getEndTime() > 0) {
			where.put("create_time",
					new BasicDBObject("$gte", req.getStartTime())
						.append("$lte", req.getEndTime()));
		} else if (req.getStartTime() > 0) {
			where.put("create_time",
					new BasicDBObject("$gte", req.getStartTime()));
		} else if (req.getEndTime() > 0) {
			where.put("create_time",
					new BasicDBObject("$lte", req.getEndTime()));
		}
		
		return where;
	}
	
	//分页page对象转换
	private MongoPage convertMongoPage(PageVo pagevo){
		MongoPage mongoPage = new MongoPage();
		mongoPage.setPageNumber(pagevo.getPageNumber());
		mongoPage.setPageSize(pagevo.getPageSize());
		mongoPage.setPageNumber(pagevo.getPageNumber());
		mongoPage.setLastPageNumber(pagevo.getLastPageNumber());
		// 第一条记录开始时间
		if (pagevo.getFirstRecordTime() > 0){
			mongoPage.setFirstRecordTime(pagevo.getFirstRecordTime());
		}
		// 最后一条记录开始时间
		if (pagevo.getLastRecordTime() > 0) {
			mongoPage.setLastRecordTime(pagevo.getLastRecordTime());
		}
		// 总记录数
		if (pagevo.getTotalCount() > 0){
			mongoPage.setTotalCount(pagevo.getTotalCount());
		}
		return mongoPage;
	}
	
	//对象集合转换
	@SuppressWarnings("unchecked")
	private List<PointSettlementDetailResVo> convertVo(List<?> list){
		List<PointSettlementDetailResVo> resVoList = new ArrayList<PointSettlementDetailResVo>();
		if(list == null){
			return resVoList;
		}
		//缓存用户的手机号
		Map<Long,String> mobileMap = new HashMap<Long, String>();
		for (PointSettlementDetailMongo mongoObj : (List<PointSettlementDetailMongo>) list) {
			PointSettlementDetailResVo vo = null;
			if (null != mongoObj) {
				
				//获取手机号
				String mobile = null;
				if(mongoObj.getMemberCode() != null){
					if(mobileMap.containsKey(mongoObj.getMemberCode())){
						mobile = mobileMap.get(mongoObj.getMemberCode());
					}else{
						mobile = this.queryMobilByMemberCode(mongoObj.getMemberCode());
						mobileMap.put(mongoObj.getMemberCode(),mobile);
					}
				}
				
				vo = convertVo(mongoObj,mobile);
			} else {
				vo = new PointSettlementDetailResVo();
			}
			resVoList.add(vo);
		}
		list = null;
		return resVoList;
	}
	
	//对象转换
	private PointSettlementDetailResVo convertVo(PointSettlementDetailMongo obj, String mobile){
		
		PointSettlementDetailResVo vo = new PointSettlementDetailResVo();
		vo.setOrderId(obj.getOrderId());
		vo.setMemberCode(String.valueOf(obj.getMemberCode()));
		vo.setMemberName(obj.getMemberName());
		vo.setMobile(mobile);
		vo.setProductName(obj.getProductName());
		vo.setProductPrice(Arith.div(obj.getProductPrice(), 1000, 2));
		vo.setProductQuantity(obj.getProductQuantity());
		vo.setProductTotalPrice(Arith.div(obj.getProductTotalPrice(), 1000, 2));
		vo.setTotalPrice(Arith.div(obj.getSettlementTotalPrice(), 1000, 2));
		vo.setPaymentMethod(obj.getPaymentMethod());
		vo.setBankPoint(obj.getBankPoint() == null ? 0 : Arith.div(obj.getBankPoint(), 1000, 2));
		vo.setFroadPoint(obj.getFroadPoint() == null ? 0 : Arith.div(obj.getFroadPoint(), 1000, 2));
		vo.setBankPointRate(StringUtils.isEmpty(obj.getBankPointRate()) ? "": (obj.getBankPointRate() + ":1"));
		vo.setCash(obj.getCash() != null ? Arith.div(obj.getCash(), 1000, 2) : 0);
		
		vo.setSettlementTime(obj.getCreateTime());
		vo.setMerchantName(obj.getMerchantName());
		vo.setClientId(obj.getClientId());
		return vo;
	}
	
	//根据memberCode查询用户手机号（调用会员系统的接口）
	private String queryMobilByMemberCode(Long memberCode){
		UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
		String mobile = "";
        if (userResult.getResult()) {// 请求成功
        	if(userResult.getUserList() != null && userResult.getUserList().size() == 1){
        		mobile = userResult.getUserList().get(0).getMobile();
        	}
        }
        return mobile;
	}
	
	//调用最新的银行积分比例
	@SuppressWarnings("unchecked")
	private String queryNewestbankPointRate(PointSettlementReqVo req) {
		//查询条件
		DBObject where = getQueryDBObject(req);
		
		// 分页查找
		MongoPage mongoPage = new MongoPage();
		mongoPage.setPageNumber(1);
		mongoPage.setPageSize(1);
		
		DBObject orderby = new BasicDBObject();
		orderby.put("create_time", -1);
		orderby.put("bank_point_rate", 1);
		
		BasicDBList values = new BasicDBList();
		values.add(new BasicDBObject("bank_point",new BasicDBObject(QueryOperators.GT,0)));
		where.put(QueryOperators.AND,values);
		
		//分页查找
		MongoPage resultPage =  mongo.findByPageWithSortObject(mongoPage,where,orderby, MONGO_TABLE, PointSettlementDetailMongo.class);
		
		List<PointSettlementDetailMongo> resultList = (List<PointSettlementDetailMongo>) resultPage.getItems();
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0).getBankPointRate();
		}else{
			return "";
		}
	}
	
	//统计商户的订单总数（按大订单号去重复）
	private Integer queryOrderCount(DBObject where,String merchantId, String merchantName){
		DB db = MongoService.getReadMongoDB();
		
		where.put("merchant_id", merchantId);
		where.put("merchant_name", merchantName);
		
	    //构造查询以及过滤条件
        BasicDBObject searchCmd = new BasicDBObject();      
        searchCmd.put("distinct", MONGO_TABLE);
        searchCmd.put("key", FieldMapping.ORDER_ID.getMongoField());
        searchCmd.append("query", where);
        CommandResult commandResult = db.command(searchCmd);
        BasicDBList arr = (BasicDBList)commandResult.get("values");
    
        //获取结果集长度
        return arr.size();
	}
	
	/**********导出操作************/
	//导出积分结算购物明细
	private ResultVo exportTable1(PointSettlementReqVo req,ExcelWriterPointReport excelWriter,String clientName,String exportDate,String excelName,String[] tableTitle,boolean isMerchant) {
		
		ResultVo resultVo = new ResultVo();
		
		//统计的信息
		PointSettlementResVo resVo = queryPointCount(req);
		if(!ResultCode.success.getCode().equals(resVo.getResultVo().getResultCode())){
			LogCvt.error("导出积分购物明细，查询购物积分结算汇总信息异常");
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("查询购物积分结算汇总信息异常");
			return resultVo;
		}
//		int sheetIndex = 0;
		
		String excelFileName = excelName;//.replace("{0}", clientName).replace("{1}", exportDate);
		
		//导出标头
		String[] msg = this.getExportMsg(req, resVo.getBankPointCount(), resVo.getFroadPointCount());
		
		
		try {
			excelWriter.writeCsv(head1, msg, tableTitle, excelFileName,null);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("导出积分购物明细，导出表头异常",e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("导出购物明细表头异常");
			return resultVo;
		}
		
		//导出数据
		int pageNumber = 1;
		resVo = new PointSettlementResVo();
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(querySize);
		pageVo.setPageNumber(pageNumber);
		req.setPage(pageVo);
		
		int numberIndex = 1;
		
		while(true){
			//查询数据
			queryPointDetail(req, resVo);
			
			if(!ResultCode.success.getCode().equals(resVo.getResultVo().getResultCode())){
				LogCvt.error("导出积分购物明细，查询明细数据异常，pageNumber=" + pageNumber);
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("查询明细数据异常");
				return resultVo;
			}
			
			List<PointSettlementDetailResVo> dataList = resVo.getDetailResVoList();
			if(dataList != null && dataList.size() > 0){
				List<List<String>> datas = new ArrayList<List<String>>();
				for(PointSettlementDetailResVo vo : dataList){
					List<String> list = new ArrayList<String>();
					list.add(String.valueOf(numberIndex++));
					list.add(vo.getOrderId());
					list.add(vo.getMemberName());
					list.add(vo.getMobile());
					list.add(vo.getProductName());
					list.add(String.valueOf(vo.getProductPrice()));
					list.add(String.valueOf(vo.getProductQuantity()));
					list.add(String.valueOf(vo.getProductTotalPrice()));
					list.add(String.valueOf(vo.getTotalPrice()));
					PaymentMethod pd = PaymentMethod.getByCode(vo.getPaymentMethod());
					list.add(pd != null ? pd.getDescribe() : "");
					list.add(vo.getBankPoint() == 0 ? "-" : String.valueOf(vo.getBankPoint()));
					list.add(vo.getBankPointRate());
					list.add(vo.getFroadPoint() == 0 ? "-" : String.valueOf(vo.getFroadPoint()));
					list.add(vo.getCash() ==0 ? "-" : String.valueOf(vo.getCash()));
					list.add(vo.getSettlementTime() > 0 ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, vo.getSettlementTime()) : "");
					
					if(!isMerchant){
						list.add(vo.getMerchantName());
						list.add(clientName);
					}
					doEmptyValue(list);
					
					datas.add(list);
				}
				
//				sheetIndex = (pageNumber-1)/maxSize;
//				//输出标头
//				if(pageNumber % maxSize == 1){
//					try {
//						excelWriter.writeDetialHead(head1, msg, titile1, sheelName1, sheetIndex, excelFileName);
//					} catch (Exception e) {
//						LogCvt.error("导出积分购物明细，生成excel标题信息异常,isQrcode=" + req.getIsQrcode(),e);
//						resultVo.setResultCode(ResultCode.failed.getCode());
//						resultVo.setResultDesc("生成excel标题信息异常");
//						return resultVo;
//					}
//				}
//				
//				try {
//					excelWriter.writeDetialData(datas, sheelName1, sheetIndex, excelFileName);
//				} catch (Exception e) {
//					e.printStackTrace();
//					LogCvt.error("导出积分购物明细，导出明细数据异常，pageNumber=" + pageNumber ,e);
//					resultVo.setResultCode(ResultCode.failed.getCode());
//					resultVo.setResultDesc("导出购物明细数据异常");
//					return resultVo;
//				} 
				
				try {
					excelWriter.writeCsv(head1, msg, tableTitle, excelFileName,datas);
				} catch (Exception e) {
					e.printStackTrace();
					LogCvt.error("导出积分购物明细，导出明细数据异常，pageNumber=" + pageNumber ,e);
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("导出购物明细数据异常");
					return resultVo;
				}
			}
			
			
			
			pageVo = resVo.getPage();
			if(pageVo.getPageCount() > pageNumber){
				pageVo.setPageSize(querySize);
				pageVo.setPageNumber(++pageNumber);
				req.setPage(pageVo);
			}else{
				break;
			}
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		return resultVo;
	}
	
	//导出积分结算购物明细
	private ResultVo exportTable2(PointSettlementReqVo req,ExcelWriterPointReport excelWriter,String clientName,String exportDate,String excelName,String[] tableTitle,boolean isMerchant) {
		
		ResultVo resultVo = new ResultVo();
		
		//统计的信息
		PointSettlementResVo resVo = queryPointCount(req);
		if(!ResultCode.success.getCode().equals(resVo.getResultVo().getResultCode())){
			LogCvt.error("导出面对面惠付明细，查询面对面惠付汇总信息异常");
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("查询面对面惠付汇总信息异常");
			return resultVo;
		}
//		int sheetIndex = 0;
		
		String excelFileName = excelName;//.replace("{0}", clientName).replace("{1}", exportDate);
		
		//导出标头
		String[] msg = this.getExportMsg(req, resVo.getBankPointCount(), resVo.getFroadPointCount());
		
		try {
			excelWriter.writeCsv(head2, msg, tableTitle, excelFileName,null);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("导出面对面惠付明细，导出表头异常",e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("导出面对面惠付明细表头异常");
			return resultVo;
		}
		
		//导出数据
		int pageNumber = 1;
		resVo = new PointSettlementResVo();
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(querySize);
		pageVo.setPageNumber(pageNumber);
		req.setPage(pageVo);
		
		int numberIndex = 1;
		
		while(true){
			//查询数据
			queryPointDetail(req, resVo);
			
			if(!ResultCode.success.getCode().equals(resVo.getResultVo().getResultCode())){
				LogCvt.error("导出面对面惠付明细，查询明细数据异常，pageNumber=" + pageNumber);
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("查询明细数据异常");
				return resultVo;
			}
			
			List<PointSettlementDetailResVo> dataList = resVo.getDetailResVoList();
			if(dataList != null && dataList.size() > 0){
				List<List<String>> datas = new ArrayList<List<String>>();
				for(PointSettlementDetailResVo vo : dataList){
					List<String> list = new ArrayList<String>();
					list.add(String.valueOf(numberIndex++));
					list.add(vo.getOrderId());
					list.add(vo.getMemberName());
					list.add(vo.getMobile());
					list.add(String.valueOf(vo.getTotalPrice()));
					PaymentMethod pd = PaymentMethod.getByCode(vo.getPaymentMethod());
					list.add(pd != null ? pd.getDescribe() : "");
					
					list.add(vo.getBankPoint() ==0 ? "-" : String.valueOf(vo.getBankPoint()));
					list.add(vo.getBankPointRate());
					list.add(vo.getFroadPoint() ==0 ? "-" : String.valueOf(vo.getFroadPoint()));
					list.add(vo.getCash() ==0 ? "-" : String.valueOf(vo.getCash()));
					
					list.add(vo.getSettlementTime() > 0 ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, vo.getSettlementTime()) : "");
					
					if(!isMerchant){
						list.add(vo.getMerchantName());
						list.add(clientName);
					}
					doEmptyValue(list);
					
					datas.add(list);
				}
				
//				sheetIndex = (pageNumber-1)/maxSize;
//				//输出标头
//				if(pageNumber % maxSize == 1){
//					try {
//						excelWriter.writeDetialHead(head2, msg, titile2, sheelName2, sheetIndex, excelFileName);
//					} catch (Exception e) {
//						LogCvt.error("导出面对面惠付明细，生成excel标题信息异常,isQrcode=" + req.getIsQrcode(),e);
//						resultVo.setResultCode(ResultCode.failed.getCode());
//						resultVo.setResultDesc("生成excel标题信息异常");
//						return resultVo;
//					}
//				}
//				
//				try {
//					excelWriter.writeDetialData(datas, sheelName2, sheetIndex, excelFileName);
//				} catch (Exception e) {
//					LogCvt.error("导出面对面惠付明细，导出明细数据异常，pageNumber=" + pageNumber ,e);
//					resultVo.setResultCode(ResultCode.failed.getCode());
//					resultVo.setResultDesc("导出面对面惠付明细数据异常");
//					return resultVo;
//				} 
				
				
				try {
					excelWriter.writeCsv(head2, msg, tableTitle, excelFileName,datas);
				} catch (Exception e) {
					LogCvt.error("导出面对面惠付明细，导出明细数据异常，pageNumber=" + pageNumber ,e);
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("导出面对面惠付明细数据异常");
					return resultVo;
				}
			}
			
			
			
			pageVo = resVo.getPage();
			if(pageVo.getPageCount() > pageNumber){
				pageVo.setPageSize(querySize);
				pageVo.setPageNumber(++pageNumber);
				req.setPage(pageVo);
			}else{
				break;
			}
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		return resultVo;
	}
	
	//导出商户汇总报表
	private ResultVo exportTable3(PointSettlementReqVo req,ExcelWriterPointReport excelWriter,String clientName,String exportDate,String excelName,String[] tableTitle) {
		
		ResultVo resultVo = new ResultVo();
		
		//统计的信息
		req.setIsQrcode(null);
		PointSettlementResVo resVo = queryPointCount(req);
		if(!ResultCode.success.getCode().equals(resVo.getResultVo().getResultCode())){
			LogCvt.error("导出商户汇总报表，查询积分汇总信息异常");
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("查询商户汇总信息异常");
			return resultVo;
		}
//		int sheetIndex = 0;
		String excelFileName = excelName;//.replace("{0}", clientName).replace("{1}", exportDate);
		
		//导出标头
		String[] msg = this.getExportMsg(req, resVo.getBankPointCount(), resVo.getFroadPointCount());
		
		try {
			excelWriter.writeCsv(head3, msg, tableTitle, excelFileName,null);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("导出商户汇总报表，导出表头异常",e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("导出商户汇总报表表头异常");
			return resultVo;
		}
		
		//导出数据
		int pageNumber = 1;
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(querySize);
		pageVo.setPageNumber(pageNumber);
		req.setPage(pageVo);
		
		int numberIndex = 1;
		
		while(true){
			//查询数据
			PointSettlementMerchantResVo resDetailVo = queryMerchantPointCount(req);
			
			if(!ResultCode.success.getCode().equals(resDetailVo.getResultVo().getResultCode())){
				LogCvt.error("导出商户汇总报表，查询明细数据异常，pageNumber=" + pageNumber);
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("导出商户汇总报表，查询明细数据异常");
				return resultVo;
			}
			
			List<PointSettlementMerchantDetailResVo> dataList = resDetailVo.getDetailResVoList();
			if(dataList != null && dataList.size() > 0){
				List<List<String>> datas = new ArrayList<List<String>>();
				for(PointSettlementMerchantDetailResVo vo : dataList){
					List<String> list = new ArrayList<String>();
					list.add(String.valueOf(numberIndex++));
					list.add(vo.getMerchantName());
					
					list.add(vo.getBankPointCount() ==0 ? "-" : String.valueOf(vo.getBankPointCount()));
					list.add(vo.getBankPointRate());
					list.add(vo.getFroadPointCount() ==0 ? "-" : String.valueOf(vo.getFroadPointCount()));

					list.add(String.valueOf(vo.getOrderCount()));
					
					doEmptyValue(list);
					
					datas.add(list);
				}
				
//				sheetIndex = (pageNumber-1)/maxSize;
//				//输出标头
//				if(pageNumber % maxSize == 1){
//					try {
//						excelWriter.writeDetialHead(head3, msg, titile3, sheelName3, sheetIndex, excelFileName);
//					} catch (Exception e) {
//						LogCvt.error("导出商户汇总报表，生成excel标题信息异常,isQrcode=" + req.getIsQrcode(),e);
//						resultVo.setResultCode(ResultCode.failed.getCode());
//						resultVo.setResultDesc("导出商户汇总报表，生成excel标题信息异常");
//						return resultVo;
//					}
//				}
//				try {
//					excelWriter.writeDetialData(datas, sheelName3, sheetIndex, excelFileName);
//				} catch (Exception e) {
//					LogCvt.error("导出商户汇总报表，导出明细数据异常，pageNumber=" + pageNumber ,e);
//					resultVo.setResultCode(ResultCode.failed.getCode());
//					resultVo.setResultDesc("导出商户汇总报表，导出明细数据异常");
//					return resultVo;
//				} 
				
				try {
					excelWriter.writeCsv(head3, msg, tableTitle, excelFileName,datas);
				} catch (Exception e) {
					LogCvt.error("导出商户汇总报表，导出明细数据异常，pageNumber=" + pageNumber ,e);
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc("导出商户汇总报表，导出明细数据异常");
					return resultVo;
				}
			}
			
			pageVo = resDetailVo.getPage();
			if(pageVo.getPageCount() > pageNumber){
				pageVo.setPageSize(querySize);
				pageVo.setPageNumber(++pageNumber);
				req.setPage(pageVo);
			}else{
				break;
			}
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		return resultVo;
	}
	
	/**
	 * 处理控制，如果是空值，显示“-”
	 * Function : doEmptyValue<br/>
	 * 2016年1月29日 下午1:19:22 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param list
	 */
	private void doEmptyValue(List<String> list){
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				if(StringUtils.isEmpty(list.get(i))){
					list.set(i, "-");
				}
			}
		}
	}
	
	/**
	 * 根据商户ID查询商户名称
	 * Function : getMerchantName<br/>
	 * 2016年2月18日 下午5:13:32 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param merchantId
	 * @return
	 */
	private String getMerchantName(String merchantId){
		DBObject where = new BasicDBObject();
		where.put("_id", merchantId);
		MerchantDetail detail = mongo.findOne(where, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
		if(detail != null){
			return detail.getMerchantName();
		}else{
			return "-";
		}
	}
	public static void main(String[] args) {
		PropertiesUtil.load();
		
		PointSettlementReqVo req = new PointSettlementReqVo();
		req.setClientId("chongqing");
//		req.setIsQrcode("0");
		req.setMerchantId("18E3F4200000");
		
		PageVo pageVo = new PageVo();
		
		pageVo.setPageNumber(1);
		pageVo.setPageSize(20);
//		pageVo.setLastPageNumber(9);
//		pageVo.setLastRecordTime(1454038405975L);
//		pageVo.setFirstRecordTime(1454304733021L);
		
		req.setPage(pageVo);
//		req.setStartTime(1451923200000L);
//		req.setEndTime(1454601599059L);
		
		
		
//		startDate=1451923200000&endDate=&pageNumber=11&pageSize=20&lastPageNumber=9&firstRecordTime=1454304733021&lastRecordTime=
				
//		
		PointSettlementSupportImpl impl = new PointSettlementSupportImpl();
		//汇总测试
//		System.out.println(JSonUtil.toJSonString(impl.queryPointCount(req)));
		//明细测试
//		System.out.println(JSonUtil.toJSonString(impl.queryPointDetail(req)));
		
		//测试商户汇总
//		System.out.println(JSonUtil.toJSonString(impl.queryMerchantPointCount(req)));
		
		//测试导出
		System.out.println(JSonUtil.toJSonString(impl.exportPointSettlement(req)));
		
	}
}

