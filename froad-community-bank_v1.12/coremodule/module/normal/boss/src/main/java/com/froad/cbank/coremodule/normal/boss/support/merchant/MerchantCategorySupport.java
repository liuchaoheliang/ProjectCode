package com.froad.cbank.coremodule.normal.boss.support.merchant;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.AddMerchantCategoryVo;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.DeleteMerchantReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantCategoryReq;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantCategoryRes;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.parentCategoryRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BossMerchantCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.merchant.BossMerchantCategoryInfoRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryListRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryVo;
import com.froad.thrift.vo.merchant.BossParentCategoryListReq;
import com.froad.thrift.vo.merchant.BossParentCategoryListRes;
import com.froad.thrift.vo.merchant.BossParentCategoryVo;
import com.froad.thrift.vo.merchant.MerchantCategoryVo;

/**
 * 
 * 类描述:商户分类support
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author "chenzhangwei"
 * @time 2015年9月17日 上午10:21:07
 * @email "chenzhangwei@f-road.com.cn"
 */
@Service
public class MerchantCategorySupport {
	@Resource
	BossMerchantCategoryService.Iface BossmerchantCategoryService;
	
	@Resource
	ClientSupport clientSupport;
	List<MerchantCategoryRes> mcList;
	/**
	 * 
	 * 方法描述:商户列表查询
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月17日 上午10:23:27
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public HashMap<String, Object> queryList(MerchantCategoryReq voReq,OriginVo originVo) throws TException, IllegalAccessException, InvocationTargetException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();				
		List<MerchantCategoryRes> cateList = new ArrayList<MerchantCategoryRes>();
		MerchantCategoryRes res;
		String clientName="";
		String clientId=voReq.getClientId();
		List<ClientRes>clientList=clientSupport.getClient();
		//执行查询
		BossMerchantCategoryListRes ls=BossmerchantCategoryService.findAll(clientId,voReq.isIncludeDisable(),originVo);
		if (!ArrayUtil.empty(ls.getVoList())){
			for (BossMerchantCategoryVo mres : ls.getVoList()) {
				res=new MerchantCategoryRes();
				BeanUtils.copyProperties(res, mres);
				//设置客户端名称
				res=setClientName(res,clientList);
				//设置是否启用
				res.setEnable(mres.isIsEnable());
				cateList.add(res);
			}
		}
		mcList=cateList;
		for (MerchantCategoryRes vo : cateList) {
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
		
		/**
		 * 前端页面显示根节点是客户端名称
		 */
		for (ClientRes c : clientList) {
			if(c.getClientId().equals(clientId)){
				clientName=c.getClientName();
			}
		}
		
		res=new MerchantCategoryRes();
		res.setId(0);
		res.setClientId(clientId);
		res.setParentId(0);
		res.setClientName(clientName);
		res.setName(clientName);
		res.setOpen(true);
		res.setEnable(true);
		cateList.add(res);
		
