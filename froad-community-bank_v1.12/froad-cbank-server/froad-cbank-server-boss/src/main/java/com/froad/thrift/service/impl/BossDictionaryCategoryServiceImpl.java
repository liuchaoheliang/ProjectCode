package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.DictionaryCategoryLogic;
import com.froad.logic.impl.DictionaryCategoryLogicImpl;
import com.froad.po.DictionaryCategory;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.dictionary.DictionaryCategoryListVoRes;
import com.froad.thrift.vo.dictionary.DictionaryCategoryVo;
import com.froad.thrift.service.BossDictionaryCategoryService;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: BossDictionaryCategoryServiceImpl.java</p>
 * <p>Description: 描述 </p> 字典类别Service实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public class BossDictionaryCategoryServiceImpl extends BizMonitorBaseService implements BossDictionaryCategoryService.Iface {
	private DictionaryCategoryLogic dictionaryCategoryLogic = new DictionaryCategoryLogicImpl();
	public BossDictionaryCategoryServiceImpl(String name, String version)
	{
		super(name, version);
	}


	/**
     * 新增字典类别
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryCategoryVo 字典类别信息
     * @return ResultVo
     */
	@Override
	public ResultVo addDictionaryCategory(OriginVo originVo,DictionaryCategoryVo dictionaryCategoryVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加字典类别信息");
			LogUtils.addLog(originVo);
						
			if(Checker.isEmpty(dictionaryCategoryVo)){
				throw new FroadServerException("添加DictionaryCategory失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(dictionaryCategoryVo.getCategoryName())){
				throw new FroadServerException("添加DictionaryCategory失败,原因:CategoryName不能为空!");
			}
			if(Checker.isEmpty(dictionaryCategoryVo.getCategoryLevel())){
				throw new FroadServerException("添加DictionaryCategory失败,原因:CategoryLevel不能为空!");
			}
			if(Checker.isEmpty(dictionaryCategoryVo.getParentId())){
				throw new FroadServerException("添加DictionaryCategory失败,原因:ParentId不能为空!");
			}
			if(Checker.isEmpty(dictionaryCategoryVo.isEnable)){
				throw new FroadServerException("添加DictionaryCategory失败,原因:isEnable不能为空!");
			}
			
			//vo 转 po 
			DictionaryCategory dictionaryCategory = (DictionaryCategory)BeanUtil.copyProperties(DictionaryCategory.class, dictionaryCategoryVo);

			Long id = dictionaryCategoryLogic.addDictionaryCategory(dictionaryCategory);
			if(id == null || id<=0){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加字典类别信息");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加字典类别addDictionaryCategory失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加字典类别addDictionaryCategory异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		
		return (ResultVo)BeanUtil.copyProperties(ResultVo.class,result);
		
	}


	/**
     * 修改字典类别
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param dictionaryCategoryVo 字典类别修改对象
     * @return ResultVo
     */
	@Override
	public ResultVo updateDictionaryCategory(OriginVo originVo, DictionaryCategoryVo dictionaryCategoryVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改字典类别信息");
			LogUtils.addLog(originVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(dictionaryCategoryVo.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			// vo 转 po 
			DictionaryCategory dictionaryCategory = (DictionaryCategory)BeanUtil.copyProperties(DictionaryCategory.class, dictionaryCategoryVo);
						
			if (!dictionaryCategoryLogic.updateDictionaryCategory(dictionaryCategory)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改字典类别DB操作异常");
			}
			
		}catch (FroadServerException e) {
			LogCvt.error("修改字典类别updateDictionaryCategory失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改字典类别updateDictionaryCategory异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	

	/**
     * 删除字典类别
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 字典类别id
     * @return ResultVo
     */
	@Override
	public ResultVo deleteDictionaryCategory(OriginVo originVo, long id) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除字典类别信息");
			LogUtils.addLog(originVo);
			
			if(id<=0){
				throw new FroadServerException("删除DictionaryCategory失败,原因:id值有误!");
			}
			
			if (!dictionaryCategoryLogic.deleteDictionaryCategory(id)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除字典类别DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除字典类别deleteDictionaryCategory失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除字典类别deleteDictionaryCategory异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

	

	/**
	 * 查询字典类别列表
	 * @param dictionaryCategoryVo 过滤参数
	 * @return DictionaryCategoryListVoRes
	 */
	@Override
	public DictionaryCategoryListVoRes getDictionaryCategory(DictionaryCategoryVo dictionaryCategoryVo) throws TException {
		
		DictionaryCategoryListVoRes voRes = new DictionaryCategoryListVoRes();
		Result result = new Result();
		List<DictionaryCategoryVo> dictionaryCategoryVoVoList = null;
		try{
			DictionaryCategory dictionaryCategory =(DictionaryCategory)BeanUtil.copyProperties(DictionaryCategory.class, dictionaryCategoryVo);
			
			List<DictionaryCategory> dictionaryCategoryList = dictionaryCategoryLogic.findDictionaryCategory(dictionaryCategory);
			dictionaryCategoryVoVoList =(List<DictionaryCategoryVo>)BeanUtil.copyProperties(DictionaryCategoryVo.class, dictionaryCategoryList);
			
			
		}catch (FroadServerException e) {
			LogCvt.error("查询字典类别列表getDictionaryCategory失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("查询字典类别列表getDictionaryCategory异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.boss_error.getCode());
			result.setResultDesc(ResultCode.boss_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setDictionaryCategoryVoList(dictionaryCategoryVoVoList==null?new ArrayList<DictionaryCategoryVo>():dictionaryCategoryVoVoList);
		return voRes;
	}


	
	/**
     * 导出脚本(导出该类别下所有的字典、条目信息)
     * @param type 1-类别Id 2-字典Id
     * @param ids 字典类别id或字典id集合
     */
	public ExportResultRes getDictionaryCategoryExportUrl(int type,List<Long> ids) throws TException {
		ExportResultRes res = new ExportResultRes();
		try{
			if(type !=1 && type !=2){
				throw new Exception("导出脚本：type类型有误");
			}
			
			res = dictionaryCategoryLogic.findDictionaryCategoryExport(type,ids);
		} catch (Exception e) {
			LogCvt.error("导出脚本getDictionaryCategoryExportUrl异常，原因:" + e.getMessage(), e);
		}
		return res;
	}
	
	
	

	
}

