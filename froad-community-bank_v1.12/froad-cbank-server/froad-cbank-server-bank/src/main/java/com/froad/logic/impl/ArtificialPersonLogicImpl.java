package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.BankOperatorMapper;
import com.froad.db.redis.BankOperatorRedis;
import com.froad.enums.BankType;
import com.froad.enums.OrgLevelEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ArtificialPersonLogic;
import com.froad.logic.BankOperatorLogic;
import com.froad.logic.BankRoleLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrgLogic;
import com.froad.po.BankOperator;
import com.froad.po.BankRole;
import com.froad.po.Org;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: ArtificialPersonLogicImpl.java</p>
 * <p>Description: 描述 </p> 法人行社管理员Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月17日
 */
public class ArtificialPersonLogicImpl implements ArtificialPersonLogic {

	/**
     * 批量生成法人行社管理员接口(一个法人行社orgCode只能生成一个管理员用户)
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @param prefix 登录名前缀
     * @return 批量生成失败的orgCode及名称(orgCode:orgName;)  生成成功返回OK  审核异常返回ERROR 
     */
	@Override
	public String addArtificialPerson(String clientId, List<String> orgCodes,String defaultPassword,String prefix) throws FroadServerException, Exception{
		/**
		 * 1.验证orgCode下是否有添加管理员
		 * 2.新增
		 */
		BankRoleLogic bankRoleLogic = new BankRoleLogicImpl();
		StringBuffer result = new StringBuffer();
		try{
			//1.根据clientId查出该客户端下管理员角色
			BankRole bankRole =new BankRole();
			bankRole.setClientId(clientId);
			bankRole.setTag("0");
			bankRole.setStatus(true);
			bankRole.setIsDelete(false);
			List<BankRole> roles = bankRoleLogic.findBankRole(bankRole);
			//新增角色中，一个clientId只能有一个默认的管理员角色
			if(roles!=null && roles.size()==1){
				bankRole=roles.get(0);
			}else{
				throw new FroadServerException(clientId+"客户端下无配置默认管理员角色");
			}
			
			BankOperator bankOperator=null;
			CommonLogic commonLogic = new CommonLogicImpl();
			BankOperatorLogic bankOperatorLogic = new BankOperatorLogicImpl();
			List<BankOperator> operators=new ArrayList<BankOperator>();
			
			//批量新增
			Long userId=0l;
			
			//2.验证roleId、clientId、orgCode下是否只有一个管理员用户
			for(String orgCode:orgCodes){
				userId=0l;
				// 判断集合机构是否已存在管理员
				bankOperator = new BankOperator();
				bankOperator.setRoleId(bankRole.getId());//法人行社管理员角色
				bankOperator.setOrgCode(orgCode);
				bankOperator.setClientId(clientId);
				operators=bankOperatorLogic.findBankOperator(bankOperator);
				//一个clientId+orgCode 下只允许一个管理员
				if(CollectionUtils.isNotEmpty(operators)){
					BankOperator rBopt = operators.get(0);
					//已添加过的信息，不需要重新insert，直接update状态改为有效
					if(!rBopt.getStatus() || rBopt.getIsDelete()){
						LogCvt.info("[机构码:"+orgCode+"]不是第一次添加法人行社管理员，更新状态为有效");
						
						//修改对象参数值
						BankOperator param = new BankOperator();
						param.setId(rBopt.getId());
						param.setStatus(true);
						param.setIsDelete(false);
						param.setPassword(defaultPassword);//密码重新设置为有效
						param.setIsReset(false);
						param.setCreateTime(new Date());//获得系统时间
						
						SqlSession sqlSession = null;
						BankOperatorMapper bankOperatorMapper = null;
						try{
							/**********************操作MySQL数据库**********************/
							sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
							bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
							bankOperatorMapper.updateBankOperatorIsDelete(param);
							sqlSession.commit();
							//新增到redis缓存，重新查下
							commonLogic.getBankOperatorById(rBopt.getClientId(), rBopt.getId());
						}catch (Exception e) { 
							if(null != sqlSession)  
								sqlSession.rollback(true);  
							throw e;
						} finally { 
							if(null != sqlSession)  
								sqlSession.close();  
						} 
					}else{
						LogCvt.info("[机构码:"+orgCode+"]已添加法人行社管理员，不能继续添加");
					}
					continue;
				}
				
				//添加管理员
				BankOperator add = new BankOperator();
				add.setClientId(clientId);
				add.setUsername(prefix+orgCode);//将AHRCU+orgCode作为登录名
				add.setPassword(defaultPassword);
				add.setOrgCode(orgCode);
				add.setRoleId(bankRole.getId());
				
				userId=bankOperatorLogic.addBankOperator(add);
				if(userId == 0l){
					result.append("[机构码:"+orgCode+"]添加法人行社管理员失败;");
				}
				LogCvt.info("[机构码:"+orgCode+"]添加法人行社管理员"+(userId != 0l ? "成功": "失败"));
			}
			
			if(result.length()!=0){
				return result.toString()+"添加异常";
			}else{
				result = result.append("OK");
			}
		}catch (FroadServerException e) { 
			throw e;
		}catch (Exception e) { 
			throw e;
		}
		return result.toString();
	}

	


