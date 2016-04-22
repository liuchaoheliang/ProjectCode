package com.froad.db.mongo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ModuleID;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OutletLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.logic.impl.OutletLogicImpl;
import com.froad.po.CreateTimeFilter;
import com.froad.po.MemberInfo;
import com.froad.po.Outlet;
import com.froad.po.OutletComment;
import com.froad.po.OutletCommentLevelAmount;
import com.froad.po.PointInfo;
import com.froad.po.RecommentNotEmpty;
import com.froad.po.StarLevelFilter;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * <p>
 * Title: OutletCommentMongo.java
 * </p>
 * <p>
 * Description: 门店(商户)评论的 mongo 操作
 * </p>
 * 
 * @author lf
 * @version 1.0
 * @created 2015年04月16日
 */
public class OutletCommentMongo {

	private static SimpleID simpleID = new SimpleID(ModuleID.outletcomment);
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String CB_OUTLET_COMMENT = "cb_outlet_comment";
	private static final String COMMAND_SET = "$set";
	
	public static final String MONGO_KEY_ID                 = "_id";
	public static final String MONGO_KEY_MERCHANT_ID         = "merchant_id";
	public static final String MONGO_KEY_OUTLET_ID           = "outlet_id";
	public static final String MONGO_KEY_CREATE_TIME         = "create_time";
	public static final String MONGO_KEY_OUTLET_NAME         = "outlet_name";
	public static final String MONGO_KEY_ORDER_VALUE         = "order_value";
	public static final String MONGO_KEY_MEMBER_INFO         = "member_info";
	public static final String MONGO_KEY_MEMBER_CODE         = "member_code";
	public static final String MONGO_KEY_MEMBER_NAME         = "member_name";
	public static final String MONGO_KEY_POINT_INFO          = "point_info";
	public static final String MONGO_KEY_STAR_LEVEL          = "star_level";
	public static final String MONGO_KEY_COMMENT_DESCRIPTION = "comment_description";
	public static final String MONGO_KEY_RECOMMENT           = "recomment";
	public static final String MONGO_KEY_CLIENT_ID           = "clientId";
	public static final String MONGO_KEY_FORG_CODE           = "forg_code";
	public static final String MONGO_KEY_SORG_CODE           = "sorg_code";
	public static final String MONGO_KEY_TORG_CODE           = "torg_code";
	public static final String MONGO_KEY_LORG_CODE           = "lorg_code";
	public static final String MONGO_KEY_MERCHANT_NAME       = "merchant_name";
	public static final String MONGO_KEY_RECOMMENT_TIME      = "recomment_time";
	public static final String MONGO_KEY_MERCHANT_USER_NAME  = "merchant_user_name";
	public static final String MONGO_KEY_OUTLET_PHONE        = "outlet_phone";
	public static final String MONGO_KEY_OUTLET_IMAGE        = "outlet_image";
	public static final String MONGO_KEY_MEMBER_HEAD         = "member_head";
	public static final String MONGO_KEY_COMMENT_TYPE        = "comment_type";
	public static final String MONGO_KEY_ORDER_ID       	 = "order_Id";
	
	private static Sort SORT = new Sort(MONGO_KEY_CREATE_TIME, OrderBy.DESC);
	
	// 机构 - 业务逻辑接口
//	private static OrgCommonLogic orgCommonLogic = new OrgCommonLogicImpl();
	static CommonLogic commonLogic = new CommonLogicImpl();
	// 门店 - 业务逻辑接口
	private static OutletLogic outletLogic = new OutletLogicImpl();
	
	/**
	 * 新增 门店(商户)评论
	 * @Title: addOutletComment
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletComment
	 * @return
	 * @return boolean 
	 * @throws
	 */
	public boolean addOutletComment(OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager();
		// 得到并设置评论的商户id的各个机构信息
		setOrgValues(outletComment);
		outletComment.setCreateTime(System.currentTimeMillis()); // 设置创建时间
		// 把 OutletComment 转换成 DBObject
		DBObject dbObject = changeToDBObjectOfAdd(outletComment);
		LogCvt.debug("添加 OutletComment:"+JSON.toJSONString(dbObject, true));
		int addCount = manager.add(dbObject, CB_OUTLET_COMMENT);
		LogCvt.debug("添加 OutletComment,mongo返回:" + addCount);
		return addCount > -1;
		
	}
	
