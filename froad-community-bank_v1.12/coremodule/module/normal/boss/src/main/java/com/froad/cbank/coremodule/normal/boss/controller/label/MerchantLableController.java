package com.froad.cbank.coremodule.normal.boss.controller.label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.thrift.TException;
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
import com.froad.cbank.coremodule.normal.boss.pojo.label.AddMerchantActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.DeleteRelateMerchantReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.EnableMerchantReqVo;
import com.froad.cbank.coremodule.normal.boss.pojo.label.ExportRelateActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantActivityDetailReqVo;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantLableActivityReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantRelatePageVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.MerchantWeigthvoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.label.RelateMerchantActivityVoReq;
import com.froad.cbank.coremodule.normal.boss.support.label.MerchantLableSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.ExcelReaderUtils;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;


/**
 * 
 * @author chenzhangwei
 * @createTime 2015年10月22日 下午5:31:20
 * @desc 商户活动controller
 */

@Controller
@RequestMapping(value="/merchantActivity")
public class MerchantLableController {
	
	@Resource
	private MerchantLableSupport merchantLableSupport;
	
	/**
	 * 
	 * @desc 分页查询商户活动列表
	 * @createTime 2015年10月22日 下午5:30:37
	 */
	@Auth(keys={"boss_label_merchant_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void merchantActivityPage(ModelMap model, MerchantLableActivityReq voReq){	
		LogCvt.info("分页查询商户活动列表："+JSON.toJSONString(voReq));
		//查询全部审核中的状态
		if("3".equals(voReq.getStatus())){
			String status = "3,4,5";
			voReq.setStatus(status);
		}
		try {
			model.clear();
			model.putAll(merchantLableSupport.queryMerchantActivitysByPage(voReq));	
		} catch (Exception e) {
			LogCvt.error("商户活动列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户活动列表查询失败!!!");
		}	
	}
	/**
	 * 
	 * @desc 活动详情
	 * @createTime 2015年10月23日 下午1:42:54
	 */
	@RequestMapping(value = "/findMerchantTagDetail", method = RequestMethod.GET)
	public void merchantDetails(HttpServletRequest request,ModelMap model,MerchantActivityDetailReqVo voReq){
		LogCvt.info("商户活动列表详情查询条件："+JSON.toJSONString(voReq));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		voReq.setOperator(user.getName());
		try {
			model.putAll(merchantLableSupport.findMerchantTagDetail(voReq));
		} catch (NumberFormatException e) {
			LogCvt.error("商户活动列表详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户活动列表详情查询失败!!!");
		} catch (TException e) {
			LogCvt.error("商户活动列表详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户活动列表详情查询失败!!!");
		}
	}
	
	/**
	 * 
	 * @desc 删除关联商户
	 * @createTime 2015年10月26日 上午9:18:57
	 */
	@RequestMapping(value="/deleteRelateMerchant",method = RequestMethod.POST)
	public void deleteRelateMerchant(@RequestBody DeleteRelateMerchantReq req,ModelMap model,HttpServletRequest request){
		LogCvt.info("删除关联商户条件："+JSON.toJSONString(req));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		req.setOperator(user.getName());
		try {
			model.putAll(merchantLableSupport.deleteRelateMerchant(req));
		} catch (NumberFormatException e) {
			LogCvt.error("删除关联商户请求异常"+e.getMessage(), e);
			new RespError(model, "删除关联商户失败!!!");
		} catch (TException e) {
			LogCvt.error("删除关联商户请求异常"+e.getMessage(), e);
			new RespError(model, "删除关联商户失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		}
	}
	
	/**
	 * 
	 * @desc 调整关联商户权重
	 * @createTime 2015年10月26日 上午9:19:22
	 */
	@RequestMapping(value="/adjustMerchantWeight",method = RequestMethod.POST)
	public void adjustMerchantWeight(@RequestBody MerchantWeigthvoReq req,ModelMap model,HttpServletRequest request){
		LogCvt.info("调整关联商户权重条件："+JSON.toJSONString(req));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		req.setOperator(user.getName());
		try {
			if(!req.getWeight().matches("[1-9][0-9]*$")){
				throw new BossException("权重必须为大于零小于100,000的整数!");
			}
			if(Integer.parseInt(req.getWeight()) < 0 || Integer.parseInt(req.getWeight()) > 100000){
				throw new BossException("权重必须为大于零小于100,000的整数!");
			}
			model.putAll(merchantLableSupport.adjustMerchantWeight(req));
		} catch (NumberFormatException e) {
			LogCvt.error("调整上商户权重请求异常"+e.getMessage(), e);
			new RespError(model, "调整商户权重失败!!!");
		} catch (TException e) {
			LogCvt.error("调整上商户权重请求异常"+e.getMessage(), e);
			new RespError(model, "调整商户权重失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		}
	}
	
	/**
	 * 
	 * @desc 启用/禁用商户推荐活动标签
	 * @createTime 2015年10月26日 上午9:20:57
	 */
	@Auth(keys={"boss_label_merchant_disable"})
	@RequestMapping(value="/enableMerchantRecommendActivityTag",method = RequestMethod.POST)
	public void enableMerchantRecommendActivityTag(@RequestBody EnableMerchantReqVo req,ModelMap model,HttpServletRequest request){
		LogCvt.info("启用/禁用商户推荐活动条件："+JSON.toJSONString(req));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		req.setOperator(user.getName());
		try {
			model.putAll(merchantLableSupport.enableMerchantRecommendActivityTag(req));
		} catch (NumberFormatException e) {
			LogCvt.error("启用/禁用商户推荐活动请求异常"+e.getMessage(), e);
			new RespError(model, "启用/禁用商户推荐活动失败!!!");
		} catch (TException e) {
			LogCvt.error("启用/禁用商户推荐活动请求异常"+e.getMessage(), e);
			new RespError(model, "启用/禁用商户推荐活动失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		}
	}
	
	/**
	 * 
	 * @desc 添加商户推荐活动
	 * @createTime 2015年10月27日 上午9:38:31
	 */
	@Auth(keys={"boss_label_merchant_add"})
	@RequestMapping(value="/addMerchantActivity",method = RequestMethod.POST)
	public void addMerchantLable(@RequestBody AddMerchantActivityVoReq req,ModelMap model,HttpServletRequest request){
		LogCvt.info("添加商户推荐活动条件："+JSON.toJSONString(req));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		req.setOperator(user.getName());
		try {
			if(req.getActivityDesc().length()< 10 || req.getActivityDesc().length() > 300){
				throw new BossException("描述限300字符内,最少10个字符!");
			}
			if(req.getActivityName().length() >50){
				throw new BossException("活动名称限50字符内!");
			}
			model.putAll(merchantLableSupport.addMerchantLable(req));
		} catch (TException e) {
			LogCvt.error("添加商户推荐活动请求异常"+e.getMessage(), e);
			new RespError(model, "添加商户推荐活动失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		}
	}
	/**
	 * 
	 * @desc 修改商户推荐活动
	 * @createTime 2015年11月3日 下午1:46:41
	 */
	@Auth(keys={"boss_label_merchant_modify"})
	@RequestMapping(value="/updateMerchantActivity",method = RequestMethod.POST)
	public void updateMerchantActivityTag(@RequestBody AddMerchantActivityVoReq req,ModelMap model,HttpServletRequest request){
		LogCvt.info("修改商户推荐活动条件："+JSON.toJSONString(req));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		req.setOperator(user.getName());
		try {
			if(req.getId()==0){
				throw new BossException("活动ID不能为空!");
			}
			if(req.getActivityDesc().length()< 10 || req.getActivityDesc().length() > 300){
				throw new BossException("描述限300字符内,最少10个字符!");
			}
			if(req.getActivityName().length() >50){
				throw new BossException("活动名称限50字符内!");
			}
			model.putAll(merchantLableSupport.updateMerchantActivityTag(req));
		} catch (TException e) {
			LogCvt.error("修改商户推荐活动请求异常"+e.getMessage(), e);
			new RespError(model, "修改商户推荐活动失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		}
	}
	
	/**
	 * 
	 * @desc 关联商户列表
	 * @createTime 2015年10月27日 下午5:29:44
	 */
	@RequestMapping(value="/relateMerchantInfo",method = RequestMethod.POST)
	public void relateMerchantActivity(@RequestBody RelateMerchantActivityVoReq req,ModelMap model,HttpServletRequest request){
		LogCvt.info("商户关联条件："+JSON.toJSONString(req));
		model.clear();
		BossUser user = (BossUser) request.getAttribute(Constants.BOSS_USER);
		req.setOperator(user.getName());
		try {
			model.putAll(merchantLableSupport.relateMechantActivity(req));
		} catch (TException e) {
			LogCvt.error("商户关联请求异常"+e.getMessage(), e);
			new RespError(model, "商户关联失败!!!");
		} catch (BossException e) {
			new RespError(model, e);
		}
	}
 
	/**
	 * 
	 * @desc 关联商户信息分页查询
	 * @createTime 2015年10月28日 上午11:09:54
	 */
	@RequestMapping(value="/findRelateMerchantInfoByPage",method = RequestMethod.GET)
	public void findRelateMerchantInfoByPage(ModelMap model, MerchantRelatePageVoReq voReq){
		LogCvt.info("关联商户信息分页查询条件："+JSON.toJSONString(voReq));
		model.clear();
		try {
			model.putAll(merchantLableSupport.findRelate(voReq));
		} catch (NumberFormatException e) {
			LogCvt.error("关联商户信息分页查询请求异常"+e.getMessage(), e);
			new RespError(model, "关联商户信息分页查询失败!!!");
		} catch (TException e) {
			LogCvt.error("关联商户信息分页查询请求异常"+e.getMessage(), e);
			new RespError(model, "关联商户信息分页查询失败!!!");
		}
	}
	
	/**
	 * 
	 * @desc 根据营业执照查询商户名称
	 * @createTime 2015年11月2日 上午11:22:28
	 */
	@RequestMapping(value="/queryMerchantNameByLicense",method = RequestMethod.GET)
	public void queryMerchantNameByLicense(ModelMap model,HttpServletRequest req) throws Exception{
		String clientId=(String)req.getParameter("clientId");
		String license=(String)req.getParameter("license");
		LogCvt.info(" 根据营业执照查询商户名称条件："+JSON.toJSONString("clientId: "+clientId+", license: "+license));
		
		try{
				if(StringUtil.isBlank(license)){
					throw new BossException("营业执照不能为空!");
				}
				if(StringUtil.isBlank(clientId)){
					throw new BossException("客户端不能为空!");
				}
			model.putAll(merchantLableSupport.queryMerchantNameByLicense(clientId, license));
			}catch(TException e){
				LogCvt.error("根据营业执照查询商户名称请求异常"+e.getMessage(), e);
				new RespError(model, "根据营业执照查询商户名称失败!!!");
			}catch(BossException e){
				new RespError(model, e);
			}
	}
	/**
	 * 
	 * @desc 批量导入关联商户
	 * @createTime 2015年10月28日 下午1:54:14
	 */
	@RequestMapping(value="/inputRelateMerchantInfo",method=RequestMethod.POST)
	@ImpExp
	public void inputRelateMerchantInfo( @RequestParam("file") MultipartFile file, ModelMap model,HttpServletRequest req, HttpServletResponse res)throws Exception {  
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");
		String activityNo = (String)req.getParameter("activityNo");
		String clientId = (String)req.getParameter("clientId");
		String activityId=(String) req.getParameter("activityId");
		LogCvt.info(" 批量关联商户条件："+JSON.toJSONString("clientId: "+clientId+"activityNo: "+activityNo+"activityId： "+activityId));
		BossUser user = (BossUser) req.getAttribute(Constants.BOSS_USER);
		String userName=user.getName();
		String json = "";
		try {
			if(StringUtil.isBlank(activityNo)){
				throw new BossException("活动编号不能为空!");
			}
			
			if(StringUtil.isBlank(clientId)){
				throw new BossException("客户端不能为空!");
			}
			if(StringUtil.isBlank(activityId)){
				throw new BossException("活动id不能为空!");
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
					if(!("序号".equals(l.get(0))&&"商户名称".equals(l.get(1))&&"商户ID".equals(l.get(2).toUpperCase())&&"权重".equals(l.get(3))&&"活动编号".equals(l.get(4)))){
						throw new BossException("文件内容格式错误");
					}
				}else{
					if(StringUtils.isBlank(l.get(2))){
						throw new BossException((i+1)+"行数据商品ID为空，请修改后重新操作");
					}
					if(!NumberUtils.isNumber(l.get(3))){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					String weight = NumberUtil.subZeroAndDot(l.get(3));
					if(!weight.matches("[1-9][0-9]*$")){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100,000整数。请修改后重新上传");
					}
					if(StringUtils.isBlank(l.get(3))||Double.parseDouble(l.get(3))<=0||Double.parseDouble(l.get(3))>=100000){
						throw new BossException((i+1)+"行权重数据非法，必须为大于零小于100000整数，请修改后重新操作");					
					}
					if(!activityNo.equals(l.get(4))){
						throw new BossException((i+1)+"行数据活动编号与本次活动不一致，请修改后重新操作");
					}
				}
			}
			HashMap<String,Object> map = merchantLableSupport.inputRelateMerchantInfo(activityNo,clientId,userName, data,activityId);
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
	 * <p>Title:关联商户信息导出</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午4:20:34 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value="/export",method=RequestMethod.GET)
	public void exportProductRelateActivityTag(ModelMap model,ExportRelateActivityVoReq req){
		LogCvt.info("关联商户信息导出条件:"+JSON.toJSONString(req));
		try {
			model.clear();
			model.putAll(merchantLableSupport.exportRelateActivity(req));
		}catch (BossException e) {
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}	
	}
	  
}
