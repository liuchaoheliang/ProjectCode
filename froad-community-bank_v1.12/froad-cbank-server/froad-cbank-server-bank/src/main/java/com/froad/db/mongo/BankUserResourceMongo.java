package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.bean.ResourcesInfo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.po.BankResource;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * 
 * @ClassName: BankUserResourceMongo 
 * @Description: 角色资源关联表mongo操作
 * @author: huangyihao
 * @date: 2015年3月26日
 */
public class BankUserResourceMongo {
	
	
	/**
	 * 
	 * @Title: addBankUserResource 
	 * @Description: 添加用户资源
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param clientId
	 * @param roleId
	 * @param bankResources
	 * @return
	 * @throws
	 */
	public static Boolean addBankUserResource(String clientId, Long roleId, List<BankResource> bankResources) {
		MongoManager manager = new MongoManager();
		String _id = getBankUserResourceId(clientId,roleId);
		//添加对象：BankUserResource
		BankUserResource bankUserResource = new BankUserResource();
		bankUserResource.setId(_id);
		bankUserResource.setResources(toResourcesInfos(bankResources));
		int result = manager.add(bankUserResource,MongoTableName.CB_BANK_USER_RESOURCE);
		return result != -1;
	}
	
	/**
	 * 
	 * @Title: deleteBankUserResource 
	 * @Description: 删除用户资源
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param clientId
	 * @param roleId
	 * @return
	 * @throws
	 */
	public static Boolean deleteBankUserResource(String clientId, Long roleId) {
		String _id = getBankUserResourceId(clientId,roleId);
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", _id);
		MongoManager manager = new MongoManager();
		int result = manager.findAndRemove(queryObj, MongoTableName.CB_BANK_USER_RESOURCE);
		return result != -1;
	}
	
	/**
	 * 
	 * @Title: updateBankUserResource 
	 * @Description: 更新用户资源
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param clientId
	 * @param roleId
	 * @param resourcesInfo
	 * @return
	 * @throws
	 */
	public static Boolean updateBankUserResource(String clientId, Long roleId, List<BankResource> bankResources){
		// 先删除
		boolean delResult = deleteBankUserResource(clientId, roleId);
		// 后增加
		boolean addResult = addBankUserResource(clientId, roleId, bankResources);
		return delResult && addResult;
	}

	/**
	 * 
	 * @Title: findByCondition 
	 * @Description: 根据条件查询
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param map 添加
	 * @return
	 * @throws
	 */
	public static List<BankUserResource> findByCondition(Map<String, Object> map) {
		MongoManager manager = new MongoManager();
		DBObject queryObj = new BasicDBObject();
		queryObj.putAll(map);
		List<BankUserResource> list = (List<BankUserResource>)manager.findAll(queryObj, MongoTableName.CB_BANK_USER_RESOURCE, BankUserResource.class);
		return list;
	}
	
	/**
	 * 
	 * @Title: findById 
	 * @Description: 根据客户端ID、角色ID查询
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param clientId
	 * @param roleId
	 * @return
	 * @throws
	 */
	public static BankUserResource findById(String clientId, Long roleId) {
		MongoManager manager = new MongoManager();
		String _id = getBankUserResourceId(clientId,roleId);
		BankUserResource mongoBean = manager.findOneById(_id, MongoTableName.CB_BANK_USER_RESOURCE, BankUserResource.class);
		return mongoBean;
	}
	
	/**
	 * 
	 * @Title: addResource 
	 * @Description: 根据客户端ID、角色ID添加资源
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月25日
	 * @param clientId
	 * @param roleId
	 * @param bankResource
	 * @return
	 * @throws
	 */
	public static Boolean addResource(String clientId, Long roleId, BankResource bankResource) {
		DBObject whereObj = new BasicDBObject();
		String _id = getBankUserResourceId(clientId, roleId);
		whereObj.put("_id", _id);
		DBObject valueObj = new BasicDBObject();
		ResourcesInfo resourcesInfo = toResourcesInfo(bankResource);
		valueObj.put("resources", JSON.parse(resourcesInfo.toString()));
		MongoManager manager = new MongoManager();
		int result = manager.update(valueObj, whereObj, MongoTableName.CB_BANK_USER_RESOURCE, "$push");
		return result != -1;
	}
	
	/**
	 * 
	 * @Title: removeResource 
	 * @Description: 根据客户端ID、角色ID删除资源
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月25日
	 * @param clientId
	 * @param roleId
	 * @param resourceId
	 * @return
	 * @throws
	 */
	public static Boolean removeResource(String clientId, Long roleId, Long resourceId) {
		DBObject whereObj = new BasicDBObject();
		String _id = getBankUserResourceId(clientId,roleId);
		whereObj.put("_id", _id);
		DBObject pullObj = new BasicDBObject();
		pullObj.put("resources", new BasicDBObject("resource_id",resourceId));
		MongoManager manager = new MongoManager();
		int result = manager.update(pullObj, whereObj, MongoTableName.CB_BANK_USER_RESOURCE, "$pull");
		return result != -1;
	}
	
	/**
	 * 
	 * @Title: updateResource 
	 * @Description: 根据客户端ID、角色ID更新资源信息
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月25日
	 * @param clientId
	 * @param roleId
	 * @param bankResource
	 * @return
	 * @throws
	 */
	public static Boolean updateResource(String clientId, Long roleId, BankResource bankResource) {
		// 先删除
		boolean delResult1 = removeResource(clientId, roleId, bankResource.getId());
		// 后添加
		boolean addResult = addResource(clientId, roleId, bankResource);
		return delResult1 && addResult;
	}
	
	/**
	 * 
	 * @Title: getBankUserResourceId 
	 * @Description: 根据客户端ID、角色ID生成id
	 * @author: froad-huangyihao 2015年3月25日
	 * @modify: froad-huangyihao 2015年3月25日
	 * @param clientId 客户端ID
	 * @param roleId	角色ID
	 * @return
	 * @throws
	 */
	private static String getBankUserResourceId(String clientId, Long roleId){
		return clientId+"_"+roleId;
	}
	
	/**
	 * 
	 * @Title: toResourcesInfo 
	 * @Description: 把bankResource转成ResourcesInfo
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param bankResource
	 * @return
	 * @throws
	 */
	private static ResourcesInfo toResourcesInfo(BankResource bankResource){
		ResourcesInfo info = new ResourcesInfo();
		info.setResourceId(bankResource.getId());
		info.setResourceName(ObjectUtils.toString(bankResource.getResourceName()));
		info.setResourceType(BooleanUtils.toString(bankResource.getResourceType(), "1", "0", ""));
		info.setTreePath(ObjectUtils.toString(bankResource.getTreePath()));
		info.setResourceIcon(ObjectUtils.toString(bankResource.getResourceIcon()));
		info.setResourceUrl(ObjectUtils.toString(bankResource.getResourceUrl()));
		info.setParentResourceId(bankResource.getParentResourceId());
		info.setApi(ObjectUtils.toString(bankResource.getApi()));
		info.setOrderValue(bankResource.getOrderValue());
		
		return info;
	}
	
	/**
	 * 
	 * @Title: toResourcesInfos 
	 * @Description: 把bankResource集合转成ResourcesInfo集合
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param bankResources
	 * @return
	 * @throws
	 */
	private static List<ResourcesInfo> toResourcesInfos(List<BankResource> bankResources){
		List<ResourcesInfo> infos = new ArrayList<ResourcesInfo>();
		for(BankResource b : bankResources){
			ResourcesInfo info = toResourcesInfo(b);
			infos.add(info);
		}
		return infos;
	}
}