		resMap.put("list", cateList);
		return  resMap;
	}
	
	/**
	 * 
	 * 方法描述:保存商户信息（添加或者修改）
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月17日 上午11:01:47
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	public HashMap<String, Object> save(AddMerchantCategoryVo voReq,OriginVo originVo) throws TException, BossException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		BossMerchantCategoryVo vo=new BossMerchantCategoryVo();
		BeanUtils.copyProperties(vo, voReq);
//		LogCvt.info("前台传进来的voReq："+JSON.toJSONString(voReq));
//		LogCvt.info("经过拷贝之后的vo："+JSON.toJSONString(vo));
		if(voReq!=null&&StringUtil.isNotBlank(voReq.getId())){//修改商户信息
			vo.setId(Long.parseLong(voReq.getId()));
			//执行修改
			LogCvt.info("执行修改......");
			ResultVo resultVo=BossmerchantCategoryService.updateBossMerchantCategoryVo(originVo, vo);
			//判断响应是否成功
			if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
				resMap.put("code",Constants.RESULT_SUCCESS);
				resMap.put("message", resultVo.getResultDesc());
			}else{
				throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
			}
			//resMap.put("resultVo", resultVo);
		}else{
			//添加商户信息
			LogCvt.info("执行新增......");
			ResultVo resultVo=BossmerchantCategoryService.addBossMerchantCategoryVo(originVo, vo);
			//判断响应是否成功
			if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
				resMap.put("code",Constants.RESULT_SUCCESS);
				resMap.put("message", resultVo.getResultDesc());
			}else{
				throw new BossException(resultVo.getResultCode(), resultVo.getResultDesc());
			}
			//resMap.put("merchantAddVo", resultVo);
		}
		resMap.put("voReq", voReq);
		return  resMap;
	}
	
	/**
	 * 
	 * 方法描述:商户分类删除
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月17日 下午4:13:47
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws NumberFormatException 
	 */
	public HashMap<String,Object> delete(DeleteMerchantReq req) throws NumberFormatException, TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//ResultVo resultVo=BossmerchantCategoryService.deleteMerchantCategory(Long.parseLong(req.getMerchantCategoryId()));
		//resMap.put("resultVo", resultVo);
		return  resMap;
	}
	
	/**
	 * 
	 * 方法描述:查询单个商户的详细信息(用户在点击查询的或者编辑)
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月18日 上午11:55:58
	 * @param originVo
	 * @param id
	 * @return
	 * @throws NumberFormatException
	 * @throws TException
	 */
	public MerchantCategoryRes findMerchantCategoryById(OriginVo originVo,String id,String clientId) throws NumberFormatException, TException{
		MerchantCategoryRes res=new MerchantCategoryRes();
		BossMerchantCategoryInfoRes temp=BossmerchantCategoryService.getBossMerchantCategoryById(originVo, Long.parseLong(id),clientId);
		BeanUtils.copyProperties(res, temp.vo);
		//获得所有的客户端
		List<ClientRes>clientList=clientSupport.getClient();
		//设置客户端名称
		res=setClientName(res,clientList);
		//设置启用状态
		res.setEnable(temp.vo.isIsEnable());
		return res;
	}
	
	/**
	 *
	 * 方法描述:用于设置客户端名称
	 * @author "chenzhangwei"
	 * @email "chenzhangwei@f-road.com.cn"
	 * @time 2015年9月24日 上午11:20:13
	 * @param clientId
	 * @param res
	 * @return
	 */
	private MerchantCategoryRes setClientName(MerchantCategoryRes res,List<ClientRes>clientList){
		for (ClientRes c : clientList) {
			if(c.getClientId().equals(res.getClientId())){
				res.setClientName(c.getClientName());
			}
		}
		return res;
	}
	/**
	 * 根据id查询name
	 * @param originVo
	 * @param id
	 * @param clientId
	 * @return
	 * @throws NumberFormatException
	 * @throws TException
	 */
	private String fullNameSet(Long id) throws NumberFormatException, TException{
		for (MerchantCategoryRes mc : mcList) {
			if(id.equals(mc.getId())){
				return mc.getName();
			}
		}
		return "";
	}
	
	/**
	 * 获取所有商户分类
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月12日 下午5:45:14
	 * @return map
	 */
	public HashMap<String, Object> getAllMerchantCategory(String clientId, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<MerchantCategoryRes> list = new ArrayList<MerchantCategoryRes>();
		MerchantCategoryRes temp = null;
		//调用SERVER端接口
		BossMerchantCategoryListRes resp = BossmerchantCategoryService.findAll(clientId, false, org);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(!ArrayUtil.empty(resp.getVoList())) {
				for(BossMerchantCategoryVo tmp : resp.getVoList()) {
					temp = new MerchantCategoryRes();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
		}
		map.put("mcList", list);
		return map;
	}
	
	/**
	 * 商户分类导出接口
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:20:14
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> exportMerchantCategory(OriginVo org, String clientId, Long categoryId) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用SERVER端接口
		try {
			ExportResultRes resp = BossmerchantCategoryService.merchantCategoryDetailExport(org, clientId, categoryId);
			//返回导出Excel下载路径
			ResultVo result = resp.getResultVo();
			if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
				map.put("url", resp.getUrl());
				map.put("code", result.getResultCode());
				map.put("message", result.getResultDesc());
			} else {
				throw new BossException(result.getResultCode(), result.getResultDesc());
			}
		} catch (TTransportException e) {
			if(e.getCause() instanceof SocketTimeoutException) {
				throw new BossException("商户分类明细导出超时，请稍后重试");
			} else {
				throw e;
			}
		}
		return map;
	}
	
	/**
	 * 商户分类的商户导入接口
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:20:14
	 * @return map
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public HashMap<String, Object> importMerchantCategory(MultipartFile file, OriginVo org) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Workbook wb = null;
		//封装请求对象
		List<MerchantCategoryVo> req = new ArrayList<MerchantCategoryVo>();
		MerchantCategoryVo tmp = null;
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
			tmp = new MerchantCategoryVo();
			
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
			cell = row.getCell(2);//商户分类
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setMerchantCategory(cell.getStringCellValue().trim());
			}
			cell = row.getCell(3);//商户分类详细
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setMerchantCategryDetail(cell.getStringCellValue().trim());
			}
			cell = row.getCell(4);//商户名称
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setMerchantName(cell.getStringCellValue().trim());
			}
			cell = row.getCell(5);//商户ID
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setMerchantId(cell.getStringCellValue().trim());
			}
			cell = row.getCell(6);//营业执照号
			if(cell != null) {
				cell.setCellType(cell.CELL_TYPE_STRING);
				tmp.setLicense(cell.getStringCellValue().trim());
			}
			req.add(tmp);
		}
		//调用SERVER端接口
		ExportResultRes resp = BossmerchantCategoryService.merchantCategoryInput(org, req);
		//封装返回对象
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else if("11004".equals(result.getResultCode())) {//11004为部分数据导入失败的结果码
			map.put("url", resp.getUrl());
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 获取父级分类列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年10月28日 下午2:20:14
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getParentCategoryList(String clientId, String categoryId, String type) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<parentCategoryRes> list = new ArrayList<parentCategoryRes>();
		parentCategoryRes temp = null;
		//封装请求对象
		BossParentCategoryListReq req = new BossParentCategoryListReq();
		req.setClientId(clientId);
		req.setCategoryId(categoryId);
		req.setType(type);
		//调用SERVER端接口
		BossParentCategoryListRes resp = BossmerchantCategoryService.getParentCategoryList(req);
		//返回导出Excel下载路径
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(!ArrayUtil.empty(resp.getVoList())) {
				for(BossParentCategoryVo tmp : resp.getVoList()) {
					temp = new parentCategoryRes();
					BeanUtils.copyProperties(temp, tmp);
					list.add(temp);
				}
			}
			map.put("parentCategoryList", list);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
}
