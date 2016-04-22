package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.DictionaryLogic;
import com.froad.logic.impl.DictionaryLogicImpl;
import com.froad.po.Dictionary;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.dictionary.DictionaryPageVoRes;
import com.froad.thrift.vo.dictionary.DictionaryVo;
import com.froad.thrift.service.BossDictionaryService;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: DictionaryServiceImpl.java</p>
 * <p>Description: 描述 </p> 字典Service实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public class BossDictionaryServiceImpl extends BizMonitorBaseService implements BossDictionaryService.Iface {
	private DictionaryLogic dictionaryLogic = new DictionaryLogicImpl();
	public BossDictionaryServiceImpl(String name, String version)
	{
		super(name, version);
	}


	/**
     * 新增字典
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryVo 字典信息
     * @return ResultVo
     */
	@Override
	public CommonAddVoRes addDictionary(OriginVo originVo,DictionaryVo dictionaryVo) throws TException {
		
		CommonAddVoRes res = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加字典信息");
			LogUtils.addLog(originVo);
						
			if(Checker.isEmpty(dictionaryVo)){
				throw new FroadServerException("添加Dictionary失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(dictionaryVo.getDicCode())){
				throw new FroadServerException("添加Dictionary失败,原因:DicCode不能为空!");
			}
			if(Checker.isEmpty(dictionaryVo.getDicName())){
				throw new FroadServerException("添加Dictionary失败,原因:DicName不能为空!");
			}
			if(Checker.isEmpty(dictionaryVo.getCategoryId())){
				throw new FroadServerException("添加Dictionary失败,原因:CategoryId不能为空!");
			}
			if(Checker.isEmpty(dictionaryVo.isEnable)){
				throw new FroadServerException("添加Dictionary失败,原因:isEnable不能为空!");
			}
			
			//vo 转 po 
			Dictionary dictionary = (Dictionary)BeanUtil.copyProperties(Dictionary.class, dictionaryVo);

			Long id = dictionaryLogic.addDictionary(dictionary);
			if(id != null && id > 0){
				res.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加字典失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加字典addDictionary失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加字典addDictionary异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		
		res.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return res;
		
	}

	/**
     * 批量删除字典
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 字典id
     * @return ResultVo
     */
	@Override
	public ResultVo deleteDictionaryBatch(OriginVo originVo, List<Long> dicIds) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("批量删除字典信息");
			LogUtils.addLog(originVo);
			
			if(dicIds.size()==0){
				throw new FroadServerException("批量删除Dictionary失败,原因:ids集合不能为空!");
			}
			
			if (!dictionaryLogic.deleteDictionaryBatch(dicIds)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("批量删除字典DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("批量删除字典deleteDictionaryBatch失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量删除字典deleteDictionaryBatch异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	

	/**
     * 修改 字典
     * @param dictionaryVo
     * @return boolean    
     */
	@Override
	public ResultVo updateDictionary(OriginVo originVo,DictionaryVo dictionaryVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改字典信息");
			LogUtils.addLog(originVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(dictionaryVo.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			// vo 转 po 
			Dictionary dictionary = (Dictionary)BeanUtil.copyProperties(Dictionary.class, dictionaryVo);
						
			if (!dictionaryLogic.updateDictionary(dictionary)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改字典DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改字典updateDictionary失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改字典updateDictionary异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
	
	
	/**
	 * 分页查询 字典列表
	 * @param dictionaryVo 过滤参数
	 * type 1-当前类别下字典查询  2-当前类别下所有子类别中字典查询
	 * @return DictionaryPageVoRes
	 */
	@Override
	public DictionaryPageVoRes getDictionaryByPage(PageVo pageVo, DictionaryVo dictionaryVo,int type) throws TException {
		
		DictionaryPageVoRes voRes = new DictionaryPageVoRes();
		Result result = new Result();
		List<DictionaryVo> dictionaryVoList = null;
		try{
			if(type!=1 && type!=2){
				throw new FroadServerException("type定义值不符！");
			}
			
			Page<Dictionary> page = (Page<Dictionary>)BeanUtil.copyProperties(Page.class, pageVo);
			Dictionary dictionary = (Dictionary)BeanUtil.copyProperties(Dictionary.class, dictionaryVo);

			page = dictionaryLogic.findDictionaryByPage(page, dictionary,type);

			
			dictionaryVoList = (List<DictionaryVo>)BeanUtil.copyProperties(DictionaryVo.class, page.getResultsContent());
			pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
				
			
		}catch (FroadServerException e) {
			LogCvt.error("分页查询字典列表getDictionaryByPage失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("分页查询字典列表getDictionaryByPage异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setPage(pageVo);
		voRes.setDictionaryVoList(dictionaryVoList==null?new ArrayList<DictionaryVo>():dictionaryVoList);
		return voRes;
	}



	
}

