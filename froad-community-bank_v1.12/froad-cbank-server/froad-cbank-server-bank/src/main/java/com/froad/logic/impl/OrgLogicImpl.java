package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.OrgRelationMongo;
import com.froad.db.mongo.bean.OrgRelation;
import com.froad.db.mongo.bean.SubOrgsInfo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.OrgMapper;
import com.froad.db.redis.OrgRedis;
import com.froad.enums.OrgLevelEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankOperatorLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrgLogic;
import com.froad.po.BankOperator;
import com.froad.po.Org;
import com.froad.support.Support;
import com.froad.util.Checker;
import com.froad.util.OrgSuperUtil;
import com.froad.util.RedisKeyUtil;

/**
 * 
 * <p>@Title: OrgLogic.java</p>
 * <p>Description: 描述 </p> 机构信息Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月26日
 */
public class OrgLogicImpl implements OrgLogic {
	

    /**
     * 增加机构信息Org
     * @param org
     * @return Long    主键ID
     */
	@Override
	public Long addOrg(Org org)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		long orgId=0l;
		try { 
			CommonLogic commonLogic = new CommonLogicImpl();
			//验证orgCode+clientId是否已存在
			if(Checker.isNotEmpty(commonLogic.queryByOrgCode(org.getClientId(), org.getOrgCode()))){
				throw new FroadServerException("组织编码已存在！");
			}
			
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);

			//设置是否有效
			org.setIsEnable(true);
			
			//设置银行类型bank_type
			String bankTpe = commonLogic.getClientById(org.getClientId()).getBankType();
			org.setBankType(bankTpe);
			
			orgMapper.addOrg(org); 
			
			/**********************同步到会员系统**********************/
			new Support().synchBankLabel(org.getOrgCode(), org.getOrgName(), 0,org.getClientId());
			
			/**********************保存机构商户关系Mongo**********************/
			boolean result=this.addOrgRelation(org);
			
			/**********************操作Redis缓存**********************/
			String key = "";
			//业务部门
			if(org.getOrgType()){
				//缓存该商户Id下对应的1-2-3-4级机构关系
				OrgRedis.set_cbbank_merchant_org_level_merchant_id(org.getMerchantId(), commonLogic.setOrgMap(org));
				
				//设置client_id+merchant_id的机构信息
				key=RedisKeyUtil.cbbank_merchant_org_client_id_merchant_id(org.getClientId(), org.getMerchantId());
				OrgRedis.set_cbbank_org(key, org);
			
				//设置client_id+outlet_id的机构信息
				key=RedisKeyUtil.cbbank_outlet_org_client_id_outlet_id(org.getClientId(), org.getOutletId());
				OrgRedis.set_cbbank_org(key, org);
			}
						
			//设置client_id+org_code的机构信息
			key=RedisKeyUtil.cbbank_org_client_id_org_code(org.getClientId(), org.getOrgCode());
			OrgRedis.set_cbbank_org(key, org);
			
