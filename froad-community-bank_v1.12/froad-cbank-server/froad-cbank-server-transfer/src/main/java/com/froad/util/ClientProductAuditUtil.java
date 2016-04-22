package com.froad.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.chonggou.entity.ClientProductAudit;
import com.froad.db.chonggou.mappers.ClientProductAuditMapper;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.db.mysql.MyBatisManager;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.Org;
import com.froad.util.Checker;

public class ClientProductAuditUtil {
	
	
	/**
     * 查询 ClientProductAudit
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @param productType 商品类型(1团购，2预售，3名优特惠，4在线积分兑换，5网点礼品)
     * @return ClientProductAudit   商品审核配置对象
     */
	public ClientProductAudit findClientProductAuditByOrgCode(String clientId, String orgCode,String productType) {

		ClientProductAudit result = new ClientProductAudit();
		try{
			/**********************操作MySQL数据库**********************/
			
			//根据clientId+orgCode查询出机构对象
			Org org = findOrgById(clientId, orgCode);
			if(Checker.isEmpty(org)){
				return null;
			}
			
			
			String orgLevel=org.getOrgLevel();
			//根据clientId+orgLevel+productType获取商品审核配置信息
			ClientProductAudit clientProductAudit = new ClientProductAudit();
			clientProductAudit.setClientId(clientId);
			clientProductAudit.setOrgLevel(orgLevel);
			clientProductAudit.setProductType(productType);
			List<ClientProductAudit> auditList = this.findClientProductAudit(clientProductAudit);
			if(Checker.isEmpty(auditList)){
				//一级机构无配置特殊处理
				if(OrgLevelEnum.orgLevel_one.getLevel().equals(orgLevel)){
					if(!org.getNeedReview()){//不需要双人审核返回0，不需要审核，设置为有效
						result.setStartAuditOrgCode("0");
						result.setEndAuditOrgCode("0");
						return result;
					}else{//需要双人审核，返回本身orgCode进行审核操作
						result.setStartAuditOrgCode(orgCode);
						result.setEndAuditOrgCode(orgCode);
						return result;
					}
				}else{
					LogCvt.error(clientId+":"+orgLevel+":"+productType+":"+"商品审核配置无符合记录");
					return null;
				}
				
			}
			clientProductAudit=auditList.get(0);//clientId+orgLevel+productType只能查出一条符合记录
			
			//起始机构级别
			String startOrgLevel=clientProductAudit.getStartOrgLevel();
			//终审机构级别
			String endOrgLevel=clientProductAudit.getEndOrgLevel();
			//获取起始机构编号+终审机构编号
			String startAuditOrgCode="";
			String endAuditOrgCode="";
			if(OrgLevelEnum.orgLevel_four.getLevel().equals(startOrgLevel)){
				startAuditOrgCode=org.getOrgCode();
			}else if(OrgLevelEnum.orgLevel_three.getLevel().equals(startOrgLevel)){
				startAuditOrgCode=org.getCountyAgency();
			}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(startOrgLevel)){
				startAuditOrgCode=org.getCityAgency();
			}else if(OrgLevelEnum.orgLevel_one.getLevel().equals(startOrgLevel)){
				startAuditOrgCode=org.getProvinceAgency();
			}
			if(OrgLevelEnum.orgLevel_four.getLevel().equals(endOrgLevel)){
				endAuditOrgCode=org.getOrgCode();
			}else if(OrgLevelEnum.orgLevel_three.getLevel().equals(endOrgLevel)){
				endAuditOrgCode=org.getCountyAgency();
			}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(endOrgLevel)){
				endAuditOrgCode=org.getCityAgency();
			}else if(OrgLevelEnum.orgLevel_one.getLevel().equals(endOrgLevel)){
				endAuditOrgCode=org.getProvinceAgency();
			}
			//若没获取到值，则起始/终审机构编号是自己本身
			if(Checker.isEmpty(startAuditOrgCode)){
				startAuditOrgCode=orgCode;
			}
			if(Checker.isEmpty(endAuditOrgCode)){
				endAuditOrgCode=orgCode;
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
	public Org findOrgById(String clientId,String orgCode) {
		SqlSession sqlSession = null;
		OrgMapper orgMapper = null;
		Org org = new Org();
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgMapper.class);
			
			//根据orgCode和clientId查询机构对象
			org.setOrgCode(orgCode);
			org.setClientId(clientId);
			org.setIsEnable(true);
			org = orgMapper.findOrgById(org); 
		} catch (Exception e) { 
			LogCvt.error("根据Id查询Org失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return org; 
	}
}

