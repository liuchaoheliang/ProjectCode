package com.froad.db.chonggou;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.chonggou.entity.ClientMerchantAudit;
import com.froad.db.chonggou.mappers.ClientMerchantAuditMapper;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.po.Org;
import com.froad.util.Checker;

public class ClientMerchantAuditUtil {
	
	
	/**
     * 根据clientId+orgCode获取商户审核配置信息ClientMerchantAudit
     * @param clientId 客户端Id
     * @param orgCode 机构编号
     * @return ClientMerchantAudit   商户审核配置对象
     */
	public ClientMerchantAudit findClientMerchantAuditByOrgCode(String clientId, String orgCode,SqlSession sqlSession,Org org) {
		ClientMerchantAudit result = new ClientMerchantAudit();
		try{
			/**********************操作MySQL数据库**********************/
			
//			//根据clientId+orgCode查询出机构对象
//			OrgMapper mapper = sqlSession.getMapper(OrgMapper.class);
//			Org org2 = new Org();
//			org2.setClientId(clientId);
//			org2.setOrgCode(orgCode);
//			org2.setIsEnable(true);
			
//			Org org  = mapper.findOrgById(org2);
//			Org org = new OrgLogicImpl().findOrgById(clientId, orgCode);
			if(Checker.isEmpty(org)){
				return null;
			}
			
			String orgLevel=org.getOrgLevel();
			//根据clientId+orgLevel获取商户审核配置信息
			ClientMerchantAudit clientMerchantAudit = new ClientMerchantAudit();
			clientMerchantAudit.setClientId(clientId);
			clientMerchantAudit.setOrgLevel(orgLevel);
			List<ClientMerchantAudit> auditList = this.findClientMerchantAudit(clientMerchantAudit,sqlSession);
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
					LogCvt.error(clientId+":"+orgLevel+"商户审核配置无符合记录");
					return null;
				}
				
			}
			clientMerchantAudit=auditList.get(0);//clientId+orgLevel只能查出一条符合记录
			
			//起始机构级别
			String startOrgLevel=clientMerchantAudit.getStartOrgLevel();
			//终审机构级别
			String endOrgLevel=clientMerchantAudit.getEndOrgLevel();
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
			result=clientMerchantAudit;
			result.setStartAuditOrgCode(startAuditOrgCode);
			result.setEndAuditOrgCode(endAuditOrgCode);
			
		}catch (Exception e) { 
			LogCvt.error("根据clientId+orgCode获取商户审核配置信息ClientMerchantAudit失败，原因:" + e.getMessage(), e); 
		} 
		return result; 
		
	}
	
	
	public List<ClientMerchantAudit> findClientMerchantAudit(ClientMerchantAudit clientMerchantAudit,SqlSession sqlSession) {

		List<ClientMerchantAudit> result = new ArrayList<ClientMerchantAudit>(); 
		ClientMerchantAuditMapper clientMerchantAuditMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			clientMerchantAuditMapper = sqlSession.getMapper(ClientMerchantAuditMapper.class);

			result = clientMerchantAuditMapper.findClientMerchantAudit(clientMerchantAudit); 
		} catch (Exception e) { 
			LogCvt.error("查询ClientMerchantAudit失败，原因:" + e.getMessage(), e); 
		} 
		return result; 

	}


}

