/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: AgreementImpl.java
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
import com.froad.logic.AgreementLogic;
import com.froad.logic.impl.AgreementLogicImpl;
import com.froad.po.Agreement;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.AgreementService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.AgreementPageVoRes;
import com.froad.thrift.vo.AgreementVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: AgreementImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class AgreementServiceImpl extends BizMonitorBaseService implements AgreementService.Iface {
	private AgreementLogic agreementLogic = new AgreementLogicImpl();
	public AgreementServiceImpl() {}

	public AgreementServiceImpl(String name, String version) {
		super(name, version);
	}
	
	
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,Agreement agreement){
		if(agreement.getClientId() == null || "".equals(agreement.getClientId())){
			LogCvt.error("添加AgreementVo失败,原因:ClientId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
	
    /**
     * 增加 Agreement
     * @param agreement
     * @return long    主键ID
     */
	@Override
	public AddResultVo addAgreement(OriginVo originVo,AgreementVo agreementVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Agreement");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		Agreement agreement= (Agreement)BeanUtil.copyProperties(Agreement.class, agreementVo);
		addResultVo=verification(resultVo,addResultVo,agreement);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		ResultBean result = agreementLogic.addAgreement(agreement);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加协议信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 Agreement
     * @param agreement
     * @return boolean    
     */
	@Override
	public ResultVo deleteAgreement(OriginVo originVo,AgreementVo agreementVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Agreement");
		Agreement agreement= (Agreement)BeanUtil.copyProperties(Agreement.class, agreementVo);
		ResultBean result = agreementLogic.deleteAgreement(agreement);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除协议成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 Agreement
     * @param agreement
     * @return boolean    
     */
	@Override
	public ResultVo updateAgreement(OriginVo originVo,AgreementVo agreementVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Agreement");
		Agreement agreement= (Agreement)BeanUtil.copyProperties(Agreement.class, agreementVo);
		ResultBean result = agreementLogic.updateAgreement(agreement);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改协议成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 查询 Agreement
     * @param agreement
     * @return List<AgreementVo>
     */
	@Override
	public List<AgreementVo> getAgreement(AgreementVo agreementVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Agreement");
		Agreement agreement= (Agreement)BeanUtil.copyProperties(Agreement.class, agreementVo);
		List<Agreement> agreementList = agreementLogic.findAgreement(agreement);
		List<AgreementVo> agreementVoList = new ArrayList<AgreementVo>();
		for (Agreement po : agreementList) {
			AgreementVo vo= (AgreementVo)BeanUtil.copyProperties(AgreementVo.class, po);
			agreementVoList.add(vo);
		}
		return agreementVoList;
	}



    /**
     * 分页查询 Agreement
     * @param agreement
     * @return AgreementPageVoRes
     */
	@Override
	public AgreementPageVoRes getAgreementByPage(PageVo pageVo, AgreementVo agreementVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询Agreement");
		
		Page<Agreement> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		Agreement agreement = (Agreement)BeanUtil.copyProperties(Agreement.class, agreementVo);
		page = agreementLogic.findAgreementByPage(page, agreement);

		AgreementPageVoRes agreementPageVoRes = new AgreementPageVoRes();
		List<AgreementVo> agreementVoList = new ArrayList<AgreementVo>();
		for (Agreement po : page.getResultsContent()) {
			AgreementVo vo = (AgreementVo)BeanUtil.copyProperties(AgreementVo.class, po);
			agreementVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		agreementPageVoRes.setPage(pageVo);
		agreementPageVoRes.setAgreementVoList(agreementVoList);
		
		 
		return agreementPageVoRes;
	}


}
