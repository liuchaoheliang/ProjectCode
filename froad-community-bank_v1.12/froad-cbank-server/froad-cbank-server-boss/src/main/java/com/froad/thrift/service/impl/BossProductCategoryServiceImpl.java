package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BossProductCategoryLogic;
import com.froad.logic.impl.BossProductCategoryLogicImpl;
import com.froad.po.ProductCategory;
import com.froad.po.ProductCategoryInput;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossProductCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.product.BossProductCategoryInfoRes;
import com.froad.thrift.vo.product.BossProductCategoryListRes;
import com.froad.thrift.vo.product.BossProductCategoryVo;
import com.froad.thrift.vo.product.ProductCategoryVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.LogUtils;
import com.froad.util.ProductInputComparator;

public class BossProductCategoryServiceImpl extends BizMonitorBaseService implements BossProductCategoryService.Iface{
	
	private BossProductCategoryLogic productCategoryLogic = new BossProductCategoryLogicImpl();
	
	public BossProductCategoryServiceImpl() {}
	
	
	public BossProductCategoryServiceImpl(String name, String version) {
		super(name, version);
	}
	
	 /**
     * 查询商品分类列表
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param clientId 客户端Id
     * @param iscludeDisable 是否包含非禁用商品分类
     * @param isMall 是否是精品商城商品类型0-否 1-是
	 * 
	 * @return BossProductCategoryListRes
	 * @author liuyanyun 2015-9-18 上午09:50
	 */
	@Override
	public BossProductCategoryListRes findAll(OriginVo originVo,
			String clientId,boolean iscludeDisable,boolean isMall) throws TException {
		 //LogCvt.info("findAll查询商品分类列表 ,OriginVo" + JSON.toJSONString(originVo));
		
		 BossProductCategoryListRes listRes = new BossProductCategoryListRes();
		 List<BossProductCategoryVo> voList = new ArrayList<BossProductCategoryVo>();
		 List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		 ResultVo resultVo = new ResultVo();
		 
		 try{
			 //添加操作日志记录
			 //originVo.setDescription("查询商品分类列表");
			 //LogUtils.addLog(originVo);
		 
			 if (Checker.isEmpty(clientId)) {
				 resultVo.setResultCode(ResultCode.failed.getCode());
				 resultVo.setResultDesc("客户端Id不能为空"); 
				 listRes.setResultVo(resultVo);
				 return listRes;
			 }
			 
			 productCategoryList = productCategoryLogic.findCategorys(clientId, iscludeDisable,isMall);
			 
			 if (productCategoryList != null && productCategoryList.size() > 0) {
				 BossProductCategoryVo vo = null;
				 for (ProductCategory po : productCategoryList) {
					 vo = new BossProductCategoryVo();
					 vo = productCategoryCopy(po);
					 voList.add(vo);
				 }
				 
				 resultVo.setResultCode(ResultCode.success.getCode());
				 resultVo.setResultDesc(ResultCode.success.getMsg());
			 }
		 }catch (Exception e) {
			 LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			 resultVo.setResultCode(ResultCode.failed.getCode());
			 resultVo.setResultDesc(ResultCode.failed.getMsg());
		 }
		 listRes.setResultVo(resultVo);
		 listRes.setVoList(voList);
		 return listRes;
	}
	
