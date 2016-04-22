/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossOrgs   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossOrgImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossOrgLogic;
import com.froad.logic.impl.BossOrgLogicImpl;
import com.froad.po.BossOrg;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossOrgService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.BossOrgPageVoRes;
import com.froad.thrift.vo.BossOrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: BossOrgImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossOrgServiceImpl extends BizMonitorBaseService implements BossOrgService.Iface {
	private BossOrgLogic bossOrgLogic = new BossOrgLogicImpl();
	public BossOrgServiceImpl() {}
	public BossOrgServiceImpl(String name,String version) {
		super(name, version);
	}

	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,BossOrg bossOrg){
		if(bossOrg.getParentOrgCode() == null || "".equals(bossOrg.getParentOrgCode())){
			LogCvt.error("添加BossOrgVo失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣父级机构码ParentOrgCode不能为空!");
			resultVo.setResultDesc("添加机构表失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣父级机构码ParentOrgCode不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossOrg.getOrgCode() == null || "".equals(bossOrg.getOrgCode())){
			LogCvt.error("添加BossOrgVo失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣机构代码OrgCode不能为空!");
			resultVo.setResultDesc("添加机构表失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣机构代码OrgCode不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossOrg.getOrgName() == null || "".equals(bossOrg.getOrgName())){
			LogCvt.error("添加BossOrgVo失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣机构名OrgName不能为空!");
			resultVo.setResultDesc("添加机构表失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣机构名OrgName不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossOrg.getRoleId() == null || "".equals(bossOrg.getRoleId())){
			LogCvt.error("添加BossOrgVo失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣􏰀􏰁􏰃机构默认角色RoleId不能为空!");
			resultVo.setResultDesc("添加机构表失败,原因:􏱐􏱎􏰀􏰁􏰢􏰣机构默认角色RoleId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
	
    /**
     * 增加 BossOrg
     * @param bossOrg
     * @return long    主键ID
     */
	@Override
	public AddResultVo addBossOrg(OriginVo originVo,BossOrgVo bossOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BossOrg");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		BossOrg bossOrg= (BossOrg)BeanUtil.copyProperties(BossOrg.class, bossOrgVo);
		addResultVo=verification(resultVo,addResultVo,bossOrg);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(bossOrg.getIsEnable() == null){
			bossOrg.setIsEnable(true);
		}
		ResultBean result = bossOrgLogic.addBossOrg(bossOrg);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加机构表信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 BossOrg
     * @param bossOrg
     * @return boolean    
     */
	@Override
	public ResultVo deleteBossOrg(OriginVo originVo,BossOrgVo bossOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BossOrg");
		BossOrg bossOrg= (BossOrg)BeanUtil.copyProperties(BossOrg.class, bossOrgVo);
		ResultBean result = bossOrgLogic.deleteBossOrg(bossOrg);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除机构表成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 BossOrg
     * @param bossOrg
     * @return boolean    
     */
	@Override
	public ResultVo updateBossOrg(OriginVo originVo,BossOrgVo bossOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BossOrg");
		BossOrg bossOrg= (BossOrg)BeanUtil.copyProperties(BossOrg.class, bossOrgVo);
		ResultBean result = bossOrgLogic.updateBossOrg(bossOrg);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改机构表成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 BossOrg
     * @param bossOrg
     * @return List<BossOrgVo>
     */
	@Override
	public List<BossOrgVo> getBossOrg(BossOrgVo bossOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询BossOrg");
		BossOrg bossOrg= (BossOrg)BeanUtil.copyProperties(BossOrg.class, bossOrgVo);
		List<BossOrg> bossOrgList = bossOrgLogic.findBossOrg(bossOrg);
		List<BossOrgVo> bossOrgVoList = new ArrayList<BossOrgVo>();
		for (BossOrg po : bossOrgList) {
			BossOrgVo vo= (BossOrgVo)BeanUtil.copyProperties(BossOrgVo.class, po);
			bossOrgVoList.add(vo);
		}
		return bossOrgVoList;
	}



    /**
     * 分页查询 BossOrg
     * @param bossOrg
     * @return BossOrgPageVoRes
     */
	@Override
	public BossOrgPageVoRes getBossOrgByPage(PageVo pageVo, BossOrgVo bossOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询BossOrg");
		
		Page<BossOrg> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		BossOrg bossOrg = (BossOrg)BeanUtil.copyProperties(BossOrg.class, bossOrgVo);
		page = bossOrgLogic.findBossOrgByPage(page, bossOrg);

		BossOrgPageVoRes bossOrgPageVoRes = new BossOrgPageVoRes();
		List<BossOrgVo> bossOrgVoList = new ArrayList<BossOrgVo>();
		for (BossOrg po : page.getResultsContent()) {
			BossOrgVo vo = (BossOrgVo)BeanUtil.copyProperties(BossOrgVo.class, po);
			bossOrgVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bossOrgPageVoRes.setPage(pageVo);
		bossOrgPageVoRes.setBossOrgVoList(bossOrgVoList);
		
		 
		return bossOrgPageVoRes;
	}


}
