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
 * @Title: BizInstanceAttrImpl.java
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
import com.froad.logic.BizInstanceAttrLogic;
import com.froad.logic.impl.BizInstanceAttrLogicImpl;
import com.froad.po.mysql.BizInstanceAttr;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BizInstanceAttrService;
import com.froad.thrift.vo.BizInstanceAttrPageVoRes;
import com.froad.thrift.vo.BizInstanceAttrVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;


/**
 * 
 * <p>@Title: BizInstanceAttrImpl.java</p>
 * <p>Description: 实现对BizInstanceAttr所有业务逻辑处理的外部thrift接口 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BizInstanceAttrServiceImpl extends BizMonitorBaseService implements BizInstanceAttrService.Iface {
	private BizInstanceAttrLogic bizInstanceAttrLogic = new BizInstanceAttrLogicImpl();
	public BizInstanceAttrServiceImpl() {}
	public BizInstanceAttrServiceImpl(String name, String version) {super(name, version);}


    /**
     * 增加 BizInstanceAttr
     * @param originVo 源信息对象
     * @param bizInstanceAttrVo
     * @return long    主键ID
     */
	@Override
	public long addBizInstanceAttr(OriginVo originVo, BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		Long id = bizInstanceAttrLogic.addBizInstanceAttr(bizInstanceAttr); // 调用Logic添加BizInstanceAttr

		return id;
	}



    /**
     * 删除 BizInstanceAttr
     * @param originVo 源信息对象
     * @param bizInstanceAttrVo
     * @return boolean    
     */
	@Override
	public boolean deleteBizInstanceAttr(OriginVo originVo, BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		Boolean result = bizInstanceAttrLogic.deleteBizInstanceAttr(bizInstanceAttr); // 调用Logic删除BizInstanceAttr

		return null == result ? false : result;
	}



    /**
     * 根据id删除单个 BizInstanceAttr
     * @param originVo 源信息对象
     * @param id
     * @return BizInstanceAttrVo
     */
	@Override
	public boolean deleteBizInstanceAttrById(OriginVo originVo, long id) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id删除单个BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(id));

		Boolean result = bizInstanceAttrLogic.deleteBizInstanceAttrById(id); // 调用Logic删除BizInstanceAttr

		return null == result ? false : result;
	}



    /**
     * 修改 BizInstanceAttr
     * @param originVo 源信息对象
     * @param bizInstanceAttrVo
     * @return boolean    
     */
	@Override
	public boolean updateBizInstanceAttr(OriginVo originVo, BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		Boolean result = bizInstanceAttrLogic.updateBizInstanceAttr(bizInstanceAttr); // 调用Logic修改BizInstanceAttr

		return null == result ? false : result;
	}



    /**
     * 根据id修改单个 BizInstanceAttr
     * @param originVo 源信息对象
     * @param id
     * @return BizInstanceAttrVo
     */
	@Override
	public boolean updateBizInstanceAttrById(OriginVo originVo, BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("根据id修改单个BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		Boolean result = bizInstanceAttrLogic.updateBizInstanceAttrById(bizInstanceAttr); // 调用Logic修改BizInstanceAttr

		return null == result ? false : result;
	}



    /**
     * 根据id查询单个 BizInstanceAttr
     * @param id
     * @return BizInstanceAttrVo
     */
	@Override
	public BizInstanceAttrVo getBizInstanceAttrById(long id) throws TException {
		LogCvt.info("根据id查询单个BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(id));

		BizInstanceAttr bizInstanceAttr = bizInstanceAttrLogic.findBizInstanceAttrById(id); // 调用Logic查询单个BizInstanceAttr

		BizInstanceAttrVo bizInstanceAttrVo = (BizInstanceAttrVo) BeanUtil.copyProperties(BizInstanceAttrVo.class, bizInstanceAttr);
		return bizInstanceAttrVo;
	}



    /**
     * 根据条件查询一个 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return BizInstanceAttrVo
     */
	@Override
	public BizInstanceAttrVo getOneBizInstanceAttr(BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info("根据条件查询一个BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		bizInstanceAttr = bizInstanceAttrLogic.findOneBizInstanceAttr(bizInstanceAttr); // 调用Logic查询单个BizInstanceAttr

		bizInstanceAttrVo = (BizInstanceAttrVo) BeanUtil.copyProperties(BizInstanceAttrVo.class, bizInstanceAttr);
		return bizInstanceAttrVo;
	}



    /**
     * 根据条件统计 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return int
     */
	@Override
	public int countBizInstanceAttr(BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info("根据条件统计BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		return bizInstanceAttrLogic.countBizInstanceAttr(bizInstanceAttr); // 调用Logic统计BizInstanceAttr

	}



    /**
     * 查询 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return List<BizInstanceAttrVo>
     */
	@Override
	public List<BizInstanceAttrVo> getBizInstanceAttr(BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info("查询BizInstanceAttr，请求参数：" + JSonUtil.toJSonString(bizInstanceAttrVo));
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		List<BizInstanceAttr> bizInstanceAttrList = bizInstanceAttrLogic.findBizInstanceAttr(bizInstanceAttr); // 调用Logic查询BizInstanceAttr

		List<BizInstanceAttrVo> bizInstanceAttrVoList = (List<BizInstanceAttrVo>) BeanUtil.copyProperties(BizInstanceAttrVo.class, bizInstanceAttrList);
		return bizInstanceAttrVoList;
	}



    /**
     * 分页查询 BizInstanceAttr
     * @param bizInstanceAttrVo
     * @return BizInstanceAttrPageVoRes
     */
	@Override
	public BizInstanceAttrPageVoRes getBizInstanceAttrByPage(PageVo pageVo, BizInstanceAttrVo bizInstanceAttrVo) throws TException {
		LogCvt.info("分页查询BizInstanceAttr，请求参数：PageVo==>" + JSonUtil.toJSonString(pageVo) + "\t bizInstanceAttrVo==>" + JSonUtil.toJSonString(bizInstanceAttrVo));
		Page<BizInstanceAttr> page = (Page<BizInstanceAttr>) BeanUtil.copyProperties(Page.class, pageVo);
		BizInstanceAttr bizInstanceAttr = (BizInstanceAttr) BeanUtil.copyProperties(BizInstanceAttr.class, bizInstanceAttrVo);

		page = bizInstanceAttrLogic.findBizInstanceAttrByPage(page, bizInstanceAttr); // 调用Logic分页查询BizInstanceAttr

		List<BizInstanceAttrVo> bizInstanceAttrVoList = (List<BizInstanceAttrVo>) BeanUtil.copyProperties(BizInstanceAttrVo.class, page.getResultsContent());
		pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

		BizInstanceAttrPageVoRes bizInstanceAttrPageVoRes = new BizInstanceAttrPageVoRes();
		bizInstanceAttrPageVoRes.setPage(pageVo);
		bizInstanceAttrPageVoRes.setBizInstanceAttrVoList(bizInstanceAttrVoList);
		return bizInstanceAttrPageVoRes;
	}


}
