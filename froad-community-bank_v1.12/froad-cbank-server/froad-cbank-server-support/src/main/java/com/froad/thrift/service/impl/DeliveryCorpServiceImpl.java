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
 * @Title: DeliveryCorpImpl.java
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
import com.froad.logic.DeliveryCorpLogic;
import com.froad.logic.impl.DeliveryCorpLogicImpl;
import com.froad.po.DeliveryCorp;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.DeliveryCorpService;
import com.froad.thrift.vo.AdVo;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.DeliveryCorpPageVoRes;
import com.froad.thrift.vo.DeliveryCorpVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: DeliveryCorpImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class DeliveryCorpServiceImpl extends BizMonitorBaseService implements DeliveryCorpService.Iface {
	private DeliveryCorpLogic deliveryCorpLogic = new DeliveryCorpLogicImpl();
	public DeliveryCorpServiceImpl() {}
	public DeliveryCorpServiceImpl(String name, String version) {
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
	public AddResultVo verification(ResultVo resultVo,AddResultVo addResultVo,DeliveryCorp deliveryCorp){
		if(deliveryCorp.getClientId() == null || "".equals(deliveryCorp.getClientId())){
			LogCvt.error("添加deliveryCorpVo失败,原因:客户端ClientId不能为空!");
			resultVo.setResultDesc("添加物流失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(deliveryCorp.getName() == null || "".equals(deliveryCorp.getName())){
			LogCvt.error("添加DeliveryCorp失败,原因:物流Name不能为空!");
			resultVo.setResultDesc("添加物流失败,原因:物流名字物流不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(deliveryCorp.getUrl() == null || "".equals(deliveryCorp.getUrl())){
			LogCvt.error("添加DeliveryCorp失败,原因:Url不能为空!");
			resultVo.setResultDesc("添加物流失败,原因:Url不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		addResultVo.setResultVo(resultVo);
		return addResultVo;
	}
    /**
     * 增加 DeliveryCorp
     * @param deliveryCorp
     * @return long    主键ID
     */
	@Override
	public AddResultVo addDeliveryCorp(OriginVo originVo,DeliveryCorpVo deliveryCorpVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加DeliveryCorp");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		DeliveryCorp  deliveryCorp = (DeliveryCorp)BeanUtil.copyProperties(DeliveryCorp.class, deliveryCorpVo);
		ResultBean result = deliveryCorpLogic.addDeliveryCorp(deliveryCorp);

		
		
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加DeliveryCorp信息成功");
		addResultVo.setResultVo(resultVo);
		addResultVo.setId((Long)result.getData());
		return addResultVo;
//		return null == result ? -1 : result;
	}



    /**
     * 删除 DeliveryCorp
     * @param deliveryCorp
     * @return boolean    
     */
	@Override
	public ResultVo deleteDeliveryCorp(OriginVo originVo,DeliveryCorpVo deliveryCorpVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除DeliveryCorp");
		DeliveryCorp  deliveryCorp = (DeliveryCorp)BeanUtil.copyProperties(DeliveryCorp.class, deliveryCorpVo);
		ResultBean result = deliveryCorpLogic.deleteDeliveryCorp(deliveryCorp);

		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"删除DeliveryCorp成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
//		return null == result ? false : result;
	}



    /**
     * 修改 DeliveryCorp
     * @param deliveryCorp
     * @return boolean    
     */
	@Override
	public ResultVo updateDeliveryCorp(OriginVo originVo,DeliveryCorpVo deliveryCorpVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改DeliveryCorp");
		DeliveryCorp  deliveryCorp = (DeliveryCorp)BeanUtil.copyProperties(DeliveryCorp.class, deliveryCorpVo);
		ResultBean result = deliveryCorpLogic.updateDeliveryCorp(deliveryCorp);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"修改 DeliveryCorp成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		
		return resultVo;
//		return null == result ? false : result;
	}



    /**
     * 查询 DeliveryCorp
     * @param deliveryCorp
     * @return List<DeliveryCorpVo>
     */
	@Override
	public List<DeliveryCorpVo> getDeliveryCorp(DeliveryCorpVo deliveryCorpVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询DeliveryCorp");
		DeliveryCorp  deliveryCorp = (DeliveryCorp)BeanUtil.copyProperties(DeliveryCorp.class, deliveryCorpVo);
		List<DeliveryCorp> deliveryCorpList = deliveryCorpLogic.findDeliveryCorp(deliveryCorp);
		List<DeliveryCorpVo> deliveryCorpVoList = new ArrayList<DeliveryCorpVo>();
		for (DeliveryCorp po : deliveryCorpList) {
			DeliveryCorpVo  vo = (DeliveryCorpVo)BeanUtil.copyProperties(DeliveryCorpVo.class, po);
			deliveryCorpVoList.add(vo);
		}
		return deliveryCorpVoList;
	}



    /**
     * 分页查询 DeliveryCorp
     * @param deliveryCorp
     * @return DeliveryCorpPageVoRes
     */
	@Override
	public DeliveryCorpPageVoRes getDeliveryCorpByPage(PageVo pageVo, DeliveryCorpVo deliveryCorpVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询DeliveryCorp");
		Page<DeliveryCorp> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		DeliveryCorp deliveryCorp = (DeliveryCorp)BeanUtil.copyProperties(DeliveryCorp.class, deliveryCorpVo);
		page = deliveryCorpLogic.findDeliveryCorpByPage(page, deliveryCorp);

		DeliveryCorpPageVoRes deliveryCorpPageVoRes = new DeliveryCorpPageVoRes();
		List<DeliveryCorpVo> deliveryCorpVoList = new ArrayList<DeliveryCorpVo>();
		for (DeliveryCorp po : page.getResultsContent()) {
			DeliveryCorpVo vo = (DeliveryCorpVo)BeanUtil.copyProperties(DeliveryCorpVo.class, po);
			deliveryCorpVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		deliveryCorpPageVoRes.setPage(pageVo);
		deliveryCorpPageVoRes.setDeliveryCorpVoList(deliveryCorpVoList);
		return deliveryCorpPageVoRes;
	}


}
