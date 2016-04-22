/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossUsers   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossUserImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;







import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossUserLogic;
import com.froad.logic.impl.BossUserLogicImpl;
import com.froad.po.BossUser;
import com.froad.po.BossUserCheckRes;
import com.froad.po.BossUserLoginRes;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossUserService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.BossUserCheckVoRes;
import com.froad.thrift.vo.BossUserLoginVoRes;
import com.froad.thrift.vo.BossUserPageVoRes;
import com.froad.thrift.vo.BossUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: BossUserImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossUserServiceImpl extends BizMonitorBaseService implements BossUserService.Iface {
	private BossUserLogic bossUserLogic = new BossUserLogicImpl();
	public BossUserServiceImpl() {}
	public BossUserServiceImpl(String name,String version) {
		super(name, version);
	}


	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,BossUser bossUser){
		if(bossUser.getUsername() == null || "".equals(bossUser.getUsername())){
			LogCvt.error("添加BossUserVo失败,原因:登陆名Username不能为空!");
			resultVo.setResultDesc("添加机构用户失败,原因:登陆名Username不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossUser.getPassword() == null || "".equals(bossUser.getPassword())){
			LogCvt.error("添加BossUserVo失败,原因:登陆密码Password不能为空!");
			resultVo.setResultDesc("添加机构用户失败,原因:登陆密码Password不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossUser.getRoleId() == null || "".equals(bossUser.getRoleId())){
			LogCvt.error("添加BossUserVo失败,原因:角色RoleId不能为空!");
			resultVo.setResultDesc("添加机构用户失败,原因:角色RoleId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
	
    /**
     * 增加 BossUser
     * @param bossUser
     * @return long    主键ID
     */
	@Override
	public AddResultVo addBossUser(OriginVo originVo,BossUserVo bossUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BossUser");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		BossUser bossUser= (BossUser)BeanUtil.copyProperties(BossUser.class, bossUserVo);
		addResultVo=verification(resultVo,addResultVo,bossUser);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(bossUser.getIsEnable() == null){
			bossUser.setIsEnable(true);
		}
		if(bossUser.getIsReset() == null){
			bossUser.setIsReset(false);
		}
		
		
		ResultBean result = bossUserLogic.addBossUser(bossUser);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加机构用户信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 BossUser
     * @param bossUser
     * @return boolean    
     */
	@Override
	public ResultVo deleteBossUser(OriginVo originVo,BossUserVo bossUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BossUser");
		BossUser bossUser= (BossUser)BeanUtil.copyProperties(BossUser.class, bossUserVo);
		ResultBean result = bossUserLogic.deleteBossUser(bossUser);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除机构用户成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 BossUser
     * @param bossUser
     * @return boolean    
     */
	@Override
	public ResultVo updateBossUser(OriginVo originVo,BossUserVo bossUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BossUser");
		BossUser bossUser= (BossUser)BeanUtil.copyProperties(BossUser.class, bossUserVo);
		ResultBean result = bossUserLogic.updateBossUser(bossUser);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改机构用户成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 BossUser
     * @param bossUser
     * @return List<BossUserVo>
     */
	@Override
	public List<BossUserVo> getBossUser(BossUserVo bossUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询BossUser");
		BossUser bossUser= (BossUser)BeanUtil.copyProperties(BossUser.class, bossUserVo);
		List<BossUser> bossUserList = bossUserLogic.findBossUser(bossUser);
		List<BossUserVo> bossUserVoList = new ArrayList<BossUserVo>();
		for (BossUser po : bossUserList) {
			BossUserVo vo= (BossUserVo)BeanUtil.copyProperties(BossUserVo.class, po);
			bossUserVoList.add(vo);
		}
		return bossUserVoList;
	}



    /**
     * 分页查询 BossUser
     * @param bossUser
     * @return BossUserPageVoRes
     */
	@Override
	public BossUserPageVoRes getBossUserByPage(PageVo pageVo, BossUserVo bossUserVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询BossUser");
		
		Page<BossUser> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		BossUser bossUser = (BossUser)BeanUtil.copyProperties(BossUser.class, bossUserVo);
		page = bossUserLogic.findBossUserByPage(page, bossUser);

		BossUserPageVoRes bossUserPageVoRes = new BossUserPageVoRes();
		List<BossUserVo> bossUserVoList = new ArrayList<BossUserVo>();
		for (BossUser po : page.getResultsContent()) {
			BossUserVo vo = (BossUserVo)BeanUtil.copyProperties(BossUserVo.class, po);
			bossUserVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bossUserPageVoRes.setPage(pageVo);
		bossUserPageVoRes.setBossUserVoList(bossUserVoList);
		
		 
		return bossUserPageVoRes;
	}
	
	/**
     * 是否存在username
     * @return bool
     * 
     * @param username
     */
	@Override
	public boolean isExistUsername(String username) throws TException {
		// TODO Auto-generated method stub
		return bossUserLogic.isExistUsername(username);
	}
	
	/**
     * * boss 用户登录
     *    *
     * * @return BossUserLoginVoRes
     * 
     * @param originVo
     * @param username
     * @param password
     */
	@Override
	public BossUserLoginVoRes login(OriginVo originVo, String username,
			String password) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("BossUser登录");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("Boss用户登录");
		com.froad.util.LogUtils.addLog(originVo);
		
		BossUserLoginRes bossUserLoginRes = bossUserLogic.login(username, password, originVo.getOperatorIp());
		LogCvt.info("登录结果 - "+JSON.toJSONString(bossUserLoginRes));
		BossUserLoginVoRes bossUserLoginVoRes = (BossUserLoginVoRes)BeanUtil.copyProperties(BossUserLoginVoRes.class, bossUserLoginRes);
		
		return bossUserLoginVoRes;
	}
	
	/**
     * boss 用户登出
     * 
     * @return boolean
     * 
     * @param originVo
     * @param token
     */
	@Override
	public boolean logout(OriginVo originVo, String token) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("BossUser用户登出");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("Boss用户退出登录");
		com.froad.util.LogUtils.addLog(originVo);
		
		Boolean result = bossUserLogic.logout(token);
		return null == result ? false : result;
	}
	
	/**
     * 校验 token
     * 
     * @return BossUser
     * 
     * @param originVo
     * @param token
     * @param userId
     */
	@Override
	public BossUserCheckVoRes tokenCheck(OriginVo originVo, String token, long userId)
			throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("校验 token");
		//添加操作日志记录
		if(StringUtils.isBlank(originVo.getDescription()))
			originVo.setDescription("Boss用户校验 token");
		com.froad.util.LogUtils.addLog(originVo);
		
		BossUserCheckRes bossUserCheckRes = bossUserLogic.tokenCheck(token, userId);
		BossUserCheckVoRes result = (BossUserCheckVoRes)BeanUtil.copyProperties(BossUserCheckVoRes.class, bossUserCheckRes);
		return result;
	}


	
}
