package com.froad.cbank.coremodule.normal.boss.support.product;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCategoryVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ProductCategoryVoRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BossProductCategoryService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.product.BossProductCategoryInfoRes;
import com.froad.thrift.vo.product.BossProductCategoryListRes;
import com.froad.thrift.vo.product.BossProductCategoryVo;


/**
 * 商品分类support
 * @author liaopeixin
 * 2915-9-17
 */

@Service
public class ProductCategorySupport {
	@Resource
	BossProductCategoryService.Iface bossProductCategoryService;
	@Resource
	ProductCategoryService.Iface productCategoryService;
	
	@Resource
	ClientSupport clientSupport;
	
	//全部分类
	List<ProductCategoryVoRes> cateList;
	/**
	 * 商品分类列表查询
	 * @author liaopeixin
	 * @param clientId
	 * @return
	 * @throws TException
	 * @throws BossException 
	 * @throws NumberFormatException 
	 */
	public HashMap<String,Object> queryProCategoryList(String clientId,OriginVo originVo,boolean isMall) throws TException, NumberFormatException, BossException{
		HashMap<String,Object> map=new HashMap<String,Object>();	

		BossProductCategoryListRes res=null;
		ProductCategoryVoRes cgvo=null;
		String clientName="";
		String reclientId=clientId;
		List<ProductCategoryVoRes> resList=new ArrayList<ProductCategoryVoRes>();
		res=bossProductCategoryService.findAll(originVo, clientId, true, isMall);
		List<ClientRes>clientList=clientSupport.getClient();
		for (BossProductCategoryVo vo : res.getVoList()) {
			cgvo=new  ProductCategoryVoRes();
			BeanUtils.copyProperties(cgvo, vo);
			for (ClientRes c : clientList) {
				if(c.getClientId().equals(vo.getClientId())){
					cgvo.setClientName(c.getClientName());
				}
			}
			cgvo.setEnable(vo.isIsEnable());
			resList.add(cgvo);
		}
		//设置分类全称
		cateList=resList;
		for (ProductCategoryVoRes vo : resList) {
			String fullName="";
			if(StringUtil.isNotBlank(vo.getTreePath())){
				String[] trees=vo.getTreePath().split(" ");
				if(trees.length>0){
					for (int i=0;i<trees.length;i++) {
						if(StringUtil.isNotBlank(trees[i])){
							fullName+=fullNameSet(Long.valueOf(trees[i]));
							if(trees.length-1>i)
								fullName+="->";		
						}		
					}
				}else{
					fullName=fullNameSet(Long.valueOf(trees[0]));
				}			
			}
			vo.setFullName(fullName);
		}
		cgvo=new  ProductCategoryVoRes();
		
		//设置clientName
		for (ClientRes c : clientList) {
			if(clientId.equals(c.getClientId())){
				clientName=c.getClientName();
			}
		}
		/**
		 * 为了让前台页面根节点是客户端名称
		 */
		cgvo.setId(Long.valueOf(0));
		cgvo.setClientName(clientName);
		cgvo.setParentId(null);
		cgvo.setClientId(reclientId);
		cgvo.setName(clientName);
		cgvo.setEnable(true);
		cgvo.setOpen(true);
		
		resList.add(cgvo);
		
		map.put("categoryList", resList);
		return map;
	}
	/**
	 * 商品分类详情接口
	 * @author liaopeixin
	 * @param categoryId
	 * @return
	 * @throws NumberFormatException
	 * @throws TException
	 * @throws BossException 
	 */
	public ProductCategoryVoRes queryProCategoryDetails(String categoryId,String clientId,OriginVo originVo) throws NumberFormatException, TException, BossException{
		return findById(Long.valueOf(categoryId),clientId,originVo);
	}
	/**
	 * 商品分类新增/修改
	 * @author liaopeixin
	 * @param categoryReq
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public HashMap<String,Object> addOrUpdate(ProductCategoryVoReq categoryReq,OriginVo originVo) throws BossException, TException{
		HashMap<String,Object> resMap=new HashMap<String,Object>();
		BossProductCategoryVo req=new BossProductCategoryVo();
		ResultVo result=null;
		
		//将前台请求参数copy到后台请求参数中
		BeanUtils.copyProperties(req, categoryReq);
		//如果id不为null，则为修改操作
		if(categoryReq.getId()!=null){
		result=bossProductCategoryService.updateBossProductCategoryVo(originVo, req);
		}else{
		//否则为新增操作
		result=bossProductCategoryService.addBossProductCategoryVo(originVo, req);
		}
		//判断响应是否成功
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())){
			resMap.put("code",Constants.RESULT_SUCCESS);
			resMap.put("message", result.getResultDesc());
		}else{
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return resMap;
	}
	/**
	 * 根据商品分类id查询商品分类
	 * @author liaopeixin
	 * @param categoryId
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	public ProductCategoryVoRes findById(long categoryId,String clientId,OriginVo originVo) throws TException, BossException{
		ProductCategoryVoRes vo=new ProductCategoryVoRes();
		BossProductCategoryInfoRes res=null;
		BossProductCategoryVo resvo=null;
		
		res=bossProductCategoryService.getBossProductCategoryById(originVo, categoryId,clientId);
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			resvo=res.getVo();
			List<ClientRes>clientList=clientSupport.getClient();
			//将响应实体copy到原实体
			BeanUtils.copyProperties(vo, resvo);
			for (ClientRes c : clientList) {
				if(c.getClientId().equals(vo.getClientId())){
					vo.setClientName(c.getClientName());
				}
			}
			vo.setEnable(resvo.isIsEnable());
		}else{
			throw new BossException(res.getResultVo().getResultCode(),res.getResultVo().getResultDesc());
		}
		return vo;
	}
	
	/**
	 * 根据treePath查询分类全称
	 * @author liaopeixin
	 * @param categoryReq
	 * @param originVo
	 * @return
	 * @throws BossException 
	 * @throws TException 
	 */
	public String fullNameSet(Long id) throws TException, BossException{
		//设置分类全称
		for (ProductCategoryVoRes vo : cateList) {
			if(id.equals(vo.getId())){
				return vo.getName();
			}
		}
		return "";
	}
	
