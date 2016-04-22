/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the bossUserOrgs   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: BossUserOrgImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.BossUserOrgLogic;
import com.froad.logic.impl.BossUserOrgLogicImpl;
import com.froad.po.BossUserOrg;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossUserOrgService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.BossUserOrgPageVoRes;
import com.froad.thrift.vo.BossUserOrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: BossUserOrgImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossUserOrgServiceImpl extends BizMonitorBaseService implements BossUserOrgService.Iface {
	private BossUserOrgLogic bossUserOrgLogic = new BossUserOrgLogicImpl();
	public BossUserOrgServiceImpl() {}
	public BossUserOrgServiceImpl(String name, String version) {
		super(name, version);
	}


	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,BossUserOrg bossUserOrg){
		if(bossUserOrg.getUserId() == null || "".equals(bossUserOrg.getUserId())){
			LogCvt.error("添加BossUserOrgVo失败,原因:用户UserId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:用户UserId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossUserOrg.getOrgCode() == null || "".equals(bossUserOrg.getOrgCode())){
			LogCvt.error("添加BossUserOrgVo失败,原因:机构编号OrgCode不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:机构编号OrgCode不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
	
    /**
     * 增加 BossUserOrg
     * @param bossUserOrg
     * @return long    主键ID
     */
	@Override
	public AddResultVo addBossUserOrg(OriginVo originVo,BossUserOrgVo bossUserOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BossUserOrg");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		BossUserOrg bossUserOrg= (BossUserOrg)BeanUtil.copyProperties(BossUserOrg.class, bossUserOrgVo);
		addResultVo=verification(resultVo,addResultVo,bossUserOrg);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		ResultBean result = bossUserOrgLogic.addBossUserOrg(bossUserOrg);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加􏱬􏴫􏱱􏰡􏰶􏸩􏰤􏱬􏴫􏱱􏰡􏰶􏸩􏰤用户关联表信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 BossUserOrg
     * @param bossUserOrg
     * @return boolean    
     */
	@Override
	public ResultVo deleteBossUserOrg(OriginVo originVo,BossUserOrgVo bossUserOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BossUserOrg");
		BossUserOrg bossUserOrg= (BossUserOrg)BeanUtil.copyProperties(BossUserOrg.class, bossUserOrgVo);
		ResultBean result = bossUserOrgLogic.deleteBossUserOrg(bossUserOrg);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除􏱬􏴫􏱱􏰡􏰶􏸩􏰤􏱬􏴫􏱱􏰡􏰶􏸩􏰤用户关联表成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 BossUserOrg
     * @param bossUserOrg
     * @return boolean    
     */
	@Override
	public ResultVo updateBossUserOrg(OriginVo originVo,BossUserOrgVo bossUserOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BossUserOrg");
		BossUserOrg bossUserOrg= (BossUserOrg)BeanUtil.copyProperties(BossUserOrg.class, bossUserOrgVo);
		ResultBean result = bossUserOrgLogic.updateBossUserOrg(bossUserOrg);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改􏱬􏴫􏱱􏰡􏰶􏸩􏰤􏱬􏴫􏱱􏰡􏰶􏸩􏰤用户关联表成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 BossUserOrg
     * @param bossUserOrg
     * @return List<BossUserOrgVo>
     */
	@Override
	public List<BossUserOrgVo> getBossUserOrg(BossUserOrgVo bossUserOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询BossUserOrg");
		BossUserOrg bossUserOrg= (BossUserOrg)BeanUtil.copyProperties(BossUserOrg.class, bossUserOrgVo);
		List<BossUserOrg> bossUserOrgList = bossUserOrgLogic.findBossUserOrg(bossUserOrg);
		List<BossUserOrgVo> bossUserOrgVoList = new ArrayList<BossUserOrgVo>();
		for (BossUserOrg po : bossUserOrgList) {
			BossUserOrgVo vo= (BossUserOrgVo)BeanUtil.copyProperties(BossUserOrgVo.class, po);
			bossUserOrgVoList.add(vo);
		}
		return bossUserOrgVoList;
	}



    /**
     * 分页查询 BossUserOrg
     * @param bossUserOrg
     * @return BossUserOrgPageVoRes
     */
	@Override
	public BossUserOrgPageVoRes getBossUserOrgByPage(PageVo pageVo, BossUserOrgVo bossUserOrgVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询BossUserOrg");
		
		Page<BossUserOrg> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		BossUserOrg bossUserOrg = (BossUserOrg)BeanUtil.copyProperties(BossUserOrg.class, bossUserOrgVo);
		page = bossUserOrgLogic.findBossUserOrgByPage(page, bossUserOrg);

		BossUserOrgPageVoRes bossUserOrgPageVoRes = new BossUserOrgPageVoRes();
		List<BossUserOrgVo> bossUserOrgVoList = new ArrayList<BossUserOrgVo>();
		for (BossUserOrg po : page.getResultsContent()) {
			BossUserOrgVo vo = (BossUserOrgVo)BeanUtil.copyProperties(BossUserOrgVo.class, po);
			bossUserOrgVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bossUserOrgPageVoRes.setPage(pageVo);
		bossUserOrgPageVoRes.setBossUserOrgVoList(bossUserOrgVoList);
		
		 
		return bossUserOrgPageVoRes;
	}



}
