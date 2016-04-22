package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ResultCode;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BossMerchantCategoryLogic;
import com.froad.logic.impl.BossMerchantCategoryLogicImpl;
import com.froad.po.MerchantCategory;
import com.froad.po.MerchantCategoryInput;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossMerchantCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.merchant.BossMerchantCategoryInfoRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryListRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryVo;
import com.froad.thrift.vo.merchant.BossParentCategoryListReq;
import com.froad.thrift.vo.merchant.BossParentCategoryListRes;
import com.froad.thrift.vo.merchant.MerchantCategoryVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.LogUtils;
import com.froad.util.MerchantInputComparator;
import com.froad.util.PropertiesUtil;

public class BossMerchantCategoryServiceImpl extends BizMonitorBaseService implements BossMerchantCategoryService.Iface {

	private BossMerchantCategoryLogic merchantCategoryLogic = new BossMerchantCategoryLogicImpl();
	
	
	public BossMerchantCategoryServiceImpl(){}

	public BossMerchantCategoryServiceImpl(String name, String version) {
		super(name, version);
	}

	/**
	 * 查询商户分类列表
	 * 
	 * @param clientId
	 * @param iscludeDisable
	 * @param originVo
	 * 
	 * @return BossMerchantCategoryListRes
	 * @author liuyanyun 2015-9-21 下午13:10
	 */
	@Override
	public BossMerchantCategoryListRes findAll(String clientId,
			boolean iscludeDisable, OriginVo originVo) throws TException {
		LogCvt.info("findAll查询商户分类列表 ,OriginVo" + JSON.toJSONString(originVo));
		
		BossMerchantCategoryListRes listRes = new BossMerchantCategoryListRes();
		List<BossMerchantCategoryVo> voList = new ArrayList<BossMerchantCategoryVo>();
		List<MerchantCategory> merchantCategoryList = new ArrayList<MerchantCategory>();
		ResultVo resultVo = new ResultVo();
		
		try {
			// 添加操作日志记录
			originVo.setDescription("查询商户分类列表");
			LogUtils.addLog(originVo);

			if (Checker.isEmpty(clientId)) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("客户端ID不能为空");
				listRes.setResultVo(resultVo);
				return listRes;
			}
			
			merchantCategoryList = merchantCategoryLogic.findCategorys(clientId, iscludeDisable);
			
			if (merchantCategoryList != null) {
				BossMerchantCategoryVo vo = null;
				for (MerchantCategory mo : merchantCategoryList) {
					vo = new BossMerchantCategoryVo();
					vo = merchantCategoryCopy(mo);
					voList.add(vo);
				}
			}
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
		} catch (Exception e) {
			LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
		}
		