	/**
     * 法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 法人行社org对象
     */
	@Override
	public List<Org> findArtificialPerson(String clientId,String orgCode) {
		List<Org> orgs = new ArrayList<Org>();
		try {
			OrgLogic orgLogic = new OrgLogicImpl();
			
			//根据orgCode+clientId查询org对象
			Org self=new CommonLogicImpl().getOrgByOrgCode(orgCode, clientId);
			if(Checker.isEmpty(self)){
				return orgs;
			}
			//判断当前登录orgCode级别是否为1
			if(!OrgLevelEnum.orgLevel_one.getLevel().equals(self.getOrgLevel())){
				LogCvt.error("当前登录管理员级别不是一级用户所属级别");
				return orgs;
			}
			
			//根据bankType不同获取不同级别的法人行社信息
			BankType bankType = BankType.getByType(self.getBankType());
			String orgLevel ="0";
			switch (bankType) {
			case province_city_county:
				orgLevel=OrgLevelEnum.orgLevel_two.getLevel();
				LogCvt.info("客户端号："+self.getClientId()+"银行类型："+BankType.province_city_county.getDescribe());
				break;
			case province_city:
				orgLevel=OrgLevelEnum.orgLevel_two.getLevel();
				LogCvt.info("客户端号："+self.getClientId()+"银行类型："+BankType.province_city.getDescribe());
				break;
			case city:
				orgLevel=OrgLevelEnum.orgLevel_one.getLevel();
				LogCvt.info("客户端号："+self.getClientId()+"银行类型："+BankType.city.getDescribe());
				break;
			default:
				
				LogCvt.info("客户端号："+self.getClientId()+"银行类型："+BankType.county.getDescribe());
				break;
			}
			
			if(orgLevel.equals("0")){
				return orgs;//没有法人行社列表，返回空
			}
			
			//过滤条件查询法人行社orgCode列表
			self = new Org();
			self.setClientId(clientId);
			self.setOrgLevel(orgLevel);
			self.setIsEnable(true);
			orgs = orgLogic.findOrg(self);
			
			
		} catch (Exception e) {
			LogCvt.error("法人行社列表查询异常 原因"+e.getMessage(), e);
		} 
		return orgs;
	}

	
	/**
     * 已生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 已生成的法人行社org对象
     */
	@Override
	public List<Org> findArtificialPersonByAdd(String clientId,String orgCode) {
		List<Org> orgs = new ArrayList<Org>();
		try {
			BankRoleLogic bankRoleLogic = new BankRoleLogicImpl();//角色Logic
			BankOperatorLogic bankOperatorLogic = new BankOperatorLogicImpl();//银行用户Logic
			
			BankRole findManager = new BankRole();
			findManager.setClientId(clientId);
			findManager.setTag("0"); //管理员
			findManager.setStatus(true); 
			findManager.setIsDelete(false);
			List<BankRole> managerRoles = bankRoleLogic.findBankRole(findManager);
			if(CollectionUtils.isNotEmpty(managerRoles)){
				BankRole managerRole = managerRoles.get(0); //一个机构下只能有一个管理员角色
				
				//根据clientId+roleId获取所有管理员信息,进而匹配orgCode是否相同
				BankOperator manager = new BankOperator();
				manager.setClientId(clientId);
				manager.setRoleId(managerRole.getId());//管理员角色id
//				manager.setStatus(true);//有效可用(禁用掉的用户也是已添加的，所以不能过滤掉)
				manager.setIsDelete(false);
				List<BankOperator> managers = bankOperatorLogic.findBankOperator(manager); //管理员角色的银行用户信息
				List<Org> artificials = this.findArtificialPerson(clientId, orgCode); //查出登录用户所属法人行社列表
				if(CollectionUtils.isNotEmpty(artificials)){
					for(Org o : artificials){
						for(BankOperator managerOperator : managers){
							if(managerOperator.getOrgCode().equals(o.getOrgCode())){
								orgs.add(o);
								continue;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LogCvt.error("已生成管理员的法人行社列表查询异常 原因"+e.getMessage(), e);
			
		} 
		
		return orgs;
	}

	
	/**
     * 未生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 未生成的法人行社org对象
     */
	@Override
	public List<Org> findArtificialPersonByNotAdd(String clientId,String orgCode) {
		List<Org> orgs = new ArrayList<Org>();
		try {
			BankRoleLogic bankRoleLogic = new BankRoleLogicImpl();//角色Logic
			BankOperatorLogic bankOperatorLogic = new BankOperatorLogicImpl();//银行用户Logic
			
			BankRole findManager = new BankRole();
			findManager.setClientId(clientId);
			findManager.setTag("0"); //管理员
			findManager.setStatus(true); 
			findManager.setIsDelete(false);
			List<BankRole> managerRoles = bankRoleLogic.findBankRole(findManager);
			if(CollectionUtils.isNotEmpty(managerRoles)){
				BankRole managerRole = managerRoles.get(0); //一个机构下只能有一个管理员角色
				
				//根据clientId+roleId获取所有管理员信息,进而匹配orgCode是否相同
				BankOperator manager = new BankOperator();
				manager.setClientId(clientId);
				manager.setRoleId(managerRole.getId());//管理员角色id
//				manager.setStatus(true);//有效可用(禁用掉的用户也是已添加的，所以不能过滤掉)
				manager.setIsDelete(false);
				List<BankOperator> managers = bankOperatorLogic.findBankOperator(manager); //管理员角色的银行用户信息
				List<Org> artificials = this.findArtificialPerson(clientId, orgCode); //查出登录用户所属法人行社列表
				if(CollectionUtils.isNotEmpty(artificials)){
					for(Org o : artificials){
						boolean isAdd = false;
						for(BankOperator managerOperator : managers){
							if(managerOperator.getOrgCode().equals(o.getOrgCode())){
								isAdd = true;
								continue;
							}
						}
						if(!isAdd){
							orgs.add(o);
						}
					}
				}
			}
			
		} catch (Exception e) {
			LogCvt.error("未生成管理员的法人行社列表查询异常 原因"+e.getMessage(), e);
			
		} 
		
		return orgs;
	}
	
	
	/**
     * 批量删除法人行社管理员账号
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteArtificialPerson(String clientId, List<String> orgCodes) throws FroadServerException, Exception{
		//批量处理放在mapper中内部处理
		Boolean result = false; 
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			//查出clientId下默认的管理员角色
			BankRole bankRole =new BankRole();
			bankRole.setClientId(clientId);
			bankRole.setTag("0");
			bankRole.setStatus(true);
			bankRole.setIsDelete(false);
			List<BankRole> roles = new BankRoleLogicImpl().findBankRole(bankRole);
			//一个clientId只能有一个默认的管理员角色
			if(roles!=null && roles.size()==1){
				bankRole=roles.get(0);
			}else{
				throw new FroadServerException(clientId+"客户端下无配置默认管理员角色");
			}
			
			//批量sql处理
			result=bankOperatorMapper.deleteBankOperatorByList(clientId, orgCodes, bankRole.getId());
			
			
			//根据clientId+roleId获取所有管理员信息，进而匹配orgCode是否相同进行后续处理
			BankOperator manager = new BankOperator();
			manager.setClientId(clientId);
			manager.setRoleId(bankRole.getId());//管理员角色id
			manager.setStatus(true);//有效可用
			manager.setIsDelete(false);
			List<BankOperator> managers = new BankOperatorLogicImpl().findBankOperator(manager); //管理员角色的银行用户信息
			if(CollectionUtils.isNotEmpty(managers)){
				for(BankOperator managerOperator : managers){
					for(String orgCode:orgCodes){
						if(managerOperator.getOrgCode().equals(orgCode)){//相同orgCode才删除管理员缓存
							//删除缓存
							result=BankOperatorRedis.del_cbbank_bank_user_client_id_user_id(managerOperator);
							result=BankOperatorRedis.del_cbbank_bank_user_login_client_id_user_id(managerOperator);
						}
					}
				}
			}
			
			//先提交后续设置缓存需要更新后的值
			if(result){
				sqlSession.commit(true);
			}
			
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		}catch (Exception e) { 
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
     * 批量重置法人行社管理员密码
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @return Boolean    是否成功
     * 
     */
	@Override
	public Boolean updateArtificialPerson(String clientId, List<String> orgCodes,String defaultPassword) throws FroadServerException, Exception{

		//批量处理放在mapper中内部处理
		Boolean result = false; 
		SqlSession sqlSession = null;
		BankOperatorMapper bankOperatorMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperatorMapper = sqlSession.getMapper(BankOperatorMapper.class);
			
			//查出clientId下默认的管理员角色
			BankRole bankRole =new BankRole();
			bankRole.setClientId(clientId);
			bankRole.setTag("0");
			bankRole.setStatus(true);
			bankRole.setIsDelete(false);
			List<BankRole> roles = new BankRoleLogicImpl().findBankRole(bankRole);
			//一个clientId只能有一个默认的管理员角色
			if(roles!=null && roles.size()==1){
				bankRole=roles.get(0);
			}else{
				throw new FroadServerException(clientId+"客户端下无配置默认管理员角色");
			}
			
			//批量sql处理
			result=bankOperatorMapper.updateBankOperatorByList(defaultPassword, clientId, orgCodes, bankRole.getId());
			//先提交后续设置缓存需要更新后的值
			if(result){
				sqlSession.commit(true);
			}
			
			//根据clientId+roleId获取所有管理员信息，进而匹配orgCode是否相同进行后续处理
			BankOperator manager = new BankOperator();
			manager.setClientId(clientId);
			manager.setRoleId(bankRole.getId());//管理员角色id
			manager.setStatus(true);//有效可用
			manager.setIsDelete(false);
			List<BankOperator> managers = new BankOperatorLogicImpl().findBankOperator(manager); //管理员角色的银行用户信息
			if(CollectionUtils.isNotEmpty(managers)){
				for(BankOperator managerOperator : managers){
					for(String orgCode:orgCodes){
						if(managerOperator.getOrgCode().equals(orgCode)){//相同orgCode才修改管理员缓存
							//重新设置Redis缓存
							result=BankOperatorRedis.set_cbbank_bank_user_client_id_user_id(managerOperator);
						}
					}
				}
			}
			
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		}catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


	
	
	
	
	
}