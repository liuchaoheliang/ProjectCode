package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.BankUserResourceMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BankResourceMapper;
import com.froad.db.mysql.mapper.BankRoleMapper;
import com.froad.enums.OrgLevelEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankRoleLogic;
import com.froad.po.BankOperator;
import com.froad.po.BankResource;
import com.froad.po.BankRole;
import com.froad.po.Org;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: BankRoleLogic.java</p>
 * <p>Description: 描述 </p> 银行角色表Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BankRoleLogicImpl implements BankRoleLogic {


    /**
     * 增加 BankRole
     * @param bankRole
     * @return Long    主键ID
     */
	@Override
	public Long addBankRole(BankRole bankRole, List<Long> resourceIds)  throws FroadServerException, Exception{

		SqlSession sqlSession = null;
		BankRoleMapper bankRoleMapper = null;
		BankResourceMapper bankResourceMapper = null;
		long bankRoleId=0l;
		try { 
			//一个clientId只能允许一个管理员roleid
			if(bankRole.getTag().equals("0")){
				//判断是否已添加管理员角色
				BankRole find = new BankRole();
				find.setClientId(bankRole.getClientId());
				find.setTag("0");
				find.setIsDelete(false);
				
				List<BankRole> roles = this.findBankRole(find);
				if(Checker.isNotEmpty(roles)){
					throw new FroadServerException("管理员角色已存在，不允许添加！");
				}
			}
			
			//验证一个clientId下角色名称唯一性
			BankRole find = new BankRole();
			find.setClientId(bankRole.getClientId());
			find.setRoleName(bankRole.getRoleName());
			find.setIsDelete(false);
			List<BankRole> roles = this.findBankRole(find);
			if(Checker.isNotEmpty(roles)){
				throw new FroadServerException("角色名已存在！");
			}
			
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);
			//设置是否删除
			bankRole.setIsDelete(false);
			//设置是否有效
			bankRole.setStatus(true);
			
			bankRoleMapper.addBankRole(bankRole); 
			
			
			/**********************操作Mongo**********************/
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);
			//查出所有资源对象
			List<BankResource> bankResources = bankResourceMapper.findBankResourceByList(resourceIds);
			if(Checker.isNotEmpty(bankResources)){
				//添加到Mongo中
				boolean result=BankUserResourceMongo.addBankUserResource(bankRole.getClientId(), bankRole.getId(), bankResources);
				//成功再做提交
				if(result){
					bankRoleId=bankRole.getId();
					sqlSession.commit(true); 
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
		return bankRoleId; 

	}


    /**
     * 删除 BankRole
     * @param bankRole
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBankRole(BankRole bankRole)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BankRoleMapper bankRoleMapper = null;
		try {
			//判断角色下是否有未删除的操作员
			BankOperator bankOperator = new BankOperator();
			bankOperator.setRoleId(bankRole.getId());
			bankOperator.setIsDelete(false);
			List<BankOperator> bankOperatorList = new BankOperatorLogicImpl().findBankOperator(bankOperator);
			if(Checker.isNotEmpty(bankOperatorList)){
				throw new FroadServerException("已经关联操作员的角色，不允许删除！");
			}
			
			
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);

			result = bankRoleMapper.deleteBankRole(bankRole); 
			
			/**********************操作Mongo**********************/
			//根据主键id查出bankRole对象
			bankRole=this.findBankRoleById(bankRole.getId());
			if(Checker.isNotEmpty(bankRole)){
				result=BankUserResourceMongo.deleteBankUserResource(bankRole.getClientId(), bankRole.getId());
				if(result){
					sqlSession.commit(true);
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
     * 修改 BankRole
     * @param bankRole
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBankRole(BankRole bankRole, List<Long> resourceIds)  throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		BankRoleMapper bankRoleMapper = null;
		BankResourceMapper bankResourceMapper = null;
		try { 
			//若修改角色名称，需判断角色名称不能相同
			if(Checker.isNotEmpty(bankRole.getRoleName())){
				BankRole find = this.findBankRoleById(bankRole.getId());
				//修复，a修改成A的情况，故其转成大写比较
				if(!find.getRoleName().toUpperCase().equals(bankRole.getRoleName().toUpperCase())){
					//验证一个clientId下角色名称唯一性
					find = new BankRole();
					find.setClientId(bankRole.getClientId());
					find.setRoleName(bankRole.getRoleName());
					find.setIsDelete(false);
					List<BankRole> roles = this.findBankRole(find);
					if(Checker.isNotEmpty(roles)){
						throw new FroadServerException("角色名已存在！");
					}
				}
			}
			
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);
			bankResourceMapper = sqlSession.getMapper(BankResourceMapper.class);
			//值不为空才更新
			if(Checker.isNotEmpty(bankRole.getStatus()) 
					|| Checker.isNotEmpty(bankRole.getRemark())
					|| Checker.isNotEmpty(bankRole.getRoleName())){
				result = bankRoleMapper.updateBankRole(bankRole); 
				sqlSession.commit(true);
			}
			
			//查出所有资源对象
			List<BankResource> bankResources = bankResourceMapper.findBankResourceByList(resourceIds);
			
			/**********************操作Mongo**********************/
			if(Checker.isNotEmpty(bankResources)){
				result=BankUserResourceMongo.updateBankUserResource(bankResources.get(0).getClientId(), bankRole.getId(), bankResources);
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
     * 根据id查询 单个BankRole
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	@Override
	public BankRole findBankRoleById(Long roleId) {
		
		BankRole resultBankRole =new BankRole();
		//过滤条件BankRole
		BankRole bankRole =new BankRole();
		bankRole.setId(roleId);
		bankRole.setStatus(true);
		bankRole.setIsDelete(false);
		List<BankRole>  bankRoles = this.findBankRole(bankRole);
		if(bankRoles!=null && bankRoles.size()==1){
			resultBankRole=bankRoles.get(0);
			return resultBankRole;
		}else{
			return new BankRole();
		}
		
	}
	
	

    /**
     * 查询 BankRole
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	@Override
	public List<BankRole> findBankRole(BankRole bankRole) {

		List<BankRole> result = new ArrayList<BankRole>(); 
		SqlSession sqlSession = null;
		BankRoleMapper bankRoleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);

			result = bankRoleMapper.findBankRole(bankRole); 
		} catch (Exception e) { 
			LogCvt.error("查询BankRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


	/**
    * 查询 BankRole(只查询当前机构下的，不查下属机构对应的角色)
    * @param bankRole
    * @return List<BankRole>    结果集合 
    */
	@Override
	public List<BankRole> findBankRoleInCurrentOrg(BankRole bankRole) {

		List<BankRole> result = new ArrayList<BankRole>(); 
		SqlSession sqlSession = null;
		BankRoleMapper bankRoleMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);

			result = bankRoleMapper.findBankRoleInCurrentOrg(bankRole); 
		} catch (Exception e) { 
			LogCvt.error("查询BankRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
    /**
     * 分页查询 BankRole
     * @param page
     * @param bankRole
     * @return List<BankRole>    结果集合 
     */
	@Override
	public Page<BankRole> findBankRoleByPage(Page<BankRole> page, BankRole bankRole) {

		List<BankRole> result = new ArrayList<BankRole>(); 
		SqlSession sqlSession = null;
		BankRoleMapper bankRoleMapper = null;
		try { 
			
			//判断orgCode级别，若为一级，减少多条件查询
			if(Checker.isNotEmpty(bankRole)){
				if(Checker.isNotEmpty(bankRole.getClientId()) && Checker.isNotEmpty(bankRole.getOrgCode())){
					Org org = new CommonLogicImpl().getOrgByOrgCode(bankRole.getOrgCode(),bankRole.getClientId());
					
					if(Checker.isEmpty(org)){
						return new Page<BankRole>();
					}
					
					//若一级则清空orgCode减少连表查询
					if(OrgLevelEnum.orgLevel_one.getLevel().equals(org.getOrgLevel())){
						bankRole.setOrgCode(null);
					}
				}
			}
			
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankRoleMapper = sqlSession.getMapper(BankRoleMapper.class);

			result = bankRoleMapper.findByPage(page, bankRole); 
			page.setResultsContent(result);
			
			
		} catch (Exception e) { 
			LogCvt.error("分页查询BankRole失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}


}