	/**
	 * 获取普通商品的商品分类
	 * <p>功能简述：根据Id查询商品分类</p> 
	 * <p>使用说明：根据Id查询商品分类</p> 
	 * <p>创建时间：2015年4月28日下午6:14:32</p>
	 * <p>作者: 陈明灿</p>
	 * @param parentId
	 * @return
	 * @throws TException 
	 */
	public Map<String, Object> queryCategoryById(Long parentId,
			String clientId) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductCategoryVoRes> list = null;
		List<ProductCategoryVo> list2= productCategoryService.queryManageProductCategorys(clientId, parentId);
		
		LogCvt.info("根据id查询商品分类--返回数据:" + JSON.toJSONString(list2));
		if (null != list2 && list2.size() > 0) {
			list = new ArrayList<ProductCategoryVoRes>();
			for (ProductCategoryVo productCategoryVo : list2) {
				ProductCategoryVoRes res = new ProductCategoryVoRes();
				res.setParentId(productCategoryVo.getParentId());
				res.setName(productCategoryVo.getName());
				res.setId(productCategoryVo.getId());
				res.setTreePath(productCategoryVo.getTreePath());
				list.add(res);
			}
			LogCvt.info("根据id查询商品分类--封装数据:" + JSON.toJSONString(list));
			map.put("list", list);
		}
		return map;
	}
	
	/**
	 * 获取精品商品分类接口
	 * @param parentId
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> queryBoutiqueGoodsCategoryById(Long parentId,
			String clientId) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductCategoryVoRes> list = null;
		List<ProductCategoryVo> list2= productCategoryService.queryBoutiqueGoodsCategorys(clientId, parentId);
		
		LogCvt.info("根据id查询精品商品分类--返回数据:" + JSON.toJSONString(list2));
		if (null != list2 && list2.size() > 0) {
			list = new ArrayList<ProductCategoryVoRes>();
			for (ProductCategoryVo productCategoryVo : list2) {
				ProductCategoryVoRes res = new ProductCategoryVoRes();
				res.setParentId(productCategoryVo.getParentId());
				res.setName(productCategoryVo.getName());
				res.setId(productCategoryVo.getId());
				res.setTreePath(productCategoryVo.getTreePath());
				list.add(res);
			}
			LogCvt.info("根据id查询精品商品分类--封装数据:" + JSON.toJSONString(list));
			map.put("list", list);
		}
		return map;
	}
	
	
	
	
	
	
	/**
	 * 获取所有商品分类
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月12日 下午6:09:14
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getAllProCategory(String clientId, OriginVo org,boolean isMall) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ProductCategoryVoRes> list = new ArrayList<ProductCategoryVoRes>();
		ProductCategoryVoRes temp = null;
		//调用SERVER端接口
		BossProductCategoryListRes resp = bossProductCategoryService.findAll(org, clientId, false, isMall);
		//封装返回结果对象
		if(!ArrayUtil.empty(resp.getVoList())) {
			for(BossProductCategoryVo tmp : resp.getVoList()) {
				temp = new ProductCategoryVoRes();
				BeanUtils.copyProperties(temp, tmp);
				list.add(temp);
			}
		}
		map.put("pcList", list);
		return map;
	}
	
	/**
	 * 商品分类导出接口
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:20:14
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> exportProductCategory(OriginVo org,String exportType, String clientId, Long categoryId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		try {
			ExportResultRes resp = bossProductCategoryService.productCategoryDetailExport(org,exportType,clientId,categoryId);
			//封装返回结果
			ResultVo result = resp.getResultVo();
			if (Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
				//返回导出Excel下载路径
				map.put("url", resp.getUrl());
				map.put("code", result.getResultCode());
				map.put("message", result.getResultDesc());
			} else {
				throw new BossException(result.getResultCode(), result.getResultDesc());
			}
		} catch (TTransportException e) {
			if(e.getCause() instanceof SocketTimeoutException) {
				throw new BossException("商品分类明细导出超时，请稍后重试");
			} else {
				throw e;
			}
		}
		return map;
	}
	
	/**
	 * 商品分类导入接口
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:20:14
	 * @return map
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public HashMap<String, Object> importProductCategory(MultipartFile file, OriginVo org,String exportType) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Workbook wb = null;
		//封装请求对象
		List<com.froad.thrift.vo.product.ProductCategoryVo> req = new ArrayList<com.froad.thrift.vo.product.ProductCategoryVo>();
		com.froad.thrift.vo.product.ProductCategoryVo tmp = null;
		//解析Excel文件
		String fileName = file.getOriginalFilename();
		String det = fileName.substring(fileName.lastIndexOf("."));
		if(".xls".equals(det)) {//2003
			wb = new HSSFWorkbook(file.getInputStream());
		} else if(".xlsx".equals(det)) {//2007
			wb = new XSSFWorkbook(file.getInputStream());
		} else {
			throw new BossException("上传文件格式有误，请检查后再上传！");
		}
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();//获取表格总行数
		//第一行为标题行，故从第二行开始读取数据
		for(int i = 1; i <= rowNum; i++) {
			tmp = new com.froad.thrift.vo.product.ProductCategoryVo();
			
			Row row = sheet.getRow(i);//读取行
			Cell cell = null;
			cell = row.getCell(0);//序号
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setId(Long.parseLong(cell.getStringCellValue().trim()));
			}
			cell = row.getCell(1);//所属客户端
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setClientName(cell.getStringCellValue().trim());
			}
			cell = row.getCell(2);//商品分类
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setProductCategory(cell.getStringCellValue().trim());
			}
			cell = row.getCell(3);//商品分类详细
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setProductCategryDetail(cell.getStringCellValue().trim());
			}
			cell = row.getCell(4);//商品名称
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setProductName(cell.getStringCellValue().trim());
			}
			cell = row.getCell(5);//商品ID
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setProductId(cell.getStringCellValue().trim());
			}
			cell = row.getCell(6);//所属商户
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setMerchantName(cell.getStringCellValue().trim());
			}
			cell = row.getCell(7);//所属商户ID
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setMerchantId(cell.getStringCellValue().trim());
			}
			req.add(tmp);
		}
		//调用SERVER端接口
		ExportResultRes resp = bossProductCategoryService.productCategoryProductInput(org, exportType, req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else if("11004".equals(result.getResultCode())) {//11004为部分数据导入失败的结果码
			//返回导出Excel下载路径
			map.put("url", resp.getUrl());
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
}