			//mysql提交
			if(result){
				orgId=org.getId();
				sqlSession.commit(true); 
			}
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);    
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return orgId; 

	}


	 /**
     * 批量增加 Org机构信息
     * @param org
     * @return Bool    操作是否成功
     */
	@Override
	public Boolean addOrgByBatch(List<Org> orgList)  throws FroadServerException, Exception{
		return false;
//		Boolean result = false; 
//		try { 
//			for(Org org:orgList){
//				/**********************操作MySQL数据库**********************/
//				this.addOrg(org);
//				
//				/**********************保存机构商户关系Mongo**********************/
//				result=this.addOrgRelation(org);
//			}
//			
//			
//		} catch (Exception e) { 
//			result=false;
//			throw e;
//		} 
//		return result; 
	}
	
	
    /**
     * 禁用/启用机构 Org(一期暂不做)
     * @param org
     * @描述：cordeModule 传主键id和禁用/启用字段过来
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteOrg(Org org)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);

			/**********************操作Mongo**********************/
			/**
			 * 禁用  -获取当前禁用机构下的所有下级机构，修改mysql数据，及禁用机构下所有的管理员用户，及mongo存储的关系对应处理
			 * 启用  -启用只启用当前机构的状态，及当前机构下的管理员用户，及mongo对应关系，而下级所有子机构自行一个个启用
			 */
			
			
			//根据机构id查出机构信息
			Org filterOrg =new Org();
			filterOrg.setId(org.getId());
			Org orgNew=orgMapper.findOrgById(filterOrg);
			if(Checker.isNotEmpty(orgNew)){
				BankOperatorLogic bankOperatorLogic=new BankOperatorLogicImpl();//银行用户Logic
				
				if(org.getIsEnable()){//启用-添加
					//修改mysql数据
					result = orgMapper.deleteOrg(org); 
					//将机构信息添加到Mongo中
					result=this.addOrgRelation(orgNew);
					
					//修改cb_bank_operator数据 为 有效
					BankOperator query = new BankOperator();
					query.setClientId(orgNew.getClientId());
					query.setOrgCode(orgNew.getOrgCode());
					// 找出当前机构以及下级机构所有银行用户
					List<BankOperator> operatorsList = bankOperatorLogic.findBankOperator(query);
					for(BankOperator b : operatorsList){
						b.setStatus(true);
						// 启用银行用户
						result = bankOperatorLogic.updateBankOperator(b);
					}
					LogCvt.info("启用"+orgNew.getOrgCode()+"完成！");
					
				}else{//禁用-移除
					//将机构信息从Mongo中移除 并 将当前机构下所有的子机构下的银行管理员设置为无效
					result=this.removeOrgRelation(orgMapper,orgNew);
					
					LogCvt.info("禁用"+orgNew.getOrgCode()+"完成！");
				}
			}else{
				LogCvt.error("未找到机构信息[机构号"+org.getOrgCode()+"]数据库信息未更新"); 
				result=false;
			}
					
			if(result){
				sqlSession.commit(true);
			}
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


	
	/**
	 *  保存至机构商户关系表Mongo处理逻辑
	  * @Title: addOrgMerchantOutlet
	  * @Description: TODO
	  * @author: ll 2015年3月26日
	  * @modify: ll 2015年3月26日
	  * @param @param orgRelatin
	  * @param @return   bool 
	  * @throws
	 */
	public Boolean addOrgRelation(Org org){
		boolean result = false;
		
		if(org.getOrgType()){//业务机构
			//机构号
			String orgCode=org.getOrgCode();
			//机构名称
			String orgName=org.getOrgName();
			//商户id
			String merchantId=org.getMerchantId();
			//门店id
			String outletId=org.getOutletId();
			
			//添加本身机构商户关系Mongo，第一次新增的机构没有下级机构sub_orgs为空
			OrgRelation orgRelation = new OrgRelation();
			orgRelation.setId(orgCode);
			orgRelation.setClientId(org.getClientId());
			orgRelation.setMerchantId(merchantId);
			orgRelation.setOutletId(outletId);
			orgRelation.setSubOrgs(new ArrayList<SubOrgsInfo>());
			result=OrgRelationMongo.addOrgRelationMongo(orgRelation);
			
			
			/**下级机构商户关系list集合**/
			//根据org_level机构级别判断包含所属上级机构<机构级别1-2-3-4->
			
			
			String orgTop=OrgSuperUtil.getOrgSuper(org);//上级机构
			if("".equals(orgTop)){//级别1的机构无上级机构
				return true;//一级机构没有上级，直接return掉
			}
			
			
			//判断是否有上级机构的Mongo
			/**查询上级机构存储的mongodb**/
			OrgRelation orgRelationTop=OrgRelationMongo.findByOrgCode(org.getClientId(),orgTop);
			if(Checker.isNotEmpty(orgRelationTop)){
				
				//声明下级机构对象
				SubOrgsInfo subOrg = new SubOrgsInfo();
				subOrg.setOrgCode(orgCode);
				subOrg.setOrgName(orgName);
				subOrg.setMerchantId(merchantId);
				subOrg.setOutletId(outletId);
				
				//将新增的机构添加到上一级的mongo中
				result=OrgRelationMongo.addSubOrgInfo(orgRelationTop.getId(),orgRelationTop.getClientId(), subOrg);
			}
		}else{
			result=true;
		}
		return result;
		
	}
	
	
	
	/**
	 *  移除某个机构的机构商户关系表Mongo处理逻辑
	  * @Title: removeOrgRelation
	  * @Description: TODO
	  * @author: ll 2015年3月27日
	  * @modify: ll 2015年3月27日
	  * @param @param org
	  * @param @return   bool 
	  * @throws
	 */
	public Boolean removeOrgRelation(OrgMapper orgMapper,Org org) throws FroadServerException, Exception{
		boolean result = false;
		
		BankOperatorLogic bankOperatorLogic=new BankOperatorLogicImpl();
		String orgCode = org.getOrgCode();//机构号
		String clientId = org.getClientId();//客户端id
		
		
		//业务机构才对mongo做处理
		if(org.getOrgType()){
			/**取得上级机构的Mongo，从上级机构的Mongo中的list集合移除**/
			String orgTop=OrgSuperUtil.getOrgSuper(org);//上级机构
			//判断是否有上级机构的Mongo
			if(Checker.isNotEmpty(orgTop)){
				/**查询上级机构存储的mongodb**/
				OrgRelation orgRelationTop=OrgRelationMongo.findByOrgCode(clientId,orgTop);
				if(Checker.isNotEmpty(orgRelationTop)){
					//移除该机构对象
					result=OrgRelationMongo.removeSubOrgInfo(orgRelationTop.getId(), clientId, orgCode);
				}
			}
		}
		
		List<BankOperator> operatorsList=null;//银行用户List
		//获得所有下级子机构
		List<Org> subOrgs = this.findAllSubOrgs(clientId,orgCode);
		for(Org subOrg : subOrgs){
			//修改cb_org数据
			subOrg.setIsEnable(false);
			result = orgMapper.deleteOrg(subOrg); 
			//依次删除该机构下属机构关系数据(包括自身)
			if(subOrg.getOrgType()){//业务机构才对mongo做处理
				result = OrgRelationMongo.deleteOrgRelationMongo(clientId,subOrg.getOrgCode());
			}
			//修改cb_bank_operator数据 为 无效
			BankOperator query = new BankOperator();
			query.setClientId(clientId);
			query.setOrgCode(subOrg.getOrgCode());
			// 找出当前机构以及下级机构所有银行用户
			operatorsList = bankOperatorLogic.findBankOperator(query);
			for(BankOperator b : operatorsList){
				b.setStatus(false);
				// 禁用银行用户
				result = bankOperatorLogic.updateBankOperator(b);
			}
		}
		
		
		return result;
	}
	
	
	
    /**
     * 编辑机构 Org
     * @param org
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateOrg(Org org)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		try {
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);

			/**********orgName/orgType发生变化时修改mongo数据**************/
			Org resultOrg=orgMapper.findOrgById(org);//根据orgId主键id查询旧机构对象
			//不能修改客户端id/机构码/机构级别/上级机构/商户编号/门店编号，故不更新Mongo数据，只orgName/orgType发生变化时修改mongo数据
			if(Checker.isNotEmpty(org.getOrgName()) || Checker.isNotEmpty(org.getOrgType())){
				if(Checker.isNotEmpty(resultOrg)){
					//若传上来的值与数据库原先的值不同的话，则说明做了改动
					if(!org.getOrgName().equals(resultOrg.getOrgName())){
						String orgTop=OrgSuperUtil.getOrgSuper(resultOrg);//取得上级机构
						
						if(Checker.isNotEmpty(orgTop)){
							//修改mongo数据
							result=OrgRelationMongo.updateSubOrgName(orgTop,org.getOrgName(),resultOrg);
							
							/**********************同步到会员系统进行更新**********************/
							new Support().synchBankLabel(org.getOrgCode(), org.getOrgName(), 1,resultOrg.getClientId());
							LogCvt.info("修改机构商户关系表中的orgName完成");
						}
					}
					
					//如果原数据库是业务机构，而改为部门机构，需要删除该机构的mongo关系
//					if(resultOrg.getOrgType()){
//						if(org.getOrgType()){
//							//后续实现，安徽无机构业务的互转
//						}
//					}
				
				}
			}
			
			//修改mysql值
			result = orgMapper.updateOrg(org); 
			
			
			//操作成功提交
			if(result){
				sqlSession.commit(true);
			}
			
			/**********************操作Redis缓存**********************/
			resultOrg=orgMapper.findOrgById(org);//根据orgId主键id查询修改过后的机构对象
			//重新设置机构缓存数据
			if(Checker.isNotEmpty(resultOrg)){
				//设置client_id+org_code的机构信息
				String key=RedisKeyUtil.cbbank_org_client_id_org_code(resultOrg.getClientId(), resultOrg.getOrgCode());
				result = OrgRedis.set_cbbank_org(key, resultOrg);
				
				//业务部门
				if(resultOrg.getOrgType()){
					//设置client_id+outlet_id的机构信息
					key=RedisKeyUtil.cbbank_outlet_org_client_id_outlet_id(resultOrg.getClientId(), resultOrg.getOutletId());
					result = OrgRedis.set_cbbank_org(key, resultOrg);
					
					//设置client_id+merchant_id的机构信息
					key=RedisKeyUtil.cbbank_merchant_org_client_id_merchant_id(resultOrg.getClientId(), resultOrg.getMerchantId());
					result = OrgRedis.set_cbbank_org(key, resultOrg);
				}
				
			}
			
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	

    /**
     * 查询 Org
     * @param org
     * @return List<Org>    结果集合 
     */
	@Override
	public List<Org> findOrg(Org org) {

		List<Org> result = new ArrayList<Org>(); 
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);

			result = orgMapper.findOrg(org); 
		} catch (Exception e) { 
			LogCvt.error("查询Org失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 Org
     * @param page
     * @param org
     * @param loginOrgCode 当前登录loginOrgCode
     * @return List<Org>    结果集合 
     */
	@Override
	public Page<Org> findOrgByPage(Page<Org> page, Org org,String loginOrgCode) {

		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		try { 
			List<Org> result = new ArrayList<Org>();
			 
			
			//判断当前登录的级别，若为一级，减少多条件查询
			if(Checker.isNotEmpty(loginOrgCode) && Checker.isNotEmpty(org.getClientId())){
				Org orgLogin = new CommonLogicImpl().getOrgByOrgCode(loginOrgCode, org.getClientId());
				
				if(Checker.isEmpty(orgLogin)){
					return new Page<Org>();
				}
				
				if(OrgLevelEnum.orgLevel_one.getLevel().equals(orgLogin.getOrgLevel())){
					LogCvt.info("一级机构查询机构列表");
					loginOrgCode=null;//清空查全部,减少多条件查询
				}
			}

			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);
			
			result = orgMapper.findByPage(page, org,loginOrgCode); 
			page.setResultsContent(result);
			
		} catch (Exception e) { 
			LogCvt.error("分页查询Org失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


	/**
     * 根据orgCode获取下级叶子机构 
     * @param orgCode 	机构编号
     * @return List<Org>    子机构集合
     */
	@Override
	public List<Org> findSubOrgs(String clientId,String orgCode) {
		
		List<Org> subOrgs = new ArrayList<Org>(); 
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		try { 

			//根据当前orgCode查出机构级别 
			Org org=new CommonLogicImpl().getOrgByOrgCode(orgCode, clientId);
			if(Checker.isNotEmpty(org)){
				if(OrgLevelEnum.orgLevel_four.getLevel().equals(org.getOrgLevel())){
					return null;
				}else if(OrgLevelEnum.orgLevel_three.getLevel().equals(org.getOrgLevel())){
					org=new Org();
					org.setClientId(clientId);
					org.setCountyAgency(orgCode);
					org.setOrgLevel("4");
				}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(org.getOrgLevel())){
					org=new Org();
					org.setClientId(clientId);
					org.setCityAgency(orgCode);
					org.setOrgLevel("3");
				}else if(OrgLevelEnum.orgLevel_one.getLevel().equals(org.getOrgLevel())){
					org=new Org();
					org.setClientId(clientId);
					org.setProvinceAgency(orgCode);
					org.setOrgLevel("2");
				}
				
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				orgMapper = sqlSession.getMapper(OrgMapper.class);
				
				subOrgs = orgMapper.findSubOrgs(org); 
			}
			
			
		} catch (Exception e) { 
			LogCvt.error("获取下级叶子机构Org失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return subOrgs;
		
	}

	
	
	/**
	 * 根据orgCode获取下级所有机构（包含自身orgCode返回） 
	 * @param orgCode 机构编码
	 * @param clientId 客户端id
	 * @return List<String> 子机构集合
	 */
	@Override
	public List<Org> findAllSubOrgs(String clientId,String orgCode){
		
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		List<Org> subOrgs = new ArrayList<Org>();
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);

			//过滤条件
			Org org =new Org();
			org.setOrgCode(orgCode);
			org.setClientId(clientId);
			subOrgs = orgMapper.findAllSubOrgCodes(org); 
			
			
		} catch (Exception e) { 
			LogCvt.error("获取下级所有机构Org失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return subOrgs;
		
	
	}
	
	/**
	 * 查询下级所有机构orgCode
	 * @param clientId
	 * @param orgCode
	 * @return orgCode机构编号集合
	 */
	@Override
	public List<String> findAllSubOrgCodes(String clientId,String orgCode){
		
		List<Org> subOrgs = this.findAllSubOrgs(clientId, orgCode);
		List<String> subOrgCodes = new ArrayList<String>();
		
		for(Org o:subOrgs){
			subOrgCodes.add(o.getOrgCode());
		}
		
		return subOrgCodes;
	}
	
	
	
	/**************业务机构存储在mongo中，从mongo中取出下级机构********START**************/
	
	/**
	 * 根据orgCode获取业务叶子机构，从mongo中取
	 * @param orgCode 机构编号
	 * @param clientId 客户端id
	 * @return orgCode集合
	 */
	private List<Org> findYewuOrgs(String clientId,String orgCode){
		List<Org> orgs = new ArrayList<Org>();
		Org org =new Org();
		
		//从mongo取机构商户关系信息
		OrgRelation orgRelation=OrgRelationMongo.findByOrgCode(clientId,orgCode); 
		if(Checker.isNotEmpty(orgRelation)){
			//取下级机构信息集合
			List<SubOrgsInfo> subOrgs = orgRelation.getSubOrgs();
			if(Checker.isNotEmpty(subOrgs)){
				for(SubOrgsInfo sub:subOrgs){
					org =new Org();
					org.setOrgCode(sub.getOrgCode());
					org.setOrgName(sub.getOrgName());
					org.setMerchantId(sub.getMerchantId());
					org.setOutletId(sub.getOutletId());
					orgs.add(org);
				}
			}
		}
		return orgs;
	}
	
	
	/**
	 * 根据orgCode获取业务所有子机构，从mongo中取
	 * @param orgCode 机构编号
	 * @param clientId 客户端id
	 * @return orgCode集合
	 */
	private List<String> findAllYewuOrgs(String clientId,String orgCode){
		OrgRelation orgRel = OrgRelationMongo.findByOrgCode(clientId,orgCode);
		List<String> list=new LinkedList<String>();
		if(Checker.isNotEmpty(orgRel)){
			list.add(orgCode);
			List<SubOrgsInfo> subs = orgRel.getSubOrgs();
			if(Checker.isNotEmpty(subs)){
				for(SubOrgsInfo s:subs){
					list.addAll(findAllYewuOrgs(clientId,s.getOrgCode()));
				}
			}
		}
		
		return list;
	}
	
	/**************业务机构存储在mongo中，从mongo中取出下级机构********END**************/
	
	
	/**
     * 查询市级区级机构(逗号隔开)
     * @param orgCode 机构编号
     * @param clientId 客户端编号
     * @return string 返回市级区级机构编号
     */
	@Override
	public String findSuperOrgCodeByType(String clientId,String orgCode){
		String resultOrg="";
		
		Org org=new CommonLogicImpl().getOrgByOrgCode(orgCode, clientId);
		if(org!=null){
//			0代表省联社-市联社-县联社-网点，1代表省联社-市联社-网点，2代表市联社-网点，3县联社-网点
//			如果是网点发展的商户，而且banktype是四级的话，那么就要市级和区级机构，如果是三级的话，那么就填写市级机构，区级机构为0；
//			如果是区级机构发展的商户，而且banktype是四级的话，那么就要市级和区级机构，如果是三级的话，那么就填写市级机构，区级机构为0；
//			如果是市级机构发展的商户，而且banktype是四级的话，那么就要市级机构，区级机构为0，如果是三级的话，那么就填写市级机构，区级机构为0；

			if(OrgLevelEnum.orgLevel_four.getLevel().equals(org.getOrgLevel())){
				resultOrg=org.getCityAgency()+","+org.getCountyAgency();
			}else if (OrgLevelEnum.orgLevel_three.getLevel().equals(org.getOrgLevel())){
				resultOrg=org.getCityAgency()+","+"0";
			}else{
				resultOrg="0,0";
			}
			
			
		}
		
		return resultOrg;
	}
	
	
	
	/**
     * 查询areaId集合下的有效机构所属的法人行社列表
     * @param clientId 客户端编号
     * @param areaIds  区Id集合
     * @return list<OrgVo> 返回机构对象集合
     */
	@Override
	public List<Org> findOrgByAreaIds(String clientId, List<Long> areaIds) {
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		List<Org> resultOrgs = new ArrayList<Org>();
		try {
			//因二级机构中的areaId未存值，无法直接关联到区域Id，故其根据区Id查出上级机构，即为所属的法人行社列表
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);

			//过滤条件
			Org fileterOrg =new Org();
			fileterOrg.setClientId(clientId);
			fileterOrg.setIsEnable(true);
			List<Org> orgs = orgMapper.findOrgByAreaIds(fileterOrg,areaIds); 
			List<String> orgCodes = new ArrayList<String>();
			
			if(Checker.isNotEmpty(orgs)){
				Org top = new Org();//上级机构对象
				CommonLogic commongLogic = new CommonLogicImpl();
				for(Org org : orgs){
					//取出上级机构
					String orgTop=OrgSuperUtil.getOrgSuper(org);
					if(orgCodes.contains(orgTop)){
						continue;//若已执行过相同的orgTop，则不查(取未添加过的法人行社添加到集合中，避免集合中出现相同的对象)
						
					}
					
					if(Checker.isNotEmpty(orgTop)){
						top = commongLogic.getOrgByOrgCode(orgTop, clientId);
						if(Checker.isNotEmpty(top) && top.getOrgLevel().equals(OrgLevelEnum.orgLevel_two.getLevel())){
							orgCodes.add(orgTop);
							
							//法人行社
							resultOrgs.add(top);
							
						}
					}
				}
			}
			
			
			
		} catch (Exception e) { 
			LogCvt.error("查询areaId集合下的有效机构所属的法人行社列表失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return resultOrgs;
	
	
	}


	/**
	 * 获取网点交集集合
	 * @param clientId 客户端id
	 * @param loginOrgCode 登录人所属机构编号
	 * @param filterOrgCode 过滤条件机构编号
	 * @return 二者orgCode下级网点交集集合
	 */
	@Override
	public List<String> findIntersectionOrgCodeList(String clientId,String loginOrgCode, String filterOrgCode) {
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		List<String> orgCodes = new ArrayList<String>();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);
			
			
			Org findOrg = new Org();
			findOrg.setClientId(clientId);
			findOrg.setOrgCode(filterOrgCode);
			findOrg.setIsEnable(true);
			//取网点级别
			findOrg.setOrgLevel(OrgSuperUtil.getPointOrgLevel(clientId));
			List<Org> orgCodeList = orgMapper.findIntersectionOrgCodeList(findOrg,loginOrgCode);
			for(Org result:orgCodeList){
				orgCodes.add(result.getOrgCode());
			}
			
			
		} catch (Exception e) { 
			LogCvt.error("查询二者orgCode下所属网点交集集合列表失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return orgCodes;
	}

    /**
     * 通过机构查询条件查询机构二级、三级、四级相关集合.
     */
	@Override
	public List<Org> findOrgByOrgNameOrClientId(String clientId, String orgName, Integer limit, String loginOrgCode) {
		List<Org> orgList = new ArrayList<Org>();
		Org orgLogin = null;
	    if ((Checker.isNotEmpty(loginOrgCode)) && (Checker.isNotEmpty(clientId))) {
	      orgLogin = new CommonLogicImpl().getOrgByOrgCode(loginOrgCode, clientId);
	      if (Checker.isEmpty(orgLogin)) {
	        return orgList;
	      }
	    }
	    SqlSession sqlSession = null;
	    OrgMapper orgMapper = null;
	    try {
	      sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	      orgMapper = (OrgMapper)sqlSession.getMapper(OrgMapper.class);

	      List<Org> orgs = orgMapper.findOrgByOrgNameOrClientId(clientId, orgName, limit, loginOrgCode);
	      Set<String>  orgcodeSet = new HashSet<String>();
	      for (Org org : orgs) {
	        orgcodeSet.add(org.getOrgCode());
	        orgList.add(org);
	        orgList = getSuperOrg(org, orgLogin, orgcodeSet, orgList);
	      }
	    }
	    catch (Exception e) {
	      LogCvt.error("查询Org失败，原因:" + e.getMessage(), e);
	    } finally {
	      if (sqlSession != null)
	        sqlSession.close();
	    }
	    return orgList;
	}
	
	/**
	 * 内部迭代方法.
	 * @param org
	 * @param orgLogin
	 * @param orgcodeSet
	 * @param orgs
	 * @return
	 */
	 private static List<Org> getSuperOrg(Org org, Org orgLogin, Set<String> orgcodeSet, List<Org> orgs) {
		  if (!(orgLogin.getOrgLevel().equals(org.getOrgLevel()))) {
		      Org superOrg = new CommonLogicImpl().getSuperOrg(org.getClientId(), org.getOrgCode());
		      if (!(orgcodeSet.contains(superOrg.getOrgCode()))) {
		        orgcodeSet.add(superOrg.getOrgCode());
		        orgs.add(superOrg);
		      }
		      getSuperOrg(superOrg, orgLogin, orgcodeSet, orgs);
		   }
		return orgs;
	}
}