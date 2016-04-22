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
 * @Title: BizInstanceAttrValueImpl.java
 * @Package com.froad.thrift.service.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.thrift.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import com.alibaba.fastjson.JSON;

import com.froad.db.mysql.bean.Page;
import com.froad.logback.LogCvt;
import com.froad.logic.BizInstanceAttrValueLogic;
import com.froad.logic.impl.BizInstanceAttrValueLogicImpl;
import com.froad.po.mysql.BizInstanceAttrValue;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BizInstanceAttrValueService;
import com.froad.thrift.vo.BizInstanceAttrValuePageVoRes;
import com.froad.thrift.vo.BizInstanceAttrValueVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: BizInstanceAttrValueImpl.java</p>
 * <p>Description: 实现对BizInstanceAttrValue所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BizInstanceAttrValueServiceImpl extends BizMonitorBaseService implements BizInstanceAttrValueService.Iface {
	private BizInstanceAttrValueLogic bizInstanceAttrValueLogic = new BizInstanceAttrValueLogicImpl();
	public BizInstanceAttrValueServiceImpl() {}
	public BizInstanceAttrValueServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param bizInstanceAttrValueVo
     * @return String    主键ID
     */
	@Override
	public String addBizInstanceAttrValue(OriginVo originVo, BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		String instanceId = bizInstanceAttrValueLogic.addBizInstanceAttrValue(bizInstanceAttrValue); // 调用Logic添加BizInstanceAttrValue

		return instanceId;
	}



    /**
     * 删除 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param bizInstanceAttrValueVo
     * @return boolean    
     */
	@Override
	public boolean deleteBizInstanceAttrValue(OriginVo originVo, BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		Boolean result = bizInstanceAttrValueLogic.deleteBizInstanceAttrValue(bizInstanceAttrValue); // 调用Logic删除BizInstanceAttrValue

		return null == result ? false : result;
	}



    /**
     * 根据instanceId删除单个 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param instanceId
     * @return BizInstanceAttrValueVo
     */
	@Override
	public boolean deleteBizInstanceAttrValueByInstanceId(OriginVo originVo, String instanceId) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据instanceId删除单个BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(instanceId));

		Boolean result = bizInstanceAttrValueLogic.deleteBizInstanceAttrValueByInstanceId(instanceId); // 调用Logic删除BizInstanceAttrValue

		return null == result ? false : result;
	}



    /**
     * 修改 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param bizInstanceAttrValueVo
     * @return boolean    
     */
	@Override
	public boolean updateBizInstanceAttrValue(OriginVo originVo, BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		Boolean result = bizInstanceAttrValueLogic.updateBizInstanceAttrValue(bizInstanceAttrValue); // 调用Logic修改BizInstanceAttrValue

		return null == result ? false : result;
	}



    /**
     * 根据instanceId修改单个 BizInstanceAttrValue
     * @param originVo 源信息对象
     * @param instanceId
     * @return BizInstanceAttrValueVo
     */
	@Override
	public boolean updateBizInstanceAttrValueByInstanceId(OriginVo originVo, BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据instanceId修改单个BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		Boolean result = bizInstanceAttrValueLogic.updateBizInstanceAttrValueByInstanceId(bizInstanceAttrValue); // 调用Logic修改BizInstanceAttrValue

		return null == result ? false : result;
	}



    /**
     * 根据instanceId查询单个 BizInstanceAttrValue
     * @param instanceId
     * @return BizInstanceAttrValueVo
     */
	@Override
	public BizInstanceAttrValueVo getBizInstanceAttrValueByInstanceId(String instanceId) throws TException {
		LogCvt.info("根据instanceId查询单个BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(instanceId));

		BizInstanceAttrValue bizInstanceAttrValue = bizInstanceAttrValueLogic.findBizInstanceAttrValueByInstanceId(instanceId); // 调用Logic查询单个BizInstanceAttrValue

		BizInstanceAttrValueVo bizInstanceAttrValueVo = (BizInstanceAttrValueVo) BeanUtil.copyProperties(BizInstanceAttrValueVo.class, bizInstanceAttrValue);
		return bizInstanceAttrValueVo;
	}



    /**
     * 根据条件查询一个 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return BizInstanceAttrValueVo
     */
	@Override
	public BizInstanceAttrValueVo getOneBizInstanceAttrValue(BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info("根据条件查询一个BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		bizInstanceAttrValue = bizInstanceAttrValueLogic.findOneBizInstanceAttrValue(bizInstanceAttrValue); // 调用Logic查询单个BizInstanceAttrValue

		bizInstanceAttrValueVo = (BizInstanceAttrValueVo) BeanUtil.copyProperties(BizInstanceAttrValueVo.class, bizInstanceAttrValue);
		return bizInstanceAttrValueVo;
	}



    /**
     * 根据条件统计 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return int
     */
	@Override
	public int countBizInstanceAttrValue(BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info("根据条件统计BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		return bizInstanceAttrValueLogic.countBizInstanceAttrValue(bizInstanceAttrValue); // 调用Logic统计BizInstanceAttrValue

	}



    /**
     * 查询 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return List<BizInstanceAttrValueVo>
     */
	@Override
	public List<BizInstanceAttrValueVo> getBizInstanceAttrValue(BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info("查询BizInstanceAttrValue，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		List<BizInstanceAttrValue> bizInstanceAttrValueList = bizInstanceAttrValueLogic.findBizInstanceAttrValue(bizInstanceAttrValue); // 调用Logic查询BizInstanceAttrValue

		List<BizInstanceAttrValueVo> bizInstanceAttrValueVoList = (List<BizInstanceAttrValueVo>) BeanUtil.copyProperties(BizInstanceAttrValueVo.class, bizInstanceAttrValueList);
		return bizInstanceAttrValueVoList;
	}



    /**
     * 分页查询 BizInstanceAttrValue
     * @param bizInstanceAttrValueVo
     * @return BizInstanceAttrValuePageVoRes
     */
	@Override
	public BizInstanceAttrValuePageVoRes getBizInstanceAttrValueByPage(PageVo pageVo, BizInstanceAttrValueVo bizInstanceAttrValueVo) throws TException {
		LogCvt.info("分页查询BizInstanceAttrValue，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t bizInstanceAttrValueVo==>" + JSonUtil.toJSonString(bizInstanceAttrValueVo));
		Page<BizInstanceAttrValue> page = (Page<BizInstanceAttrValue>) BeanUtil.copyProperties(Page.class, pageVo);
		BizInstanceAttrValue bizInstanceAttrValue = (BizInstanceAttrValue) BeanUtil.copyProperties(BizInstanceAttrValue.class, bizInstanceAttrValueVo);

		page = bizInstanceAttrValueLogic.findBizInstanceAttrValueByPage(page, bizInstanceAttrValue); // 调用Logic分页查询BizInstanceAttrValue

		List<BizInstanceAttrValueVo> bizInstanceAttrValueVoList = (List<BizInstanceAttrValueVo>) BeanUtil.copyProperties(BizInstanceAttrValueVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		BizInstanceAttrValuePageVoRes bizInstanceAttrValuePageVoRes = new BizInstanceAttrValuePageVoRes();
		bizInstanceAttrValuePageVoRes.setPage(pageVo);
		bizInstanceAttrValuePageVoRes.setBizInstanceAttrValueVoList(bizInstanceAttrValueVoList);
		return bizInstanceAttrValuePageVoRes;
	}


}
