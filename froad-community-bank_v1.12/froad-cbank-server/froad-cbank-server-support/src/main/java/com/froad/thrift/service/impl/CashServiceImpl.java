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
 * @Title: CashImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.CashLogic;
import com.froad.logic.impl.CashLogicImpl;
import com.froad.po.Cash;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.CashService;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.CashPageVoRes;
import com.froad.thrift.vo.CashVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: CashImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class CashServiceImpl extends BizMonitorBaseService implements CashService.Iface {
	private CashLogic cashLogic = new CashLogicImpl();
	public CashServiceImpl() {}
	public CashServiceImpl(String name, String version) {
		super(name, version);
	}

	
    /**
     * 增加 Cash
     * @param cash
     * @return long    主键ID
     */
	@Override
	public AddResultVo addCash(OriginVo originVo,CashVo cashVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加Cash");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		Cash cash = (Cash)BeanUtil.copyProperties(Cash.class, cashVo);
		Long rlt = cashLogic.addCash(cash);
		
		if(rlt != null && rlt > 0){
			addResultVo.setId(rlt);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
		}else{
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("添加Cash失败");
		}
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}



    /**
     * 删除 Cash
     * @param cash
     * @return boolean    
     */
	@Override
	public ResultVo deleteCash(OriginVo originVo,CashVo cashVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除Cash");
		Result result = new Result();
		Cash cash = (Cash)BeanUtil.copyProperties(Cash.class, cashVo);
		Boolean b = cashLogic.deleteCash(cash);
		if (!b) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("删除Cash失败");
		}	
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 修改 Cash
     * @param cash
     * @return boolean    
     */
	@Override
	public ResultVo updateCash(OriginVo originVo,CashVo cashVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改Cash");
		Result result = new Result();
		Cash cash = (Cash)BeanUtil.copyProperties(Cash.class, cashVo);
		Boolean b = cashLogic.updateCash(cash);
		if (!b) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("修改Cash失败");
		}	
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 查询 Cash
     * @param cash
     * @return List<CashVo>
     */
	@Override
	public List<CashVo> getCash(CashVo cashVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询Cash");
		Cash  cash = (Cash)BeanUtil.copyProperties(Cash.class, cashVo);
		List<Cash> cashList = cashLogic.findCash(cash);
		List<CashVo> cashVoList = new ArrayList<CashVo>();
		for (Cash po : cashList) {
			CashVo vo = (CashVo)BeanUtil.copyProperties(CashVo.class, po);
			cashVoList.add(vo);
		}
		return cashVoList;
	}



    /**
     * 分页查询 Cash
     * @param cash
     * @return CashPageVoRes
     */
	@Override
	public CashPageVoRes getCashByPage(PageVo pageVo, CashVo cashVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询Cash");
		
		Page<Cash> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		Cash Cash = (Cash)BeanUtil.copyProperties(Cash.class, cashVo);
		page = cashLogic.findCashByPage(page, Cash);

		CashPageVoRes cashPageVoRes = new CashPageVoRes();
		List<CashVo> cashVoList = new ArrayList<CashVo>();
		for (Cash po : page.getResultsContent()) {
			CashVo vo = (CashVo)BeanUtil.copyProperties(CashVo.class, po);
			cashVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		cashPageVoRes.setPage(pageVo);
		cashPageVoRes.setCashVoList(cashVoList);
		return cashPageVoRes;
	}


}
