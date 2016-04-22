package com.froad.cbank.coremodule.normal.boss.controller.label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ExportRelateActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductDelReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductLabelAddReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductLabelReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ProductWeightReq;
import com.froad.cbank.coremodule.normal.boss.support.label.ProductLabelSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.ExcelReaderUtils;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 商品推荐活动标签
 * @ClassName ProductLableController
 * @author zxl
 * @date 2015年10月28日 下午2:30:31
 */
@Controller
@RequestMapping("/prolable")
public class ProductLableController {
	
	@Resource
	ProductLabelSupport productLabelSupport;
	
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年10月28日 下午2:38:25
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_label_product_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(ModelMap model, ProductLabelReq req){	
		try {
			model.clear();
			model.putAll(productLabelSupport.list(req));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 详情
	 * @tilte detail
	 * @author zxl
	 * @date 2015年10月28日 下午2:38:33
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String id,String activityNo,String clientId){	
		try {
			if(StringUtils.isBlank(id)){
				new RespError(model,"id不能为空");
				return;
			}
			if(StringUtils.isBlank(clientId)){
				new RespError(model,"客户端不能为空");
				return;
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.clear();
			model.putAll(productLabelSupport.detail(id,activityNo,clientId,user.getName()));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 新增
	 * @tilte add
	 * @author zxl
	 * @date 2015年10月28日 下午2:38:39
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_label_product_add"})
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody ProductLabelAddReq req){	
		try {
			if(req.getActivityName().length() >50){
				throw new BossException("活动名称限50字符内!");
			}
			if(req.getActivityDesc().length() < 10 || req.getActivityDesc().length() > 300){
				throw new BossException("描述限300字符内,最少10个字符!");
			}
			model.clear();
			model.putAll(productLabelSupport.add(req,(BossUser)request.getAttribute(Constants.BOSS_USER)));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 修改
	 * @tilte mdy
	 * @author zxl
	 * @date 2015年10月28日 下午2:38:47
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_label_product_modify"})
	@RequestMapping(value = "/mdy", method = RequestMethod.POST)
	public void mdy(ModelMap model, HttpServletRequest request,@RequestBody ProductLabelAddReq req){	
		try {
			if(req.getId()==null){
				new RespError(model,"id不能为空");
				return;
			}
			if(req.getActivityName().length() >50){
				throw new BossException("活动名称限50字符内!");
			}
			if(req.getActivityDesc().length() < 10 || req.getActivityDesc().length() > 300){
				throw new BossException("描述限300字符内,最少10个字符!");
			}
			model.clear();
			model.putAll(productLabelSupport.mdy(req,(BossUser)request.getAttribute(Constants.BOSS_USER)));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 禁用
	 * @tilte del
	 * @author zxl
	 * @date 2015年10月28日 下午2:38:52
	 * @param model
	 * @param req
	 */
	@Auth(keys={"boss_label_product_disable"})
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public void del(ModelMap model,HttpServletRequest request,String id,String clientId){	
		try {
			if(StringUtils.isBlank(id)){
				new RespError(model,"id不能为空");
				return;
			}
			if(StringUtils.isBlank(clientId)){
				new RespError(model,"客户端不能为空");
				return;
			}
			BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
			model.clear();
			model.putAll(productLabelSupport.del(id,clientId,user.getName()));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 关联商品列表
	 * @tilte productList
	 * @author zxl
	 * @date 2015年10月29日 上午9:49:21
	 * @param model
	 * @param map
	 */
	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public void productList(ModelMap model, ProductListReq req){	
		try {
			if(StringUtils.isBlank(req.getClientId())){
				new RespError(model,"客户端不能为空");
				return;
			}
			model.clear();
			model.putAll(productLabelSupport.productList(req));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 关联商品添加
	 * @tilte productAdd
	 * @author zxl
	 * @date 2015年10月29日 上午10:14:38
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public void productAdd(ModelMap model,HttpServletRequest request,@RequestBody ProductAddReq req){	
		try {
			if(StringUtils.isBlank(req.getClientId())){
				new RespError(model,"客户端不能为空");
				return;
			}
			if(!req.getWeight().matches("[1-9][0-9]*$")){
				throw new BossException("权重必须为大于零小于100，000的整数!");
			}
			if(Integer.parseInt(req.getWeight()) < 0 || Integer.parseInt(req.getWeight()) > 100000){
				throw new BossException("权重必须为大于零小于100，000的整数!");
			}
			model.clear();
			model.putAll(productLabelSupport.productAdd(req,(BossUser)request.getAttribute(Constants.BOSS_USER)));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 关联商品删除
	 * @tilte productDel
	 * @author zxl
	 * @date 2015年10月29日 上午10:23:38
	 * @param model
	 * @param request
	 * @param req
	 */
	@RequestMapping(value = "/product/del", method = RequestMethod.DELETE)
	public void productDel(ModelMap model,HttpServletRequest request,ProductDelReq req){	
		try {
			model.clear();
			model.putAll(productLabelSupport.productDel(req,(BossUser)request.getAttribute(Constants.BOSS_USER)));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 权重调整
	 * @tilte productWeigth
	 * @author zxl
	 * @date 2015年10月29日 上午10:31:26
	 * @param model
	 * @param request
	 * @param req
	 */
	@RequestMapping(value = "/product/weight", method = RequestMethod.GET)
	public void productWeigth(ModelMap model,HttpServletRequest request,ProductWeightReq req){	
		try {
			if(!req.getWeight().matches("[1-9][0-9]*$")){
				throw new BossException("权重必须为大于零小于100，000的整数!");
			}
			if(Integer.parseInt(req.getWeight()) < 0 || Integer.parseInt(req.getWeight()) > 100000){
				throw new BossException("权重必须为大于零小于100，000的整数!");
			}
			model.clear();
			model.putAll(productLabelSupport.productWeight(req,(BossUser)request.getAttribute(Constants.BOSS_USER)));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	
	/**
	 * 根据商品ID获取商品名称
	 * @author yfy
	 * @date: 2015年10月21日 下午17:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 * @param outletId
	 */
	@RequestMapping(value = "/product/name", method = RequestMethod.GET)
	public void getOutletNameByOutletId(ModelMap model, HttpServletRequest request,String clientId,String productId) {
		try {
			if(!StringUtils.isNotBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			if(!StringUtils.isNotBlank(productId)){
				throw new BossException("商品ID不能为空!");
			}
			model.clear();
			model.putAll(productLabelSupport.getProductNameByProductId(clientId,productId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("根据商品ID获取商品名称请求异常"+e.getMessage(), e);
			new RespError(model, "根据商品ID获取商品名称失败!!!");
		}
	}
	
	/**
	 * 商品文件上传
	 * @tilte productUpload
	 * @author zxl
	 * @date 2015年10月29日 上午10:43:59
	 * @param request
	 * @param res
	 * @param file
	 * @throws IOException
	 */
	@ImpExp
	@RequestMapping(value = "/product/upload", method = RequestMethod.POST)
	public void productUpload(HttpServletRequest request,HttpServletResponse res,@RequestParam(value="file") MultipartFile file) throws IOException{	
		String json = "";
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			String activityId = (String)request.getParameter("id");
			String activityNo = (String)request.getParameter("activityNo");
			String clientId = (String)request.getParameter("clientId");
			if(StringUtil.isBlank(activityNo)){
				throw new BossException("活动编号不能为空!");
			}
			if(StringUtil.isBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			if(file.getOriginalFilename().indexOf(".xls") == -1  && file.getOriginalFilename().indexOf(".xlsx") == -1) {
				throw new BossException("文件格式有误，请上传excel文件"); 
			}
			
			List<List<String>> data = new ArrayList<List<String>>();
			//excel读取
			ExcelReaderUtils.readExcel(file.getInputStream(), file.getOriginalFilename(), null, 5, data);
			
			//上传数据检查
			if(data.size()==0){
				throw new BossException("上传文件内容为空!");
			}
			for(int i = 0; i< data.size();i++){
				List<String> l = data.get(i);
				//首行
				if(i == 0){
					if(!("序号".equals(l.get(0))&&"商品名称".equals(l.get(1))&&"商品ID".equals(l.get(2).toUpperCase())&&"权重".equals(l.get(3))&&"活动编号".equals(l.get(4)))){
						throw new BossException("文件内容格式错误");
					}
				}else{
					if(StringUtils.isBlank(l.get(2))){
						throw new BossException((i+1)+"行数据商品ID为空。请修改后重新操作");
					}
					if(!NumberUtils.isNumber(l.get(3))){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					String weight = NumberUtil.subZeroAndDot(l.get(3));
					if(!weight.matches("[1-9][0-9]*$")){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					if(StringUtils.isBlank(l.get(3))||Double.parseDouble(l.get(3))<=0||Double.parseDouble(l.get(3))>=100000){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新操作");					
					}
					if(StringUtil.isBlank(l.get(4))){
						throw new BossException((i+1)+"行数据活动编号不能为空。请修改后重新上传");
					}
					if(!activityNo.equals(l.get(4))){
						throw new BossException((i+1)+"行数据活动编号与本次活动不一致。请修改后重新操作");
					}
				}
			}
			
			HashMap<String,Object> map = productLabelSupport.productUpload(request,activityId,activityNo,clientId,data);
			
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		}catch (BossException e) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (Exception e) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		}	
	}
	
	
	/**
	 * 
	 * <p>Title:关联商品信息导出</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午4:20:34 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value="/export",method=RequestMethod.GET)
	public void exportProductRelateActivityTag(ModelMap model,ExportRelateActivityVoReq req){
		LogCvt.info("关联商品信息导出条件:"+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(productLabelSupport.exportRelateActivity(req));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
}
