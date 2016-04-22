/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */

/**  
 * @Title: MerchantRoleResourceMongo.java
 * @Package com.froad.db.mongo
 * @Description: TODO
 * @author vania
 * @date 2015年5月23日
 */

package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.po.mongo.MerchantRoleResource;
import com.froad.po.mongo.Resource;
import com.froad.util.JSonUtil;
import com.froad.util.MongoTableName;
import com.froad.util.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * <p>
 * Title: MerchantRoleResourceMongo.java
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * 
 * @author vania
 * @version 1.0
 * @created 2015年5月23日 上午10:28:32
 */

public class MerchantRoleResourceMongo {
	private MongoManager manager = new MongoManager();

	/**
	 * 添加角色资源
	 * 
	 * @Title: addMerchantRoleResource
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantRoleResource
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public int addMerchantRoleResource(MerchantRoleResource merchantRoleResource) {
		return manager.add(merchantRoleResource, MongoTableName.CB_MERCHANT_ROLE_RESOURCE);
	}

	/**
	 * 角色添加资源信息
	 * 
	 * @Title: addResources
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param roleId
	 * @param resource
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public int addResources(String clientId, long roleId, Resource resource) {
		int result = -1;
		if (null == resource)
			return result;

		String id = clientId + "_" + roleId;

		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", id);

		DBObject pushObj = new BasicDBObject();

		pushObj.put("resources", com.mongodb.util.JSON.parse(JSonUtil.toJSonString(resource)));

		result = manager.update(pushObj, whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, "$push");
		return result;

	}

	/**
	 * 逻辑删除角色下面的资源
	 * 
	 * @Title: deleteResources
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param roleId
	 * @param resourceId
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public int deleteResources(String clientId, long roleId, long resourceId) {

		String id = clientId + "_" + roleId;

		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", id);
		DBObject dbo = new BasicDBObject();
		dbo.put("$elemMatch", new BasicDBObject("resource_id", resourceId));
		whereObj.put("resources", dbo);

		DBObject valueObj = new BasicDBObject();
		valueObj.put("resources.$.is_enable", false);

		return manager.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, "$set");

	}

	/**
	 * 物理删除角色下的资源
	 * 
	 * @Title: removeResources
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param roleId
	 * @param resourceId
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public int removeResources(String clientId, long roleId, long resourceId) {

		String id = clientId + "_" + roleId;

		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", id);

		DBObject valueObj = new BasicDBObject();
		DBObject elemMatch = new BasicDBObject();
		elemMatch.put("$elemMatch", new BasicDBObject("resource_id", resourceId));
		valueObj.put("resources", elemMatch);

		return manager.update(valueObj, whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, "$pull");

	}

	
	
	/**
	 * 物理删除角色资源
	 * 
	 * @Title: removeMerchantRoleResource
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param roleId
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public int removeMerchantRoleResource(String clientId, long roleId) {
		String _id = clientId + "_" + roleId;

		return this.removeMerchantRoleResource(_id);
	}
	
	/**
	 * 物理删除角色资源
	 * @Title: removeMerchantRoleResource 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param _id
	 * @return
	 * @return int    返回类型 
	 * @throws
	 */
	public int removeMerchantRoleResource(String _id) {
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", _id);

		return manager.remove(whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE);
	}

	/**
	 * 覆盖修改角色资源
	 * 
	 * @Title: updateMerchantRoleResource
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param roleId
	 * @param merchantRoleResource
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	public int updateMerchantRoleResource(String clientId, long roleId, MerchantRoleResource merchantRoleResource) {
		String _id = clientId + "_" + roleId;


		return updateMerchantRoleResource(_id, merchantRoleResource);
	}
	
	/**
	 * 覆盖修改角色资源
	 * @Title: updateMerchantRoleResource 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param _id
	 * @param merchantRoleResource
	 * @return
	 * @return int    返回类型 
	 * @throws
	 */
	public int updateMerchantRoleResource(String _id, MerchantRoleResource merchantRoleResource) {
		
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", _id);
		
		return manager.update(com.mongodb.util.JSON.parse(JSonUtil.toJSonString(merchantRoleResource)), whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, null);
	}
	
