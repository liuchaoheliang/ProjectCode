package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientMerchantAuditMapper;
import com.froad.db.redis.MerchantAuditRedis;
import com.froad.enums.OrgLevelEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ClientMerchantAuditLogic;
import com.froad.po.ClientMerchantAudit;
import com.froad.po.Org;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: ClientMerchantAuditLogic.java</p>
 * <p>Description: 描述 </p> 商户审核配置Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月26日
 */
public class ClientMerchantAuditLogicImpl implements ClientMerchantAuditLogic {


    /**
     * 增加 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Long    主键ID
     */
	@Override
	public Long addClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) throws FroadServerException, Exception{

		Long clientMerchantAuditId = 0l; 
		SqlSession sqlSession = null;
		ClientMerchantAuditMapper clientMerchantAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMerchantAuditMapper = sqlSession.getMapper(ClientMerchantAuditMapper.class);

			//clientId+orgLevel组合下只能允许一条记录
			String clientId=clientMerchantAudit.getClientId();
			String orgLevel=clientMerchantAudit.getOrgLevel();
			String type =clientMerchantAudit.getType();
			//根据clientId+orgLevel获取商户审核配置信息
			ClientMerchantAudit filter = new ClientMerchantAudit();
			filter.setClientId(clientId);
			filter.setOrgLevel(orgLevel);
			filter.setType(type);
			List<ClientMerchantAudit> auditList = this.findClientMerchantAudit(filter);
			if(Checker.isNotEmpty(auditList)){
				throw new FroadServerException(clientId+":"+orgLevel+"商户审核配置已存在！");
			}
			
			// 添加成功
			if (clientMerchantAuditMapper.addClientMerchantAudit(clientMerchantAudit) > -1) { 
				clientMerchantAuditId = clientMerchantAudit.getId(); 
				sqlSession.commit(true); 
			} else { // 添加失败
				sqlSession.rollback(true); 
			}
			
			/**********************操作Redis缓存**********************/
			MerchantAuditRedis.set_cbbank_merchant_audit_client_id_org_level_type(clientMerchantAudit);
			
			
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
		return clientMerchantAuditId; 

	}


    /**
     * 删除 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientMerchantAuditMapper clientMerchantAuditMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMerchantAuditMapper = sqlSession.getMapper(ClientMerchantAuditMapper.class);
			
			/**********************操作Redis缓存**********************/
			//先查出删除前的记录
			ClientMerchantAudit oldMerchantAudit = clientMerchantAuditMapper.findClientMerchantAuditById(clientMerchantAudit.getId());
			if(Checker.isNotEmpty(oldMerchantAudit)){
				result = MerchantAuditRedis.del_cbbank_merchant_audit_client_id_org_level_type(oldMerchantAudit);
			}
			
			
			/**********************操作MySQL数据库**********************/
			result = clientMerchantAuditMapper.deleteClientMerchantAudit(clientMerchantAudit); 
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
     * 修改 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		ClientMerchantAuditMapper clientMerchantAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMerchantAuditMapper = sqlSession.getMapper(ClientMerchantAuditMapper.class);

			result = clientMerchantAuditMapper.updateClientMerchantAudit(clientMerchantAudit); 
			if (result) { // 修改成功
				sqlSession.commit(true);
			} else { // 修改失败
				sqlSession.rollback(true); 
			}
			
			/**********************操作Redis缓存**********************/
			ClientMerchantAudit find = new ClientMerchantAudit();
			find.setId(clientMerchantAudit.getId());
			//根据主键过滤查询出商户审核配置对象，将最新修改过后的数据重新set到缓存中
			List<ClientMerchantAudit> findList = this.findClientMerchantAudit(find);
			if(Checker.isNotEmpty(findList)){
				clientMerchantAudit = findList.get(0);
				//缓存商户审核配置信息
				result = MerchantAuditRedis.set_cbbank_merchant_audit_client_id_org_level_type(clientMerchantAudit);
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
     * 根据clientId+orgCode获取商户审核配置信息ClientMerchantAudit
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @param type 1-审核 2-商户重置密码
     * @return ClientMerchantAudit   商户审核配置对象
     */
	@Override
	public ClientMerchantAudit findClientMerchantAuditByOrgCode(String clientId, String orgCode,String type) {
		ClientMerchantAudit result = new ClientMerchantAudit();
		try{
			/**********************操作MySQL数据库**********************/
			
			//根据clientId+orgCode查询出机构对象
			Org org = new CommonLogicImpl().getOrgByOrgCode(orgCode,clientId);
			if(Checker.isEmpty(org)){
				return null;
			}
			
			String orgLevel=org.getOrgLevel();
			//根据clientId+orgLevel+type获取商户审核配置信息
			ClientMerchantAudit clientMerchantAudit = new ClientMerchantAudit();
			clientMerchantAudit.setClientId(clientId);
			clientMerchantAudit.setOrgLevel(orgLevel);
			clientMerchantAudit.setType(type);
			//先查缓存 filed:client_id/org_level/type/start_org_level/end_org_level
			Map<String, String> clientMerchantAuditRedis = MerchantAuditRedis.getAll_cbbank_merchant_audit_client_id_org_level_type(clientId, orgLevel, type);
			if(Checker.isNotEmpty(clientMerchantAuditRedis)){
				clientMerchantAudit = new ClientMerchantAudit();
				clientMerchantAudit.setClientId(clientMerchantAuditRedis.get("client_id"));
				clientMerchantAudit.setOrgLevel(clientMerchantAuditRedis.get("org_level"));
				clientMerchantAudit.setType(clientMerchantAuditRedis.get("type"));
				clientMerchantAudit.setStartOrgLevel(clientMerchantAuditRedis.get("start_org_level"));
				clientMerchantAudit.setEndOrgLevel(clientMerchantAuditRedis.get("end_org_level"));
			}else{
				//查mysql
				List<ClientMerchantAudit> auditList = this.findClientMerchantAudit(clientMerchantAudit);
				if(Checker.isEmpty(auditList)){
					return null; //无配置直接返回null无权限控制商户操作
				}else{
					clientMerchantAudit=auditList.get(0);//clientId+orgLevel只能查出一条符合记录
					/**********************操作Redis缓存**********************/
					MerchantAuditRedis.set_cbbank_merchant_audit_client_id_org_level_type(clientMerchantAudit);
				}
			}
			
			
			/**********************开始计算起始/终审机构编号**********************/
			
			//获取起始机构编号+终审机构编号
			String startAuditOrgCode="";
			String endAuditOrgCode="";
			
			
			//起始机构级别
			if(Checker.isNotEmpty(clientMerchantAudit.getStartOrgLevel())){
				String startOrgLevel=clientMerchantAudit.getStartOrgLevel();
				
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
			if(Checker.isNotEmpty(clientMerchantAudit.getEndOrgLevel())){
				String endOrgLevel=clientMerchantAudit.getEndOrgLevel();
				
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
			result=clientMerchantAudit;
			result.setStartAuditOrgCode(startAuditOrgCode);
			result.setEndAuditOrgCode(endAuditOrgCode);
			
		}catch (Exception e) { 
			LogCvt.error("根据clientId+orgCode获取商户审核配置信息ClientMerchantAudit失败，原因:" + e.getMessage(), e); 
		} 
		return result; 
		
	}
	

    /**
     * 查询 ClientMerchantAudit
     * @param clientMerchantAudit
     * @return List<ClientMerchantAudit>    结果集合 
     */
	@Override
	public List<ClientMerchantAudit> findClientMerchantAudit(ClientMerchantAudit clientMerchantAudit) {

		List<ClientMerchantAudit> result = new ArrayList<ClientMerchantAudit>(); 
		SqlSession sqlSession = null;
		ClientMerchantAuditMapper clientMerchantAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMerchantAuditMapper = sqlSession.getMapper(ClientMerchantAuditMapper.class);

			result = clientMerchantAuditMapper.findClientMerchantAudit(clientMerchantAudit); 
		} catch (Exception e) { 
			LogCvt.error("查询ClientMerchantAudit失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 ClientMerchantAudit
     * @param page
     * @param clientMerchantAudit
     * @return Page<ClientMerchantAudit>    结果集合 
     */
	@Override
	public Page<ClientMerchantAudit> findClientMerchantAuditByPage(Page<ClientMerchantAudit> page, ClientMerchantAudit clientMerchantAudit) {

		List<ClientMerchantAudit> result = new ArrayList<ClientMerchantAudit>(); 
		SqlSession sqlSession = null;
		ClientMerchantAuditMapper clientMerchantAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMerchantAuditMapper = sqlSession.getMapper(ClientMerchantAuditMapper.class);

			result = clientMerchantAuditMapper.findByPage(page, clientMerchantAudit); 
			page.setResultsContent(result);
		} catch (Exception e) { 
			LogCvt.error("分页查询ClientMerchantAudit失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	


}