	// 得到并设置评论的商户id的各个机构信息
	private static void setOrgValues(OutletComment outletComment){
		
		String clientId = outletComment.getClientId();
		String merchantId = outletComment.getMerchantId();
		try{
			Map<OrgLevelEnum, String> orgValue = commonLogic.getSuperOrgByMerchantId(clientId, merchantId);
//			Map<OrgLevelEnum, String> orgValue = orgCommonLogic.getSuperOrgByMerchantId(merchantId);
			if( orgValue != null ){
				outletComment.setForgCode(orgValue.get(OrgLevelEnum.orgLevel_one));
				outletComment.setSorgCode(orgValue.get(OrgLevelEnum.orgLevel_two));
				outletComment.setTorgCode(orgValue.get(OrgLevelEnum.orgLevel_three));
				outletComment.setLorgCode(orgValue.get(OrgLevelEnum.orgLevel_four));
			}
		}catch(Exception e){
			LogCvt.error("设置评论的商户的各种机构信息失败，原因:" + e.getMessage(), e); 
		}
	}
	
	// 把 OutletComment 转换成 DBObject 供 add 使用
	private static DBObject changeToDBObjectOfAdd(OutletComment outletComment){
		
		outletComment.setId(getOutletCommentId());
		
		DBObject dbA = new BasicDBObject();
		
		dbA.put(MONGO_KEY_ID, outletComment.getId());
		dbA.put(MONGO_KEY_MERCHANT_ID, outletComment.getMerchantId());
		dbA.put(MONGO_KEY_OUTLET_ID, outletComment.getOutletId());
		dbA.put(MONGO_KEY_CREATE_TIME, outletComment.getCreateTime());
		dbA.put(MONGO_KEY_OUTLET_NAME, outletComment.getOutletName());
		dbA.put(MONGO_KEY_ORDER_VALUE, outletComment.getOrderValue());
		DBObject memberDBO = new BasicDBObject();
		memberDBO.put(MONGO_KEY_MEMBER_CODE, outletComment.getMemberCode());
		memberDBO.put(MONGO_KEY_MEMBER_NAME, outletComment.getMemberName());
		memberDBO.put(MONGO_KEY_MEMBER_HEAD, outletComment.getMemberHead());
		dbA.put(MONGO_KEY_MEMBER_INFO, memberDBO);
		DBObject pointDBO = new BasicDBObject();
		pointDBO.put(MONGO_KEY_STAR_LEVEL, outletComment.getStarLevel());
		pointDBO.put(MONGO_KEY_COMMENT_DESCRIPTION, outletComment.getCommentDescription());
		dbA.put(MONGO_KEY_POINT_INFO, pointDBO);
		dbA.put(MONGO_KEY_RECOMMENT, "");
		dbA.put(MONGO_KEY_RECOMMENT_TIME, 0);
		dbA.put(MONGO_KEY_MERCHANT_USER_NAME, "");
		dbA.put(MONGO_KEY_CLIENT_ID, outletComment.getClientId());
		dbA.put(MONGO_KEY_FORG_CODE, outletComment.getForgCode());
		dbA.put(MONGO_KEY_SORG_CODE, outletComment.getSorgCode());
		dbA.put(MONGO_KEY_TORG_CODE, outletComment.getTorgCode());
		dbA.put(MONGO_KEY_LORG_CODE, outletComment.getLorgCode());
		dbA.put(MONGO_KEY_MERCHANT_NAME, outletComment.getMerchantName());
		dbA.put(MONGO_KEY_OUTLET_PHONE, outletComment.getOutletPhone());
		dbA.put(MONGO_KEY_OUTLET_IMAGE, outletComment.getOutletImage());
		dbA.put(MONGO_KEY_COMMENT_TYPE,String.valueOf(outletComment.getCommentType()));
		dbA.put(MONGO_KEY_ORDER_ID,String.valueOf(outletComment.getOrderId()));
		
		return dbA;
	}
	
	// 得到门店评论主键
	private static String getOutletCommentId(){
		return simpleID.nextId();
	}
	