	/**
	 * 根据分类id,clientId 查询商品分类信息
	 * @param originVo
	 * @param id
	 * @param clientId
	 * 
	 * @return BossProductCategoryInfoRes
	 * @author liuyanyun 2015-9-18  下午14:33
	 */
	@Override
	public BossProductCategoryInfoRes getBossProductCategoryById(
			OriginVo originVo, long id, String clientId) throws TException {
		 //LogCvt.info("getBossProductCategoryById 查询商品分类信息 ,OriginVo" + JSON.toJSONString(originVo));
		
		 BossProductCategoryInfoRes infoRes = new BossProductCategoryInfoRes();
		 BossProductCategoryVo vo = new BossProductCategoryVo();
		 ResultVo resultVo = new ResultVo();
		 
		 try{
			 //添加操作日志记录
			 //originVo.setDescription("查询商品分类信息");
			 //LogUtils.addLog(originVo);
		 
			 if (Checker.isEmpty(clientId)) {
				 resultVo.setResultCode(ResultCode.failed.getCode());
				 resultVo.setResultDesc("客户端Id不能为空"); 
				 infoRes.setResultVo(resultVo);
				 return infoRes;
			 }
			 if (id == 0) {
				 resultVo.setResultCode(ResultCode.failed.getCode());
				 resultVo.setResultDesc("商品分类Id不能为空");
				 infoRes.setResultVo(resultVo);
				 return infoRes;
			 }
			 
			 vo = productCategoryLogic.getBossProductCategoryById(id, clientId);
			 if (vo != null && !"".equals(vo)) {
				 infoRes.setVo(vo);
				 resultVo.setResultCode(ResultCode.success.getCode());
				 resultVo.setResultDesc(ResultCode.success.getMsg());
			 }
		 }catch (Exception e) {
			 LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			 resultVo.setResultCode(ResultCode.failed.getCode());
			 resultVo.setResultDesc(ResultCode.failed.getMsg()); 
		 }
		 infoRes.setVo(vo);
		 infoRes.setResultVo(resultVo);
		 return infoRes;
	}


