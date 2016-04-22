/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossRoles   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossRoleImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossRoleLogic;
import com.froad.logic.impl.BossRoleLogicImpl;
import com.froad.po.BossRole;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossRoleService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.BossRolePageVoRes;
import com.froad.thrift.vo.BossRoleVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: BossRoleImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossRoleServiceImpl extends BizMonitorBaseService implements BossRoleService.Iface {
	private BossRoleLogic bossRoleLogic = new BossRoleLogicImpl();
	public BossRoleServiceImpl() {}
	public BossRoleServiceImpl(String name,String version) {
		super(name, version);
	}

	
    /**
     * 增加 BossRole
     * @param bossRole
     * @return long    主键ID
     */
	@Override
	public AddResultVo addBossRole(OriginVo originVo,BossRoleVo bossRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BossRole");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		BossRole bossRole= (BossRole)BeanUtil.copyProperties(BossRole.class, bossRoleVo);
//		addResultVo=verification(resultVo,addResultVo,bossRole);
//		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
//			return addResultVo;
//		}
		if(bossRole.getIsEnable() == null){
			bossRole.setIsEnable(true);
		}
		
		ResultBean result = bossRoleLogic.addBossRole(bossRole);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加角色信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 BossRole
     * @param bossRole
     * @return boolean    
     */
	@Override
	public ResultVo deleteBossRole(OriginVo originVo,BossRoleVo bossRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BossRole");
		BossRole bossRole= (BossRole)BeanUtil.copyProperties(BossRole.class, bossRoleVo);
		ResultBean result = bossRoleLogic.deleteBossRole(bossRole);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除角色成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 BossRole
     * @param bossRole
     * @return boolean    
     */
	@Override
	public ResultVo updateBossRole(OriginVo originVo,BossRoleVo bossRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BossRole");
		BossRole bossRole= (BossRole)BeanUtil.copyProperties(BossRole.class, bossRoleVo);
		ResultBean result = bossRoleLogic.updateBossRole(bossRole);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改角色成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 BossRole
     * @param bossRole
     * @return List<BossRoleVo>
     */
	@Override
	public List<BossRoleVo> getBossRole(BossRoleVo bossRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询BossRole");
		BossRole bossRole= (BossRole)BeanUtil.copyProperties(BossRole.class, bossRoleVo);
		List<BossRole> bossRoleList = bossRoleLogic.findBossRole(bossRole);
		List<BossRoleVo> bossRoleVoList = new ArrayList<BossRoleVo>();
		for (BossRole po : bossRoleList) {
			BossRoleVo vo= (BossRoleVo)BeanUtil.copyProperties(BossRoleVo.class, po);
			bossRoleVoList.add(vo);
		}
		return bossRoleVoList;
	}



    /**
     * 分页查询 BossRole
     * @param bossRole
     * @return BossRolePageVoRes
     */
	@Override
	public BossRolePageVoRes getBossRoleByPage(PageVo pageVo, BossRoleVo bossRoleVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询BossRole");
		
		Page<BossRole> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		BossRole bossRole = (BossRole)BeanUtil.copyProperties(BossRole.class, bossRoleVo);
		page = bossRoleLogic.findBossRoleByPage(page, bossRole);

		BossRolePageVoRes bossRolePageVoRes = new BossRolePageVoRes();
		List<BossRoleVo> bossRoleVoList = new ArrayList<BossRoleVo>();
		for (BossRole po : page.getResultsContent()) {
			BossRoleVo vo = (BossRoleVo)BeanUtil.copyProperties(BossRoleVo.class, po);
			bossRoleVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bossRolePageVoRes.setPage(pageVo);
		bossRolePageVoRes.setBossRoleVoList(bossRoleVoList);
		
		 
		return bossRolePageVoRes;
	}



}
