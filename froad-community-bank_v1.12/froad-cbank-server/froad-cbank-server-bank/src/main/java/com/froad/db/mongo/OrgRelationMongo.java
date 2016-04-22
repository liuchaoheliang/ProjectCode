package com.froad.db.mongo;


import com.froad.db.mongo.bean.OrgRelation;
import com.froad.db.mongo.bean.SubOrgsInfo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.po.Org;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
/**
 * 
 * <p>@Title: OrgRelationMongo.java</p>
 * <p>Description: 描述 </p>  机构商户关系表 Mongo处理类
 * @author ll  
 * @version 1.0
 * @created 2015年3月26日
 */
public class OrgRelationMongo {
	
	
	/**
	 *  保存至机构商户关系表Mongo
	  * @Title: addOrgRelationMongo
	  * @Description: TODO
	  * @author: ll 2015年3月26日
	  * @modify: ll 2015年3月26日
	  * @param  orgRelatin
	  * @param    bool 
	  * @throws
	 */
	public static Boolean addOrgRelationMongo(OrgRelation orgRelation){
		MongoManager manager = new MongoManager(); 
		OrgRelation bean= findByOrgCode(orgRelation.getClientId(), orgRelation.getId());
		int result = -1;
		if(bean == null){
			result = manager.add(orgRelation, MongoTableName.CB_ORG_RELATION);
		}
		return result != -1;
	}
	
	/**
	 * 添加Mongo中cb_org_relation表_id下的list某个元素
	 * @author: ll 2015年3月27日
	 * @modify: ll 2015年3月27日
	 * @param _id Mongo中的id标识
	 * @param subOrgsInfo Mongo存储list下的对象
	 * @return bool
	 */
	public static Boolean addSubOrgInfo(String _id,String clientId,SubOrgsInfo subOrgsInfo) {
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", _id);
		whereObj.put("client_id", clientId);
		
		DBObject pushObj = new BasicDBObject();
		pushObj.put("sub_orgs", JSON.parse(JSonUtil.toJSonString(subOrgsInfo)));
		
		MongoManager manager = new MongoManager();
		int result = manager.update(pushObj, whereObj, MongoTableName.CB_ORG_RELATION, "$push");
		return result != -1;
	}	
	
	/**
	 *  删除机构商户关系表Mongo中的_id
	  * @Title: deleteOrgRelationMongo
	  * @Description: TODO
	  * @author: ll 2015年3月27日
	  * @modify: ll 2015年3月27日
	  * @param  org_code
	  * @param   bool 
	  * @throws
	 */
	public static Boolean deleteOrgRelationMongo(String clientId,String org_code){
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", org_code);
		queryObj.put("client_id", clientId);
		
		MongoManager manager = new MongoManager();
		int result = manager.findAndRemove(queryObj, MongoTableName.CB_ORG_RELATION);
		return result != -1;
	}
	
	/**
	 * 移除Mongo中cb_org_relation表_id下的list某个元素
	 * @author: ll 2015年3月27日
	 * @modify: ll 2015年3月27日
	 * @param _id Mongo中的id标识
	 * @param org_code Mongo存储list下的org_code
	 * @return
	 */
	public static Boolean removeSubOrgInfo(String _id,String clientId,String org_code) {
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", _id);
		whereObj.put("client_id", clientId);
		
		DBObject pullObj = new BasicDBObject();
		pullObj.put("sub_orgs", new BasicDBObject("org_code",org_code));
		
		MongoManager manager = new MongoManager();
		int result = manager.update(pullObj, whereObj, MongoTableName.CB_ORG_RELATION, "$pull");
		return result != -1;
	}	
	
	
	/**
	 *  查询机构商户关系表Mongo
	  * @Title: findByOrgCode
	  * @Description: TODO
	  * @author: ll 2015年3月26日
	  * @modify: ll 2015年3月26日
	  * @param @param orgCode
	  * @param @return   OrgRelation 
	  * @throws
	 */
	public static OrgRelation findByOrgCode(String clientId,String orgCode){
		DBObject dbObject = new BasicDBObject();
		dbObject.put("_id", orgCode);
		dbObject.put("client_id", clientId);
		
		
		MongoManager manager = new MongoManager(); 
		OrgRelation bean = manager.findOne(dbObject, MongoTableName.CB_ORG_RELATION, OrgRelation.class);
		return bean;
	}
	
	
	
	/**
	 * 根据id和clientId和orgCode修改orgName
	 * @author: ll 2015年4月9日
	 * @modify: ll 2015年4月9日
	 * @param _id Mongo中的id标识
	 * @param org_name 修改机构名称
	 * @param org 原机构对象
	 * @return
	 */
	public static Boolean updateSubOrgName(String _id,String org_name,Org org) {
		String clientId=org.getClientId();
		String org_code=org.getOrgCode();
		
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", _id);
		whereObj.put("client_id", clientId);
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("org_code",org_code));
		whereObj.put("sub_orgs", dbo);
		
		
		DBObject setObj = new BasicDBObject();
		setObj.put("sub_orgs.$.org_name", org_name);
		
		
		MongoManager manager = new MongoManager();
		int result = manager.update(setObj, whereObj, MongoTableName.CB_ORG_RELATION, "$set");
		
		
		
		//删掉子集再加子集
//		removeSubOrgInfo(_id, clientId, org_code);
//		
//		
//		//重新设置SubOrgsInfo
//		SubOrgsInfo subOrgsInfo =new SubOrgsInfo();
//		subOrgsInfo.setOrgCode(org_code);
//		subOrgsInfo.setMerchantId(org.getMerchantId());
//		subOrgsInfo.setOrgName(org_name);
//		subOrgsInfo.setOutletId(org.getOutletId());
//		addSubOrgInfo(_id, clientId, subOrgsInfo);
		
		return result!=-1;
		
	}	
	
	
	
}
