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
 * @Title: BossSenseWordsImpl.java
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
import com.froad.logic.BossSenseWordsLogic;
import com.froad.logic.impl.BossSenseWordsLogicImpl;
import com.froad.po.BossSenseWords;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossSenseWordsService;
import com.froad.thrift.vo.AddResultVo;
import com.froad.thrift.vo.BossSenseWordsPageVoRes;
import com.froad.thrift.vo.BossSenseWordsVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;


/**
 * 
 * <p>@Title: BossSenseWordsImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class BossSenseWordsServiceImpl extends BizMonitorBaseService implements BossSenseWordsService.Iface {
	private BossSenseWordsLogic bossSenseWordsLogic = new BossSenseWordsLogicImpl();
	public BossSenseWordsServiceImpl() {}

	public BossSenseWordsServiceImpl(String name, String version) {
		super(name, version);
	}
    /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return long    主键ID
     */
	@Override
	public AddResultVo addBossSenseWords(OriginVo originVo,BossSenseWordsVo bossSenseWordsVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("添加BossSenseWords");
		AddResultVo addResultVo=new AddResultVo();
		ResultVo resultVo=new ResultVo();
		BossSenseWords bossSenseWords= (BossSenseWords)BeanUtil.copyProperties(BossSenseWords.class, bossSenseWordsVo);
		if(bossSenseWords.getWord()== null  || "".equals(bossSenseWords.getWord())){
			LogCvt.error("添加敏感失败,原因:敏感词不能为空!");
			resultVo.setResultDesc("添加敏感词失败,原因:敏感词不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		if(bossSenseWords.getIsEnable() ==null)
		{
			bossSenseWords.setIsEnable(true);
		}
		Long rlt = bossSenseWordsLogic.addBossSenseWords(bossSenseWords);
		if(rlt != null && rlt > 0){
			resultVo.setResultDesc("添加BossSenseWords成功");
			addResultVo.setId(rlt);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
		}else{
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("添加BossSenseWords失败");
		}
		addResultVo.setResultVo(resultVo);
		return addResultVo;
//		return null == result || result < 1 ? -1 : result;
	}



    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return boolean    
     */
	@Override
	public ResultVo deleteBossSenseWords(OriginVo originVo,BossSenseWordsVo bossSenseWordsVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("删除BossSenseWords");
		Result result = new Result();
		BossSenseWords bossSenseWords= (BossSenseWords)BeanUtil.copyProperties(BossSenseWords.class, bossSenseWordsVo);
		Boolean b = bossSenseWordsLogic.deleteBossSenseWords(bossSenseWords);
		if (!b) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("删除BossSenseWords失败");
		}	
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
//		return null == result ? false : result;
	}



    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return boolean    
     */
	@Override
	public ResultVo updateBossSenseWords(OriginVo originVo,BossSenseWordsVo bossSenseWordsVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info(JSON.toJSONString(originVo));
		LogCvt.info("修改BossSenseWords");
		Result result = new Result();
		BossSenseWords bossSenseWords= (BossSenseWords)BeanUtil.copyProperties(BossSenseWords.class, bossSenseWordsVo);
		Boolean b = bossSenseWordsLogic.updateBossSenseWords(bossSenseWords);
		if (!b) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("修改BossSenseWords失败");
		}	
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}



    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWordsVo>
     */
	@Override
	public List<BossSenseWordsVo> getBossSenseWords(BossSenseWordsVo bossSenseWordsVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("查询BossSenseWords");
		BossSenseWords bossSenseWords= (BossSenseWords)BeanUtil.copyProperties(BossSenseWords.class, bossSenseWordsVo);
		List<BossSenseWords> bossSenseWordsList = bossSenseWordsLogic.findBossSenseWords(bossSenseWords);
		List<BossSenseWordsVo> bossSenseWordsVoList = new ArrayList<BossSenseWordsVo>();
		for (BossSenseWords po : bossSenseWordsList) {
			BossSenseWordsVo vo= (BossSenseWordsVo)BeanUtil.copyProperties(BossSenseWordsVo.class, po);
			bossSenseWordsVoList.add(vo);
		}
		return bossSenseWordsVoList;
	}



    /**
     * 分页查询 BossSenseWords
     * @param bossSenseWords
     * @return BossSenseWordsPageVoRes
     */
	@Override
	public BossSenseWordsPageVoRes getBossSenseWordsByPage(PageVo pageVo, BossSenseWordsVo bossSenseWordsVo) throws TException {
		// TODO Auto-generated method stub
		LogCvt.info("分页查询BossSenseWords");
		Page<BossSenseWords> page = (Page)BeanUtil.copyProperties(Page.class, pageVo);
		BossSenseWords bossSenseWords = (BossSenseWords)BeanUtil.copyProperties(BossSenseWords.class, bossSenseWordsVo);
		page = bossSenseWordsLogic.findBossSenseWordsByPage(page, bossSenseWords);

		BossSenseWordsPageVoRes bossSenseWordsPageVoRes = new BossSenseWordsPageVoRes();
		List<BossSenseWordsVo> bossSenseWordsVoList = new ArrayList<BossSenseWordsVo>();
		for (BossSenseWords po : page.getResultsContent()) {
			BossSenseWordsVo vo = (BossSenseWordsVo)BeanUtil.copyProperties(BossSenseWordsVo.class, po);
			bossSenseWordsVoList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		bossSenseWordsPageVoRes.setPage(pageVo);
		bossSenseWordsPageVoRes.setBossSenseWordsVoList(bossSenseWordsVoList);
		return bossSenseWordsPageVoRes;
	}


}