		listRes.setResultVo(resultVo);
		listRes.setVoList(voList);
		return listRes;
	}

	/**
	 * 根据商户分类id,clientId 查询商户分类信息
	 * 
	 * @param originVo
	 * @param id
	 * @param clientId
	 * 
	 * @return BossMerchantCategoryInfoRes
	 * @author liuyanyun 2015-9-21 下午14:33
	 */
	@Override
	public BossMerchantCategoryInfoRes getBossMerchantCategoryById(
			OriginVo originVo, long id, String clientId) throws TException {
		LogCvt.info("getBossMerchantCategoryById 查询商户分类信息 ,OriginVo" + JSON.toJSONString(originVo));
		
		BossMerchantCategoryInfoRes infoRes = new BossMerchantCategoryInfoRes();
		BossMerchantCategoryVo vo = new BossMerchantCategoryVo();
		ResultVo resultVo = new ResultVo();
		
		try{
			// 添加操作日志记录
			originVo.setDescription("查询商户分类信息");
			LogUtils.addLog(originVo);
		
			if (Checker.isEmpty(clientId)) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("客户端ID不能为空");
				infoRes.setResultVo(resultVo);
				return infoRes;
			}
			if (id == 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("id不能为空");
				infoRes.setResultVo(resultVo);
				return infoRes;
			}
			
			vo = merchantCategoryLogic.getBossMerchantCategoryById(id, clientId);
			
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
	 * 新增商户分类
	 * 
	 * @param originVo
	 * @param bossMerchantCategoryVo
	 * 
	 * @return ResultVo
	 * @author liuyanyun 2015-9-21 下午14:53
	 */
	@Override
	public ResultVo addBossMerchantCategoryVo(OriginVo originVo,
			BossMerchantCategoryVo bossMerchantCategoryVo) throws TException {
		LogCvt.info("addBossMerchantCategoryVo 新增商户分类 ,OriginVo" + JSON.toJSONString(originVo));
		
		ResultVo resultVo = new ResultVo();
		int rowsNumber = 0;
		
		try{
			// 添加操作日志记录
			originVo.setDescription("新增商户分类");
			LogUtils.addLog(originVo);

			if (Checker.isEmpty(bossMerchantCategoryVo.getClientId()) || Checker.isEmpty(bossMerchantCategoryVo.getIcoUrl()) 
					|| Checker.isEmpty(bossMerchantCategoryVo.getName())) {
				
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("新增商户分类信息不能为空"); 
				return resultVo;
			}
			if (bossMerchantCategoryVo.getOrderValue() < 0 || bossMerchantCategoryVo.getOrderValue() == 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("新增商户分类，分类序号只能为正整数");
				return resultVo;
			}
			
			MerchantCategory mo = BossMerchantCategoryVoCopy(bossMerchantCategoryVo);
			rowsNumber = merchantCategoryLogic.addBossMerchantCategoryVo(mo);

			if (rowsNumber > 0) {
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}
			if (rowsNumber < 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(mo.getName() +"  分类名称已存在，请填正确的分类名称");
			}
		}catch (Exception e) {
			LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
		}
		
		return resultVo;
	}

	/**
	 * 修改商户分类
	 * 
	 * @param originVo
	 * @param bossMerchantCategoryVo
	 * 
	 * @return ResultVo
	 * @author liuyanyun 2015-9-21 下午15:08
	 */
	@Override
	public ResultVo updateBossMerchantCategoryVo(OriginVo originVo,
			BossMerchantCategoryVo bossMerchantCategoryVo) throws TException {
		LogCvt.info("updateBossMerchantCategoryVo 修改商户分类 ,OriginVo" + JSON.toJSONString(originVo));
		
		ResultVo resultVo = new ResultVo();
		int rowsNumber = 0;
		
		try{
			// 添加操作日志记录
			originVo.setDescription("修改商户分类");
			LogUtils.addLog(originVo);

			if (Checker.isEmpty(bossMerchantCategoryVo)) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("修改商户分类信息不能为空");
				return resultVo;
			}
			
			if (bossMerchantCategoryVo.getOrderValue() < 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("修改商户分类，分类序号只能为正整数");
				return resultVo;
			}
		
			MerchantCategory mo = BossMerchantCategoryVoCopy(bossMerchantCategoryVo);
			rowsNumber = merchantCategoryLogic.updateBossMerchantCategoryVo(mo);

			if (rowsNumber > 0) {
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}
			if (rowsNumber < 0) {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(mo.getName() +"  分类名称已存在，请填正确的分类名称");
			}
		}catch (Exception e) {
			LogCvt.error("操作失败，异常信息：" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
		}
		
		return resultVo;
	}

	/**
	 * 商户分类vo转换
	 */
	private BossMerchantCategoryVo merchantCategoryCopy(MerchantCategory mo) {
		BossMerchantCategoryVo vo = new BossMerchantCategoryVo();
		vo.setId(mo.getId());
		vo.setClientId(mo.getClientId());
		vo.setName(mo.getName());
		vo.setParentId(mo.getParentId());
		vo.setTreePath(mo.getTreePath());
		if (mo.getIsDelete()) {
			mo.setIsDelete(false);
		} else {
			mo.setIsDelete(true);
		}
		vo.setIsEnable(mo.getIsDelete());
		vo.setIcoUrl(mo.getIcoUrl());
		vo.setOrderValue(mo.getOrderValue().shortValue());
		return vo;
	}

	/**
	 * 商户分类mo转换
	 */
	private MerchantCategory BossMerchantCategoryVoCopy(BossMerchantCategoryVo vo) {
		MerchantCategory mo = new MerchantCategory();
		mo.setId(vo.getId());
		mo.setClientId(vo.getClientId());
		mo.setName(vo.getName());
		mo.setParentId(vo.getParentId());
		mo.setTreePath(vo.getTreePath());
		mo.setIcoUrl(vo.getIcoUrl());
		if (vo.isEnable) {
			vo.setIsEnable(false);
		}else {
			vo.setIsEnable(true);
		}
		mo.setIsDelete(vo.isEnable);
		mo.setOrderValue((int) vo.getOrderValue());
		return mo;
	}

	@Override
	public ExportResultRes merchantCategoryInput(OriginVo originVo, List<MerchantCategoryVo> vos) throws TException {
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		String url = "";
		try {
			// 添加操作日志记录
			originVo.setDescription("商户分类的商户导入");
			LogUtils.addLog(originVo);
			
			int inputCount = vos.size();
			
			List<MerchantCategoryInput> inputs = (List<MerchantCategoryInput>) BeanUtil.copyProperties(MerchantCategoryInput.class, vos);
			List<MerchantCategoryInput> list = merchantCategoryLogic.merchantCategoryInput(inputs);
			
			int errCount = 0;
			
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("所属客户端");
			header.add("商户分类");
			header.add("商户分类详细");
			header.add("商户名称");
			header.add("商户ID");
			header.add("营业执照号");
			
			List<List<String>> allData = new ArrayList<List<String>>();
			if(Checker.isNotEmpty(list)){
				errCount = list.size();
				allData = convertExportMerchant(list);
				
				ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
				excelWriter.write(header, allData, "商户分类商户列表", "商户分类商户列表导入失败数据");
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
				
			}else{
				StringBuilder builder = new StringBuilder(ResultCode.success.getMsg());
				builder.append(", 一共导入").append(inputCount).append("条数据。");
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(builder.toString());
			}
			
			
			res.setResultVo(resultVo);
			res.setUrl(url);
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
			res.setUrl(url);
		}
		return res;
	}
	
	public static void main(String args[]) {
		PropertiesUtil.load();
		try {
			BossMerchantCategoryServiceImpl b = new BossMerchantCategoryServiceImpl();
			OriginVo o = new OriginVo();
			//b.merchantCategoryDetailExport(o, "chongqing", 0L);
			BossParentCategoryListReq req = new BossParentCategoryListReq();
			req.setCategoryId("100000037");
			req.setClientId("taizhou");
			req.setType("0");
			//b.getParentCategoryList(req); 
			
			BossProductCategoryServiceImpl b2 = new BossProductCategoryServiceImpl();
			b2.productCategoryDetailExport(o,"1", "jilin", 0L);
			//b.merchantCategoryDetailExport(o, "chongqing", 100000019L);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public ExportResultRes merchantCategoryDetailExport(OriginVo originVo, String clientId, long categoryId)
			throws TException {
		ExportResultRes res = new ExportResultRes();
		ResultVo resultVo = new ResultVo();
		try {
			Map<String, Object> map = merchantCategoryLogic.merchantCategoryDetailExport(clientId, categoryId);
			List<MerchantCategoryInput> list = (List<MerchantCategoryInput>)map.get("resultList");
			Collections.sort(list, new MerchantInputComparator());
			
			
			String categoryName = (String)map.get("categoryName");
			String fileName = categoryName + "_" + DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, new Date());
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("所属客户端");
			header.add("商户分类");
			header.add("商户分类详细");
			header.add("商户名称");
			header.add("商户ID");
			header.add("营业执照号");
			
			List<List<String>> allData = new ArrayList<List<String>>();
			allData = convertExportMerchant(list);
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			excelWriter.write(header, allData,  "商户分类商户列表数据", fileName );
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
			LogCvt.error("导出商户分类的商户列表失败", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(resultVo);
		return res;
	}

	
	private List<List<String>>  convertExportMerchant(List<MerchantCategoryInput> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(list == null || list.size() == 0) {
			return perList;
		}
		for(int i = 0; i < list.size(); i++) {
			List<String> rowList = new ArrayList<String>();
			MerchantCategoryInput vo = list.get(i);
			rowList.add(i + 1 + "");
			rowList.add(vo.getClientName());
			rowList.add(vo.getMerchantCategory());
			rowList.add(vo.getMerchantCategryDetail());
			rowList.add(vo.getMerchantName());
			rowList.add(vo.getMerchantId());
			rowList.add(vo.getLicense());
			perList.add(rowList);
		}
		return perList;
	}

	@Override
	public BossParentCategoryListRes getParentCategoryList(BossParentCategoryListReq req) {
		return merchantCategoryLogic.getParentCategoryList(req);
	}
	
}
