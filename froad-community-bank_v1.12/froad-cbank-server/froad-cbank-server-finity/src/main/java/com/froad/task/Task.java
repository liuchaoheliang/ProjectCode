/**
 * Project Name:froad-cbank-server-finity
 * File Name:AutoTask.java
 * Package Name:com.froad.thrift
 * Date:2015年9月24日下午2:19:47
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.ArrayUtil;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.MerchantRoleResourceMongo;
import com.froad.db.mongo.RoleResourceMongo;
import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.bean.MerchantResource;
import com.froad.db.mongo.bean.MerchantRoleResource;
import com.froad.db.mongo.bean.ResourcesInfo;
import com.froad.db.mysql.bean.Page;
import com.froad.db.redis.impl.RedisManager;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantUserLogic;
import com.froad.logic.ResourceLogic;
import com.froad.logic.RoleResourceLogic;
import com.froad.logic.UserResourceLogic;
import com.froad.logic.impl.MerchantUserLogicImpl;
import com.froad.logic.impl.ResourceLogicImpl;
import com.froad.logic.impl.RoleResourceLogicImpl;
import com.froad.logic.impl.UserResourceLogicImpl;
import com.froad.po.MerchantUser;
import com.froad.po.Resource;
import com.froad.po.UserResource;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:AutoTask Reason: TODO ADD REASON. Date: 2015年9月24日 下午2:19:47
 * 
 * @author 刘超 liuchao@f-road.com.cn
 * @version
 * @see
 */

public class Task {
	
	public static String _pix="20151028";
	public static String _pix2="20151028";
 