	/**
	 * 删除 门店(商户)评论
	 * @Title: deleteOutletComment
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return boolean 
	 * @throws
	 */
	public boolean deleteOutletComment(String id) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		DBObject dbObject = changeToDBObjectOfOne(id);
		LogCvt.debug("删除 OutletComment:"+JSON.toJSONString(dbObject, true));
		int rint = manager.remove(dbObject, CB_OUTLET_COMMENT);
		LogCvt.debug("删除 OutletComment受影响行数:" + rint);
		return rint > 0;
	}
	
	// 把 OutletComment 转换成 DBObject 供 单个操作 使用(查询单个 & 删除单个)
	private static DBObject changeToDBObjectOfOne(String id){
			
		DBObject dbQ = new BasicDBObject();
		dbQ.put(MONGO_KEY_ID, id);
		return dbQ;
	}
	
	/**
	 * 更新 门店(商户)评论
	 * @Title: updateOutletComment
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletComment
	 * @return
	 * @return boolean 
	 * @throws
	 */
	public boolean updateOutletComment(OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		DBObject setObject = changeToDBObjectOfUpdateSet(outletComment);
		DBObject wheObject = changeToDBObjectOfUpdateWhe(outletComment);
		LogCvt.debug("更新 OutletComment:"+JSON.toJSONString(outletComment, true));
		int rint = manager.update(setObject, wheObject, CB_OUTLET_COMMENT, COMMAND_SET);
		LogCvt.debug("更新 OutletComment受影响行数:" + rint);
		return rint > 0;
		
	}

	// 把 OutletComment 转换成 DBObject 供 update 使用的 set
	private static DBObject changeToDBObjectOfUpdateSet(OutletComment outletComment){
		
		DBObject dbS = new BasicDBObject();
		
		if( outletComment.getCreateTime() > 0 ){
			dbS.put(MONGO_KEY_CREATE_TIME, outletComment.getCreateTime());
		}
		if( outletComment.getOrderValue() > -1 ){
			dbS.put(MONGO_KEY_ORDER_VALUE, outletComment.getOrderValue());
		}
		if( outletComment.getStarLevel() > -1 || 
			outletComment.getCommentDescription() != null ){
			
			DBObject pointDBO = new BasicDBObject();
			if( outletComment.getStarLevel() > -1 ){
				pointDBO.put(MONGO_KEY_STAR_LEVEL, outletComment.getStarLevel());
			}
			if( outletComment.getCommentDescription() != null ){
				pointDBO.put(MONGO_KEY_COMMENT_DESCRIPTION, outletComment.getCommentDescription());
			}
			dbS.put(MONGO_KEY_POINT_INFO, pointDBO);
		}
		
		if( outletComment.getOutletImage() != null ){
			dbS.put(MONGO_KEY_OUTLET_IMAGE, outletComment.getOutletImage());
		}
		
		return dbS;
	}
	
	// 把 OutletComment 转换成 DBObject 供 update 使用的 where
	private static DBObject changeToDBObjectOfUpdateWhe(OutletComment outletComment){
			
		DBObject dbW = new BasicDBObject();
		dbW.put(MONGO_KEY_ID, outletComment.getId());
			
		return dbW;
	}
	
	/**
	 * 查询单个 门店(商户)评论
	 * @Title: findOutletCommentById
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return OutletComment 
	 * @throws
	 */
	public OutletComment findOutletCommentById(String id) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		DBObject dbObject = changeToDBObjectOfOne(id);
		LogCvt.debug("查询 OutletComment:"+JSON.toJSONString(dbObject, true));
		OutletComment outletComment = manager.findOne(dbObject, CB_OUTLET_COMMENT, OutletComment.class);
		outletComment = changeSet(outletComment);
		LogCvt.debug("查询 OutletComment 结果:" + JSON.toJSONString(outletComment, true));
		return outletComment;
	}
	
	private static OutletComment changeSet(OutletComment outletComment){
		
		MemberInfo memberInfo = outletComment.getMemberInfo();
		if( memberInfo != null ){
			outletComment.setMemberCode(memberInfo.getMemberCode());
			outletComment.setMemberName(memberInfo.getMemberName());
			outletComment.setMemberHead(memberInfo.getMemberHead());
		}
		PointInfo pointInfo = outletComment.getPointInfo();
		if( pointInfo != null ){
			outletComment.setStarLevel(pointInfo.getStarLevel());
			outletComment.setCommentDescription(pointInfo.getCommentDescription());
		}
		
		return outletComment;
	}
	
	/**
	 * 查询多条 门店(商户)评论
	 * @Title: findOutletCommentList
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return OutletComment 
	 * @throws
	 */
	public List<OutletComment> findOutletCommentList(OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		List<OutletComment> result = new ArrayList<OutletComment>();
		DBObject dbObject = changeToDBObjectOfFindAll(outletComment);
		LogCvt.debug("查询OutletCommentList条件:"+JSON.toJSONString(dbObject, true));
		List<OutletComment> tResult = (List<OutletComment>)manager.findAll(dbObject, CB_OUTLET_COMMENT, OutletComment.class);
		if( tResult != null && tResult.size() > 0 ){
			LogCvt.debug("查询OutletCommentList结果条数:"+tResult.size());
			for( OutletComment toc : tResult ){
				toc = changeSet(toc);
				result.add(toc);
			}
		}else{
			LogCvt.debug("查询OutletCommentList无数据");
		}
		
		return result;
	}
	
	// 把 OutletComment 转换成 DBObject 供 findAll 使用
	private static DBObject changeToDBObjectOfFindAll(OutletComment outletComment){
			
		DBObject dbQ = new BasicDBObject();
			
		// 商户id
		if( outletComment.getMerchantId() != null ){
			dbQ.put(MONGO_KEY_MERCHANT_ID, outletComment.getMerchantId());
		}
		
		// 门店id
		if( outletComment.getOutletId() != null ){
			dbQ.put(MONGO_KEY_OUTLET_ID, outletComment.getOutletId());
		}
		
		// 会员编号
		if( outletComment.getMemberCode() != null ){
			dbQ.put(MONGO_KEY_MEMBER_INFO+"."+MONGO_KEY_MEMBER_CODE, outletComment.getMemberCode());
		}
		
		// 客户端id
		if( outletComment.getClientId() != null && !"".equals(outletComment.getClientId()) ){
			dbQ.put(MONGO_KEY_CLIENT_ID, outletComment.getClientId());
		}
		
		// 商户名称
		String merchantName = outletComment.getMerchantName();
//		if( merchantName != null ){
//			dbQ.put(MONGO_KEY_MERCHANT_NAME, new BasicDBObject("$regex", Pattern.compile(".*"+merchantName+".*", Pattern.CASE_INSENSITIVE).toString()));
//		}
		if(StringUtils.isNotBlank(merchantName)){
			char[] cs = merchantName.toCharArray();
			String regexStr = ".*";
			for (char c : cs) {
				regexStr += c + ".*";
			}
			regexStr += "";
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			dbQ .put(MONGO_KEY_MERCHANT_NAME, like);
		}
		
		// 门店名称
		String outletName = outletComment.getOutletName();
//		if( outletName != null ){
//			dbQ.put(MONGO_KEY_OUTLET_NAME, new BasicDBObject("$regex", Pattern.compile(".*"+outletName+".*", Pattern.CASE_INSENSITIVE).toString()));
//		}
		if(StringUtils.isNotBlank(outletName)){
			char[] cs = outletName.toCharArray();
			String regexStr = ".*";
			for (char c : cs) {
				regexStr += c + ".*";
			}
			regexStr += "";
			Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.CASE_INSENSITIVE);
//			Pattern pattern = Pattern.compile("^.*"+outletDetail.getOutletName()+".*$", Pattern.MULTILINE);
			BasicDBObject like = new BasicDBObject();
			like.append("$regex", pattern);
			
			dbQ .put(MONGO_KEY_OUTLET_NAME, like);
		}
		
		// 评论是否回复
		RecommentNotEmpty recommentNotEmpty = outletComment.getRecommentNotEmpty();
		if( recommentNotEmpty != null ){
			if( recommentNotEmpty.getNotEmpty() ){ // 回复不为空
				DBObject recommentQ = new BasicDBObject();
				recommentQ.put(QueryOperators.NE, "");
				dbQ.put(MONGO_KEY_RECOMMENT, recommentQ);
			}else{ // 回复为空
				dbQ.put(MONGO_KEY_RECOMMENT, "");
			}
			
		}
		
		// 评级
		StarLevelFilter starLevelFilter = outletComment.getStarLevelFilter();
		if( starLevelFilter != null ){
			dbQ.put(MONGO_KEY_POINT_INFO+"."+MONGO_KEY_STAR_LEVEL, starLevelFilter.getStarLevel());
		}
		
		// 创建时间
		CreateTimeFilter createTimeFilter = outletComment.getCreateTimeFilter();
		if( createTimeFilter != null ){
			Long begTime = createTimeFilter.getBegTime();
			Long endTime = createTimeFilter.getEndTime();
			if( begTime != null && begTime > 0 ){
				DBObject timeQ = new BasicDBObject();
				timeQ.put(QueryOperators.GTE, begTime);
				if( endTime != null && endTime > 0 ){
					timeQ.put(QueryOperators.LTE, endTime);
				}
				dbQ.put(MONGO_KEY_CREATE_TIME, timeQ);
			}else{
				if( endTime != null && endTime > 0 ){
					DBObject timeQ = new BasicDBObject();
					timeQ.put(QueryOperators.LTE, endTime);
					dbQ.put(MONGO_KEY_CREATE_TIME, timeQ);
				}
			}
		}
		
		return dbQ;
	}
	
	/**
	 * 分页查询 门店(商户)评论
	 * @Title: findOutletCommentByPage
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param Page<OutletComment>
	 * @param OutletComment
	 * 
	 * @return Page<OutletComment> 
	 * @throws
	 */
	public Page<OutletComment> findOutletCommentByPage(Page<OutletComment> page,
			OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
		mPage.setSort(SORT);
		DBObject dbObject = changeToDBObjectOfFindAll(outletComment);
		dbObject = changeToDBObjectByDate(dbObject, page);
		LogCvt.debug("分页查询OutletComment条件:"+JSON.toJSONString(dbObject, true));
		mPage = manager.findByPage(mPage, dbObject, CB_OUTLET_COMMENT, OutletComment.class);
		List<OutletComment> outletCommentList = (List<OutletComment>)mPage.getItems();
		if( outletCommentList != null && outletCommentList.size() > 0 ){
			LogCvt.debug("分页查询OutletComment结果条数:"+outletCommentList.size());
			for( OutletComment toc : outletCommentList ){
				toc = changeSet(toc);
			}
		}else{
			LogCvt.debug("分页查询OutletComment无数据");
		}
		page.setResultsContent(outletCommentList);
		page.setPageCount(mPage.getPageCount());
		page.setTotalCount(mPage.getTotalCount());
		
		return page;
	}
	
	// 转换成有时间过滤条件的 DBObject
	private static DBObject changeToDBObjectByDate(DBObject dbObject, Page<OutletComment> page){
		
		Long bagDate = page.getBegDate();
		Long endDate = page.getEndDate();
		if( bagDate != null && bagDate > 0 ){
			DBObject dateObject = new BasicDBObject();
			dateObject.put(QueryOperators.GTE, bagDate);
			if( endDate != null && endDate > 0 ){
				dateObject.put(QueryOperators.LTE, endDate);
			}
			dbObject.put(MONGO_KEY_CREATE_TIME, dateObject);
		}else{
			if( endDate != null && endDate > 0 ){
				DBObject dateObject = new BasicDBObject();
				dateObject.put(QueryOperators.LTE, endDate);
				dbObject.put(MONGO_KEY_CREATE_TIME, dateObject);
			}
		}
		
		return dbObject;
	}
	
	/**
	 * 分页查询 门店(商户)评论 - 带结构码的查询条件
	 * @Title: findOutletCommentPageByOrgCode
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param Page<OutletComment>
	 * @param OutletComment<B>(+orgCode)</B>
	 * 
	 * @return Page<OutletComment> 
	 * @throws
	 */
	public Page<OutletComment> findOutletCommentPageByOrgCode(Page<OutletComment> page,
			OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
		mPage.setSort(SORT);
		DBObject dbObject = changeToDBObjectOfFindAllByOrgCode(outletComment);
		dbObject = changeToDBObjectByDate(dbObject, page);
		LogCvt.debug("分页查询OutletComment条件:"+JSON.toJSONString(dbObject, true));
		mPage = manager.findByPage(mPage, dbObject, CB_OUTLET_COMMENT, OutletComment.class);
		List<OutletComment> outletCommentList = (List<OutletComment>)mPage.getItems();
		if( outletCommentList != null && outletCommentList.size() > 0 ){
			LogCvt.debug("分页查询OutletComment结果条数:"+outletCommentList.size());
			for( OutletComment toc : outletCommentList ){
				toc = changeSet(toc);
			}
		}else{
			LogCvt.debug("分页查询OutletComment无数据");
		}
		page.setResultsContent(outletCommentList);
		page.setPageCount(mPage.getPageCount());
		page.setTotalCount(mPage.getTotalCount());
		
		return page;
	}
	
	// 把 OutletComment 转换成 DBObject 供 findAll 使用 查询条件增加结构码
	private static DBObject changeToDBObjectOfFindAllByOrgCode(OutletComment outletComment){
		
		DBObject dbQ = changeToDBObjectOfFindAll(outletComment);
		
		String orgCode = outletComment.getOrgCode();
		String clientId = outletComment.getClientId();
		if(orgCode != null && orgCode.split(",").length > 0){
			List<String> orgCodeList = new ArrayList<String>(Arrays.asList(orgCode.split(",")));
			List<String> orgCodesCondition = commonLogic.queryLastOrgCode(clientId, orgCodeList);
			
			// in查询的条件code
			BasicDBList inValues = new BasicDBList();  
			for(String str : orgCodesCondition){
				inValues.add(str);
			}
			BasicDBList orValues = new BasicDBList();
			orValues.add(new BasicDBObject(MONGO_KEY_FORG_CODE, new BasicDBObject("$in", inValues)));
			orValues.add(new BasicDBObject(MONGO_KEY_SORG_CODE, new BasicDBObject("$in", inValues)));
			orValues.add(new BasicDBObject(MONGO_KEY_TORG_CODE, new BasicDBObject("$in", inValues)));
			orValues.add(new BasicDBObject(MONGO_KEY_LORG_CODE, new BasicDBObject("$in", inValues)));
			dbQ.put("$or", orValues);
		}
		
		
//		OrgLevelEnum orgLevelEnum = commonLogic.getOrgLevelByOrgCode(orgCode, clientId);
//		if( orgLevelEnum != null ){
//			String level = orgLevelEnum.getLevel();
//			if( OrgLevelEnum.orgLevel_one.getLevel().equals(level) ){
//				dbQ.put(MONGO_KEY_FORG_CODE, orgCode);
//			}else if( OrgLevelEnum.orgLevel_two.getLevel().equals(level) ){
//				dbQ.put(MONGO_KEY_SORG_CODE, orgCode);
//			}else if( OrgLevelEnum.orgLevel_three.getLevel().equals(level) ){
//				dbQ.put(MONGO_KEY_TORG_CODE, orgCode);
//			}else if( OrgLevelEnum.orgLevel_four.getLevel().equals(level) ){
//				dbQ.put(MONGO_KEY_LORG_CODE, orgCode);
//			}
//		}
		
		return dbQ;
	}
	
	/**
	 * 增加 评论回复
	 * @Title: addOutletCommentOfRecomment
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletComment
	 * 
	 * @return boolean
	 * @throws
	 */
	public boolean addOutletCommentOfRecomment(OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager(); 
		DBObject recommentObject = changeToDBObjectOfAddRecomment(outletComment);
		DBObject wheObject = changeToDBObjectOfUpdateWhe(outletComment);
		LogCvt.debug("增加回复:"+JSON.toJSONString(recommentObject, true)+" - "+JSON.toJSONString(wheObject, true));
		int rint = manager.update(recommentObject, wheObject, CB_OUTLET_COMMENT, COMMAND_SET);
		LogCvt.debug("增加回复受影响行数:" + rint);
		return rint > 0;
	}

	// 把 OutletComment 转换成 DBObject 供 增加评论的回复 使用
	private static DBObject changeToDBObjectOfAddRecomment(OutletComment outletComment){
			
		DBObject dbS = new BasicDBObject();
			
		dbS.put(MONGO_KEY_RECOMMENT, outletComment.getRecomment());
		dbS.put(MONGO_KEY_RECOMMENT_TIME, outletComment.getRecommentTime()<=0?System.currentTimeMillis():outletComment.getRecommentTime());
		dbS.put(MONGO_KEY_MERCHANT_USER_NAME, outletComment.getMerchantUserName());
			
		return dbS;
	}
	
	/**
	 * 门店评论数量查询
	 * @Title: getOutletCommentSum
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletComment
	 * 
	 * @return int
	 * @throws
	 */
	public int getOutletCommentSum(OutletComment outletComment) throws Exception {
		
		MongoManager manager = new MongoManager();
		DBObject dbObject = changeToDBObjectOfQuerySum(outletComment);			
		LogCvt.debug("门店评论数量查询 参数:"+JSON.toJSONString(dbObject, true));
		int result = manager.getCount(dbObject, CB_OUTLET_COMMENT);
		LogCvt.debug("门店评论数量查询 结果:" + result);
		return result;
	}
	
	// 把 OutletComment 转换成 DBObject 供 QuerySum 使用
	private static DBObject changeToDBObjectOfQuerySum(OutletComment outletComment){
		
		DBObject dbQ = new BasicDBObject();
		
		dbQ.put(MONGO_KEY_MERCHANT_ID, outletComment.getMerchantId());
		dbQ.put(MONGO_KEY_OUTLET_ID, outletComment.getOutletId());
		if( outletComment.getClientId() != null && !"".equals(outletComment.getClientId())){
			dbQ.put(MONGO_KEY_CLIENT_ID, outletComment.getClientId());
		}
		if( outletComment.getMemberCode() != null ){
			dbQ.put(MONGO_KEY_MEMBER_INFO+"."+MONGO_KEY_MEMBER_CODE, outletComment.getMemberCode());
		}
		if( outletComment.getStarLevel() > 0 ){
			dbQ.put(MONGO_KEY_POINT_INFO+"."+MONGO_KEY_STAR_LEVEL, outletComment.getStarLevel());
		}

		return dbQ;
	}
	
	/**
	 * 门店评论级别数量查询
	 * @Title: getOutletCommentLevelAmount
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletComment - merchantId & outletId
	 * 
	 * @return OutletCommentLevelAmount
	 * @throws
	 */
	public OutletCommentLevelAmount getOutletCommentLevelAmount(
			OutletComment outletComment) throws Exception {
		
		OutletCommentLevelAmount result = new OutletCommentLevelAmount();
		
		LogCvt.debug("门店评论级别数量查询 参数:"+JSON.toJSONString(outletComment, true));
		MongoManager manager = new MongoManager(); 
		outletComment.setStarLevel(5);
		DBObject dbObject = changeToDBObjectOfQuerySum(outletComment);			
		int sum = manager.getCount(dbObject, CB_OUTLET_COMMENT);
		result.setLevelAmountFive(sum);
		outletComment.setStarLevel(4);
		dbObject = changeToDBObjectOfQuerySum(outletComment);			
		sum = manager.getCount(dbObject, CB_OUTLET_COMMENT);
		result.setLevelAmountFour(sum);
		outletComment.setStarLevel(3);
		dbObject = changeToDBObjectOfQuerySum(outletComment);			
		sum = manager.getCount(dbObject, CB_OUTLET_COMMENT);
		result.setLevelAmountThree(sum);
		outletComment.setStarLevel(2);
		dbObject = changeToDBObjectOfQuerySum(outletComment);			
		sum = manager.getCount(dbObject, CB_OUTLET_COMMENT);
		result.setLevelAmountTwo(sum);
		outletComment.setStarLevel(1);
		dbObject = changeToDBObjectOfQuerySum(outletComment);			
		sum = manager.getCount(dbObject, CB_OUTLET_COMMENT);
		result.setLevelAmountOne(sum);
		result.setOutletId(outletComment.getOutletId());
		Outlet outlet = outletLogic.findOutletByOutletId(outletComment.getOutletId());
		result.setOutletName(outlet==null?"未知门店":outlet.getOutletName());
		LogCvt.debug("门店评论级别数量查询 结果:"+JSON.toJSONString(result, true));
		
		return result;
	}
	
	/**
	 * 商户评论级别数量查询
	 * @Title: getMerchantCommentLevelAmount
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * 
	 * @return list<OutletCommentLevelAmount>
	 * @throws
	 */
	public List<OutletCommentLevelAmount> getMerchantCommentLevelAmount(
			String merchantId) throws Exception {
		
		List<OutletCommentLevelAmount> result = new ArrayList<OutletCommentLevelAmount>();
		
		LogCvt.debug("商户评论级别数量查询 参数:"+merchantId);
		MongoManager manager = new MongoManager(); 
		// 查询商户id下的所有门店id
		DBObject dbObject = new BasicDBObject();
		dbObject.put(MONGO_KEY_MERCHANT_ID, merchantId);
		List<String> fieldNames = new ArrayList<String>();
		fieldNames.add(MONGO_KEY_OUTLET_ID);
		List<OutletComment> outletCommentList = (List<OutletComment>)manager.findAllByFieldNames(dbObject, CB_OUTLET_COMMENT, fieldNames, OutletComment.class);
		// 去除重复outletId
		List<String> outletIds = removalRepeatOfOutletId(outletCommentList);
				
		// 循环 outletIds 查询门店评论级别数量
		if( outletIds != null && outletIds.size() > 0 ){
			OutletComment outletComment = new OutletComment();
			for( String outletId : outletIds ){
				outletComment.setMerchantId(merchantId);
				outletComment.setOutletId(outletId);
				OutletCommentLevelAmount outletCommentLevelAmount = this.getOutletCommentLevelAmount(outletComment);
				result.add(outletCommentLevelAmount);
			}
		}
		LogCvt.debug("商户评论级别数量查询 结果:"+JSON.toJSONString(result, true));
				
		return result;
	}
	
	// 去除重复outletId
	private static List<String> removalRepeatOfOutletId(List<OutletComment> outletCommentList){
		
		List<String> outletIds = new ArrayList<String>();
		
		Set<String> set = new HashSet<String>();
		if( outletCommentList != null && outletCommentList.size() > 0 ){
			for( OutletComment outletComment : outletCommentList ){
				String outletId = outletComment.getOutletId();
				if( set.add(outletId) ){
					outletIds.add(outletId);
				}
			}
		}
		
		return outletIds;
	}
	
	/**
	 * 检查 一个会员一天内只能针对一门店评论一次
	 * @Title: checkMemberCommentCount
	 * @author lf
	 * @version 1.0
	 * @see: TODO
	 * @param outletComment
	 * 
	 * @return boolean
	 * @throws
	 */
	public boolean checkMemberCommentCount(OutletComment outletComment) throws Exception {
		
		// 得到查询条件
		String memberCode = outletComment.getMemberCode(); // 会员编号
		String outletId = outletComment.getOutletId(); // 门店id
		long createTime = outletComment.getCreateTime(); // 创建时间
		long begTime = sdf1.parse(sdf1.format(new Date(createTime))).getTime();
		long endTime = begTime + (24*60*60*1000-1000);
		DBObject dbQ = new BasicDBObject();
		dbQ.put(MONGO_KEY_OUTLET_ID, outletId);
		dbQ.put(MONGO_KEY_MEMBER_INFO+"."+MONGO_KEY_MEMBER_CODE, memberCode);
		DBObject timeQ = new BasicDBObject();
		timeQ.put(QueryOperators.GTE, begTime);
		timeQ.put(QueryOperators.LTE, endTime);
		dbQ.put(MONGO_KEY_CREATE_TIME, timeQ);
		dbQ.put(MONGO_KEY_COMMENT_TYPE, new BasicDBObject(QueryOperators.NE,String.valueOf(Constants.COMMENT_TYPE_FACETOFACE)));
		
		LogCvt.debug("查询一个会员一天内针对一个门店普通评论的次数 条件:"+JSON.toJSONString(dbQ, true));
		MongoManager manager = new MongoManager();
		List<OutletComment> tResult = (List<OutletComment>)manager.findAll(dbQ, CB_OUTLET_COMMENT, OutletComment.class);
		if( tResult != null && tResult.size() >= 1 ){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 是否存在某会员已经对门店进行了面对面的评论.
	 * 
	 * @param outletComment
	 * @return
	 * @throws Exception
	 */
	public boolean checkFaceToFaceCommentCount(OutletComment outletComment)throws Exception {
		// 得到查询条件
		String memberCode = outletComment.getMemberCode(); // 会员编号
		String outletId = outletComment.getOutletId(); // 门店id
		DBObject dbQ = new BasicDBObject();
		dbQ.put(MONGO_KEY_OUTLET_ID, outletId);
		dbQ.put(MONGO_KEY_MEMBER_INFO + "." + MONGO_KEY_MEMBER_CODE, memberCode);
		dbQ.put(MONGO_KEY_COMMENT_TYPE, String.valueOf(Constants.COMMENT_TYPE_FACETOFACE));
		dbQ.put(MONGO_KEY_ORDER_ID, outletComment.getOrderId());

		LogCvt.debug("查询一个会员针对面对面门店评论的次数 条件:" + JSON.toJSONString(dbQ, true));
		MongoManager manager = new MongoManager();
		List<OutletComment> tResult = (List<OutletComment>) manager.findAll(dbQ, CB_OUTLET_COMMENT, OutletComment.class);
		if (tResult != null && tResult.size() >= 1) {
			return false;
		}
		return true;
	}
}