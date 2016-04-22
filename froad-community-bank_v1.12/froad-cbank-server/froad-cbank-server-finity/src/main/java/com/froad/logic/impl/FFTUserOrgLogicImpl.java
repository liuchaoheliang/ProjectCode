package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.FFTUserOrgMapper;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.FFTUserOrgLogic;
import com.froad.po.FFTUserOrg;

/**
 * 
 * <p>@Title: FFTOrgLogicImpl.java</p>
 * <p>Description: 描述 </p> 组织Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public class FFTUserOrgLogicImpl implements FFTUserOrgLogic {

	/**
     * 新增 FFTUserOrg
     * @param FFTUserOrg
     * @return Long    主键ID
     */
	@Override
	public Long addFFTUserOrg(FFTUserOrg fftUserOrg)throws FroadServerException, Exception {
		return null;
	}

	/**
     * 修改 FFTUserOrg
     * @param FFTUserOrg
     * @return Long    主键ID
     */
	@Override
	public Boolean updateFFTUserOrg(FFTUserOrg fftUserOrg) throws FroadServerException, Exception {
		return null;
	}

	/**
     * 删除用户组织 FFTUserOrg
     * @param userId
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteFFTUserOrg(Long userId) throws FroadServerException,Exception {
		Boolean result = false;
		SqlSession sqlSession = null;
		FFTUserOrgMapper fftuserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftuserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);

			result = fftuserOrgMapper.deleteFFTUserOrgByUserId(userId);
			
			if (result) { // 删除成功
				sqlSession.commit(true);
			} else { // 删除失败
				sqlSession.rollback(true); 
			}
			
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
     * 用户所属组织列表
     * @param userId 用户Id
     * @return List<String>    orgId结果集合 
     */
	@Override
	public List<String> findFFTUserOrgByUserId(Long userId) throws Exception {
		List<String> orgIds = new ArrayList<String>(); 
		
		List<FFTUserOrg> result = new ArrayList<FFTUserOrg>(); 
		SqlSession sqlSession = null;
		FFTUserOrgMapper fftUserOrgMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			fftUserOrgMapper = sqlSession.getMapper(FFTUserOrgMapper.class);

			result = fftUserOrgMapper.findFFTUserOrgByUserId(userId);
			for(FFTUserOrg fftUserOrg : result){
				orgIds.add(fftUserOrg.getOrgId());
			}
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return orgIds; 
	}
	
	
}