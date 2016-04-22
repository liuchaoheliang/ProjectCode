package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientProductAuditMapper;
import com.froad.db.redis.MerchantAuditRedis;
import com.froad.db.redis.ProductAuditRedis;
import com.froad.enums.OrgLevelEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientProductAuditLogic;
import com.froad.po.ClientMerchantAudit;
import com.froad.po.ClientProductAudit;
import com.froad.po.Org;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: ClientProductAuditLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ClientProductAuditLogicImpl implements ClientProductAuditLogic {


    /**
     * 增加 ClientProductAudit
     * @param clientProductAudit
     * @return Long    主键ID
     */
	@Override
	public Long addClientProductAudit(ClientProductAudit clientProductAudit) throws FroadServerException, Exception{

		Long clientProductAuditId = 0l; 
		SqlSession sqlSession = null;
		ClientProductAuditMapper clientProductAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientProductAuditMapper = sqlSession.getMapper(ClientProductAuditMapper.class);

			//clientId+orgLevel+productType组合下只能允许一条记录
			String clientId=clientProductAudit.getClientId();
			String orgLevel=clientProductAudit.getOrgLevel();
			String productType=clientProductAudit.getProductType();
			//根据clientId+orgLevel+productType获取商品审核配置信息
			ClientProductAudit filter = new ClientProductAudit();
			filter.setClientId(clientId);
			filter.setOrgLevel(orgLevel);
			filter.setProductType(productType);
			List<ClientProductAudit> auditList = this.findClientProductAudit(filter);
			if(Checker.isNotEmpty(auditList)){
				throw new FroadServerException(clientId+":"+orgLevel+":"+productType+":"+"商品审核配置无符合记录");
			}
			
			
			// 添加数据
			if (clientProductAuditMapper.addClientProductAudit(clientProductAudit) > -1) { 
				clientProductAuditId = clientProductAudit.getId(); 
				sqlSession.commit(true); 
			} else { // 添加失败
				sqlSession.rollback(true); 
			}
			
			/**********************操作Redis缓存**********************/
			ProductAuditRedis.set_cbbank_product_audit_client_id_org_level_product_type(clientProductAudit);

			
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
		return clientProductAuditId; 

	}


    /**
     * 删除 ClientProductAudit
     * @param clientProductAudit
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteClientProductAudit(ClientProductAudit clientProductAudit) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientProductAuditMapper clientProductAuditMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientProductAuditMapper = sqlSession.getMapper(ClientProductAuditMapper.class);

			/**********************操作Redis缓存**********************/
			//先查出删除前的记录
			ClientProductAudit oldProductAudit = clientProductAuditMapper.findClientProductAuditById(clientProductAudit.getId());
			if(Checker.isNotEmpty(oldProductAudit)){
				result = ProductAuditRedis.del_cbbank_product_audit_client_id_org_level_product_type(oldProductAudit);
			}
			
			
			/**********************操作MySQL数据库**********************/
			result = clientProductAuditMapper.deleteClientProductAudit(clientProductAudit); 
			if (result) { // 删除成功
				sqlSession.commit(true);
			} else { // 删除失败
				sqlSession.rollback(true); 
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
     * 修改 ClientProductAudit
     * @param clientProductAudit
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateClientProductAudit(ClientProductAudit clientProductAudit) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientProductAuditMapper clientProductAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientProductAuditMapper = sqlSession.getMapper(ClientProductAuditMapper.class);

			result = clientProductAuditMapper.updateClientProductAudit(clientProductAudit); 
			if (result) { // 修改成功
				sqlSession.commit(true);
			} else { // 修改失败
				sqlSession.rollback(true); 
			}
			
			
			/**********************操作Redis缓存**********************/
			ClientProductAudit find = new ClientProductAudit();
			find.setId(clientProductAudit.getId());
			//根据主键过滤查询出商品审核配置对象，将最新修改过后的数据重新set到缓存中
			List<ClientProductAudit> findList = this.findClientProductAudit(find);
			if(Checker.isNotEmpty(findList)){
				clientProductAudit = findList.get(0);
				//缓存商品审核配置信息
				result = ProductAuditRedis.set_cbbank_product_audit_client_id_org_level_product_type(clientProductAudit);
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
     * 查询 ClientProductAudit
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @param productType 商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
     * @return ClientProductAudit   商品审核配置对象
     */
	@Override
	public ClientProductAudit findClientProductAuditByOrgCode(String clientId, String orgCode,String productType) {

		ClientProductAudit result = new ClientProductAudit();
		try{
			/**********************操作MySQL数据库**********************/
			
			//根据clientId+orgCode查询出机构对象
			Org org = new CommonLogicImpl().getOrgByOrgCode(orgCode,clientId);
			if(Checker.isEmpty(org)){
				return null;
			}
			
			
			String orgLevel=org.getOrgLevel();
			//根据clientId+orgLevel+productType获取商品审核配置信息
			ClientProductAudit clientProductAudit = new ClientProductAudit();
			clientProductAudit.setClientId(clientId);
			clientProductAudit.setOrgLevel(orgLevel);
			clientProductAudit.setProductType(productType);
			//先查缓存field包含client_id/org_level/product_type/start_org_level/end_org_level
			Map<String, String> clientProductAuditRedis = ProductAuditRedis.getAll_cbbank_product_audit_client_id_org_level_product_type(clientId, orgLevel, productType);
			if(Checker.isNotEmpty(clientProductAuditRedis)){
				clientProductAudit = new ClientProductAudit();
				clientProductAudit.setClientId(clientProductAuditRedis.get("client_id"));
				clientProductAudit.setOrgLevel(clientProductAuditRedis.get("org_level"));
				clientProductAudit.setProductType(clientProductAuditRedis.get("product_type"));
				clientProductAudit.setStartOrgLevel(clientProductAuditRedis.get("start_org_level"));
				clientProductAudit.setEndOrgLevel(clientProductAuditRedis.get("end_org_level"));
			}else{
				//查mysql
				List<ClientProductAudit> auditList = this.findClientProductAudit(clientProductAudit);
				if(Checker.isEmpty(auditList)){
					return null; //无配置直接返回null无权限控制商品操作
				}else{
					clientProductAudit=auditList.get(0);//clientId+orgLevel+productType只能查出一条符合记录
					/**********************操作Redis缓存**********************/
					ProductAuditRedis.set_cbbank_product_audit_client_id_org_level_product_type(clientProductAudit);
				}
				
			}
			
			
			
			
			
			/**********************开始计算起始/终审机构编号**********************/
			
			//获取起始机构编号+终审机构编号
			String startAuditOrgCode="";
			String endAuditOrgCode="";
			
			//起始机构级别
			if(Checker.isNotEmpty(clientProductAudit.getStartOrgLevel())){
				String startOrgLevel=clientProductAudit.getStartOrgLevel();
				
				if(OrgLevelEnum.orgLevel_four.getLevel().equals(startOrgLevel)){
					startAuditOrgCode=org.getOrgCode();
				}else if(OrgLevelEnum.orgLevel_three.getLevel().equals(startOrgLevel)){
					startAuditOrgCode=org.getCountyAgency();
				}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(startOrgLevel)){
					startAuditOrgCode=org.getCityAgency();
				}else if(OrgLevelEnum.orgLevel_one.getLevel().equals(startOrgLevel)){
					startAuditOrgCode=org.getProvinceAgency();
				}
				
				//若没获取到值，则起始审核机构为自己本身
				if(Checker.isEmpty(startAuditOrgCode)){
					startAuditOrgCode=orgCode;
				}
			}
			
			
			
			//终审机构级别
			if(Checker.isNotEmpty(clientProductAudit.getEndOrgLevel())){
				String endOrgLevel=clientProductAudit.getEndOrgLevel();
				
				if(OrgLevelEnum.orgLevel_four.getLevel().equals(endOrgLevel)){
					endAuditOrgCode=org.getOrgCode();
				}else if(OrgLevelEnum.orgLevel_three.getLevel().equals(endOrgLevel)){
					endAuditOrgCode=org.getCountyAgency();
				}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(endOrgLevel)){
					endAuditOrgCode=org.getCityAgency();
				}else if(OrgLevelEnum.orgLevel_one.getLevel().equals(endOrgLevel)){
					endAuditOrgCode=org.getProvinceAgency();
				}
				
				//若没获取到值，则终审机构为自己本身
				if(Checker.isEmpty(endAuditOrgCode)){
					endAuditOrgCode=orgCode;
				}
			}
			
			
			//设置起始机构编号+终审机构编号进行返回
			result=clientProductAudit;
			result.setStartAuditOrgCode(startAuditOrgCode);
			result.setEndAuditOrgCode(endAuditOrgCode);
			
		}catch (Exception e) { 
			LogCvt.error("根据clientId+orgCode+productType获取商品审核配置信息ClientProductAudit失败，原因:" + e.getMessage(), e); 
		} 
		return result; 
		
	
	}
	

    /**
     * 查询 ClientProductAudit
     * @param clientProductAudit
     * @return List<ClientProductAudit>    结果集合 
     */
	@Override
	public List<ClientProductAudit> findClientProductAudit(ClientProductAudit clientProductAudit) {

		List<ClientProductAudit> result = new ArrayList<ClientProductAudit>(); 
		SqlSession sqlSession = null;
		ClientProductAuditMapper clientProductAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientProductAuditMapper = sqlSession.getMapper(ClientProductAuditMapper.class);

			result = clientProductAuditMapper.findClientProductAudit(clientProductAudit); 
		} catch (Exception e) { 
			LogCvt.error("查询ClientProductAudit失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 ClientProductAudit
     * @param page
     * @param clientProductAudit
     * @return Page<ClientProductAudit>    结果集合 
     */
	@Override
	public Page<ClientProductAudit> findClientProductAuditByPage(Page<ClientProductAudit> page, ClientProductAudit clientProductAudit) {

		List<ClientProductAudit> result = new ArrayList<ClientProductAudit>(); 
		SqlSession sqlSession = null;
		ClientProductAuditMapper clientProductAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientProductAuditMapper = sqlSession.getMapper(ClientProductAuditMapper.class);

			result = clientProductAuditMapper.findByPage(page, clientProductAudit); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询ClientProductAudit失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}