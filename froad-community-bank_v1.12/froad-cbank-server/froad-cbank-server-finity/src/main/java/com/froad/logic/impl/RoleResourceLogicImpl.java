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
 * 
 * @Title: RoleResourceLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ResourceMapper;
import com.froad.db.mysql.mapper.RoleResourceMapper;
import com.froad.enums.Platform;
import com.froad.logback.LogCvt;
import com.froad.logic.RoleResourceLogic;
import com.froad.po.Resource;
import com.froad.po.RoleResource;

/**
 * 
 * <p>
 * 
 * @Title: RoleResourceLogic.java
 *         </p>
 *         <p>
 *         Description: 描述
 *         </p>
 * 
 * @author f-road
 * @version 1.0
 * @created 2015年3月17日
 */
public class RoleResourceLogicImpl implements RoleResourceLogic {
	
	private static String newIdss="";

	/**
	 * 增加 RoleResource
	 * 
	 * @param roleResource
	 * @return Long 主键ID
	 */
	@Override
	public Long addRoleResource(RoleResource roleResource) {

		Long result = -1L;
		SqlSession sqlSession = null;
		RoleResourceMapper roleResourceMapper = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);

			if (roleResourceMapper.addRoleResource(roleResource) > -1) { // 添加成功
				result = roleResource.getRoleId();
				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				sqlSession.commit(true);

			} else { // 添加失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = -1L;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("添加RoleResource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 删除 RoleResource
	 * 
	 * @param roleResource
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean deleteRoleResource(Long roleId) {

		Boolean result = false;
		SqlSession sqlSession = null;
		RoleResourceMapper roleResourceMapper = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);

			RoleResource roleResource = new RoleResource();
			roleResource.setRoleId(roleId);
			result = roleResourceMapper.deleteRoleResource(roleResource);
			if (result) { // 删除成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				sqlSession.commit(true);

				result = true;

			} else { // 删除失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("删除RoleResource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 修改 RoleResource
	 * 
	 * @param roleResource
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateRoleResource(Long roleId, List<Long> resourceIds) {

		Boolean result = false;
		SqlSession sqlSession = null;
		RoleResourceMapper roleResourceMapper = null;
		RoleResource roleResource = null;
		ResourceMapper rm = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			rm = sqlSession.getMapper(ResourceMapper.class);
			// 对于角色资源树的修改处理逻辑： 先清除之前的资源关系，在新建现有传入资源关系
			roleResource = new RoleResource();
			roleResource.setRoleId(roleId);
			// 清除RoleId的资源关系
			roleResourceMapper.deleteRoleResource(roleResource);

			 
 
			List<RoleResource> list = new ArrayList<RoleResource>();
			for (Long resourceId : resourceIds) {  
				roleResource = new RoleResource();
				roleResource.setPlatform(Platform.bank.getType());
				roleResource.setRoleId(roleId);
				roleResource.setResourceId(resourceId);
				list.add(roleResource);
 			}
			result = roleResourceMapper.addRoleResourceByBatch(list);
			LogCvt.info("新建RoleId的资源关系结果为:" + result);

			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				sqlSession.commit(true);

				result = true;

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("修改RoleResource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}
	
	
	/**
	 * 修改 BossRoleResource
	 * 
	 * @param roleResource
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateBossRoleResource(Long roleId,String platform, List<Long> resourceIds) {

		Boolean result = false;
		SqlSession sqlSession = null;
		RoleResourceMapper roleResourceMapper = null;
		RoleResource roleResource = null;
		ResourceMapper rm = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			rm = sqlSession.getMapper(ResourceMapper.class);
			// 对于角色资源树的修改处理逻辑： 先清除之前的资源关系，在新建现有传入资源关系
			roleResource = new RoleResource();
			roleResource.setRoleId(roleId);
			roleResource.setPlatform(platform);
			// 清除RoleId的资源关系
			roleResourceMapper.deleteRoleResource(roleResource);

			 
 
			List<RoleResource> list = new ArrayList<RoleResource>();
			for (Long resourceId : resourceIds) {  
				roleResource = new RoleResource();
				roleResource.setPlatform(platform);
				roleResource.setRoleId(roleId);
				roleResource.setResourceId(resourceId);
				list.add(roleResource);
 			}
			result = roleResourceMapper.addRoleResourceByBatch(list);
			LogCvt.info("新建RoleId的资源关系结果为:" + result);

			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				sqlSession.commit(true);

				result = true;

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("修改RoleResource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}
	
	
	
	/**
	 * 导入银行端 角色资源关系TASK
	 * 修改 RoleResource
	 * 
	 * @param roleResource
	 * @return Boolean 是否成功
	 */
	@Override
	public Boolean updateRoleResourceByBankTask(Long roleId, List<Long> resourceIds) {

		Boolean result = false;
		SqlSession sqlSession = null;
		RoleResourceMapper roleResourceMapper = null;
		RoleResource roleResource = null;
		ResourceMapper rm = null;
		try {
			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			rm = sqlSession.getMapper(ResourceMapper.class);
			// 对于角色资源树的修改处理逻辑： 先清除之前的资源关系，在新建现有传入资源关系
			roleResource = new RoleResource();
			roleResource.setRoleId(roleId);
			// 清除RoleId的资源关系
			roleResourceMapper.deleteRoleResource(roleResource);
			List<RoleResource> list = new ArrayList<RoleResource>();
			if(roleId==100000000 || roleId==100000001){//如果是安徽法人行社管理员 、超级管理员admin
				Resource rtemp = new Resource(); 
				rtemp.setTreePath("100000000");
				List<Resource> rl = rm.findResource(rtemp);//获取所有资源
				for(int i=0;i<rl.size();i++){
					if(rl.get(i).getTreePath().indexOf("100000217")>0 ||  rl.get(i).getTreePath().equals("100000000") //安徽没有”银行用户管理“权限
							||rl.get(i).getTreePath().indexOf("100000223")>0 //老  操作员管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000224")>0 //老  操作员列表 -需要清除
							||rl.get(i).getTreePath().indexOf("100000208")>0 //老  角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000209")>0 //老  角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000242")>0 //老  机构管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000243")>0 //老  机构管理-需要清除
							){//安徽”操作员管理“权限合并到组织结构管理
//					   rl.remove(rl.get(i));	
					   System.out.println("--->"+rl.get(i).getTreePath()+"--"+rl.get(i).getResourceName());
					}else{
						roleResource = new RoleResource();
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(rl.get(i).getId());
						list.add(roleResource);
					}
				}
			}else if(roleId==100000260){//如果是重庆管理员 测试重庆超级管理员ID=100000275
				Resource rtemp = new Resource(); 
				rtemp.setTreePath("100000000");
				List<Resource> rl = rm.findResource(rtemp);//获取所有资源
				for(int i=0;i<rl.size();i++){ 
					if(     rl.get(i).getTreePath().equals("100000000") //"银行顶节点不需要"
							||  rl.get(i).getTreePath().indexOf("100000019")>0 //"待审核名优特惠"
							||rl.get(i).getTreePath().indexOf("100000024")>0 //"待审核预售"
							||rl.get(i).getTreePath().indexOf("100000014")>0 //"待审核线下礼品"
							||rl.get(i).getTreePath().indexOf("100000029")>0 //待审核秒杀
//							||rl.get(i).getTreePath().indexOf("100000038")>0 //审核箱
//							||rl.get(i).getTreePath().indexOf("100000039")>0 //审核监控
//							||rl.get(i).getTreePath().indexOf("100000043")>0 //综合查询
							||rl.get(i).getTreePath().indexOf("100000047")>0 //"积分兑换"
							||rl.get(i).getTreePath().indexOf("100000048")>0 //"积分礼品管理"
							||rl.get(i).getTreePath().indexOf("100000056")>0 //"线下积分兑换"
							||rl.get(i).getTreePath().indexOf("100000069")>0 //"预售订单列表"
							||rl.get(i).getTreePath().indexOf("100000079")>0 //"名优特惠订单列表"
							||rl.get(i).getTreePath().indexOf("100000084")>0 //"积分兑换订单列表"
							||rl.get(i).getTreePath().indexOf("100000122")>0 //"精品预售管理"
							||rl.get(i).getTreePath().indexOf("100000123")>0 //预售商品列表
							||rl.get(i).getTreePath().indexOf("100000137")>0 //提货查询
							||rl.get(i).getTreePath().indexOf("100000140")>0 //提货码验证
							||rl.get(i).getTreePath().indexOf("100000152")>0 //名优特惠
							||rl.get(i).getTreePath().indexOf("100000153")>0 //名优特惠商品管理 
							||rl.get(i).getTreePath().indexOf("100000223")>0 //操作员管理
							||rl.get(i).getTreePath().indexOf("100000224")>0 //操作员列表
							||rl.get(i).getTreePath().indexOf("100000236")>0 //法人行社管理员
							||rl.get(i).getTreePath().indexOf("100000237")>0 //法人行社管理员
							||rl.get(i).getTreePath().indexOf("100000253")>0 //安全中心
							||rl.get(i).getTreePath().indexOf("100000254")>0 //修改密码  
							||rl.get(i).getTreePath().indexOf("100000167")>0 //秒杀管理
							||rl.get(i).getTreePath().indexOf("100000168")>0 //秒杀商品列表
							||rl.get(i).getTreePath().indexOf("100000195")>0 //社区银行用户统计  
							
							||rl.get(i).getTreePath().indexOf("100000208")>0 //老角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000209")>0 //老角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000242")>0 //老  机构管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000243")>0 //老  机构管理-需要清除
							){ 
					   System.out.println("--->"+rl.get(i).getTreePath()+"--"+rl.get(i).getResourceName());
					}else{
						roleResource = new RoleResource();
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(rl.get(i).getId());
						list.add(roleResource);
					}
				}
			} else if(roleId==100000267){//如果是吉林管理员，测试超级管理员ID=100000336
				Resource rtemp = new Resource(); 
				rtemp.setTreePath("100000000");
				List<Resource> rl = rm.findResource(rtemp);//获取所有资源
				for(int i=0;i<rl.size();i++){ 
					if(     rl.get(i).getTreePath().equals("100000000") //"银行顶节点不需要"
							||  rl.get(i).getTreePath().indexOf("100000019")>0 //"待审核名优特惠"
							||rl.get(i).getTreePath().indexOf("100000024")>0 //"待审核预售"
							||rl.get(i).getTreePath().indexOf("100000014")>0 //"待审核线下礼品"
							||rl.get(i).getTreePath().indexOf("100000029")>0 //待审核秒杀
//							||rl.get(i).getTreePath().indexOf("100000038")>0 //审核箱
//							||rl.get(i).getTreePath().indexOf("100000039")>0 //审核监控
//							||rl.get(i).getTreePath().indexOf("100000043")>0 //综合查询
							||rl.get(i).getTreePath().indexOf("100000047")>0 //"积分兑换"
							||rl.get(i).getTreePath().indexOf("100000048")>0 //"积分礼品管理"
							||rl.get(i).getTreePath().indexOf("100000056")>0 //"线下积分兑换"
							||rl.get(i).getTreePath().indexOf("100000069")>0 //"预售订单列表"
							||rl.get(i).getTreePath().indexOf("100000079")>0 //"名优特惠订单列表"
							||rl.get(i).getTreePath().indexOf("100000084")>0 //"积分兑换订单列表"
							||rl.get(i).getTreePath().indexOf("100000122")>0 //"精品预售管理"
							||rl.get(i).getTreePath().indexOf("100000123")>0 //预售商品列表
							||rl.get(i).getTreePath().indexOf("100000137")>0 //提货查询
							||rl.get(i).getTreePath().indexOf("100000140")>0 //提货码验证
							||rl.get(i).getTreePath().indexOf("100000152")>0 //名优特惠
							||rl.get(i).getTreePath().indexOf("100000153")>0 //名优特惠商品管理 
							||rl.get(i).getTreePath().indexOf("100000223")>0 //操作员管理
							||rl.get(i).getTreePath().indexOf("100000224")>0 //操作员列表
							||rl.get(i).getTreePath().indexOf("100000236")>0 //法人行社管理员
							||rl.get(i).getTreePath().indexOf("100000237")>0 //法人行社管理员
//							||rl.get(i).getTreePath().indexOf("100000253")>0 //安全中心
//							||rl.get(i).getTreePath().indexOf("100000254")>0 //修改密码  
							||rl.get(i).getTreePath().indexOf("100000167")>0 //秒杀管理
							||rl.get(i).getTreePath().indexOf("100000168")>0 //秒杀商品列表
							||rl.get(i).getTreePath().indexOf("100000186")>0 //社区银行统计报表
							
							||rl.get(i).getTreePath().indexOf("100000208")>0 //老角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000209")>0 //老角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000242")>0 //老  机构管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000243")>0 //老  机构管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000217")>0 //银行用户管理
							){ 
					   System.out.println("--->"+rl.get(i).getTreePath()+"--"+rl.get(i).getResourceName());
					}else{
						roleResource = new RoleResource();
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(rl.get(i).getId());
						list.add(roleResource);
					}
				}
			}else if(roleId==100000275){//如果是台州管理员，测试环境ID=100000337
				Resource rtemp = new Resource(); 
				rtemp.setTreePath("100000000");
				List<Resource> rl = rm.findResource(rtemp);//获取所有资源
				for(int i=0;i<rl.size();i++){ 
					if(     rl.get(i).getTreePath().equals("100000000") //"银行顶节点不需要"
							||  rl.get(i).getTreePath().indexOf("100000019")>0 //"待审核名优特惠"
							||rl.get(i).getTreePath().indexOf("100000024")>0 //"待审核预售"
							||rl.get(i).getTreePath().indexOf("100000014")>0 //"待审核线下礼品"
							||rl.get(i).getTreePath().indexOf("100000029")>0 //待审核秒杀
//							||rl.get(i).getTreePath().indexOf("100000038")>0 //审核箱
//							||rl.get(i).getTreePath().indexOf("100000039")>0 //审核监控
//							||rl.get(i).getTreePath().indexOf("100000043")>0 //综合查询
							||rl.get(i).getTreePath().indexOf("100000047")>0 //"积分兑换"
							||rl.get(i).getTreePath().indexOf("100000048")>0 //"积分礼品管理"
							||rl.get(i).getTreePath().indexOf("100000056")>0 //"线下积分兑换"
							||rl.get(i).getTreePath().indexOf("100000069")>0 //"预售订单列表"
							||rl.get(i).getTreePath().indexOf("100000079")>0 //"名优特惠订单列表"
							||rl.get(i).getTreePath().indexOf("100000084")>0 //"积分兑换订单列表"
							||rl.get(i).getTreePath().indexOf("100000122")>0 //"精品预售管理"
							||rl.get(i).getTreePath().indexOf("100000123")>0 //预售商品列表
							||rl.get(i).getTreePath().indexOf("100000137")>0 //提货查询
							||rl.get(i).getTreePath().indexOf("100000140")>0 //提货码验证
							||rl.get(i).getTreePath().indexOf("100000152")>0 //名优特惠
							||rl.get(i).getTreePath().indexOf("100000153")>0 //名优特惠商品管理 
							||rl.get(i).getTreePath().indexOf("100000223")>0 //操作员管理
							||rl.get(i).getTreePath().indexOf("100000224")>0 //操作员列表
							||rl.get(i).getTreePath().indexOf("100000236")>0 //法人行社管理员
							||rl.get(i).getTreePath().indexOf("100000237")>0 //法人行社管理员
//							||rl.get(i).getTreePath().indexOf("100000253")>0 //安全中心
//							||rl.get(i).getTreePath().indexOf("100000254")>0 //修改密码  
							||rl.get(i).getTreePath().indexOf("100000167")>0 //秒杀管理
							||rl.get(i).getTreePath().indexOf("100000168")>0 //秒杀商品列表
							||rl.get(i).getTreePath().indexOf("100000186")>0 //社区银行统计报表 
							
							||rl.get(i).getTreePath().indexOf("100000208")>0 //老角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000209")>0 //老角色菜单信息-需要清除
							||rl.get(i).getTreePath().indexOf("100000242")>0 //老  机构管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000243")>0 //老  机构管理-需要清除
							||rl.get(i).getTreePath().indexOf("100000217")>0 //银行用户管理
							){ 
					   System.out.println("--->"+rl.get(i).getTreePath()+"--"+rl.get(i).getResourceName());
					}else{
						roleResource = new RoleResource();
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(rl.get(i).getId());
						list.add(roleResource);
					}
				}
			}else { 
				Map<String, String> map = new HashMap<String, String>();// 已有数据和新数据关系兑换（"安徽or重庆资源ID","新资源ID"）
				map.put("100000000", "100000001"); //任务中心,anhui
				map.put("100001000", "100000001"); //chongqing
				map.put("100002000", "100000001"); //taizhou
				map.put("100003000", "100000001"); //jilin
				map.put("100000001", "100000002"); //每日工作提醒
				map.put("100001001", "100000002");
				map.put("100002001", "100000002");
				map.put("100003001", "100000002");
				map.put("100000002", "100000009"); //待审核团购
				map.put("100001002", "100000009");
				map.put("100002002", "100000009");
				map.put("100003002", "100000009");
				map.put("100000003", "100000019"); //待审核名优特惠
				map.put("100000004", "100000004"); //待审核商户
				map.put("100001004", "100000004");
				map.put("100002004", "100000004");
				map.put("100003004", "100000004");
				map.put("100000005", "100000024"); //待审核预售
				map.put("100000006", "100000014"); //待审核线下礼品
				map.put("100000007", "100000047"); //积分兑换
				map.put("100000008", "100000048"); //积分礼品管理
				map.put("100000009", "100000056"); //线下积分兑换
				map.put("100000010", "100000063"); //订单管理
				map.put("100001010", "100000063");
				map.put("100002010", "100000063");
				map.put("100003010", "100000063");
				map.put("100000011", "100000064"); //团购订单列表
				map.put("100001011", "100000064");
				map.put("100002011", "100000064");
				map.put("100003011", "100000064");
				map.put("100000012", "100000069"); //预售订单列表
				map.put("100000013", "100000074"); //面对面订单列表
				map.put("100001013", "100000074"); 
				map.put("100002013", "100000074"); 
				map.put("100003013", "100000074"); 
				map.put("100000014", "100000079"); //名优特惠订单列表
				map.put("100000015", "100000084"); //积分兑换订单列表
				map.put("100000016", "100000097"); //商户管理
				map.put("100001016", "100000097");
				map.put("100002016", "100000097");
				map.put("100003016", "100000097");
				map.put("100000017", "100000098"); //商户列表
				map.put("100001017", "100000098");
				map.put("100002017", "100000098");
				map.put("100003017", "100000098");
				map.put("100000018", "100000113"); //评价管理
				map.put("100001018", "100000113");
				map.put("100002018", "100000113");
				map.put("100003018", "100000113");
				map.put("100000019", "100000114"); //商户评价列表
				map.put("100001019", "100000114");
				map.put("100002019", "100000114");
				map.put("100003019", "100000114");
				map.put("100000020", "100000118"); //商品评价列表
				map.put("100001020", "100000118");
				map.put("100002020", "100000118");
				map.put("100003020", "100000118");
				map.put("100000022", "100000122"); //精品预售管理
				map.put("100000023", "100000123"); //预售商品列表
				map.put("100000024", "100000137"); //提货查询
				map.put("100000025", "100000140"); //提货码验证
				map.put("100000026", "100000144"); //团购管理
				map.put("100001026", "100000144");
				map.put("100002026", "100000144");
				map.put("100003026", "100000144");
				map.put("100001027", "100000145"); //团购列表
				map.put("100000027", "100000145");
				map.put("100002027", "100000145");
				map.put("100003027", "100000145");
				map.put("100000028", "100000152"); //名优特惠
				map.put("100000029", "100000153"); //名优特惠商品管理
				
				/***老角色菜单信息***/
				/*map.put("100000030", "100000208");
				map.put("100001030", "100000208");
				map.put("100002030", "100000208");
				map.put("100003030", "100000208");
				map.put("100000031", "100000209");
				map.put("100001031", "100000209");
				map.put("100002031", "100000209");
				map.put("100003031", "100000209");*/
				/**新角色菜单--修改新旧角色对应关系**/
				map.put("100000030", "100000270"); //角色管理
				map.put("100001030", "100000270");
				map.put("100002030", "100000270");
				map.put("100003030", "100000270");
				map.put("100000031", "100000271");
				map.put("100001031", "100000271");
				map.put("100002031", "100000271");
				map.put("100003031", "100000271");
				
				/**老操作员管理***/
				/*map.put("100000032", "100000223");
				map.put("100002032", "100000223"); 
				map.put("100003032", "100000223");
				map.put("100000033", "100000224");
				map.put("100002033", "100000224");
				map.put("100003033", "100000224");*/
				
				map.put("100000034", "100000236");//法人行社管理员
				map.put("100000035", "100000237");//法人行社管理员
				
				/***老机构管理菜单信息***/
				/*map.put("100000036", "100000242");
				map.put("100001036", "100000242");
				map.put("100002036", "100000242");
				map.put("100003036", "100000242");
				map.put("100000037", "100000243");
				map.put("100001037", "100000243");
				map.put("100002037", "100000243");
				map.put("100003037", "100000243");*/
				/***新机构管理菜单信息***/
				map.put("100000036", "100000256");//机构管理-组织机构管理
				map.put("100001036", "100000256");
				map.put("100002036", "100000256");
				map.put("100003036", "100000256");
				map.put("100000037", "100000257");//组织机构管理-机构列表
				map.put("100001037", "100000257");
				map.put("100002037", "100000257");
				map.put("100003037", "100000257");
				
				map.put("100000038", "100000250"); //操作日志
				map.put("100001038", "100000250");
				map.put("100002038", "100000250");
				map.put("100003038", "100000250");
				map.put("100000039", "100000251");//操作日志
				map.put("100001039", "100000251");
				map.put("100002039", "100000251");
				map.put("100003039", "100000251");
				map.put("100000040", "100000253");//安全中心
				map.put("100002040", "100000253");
				map.put("100003040", "100000253");
				map.put("100000041", "100000254");//修改密码
				map.put("100002041", "100000254");
				map.put("100003041", "100000254");
				map.put("100000042", "100000029");//待审核秒杀
				map.put("100000043", "100000167");//秒杀管理
				map.put("100000044", "100000168");//秒杀商品列表
				map.put("100001045", "100000217");//银行用户管理
				map.put("100001046", "100000218");//银行用户列表
				map.put("100000047", "100000186");//统计报表
				map.put("100001047", "100000186");
				map.put("100002047", "100000186");
				map.put("100000048", "100000187");//社区银行商户信息统计
				map.put("100001048", "100000187");
				map.put("100002048", "100000187");
				map.put("100000049", "100000192");//社区银行商户签约人统计
				map.put("100001049", "100000192");
				map.put("100002049", "100000192");
				map.put("100000050", "100000195");//社区银行用户统计
				map.put("100000051", "100000200");//社区银行业务销售统计
				map.put("100001051", "100000200");
				map.put("100002051", "100000200");
				map.put("100000052", "100000205");//社区银行商品统计
				map.put("100001052", "100000205");
				map.put("100002052", "100000205");
				map.put("100000053", "100000038");//审核箱
				map.put("100000054", "100000039");//审核监控
				map.put("100000055", "100000043");//综合查询
				map.put("100000056", "100000089");//结算查询
				map.put("100001056", "100000089");
				map.put("100002056", "100000089");
				map.put("100003056", "100000089");
				map.put("100000057", "100000090");//结算查询
				map.put("100001057", "100000090");
				map.put("100002057", "100000090");
				map.put("100003057", "100000090");

				ArrayList<Long> al = new ArrayList<Long>();// 一级菜单-新资源
				al.add(100000001l);
				al.add(100000038l);
				al.add(100000047l);
				al.add(100000063l);
				al.add(100000089l); 
				al.add(100000097l);
				al.add(100000113l);
				al.add(100000122l);
				al.add(100000144l);
				al.add(100000152l);
				al.add(100000167l);
				al.add(100000186l);
				al.add(100000217l);
				al.add(100000223l);
				al.add(100000236l);
				/***老机构管理一级菜单信息***/
//				al.add(100000242l);
				al.add(100000250l);
				al.add(100000253l);
				/***组织机构管理 一级菜单信息***/
				al.add(100000256l);
				/***新角色一级菜单信息***/
				al.add(100000270l); 
				/***老角色一级菜单信息***/
//				al.add(100000208l); 
				
				for (Long resourceId : resourceIds) {// 资源整合，如果是一级则直接加入，如果是二级菜单需要查询相关资源数据
					if (map.get(resourceId + "") == null) {
						continue;
					}
					Long newId = Long.valueOf(map.get(resourceId + ""));
					if(newIdss.indexOf(newId.toString())>=0){
						System.out.println(newIdss);
					}
					newIdss=newId+",";
					
					if (al.contains(newId)) {// 一级菜单
						roleResource = new RoleResource();
						roleResource.setRoleId(roleId);
						roleResource.setResourceId(newId);
						list.add(roleResource);
					} else {// 二级菜单以及旗下资源
						Resource rtemp = new Resource();
						rtemp.setTreePath(newId + "");
//						System.out.println("-------->"+newId);
						List<Resource> rl = rm.findResource(rtemp);
						if (rl != null && rl.size() > 0) {
							for (Resource r : rl) { 
								boolean flag=true;
								roleResource = new RoleResource();
								roleResource.setRoleId(roleId);
								roleResource.setResourceId(r.getId());  
							    for(RoleResource rr:list){
							    	if(rr.getResourceId().intValue()==r.getId().intValue() && rr.getRoleId().intValue()==roleId.intValue()){
							    		flag=false;
							    	}
							    }
							    if(flag){
							    	list.add(roleResource);
							    	System.out.println("===" + r.getId() + "==" + roleId);
							    }								
							}
						}
					}
				}
			}
			result = roleResourceMapper.addRoleResourceByBatch(list);
			LogCvt.info("新建RoleId的资源关系结果为:" + result);

			if (result) { // 修改成功

				/********************** 操作Mongodb数据库 **********************/

				/********************** 操作Redis缓存 **********************/
				sqlSession.commit(true);

				result = true;

			} else { // 修改失败
				sqlSession.rollback(true);
			}
		} catch (Exception e) {
			result = false;
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("修改RoleResource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 查询 RoleResource
	 * 
	 * @param roleResource
	 * @return List<RoleResource> 结果集合
	 */
	@Override
	public List<RoleResource> findRoleResource(Long roleId) {

		List<RoleResource> result = new ArrayList<RoleResource>();
		SqlSession sqlSession = null;
		RoleResourceMapper roleResourceMapper = null;
		try {
			/********************** 操作Redis缓存 **********************/
			
			/********************** 操作Mongodb数据库 **********************/

			/********************** 操作MySQL数据库 **********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
			RoleResource roleResource = new RoleResource();
			roleResource.setRoleId(roleId);
			result = roleResourceMapper.findRoleResource(roleResource);
			// sqlSession.commit(true);
		} catch (Exception e) {
			result = null;
			// if(null != sqlSession)
			// sqlSession.rollback(true);
			LogCvt.error("查询RoleResource失败，原因:" + e.getMessage(), e);
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return result;

	}

	/**
	 * 分页查询 RoleResource
	 * 
	 * @param page
	 * @param roleResource
	 * @return Page<RoleResource> 结果集合
	 */
	// @Override
	// public Page<RoleResource> findRoleResourceByPage(Page<RoleResource> page,
	// RoleResource roleResource) {
	//
	// List<RoleResource> result = new ArrayList<RoleResource>();
	// SqlSession sqlSession = null;
	// RoleResourceMapper roleResourceMapper = null;
	// try {
	// /**********************操作Redis缓存**********************/
	//
	// /**********************操作Mongodb数据库**********************/
	//
	// /**********************操作MySQL数据库**********************/
	// sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	// roleResourceMapper = sqlSession.getMapper(RoleResourceMapper.class);
	//
	// result = roleResourceMapper.findByPage(page, roleResource);
	// page.setResultsContent(result);
	// // sqlSession.commit(true);
	// } catch (Exception e) {
	// // if(null != sqlSession)
	// // sqlSession.rollback(true);
	// LogCvt.error("分页查询RoleResource失败，原因:" + e.getMessage(), e);
	// } finally {
	// if(null != sqlSession)
	// sqlSession.close();
	// }
	// return page;
	//
	// }

}