	/**
	 * 新增商品分类
	 * @param originVo
	 * @param bossProductCategoryVo
	 * 
	 * @return ResultVo
	 * @author liuyanyun 2015-9-18 下午15:23
	 */
	@Override
	public ResultVo addBossProductCategoryVo(OriginVo originVo,
			BossProductCategoryVo bossProductCategoryVo) throws TException {
		
		ResultVo resultVo = new ResultVo();
		int rowsNumber = 0;
		
		try{
			//添加操作日志记录
			originVo.setDescription("新增商品分类");
			LogUtils.addLog(originVo);
			
			if (Checker.isEmpty(bossProductCategoryVo.getClientId()) 
					|| Checker.isEmpty(bossProductCategoryVo.getIcoUrl()) 
					|| Checker.isEmpty(bossProductCategoryVo.getName())
					|| Checker.isEmpty(bossProductCategoryVo.isMarket)
					|| Checker.isEmpty(bossProductCategoryVo.isMall)
					|| Checker.isEmpty(bossProductCategoryVo.isEnable)
					|| Checker.isEmpty(bossProductCategoryVo.getOrderValue())
					|| Checker.isEmpty(bossProductCategoryVo.getParentId())
				) {
				
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("新增商品分类信息不能为空"); 
				return resultVo;
			}
			
			if (bossProductCategoryVo.getOrderValue() < 0  || bossProductCategoryVo.getOrderValue() == 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("新增商品分类，分类序号必须为正整数"); 
				return resultVo;
			}
			
			ProductCategory po = BossProductCategoryVoCopy(bossProductCategoryVo);
			rowsNumber = productCategoryLogic.addBossProductCategoryVo(po);
			if (rowsNumber > 0) {
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}
			if (rowsNumber < 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(po.getName() +"  分类名称已存在，请填正确的分类名称");
			}
		}catch (Exception e) {
			LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg()); 
		}
		return resultVo;
	}
	
	/**
	 * 修改商品分类
	 * @param originVo
	 * @param bossProductCategoryVo
	 * 
	 * @return ResultVo
	 * @author liuyanyun 2015-9-18 下午16:47
	 */
	@Override
	public ResultVo updateBossProductCategoryVo(OriginVo originVo,
			BossProductCategoryVo bossProductCategoryVo) throws TException {
		//LogCvt.info("updateBossProductCategoryVo 修改商品分类 ,OriginVo" + JSON.toJSONString(originVo));
		
		ResultVo resultVo = new ResultVo();
		int rowsNumber = 0;
		
		try{
			//添加操作日志记录
			originVo.setDescription("修改商品分类");
			LogUtils.addLog(originVo);
			
			if (Checker.isEmpty(bossProductCategoryVo)) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("修改商品分类信息不能为空");
				return resultVo;
			}
			
			if (bossProductCategoryVo.getOrderValue() < 0  || bossProductCategoryVo.getOrderValue() == 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("修改商品分类信息，分类序号必须为正整数");
				return resultVo;
			}
			
			ProductCategory po = BossProductCategoryVoCopy(bossProductCategoryVo);
			 
			rowsNumber = productCategoryLogic.updateBossProductCategoryVo(po);
			if (rowsNumber > 0) {
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}
			if (rowsNumber < 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(po.getName() +"  分类名称已存在，请填正确的分类名称");
			}
		}catch (Exception e) {
			LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg()); 
		}
		return resultVo;
	}
	
	
	/**
	 * 商品分类vo转换
	 */
	private BossProductCategoryVo productCategoryCopy(ProductCategory po){
		BossProductCategoryVo vo = new BossProductCategoryVo();
		vo.setId(po.getId());
		vo.setClientId(po.getClientId());
		vo.setName(po.getName());
		vo.setParentId(po.getParentId());
		vo.setTreePath(po.getTreePath());
		if (po.getIsDelete()) {
			po.setIsDelete(false);
		} else {
			po.setIsDelete(true);
		}
		vo.setIsEnable(po.getIsDelete());
		vo.setIcoUrl(po.getIcoUrl());
		vo.setOrderValue(po.getOrderValue().shortValue());
		vo.setIsMall(po.getIsMall());
		vo.setIsMarket(po.getIsMarket());
		return vo;
	}
	
	/**
	 * 商品分类po转换
	 */
	private ProductCategory BossProductCategoryVoCopy(BossProductCategoryVo vo){
		ProductCategory po = new ProductCategory();
		po.setId(vo.getId());
		po.setClientId(vo.getClientId());
		po.setName(vo.getName());
		po.setParentId(vo.getParentId());
		po.setTreePath(vo.getTreePath());
		po.setIcoUrl(vo.getIcoUrl());
		if (vo.isEnable) {
			vo.setIsEnable(false);
		} else {
			vo.setIsEnable(true);
		}
		po.setIsDelete(vo.isIsEnable());
		po.setOrderValue((int)vo.getOrderValue());
		po.setIsMarket(vo.isIsMarket());
		po.setIsMall(vo.isIsMall());
		return po;
	}

	/**
	 * 商品分类 商品导入
	 * @param inputType 导入类型  1-商品分类   2-精品商城商品分类(区别于excel内容不一样)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExportResultRes productCategoryProductInput(OriginVo originVo,String inputType, List<ProductCategoryVo> vos)
			throws TException {
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		String url = "";
		String inputDesc = (inputType.equals("2")?"精品商城":"");
		boolean isMall = "2".equals(inputType);
		try{
			//添加操作日志记录
			originVo.setDescription(inputDesc+"商品分类的商品导入");
			LogUtils.addLog(originVo);
			
			int inputCount = vos.size();
			
			List<ProductCategoryInput> inputs = (List<ProductCategoryInput>) BeanUtil.copyProperties(ProductCategoryInput.class, vos);
			List<ProductCategoryInput> list = productCategoryLogic.productCategoryInput(inputs,isMall);
			
			int errCount = 0;
			
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("所属客户端");
			header.add("商品分类");
			header.add("商品分类详细");
			if(isMall){
				header.add(inputDesc+"商品名称");
			}else{
				header.add("商品名称");
			}
			header.add("商品ID");
			if(!isMall){//普通商品分类
				header.add("所属商户");
				header.add("所属商户ID");
			}
			
			List<List<String>> allData = new ArrayList<List<String>>();
			if(Checker.isNotEmpty(list)){
				errCount = list.size();
				allData = convertExportProduct(inputType,list);
				
				ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
				excelWriter.write(header, allData, inputDesc+"商品分类商品列表", inputDesc+"商品分类商品列表导入失败数据");
				url = excelWriter.getUrl();
				
				if(StringUtils.isNotEmpty(url)) {
					StringBuilder builder = new StringBuilder(ResultCode.input_partial_fail.getMsg());
					builder.append("，").append("剩余").append(errCount).append("条数据未能成功导入，请重新下载附件查看。");
					
					resultVo.setResultCode(ResultCode.input_partial_fail.getCode());
					resultVo.setResultDesc(builder.toString());
				} else {
					resultVo.setResultCode(ResultCode.failed.getCode());
					resultVo.setResultDesc(ResultCode.failed.getMsg());
				}
				
			} else {
				StringBuilder builder = new StringBuilder(ResultCode.success.getMsg());
				builder.append(", 一共导入").append(inputCount).append("条数据。");
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(builder.toString());
			}
			
			res.setResultVo(resultVo);
			res.setUrl(url);
		}catch (Exception e) {
			LogCvt.error(inputDesc+"商品分类的商品导入异常",e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
			res.setUrl(url);
		}
		return res;
	}

	
	/**
	 * 导出分类明细
	 * @param exportType 导入类型  1-商品分类   2-精品商城商品分类(区别于excel内容不一样)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExportResultRes productCategoryDetailExport(OriginVo originVo,String exportType, String clientId, long categoryId)throws TException {
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		boolean isMall = "2".equals(exportType);
		String exportDesc = isMall ? "精品商城" : "";
		try {
			Map<String, Object> map = productCategoryLogic.productCategoryDetailExport(clientId, categoryId, isMall);
			List<ProductCategoryInput> list = (List<ProductCategoryInput>)map.get("resultList");
			Collections.sort(list, new ProductInputComparator());
			String categoryName = (String)map.get("categoryName");
			String fileName = categoryName + "_" + DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, new Date());
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("所属客户端");
			header.add("商品分类");
			header.add("商品分类详细");
			if(isMall){
				header.add(exportDesc+"商品名称");
			}else{
				header.add("商品名称");
			}
			header.add("商品ID");
			if(!isMall){
				header.add("所属商户");
				header.add("所属商户ID");
			}
			
			List<List<String>> allData = new ArrayList<List<String>>();
			allData = convertExportProduct(exportType,list);
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			excelWriter.write(header, allData,  exportDesc+"商品分类商品列表数据", fileName );
			String url = excelWriter.getUrl();
			if(StringUtils.isNotEmpty(url)) {
				res.setUrl(url);
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			LogCvt.error(exportDesc+"导出商品分类的商品列表异常", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(resultVo);
		return res;
	}
	
	/**
	 * 商品分类导出excel内容写入
	 * @param writeType 写入类型  1-商品分类   2-精品商城商品分类
	 * @param list
	 * @return
	 */
	private List<List<String>>  convertExportProduct(String writeType,List<ProductCategoryInput> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(list == null || list.size() == 0) {
			return perList;
		}
		for(int i = 0; i < list.size(); i++) {
			List<String> rowList = new ArrayList<String>();
			ProductCategoryInput vo = list.get(i);
			rowList.add(i + 1 + "");
			if (Checker.isNotEmpty(vo.getClientName())) {
				rowList.add(vo.getClientName());
			} else {
				rowList.add("--");
			}
			if (Checker.isNotEmpty(vo.getProductCategory())) {
				rowList.add(vo.getProductCategory());
			} else {
				rowList.add("--");
			}
			if (Checker.isNotEmpty(vo.getProductCategryDetail())) {
				rowList.add(vo.getProductCategryDetail());
			} else {
				rowList.add("--");
			}
			if (Checker.isNotEmpty(vo.getProductName())) {
				rowList.add(vo.getProductName());
			} else {
				rowList.add("--");
			}
			if (Checker.isNotEmpty(vo.getProductId())) {
				rowList.add(vo.getProductId());
			} else {
				rowList.add("--");
			}
			//普通商品类型才累加写入所属商户、所属商户ID
			if(writeType.equals("1")){
				if (Checker.isNotEmpty(vo.getMerchantName())) {
					rowList.add(vo.getMerchantName());
				} else {
					rowList.add("--");
				}
				if (Checker.isNotEmpty(vo.getMerchantId())) {
					rowList.add(vo.getMerchantId());
				} else {
					rowList.add("--");
				}
			}
			
			perList.add(rowList);
		}
		return perList;
	}

	
}
