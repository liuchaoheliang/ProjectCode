package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.DictionaryItemLogic;
import com.froad.logic.DictionaryLogic;
import com.froad.logic.impl.DictionaryItemLogicImpl;
import com.froad.logic.impl.DictionaryLogicImpl;
import com.froad.po.Dictionary;
import com.froad.po.DictionaryItem;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.dictionary.DictionaryItemPageVoRes;
import com.froad.thrift.vo.dictionary.DictionaryItemVo;
import com.froad.thrift.service.BossDictionaryItemService;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: BossDictionaryItemServiceImpl.java</p>
 * <p>Description: 描述 </p> 字典条目Service实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public class BossDictionaryItemServiceImpl extends BizMonitorBaseService implements BossDictionaryItemService.Iface {
	private DictionaryItemLogic dictionaryItemLogic = new DictionaryItemLogicImpl();
	public BossDictionaryItemServiceImpl(String name, String version)
	{
		super(name, version);
	}


	/**
     * 新增字典条目
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryItemVo 字典条目信息
     * @return ResultVo
     */
	@Override
	public ResultVo addDictionaryItem(OriginVo originVo,DictionaryItemVo dictionaryItemVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加字典条目信息");
			LogUtils.addLog(originVo);
						
			if(Checker.isEmpty(dictionaryItemVo)){
				throw new FroadServerException("添加DictionaryItem失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(dictionaryItemVo.getDicId())){
				throw new FroadServerException("添加DictionaryItem失败,原因:DicId不能为空!");
			}
			if(Checker.isEmpty(dictionaryItemVo.getItemName())){
				throw new FroadServerException("添加DictionaryItem失败,原因:ItemName不能为空!");
			}
			if(Checker.isEmpty(dictionaryItemVo.getItemCode())){
				throw new FroadServerException("添加DictionaryItem失败,原因:ItemCode不能为空!");
			}
			if(Checker.isEmpty(dictionaryItemVo.getItemValue())){
				throw new FroadServerException("添加DictionaryItem失败,原因:ItemValue不能为空!");
			}
			if(Checker.isEmpty(dictionaryItemVo.getClientId())){
				throw new FroadServerException("添加DictionaryItem失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(dictionaryItemVo.isEnable)){
				throw new FroadServerException("添加DictionaryItem失败,原因:isEnable不能为空!");
			}
			
			//vo 转 po 
			DictionaryItem dictionaryItem = (DictionaryItem)BeanUtil.copyProperties(DictionaryItem.class, dictionaryItemVo);

			Long id = dictionaryItemLogic.addDictionaryItem(dictionaryItem);
			if(id == null || id<=0){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加字典条目信息");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加字典条目addDictionaryItem失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加字典条目addDictionaryItem异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		
		return (ResultVo)BeanUtil.copyProperties(ResultVo.class,result);
		
	}


	/**
     * 批量删除字典条目
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param itemIds 字典条目itemIds
     * @return ResultVo
     */
	@Override
	public ResultVo deleteDictionaryItemBatch(OriginVo originVo, List<Long> itemIds) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("批量删除字典条目信息");
			LogUtils.addLog(originVo);
			
			if(itemIds.size()==0){
				throw new FroadServerException("批量删除DictionaryItem失败,原因:ids集合不能为空!");
			}
			
			if (!dictionaryItemLogic.deleteDictionaryItemBatch(itemIds)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("批量删除字典条目DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("批量删除字典条目deleteDictionaryItem失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量删除字典条目deleteDictionaryItem异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

	

	/**
     * 修改 字典条目
     * @param dictionaryItem
     * @return boolean    
     */
	@Override
	public ResultVo updateDictionaryItem(OriginVo originVo,DictionaryItemVo dictionaryItemVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改字典条目信息");
			LogUtils.addLog(originVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(dictionaryItemVo.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			// vo 转 po 
			DictionaryItem dictionaryItem = (DictionaryItem)BeanUtil.copyProperties(DictionaryItem.class, dictionaryItemVo);
						
			if (!dictionaryItemLogic.updateDictionaryItem(dictionaryItem)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改字典条目DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改字典条目updateDictionaryItem失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改字典条目updateDictionaryItem异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
	
	/**
	 * 分页查询 字典条目列表
	 * @param dictionaryItemVo 过滤参数
	 * @return DictionaryItemPageVoRes
	 */
	@Override
	public DictionaryItemPageVoRes getDictionaryItemByPage(PageVo pageVo, DictionaryItemVo dictionaryItemVo) throws TException {
		
		DictionaryItemPageVoRes voRes = new DictionaryItemPageVoRes();
		Result result = new Result();
		List<DictionaryItemVo> dictionaryItemVoList = new ArrayList<DictionaryItemVo>();
		try{
			
			Page<DictionaryItem> page = (Page<DictionaryItem>)BeanUtil.copyProperties(Page.class, pageVo);
			DictionaryItem dictionaryItem = (DictionaryItem)BeanUtil.copyProperties(DictionaryItem.class, dictionaryItemVo);

			page = dictionaryItemLogic.findDictionaryItemByPage(page, dictionaryItem);
			List<DictionaryItem> itemList = page.getResultsContent();
			DictionaryItemVo vo = null;
			Dictionary dictonary = null;
			DictionaryLogic dictionaryLogic = new DictionaryLogicImpl();
			//设置字典名称
			for(DictionaryItem item : itemList){
				vo = (DictionaryItemVo)BeanUtil.copyProperties(DictionaryItemVo.class, item);
				dictonary = dictionaryLogic.findDictionaryById(item.getDicId());
				if(Checker.isNotEmpty(dictonary)){
					vo.setDicName(dictonary.getDicName());
				}
				dictionaryItemVoList.add(vo);
			}
			
			
			//dictionaryItemVoList = (List<DictionaryItemVo>)BeanUtil.copyProperties(DictionaryItemVo.class, page.getResultsContent());
			pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
				
			
		}catch (FroadServerException e) {
			LogCvt.error("分页查询字典条目列表getDictionaryItemByPage失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("分页查询字典条目列表getDictionaryItemByPage异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setPage(pageVo);
		voRes.setDictionaryItemVoList(dictionaryItemVoList==null?new ArrayList<DictionaryItemVo>():dictionaryItemVoList);
		return voRes;
	}



	
}

