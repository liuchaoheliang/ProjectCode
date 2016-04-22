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
 * @Title: SmsLogImpl.java
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
import com.froad.logback.LogCvt;
import com.froad.logic.SmsLogLogic;
import com.froad.logic.impl.SmsLogLogicImpl;
import com.froad.po.SmsLog;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.SmsLogService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.SmsLogPageVoRes;
import com.froad.thrift.vo.SmsLogVo;
import com.froad.util.BeanUtil;
import com.froad.util.SmsTypeUtil;


/**
 * 
 * <p>@Title: SmsLogImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class SmsLogServiceImpl extends BizMonitorBaseService implements SmsLogService.Iface {
	private SmsLogLogic smsLogLogic = new SmsLogLogicImpl();
	public SmsLogServiceImpl() {}
	public SmsLogServiceImpl(String name, String version) {
		super(name, version);
	}

    /**
     * 增加 SmsLog
     * @param smsLog
     * @return long    主键ID
     */
	@Override
	public long addSmsLog(OriginVo originVo,SmsLogVo smsLogVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加SmsLog");
		SmsLog  smsLog = (SmsLog)BeanUtil.copyProperties(SmsLog.class, smsLogVo);
		Long result = smsLogLogic.addSmsLog(smsLog);
		return null == result ? -1 : result;
	}



    /**
     * 删除 SmsLog
     * @param smsLog
     * @return boolean    
     */
	@Override
	public boolean deleteSmsLog(OriginVo originVo,SmsLogVo smsLogVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除SmsLog");
		SmsLog  smsLog = (SmsLog)BeanUtil.copyProperties(SmsLog.class, smsLogVo);
		Boolean result = smsLogLogic.deleteSmsLog(smsLog);
		return null == result ? false : result;
	}



    /**
     * 修改 SmsLog
     * @param smsLog
     * @return boolean    
     */
	@Override
	public boolean updateSmsLog(OriginVo originVo,SmsLogVo smsLogVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改SmsLog");
		SmsLog  smsLog = (SmsLog)BeanUtil.copyProperties(SmsLog.class, smsLogVo);
		Boolean result = smsLogLogic.updateSmsLog(smsLog);
		return null == result ? false : result;
	}



    /**
     * 查询 SmsLog
     * @param smsLog
     * @return List<SmsLogVo>
     */
	@Override
	public List<SmsLogVo> getSmsLog(SmsLogVo smsLogVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询SmsLog");
		SmsLog  smsLog = (SmsLog)BeanUtil.copyProperties(SmsLog.class, smsLogVo);
        String flag=smsLogVo.getSmsTypeMark();
		List<SmsLog> smsLogList = smsLogLogic.findSmsLog(smsLog,flag);
		List<SmsLogVo> smsLogVoList = new ArrayList<SmsLogVo>();
		for (SmsLog po : smsLogList) {
			SmsLogVo vo= (SmsLogVo)BeanUtil.copyProperties(SmsLogVo.class,po);
			vo.setSmsTypeMark(SmsTypeUtil.setSmsTypeMark(vo.getSmsType()));
			smsLogVoList.add(vo);
		}
		return smsLogVoList;
	}



    /**
     * 分页查询 SmsLog
     * @param smsLog
     * @return SmsLogPageVoRes
     */
	@Override
	public SmsLogPageVoRes getSmsLogByPage(PageVo pageVo, SmsLogVo smsLogVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询SmsLog");
		Page<SmsLog> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		SmsLog smsLog = (SmsLog)BeanUtil.copyProperties(SmsLog.class, smsLogVo);
		String flag=smsLogVo.getSmsTypeMark();
		page = smsLogLogic.findSmsLogByPage(page, smsLog,flag);

		SmsLogPageVoRes smsLogPageVoRes = new SmsLogPageVoRes();
		List<SmsLogVo> smsLogVoList = new ArrayList<SmsLogVo>();
		for (SmsLog po : page.getResultsContent()) {
			SmsLogVo vo = (SmsLogVo)BeanUtil.copyProperties(SmsLogVo.class, po);
			vo.setSmsTypeMark(SmsTypeUtil.setSmsTypeMark(vo.getSmsType()));
			smsLogVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		smsLogPageVoRes.setSmsLogVoList(smsLogVoList);
		smsLogPageVoRes.setPage(pageVo);
		return smsLogPageVoRes;
	}
	@Override
	public boolean updateSmsLog1(SmsLogVo smsLogVo) throws TException {
		LogCvt.info("修改SmsLog");
		SmsLog  smsLog = (SmsLog)BeanUtil.copyProperties(SmsLog.class, smsLogVo);
		Boolean result = smsLogLogic.updateSmsLog(smsLog);
		return null == result ? false : result;
		
	}


}
