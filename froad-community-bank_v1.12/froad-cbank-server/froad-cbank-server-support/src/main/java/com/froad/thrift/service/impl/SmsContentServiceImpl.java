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
 * @Title: SmsContentImpl.java
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
import com.froad.logic.SmsContentLogic;
import com.froad.logic.impl.SmsContentLogicImpl;
import com.froad.po.DeliveryCorp;
import com.froad.po.SmsContent;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.SmsContentService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsContentPageVoRes;
import com.froad.thrift.vo.SmsContentVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: SmsContentImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class SmsContentServiceImpl extends BizMonitorBaseService implements SmsContentService.Iface {
	private SmsContentLogic smsContentLogic = new SmsContentLogicImpl();
	public SmsContentServiceImpl() {}
	public SmsContentServiceImpl(String name, String version) {
		super(name, version);
	}

	
	/** 非空验证
	* @Title: verification 
	* @Description: 
	* @author longyunbo
	* @param @param resultVo
	* @param @param addResultVo
	* @param @param adVo
	* @param @return
	* @return AddResultVo
	* @throws 
	*/
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,SmsContent smsContent){
		if(smsContent.getClientId() == null || "".equals(smsContent.getClientId())){
			LogCvt.error("添加smsContent失败,原因:客户端ClientId不能为空!");
			resultVo.setResultDesc("添加短信模板失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(smsContent.getContent()== null || "".equals(smsContent.getContent())){
			LogCvt.error("添加SmsContent失败,原因:短信模板Content不能为空!");
			resultVo.setResultDesc("添加短信模板失败,原因:短信模板内容短信模板不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(smsContent.getSmsType()== null || "".equals(smsContent.getSmsType())){
			LogCvt.error("添加SmsContent失败,原因:SmsType不能为空!");
			resultVo.setResultDesc("添加短信模板失败,原因:类型不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
    /**
     * 增加 SmsContent
     * @param smsContent
     * @return long    主键ID
     */
	@Override
	public AddResultVo addSmsContent(OriginVo originVo,SmsContentVo smsContentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加SmsContent");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		SmsContent  smsContent = (SmsContent)BeanUtil.copyProperties(SmsContent.class, smsContentVo);
		//非空验证
		addResultVo=verification(resultVo,addResultVo,smsContent);
		if(!StringUtils.equals(ResultCode.success.getCode(),addResultVo.getResultVo().getResultCode())){
			return addResultVo;
		}
		if(smsContent.getValidTime()== null){
			smsContent.setValidTime(0);
		}
		if(smsContent.getIsEnable()== null){
			smsContent.setIsEnable(true);
		}
		ResultBean result = smsContentLogic.addSmsContent(smsContent);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加SmsContent信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
	}



    /**
     * 删除 SmsContent
     * @param smsContent
     * @return boolean    
     */
	@Override
	public ResultVo deleteSmsContent(OriginVo originVo,SmsContentVo smsContentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除SmsContent");
		SmsContent  smsContent = (SmsContent)BeanUtil.copyProperties(SmsContent.class, smsContentVo);
		ResultBean result = smsContentLogic.deleteSmsContent(smsContent);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除SmsContent成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
	}



    /**
     * 修改 SmsContent
     * @param smsContent
     * @return boolean    
     */
	@Override
	public ResultVo updateSmsContent(OriginVo originVo,SmsContentVo smsContentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改SmsContent");
		SmsContent  smsContent = (SmsContent)BeanUtil.copyProperties(SmsContent.class, smsContentVo);
		ResultBean result = smsContentLogic.updateSmsContent(smsContent);
		
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改 SmsContent成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		return resultVo;
	}



    /**
     * 查询 SmsContent
     * @param smsContent
     * @return List<SmsContentVo>
     */
	@Override
	public List<SmsContentVo> getSmsContent(SmsContentVo smsContentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询SmsContent");
		SmsContent  smsContent = (SmsContent)BeanUtil.copyProperties(SmsContent.class, smsContentVo);

		List<SmsContent> smsContentList = smsContentLogic.findSmsContent(smsContent);
		List<SmsContentVo> smsContentVoList = new ArrayList<SmsContentVo>();
		for (SmsContent po : smsContentList) {
			SmsContentVo  vo = (SmsContentVo)BeanUtil.copyProperties(SmsContentVo.class, po);  
			smsContentVoList.add(vo);
		}
		return smsContentVoList;
	}



    /**
     * 分页查询 SmsContent
     * @param smsContent
     * @return SmsContentPageVoRes
     */
	@Override
	public SmsContentPageVoRes getSmsContentByPage(PageVo pageVo, SmsContentVo smsContentVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询SmsContent");
		Page<SmsContent> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		SmsContent smsContent = (SmsContent)BeanUtil.copyProperties(SmsContent.class, smsContentVo);
		page = smsContentLogic.findSmsContentByPage(page, smsContent);
		SmsContentPageVoRes smsContentPageVoRes = new SmsContentPageVoRes();
		List<SmsContentVo> smsContentVoList = new ArrayList<SmsContentVo>();
		for (SmsContent po : page.getResultsContent()) {
			SmsContentVo vo = (SmsContentVo)BeanUtil.copyProperties(SmsContentVo.class, po);
			smsContentVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		smsContentPageVoRes.setPage(pageVo);
		smsContentPageVoRes.setSmsContentVoList(smsContentVoList);
		return smsContentPageVoRes;
	}
	
	/**
     * 查询 SmsContent
     * @return SmsContentVo
     * 
     * @param clientId
     * @param smsType
     */
	@Override
	public SmsContentVo getSmsContentByClientIdAndType(String clientId,
			int smsType) throws TException {
		
		LogCvt.info("查询SmsContent");
		SmsContent smsConent = smsContentLogic.getSmsContentByClientIdAndType(clientId, smsType);
		SmsContentVo result = (SmsContentVo)BeanUtil.copyProperties(SmsContentVo.class, smsConent);
		return result;
	}


}