	/**
	 * 根据clientid查询角色资源
	 * @Title: findMerchantRoleResourceListByClientId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @return
	 * @return List<MerchantRoleResource>    返回类型 
	 * @throws
	 */
	public List<MerchantRoleResource> findMerchantRoleResourceListByClientId(String clientId) {
		DBObject whereObj = new BasicDBObject();
		clientId = clientId + "_";
		whereObj.put("_id", new BasicDBObject("$regex", Pattern.compile("^" + clientId + ".*$", Pattern.CASE_INSENSITIVE).toString()));
		return (List<MerchantRoleResource>) manager.findAll(whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, MerchantRoleResource.class);
	}

	/**
	 * 查询全部角色资源
	 * @Title: findMerchantRoleResource 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return
	 * @return List<MerchantRoleResource>    返回类型 
	 * @throws
	 */
	public List<MerchantRoleResource> findMerchantRoleResource() {
		DBObject whereObj = new BasicDBObject();
		return (List<MerchantRoleResource>)manager.findAll(whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, MerchantRoleResource.class);
	}
	
	/**
	 * 查询单个角色资源
	 * 
	 * @Title: findMerchantRoleResource
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param roleId
	 * @return
	 * @return MerchantRoleResource 返回类型
	 * @throws
	 */
	public MerchantRoleResource findMerchantRoleResource(String clientId, long roleId) {
		String id = clientId + "_" + roleId;
		return findMerchantRoleResource(id);
	}
	
	/**
	 * 查询单个角色资源
	 * @Title: findMerchantRoleResource 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param _id
	 * @return
	 * @return MerchantRoleResource    返回类型 
	 * @throws
	 */
	public MerchantRoleResource findMerchantRoleResource(String _id) {
		DBObject whereObj = new BasicDBObject();
		whereObj.put("_id", _id);
//		Sort sort = new Sort("resources.order_value", OrderBy.ASC);
//		List<MerchantRoleResource> list = (List<MerchantRoleResource>) manager.findAll(whereObj, sort, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, MerchantRoleResource.class);
//		if(CollectionUtils.isNotEmpty(list)){
//			return list.get(0);
//		}
//		return null;
		return manager.findOne(whereObj, MongoTableName.CB_MERCHANT_ROLE_RESOURCE, MerchantRoleResource.class);
		
	}

	public static void main(String[] args) {
		for (int i = 0, j = 0; i < 10 || j < 5; i++, j++) {
		}
		PropertiesUtil.load();
		// anhui_100000002

		Resource resource0 = new Resource();
		resource0.setResource_id(12222);
		resource0.setResource_name("计费管理");
		resource0.setApi("/aa/bb/cc");
		resource0.setResource_url("www.baidu.com");
		resource0.setOrder_value(3);
		
		Resource resource1 = new Resource();
		resource1.setResource_id(211111);
		resource1.setResource_name("报表管理");
		resource1.setApi("/11/11/22");
		resource1.setResource_url("www.google.com");
		resource1.setOrder_value(2);

		List<Resource> resources = new ArrayList<Resource>();
		resources.add(resource0);
		resources.add(resource1);

		MerchantRoleResource mr = new MerchantRoleResource();
		mr.set_id("anhui_88888888");
		mr.setResources(resources);

		MerchantRoleResourceMongo mongo = new MerchantRoleResourceMongo();
		System.out.println(JSON.toJSONString(mongo.findMerchantRoleResource("anhui", 88888888), true));
		 System.out.println(mongo.addMerchantRoleResource(mr));
		// System.out.println(mongo.removeMerchantRoleResource("anhui",  88888888));
//		System.out.println(mongo.updateMerchantRoleResource("anhui", 88888888, mr));
//		System.out.println(mongo.removeResources("anhui", 88888888, 12222));

		System.out.println(JSON.toJSONString(mongo.findMerchantRoleResource("anhui", 88888888), true));
		// System.out.println(JSON.toJSONString(mongo.findMerchantRoleResource("anhui",
		// 100000002), true));
	}
}