	/**
	 * runTaskBank:() 银行角色资源关系同步接口
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年9月24日 下午5:37:02
	 * 
	 */
	public static void runTaskBank() {
//		RoleResourceMongo roleResourceMongo = new RoleResourceMongo();
//		RoleResourceLogic roleResourceLogic = new RoleResourceLogicImpl();
//		RedisManager redisManager = new RedisManager();
//
//		String isDone = redisManager.getString(RedisKeyUtil.cbbank_finity_role_resource_do_task()+_pix2);
//		
//		// 同步任务只需执行一次，第一次不存在缓存，则执行，后面则不执行
//		if (StringUtils.isNotBlank(isDone)) {
//			LogCvt.info("银行端角色资源同步任务已经执行过，无需重复执行");
//		}else{
//			List<BankUserResource> list = roleResourceMongo.findRoleResource(new HashMap<String, Object>());
//			int n = 1;
//			LogCvt.info("========银行端角色资源同步开始============");
//			for (BankUserResource temp : list) {
//				List<Long> resourceIds = new ArrayList<Long>();
//				for (ResourcesInfo temp1 : temp.getResources()) {
//					resourceIds.add(temp1.getResourceId());
//				}
//				String[] str = temp.getId().split("_");
//				String clientId = str[0];
//				String roleId = str[1];
//				LogCvt.info("客户端Id:" + clientId + ",角色Id:" + roleId); 
//				LogCvt.info("第" + n + "次同步,roleId:" + roleId + ",所拥有的资源Id:" + JSON.toJSONString(resourceIds));
//				try{
//					roleResourceLogic.updateRoleResourceByBankTask(Long.valueOf(roleId), resourceIds);
//				}catch(Exception e){
//					LogCvt.error("执行第"+n+"次同步出现异常,roleId:"+roleId, e);
//				}
//				n++;
//			}
//			redisManager.set(RedisKeyUtil.cbbank_finity_role_resource_do_task()+_pix2, "1");
//			LogCvt.info("===========银行端角色资源同步结束============");
//		}
	}
	
	
	/**
	 * runTaskMerchant:(商户用户资源同步任务) 
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年10月16日 上午11:30:49
	 * @throws Exception 
	 * @throws FroadServerException 
	 * 
	 */
	public static void runTaskMerchant() throws FroadServerException, Exception {
//		MerchantRoleResourceMongo resourceMongo = new MerchantRoleResourceMongo();
//		RedisManager redisManager = new RedisManager();
//		UserResourceLogic userResourceLogic = new UserResourceLogicImpl();
//		MerchantUserLogic merchantUserLogic = new MerchantUserLogicImpl();
//		int count = 0,userCount=0 ; //统计成功同步条数
//	
//		String isDone = redisManager.getString(RedisKeyUtil.cbbank_finity_user_resource_do_task_merchant()+_pix) ;
//		if(StringUtils.isNotBlank(isDone) ){
//			LogCvt.info("商户用户资源同步任务已经执行过，无需重复执行");
//			return ;
//		}		
//		//添加已执行标志
//        redisManager.set(RedisKeyUtil.cbbank_finity_user_resource_do_task_merchant()+_pix, "1");
//		LogCvt.info("========商户用户资源同步开始============");
//		 
//		//查询有效的商户用户
//		MerchantUser merchantUser = new MerchantUser();
////		merchantUser.setIsDelete(false);
//		
//		Page<MerchantUser> page=new Page<MerchantUser>();
//		page.setPageSize(1000);
//  		List<MerchantUser> list = null;
// 		int i=0;
//		while((list!=null && list.size()>0) || i==0){
//			page.setBegDate(Long.valueOf(page.getPageSize()*i));
// 			list = merchantUserLogic.findMerchantUser(page,merchantUser);
// 		       if(list==null || list.isEmpty()){
//					LogCvt.info("未查询到任何商户用户信息");
//					return ;
//				}else{
//					LogCvt.info("开始同步商户用户资源");	
//					for(MerchantUser user : list ){				
//						//所需条件
//						Long userId = user.getId();
//						Long roletype = user.getMerchantRoleId();
//						String clientId =  user.getClientId();
//						String key=clientId+"_"+roletype.toString();
//						//组合query
//						Map<String, Object> map = new HashMap<String, Object>();
//						map.put("_id", key);
//						MerchantRoleResource resources=resourceMongo.findMerchantRoleResource(map);
//						//未查询到角色资源
//						if(resources == null || resources.getResources() == null ){
//							LogCvt.info("未查询到任何用户资源，查询id:"+key);
//							continue ;
//						}else{
//						//查询到角色相关资源
//							LogCvt.info("查询到用户资源，查询id:"+key+",拥有资源数："+resources.getResources().size());
//							List<UserResource> userResources = new ArrayList<UserResource>();
//							
//							UserResource baseResource = new UserResource();
//							// 1 商户   2 银行  3 boss
//							baseResource.setUserType(1);  
//							baseResource.setUserId(userId);
//							//默认都添加收银台资源
//							baseResource.setResourceId(200000014L);
//							userResources.add(baseResource);
//												
//							//是否拥有口碑管理资源
//							boolean isHave=false;					
//							boolean isHave18=false;
//							boolean isHave19=false;
//							for(MerchantResource resource:resources.getResources()  ){
//								Long id =resource.getResource_id();						
//								//口碑管理(含有商户评价或者门店评价的默认都有口碑管理)
//								if(id==300000005 || id==310000005 || id==300000004 || id==310000004  ){
//									isHave=true;
//								} 
//								
//								Long newResourceId=Command.getNewResourceId(id);
//								if(newResourceId==null){
//									LogCvt.info("未查询到旧资源对应的新ID，查询key:"+id);
//									continue;
//								}else{
//									UserResource userResource = new UserResource();
//									// 1 商户   2 银行  3 boss
//									userResource.setUserType(1);
//									userResource.setUserId(userId);
//									userResource.setResourceId(newResourceId);
//									userResources.add(userResource);
//								}
//								
//								//是否含有一级用户管理菜单
//								if(newResourceId.longValue()==200000018){
//									isHave18=true;
//								}
//								//是否含有二级用户管理菜单
//								if(newResourceId.longValue()==200000019){
//									isHave19=true;
//								}
//		
//							}
//							//如果有口碑管理的资源，则追加口碑管理资源
//							if(isHave){
//								UserResource userResource = new UserResource();
//								// 1 商户   2 银行  3 boss
//								userResource.setUserType(1);
//								userResource.setUserId(userId);
//								//200000015 口碑管理资源Id
//								userResource.setResourceId(200000015L);
//								userResources.add(userResource);
//							}
//							//如果只有一级用户管理菜单，则默认添加二级用户管理
//							if(isHave18 && !isHave19){ 
//								//默认用户管理资源
//								baseResource = new UserResource();
//								// 1 商户   2 银行  3 boss
//								baseResource.setUserType(1);  
//								baseResource.setUserId(userId);
//								//默认都添加收银台资源
//								baseResource.setResourceId(200000019L);
//								userResources.add(baseResource);
//							}
//							
//							boolean flag= userResourceLogic.addUserResourceByBatch(userResources);
//							LogCvt.info("批量添加用户："+userId+"的资源，结果："+flag);	
//							if(flag){
//								userCount++;
//								count+=userResources.size();
//							}
//						}
//					} 
//				}	
//		i++;
//		} 
//		LogCvt.info("结束同步商户用户资源，成功执行"+userCount+"名用户信息，成功添加用户资源条数："+count);
	}
	
	
	public static void main(String[]avgs){
//		try {
//			Task.runTaskMerchant();
//		} catch (FroadServerException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	
	
	
	

}
