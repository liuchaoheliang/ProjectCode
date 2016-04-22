package com.froad.common.mongo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.alibaba.fastjson.JSON;
import com.froad.db.chonggou.entity.MemberInfo;
import com.froad.db.chonggou.entity.OutletComment;
import com.froad.db.chonggou.entity.PointInfo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ModuleID;
import com.froad.logback.LogCvt;
import com.froad.util.SimpleID;
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
	
	private static Sort SORT = new Sort(MONGO_KEY_ID, OrderBy.ASC);
	
	// 机构 - 业务逻辑接口
//	private static OrgCommonLogic orgCommonLogic = new OrgCommonLogicImpl();
	// 门店 - 业务逻辑接口
//	private static OutletLogic outletLogic = new OutletLogicImpl();
	
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
		
		String merchantId = outletComment.getMerchantId();
		try{
//			Map<OrgLevelEnum, String> orgValue = orgCommonLogic.getSuperOrgByMerchantId(merchantId);
//			if( orgValue != null ){
//				outletComment.setForgCode(orgValue.get(OrgLevelEnum.orgLevel_one));
//				outletComment.setSorgCode(orgValue.get(OrgLevelEnum.orgLevel_two));
//				outletComment.setTorgCode(orgValue.get(OrgLevelEnum.orgLevel_three));
//				outletComment.setLorgCode(orgValue.get(OrgLevelEnum.orgLevel_four));
//			}
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
		
		LogCvt.debug("查询一个会员一天内针对一个门店评论的次数 条件:"+JSON.toJSONString(dbQ, true));
		MongoManager manager = new MongoManager();
		List<OutletComment> tResult = (List<OutletComment>)manager.findAll(dbQ, CB_OUTLET_COMMENT, OutletComment.class);
		if( tResult != null && tResult.size() >= 1 ){
			return false;
		}
		
		return true;
	}
}
